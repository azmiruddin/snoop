package tub.ods.pch.channel.util;

import java.math.BigInteger;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;


public class ChannelData {
    public Address address;
    public BigInteger _amount;
    public Uint settleTimeout;
    public long opened;
    public long closeChannel;
    public long settled;
    public Address closingAddress;
    public Address sender;
    public Address receiver;
    public Uint256 balance;

    public StateUpdate sender_update;
    public StateUpdate receiver_update;

//    MerkleContract contract;

    public ChannelData() {

    }

    public class StateUpdate {
        public BigInteger _amount;
    }

/*    public MerkleContract getContract() {
        return contract;
    }
*/
    public Address getAddress() {
        return address;
    }

    public Address getClosingAddress() {
        return closingAddress;
    }

    public Address getSender() {
        return sender;
    }

    public Address getReceiver() {
        return receiver;
    }

    public BigInteger get_amount() {
        return _amount;
    }

    public long getCloseChannel() {
        return closeChannel;
    }
}
