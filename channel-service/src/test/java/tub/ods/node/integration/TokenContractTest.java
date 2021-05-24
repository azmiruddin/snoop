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
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import tub.ods.pch.channel.model.SCHToken;

public class TokenContractTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenContractTest.class);

	@Autowired
	Web3j web3j;

	public static final BigInteger GAS_PRICE = BigInteger.valueOf(1L);
	public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21_000L);
	private static final String PASSWORD = "qwerty";

	public static void main(String[] args) {
		try {
			use();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void use() throws Exception {
		Web3j web3j = Web3j.build(new HttpService("http://127.0.0.1:8545"));
		// Prepare a wallet
		String pk = "801feeae6cec79bd81f9d5631ca00cf5d09cdd853608f8ec39ee72a2a587975b";
		// Account address: 0x4c471984F36faB9A1D27Bc83C62d1f656F7E65d5
		Credentials credentials = Credentials.create(pk);

		// Load the contract
		String contractAddress = "0xccD4486BE0D14f52b57F5A98Cd83eb1A7B9BaAa6";
		SCHToken token = SCHToken.load(contractAddress, web3j, credentials, new DefaultGasProvider());
		
		String symbol = token.symbol().send();
		String name = token.name().send();
		BigInteger decimal = token.decimals().send();

		System.out.println("symbol: " + symbol);
		System.out.println("name: " + name);
		System.out.println("decimal: " + decimal.intValueExact());
		
		BigInteger balance0 = token.balanceOf("0x4c471984F36faB9A1D27Bc83C62d1f656F7E65d5").send();
		System.out.println("balance (0x4c471984F36faB9A1D27Bc83C62d1f656F7E65d5)="+balance0.toString());

		BigInteger balance01 = token.balanceOf("0x95Cb5B3436E5A4Fc45A7eafb2135C40d00f52320").send();
		System.out.println("balance (0x95Cb5B3436E5A4Fc45A7eafb2135C40d00f52320)="+balance01.toString());
		
		try {
			TransactionReceipt receipt = token.transfer("0x95Cb5B3436E5A4Fc45A7eafb2135C40d00f52320", new BigInteger("12")).send();
			System.out.println("Transaction hash: "+receipt.getTransactionHash());

			BigInteger balance1 = token.balanceOf("0x95Cb5B3436E5A4Fc45A7eafb2135C40d00f52320").send();
			System.out.println("balance (0x95Cb5B3436E5A4Fc45A7eafb2135C40d00f52320)="+balance1.toString());

			BigInteger balance2 = token.balanceOf("0x4c471984F36faB9A1D27Bc83C62d1f656F7E65d5").send();
			System.out.println("balance (0x4c471984F36faB9A1D27Bc83C62d1f656F7E65d5)="+balance2.toString());
			
			List<SCHToken.TransferEventResponse> events = token.getTransferEvents(receipt);
			events.forEach(event
			 -> System.out.println("from: " + event._from + ", to: " + event._to + ", value: " + event._value));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
