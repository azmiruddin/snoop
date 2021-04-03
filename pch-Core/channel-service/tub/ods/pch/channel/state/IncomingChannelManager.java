package tub.ods.pch.channel.state;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Address;

import tub.ods.pch.channel.model.ChannelManagerContract;
import tub.ods.pch.channel.node.ContractsManager;

public class IncomingChannelManager {
    private static final Logger log = LoggerFactory.getLogger(IncomingChannelManager.class);
    private final Address receiverAddress;

    private Map<Address, Map<Address, IncomingChannelState>> channelsBySender = new ConcurrentHashMap<>();

    private final ContractsManager contractsManager;
    private final ChannelManagerContract channelManagerContract;

    public IncomingChannelManager(ContractsManager contractsManager) {
        this.receiverAddress = contractsManager.getAddress();
        this.contractsManager = contractsManager;
        this.channelManagerContract = contractsManager.channelManager();
    }

    public Collection<IncomingChannelState> getChannels(Address address) {
        return channelsBySender.getOrDefault(address, Collections.emptyMap()).values();
    }
    
    public IncomingChannelState putChannel(IncomingChannelState state) {
        return channelsBySender.computeIfAbsent(state.getSenderAddress(), address -> new ConcurrentHashMap<>())
            .putIfAbsent(state.getChannelAddress(), state);
    }
}
