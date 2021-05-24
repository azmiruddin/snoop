package tub.ods.pch.channel;


import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.datastax.driver.mapping.annotations.Transient;

import papyrus.channel.node.MessageLock;
import papyrus.channel.node.TransferMessage;
import papyrus.channel.node.server.ethereum.CryptoUtil;
import papyrus.channel.node.server.ethereum.SignedObject;
import papyrus.channel.node.server.ethereum.TokenConvert;
import papyrus.channel.node.server.persistence.Keyspaces;
import tub.ods.pch.channel.util.Utility;

@Table(keyspace = Keyspaces.CHANNEL, name = "transfer")
public class SignedTransfer extends SignedObject {
    @Column(name = "transfer_id")
    @ClusteringColumn
    private Uint256 transferId;
    @Column(name = "channel_id")
    @PartitionKey
    private Address channelAddress;
    private BigDecimal value;
    private boolean locked;

    public SignedTransfer() {
    }

    public SignedTransfer(TransferMessage transferMessage) {
        this(
            transferMessage.getTransferId(), 
            transferMessage.getChannelAddress(), 
            transferMessage.getValue(), 
            transferMessage.getLock() == MessageLock.AUDITOR
        );
        signature = Numeric.hexStringToByteArray(transferMessage.getSignature());
    }

    public SignedTransfer(String transferId, String channelAddress, String value, boolean locked) {
        this(
            new Uint256(Numeric.toBigInt(transferId)), 
            new Address(channelAddress),
            new BigDecimal(value), 
            locked
        );
    }

    public SignedTransfer(Uint256 transferId, Address channelAddress, BigDecimal value, boolean locked) {
        this.transferId = transferId;
        this.channelAddress = channelAddress;
        this.value = value;
        this.locked = locked;
    }

    public Uint256 getTransferId() {
        return transferId;
    }

    public void setTransferId(Uint256 transferId) {
        this.transferId = transferId;
    }

    public Address getChannelAddress() {
        return channelAddress;
    }

    public void setChannelAddress(Address channelAddress) {
        this.channelAddress = channelAddress;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Transient
    public BigInteger getValueWei() {
        return TokenConvert.toWei(value);
    }

    public void setValueWei(BigInteger value) {
        this.value = TokenConvert.fromWei(value);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

// TODO 
//  (Utility.toHexStringNoPrefix(channelAddress.getValue())) 
//	return null??
//    return value.toString();
    public TransferMessage toMessage() {
        return TransferMessage.newBuilder()
            .setChannelAddress(Utility.toHexStringNoPrefix(channelAddress.getValue())) 
            .setSignature(Numeric.toHexStringNoPrefix(signature))
            .setTransferId(Numeric.toHexStringNoPrefix(transferId.getValue()))
            .setValue(value.toString())
            .setLock(locked ? MessageLock.AUDITOR : MessageLock.NONE)
            .build();
    }

    @Override
    public byte[] hash() {
        return CryptoUtil.soliditySha3(transferId, channelAddress, value, locked ? BigInteger.ONE : BigInteger.ZERO);
    }
}

