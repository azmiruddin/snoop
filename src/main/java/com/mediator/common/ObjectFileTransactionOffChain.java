package com.mediator.common;



import java.io.Serializable;

public class ObjectFileTransactionOffChain implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	String addressFrom;
	String addressTo;
	String addressAudit;
	
	
	String balanceFrom;
	String balanceTo;
	String amountTransaction;
	
	String tokenAddress;
	String channelAddress;
	
	
	String trxHashTransfer;
	String blockNumberTransfer;
	
	String trxHashApprove;
	String blockNumberApprove;
	
	String state;
	String status;
	
	String settleTimeout;
	String revealTimeout;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getChannelAddress() {
		return channelAddress;
	}
	public void setChannelAddress(String channelAddress) {
		this.channelAddress = channelAddress;
	}
	public String getTrxHashTransfer() {
		return trxHashTransfer;
	}
	public void setTrxHashTransfer(String trxHashTransfer) {
		this.trxHashTransfer = trxHashTransfer;
	}
	public String getBlockNumberTransfer() {
		return blockNumberTransfer;
	}
	public void setBlockNumberTransfer(String blockNumberTransfer) {
		this.blockNumberTransfer = blockNumberTransfer;
	}
	public String getTrxHashApprove() {
		return trxHashApprove;
	}
	public void setTrxHashApprove(String trxHashApprove) {
		this.trxHashApprove = trxHashApprove;
	}
	public String getBlockNumberApprove() {
		return blockNumberApprove;
	}
	public void setBlockNumberApprove(String blockNumberApprove) {
		this.blockNumberApprove = blockNumberApprove;
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
	public String getAmountTransaction() {
		return amountTransaction;
	}
	public void setAmountTransaction(String amountTransaction) {
		this.amountTransaction = amountTransaction;
	}
	public String getSettleTimeout() {
		return settleTimeout;
	}
	public void setSettleTimeout(String settleTimeout) {
		this.settleTimeout = settleTimeout;
	}
	public String getRevealTimeout() {
		return revealTimeout;
	}
	public void setRevealTimeout(String revealTimeout) {
		this.revealTimeout = revealTimeout;
	}
	
	
	
	
	
	
	
	
	
}
