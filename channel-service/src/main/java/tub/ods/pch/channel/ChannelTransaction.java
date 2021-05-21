package tub.ods.pch.channel;

import java.math.BigInteger;

public class ChannelTransaction {
    private final BigInteger channelAddress;
    private final BigInteger amount;

    public ChannelTransaction(BigInteger channelAddress, BigInteger amount) {
        this.channelAddress = channelAddress;
        this.amount = amount;
    }

    public BigInteger getChannelAddress() {
        return channelAddress;
    }

    public BigInteger getAmount() {
        return amount;
    }
}