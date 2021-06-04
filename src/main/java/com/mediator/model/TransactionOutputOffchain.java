package com.mediator.model;

import common.data.json.model.ObjectFileTransactionOffChain;

public class TransactionOutputOffchain  {
	
	
	ObjectFileTransactionOffChain dataTrx;
	String key;
	String channelKey;
	String statusCode;
	String statusMessage;
	
	
	
	
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public String getChannelKey() {
		return channelKey;
	}
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	public ObjectFileTransactionOffChain getDataTrx() {
		return dataTrx;
	}
	public void setDataTrx(ObjectFileTransactionOffChain dataTrx) {
		this.dataTrx = dataTrx;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	

}
