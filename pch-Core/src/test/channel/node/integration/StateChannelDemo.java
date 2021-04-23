package test.channel.node.integration;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import io.reactivex.Flowable;
import tub.ods.pch.channel.node.ContractsProperties;
import tub.ods.pch.channel.node.EthProperties;
import tub.ods.pch.channel.node.EthereumConfig;
import tub.ods.pch.channel.node.PropertyConvertersConfig;
import tub.ods.pch.channel.node.Web3jConfigurer;
import tub.ods.pch.channel.util.ChannelServerProperties;
import tub.ods.pch.channel.model.ChannelContract;
import tub.ods.pch.channel.model.ChannelContract.ChannelClosedEventResponse;
import tub.ods.pch.channel.SignedChannelState;
import tub.ods.pch.channel.node.ContractsManager;
import tub.ods.pch.channel.node.ContractsManagerFactory;
import tub.ods.pch.channel.node.EthereumService;
import tub.ods.pch.channel.node.TokenConvert;

@EnableConfigurationProperties({EthProperties.class, ContractsProperties.class})
@SpringBootApplication()
@Import({ChannelServerProperties.class, PropertyConvertersConfig.class, EthereumConfig.class, Web3jConfigurer.class, ContractsManagerFactory.class, EthereumService.class})
public class StateChannelDemo {
    private static final String PROFILE = "demo";
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
    ContractsManagerFactory contractsManagerFactory;
    @Autowired
    Web3j web3j;
    
    public static void main(String[] args) throws Exception {
        SpringApplicationBuilder builder = new SpringApplicationBuilder()
            .sources(StateChannelDemo.class)
            .main(StateChannelDemo.class)
            .profiles(PROFILE);
        ConfigurableApplicationContext context = builder.build().run();
        context.getBean(StateChannelDemo.class).run();
    }

