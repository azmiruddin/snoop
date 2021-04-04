package test.channel.node.server.ethereum.contract;

import test.channel.node.server.ethereum.Environment;
import tub.ods.pch.channel.model.SCHToken;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

import java.math.BigInteger;

public class SolidityContractTest {
	public static void main(String[] args) {
		//deploy(); //using ContractDeploy.java
		use();
	}

	@SuppressWarnings("deprecation")
	private static void use() {
		Web3j web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		String contractAddress = null;
		Credentials credentials = null;//Can be generated based on the private key
		SCHToken contract = SCHToken.load(contractAddress, web3j, credentials,
				Convert.toWei("10", Convert.Unit.GWEI).toBigInteger(),
				BigInteger.valueOf(100000));
		String myAddress = null;
		String toAddress = null;
		BigInteger amount = BigInteger.ONE;
		try {
			BigInteger balance = contract.balanceOf(myAddress).send();
			TransactionReceipt receipt = contract.transfer(toAddress, amount).send();
			//etc..
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	private static void deploy() {
		Web3j web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		Credentials credentials = null;//Can be generated based on the private key
		RemoteCall<SCHToken> deploy = SCHToken.deploy(web3j, credentials,
				Convert.toWei("10", Convert.Unit.GWEI).toBigInteger(),
				BigInteger.valueOf(3000000),
				BigInteger.valueOf(5201314),
				"my token", "mt");
		try {
			SCHToken tokenERC20 = deploy.send();
			tokenERC20.isValid();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
*/
}
