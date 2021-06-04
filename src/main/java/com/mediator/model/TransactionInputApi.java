package com.mediator.model;

import java.math.BigInteger;

public class TransactionInputApi {
	String addressFrom;
	String addressTo;
	String addressAudit;
	long valueTrx;
	BigInteger timeout;
	String addressFromPk;
	String tokenAddress;
	
	long depositMinimum;
	String channelKey;
	
	
	
	
	
	
	
	public long getDepositMinimum() {
		return depositMinimum;
	}
	public void setDepositMinimum(long depositMinimum) {
		this.depositMinimum = depositMinimum;
	}
	public String getChannelKey() {
		return channelKey;
	}
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	public String getAddressAudit() {
		return addressAudit;
	}
	public void setAddressAudit(String addressAudit) {
		this.addressAudit = addressAudit;
	}
	public String getTokenAddress() {
		return tokenAddress;
	}
	public void setTokenAddress(String tokenAddress) {
		this.tokenAddress = tokenAddress;
	}
	public String getAddressFromPk() {
		return addressFromPk;
	}
	public void setAddressFromPk(String addressFromPk) {
		this.addressFromPk = addressFromPk;
	}
	
	public String getAddressFrom() {
		return addressFrom;
	}
	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}
	public String getAddressTo() {
		return addressTo;
	}
	public void setAddressTo(String addressTo) {
		this.addressTo = addressTo;
	}
	public long getValueTrx() {
		return valueTrx;
	}
	public void setValueTrx(long valueTrx) {
		this.valueTrx = valueTrx;
	}
	public BigInteger getTimeout() {
		return timeout;
	}
	public void setTimeout(BigInteger timeout) {
		this.timeout = timeout;
	}
	
	
	
	
}
