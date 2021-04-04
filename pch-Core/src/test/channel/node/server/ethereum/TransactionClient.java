package test.channel.node.server.ethereum;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Convert;

/**
 * @author Azmiruddin
 *
 */
public class TransactionClient {
	private static Web3j web3j;
	private static Admin admin;

	private static String fromAddress = "0x7b1cc408fcb2de1d510c1bf46a329e9027db4112";
	private static String toAddress = "0x05f50cd5a97d9b3fec35df3d0c6c8234e6793bdf";
	private static BigDecimal defaultGasPrice = BigDecimal.valueOf(5);

	public static void main(String[] args) {
		web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		admin = Admin.build(new HttpService(Environment.RPC_URL));

		getBalance(fromAddress);
		sendTransaction();
	}

	/**
	 * Get balance
	 *
	 * @param address wallet address
	 * @return Balance
	 */
	private static BigInteger getBalance(String address) {
		BigInteger balance = null;
		try {
			EthGetBalance ethGetBalance = web3j.ethGetBalance(address, DefaultBlockParameterName.LATEST).send();
			balance = ethGetBalance.getBalance();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("address " + address + " balance " + balance + "wei");
		return balance;
	}

	/**
	 * Generate an ordinary transaction object
	 *
	 * @param fromAddress 
	 * @param toAddress   
	 * @param nonce       Transaction number
	 * @param gasPrice    gas price
	 * @param gasLimit    gas limit
	 * @param value       amount
	 * @return beneficiary address 
	 */
	private static Transaction makeTransaction(String fromAddress, String toAddress,
											   BigInteger nonce, BigInteger gasPrice,
											   BigInteger gasLimit, BigInteger value) {
		Transaction transaction;
		transaction = Transaction.createEtherTransaction(fromAddress, nonce, gasPrice, gasLimit, toAddress, value);
		return transaction;
	}

	/**
	 * Get the gas upper limit of ordinary transactions
	 * @param transaction transaction object
	 * @return gas limit
	 */
	
	private static BigInteger getTransactionGasLimit(Transaction transaction) {
		BigInteger gasLimit = BigInteger.ZERO;
		try {
			EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();
			gasLimit = ethEstimateGas.getAmountUsed();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gasLimit;
	}

	/**
	 * Tranaction number nonce
	 *
	 * @param address Wallet address
	 * @return nonce
	 */
	private static BigInteger getTransactionNonce(String address) {
		BigInteger nonce = BigInteger.ZERO;
		try {
			EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(address, DefaultBlockParameterName.PENDING).send();
			nonce = ethGetTransactionCount.getTransactionCount();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nonce;
	}

	/**
	 * Send a normal transaction
	 * @return transaction Hash
	 */
	
	private static String sendTransaction() {
		String password = "yzw";
		BigInteger unlockDuration = BigInteger.valueOf(60L);
		BigDecimal amount = new BigDecimal("0.01");
		String txHash = null;
		try {
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromAddress, password, unlockDuration).send();
			if (personalUnlockAccount.accountUnlocked()) {
				BigInteger value = Convert.toWei(amount, Convert.Unit.ETHER).toBigInteger();
				Transaction transaction = makeTransaction(fromAddress, toAddress,
						null, null, null, value);
				//Not required, you can use the default value
				BigInteger gasLimit = getTransactionGasLimit(transaction);
				//Not required, the default value is the correct value
				BigInteger nonce = getTransactionNonce(fromAddress);
				//This value is a gasPrice acceptable to most miners
				BigInteger gasPrice = Convert.toWei(defaultGasPrice, Convert.Unit.GWEI).toBigInteger();
				transaction = makeTransaction(fromAddress, toAddress,
						nonce, gasPrice,
						gasLimit, value);
				EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();
				txHash = ethSendTransaction.getTransactionHash();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("tx hash " + txHash);
		return txHash;
	}

	//Use web3j.ethSendRawTransaction() to send a transaction. 
	//We need to use a private key to self-sign the transaction. See ColdWallet.java for details
}
