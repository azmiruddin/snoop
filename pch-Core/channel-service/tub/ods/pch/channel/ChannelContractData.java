package tub.ods.pch.channel;

import java.math.BigInteger;
import java.util.List;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Uint;
import org.web3j.abi.datatypes.generated.Uint256;


public class ChannelContractData {
	public Address address;
	public BigInteger transferAmount;
	public Uint settleTimeout;
	public long opened;
	public long closed;
	public long settled;
	public Address closingAddress;
	public Address token;
	public Address sender;
	public Address receiver;
	public Uint256 balance;

	public StateUpdate sender_update;
	public StateUpdate receiver_update;

	public ChannelContractData() {
	}

	public class StateUpdate {
		public BigInteger transferAmount;
	}

	public void convert(List<Type> data) {
		opened = ((Uint) data.get(0)).getValue().longValueExact();
		closed = ((Uint) data.get(1)).getValue().longValueExact();
		settled = ((Uint) data.get(2)).getValue().longValueExact();
		closingAddress = ((Address) data.get(3));
		token = ((Address) data.get(4));
		sender = ((Address) data.get(5));
		receiver = ((Address) data.get(6));
		balance = ((Uint256) data.get(7));
		sender_update = new StateUpdate();
		sender_update.transferAmount = (BigInteger) data.get(8);
		receiver_update = new StateUpdate();
		receiver_update.transferAmount = (BigInteger) data.get(9);
	}
}
