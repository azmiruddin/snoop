package com.mediator.common;



import java.io.Serializable;
import java.math.BigInteger;

public class ObjectFileOtherValues implements Serializable{
	private static final long serialVersionUID = 1L;
	private String coinbaseAddress;
	private String transactionCount;
	private String credentialsAddress;
	private String gasPrice;
	private String gasLimit;
	private String valueTrx;
	private String balanceFrom;
	private String balanceTo;
	
	public String getCoinbaseAddress() {
		return coinbaseAddress;
	}
	public void setCoinbaseAddress(String coinbaseAddress) {
		this.coinbaseAddress = coinbaseAddress;
	}
	public String getTransactionCount() {
		return transactionCount;
	}
	public void setTransactionCount(String transactionCount) {
		this.transactionCount = transactionCount;
	}
	public String getCredentialsAddress() {
		return credentialsAddress;
	}
	public void setCredentialsAddress(String credentialsAddress) {
		this.credentialsAddress = credentialsAddress;
	}
	public String getGasPrice() {
		return gasPrice;
	}
	public void setGasPrice(String gasPrice) {
		this.gasPrice = gasPrice;
	}
	public String getGasLimit() {
		return gasLimit;
	}
	public void setGasLimit(String gasLimit) {
		this.gasLimit = gasLimit;
	}
	public String getValueTrx() {
		return valueTrx;
	}
	public void setValueTrx(String valueTrx) {
		this.valueTrx = valueTrx;
	}
	public String getBalanceFrom() {
		return balanceFrom;
	}
	public void setBalanceFrom(String balanceFrom) {
		this.balanceFrom = balanceFrom;
	}
	public String getBalanceTo() {
		return balanceTo;
	}
	public void setBalanceTo(String balanceTo) {
		this.balanceTo = balanceTo;
	}
	
	
	
	
	
	
	

}
