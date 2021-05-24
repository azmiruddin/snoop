package tub.ods.node.integration;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import tub.ods.pch.channel.model.SCHToken;

public class SolidityContractTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SolidityContractTest.class);
	
	@Autowired
	Web3j web3j;

	public static final BigInteger GAS_PRICE = BigInteger.valueOf(1L);
	public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21_000L);
	private static final String PASSWORD = "qwerty";
	
	
	public static void main(String[] args) {
		// deploy(); //using ContractDeploy.java
		use();
	}

	private static void use() {
		Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));

//		String contractAddress = "0x4Dbf60d8Ee42Bf9a2f2400aA5a97CF5a475B633f"; //ChannelApiStub
		String contractAddress = "0xccD4486BE0D14f52b57F5A98Cd83eb1A7B9BaAa6"; //SCHToken --> owner address
//		String contractAddress = "0x541e7c3Fbf30419AD549c1A8E924468DD69C3864"; //ChannelLibrary
//		String contractAddress = "0x881A09301ED349040bad34a505a2c16B0d68EA69"; //EndpointRegistryContract
		
		// Account address: 0x4c471984F36faB9A1D27Bc83C62d1f656F7E65d5
		String pk = "801feeae6cec79bd81f9d5631ca00cf5d09cdd853608f8ec39ee72a2a587975b"; 
		// Decrypt and open the wallet into a Credential object
		Credentials credentials = Credentials.create(pk);
		System.out.println("credentials --> \t" + credentials.getAddress());
//		SCHToken contract = SCHToken.load(contractAddress, web3j, credentials, (ContractGasProvider) GAS_LIMIT);
		
		SCHToken contract = SCHToken.load(contractAddress, web3j, credentials,
				Convert.toWei("0.001", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(100000));
		
		System.out.println("Contract --> \t" + contract.getContractAddress());
		String myAddress = "0x0afe0b64c384976905e6398d4ee1161b0af80693"; 
		String toAddress = "0x9FEB109D29C8bAfa6CF205684B9d56b6e2eA6c04"; //Token address
		System.out.println("myAddress --> \t" + myAddress);
		BigInteger amount = BigInteger.TEN;
		try {
			TransactionReceipt trxReceipt = contract.transfer(toAddress, new BigInteger("12")).send();
			System.out.println("Transaction hash: "+trxReceipt.getTransactionHash());
			
//			BigInteger balance = contract.balanceOf(myAddress).send();
			CompletableFuture<BigInteger> balance = contract.balanceOf(myAddress).sendAsync();
			System.out.println("myAddress 2 and balance --> \t" + myAddress +balance.toString());
			
//			CompletableFuture<TransactionReceipt> receipt = contract.transfer(myAddress, amount).sendAsync();
			TransactionReceipt receipt = contract.transfer(toAddress, new BigInteger("12")).send();
			System.out.println("Address and Receipt --> \t" + toAddress + receipt.toString());
			List<SCHToken.TransferEventResponse> events = contract.getTransferEvents(receipt);
			events.forEach(event
			 -> System.out.println("\n from: " + event._from + " \n to: " + event._to + ",  \n value: " + event._value));
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
