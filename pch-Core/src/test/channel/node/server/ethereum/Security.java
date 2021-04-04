package test.channel.node.server.ethereum;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.Bip39Wallet;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class Security {
	private static Web3j web3j;

	public static void main(String[] args) {
		web3j = Web3j.build(new HttpService(Environment.RPC_URL));

		exportPrivateKey("/Users/user/Library/Ethereum/testnet/keystore/UTC--2018-03-03T03-51-50.155565446Z--7b1cc408fcb2de1d510c1bf46a329e9027db4112",
				"yzw");

		importPrivateKey(new BigInteger("", 16),
				"yzw",
				WalletUtils.getTestnetKeyDirectory());

		exportBip39Wallet(WalletUtils.getTestnetKeyDirectory(),
				"yzw");
	}

	/**
	 * Export private key
	 *
	 * @param keystorePath The keystore path of the account
	 * @param password     
	 */
	private static void exportPrivateKey(String keystorePath, String password) {
		try {
			Credentials credentials = WalletUtils.loadCredentials(
					password,
					keystorePath);
			BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();
			System.out.println(privateKey.toString(16));
		} catch (IOException | CipherException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 *
	 * @param privateKey 
	 * @param password   
	 * @param directory  Storage path Default test network 
	 * WalletUtils.getTestnetKeyDirectory() 
	 * Default main network WalletUtils.getMainnetKeyDirectory()
	 */
	private static void importPrivateKey(BigInteger privateKey, String password, String directory) {
		ECKeyPair ecKeyPair = ECKeyPair.create(privateKey);
		try {
			String keystoreName = WalletUtils.generateWalletFile(password,
					ecKeyPair,
					new File(directory),
					true);
			System.out.println("keystore name " + keystoreName);
		} catch (CipherException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generate an account with mnemonic words
	 *
	 * @param keystorePath
	 * @param password
	 */
	private static void exportBip39Wallet(String keystorePath, String password) {
		try {
			// TODO: 2018/3/14 Will throw an exception. Issue has been submitted to the official for reply
			Bip39Wallet bip39Wallet = WalletUtils.generateBip39Wallet(password, new File(keystorePath));
			System.out.println(bip39Wallet);
		} catch (CipherException | IOException e) {
			e.printStackTrace();
		}
	}

}
