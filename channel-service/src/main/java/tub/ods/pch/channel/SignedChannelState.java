package tub.ods.pch.channel;

import java.beans.Transient;
import java.math.BigInteger;
import java.security.SignatureException;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.ECKeyPair;

import com.google.common.base.Preconditions;
import com.google.protobuf.ByteString;

import papyrus.channel.protocol.ChannelStateMessage;
import tub.ods.pch.channel.util.CryptoUtil;

import tub.ods.pch.channel.util.HashedObject;


public class SignedChannelState implements HashedObject {
	
	private long nonce;
    private Address channelAddress;
    private BigInteger completedTransfers = BigInteger.ZERO;
    private Bytes32 transfersRoot = null;
    private Bytes32 lockedTransfersRoot = null;
    
    protected byte[] signature;

    public byte[] getSignature() {
        return signature;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public SignedChannelState(Address channelAddress) {
        this.channelAddress = channelAddress;
    }
    
    public void sign(ECKeyPair keyPair) {
        byte[] hash = hash();
        signature = CryptoUtil.sign(keyPair, hash);
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
    
    public void verifySignature(Address expectedSigner) throws SignatureException {
        Address signerAddress = getSignerAddress();
        if (!expectedSigner.equals(signerAddress)) {
            throw new SignatureException("Invalid signature address: " + signerAddress + ", expected: " + expectedSigner);
        }
    }
    
    @Transient
    public Address getSignerAddress() throws SignatureException {
        return CryptoUtil.verifySignature(signature, hash());
    }

	@Override
	public byte[] hash() {
		// TODO Auto-generated method stub
		return CryptoUtil.soliditySha3(channelAddress, new Uint256(nonce), completedTransfers);
	}

	public ChannelStateMessage toMessage() {
        Preconditions.checkState(signature != null);
        return ChannelStateMessage.newBuilder()
            .setNonce(nonce)
            .setChannelAddress(channelAddress.toString())
            .setCompletedTransfers(completedTransfers.toString())
            .setSignature(ByteString.copyFrom(signature))
            .build();
	}

}
