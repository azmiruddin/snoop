package tub.ods.pch.channel;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import com.google.common.base.Preconditions;

import tub.ods.pch.channel.util.ChannelProperties;
import tub.ods.pch.contract.model.MerkleContract;
import tub.ods.pch.channel.util.DataObject;


@SuppressWarnings("serial")
public class BlockchainChannel extends DataObject {
    private static final int TIMEOUT_MS = 10000;
    private final Address senderAddress;
    private final Address clientAddress;
    private final Address receiverAddress;
    private BigInteger balance = BigInteger.ZERO;
    private ChannelProperties properties;
    private Address channelAddress;
    private List<byte[]> closingAddress;
    private long created;
    private long closed;
    private long closeRequested;
    private long settled;
    private long nonce;
    private BigInteger completedTransfers = BigInteger.ZERO;

    MerkleContract contract;

/*
    public BlockchainChannel(Address senderAddress, Address clientAddress, Address receiverAddress) {
        Preconditions.checkNotNull(senderAddress);
        Preconditions.checkNotNull(clientAddress);
        Preconditions.checkNotNull(receiverAddress);

        this.senderAddress = senderAddress;
        this.clientAddress = clientAddress;
        this.receiverAddress = receiverAddress;
    }
*/
    
	private BlockchainChannel(Address managerAddress, Address clientAddress, MerkleContract contract)
			throws ExecutionException, InterruptedException, TimeoutException {
        this.clientAddress = clientAddress;
        this.channelAddress = new Address(contract.getContractAddress());
        long CloseChannel = getLong(contract.CloseChannel(BigInteger.TEN, closingAddress));
        Preconditions.checkState(CloseChannel >= 0);
        Address sender = null;
        Address receiver = null;

        ChannelProperties properties = new ChannelProperties(sender, receiver);

        properties.setCloseChannel(CloseChannel);

        this.properties = properties;

        senderAddress = getAddress(contract.SenderAddress());
        Preconditions.checkState(senderAddress != null);


        receiverAddress = getAddress(contract.ReceiverAddress());
        Preconditions.checkState(receiverAddress != null);
        balance = (BigInteger) contract.SeeBalance().get(TIMEOUT_MS);
        Preconditions.checkState(balance.signum() >= 0);

        completedTransfers = (BigInteger) contract.roots().decodeFunctionResponse(completedTransfers.toString());
        Preconditions.checkState(completedTransfers.signum() >= 0);

        this.contract = contract;
    }
    private long getLong(Future<Uint256> future) throws InterruptedException, ExecutionException, TimeoutException {
        return getResult(future).getValue().longValueExact();
    }

    private Uint256 getResult(Future<Uint256> future) throws InterruptedException, ExecutionException, TimeoutException {
        return future.get(TIMEOUT_MS, TimeUnit.MILLISECONDS);
    }

    private Address getAddress(Future<Address> address) throws InterruptedException, ExecutionException, TimeoutException {
        Address a = address.get(TIMEOUT_MS, TimeUnit.MILLISECONDS);
        return a != null && !a.getValue().equals(BigInteger.ZERO) ? a : null;
    }

    public Address getSenderAddress() {
        return senderAddress;
    }

    public Address getReceiverAddress() {
        return receiverAddress;
    }

    public Address getClientAddress() {
        return clientAddress;
    }

    public Address getChannelAddress() {
        return channelAddress;
    }

    public void setChannelAddress(Address channelAddress) {
        this.channelAddress = channelAddress;
    }

    public ChannelProperties getProperties() {
        return properties;
    }

    public void setProperties(ChannelProperties properties) {
        this.properties = properties;
    }

    public long getCreated() {
        return created;
    }

    public long getCloseRequested() {
        return closeRequested;
    }

    public void setCloseRequested(long closeRequested) {
        this.closeRequested = closeRequested;
    }

    public long getClosed() {
        return closed;
    }

    public void setClosed(long closed) {
        this.closed = closed;
    }

    public long getSettled() {
        return settled;
    }

    public void setSettled(long settled) {
        this.settled = settled;
    }

    public MerkleContract getContract() {
        return contract;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public Address getClosingAddress() {
        return (Address) closingAddress;
    }

    @SuppressWarnings("unchecked")
	public void setClosingAddress(Address closingAddress) {
        this.closingAddress = (List<byte[]>) closingAddress;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getNonce() {
        return nonce;
    }

    public void setNonce(long nonce) {
        this.nonce = nonce;
    }

    public BigInteger getCompletedTransfers() {
        return completedTransfers;
    }

    public void setCompletedTransfers(BigInteger completedTransfers) {
        this.completedTransfers = completedTransfers;
    }

    public void linkNewContract(MerkleContract contract) {
        Preconditions.checkArgument(contract.getTransactionReceipt().isPresent());

        this.contract = contract;
        this.channelAddress = new Address(contract.getContractAddress());
        this.created = contract.getTransactionReceipt().get().getBlockNumber().longValueExact();
    }

}
