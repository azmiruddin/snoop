package com.mediator.common;



import java.math.BigDecimal;

public class ObjectFileSignedTransaction {

	private String transferId;

	private String channelAddress;
	private BigDecimal value;
	private boolean locked;
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public String getChannelAddress() {
		return channelAddress;
	}
	public void setChannelAddress(String channelAddress) {
		this.channelAddress = channelAddress;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public boolean isLocked() {
		return locked;
	}
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	

}
