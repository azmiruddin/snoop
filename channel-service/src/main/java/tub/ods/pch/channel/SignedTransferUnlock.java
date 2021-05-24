package tub.ods.pch.channel;


import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;

import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import papyrus.channel.node.UnlockTransferMessage;
import papyrus.channel.node.server.ethereum.CryptoUtil;
import papyrus.channel.node.server.ethereum.SignedObject;
import papyrus.channel.node.server.persistence.Keyspaces;
import tub.ods.pch.channel.util.Utility;

@Table(keyspace = Keyspaces.CHANNEL, name = "unlock")
public class SignedTransferUnlock extends SignedObject {
    @Column(name = "transfer_id")
    @ClusteringColumn
    private Uint256 transferId;

    @Column(name = "channel_id")
    @PartitionKey
    private Address channelAddress;

    public SignedTransferUnlock() {
    }

    public SignedTransferUnlock(UnlockTransferMessage unlockTransferMessage) {
        this(unlockTransferMessage.getTransferId(), unlockTransferMessage.getChannelAddress());
        signature = Numeric.hexStringToByteArray(unlockTransferMessage.getSignature());
    }

    public SignedTransferUnlock(String transferId, String channelAddress) {
        this(new Uint256(Numeric.toBigInt(transferId)), new Address(channelAddress));
    }

    public SignedTransferUnlock(Uint256 transferId, Address channelAddress) {
        this.transferId = transferId;
        this.channelAddress = channelAddress;
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

    public UnlockTransferMessage toMessage() {
        return UnlockTransferMessage.newBuilder()
            .setChannelAddress(Utility.toHexStringNoPrefix(channelAddress.getValue()))
            .setSignature(Numeric.toHexStringNoPrefix(signature))
            .setTransferId(Numeric.toHexStringNoPrefix(transferId.getValue()))
            .build();
    }

    @Override
    public byte[] hash() {
        return CryptoUtil.soliditySha3(transferId, channelAddress);
    }
}
