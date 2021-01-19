package tub.ods.pch.contract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

import tub.ods.common.data.JSONDataCustome;
import tub.ods.common.data.model.ObjectFileTransaction;
import tub.ods.common.data.SaveTransaction;
import tub.ods.common.data.SerializeUtils;
import redis.clients.jedis.Jedis;
import tub.ods.pch.contract.model.MerkleContract;
import tub.ods.rdf4led.distributed.benchmark.ValidationServiceTest;
import tub.ods.rdf4led.distributed.index.IPFSPhysicalLayer;
import tub.ods.rdf4led.distributed.index.RedisBufferLayer;
import tub.ods.rdf4led.distributed.storage.DistributedTripleStore;
import tub.ods.rdf4led.distributed.storage.factory.BufferLayerFactory;
import tub.ods.rdf4led.distributed.storage.factory.PhysicalLayerFactory;
import tub.ods.rdf4led.distributed.storage.factory.ValidationServiceFactory;

@Service
public class ContractMerkleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractMerkleService.class);
    private static final String PASSWORD = "qwerty";
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(1L);
	private static final BigInteger GAS_LIMIT = BigInteger.valueOf(500_000L);
	private static final BigInteger WEI_VALUE = BigInteger.valueOf(100_000L);
	private final byte[] root = null;
	private final byte[] leaf = null;
	private final List<byte[]> proof = null;


	@Autowired
    Web3j web3j;
    Credentials credentials;
	private final List<String> contracts = new ArrayList<>();
	
	
    @PostConstruct
    public void init() throws IOException, CipherException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException{
        String file = WalletUtils.generateLightNewWalletFile(PASSWORD, null);
    	BigInteger gasPrice = BigInteger.valueOf(20_000_000_000L);
    	BigInteger gasLimit = BigInteger.valueOf(21_000);
    	BigInteger valueTrx =  BigInteger.valueOf(25_000_000_000_000_000L);
    	
    	credentials = WalletUtils.loadCredentials(PASSWORD, file);
    	LOGGER.info("Credentials created: file={}, address={}", file, credentials.getAddress());
    	EthCoinbase coinbase = web3j.ethCoinbase().send();
    	EthGetTransactionCount transactionCount = web3j.ethGetTransactionCount(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();
    	Transaction transaction = Transaction.createEtherTransaction(coinbase.getAddress(), transactionCount.getTransactionCount(), gasPrice, gasLimit, credentials.getAddress(),valueTrx);
    	web3j.ethSendTransaction(transaction).send();
    	EthGetBalance balance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST).send();
    	EthGetBalance balanceFrom = web3j.ethGetBalance(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();   // custome
    	LOGGER.info("Balance From: {}", balanceFrom.getBalance().longValue());
    	LOGGER.info("Balance To: {}", balance.getBalance().longValue());
    	  balanceFrom.getBalance().longValue();	// balance from 
    	  	
    	 JSONDataCustome custome = new JSONDataCustome(file, coinbase.getAddress(), transactionCount.getTransactionCount(), credentials.getAddress(), gasPrice, gasLimit, valueTrx, balanceFrom.getBalance().longValue() , balance.getBalance().longValue());
  
    }  

	public String getOwnerAccount() {
		LOGGER.info("Credential: {}", credentials.getAddress());
    	return credentials.getAddress();    	
    }

    public MerkleContract createContract(MerkleContract newContract) throws Exception {
		String file = WalletUtils.generateLightNewWalletFile(PASSWORD, null);
    	Credentials admin = WalletUtils.loadCredentials(PASSWORD, file);
    	LOGGER.info("Credentials created: file={}, address={}", file, credentials.getAddress());
		MerkleContract contract = MerkleContract.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT, WEI_VALUE,
				newContract.getContractAddress(), newContract.getGasPrice(), root).send();
		newContract.channelRecipient(admin.getAddress());
    	newContract.setContractAddress(contract.getContractAddress());
    	contracts.add(contract.getContractAddress());
    	LOGGER.info("New contract deployed: address={}", contract.getContractAddress());
    	Optional<TransactionReceipt> tr = contract.getTransactionReceipt();
    	if (tr.isPresent()) {
    		LOGGER.info("Transaction receipt: from={}, to={}, gas={}", tr.get().getFrom(), tr.get().getTo(), tr.get().getGasUsed().intValue());
    	}
    	return newContract;
	}

	public void MerkleVerification() throws Exception {
		MerkleContract MerkleVerification = new MerkleContract.verifyMerkle(null,null, null, null);
		MerkleVerification.verifyMerkle(null, null, null).send();
	}
	
	public void customeDataJson(String file, String coinBaseAddress, BigInteger trxCount, String credentialsAddrs, BigInteger gasPrice, BigInteger gasLimit, BigInteger valueTrx) throws IOException{
		 	BufferedReader reader = null;
         
			File filetobereplace = new File(file);
			
			reader = new BufferedReader(new FileReader(filetobereplace));
			
			String line = reader.readLine();
			String oldContent = "";

			while (line != null)
			{
			        oldContent = oldContent + line;
			        line = reader.readLine();
			}
			
			 String modifiedFileContent = oldContent.replaceAll("}}", "},"+"\""+"other_values"+"\""+":");

	         //8
			 FileWriter writer = new FileWriter(file);
			 writer.write(modifiedFileContent);
			 
			 writer.flush();
			 writer.close();
			 reader.close();
			 
			 
			 
			 JsonWriter  writerJson = new JsonWriter(new FileWriter(file,true)); 
			 writerJson.beginObject();
			 writerJson.name("coinbaseAddress").value(coinBaseAddress);
			 writerJson.name("transactionCount").value(trxCount);
			 writerJson.name("credentialsAddress").value(credentialsAddrs);
			 writerJson.name("gasPrice").value(gasPrice);
			 writerJson.name("gasLimit").value(gasLimit);
			 writerJson.name("valueTrx").value(valueTrx);
			 writerJson.endObject();
			 writerJson.close();
			 
			 BufferedReader readerNew = null;
	
			 readerNew = new BufferedReader(new FileReader(filetobereplace));
	
			 String lineNew = readerNew.readLine();
	
			 String modifiedFileContentNew = lineNew + "}";
	
			// 8
			 FileWriter writerNew = new FileWriter(file);
			 writerNew.write(modifiedFileContentNew);
	
			 writerNew.flush();
			 writerNew.close();
			 readerNew.close();
		}

}
