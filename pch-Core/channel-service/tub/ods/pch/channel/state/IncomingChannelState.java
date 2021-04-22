package tub.ods.pch.channel.state;

import java.security.SignatureException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;

import tub.ods.pch.channel.util.ChannelProperties;
import tub.ods.common.data.model.SignedTransfer;
import tub.ods.common.data.model.SignedTransferUnlock;
import tub.ods.pch.channel.BlockchainChannel;
import tub.ods.pch.channel.SignedChannelState;

public class IncomingChannelState {
    private static final Logger log = LoggerFactory.getLogger(IncomingChannelState.class);
    
    private BlockchainChannel channel;
    private SignedChannelState ownState;
    private SignedChannelState senderState;
    //TODO implement merkle tree
    private Map<Uint256, SignedTransfer> transfers = new HashMap<>(); 
    private Map<Uint256, SignedTransfer> lockedTransfers = new HashMap<>(); 

    public IncomingChannelState(BlockchainChannel channel) {
        this.channel = channel;
        ownState = new SignedChannelState(channel.getChannelAddress());
    }

    public BlockchainChannel getChannel() {
        return channel;
    }

    public SignedChannelState getOwnState() {
        computeRoots();
        return ownState;
    }

    private synchronized void computeRoots() {
        //TODO Merkle 
    }
    
    public SignedChannelState getSenderState() {
        return senderState;
    }

    public synchronized void updateSenderState(SignedChannelState receiverState) {
        if (this.senderState != null && this.senderState.getNonce() > receiverState.getNonce()) {
            log.debug("Ignoring old state update for channel {}", channel.getChannelAddress());
        }
        this.senderState = receiverState;
    }
    
    public synchronized boolean registerTransfer(SignedTransfer transfer) throws SignatureException {
        Uint256 transferId = transfer.getTransferId();
		if (transfers.containsKey(transferId) || lockedTransfers.containsKey(transferId)) {
		    return false;
		}
		transfer.verifySignature(channel.getClientAddress());
		if (transfer.isLocked()) {
		    lockedTransfers.put(transferId, transfer);
		} else {
		    transfers.put(transferId, transfer);
		    ownState.setCompletedTransfers(ownState.getCompletedTransfers().add(transfer.getValueWei()));
		    ownState.setTransfersRoot(null);
		}
		return true;
    }

    public synchronized boolean registerTransferUnlock(SignedTransferUnlock transferUnlock) {
        ChannelProperties properties = channel.getProperties();
        properties.getAuditor().ifPresent(expectedSigner -> {
            try {
                transferUnlock.verifySignature(expectedSigner);
            } catch (SignatureException e) {
                throw new RuntimeException(e);
            }
        });
        Uint256 transferId = transferUnlock.getTransferId();
        SignedTransfer transfer = lockedTransfers.remove(transferId);
        if (transfer == null) {
            return false;
        }
        transfers.put(transferId, transfer);
        ownState.setCompletedTransfers(ownState.getCompletedTransfers().add(transfer.getValueWei()));
        ownState.setTransfersRoot(null);
        ownState.setLockedTransfersRoot(null);
        return true;
    }

    public Address getChannelAddress() {
        return channel.getChannelAddress();
    }

    public boolean requestClose() {
        //TODO
        throw new UnsupportedOperationException();                       
    }

    public Address getSenderAddress() {
        return channel.getSenderAddress();
    }

    public Address getReceiverAddress() {
        return channel.getReceiverAddress();
    }

    public Map<Uint256, SignedTransfer> getTransfers() {
        return Collections.unmodifiableMap(transfers);
    }

    public Map<Uint256, SignedTransfer> getLockedTransfers() {
        return Collections.unmodifiableMap(lockedTransfers);
    }
}
