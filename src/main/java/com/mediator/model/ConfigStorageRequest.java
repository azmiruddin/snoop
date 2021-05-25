package com.mediator.model;

public class ConfigStorageRequest {
	
	String hostRedis;
	int portRedis;
	String addressIpfs;
	
	
	public String getHostRedis() {
		return hostRedis;
	}
	public void setHostRedis(String hostRedis) {
		this.hostRedis = hostRedis;
	}
	public int getPortRedis() {
		return portRedis;
	}
	public void setPortRedis(int portRedis) {
		this.portRedis = portRedis;
	}
	public String getAddressIpfs() {
		return addressIpfs;
	}
	public void setAddressIpfs(String addressIpfs) {
		this.addressIpfs = addressIpfs;
	}
	
	

}
