package tub.ods.pch.channel;

import org.web3j.abi.datatypes.Address;
import org.web3j.utils.Convert;
import tub.ods.pch.channel.util.ChannelProperties;

public class ChannelPoolProperties {
    private final Address sender;
    private final Address receiver;
    private final int minActiveChannels;
    private final int maxActiveChannels;
    private boolean shutdown = false;
    private final ChannelProperties blockchainProperties;
    

    public ChannelPoolProperties(
            Address sender,
            Address receiver,
            int minActiveChannels,
            int maxActiveChannels,
            ChannelProperties blockchainProperties
    ) {
        this.sender = sender;
        this.receiver = receiver;
        this.minActiveChannels = minActiveChannels;
        this.maxActiveChannels = maxActiveChannels;
        this.shutdown = shutdown;
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
    

    public ChannelProperties getBlockchainProperties() {
        return blockchainProperties;
    }

    public boolean isShutdown() {
        return shutdown;
    }
}
