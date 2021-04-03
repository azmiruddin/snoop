package tub.ods.pch.channel.state;

import java.net.UnknownHostException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;

import com.datastax.driver.core.exceptions.NoHostAvailableException;

import tub.ods.pch.channel.node.EthProperties;
import tub.ods.pch.channel.node.EthereumConfig;
import tub.ods.pch.channel.SignedTransfer;
import tub.ods.pch.channel.SignedTransferUnlock;
import tub.ods.pch.channel.TransferRepository;
import tub.ods.pch.channel.TransferUnlockRepository;
//import papyrus.channel.node.util.Retriable;

@EnableConfigurationProperties(EthProperties.class)
@Component
public class OutgoingChannelPoolManager {
    private static final Logger log = LoggerFactory.getLogger(OutgoingChannelPoolManager.class);
    
    private final Map<Address, Map<Address, ChannelPoolProperties>> channelPools = new ConcurrentHashMap<>();
    private final Thread watchThread;

    @Autowired
    private EthereumConfig ethereumConfig;
    @Autowired
    private OutgoingChannelCoordinator registry;
    @Autowired
    private OutgoingChannelPoolRepository poolRepository;
    @Autowired
    private OutgoingChannelRepository channelRepository;
    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private TransferUnlockRepository unlockRepository;

    public OutgoingChannelPoolManager() {
        this.watchThread = new Thread(this::cycle,"Channel pool watcher");
    }

    private void cycle() {
        while (!Thread.interrupted()) {
            try {
                Map<Address, Map<Address, List<OutgoingChannelState>>> indexed = new HashMap<>();
                registry.all().forEach(
                    channel -> indexed.computeIfAbsent(channel.getChannel().getSenderAddress(), sender -> new HashMap<>())
                        .computeIfAbsent(channel.getChannel().getReceiverAddress(), receiver -> new ArrayList<>())
                        .add(channel)
                );
                indexed.forEach((sender, map) -> map.forEach((receiver,channels) -> managePool(sender, receiver, channels)));
                channelPools.forEach((sender, map) -> map.forEach((receiver,conf) -> {
                    if (indexed.get(sender) == null || indexed.get(sender).get(receiver) == null) {
                        managePool(sender, receiver, Collections.emptyList());
                    }
                }));

                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                break;
            } catch (Throwable e) {
                log.error("Cycle failed", e);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e1) {
                    break;
                }
            }
        }
    }

    private void managePool(Address senderAddress, Address receiverAddress, List<OutgoingChannelState> channels) {
        ChannelPoolProperties channelProperties = getProperties(senderAddress, receiverAddress);
        if (channelProperties == null) {
            channels.stream()
                .filter(ch -> ch.isActive() && ch.getOpenBlock() > 0)
                .sorted(Comparator.comparing(OutgoingChannelState::getOpenBlock))
                .forEach(c -> registry.setPolicy(c, OutgoingChannelPolicy.NONE));
        } else {
            channels.forEach(c -> registry.setPolicy(c, channelProperties.getPolicy()));
            long notClosedOrClosing = channels.stream().filter(c -> !c.isCloseRequested()).count();
            if (notClosedOrClosing < channelProperties.getMinActiveChannels()) {
                Address clientAddress = ethereumConfig.getClientAddress(senderAddress);
                OutgoingChannelState newChannel = new OutgoingChannelState(senderAddress, clientAddress, receiverAddress, channelProperties.getBlockchainProperties());
                registry.register(newChannel, channelProperties.getPolicy());
            }
            long active = channels.stream().filter(OutgoingChannelState::isActive).count();
            if (active > channelProperties.getMaxActiveChannels()) {
                channels.stream()
                    .filter(ch -> ch.isActive() && ch.getOpenBlock() > 0)
                    .sorted(Comparator.comparing(OutgoingChannelState::getOpenBlock))
                    .limit(active - channelProperties.getMaxActiveChannels())
                    .forEach(OutgoingChannelState::setNeedClose);
            }
        }
    }
