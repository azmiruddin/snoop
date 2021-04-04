package tub.ods.pch.channel.node;


import java.io.IOException;
import java.math.BigInteger;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.RawTransaction;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.RawTransactionManager;

import tub.ods.pch.channel.node.EthRpcProperties;

public class ThreadsafeTransactionManager extends RawTransactionManager {
    private final Credentials credentials;
    private final Web3j web3j;
    private BigInteger nonce = BigInteger.valueOf(-1);

    public ThreadsafeTransactionManager(Web3j web3j, Credentials credentials, EthRpcProperties rpc) {
        super(web3j, credentials, rpc.getAttempts(), (int) rpc.getSleep().toMillis());
        this.web3j = web3j;
        this.credentials = credentials;
    }

    BigInteger getNonce() throws IOException {
        if (nonce.signum() == -1) {
            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

            nonce = ethGetTransactionCount.getTransactionCount();
        } else {
            nonce = nonce.add(BigInteger.ONE);
        }
        return nonce;
    }

    @Override
    public synchronized EthSendTransaction sendTransaction(
        BigInteger gasPrice, BigInteger gasLimit, String to,
        String data, BigInteger value) throws IOException {

        BigInteger nonce = getNonce();
        boolean success = false;
        try {
            BigInteger amountUsed = web3j.ethEstimateGas(new Transaction(getFromAddress(), nonce, gasPrice, gasLimit, to, value, data)).send().getAmountUsed();
            if (amountUsed.compareTo(gasLimit) >= 0) {
                throw new IllegalStateException(String.format("Estimate out of gas, from: %s, to : %s, gas limit: %s", getFromAddress(), to, gasLimit));
            }
            RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                amountUsed.shiftLeft(1),
                to,
                value,
                data);

            EthSendTransaction transaction = signAndSend(rawTransaction);
            success = true;
            return transaction;
        } finally {
            if (!success) this.nonce = BigInteger.valueOf(-1);
        }
    }
}
