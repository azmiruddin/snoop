package tub.ods.pch.channel.node;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.google.common.base.Throwables;

import tub.ods.pch.channel.model.SCHToken;

public class TokenService {
    private final SCHToken schToken;
    private final Address address;
    private BigInteger balance;
    private CompletableFuture<BigInteger> balanceLoader;

    public TokenService(SCHToken schToken, Address address) {
        this.schToken = schToken;
        this.address = address;
    }

    public synchronized BigInteger getBalance() {
        if (balance == null) {
            reloadBalance();
        }
        return balance;
    }

    public void reloadBalance() {
        if (balanceLoader == null ||  balanceLoader.isCompletedExceptionally()) {
            balanceLoader = CompletableFuture.supplyAsync(this::loadBalance); 
        }
        try {
            balance = balanceLoader.get();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

    public synchronized void invalidateBalance() {
        balance = null;
    }

    public synchronized CompletableFuture<TransactionReceipt> approve(Address spender, BigInteger value) {
        if (getBalance().compareTo(value) < 0) {
            //try again
            reloadBalance();
            if (balance.compareTo(value) < 0) {
                throw new IllegalStateException("Not enough funds to make deposit: " + balance);
            }
        }
        balance = balance.subtract(value);
        return (CompletableFuture<TransactionReceipt>) schToken.approve(spender, new Uint256(value));
    }
    
    public BigInteger allowance(Address spender) throws ExecutionException, InterruptedException {
        return schToken.allowance(address, spender).get().getValue();
    }

    private BigInteger loadBalance() {
        try {
            return schToken.balanceOf(address).get().getValue();
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }
}
