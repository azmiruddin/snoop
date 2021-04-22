package tub.ods.pch.channel.state;

import java.math.BigInteger;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.generated.Uint256;

import com.google.common.base.Preconditions;

import tub.ods.pch.channel.model.ChannelContract;
import tub.ods.pch.channel.util.ChannelProperties;
import tub.ods.common.data.model.OutgoingChannelBean;
import tub.ods.common.data.model.SignedTransfer;
import tub.ods.common.data.model.SignedTransferUnlock;
import tub.ods.pch.channel.BlockchainChannel;
import tub.ods.pch.channel.SignedChannelState;
import tub.ods.pch.channel.node.TokenConvert;
import tub.ods.pch.channel.node.TokenService;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class OutgoingChannelState {
	private static final Logger log = LoggerFactory.getLogger(OutgoingChannelState.class);

	private Status status;
	private BlockchainChannel channel;
	private BigInteger transferedAmount = BigInteger.ZERO;
	private Map<Uint256, SignedTransfer> transfers = new HashMap<>();
	private Map<Uint256, SignedTransferUnlock> unlocks = new HashMap<>();
	private long currentNonce;
	private long syncedNonce;
	private volatile boolean needClose;

	private StateTransition transition;

	public OutgoingChannelState(Address senderAddress, Address clientAddress, Address receiverAddress,
			ChannelProperties properties) {
		channel = new BlockchainChannel(senderAddress, clientAddress, receiverAddress);
		channel.setProperties(properties);
		setStatus(Status.NEW);
	}

	public OutgoingChannelState(BlockchainChannel channel) {
		this.channel = channel;
		status = channel.getSettled() > 0 ? Status.SETTLED
				: channel.getClosed() > 0 ? Status.CLOSED
						: channel.getCloseRequested() > 0 ? Status.CLOSE_REQUESTED
								: channel.getBalance().signum() > 0 ? Status.OPENED : Status.CREATED;
	}

	public void verifyTransfer(SignedTransfer transfer) {
		try {
			transfer.verifySignature(getChannel().getClientAddress());
		} catch (SignatureException e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isCloseRequested() {
		return needClose || channel != null && channel.getCloseRequested() > 0
				|| getPendingStatus() == Status.CLOSE_REQUESTED;
	}

	public boolean isActive() {
		return !needClose && status == Status.ACTIVE && getPendingStatus() == Status.ACTIVE;
	}

	public boolean isSettled() {
		return channel.getSettled() > 0;
	}

	BlockchainChannel getChannel() {
		return channel;
	}

	public void updateBlockchainState(OutgoingChannelState state) {
		this.channel = state.getChannel();
		if (status != state.getStatus() && (transition == null || transition.nextStatus != state.getStatus())) {
			log.warn("Channel {} status changed in blockchain from {} to {} ", getAddressSafe(), status,
					state.getStatus());
			status = state.getStatus();
		}
	}

	public void updatePersistentState(OutgoingChannelBean bean, Iterable<SignedTransfer> transfers,
			Iterable<SignedTransferUnlock> unlocks) {
		currentNonce = bean.getCurrentNonce();
		syncedNonce = bean.getSyncedNonce();
		transferedAmount = TokenConvert.toWei(bean.getTransferred());
		status = bean.getStatus();

		for (SignedTransferUnlock unlock : unlocks) {
			verifyUnlock(unlock);
			this.unlocks.put(unlock.getTransferId(), unlock);
		}
		BigInteger transferred = BigInteger.ZERO;
		for (SignedTransfer transfer : transfers) {
			verifyTransfer(transfer);
			this.transfers.put(transfer.getTransferId(), transfer);
			if (!transfer.isLocked() || this.unlocks.containsKey(transfer.getTransferId())) {
				transferred = transferred.add(transfer.getValueWei());
			}
		}
		transferedAmount = transferred;
	}

	public OutgoingChannelBean getPersistentState() {
		OutgoingChannelBean bean = new OutgoingChannelBean();
		bean.setCurrentNonce(currentNonce);
		bean.setSyncedNonce(syncedNonce);
		bean.setAddress(getChannelAddress());
		bean.setStatus(status);
		bean.setTransferred(TokenConvert.fromWei(transferedAmount));
		return bean;
	}

	public Address getChannelAddress() {
		return channel.getChannelAddress();
	}

	public void startDeploying(CompletableFuture<ChannelContract> deployingContract) {
		checkStatus(Status.NEW);
		checkNotNull(deployingContract);
		startTransition(Status.CREATED, deployingContract);
	}

	private void checkStatus(Status... expected) {
		checkState(status.isAnyOf(expected), "Invalid status: %s, expected: %s", status,
				expected.length == 1 ? expected[0] : Arrays.asList(expected));
	}

	public void setActive() {
		checkStatus(Status.OPENED);
		setStatus(Status.ACTIVE);
	}

	private void setStatus(Status newStatus) {
		status = newStatus;
	}

	public Status getStatus() {
		return status;
	}

	public Status getPendingStatus() {
		StateTransition tr = this.transition;
		return tr != null ? tr.nextStatus : status;
	}

	public boolean checkTransitionInProgress() {
		if (transition != null)
			transition.checkAndApply();
		return transition != null;
	}

	public BigInteger getTransferedAmount() {
		return transferedAmount;
	}

	public long getOpenBlock() {
		return channel != null ? channel.getCreated() : 0;
	}

	public BigInteger getTransferredAmount() {
		return channel != null ? channel.getCompletedTransfers() : BigInteger.ZERO;
	}

	String getAddressSafe() {
		if (channel.getChannelAddress() != null)
			return channel.getChannelAddress().toString();
		return status.toString();
	}

	public Map<Uint256, SignedTransfer> getTransfers() {
		return Collections.unmodifiableMap(transfers);
	}

	public Map<Uint256, SignedTransferUnlock> getUnlocks() {
		return Collections.unmodifiableMap(unlocks);
	}

	public synchronized boolean registerTransfer(SignedTransfer transfer) throws SignatureException {
		Preconditions.checkArgument(transfer.getChannelAddress().equals(getChannelAddress()));
		Preconditions.checkArgument(transfer.getValue().signum() > 0);

		verifyTransfer(transfer);

		Uint256 transferId = transfer.getTransferId();
		if (unlocks.containsKey(transferId) || transfers.containsKey(transferId)) {
			return false;
		}
		transfers.put(transferId, transfer);
		if (!transfer.isLocked()) {
			transferedAmount = transferedAmount.add(transfer.getValueWei());
		}
		stateChanged();
		return true;
	}

	public synchronized boolean unlockTransfer(SignedTransferUnlock transfer) {
		verifyUnlock(transfer);

		Uint256 transferId = transfer.getTransferId();
		SignedTransfer existing = transfers.get(transferId);
		if (existing == null) {
			log.warn("No transfer to unlock found. Channel: {}, transfer: {}", transfer.getChannelAddress(),
					transfer.getTransferId());
			return false;
		}
		if (unlocks.put(transferId, transfer) == null) {
			transferedAmount = transferedAmount.add(existing.getValueWei());
		}
		stateChanged();
		return true;
	}

	private void verifyUnlock(SignedTransferUnlock transfer) {
		ChannelProperties properties = channel.getProperties();
		properties.getAuditor().ifPresent(expectedSigner -> {
			try {
				transfer.verifySignature(expectedSigner);
			} catch (SignatureException e) {
				throw new RuntimeException(e);
			}
		});
	}

	private void stateChanged() {
		if (currentNonce == syncedNonce) {
			currentNonce++;
		}
	}

	public synchronized boolean isNeedsSync() {
		return currentNonce > syncedNonce;
	}

	public synchronized SignedChannelState createState() {
		SignedChannelState state = new SignedChannelState(channel.getChannelAddress());
		state.setNonce(currentNonce);
		state.setCompletedTransfers(transferedAmount);
		return state;
	}

	public synchronized void syncCompleted(SignedChannelState state) {
		syncedNonce = state.getNonce();
	}

	public BigInteger getBalance() {
		return channel.getBalance();
	}

	public void approveDeposit(TokenService token, BigInteger value) {
		checkStatus(Status.CREATED);
		startTransition(Status.DEPOSIT_APPROVED, token.approve(channel.getChannelAddress(), value));
	}

	public void resetDepositApproval() {
		checkStatus(Status.DEPOSIT_APPROVED);
		status = Status.CREATED;
	}

	public void deposit(BigInteger value) {
		checkStatus(Status.DEPOSIT_APPROVED);
		startTransition(Status.OPENED, channel.getContract().deposit(new Uint256(value)),
				tr -> channel.setBalance(channel.getBalance().add(value)));
	}

	public boolean setNeedClose() {
		if (!status.isAnyOf(Status.SETTLED, Status.DISPUTING)) {
			this.needClose = true;
			return true;
		}
		return false;
	}

	public boolean isNeedClose() {
		return needClose;
	}

	void setNeedClose(boolean needClose) {
		this.needClose = needClose;
	}

	public void makeDisposable() {
		Preconditions.checkState(status.isAnyOf(Status.NEW, Status.SETTLED));
		this.status = Status.DISPOSABLE;
	}

	public void doRequestClose() {
		checkStatus(Status.OPENED, Status.CREATED, Status.ACTIVE);
		startTransition(Status.CLOSE_REQUESTED, channel.getContract().request_close(),
				tr -> channel.setCloseRequested(tr.getBlockNumber().longValueExact()));
	}

	public void closeIfPossible(long currentBlockNumber) {
		Preconditions.checkState(channel.getCloseRequested() > 0);
		checkStatus(Status.CLOSE_REQUESTED);
		long blocksLeft = channel.getCloseRequested() + channel.getProperties().getCloseTimeout() - currentBlockNumber;
		if (blocksLeft <= 0) {
			startTransition(Status.CLOSED,
					channel.getContract().close(new Uint256(currentNonce), new Uint256(transferedAmount),
							new DynamicBytes(new byte[0])),
					tr -> channel.setClosed(tr.getBlockNumber().longValueExact()));
		} else {
			log.debug("Waiting close timeout for channel {} blocks left: {}", getAddressSafe(), blocksLeft);
		}
	}

	public void settleIfPossible(long currentBlockNumber) {
		Preconditions.checkState(channel.getClosed() > 0);
		Preconditions.checkState(channel.getSettled() == 0);
		checkStatus(Status.CLOSED);
		long blocksLeft = channel.getClosed() + channel.getProperties().getSettleTimeout() - currentBlockNumber;
		if (blocksLeft <= 0) {
			startTransition(Status.SETTLED, channel.getContract().settle(), tr -> {
				long blockNumber = tr.getBlockNumber().longValueExact();
				channel.setSettled(blockNumber);
				log.debug("Channel {} settled. Block number: {}. Completed transfers: {}", getAddressSafe(),
						blockNumber, channel.getCompletedTransfers());
			});
		} else {
			log.debug("Waiting settle timeout for channel {} blocks left: {}", getAddressSafe(), blocksLeft);
		}
	}

	public enum Status {
		/** Fresh new object */
		NEW,
		/** Created in blockchain */
		CREATED,
		/** Deposit approved */
		DEPOSIT_APPROVED,
		/** Active (deposit added) */
		OPENED,
		/** Counterparty notified */
		ACTIVE,
		/** Close requested in blockchain. */
		CLOSE_REQUESTED,
		/** Closed in blockchain. */
		CLOSED,
		/** Settled */
		SETTLED,
		/** Dispute process initiated */
		DISPUTING,
		/** All operations completed, may forget about channel */
		DISPOSABLE,;

		public boolean isAnyOf(Status... statuses) {
			for (Status status : statuses)
				if (status == this)
					return true;
			return false;
		}
	}

	private abstract class StateTransition {
		Status nextStatus;

		public StateTransition(Status nextStatus) {
			this.nextStatus = nextStatus;
		}

		abstract void checkAndApply();
	}

	private <T> void startTransition(Status nextStatus, Future<T> task) {
		startTransition(nextStatus, task, t -> {
		});
	}

	private <T> void startTransition(Status nextStatus, Future<T> task, Consumer<T> applyResult) {
		Status prevStatus = status;
		log.warn("Channel {} starting transition {}->{}", getAddressSafe(), prevStatus, nextStatus);
		Preconditions.checkState(transition == null);
		transition = new StateTransition(nextStatus) {
			@Override
			void checkAndApply() {
				if (task.isDone()) {
					transition = null;
					try {
						applyResult.accept(task.get());
						status = nextStatus;
						log.warn("Channel {} transition {}->{} SUCCESS", getAddressSafe(), prevStatus, nextStatus);
					} catch (Exception e) {
						log.warn("Channel {} transition {}->{} FAILED", getAddressSafe(), prevStatus, nextStatus, e);
					}
				}
			}
		};
	}

	public BigInteger getValue() {
		// TODO Auto-generated method stub
		OutgoingChannelBean bean = new OutgoingChannelBean();
		Iterable<SignedTransferUnlock> unlocks = new Iterable<SignedTransferUnlock>() {
			@Override
			public Iterator<SignedTransferUnlock> iterator() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		currentNonce = bean.getCurrentNonce();
		syncedNonce = bean.getSyncedNonce();
		transferedAmount = TokenConvert.toWei(bean.getTransferred());
		status = bean.getStatus();

		for (SignedTransferUnlock unlock : unlocks) {
			verifyUnlock(unlock);
			this.unlocks.put(unlock.getTransferId(), unlock);
		}

		Iterable<SignedTransfer> transfers = new Iterable<SignedTransfer>() {
			@Override
			public Iterator<SignedTransfer> iterator() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		BigInteger transferred = BigInteger.ZERO;
		for (SignedTransfer transfer : transfers) {
			verifyTransfer(transfer);
			this.transfers.put(transfer.getTransferId(), transfer);
			if (!transfer.isLocked() || this.unlocks.containsKey(transfer.getTransferId())) {
				transferred = transferred.add(transfer.getValueWei());
			}
		}
		transferedAmount = transferred;
		return transferred;
	}
}