    private void run() throws Exception {
        System.out.println("Run  :: Spring Boot :: ");

//        System.out.println(Numeric.toHexString(Numeric.toBytesPadded(devnetCredentials.getEcKeyPair().getPrivateKey(), 32)));
        String password = "83917419471923841";
        Credentials dspCredentials = WalletUtils.loadCredentials(password, "/Users/leonidtalalaev/Downloads/keystore/UTC--2017-10-11T22-36-44.792457800Z--b81d511f1c605a90477b2721f3ba48a6356f5c71");
        System.out.println(Numeric.toHexString(Numeric.toBytesPadded(dspCredentials.getEcKeyPair().getPrivateKey(), 32)));
        Credentials sspCredentials = WalletUtils.loadCredentials(password, "/Users/leonidtalalaev/Downloads/keystore/UTC--2017-10-11T22-37-08.288421000Z--516adeee35dabbadfca78a534aa875eb1f1f2f11");
        Credentials auditorCredentials = WalletUtils.loadCredentials(password, "/Users/leonidtalalaev/Downloads/keystore/UTC--2017-10-11T22-37-18.395542600Z--c81e161602c5ca038c931a827b7b2ab1ac34a7c0");

//        Credentials dspCredentials = WalletUtils.loadCredentials("mypasswd", "/Users/leonidtalalaev/Downloads/activityTrack_part1/UTC--2017-10-10T19-12-02.047955200Z--b1272d6cc40d0c354a83b6a9ba393d5f0588de6c");
//        Credentials sspCredentials = WalletUtils.loadCredentials("mypasswd", "/Users/leonidtalalaev/Downloads/activityTrack_part1/UTC--2017-10-10T19-12-34.724365300Z--146fe0230dbaa285be64099770f26e4915ad8932");
//        Credentials auditorCredentials = WalletUtils.loadCredentials("mypasswd", "/Users/leonidtalalaev/Downloads/activityTrack_part1/UTC--2017-10-10T19-12-52.027114900Z--33dd487b3be0ccd25c01e39877a9f04f73552756");

//        Credentials dspCredentials = Credentials.create(ethProperties.getAccounts().get("dsp").getPrivateKey());
        Address dspAddress = new Address(dspCredentials.getAddress());

//        Credentials sspCredentials = Credentials.create(ethProperties.getAccounts().get("ssp").getPrivateKey());
        Address sspAddress = new Address(sspCredentials.getAddress());

//        Credentials auditorCredentials = Credentials.create(ethProperties.getAccounts().get("auditor").getPrivateKey());
        Address auditorAddress = new Address(auditorCredentials.getAddress());

        System.out.println("DSP Address: " + dspAddress);
        System.out.println("SSP Address: " + sspAddress);
        System.out.println("Auditor Address: " + auditorAddress);

        ContractsManager dspManager = contractsManagerFactory.createManager(ethereumConfig.createTransactionManager(dspCredentials), dspCredentials);
        ContractsManager sspManager = contractsManagerFactory.createManager(ethereumConfig.createTransactionManager(sspCredentials), sspCredentials);
        ContractsManager auditorManager = contractsManagerFactory.createManager(ethereumConfig.createTransactionManager(auditorCredentials), auditorCredentials);

        BigDecimal dspBalance = ethereumService.getBalance(dspAddress.toString(), Convert.Unit.ETHER);
        System.out.println("DSP ETH balance: " + dspBalance);
        System.out.println("SSP ETH balance: " + ethereumService.getBalance(sspAddress.toString(), Convert.Unit.ETHER));
        System.out.println("Auditor ETH balance: " + ethereumService.getBalance(auditorAddress.toString(), Convert.Unit.ETHER));

        System.out.println("DSP PRP balance: " + dspManager.getTokenService().getBalance());
        System.out.println("SSP PRP balance: " + sspManager.getTokenService().getBalance());
        System.out.println("Auditor PRP balance: " + auditorManager.getTokenService().getBalance());

//        Transfer.sendFunds(web3j, dspCredentials, myCredentials.getAddress(), new BigDecimal("0.154"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, myCredentials, dspCredentials.getAddress(), new BigDecimal("0.1"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, myCredentials, sspCredentials.getAddress(), new BigDecimal("0.005"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, myCredentials, auditorCredentials.getAddress(), new BigDecimal("0.005"), Convert.Unit.ETHER);
        System.exit(0);

        long closeTimeout = 6;
        long settleTimeout = 6;
        long auditTimeout = 100;
        BigInteger deposit = TokenConvert.toWei("1");
        long nonce = 10;
        BigInteger completedTransfers = TokenConvert.toWei("0.8");
        int auditTotal = 10;
        int auditFraud = 2;
        
//        System.out.println("Creating channel");
//        TransactionReceipt receipt = dspManager.channelManager().newChannel(dspAddress, sspAddress, new Uint256(closeTimeout), new Uint256(settleTimeout), new Uint256(auditTimeout), auditorAddress).get();
//        List<ChannelManagerContract.ChannelNewEventResponse> events = dspManager.channelManager().getChannelNewEvents(receipt);
//        if (events.isEmpty()) {
//            throw new IllegalStateException("Channel contract was not created");
//        }
//        Address address = events.get(events.size() - 1).channel_address;//1- 0x45b9e1b6bd249a255d9b347a426ca7f2e9a990ff, 2 - 0x964613d656d330180048e39fb2a96050d21f5bb8, mainnet: 0xa305bf2ce267eca84efa948be34024eec1e32e6d
//        long contractCreated = receipt.getBlockNumber().longValueExact();
//        System.out.println("Created contract: " + address +" at block " + contractCreated);
        Address address = new Address("0xa305bf2ce267eca84efa948be34024eec1e32e6d");
        
        ChannelContract channelDsp = dspManager.load(ChannelContract.class, address);
        
//        System.out.println("Approving deposit");
//        long depositApproved = dspManager.getTokenService().approve(address, deposit).get().getBlockNumber().longValueExact();
//        System.out.println("Put deposit");
//        long depositPut = channelDsp.deposit(new Uint256(deposit)).get().getBlockNumber().longValueExact();
//
//        waitBlock( depositPut + 5);
//
//        System.out.println("Requesting close");
//        long closeRequested = channelDsp.request_close().get().getBlockNumber().longValueExact();
//
//        waitBlock(closeRequested + closeTimeout);
        SignedChannelState state = new SignedChannelState(address);
        state.setNonce(nonce);
        state.setCompletedTransfers(completedTransfers);
        state.sign(dspCredentials.getEcKeyPair());
        
        System.out.println("Closing");
//        long closed = channelDsp.close(new Uint256(state.getNonce()), new Uint256(state.getCompletedTransfers()), new DynamicBytes(state.getSignature())).get().getBlockNumber().longValueExact();
		EthFilter filter = null;
		Flowable<ChannelClosedEventResponse> closed = channelDsp.channelClosedEventFlowable(filter);
		waitBlock(closeTimeout);
//        waitBlock(closed + settleTimeout);
/*
        System.out.println("Settling");
        long settled = channelDsp.settle().get().getBlockNumber().longValueExact();
        waitBlock(settled + 2);
        
        System.out.println("Auditor response");
        long audited = auditorManager.channelManager().auditReport(address, new Uint256(auditTotal), new Uint256(auditFraud)).get().getBlockNumber().longValueExact();
        System.out.println("Completed at block " + audited);*/
    }

    private void waitBlock(long blockNumber) throws InterruptedException {
        do {
            long currentBlock = ethereumService.getBlockNumber();
            if (currentBlock >= blockNumber) break;
            System.out.printf("Waiting %d more blocks (%d / %d)%n", blockNumber - currentBlock, currentBlock, blockNumber);
            Thread.sleep(5000L);
        } while (true);
    }
}
