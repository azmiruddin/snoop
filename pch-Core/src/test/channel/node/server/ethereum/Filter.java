package test.channel.node.server.ethereum;

import java.math.BigInteger;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.http.HttpService;

import rx.Subscription;

/**
 * filter related
 * Monitor blocks and transactions
 * All monitoring is in Web3jRx
*/

public class Filter {
	private static Web3j web3j;

	public static void main(String[] args) {
		web3j = Web3j.build(new HttpService(Environment.RPC_URL));
		/**
		 * New block monitoring
		 */
		newBlockFilter(web3j);
		/**
		 * New transaction monitoring
		 */
		newTransactionFilter(web3j);
		/**
		 * Traverse old blocks, transactions
		 */
		replayFilter(web3j);
		/**
		 * 
		 */
		catchUpFilter(web3j);

		/**
		 * Unsubscribe
		 */
		//subscription.unsubscribe();
	}

	private static void newBlockFilter(Web3j web3j) {
		Subscription subscription = (Subscription) web3j.blockFlowable(false).
				subscribe(block -> {
					System.out.println("new block come in");
					System.out.println("block number" + block.getBlock().getNumber());
				});
	}

	private static void newTransactionFilter(Web3j web3j) {
		Subscription subscription = (Subscription) web3j.transactionFlowable().
				subscribe(transaction -> {
					System.out.println("transaction come in");
					System.out.println("transaction txHash " + transaction.getHash());
				});
	}

	private static void replayFilter(Web3j web3j) {
		BigInteger startBlock = BigInteger.valueOf(2000000);
		BigInteger endBlock = BigInteger.valueOf(2010000);
		/**
		 * Traverse old blocks
		 */
		Subscription subscription = (Subscription) web3j.
				replayPastBlocksFlowable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock),
						false).
				subscribe(ethBlock -> {
					System.out.println("replay block");
					System.out.println(ethBlock.getBlock().getNumber());
				});

		/**
		 * Iterate through old transactions
		 */
		Subscription subscription1 = (Subscription) web3j.
				replayPastTransactionsFlowable(
						DefaultBlockParameter.valueOf(startBlock),
						DefaultBlockParameter.valueOf(endBlock)).
				subscribe(transaction -> {
					System.out.println("replay transaction");
					System.out.println("txHash " + transaction.getHash());
				});
	}

	private static void catchUpFilter(Web3j web3j) {
		BigInteger startBlock = BigInteger.valueOf(2000000);

		/**
		 * Traverse the old block and monitor the new block
		 */
		Subscription subscription = web3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
				DefaultBlockParameter.valueOf(startBlock), false)
				.subscribe(block -> {
					System.out.println("block");
					System.out.println(block.getBlock().getNumber());
				});

		/**
		 * Traverse old transactions and monitor new transactions
		 */
		Subscription subscription2 = web3j.catchUpToLatestAndSubscribeToNewTransactionsObservable(
				DefaultBlockParameter.valueOf(startBlock))
				.subscribe(tx -> {
					System.out.println("transaction");
					System.out.println(tx.getHash());
				});
	}
}
