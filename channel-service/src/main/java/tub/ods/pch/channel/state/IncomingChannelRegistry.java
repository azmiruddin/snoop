package tub.ods.pch.channel.state;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;

import com.google.common.base.Throwables;

import tub.ods.pch.channel.model.ChannelContract;
import tub.ods.pch.channel.BlockchainChannel;
import tub.ods.pch.channel.node.ContractsManager;
import tub.ods.pch.channel.node.ContractsManagerFactory;

@Component
public class IncomingChannelRegistry {
    private static final Logger log = LoggerFactory.getLogger(IncomingChannelRegistry.class);
    
    private final Map<Address, IncomingChannelState> allChannelsByAddress = new ConcurrentHashMap<>();
    private final ContractsManager contractsManager;

    public IncomingChannelRegistry(ContractsManagerFactory factory) {
        contractsManager = factory.getMainContractManager();
    }
    
    public void setAddress(IncomingChannelState channel, Address channelAddress) {
        if (allChannelsByAddress.putIfAbsent(channelAddress, channel) != null) {
            throw new IllegalStateException("Duplicate channel: " + channel.getChannelAddress());
        }
    }
    
    public Optional<IncomingChannelState> get(Address channelAddress) {
        return Optional.ofNullable(allChannelsByAddress.get(channelAddress));
    }

    public IncomingChannelState getOrLoad(Address address) {
        try {
            return allChannelsByAddress.computeIfAbsent(address, this::loadChannel);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }


    private IncomingChannelState loadChannel(Address address) {
        log.info("Loading channel from blockchain: {}", address);
        ChannelContract contract = contractsManager.load(ChannelContract.class, address);
        BlockchainChannel channel = BlockchainChannel.fromExistingContract(contractsManager.channelManager(), contract);
        return new IncomingChannelState(channel);
    }
}
