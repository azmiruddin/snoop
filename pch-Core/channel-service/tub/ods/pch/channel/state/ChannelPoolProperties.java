package papyrus.channel.node.server.channel.outgoing;

import org.web3j.abi.datatypes.Address;
import org.web3j.utils.Convert;

import papyrus.channel.node.ChannelPoolMessage;
import papyrus.channel.node.entity.ChannelProperties;
import papyrus.channel.node.entity.DataObject;
import papyrus.channel.node.server.ethereum.TokenConvert;

public class ChannelPoolProperties extends DataObject {
    private final Address sender;
    private final Address receiver;
    private final int minActiveChannels;
    private final int maxActiveChannels;
    private final boolean shutdown;
    private final OutgoingChannelPolicy policy;
    private final ChannelProperties blockchainProperties;

    public ChannelPoolProperties(ChannelPoolMessage request) {
        this(
            new Address(request.getSenderAddress()),
            new Address(request.getReceiverAddress()),
            request.getMinActiveChannels(), 
            request.getMaxActiveChannels(),
            false, 
            new OutgoingChannelPolicy(Convert.toWei(request.getDeposit(), Convert.Unit.ETHER).toBigIntegerExact(), request.getCloseBlocksCount()), 
            new ChannelProperties(request.getProperties())
        );
    }
    
    public ChannelPoolProperties(OutgoingChannelPoolBean bean) {
        this(
            bean.getSender(),
            bean.getReceiver(),
            bean.getMinActive(), 
            bean.getMaxActive(),
            bean.isShutdown(), 
            new OutgoingChannelPolicy(TokenConvert.toWei(bean.getDeposit()), bean.getCloseBlocksCount()), 
            new ChannelProperties(bean)
        );
    }
    
    public ChannelPoolProperties(
        Address sender,
        Address receiver,
        int minActiveChannels,
        int maxActiveChannels,
        boolean shutdown, OutgoingChannelPolicy policy,
        ChannelProperties blockchainProperties
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.minActiveChannels = minActiveChannels;
        this.maxActiveChannels = maxActiveChannels;
        this.shutdown = shutdown;
        this.policy = policy;
        this.blockchainProperties = blockchainProperties;
    }
    
    public Address getSender() {
        return sender;
    }

    public Address getReceiver() {
        return receiver;
    }

    public int getMinActiveChannels() {
        return minActiveChannels;
    }

    public int getMaxActiveChannels() {
        return maxActiveChannels;
    }

    public OutgoingChannelPolicy getPolicy() {
        return policy;
    }

    public ChannelProperties getBlockchainProperties() {
        return blockchainProperties;
    }

    public boolean isShutdown() {
        return shutdown;
    }
}
