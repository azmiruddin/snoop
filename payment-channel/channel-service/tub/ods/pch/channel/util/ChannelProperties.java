package tub.ods.pch.channel.util;

import org.web3j.abi.datatypes.Address;

public class ChannelProperties extends DataObject {

	private static final long serialVersionUID = 1L;
	private final Address sender;
    private final Address receiver;

    public Address getSender() {
        return sender;
    }

    public Address getReceiver() {
        return receiver;
    }

    public ChannelProperties(Address sender, Address receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public void setCloseChannel(long closeChannel) {
    }
}