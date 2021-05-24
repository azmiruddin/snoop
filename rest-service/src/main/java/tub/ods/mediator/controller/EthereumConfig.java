package tub.ods.mediator.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;

import com.google.common.base.Preconditions;
//import com.google.common.base.Throwables;



@EnableConfigurationProperties({EthProperties.class, ContractsProperties.class})
@Configuration
@EntityScan(basePackageClasses=EthProperties.class)
public class EthereumConfig {
    private static final Logger log = LoggerFactory.getLogger(EthereumConfig.class);

    private EthProperties properties;
    private final Web3j web3j;
    private final Map<Address, EthKeyProperties> keyProperties;
    private final Map<Address, Credentials> credentialsMap;
    private final Map<Address, ThreadsafeTransactionManager> transactionManagerMap;
    private final Credentials mainCredentials;

    @Autowired
    public EthereumConfig(EthProperties properties, Web3j web3j) throws IOException, CipherException {
        this.properties = properties;
        this.web3j = web3j;
        keyProperties = new HashMap<>();
        credentialsMap = new HashMap<>();
        transactionManagerMap = new HashMap<>();

      //  String pk = "1acbb745c0445a83a203fd3d57f24e19d46aa73320eaf6d227d9420e648a77fb";
        Credentials mainCred = null;
     //   Credentials mainCred = Credentials.create(pk);
        for (Map.Entry<String, EthKeyProperties> e : properties.getAccounts().entrySet()) {
            EthKeyProperties key = e.getValue();
            Credentials credentials = loadCredentials(key);
            if (mainCred == null) mainCred = credentials;
            Address address = new Address(credentials.getAddress());
            keyProperties.put(address, key);
            credentialsMap.put(address, credentials);
            ThreadsafeTransactionManager transactionManager = createTransactionManager(credentials);
            transactionManagerMap.put(address, transactionManager);
        }
        
        assert keyProperties.size() == properties.getAccounts().size();
        assert credentialsMap.size() == properties.getAccounts().size();
        assert transactionManagerMap.size() == properties.getAccounts().size();
        
        Preconditions.checkState(mainCred != null, "No eth.keys defined");
        mainCredentials = mainCred;
        
        log.info("Configured ETH payment accounts:{} and rpc server: {}", credentialsMap.keySet(), this.properties.getRpc().getNodeUrl());
    }

    public ThreadsafeTransactionManager createTransactionManager(Credentials credentials) {
        return new ThreadsafeTransactionManager(web3j, credentials, properties.getRpc());
    }

    private static Credentials loadCredentials(EthKeyProperties keyProperties) {
        if (keyProperties.getKeyLocation() != null) {
            try {
                return WalletUtils.loadCredentials(keyProperties.getKeyPassword(), keyProperties.getKeyLocation());
            } catch (Exception e) {
              //  throw Throwables.propagate(e);
            	   throw new IllegalStateException(e.getMessage());
            }
        } else if (keyProperties.getPrivateKey() != null) {
            return Credentials.create(keyProperties.getPrivateKey());
        } else {
            throw new IllegalStateException("Either node.eth.key-location or node.eth.test.private-key required");
        }
    }

    public EthKeyProperties getKeyProperties(Address address) {
        checkAddress(address);
        return keyProperties.get(address);
    }

    public Set<Address> getAddresses() {
        return keyProperties.keySet();
    }

    public void checkAddress(Address address) {
        if (!hasAddress(address)) {
            throw new IllegalArgumentException("Account " + address + " is not managed");
        }
    }
    
    public boolean hasAddress(Address address) {
        return credentialsMap.containsKey(address);
    }

    public Credentials getMainCredentials() {
        return mainCredentials;
    }

    public Address getMainAddress() {
        return new Address(getMainCredentials().getAddress());
    }

    public ThreadsafeTransactionManager getTransactionManager(Address address) {
        checkAddress(address);
        return transactionManagerMap.get(address);
    }

    public EthRpcProperties getRpcProperties() {
        return properties.getRpc();
    }

    public Address getClientAddress(Address address) {
        checkAddress(address);
        EthKeyProperties ethKeyProperties = keyProperties.get(address);
        return ethKeyProperties == null ? null : ethKeyProperties.getClientAddress() == null ? address : ethKeyProperties.getClientAddress();
    }

    public Credentials getCredentials(Address address) {
        return credentialsMap.get(address);
    }

}