/*
    @EventListener(ContextStartedEvent.class)
    public void start() throws Exception {
        Retriable.wrapTask(() -> {
            loadPools();
            loadChannels();
        })
        .retryOn(NoHostAvailableException.class, UnknownHostException.class)
        .withErrorMessage("Failed to load state from persistent store")
        .call();

        watchThread.start();
    }
*/
    @PreDestroy
    public void destroy() throws InterruptedException {
        //TODO close all channels
        watchThread.interrupt();
        watchThread.join();
    }
    
    private void loadPools() {
        for (OutgoingChannelPoolBean bean : poolRepository.all()) {
            if (!ethereumConfig.hasAddress(bean.getSender())) {
                log.warn("Address is not managed, skipping pool: " + bean.getSender());
            } else {
                createOrUpdatePool(
                    bean.getSender(),
                    bean.getReceiver(),
                    new ChannelPoolProperties(bean)
                );
            }
        }
    }

    private void loadChannels() {
        for (OutgoingChannelBean bean : channelRepository.all()) {
            if (bean.getStatus() == OutgoingChannelState.Status.SETTLED) continue;
            Optional<OutgoingChannelState> state = registry.getChannel(bean.getAddress());
            OutgoingChannelState channel;
            if (!state.isPresent()) {
                channel = registry.loadChannel(bean.getAddress());
                if (!ethereumConfig.hasAddress(channel.getChannel().getSenderAddress())) {
                    log.warn("Address is not managed, skipping channel: " + channel.getChannelAddress());
                    continue;
                }
                registry.register(channel, getPolicy(channel.getChannel().getSenderAddress(), channel.getChannel().getReceiverAddress()));
            } else {
                channel = state.get();
            }
                
            Iterable<SignedTransfer> transfers = transferRepository.getAllById(bean.getAddress());
            Iterable<SignedTransferUnlock> unlocks = unlockRepository.getAllById(bean.getAddress());
            channel.updatePersistentState(bean, transfers, unlocks);
        }
    }

    private OutgoingChannelPolicy getPolicy(Address sender, Address receiver) {
        ChannelPoolProperties properties = getProperties(sender, receiver);
        return properties != null ? properties.getPolicy() : OutgoingChannelPolicy.NONE;
    }

    public ChannelPoolProperties getProperties(Address sender, Address receiver) {
        Map<Address, ChannelPoolProperties> map = channelPools.get(sender);
        return map != null ? map.get(receiver) : null;
    }
    
    List<OutgoingChannelState> getChannels(Address sender, Address receiver) {
        return registry.getByParticipants(sender, receiver).collect(Collectors.toList());
    }

    public void addPool(Address sender, Address receiver, ChannelPoolProperties config) {
        OutgoingChannelPoolBean bean = new OutgoingChannelPoolBean(sender, receiver, config);
        poolRepository.save(bean);
        createOrUpdatePool(sender, receiver, config);
    }

    private void createOrUpdatePool(Address sender, Address receiver, ChannelPoolProperties config) {
        ethereumConfig.checkAddress(sender);
        channelPools.computeIfAbsent(sender, a -> new ConcurrentHashMap<>()).put(receiver, config);
    }

    public void removePool(Address sender, Address receiver) {
        Map<Address, ChannelPoolProperties> poolMap = channelPools.get(sender);
        if (poolMap == null) return;
        poolMap.remove(receiver);
    }
    
    void registerTransfer(SignedTransfer signedTransfer) throws SignatureException {
        OutgoingChannelState channelState = registry.getChannel(signedTransfer.getChannelAddress()).orElseThrow(
            () -> new IllegalStateException("Unknown channel address: " + signedTransfer.getChannelAddress())
        );
        channelState.registerTransfer(signedTransfer);
        transferRepository.save(signedTransfer);
        channelRepository.save(channelState.getPersistentState());
    }

    void registerTransferUnlock(SignedTransferUnlock transferUnlock) {
        OutgoingChannelState channelState = registry.getChannel(transferUnlock.getChannelAddress()).orElseThrow(
            () -> new IllegalStateException("Unknown channel address: " + transferUnlock.getChannelAddress())
        );
        channelState.unlockTransfer(transferUnlock);
        unlockRepository.save(transferUnlock);
        channelRepository.save(channelState.getPersistentState());
    }

    public boolean requestCloseChannel(Address address) {
        return registry.getChannel(address).map(OutgoingChannelState::setNeedClose).orElse(false);
    }

    public Collection<ChannelPoolProperties> getPools(Address senderAddress, Address receiverAddress) {
        Map<Address, ChannelPoolProperties> poolMap = channelPools.get(senderAddress);
        return receiverAddress == null ? poolMap.values() : Collections.singleton(poolMap.get(receiverAddress));
    }
}
