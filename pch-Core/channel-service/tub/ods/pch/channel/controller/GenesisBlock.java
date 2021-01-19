package tub.ods.pch.channel.controller;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tub.ods.pch.channel.util.Hasher;

/**
 * @author Azmiruddin
 * A block is a fundamental element of a blockchain. 
 * A block is a data structure that consists of a block number also known as an index, a timestamp, a previous hash, hash, and data.
 */
public class GenesisBlock {

    private ArrayList<TransactionBlock> transactionBlocks;
    public static HashMap<String,TransactionOutput> allUnspentTransactions;
    public TransactionProcessor transactionProcessor;
    Wallet genesisWallet;
    Transaction genesisTransaction;
    private static final Logger LOGGER = LoggerFactory.getLogger(GenesisBlock.class);

    public GenesisBlock() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        transactionBlocks = new ArrayList<>();
        genesisWallet = new Wallet("Genesis");
        allUnspentTransactions = new HashMap<String,TransactionOutput>();
        transactionProcessor = new TransactionProcessor();
        createGenesisTransaction();
    }

    public TransactionBlock getLastBlock()
    {
        return transactionBlocks.get(transactionBlocks.size()-1);
    }

    public void addNewBlock(TransactionBlock newBlock)
    {
        transactionBlocks.add(newBlock);
    }

    public TransactionBlock feedWallet(Wallet toFeed, int value) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        JSONObject blockData = new JSONObject();
        blockData.put("Data", "Block");
        TransactionBlock tmp = generateNewBlock(blockData);
        LOGGER.info("Feeding wallet " + toFeed.getName() + " with value " + value);
        tmp.addTransaction(transactionProcessor, genesisWallet.createTransaction(toFeed.getPublicKey(), value));
        addNewBlock(tmp);
        return tmp;
    }


    private void createGenesisTransaction() throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        genesisTransaction = new Transaction(genesisWallet.getPublicKey(), genesisWallet.getPublicKey(), 0);

        genesisTransaction.transactionId = "0";
        TransactionOutput output = new TransactionOutput(genesisTransaction.receiver, genesisTransaction.value, genesisTransaction.transactionId);
        allUnspentTransactions.put(output.id, output);
        LOGGER.info("Creating and Mining Genesis block... " +output);
        JSONObject blockData = new JSONObject();
        blockData.put("Data", "Genesis");
        TransactionBlock genesis = generateNewGenesisBlock(blockData);
        genesis.addTransaction(transactionProcessor,genesisTransaction);
        addNewBlock(genesis);
        LOGGER.info("Finish Mining Genesis block... " +output +blockData);
        
    }

    public void removeLastBlock()
    {
        transactionBlocks.remove(transactionBlocks.size()-1);
    }
    public  Boolean validateBlockchain() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        TransactionBlock currentBlock;
        TransactionBlock previousBlock;

        for(int i=1; i < transactionBlocks.size(); i++) {

            currentBlock = transactionBlocks.get(i);
            previousBlock = transactionBlocks.get(i-1);


            if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
            	LOGGER.info("#Current Hashes not equal");
                return false;
            }

            if(!currentBlock.getPreviousBlockHash().equals(calculateBlockHashFromBlock(previousBlock)) ) {
            	LOGGER.info("Chain is not valid! Currentblock: " + i );
                return false;
            }

            if(!previousBlock.getHash().equals(currentBlock.getPreviousBlockHash()) ) {
            	LOGGER.info("#Previous Hashes not equal");
                return false;
            }

        }
        LOGGER.info("Wallet is valid");
        return true;
    }

    public byte[] generateBlockSignature(PrivateKey privateKey, TransactionBlock blockToSign) throws JSONException {
        JSONObject blockData = new JSONObject();
        blockData.put("Sender", blockToSign.transaction.sender);
        blockData.put("Receiver", blockToSign.transaction.receiver);
        blockData.put("Receiver", blockToSign.transaction.value);
        blockData.put("PreviousBlockHash", blockToSign.getPreviousBlockHash());
        blockData.put("Timestamp", blockToSign.getTimestamp());
        return Hasher.createSignature(privateKey, blockData);
    }

    public boolean verifyBlockSignature(PublicKey publicKey, TransactionBlock transactionBlock, byte[] blockSignature) throws JSONException {
        JSONObject blockData = new JSONObject();
        blockData.put("Sender", transactionBlock.transaction.sender);
        blockData.put("Receiver", transactionBlock.transaction.receiver);
        blockData.put("Receiver", transactionBlock.transaction.value);
        blockData.put("PreviousBlockHash", transactionBlock.getPreviousBlockHash());
        blockData.put("Timestamp", transactionBlock.getTimestamp());
        return Hasher.verifySignature(publicKey, blockData, blockSignature);
    }

    public TransactionBlock generateNewBlock(JSONObject dataForNewBlock) throws  NoSuchAlgorithmException {
        int index;
        String hash;
        String timestamp;
        String lastBlockHash;

        if(transactionBlocks.size() == 0)
        {
            index = 0;
            timestamp = new Timestamp(System.currentTimeMillis()).toString();
            lastBlockHash = "";
        }
        else
        {
            TransactionBlock lastBlock = getLastBlock();
            index = lastBlock.getIndex() + 1;
            timestamp = new Timestamp(System.currentTimeMillis()).toString();
            lastBlockHash = lastBlock.getHash();
        }

        hash = calculateBlockHash(lastBlockHash, dataForNewBlock.toString(),timestamp, index);
        return new TransactionBlock(index, hash, lastBlockHash, dataForNewBlock.toString(), timestamp);
    }

    public TransactionBlock generateNewGenesisBlock(JSONObject dataForNewBlock) throws  NoSuchAlgorithmException {
        int index =0;
        String timestamp = "2018-01-01 00:00:00";
        String lastBlockHash ="";



        String hash = calculateBlockHash(lastBlockHash, dataForNewBlock.toString(),timestamp, index);
        return new TransactionBlock(index, hash, lastBlockHash, dataForNewBlock.toString(), timestamp);
    }

    public String calculateBlockHash(String previousHash, String data, String timestamp, int index ) throws NoSuchAlgorithmException {
        String text = previousHash + data + timestamp + index;
        return Hasher.getHashFromString(text);
    }

    public String calculateBlockHashFromBlock(TransactionBlock transactionBlock) throws NoSuchAlgorithmException {
        String text = transactionBlock.getPreviousBlockHash() + transactionBlock.getData() + transactionBlock.getTimestamp() + transactionBlock.getIndex();
        return Hasher.getHashFromString(text);
    }
}
