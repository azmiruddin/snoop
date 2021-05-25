package com.mediator.controller;

import java.math.BigDecimal;

import org.web3j.abi.datatypes.Address;

public class EthKeyProperties {
    
    private String keyLocation;
    private String keyPassword;
    /**
     * Address for client transfers. If not specified main address is used.
     */
    private Address clientAddress;
    
    /*
     * !!! NEXT CONFIGS ARE FOR TESTS ONLY !!!
     */
    
    private String clientPrivateKey;
    private String privateKey;
    //automatically add some ethers to address
    private BigDecimal autoRefill = BigDecimal.ZERO;
    
    public String getKeyLocation() {
        return keyLocation;
    }

    public void setKeyLocation(String keyLocation) {
        this.keyLocation = keyLocation;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public BigDecimal getAutoRefill() {
        return autoRefill;
    }

    public void setAutoRefill(BigDecimal autoRefill) {
        this.autoRefill = autoRefill;
    }

    public Address getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(Address clientAddress) {
        this.clientAddress = clientAddress;
    }

    public String getClientPrivateKey() {
        return clientPrivateKey;
    }

    public void setClientPrivateKey(String clientPrivateKey) {
        this.clientPrivateKey = clientPrivateKey;
    }
}