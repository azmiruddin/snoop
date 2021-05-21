package tub.ods.pch.channel.state;

import java.math.BigDecimal;

import org.web3j.abi.datatypes.Address;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import papyrus.channel.node.server.persistence.Keyspaces;

@Table(keyspace = Keyspaces.OUTGOING, name = "channel")
public class OutgoingChannelBean {

    @PartitionKey
    private Address address;

    private OutgoingChannelState.Status status;
    
    private BigDecimal transferred;

    @Column(name = "current_nonce")
    private long currentNonce;

    @Column(name = "synced_nonce")
    private long syncedNonce;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public OutgoingChannelState.Status getStatus() {
        return status;
    }

    public void setStatus(OutgoingChannelState.Status status) {
        this.status = status;
    }

    public BigDecimal getTransferred() {
        return transferred;
    }

    public void setTransferred(BigDecimal transferred) {
        this.transferred = transferred;
    }

    public long getCurrentNonce() {
        return currentNonce;
    }

    public void setCurrentNonce(long currentNonce) {
        this.currentNonce = currentNonce;
    }

    public long getSyncedNonce() {
        return syncedNonce;
    }

    public void setSyncedNonce(long syncedNonce) {
        this.syncedNonce = syncedNonce;
    }
}
