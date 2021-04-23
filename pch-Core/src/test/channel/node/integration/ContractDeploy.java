package test.channel.node.integration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.utils.Files;

import tub.ods.pch.channel.node.ContractsProperties;
import tub.ods.pch.channel.node.EthProperties;
import tub.ods.pch.channel.node.EthereumConfig;
import tub.ods.pch.channel.node.EthereumService;
import tub.ods.pch.channel.node.PropertyConvertersConfig;
import tub.ods.pch.channel.node.ThreadsafeTransactionManager;
import tub.ods.pch.channel.node.Web3jConfigurer;
import tub.ods.pch.channel.util.ChannelServerProperties;

@EnableConfigurationProperties({EthProperties.class, ContractsProperties.class})
@SpringBootApplication()
@Import({PropertyConvertersConfig.class, EthereumConfig.class, Web3jConfigurer.class, EthereumService.class, ChannelServerProperties.class})
public class ContractDeploy {
    private static final String PROFILE = "deploy";
//    private static final String PROFILE = "demomain";
    @Autowired
    EthProperties ethProperties;
    @Autowired
    ChannelServerProperties channelServerProperties;
    @Autowired
    ContractsProperties contractsProperties;
    @Autowired
    EthereumConfig ethereumConfig;
    @Autowired
    EthereumService ethereumService;
    @Autowired
    Web3j web3j;
    private ThreadsafeTransactionManager m;

    public static void main(String[] args) throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder()
            .sources(ContractDeploy.class)
            .main(ContractDeploy.class)
            .profiles(PROFILE);
        ConfigurableApplicationContext context = builder.build().run();
        context.getBean(ContractDeploy.class).run();
    }

    private void run() throws Exception {
    	System.out.println("Run  :: Spring Boot :: ");
    	
        Address address = new Address("0f08407d6816bb5fac35d4d057bd2175e27b19de");
        m = ethereumConfig.getTransactionManager(address);

        Map<String, Address> libraries = new HashMap<>();
        String pathname = "pch-Core/contract-service/resources/bin/ECRecovery.bin"; //"smart-contracts/build/solc/ECRecovery.bin";
        Address ecRecoveryAddress = deploy(libraries, pathname);
        libraries.put("/pch-Core/contract-service/resources/ChannelLibrary.sol:Channel", ecRecoveryAddress);
        Address channelLibraryAddress = deploy(libraries, "pch-Core/contract-service/resources/bin//ChannelLibrary.bin");
        Address endpointRegistryAddress = deploy(libraries, "pch-Core/contract-service/resources/bin/EndpointRegistryContract.bin");
        Address tokenAddress = deploy(libraries, "pch-Core/contract-service/resources/bin/CHToken.bin");
        Address apiAddress = deploy(libraries, "pch-Core/contract-service/resources/bin/ChannelApiStub.bin");

        Address contractAddress = ethereumService.deployContract(m, 
            Files.readString(new File("pch-Core/contract-service/resources/bin/ChannelLibrary.bin")),
            libraries,
            tokenAddress, 
            apiAddress
        );
        System.out.println("Deployed: " + contractAddress);
        String password = "83917419471923841";
        Credentials dspCredentials = WalletUtils.loadCredentials(password, "/pch-Core/UTC--2021-04-05T09-09-36.626000000Z--6c620d1d341bb5ee6ffa688283f80c4890e151b9");
//        Credentials sspCredentials = WalletUtils.loadCredentials(password, "/Users/leonidtalalaev/Downloads/keystore/UTC--2017-10-11T22-37-08.288421000Z--516adeee35dabbadfca78a534aa875eb1f1f2f11");
//        Credentials auditorCredentials = WalletUtils.loadCredentials(password, "/Users/leonidtalalaev/Downloads/keystore/UTC--2017-10-11T22-37-18.395542600Z--c81e161602c5ca038c931a827b7b2ab1ac34a7c0");

//        Transfer.sendFunds(web3j, myCredentials, dspCredentials.getAddress(), new BigDecimal("0.1"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, myCredentials, sspCredentials.getAddress(), new BigDecimal("0.005"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, myCredentials, auditorCredentials.getAddress(), new BigDecimal("0.005"), Convert.Unit.ETHER);
    }

    private Address deploy(Map<String, Address> libraries, String pathname) throws IOException, TransactionException, InterruptedException {
        Address address = ethereumService.deployContract(m, Files.readString(new File(pathname)), libraries);
        System.out.println(pathname + ": " + address);
        return address;
    }
}
