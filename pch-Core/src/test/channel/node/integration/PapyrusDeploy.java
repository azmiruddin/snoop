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
import org.web3j.protocol.exceptions.TransactionTimeoutException;
import org.web3j.utils.Files;

import papyrus.channel.node.config.ContractsProperties;
import papyrus.channel.node.config.EthProperties;
import papyrus.channel.node.config.EthereumConfig;
import papyrus.channel.node.config.PropertyConvertersConfig;
import papyrus.channel.node.config.Web3jConfigurer;
import papyrus.channel.node.server.ethereum.EthereumService;
import papyrus.channel.node.server.ethereum.ThreadsafeTransactionManager;

@EnableConfigurationProperties({EthProperties.class, ContractsProperties.class})
@SpringBootApplication()
@Import({PropertyConvertersConfig.class, EthereumConfig.class, Web3jConfigurer.class, EthereumService.class})
public class PapyrusDeploy {
    private static final String PROFILE = "deploy";
//    private static final String PROFILE = "demomain";
    @Autowired
    EthProperties ethProperties;
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
            .sources(PapyrusDeploy.class)
            .main(PapyrusDeploy.class)
            .profiles(PROFILE);
        ConfigurableApplicationContext context = builder.build().run();
        context.getBean(PapyrusDeploy.class).run();
    }

    private void run() throws Exception {
        Address address = new Address("0f08407d6816bb5fac35d4d057bd2175e27b19de");
        m = ethereumConfig.getTransactionManager(address);

        Map<String, Address> libraries = new HashMap<>();
        String pathname = "smart-contracts/build/solc/ECRecovery.bin";
        Address ecRecoveryAddress = deploy(libraries, pathname);
        libraries.put("contracts/ChannelLibrary.sol:Channel", ecRecoveryAddress);
        Address channelLibraryAddress = deploy(libraries, "smart-contracts/build/solc/ChannelLibrary.bin");
        Address endpointRegistryAddress = deploy(libraries, "smart-contracts/build/solc/EndpointRegistryContract.bin");
        Address tokenAddress = deploy(libraries, "smart-contracts/build/solc/PapyrusToken.bin");
        Address apiAddress = deploy(libraries, "smart-contracts/build/solc/ChannelApiStub.bin");

        Address contractAddress = ethereumService.deployContract(m, 
            Files.readString(new File("smart-contracts/build/solc/ChannelLibrary.bin")),
            libraries,
            tokenAddress, 
            apiAddress
        );
        System.out.println("Deployed: " + contractAddress);
        String password = "83917419471923841";
        Credentials dspCredentials = WalletUtils.loadCredentials(password, "/Users/leonidtalalaev/Downloads/keystore/UTC--2017-10-11T22-36-44.792457800Z--b81d511f1c605a90477b2721f3ba48a6356f5c71");
//        Credentials sspCredentials = WalletUtils.loadCredentials(password, "/Users/leonidtalalaev/Downloads/keystore/UTC--2017-10-11T22-37-08.288421000Z--516adeee35dabbadfca78a534aa875eb1f1f2f11");
//        Credentials auditorCredentials = WalletUtils.loadCredentials(password, "/Users/leonidtalalaev/Downloads/keystore/UTC--2017-10-11T22-37-18.395542600Z--c81e161602c5ca038c931a827b7b2ab1ac34a7c0");

//        Transfer.sendFunds(web3j, myCredentials, dspCredentials.getAddress(), new BigDecimal("0.1"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, myCredentials, sspCredentials.getAddress(), new BigDecimal("0.005"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, myCredentials, auditorCredentials.getAddress(), new BigDecimal("0.005"), Convert.Unit.ETHER);
    }

    private Address deploy(Map<String, Address> libraries, String pathname) throws IOException, TransactionTimeoutException, InterruptedException {
        Address address = ethereumService.deployContract(m, Files.readString(new File(pathname)), libraries);
        System.out.println(pathname + ": " + address);
        return address;
    }
}
