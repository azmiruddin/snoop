package tub.ods.pch.channel.node;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import javax.annotation.PostConstruct;

import org.apache.http.conn.HttpHostConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Type;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Ethereum;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.tx.ClientTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;

import com.google.common.base.Throwables;

import tub.ods.pch.channel.model.ContractLinker;
import tub.ods.pch.channel.util.Retriable;

@Service
public class EthereumService {
    private static final Logger log = LoggerFactory.getLogger(EthereumService.class);
    private String netVersion;

    private EthereumConfig config;
    private final Web3j web3j;
    private long blockNumber;
    private long blockNumberUpdated;

    public EthereumService(EthereumConfig config, Web3j web3j) throws IOException, ExecutionException, InterruptedException {
        this.config = config;

        this.web3j = web3j;
    }

    @PostConstruct
    public void init() throws IOException, InterruptedException, ExecutionException {
        Retriable.wrapTask(this::doInit)
            .retryOn(HttpHostConnectException.class)
            .withErrorMessage("Failed to connect to ethereum RPC")
            .withDelaySec(10)
            .call();
    }

    private void doInit() throws IOException, InterruptedException, ExecutionException {
        netVersion = web3j.netVersion().send().getNetVersion();
        for (Address address : config.getAddresses()) {
            BigDecimal autoRefill = config.getKeyProperties(address).getAutoRefill();

            String nodeAddress = address.toString();
            refill(autoRefill, nodeAddress);
        }
    }

    public void refill(BigDecimal minBalance, String address) throws IOException, InterruptedException, ExecutionException {
        refill(minBalance, address, () -> {
            try {
                String coinbase = web3j.ethCoinbase().send().getAddress();
                return new ClientTransactionManager(web3j, coinbase, 100, 1000);
            } catch (IOException e) {
                throw Throwables.propagate(e);
            }
        });
    }

    public void refill(BigDecimal minBalance, String address, Supplier<TransactionManager> managerSupplier) throws InterruptedException, ExecutionException {
        BigDecimal balance = getBalance(address, Convert.Unit.ETHER);

        log.info("Balance of {} is {}", address, balance);

        if (minBalance != null && minBalance.signum() > 0) {
            if (balance.compareTo(minBalance) < 0) {
                TransactionManager manager = managerSupplier.get();
                log.info("Refill {} ETH from {} to {}", minBalance, manager.getFromAddress(), address);
                //new Transfer(web3j, manager).sendFundsAsync(address, minBalance, Convert.Unit.ETHER).get();
                ((Supplier<TransactionManager>) new Transfer(web3j, manager).sendFunds(address, minBalance, Convert.Unit.ETHER)).get();
            }
            log.info("Balance after refill of {} is {}", address, getBalance(address, Convert.Unit.ETHER));
        }
    }

    public Web3j getWeb3j() {
        return web3j;
    }

    public BigDecimal getBalance(String accountAddress, Convert.Unit unit) {
        try {
            BigInteger balance = web3j.ethGetBalance(accountAddress, DefaultBlockParameterName.LATEST).send().getBalance();
            return Convert.fromWei(new BigDecimal(balance), unit);
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public long getBlockNumber() {
        try {
            if (blockNumber == 0 || System.currentTimeMillis() - blockNumberUpdated > 1000) {
                blockNumber = web3j.ethBlockNumber().send().getBlockNumber().longValueExact();
                blockNumberUpdated = System.currentTimeMillis();
            }
            return blockNumber;
        } catch (IOException e) {
            throw Throwables.propagate(e);
        }
    }

    public Address deployContract(TransactionManager transactionManager, String binary) throws InterruptedException, TransactionException, IOException {
        return deployContract(transactionManager, binary, null);
    }

    public Address deployContract(TransactionManager transactionManager, String binary, Map<String, Address> libraries, Type... constructorArgs) throws IOException, TransactionException, InterruptedException {
        ContractLinker linker = new ContractLinker(binary);
        if (libraries != null && !libraries.isEmpty()) {
            libraries.forEach(linker::link);
        }
        linker.assertLinked();
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(constructorArgs));
        String data = linker.getBinary() + encodedConstructor;

        EthRpcProperties rpcProperties = config.getRpcProperties();
        EthSendTransaction transactionResponse = transactionManager.sendTransaction(
            rpcProperties.getGasPrice(), rpcProperties.getGasLimit(), null, data, BigInteger.ZERO);

        if (transactionResponse.hasError()) {
            throw new RuntimeException("Error processing transaction request: "
                + transactionResponse.getError().getMessage());
        }

        String transactionHash = transactionResponse.getTransactionHash();

        Optional<TransactionReceipt> receiptOptional =
            sendTransactionReceiptRequest(transactionHash, web3j);

        long millis = rpcProperties.getSleep().toMillis();
        int attempts = rpcProperties.getAttempts();
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                Thread.sleep(millis);
                receiptOptional = sendTransactionReceiptRequest(transactionHash, web3j);
            } else {
                String contractAddress = receiptOptional.get().getContractAddress();
                return new Address(contractAddress);
            }
        }
        throw new TransactionException("Transaction receipt was not generated after " + (millis * attempts / 1000)
            + " seconds for transaction: " + transactionHash);
    }

    private Optional<TransactionReceipt> sendTransactionReceiptRequest(
        String transactionHash, Ethereum web3j) throws IOException {
        EthGetTransactionReceipt transactionReceipt =
            web3j.ethGetTransactionReceipt(transactionHash).send();
        if (transactionReceipt.hasError()) {
            throw new RuntimeException("Error processing request: "
                + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}
