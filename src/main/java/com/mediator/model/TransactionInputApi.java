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
