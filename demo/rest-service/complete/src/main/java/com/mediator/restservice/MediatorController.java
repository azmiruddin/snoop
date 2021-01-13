package com.mediator.restservice;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;

import com.mediator.model.DataStoreRequest;
import com.mediator.model.DataStoreResponse;
import com.mediator.model.ResponseInit;
import com.mediator.model.TransactionInputApi;
import com.mediator.model.TransactionOutputApi;

import common.data.json.custome.JSONDataCustome;
import common.data.json.model.ObjectFileOtherValues;
import common.data.json.model.ObjectFileTransaction;
import common.data.json.serialized.SerializeUtils;
import src.java.storage.DataStorage;
import tub.ods.pch.payment.controller.TransactionController;

@RestController
@RequestMapping("/mediatorApi")
public class MediatorController {
	
	 @Autowired
	    Web3j web3j;
	//  Credentials credentials;
	  DataStorage pushData;

	
	public static final BigInteger GAS_PRICE = BigInteger.valueOf(1L);
	public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21_000L);
	  private static final String PASSWORD = "qwerty";
	 
	  
	 @PostConstruct 
	 public void init(){
		 pushData = new DataStorage("localhost", 6379, "/ip4/127.0.0.1/tcp/5001");	// next to be parametrized
		 System.out.println("init data to redis, ipfs sukses");
	 }
	
	
	@GetMapping("/init")
	public  ResponseInit initAccount() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException, CipherException {
	
		ResponseInit response = new ResponseInit();
		ArrayList<String> listTempAccount=new ArrayList<String>();
		ArrayList<String> listTempBalance=new ArrayList<String>();
		
		EthCoinbase coinbase = web3j.ethCoinbase().send();
    	EthAccounts accounts = web3j.ethAccounts().send();
    	
    	for (int i = 1; i < accounts.getAccounts().size(); i++) {
    		EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();
//            Transaction transaction = Transaction.createEtherTransaction(coinbase.getAddress(), transactionCount.getTransactionCount(), GAS_PRICE, GAS_LIMIT, accounts.getAccounts().get(i), BigInteger.valueOf(25_000_000_000L));
//            web3j.ethSendTransaction(transaction).send();
            EthGetBalance balance = web3j.ethGetBalance(accounts.getAccounts().get(i), DefaultBlockParameterName.LATEST).send();
            String account  = accounts.getAccounts().get(i);
            String balanceAcct = String.valueOf(balance.getBalance());
            listTempAccount.add(account);
            listTempBalance.add(balanceAcct);
            
            
            //LOGGER.info("Balance: address={}, amount={}", accounts.getAccounts().get(i), balance.getBalance().longValue());
		}
    	
        EthGetBalance balanceCoinbase = web3j.ethGetBalance(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();
    	
        response.setEthCoinbaseAddress(coinbase.getAddress());
        response.setBalanceEthCoinbaseAddress(String.valueOf(balanceCoinbase.getBalance()));
        
        
        
    	String[] stockAcct = new String[listTempAccount.size()];
    	stockAcct = listTempAccount.toArray(stockAcct);
    	
    	String[] stockBalance = new String[listTempBalance.size()];
    	stockBalance = listTempBalance.toArray(stockBalance);
    	
    	response.setListEthAccount(stockAcct);
    	response.setBalanceListEthAccount(stockBalance);
    	
    	return response;
		
		//return test;
	}
	
	@PostMapping("/simpleTransaction")
	public TransactionOutputApi simpleTransaction (@RequestBody TransactionInputApi input) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, CipherException, IOException{
		ObjectFileTransaction result = new ObjectFileTransaction();
		ObjectFileOtherValues other = new ObjectFileOtherValues();
		TransactionOutputApi resutFix = new TransactionOutputApi();
		other.setTransactionCount(String.valueOf(input.getValueTrx()));
		
		 	String file = WalletUtils.generateLightNewWalletFile(PASSWORD, null);
	    	BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L);
	    	BigInteger gasLimit = BigInteger.valueOf(21_000);
	    	BigInteger valueTrx =  BigInteger.valueOf(input.getValueTrx()); // dari fontend
	    	
	    	//credentials = WalletUtils.loadCredentials(PASSWORD, file);
	    
	    	EthCoinbase coinbase = web3j.ethCoinbase().send();
	    	EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();
	    	//Transaction transaction = Transaction.createEtherTransaction(coinbase.getAddress(), transactionCount.getTransactionCount(), gasPrice, gasLimit, credentials.getAddress(),valueTrx);
	    	Transaction transaction = Transaction.createEtherTransaction(coinbase.getAddress(), transactionCount.getTransactionCount(), gasPrice, gasLimit, input.getAddressTo(),valueTrx);
	    	
	    	
	    	web3j.ethSendTransaction(transaction).send();
	    	EthGetBalance balanceTo = web3j.ethGetBalance(input.getAddressTo(), DefaultBlockParameterName.LATEST).send();
	    	EthGetBalance balanceFrom = web3j.ethGetBalance(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();   // custome
	    	
	    	balanceFrom.getBalance();	// balance from
	    												
	    	JSONDataCustome custome = new JSONDataCustome(
	    			file, coinbase.getAddress(), 
	    					transactionCount.getTransactionCount(), 
	    					input.getAddressTo(), 
	    					gasPrice, gasLimit, 
	    					valueTrx, 
	    					balanceFrom.getBalance().longValue()
	    					, balanceTo.getBalance().longValue());
	    	
		
	    	
			
	    	String key = pushData.storeDataToIPFSandRedis(file);
	    	
	    	result = pushData.readDataFromRedis(key);
	    	
	    	resutFix.setAddress(result.getAddress());
	    	resutFix.setCrypto(result.getCrypto());
	    	resutFix.setId(result.getId());
	    	resutFix.setVersion(result.getVersion());
	    	resutFix.setOther_values(result.getOther_values());
	    	resutFix.setKey(key);
	    	resutFix.setNameFile(file);
	    	
	    
		return resutFix;
	}
	
	
	@PostMapping("/readDataFromRedis")
	public ObjectFileTransaction readDataFromRedis(@RequestBody DataStoreRequest dataStoreReq){
		ObjectFileTransaction result = pushData.readDataFromRedis(dataStoreReq.getKey());
		return result;
	}
	
	@PostMapping("/readDataFromIPFS")
	public DataStoreResponse readDataFromIPFS(@RequestBody DataStoreRequest dataStoreReq){
		DataStoreResponse response = new DataStoreResponse();
		String result = pushData.readDataIPFS(dataStoreReq.getKey());
		
		if(null != result){
			response.setKey(dataStoreReq.getKey());
			response.setValue(result);
		} else {
			response.setKey(dataStoreReq.getKey());
			response.setValue("no data found");
		}
		
		return response;
		
		
	}
}
