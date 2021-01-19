package tub.ods.pch.channel.controller;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;

import tub.ods.pch.channel.util.Hasher;

public class TransactionBlock{

    private int index;
    private String hash;
    private String previousBlockHash;
    private String data;
    private String timestamp;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionBlock.class);

    public Transaction transaction;
    
    public TransactionObject trxObject;

    public TransactionBlock(int _index, String _hash, String _previousBlockHash, String _data, String _timestamp){
        this.index = _index;
        this.hash = _hash;
        this.previousBlockHash = _previousBlockHash;
        this.data = _data;
        this.timestamp = _timestamp;
    }
    
    public void TransactionObject() {
    	
    }


    public Transaction getTransaction()
    {
        return transaction;
    }
    public String calculateHash() throws  NoSuchAlgorithmException {
        String text = previousBlockHash + data + timestamp + index;
        LOGGER.info("MergedText: " + text);
        String hash = Hasher.getHashFromString(text);
        LOGGER.info("Hash: " + hash);
        return hash;
    }

    //Add transactions to this block
    //Operation similiar to Web3J
    //TransactionObject trxObject;
    public boolean addTransaction(TransactionProcessor proc, Transaction tmp) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if(tmp == null) return false;
        if((previousBlockHash != "")) {
            if((proc.processTransaction(tmp) != true)) {
            	LOGGER.info("Transaction failed to process. Discarded.");
                return false;
            }
        }
        transaction = tmp;
//        LOGGER.info("Transaction Successfully added to Block: transaction={}, from={}, to={}, value={}", tmp.transactionId, tmp.sender, tmp.receiver, tmp.value);
        return true;
    }


    public String getHash() {
        return hash;
    }

    public int getIndex() {
        return index;
    }

    public String getData() {
        return data;
    }

    public String getPreviousBlockHash() {
        return previousBlockHash;
    }

    public String getTimestamp() {
        return timestamp;
    }
}