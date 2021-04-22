package tub.ods.pch.channel.state;

import java.math.BigInteger;

import tub.ods.pch.channel.util.DataObject;

public class OutgoingChannelPolicy extends DataObject {
    public static final OutgoingChannelPolicy NONE = new OutgoingChannelPolicy(BigInteger.ZERO, 0);
    
    private final BigInteger deposit;
    private final long closeBlocksCount;

    public OutgoingChannelPolicy(BigInteger deposit, long closeBlocksCount) {
        this.deposit = deposit;
        this.closeBlocksCount = closeBlocksCount;
    }

    public BigInteger getDeposit() {
        return deposit;
    }

    public long getCloseBlocksCount() {
        return closeBlocksCount;
    }

    public boolean isNone() {
        return deposit.signum() == 0;
    }
}
