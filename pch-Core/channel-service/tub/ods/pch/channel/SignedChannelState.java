package tub.ods.pch.channel;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Bytes32;


public class SignedChannelState {
	
	private long nonce;
    private Address channelAddress;
    private BigInteger completedTransfers = BigInteger.ZERO;
    private Bytes32 transfersRoot = null;
    private Bytes32 lockedTransfersRoot = null;

    public SignedChannelState(Address channelAddress) {
        this.channelAddress = channelAddress;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public Address getChannelAddress() {
        return channelAddress;
    }

    public void setChannelAddress(Address channelAddress) {
        this.channelAddress = channelAddress;
    }

    public BigInteger getCompletedTransfers() {
        return completedTransfers;
    }

    public void setCompletedTransfers(BigInteger completedTransfers) {
        this.completedTransfers = completedTransfers;
    }

    public Bytes32 getTransfersRoot() {
        return transfersRoot;
    }

    public void setTransfersRoot(Bytes32 transfersRoot) {
        this.transfersRoot = transfersRoot;
    }

    public Bytes32 getLockedTransfersRoot() {
        return lockedTransfersRoot;
    }

    public void setLockedTransfersRoot(Bytes32 lockedTransfersRoot) {
        this.lockedTransfersRoot = lockedTransfersRoot;
    }

}
