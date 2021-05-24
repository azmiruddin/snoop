package tub.ods.node.integration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.Future;

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
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import tub.ods.pch.channel.model.ChannelManagerContract;
import tub.ods.pch.channel.node.ContractsProperties;
import tub.ods.pch.channel.node.EthProperties;
import tub.ods.pch.channel.node.EthereumConfig;
import tub.ods.pch.channel.node.PropertyConvertersConfig;
import tub.ods.pch.channel.node.Web3jConfigurer;
import tub.ods.pch.channel.model.ChannelContract;
import tub.ods.pch.channel.model.ChannelManagerContract.ChannelNewEventResponse;
import tub.ods.pch.channel.SignedChannelState;
import tub.ods.pch.channel.node.ContractsManager;
import tub.ods.pch.channel.node.ContractsManagerFactory;
import tub.ods.pch.channel.node.EthereumService;
import tub.ods.pch.channel.node.TokenConvert;

@EnableConfigurationProperties({ EthProperties.class, ContractsProperties.class })
@SpringBootApplication()
@Import({ PropertyConvertersConfig.class, EthereumConfig.class, Web3jConfigurer.class, ContractsManagerFactory.class,
		EthereumService.class })
public class PaymentChannelDemoInit {
	
    private static final String PROFILE = "demo";
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
            .sources(PaymentChannelDemoInit.class)
            .main(PaymentChannelDemoInit.class)
            .profiles(PROFILE);
        ConfigurableApplicationContext context = builder.build().run();
        context.getBean(PaymentChannelDemoInit.class).run();
    }

    private void run() throws Exception {
//        System.out.println("Hello");
//        System.out.println(Numeric.toHexString(Numeric.toBytesPadded(devnetCredentials.getEcKeyPair().getPrivateKey(), 32)));
    	
    	String pkdspCredentials = "5f8483f4ead54e0d59c05eea70bd183e7a32e607c2d0e31baa22c2cd1d699e68"; //0x9FEB109D29C8bAfa6CF205684B9d56b6e2eA6c04
    	Credentials dspCredentials = Credentials.create(pkdspCredentials);
    	
    	String pksspCredentials = "b4fe12264ad31e7c1a0e9de7fa4c2b0a07d5d6b9d1342c1f979a2a3134bf3ffc"; //0x3165811587992715A55996abbdc4F128C6C04BEc
    	Credentials sspCredentials = Credentials.create(pksspCredentials);
    	
    	String pkauditorCredentials = "c310e0ac9782bcf4d52cb8a2c0ef4566e2d411d3f1f3b324163de00f30e347aa"; //0x0AFe0b64C384976905e6398d4eE1161B0aF80693
    	Credentials auditorCredentials = Credentials.create(pkauditorCredentials);
    	
    	System.out.println(Numeric.toHexString(Numeric.toBytesPadded(dspCredentials.getEcKeyPair().getPrivateKey(), 32)));
    	
//        Credentials dspCredentials = WalletUtils.loadCredentials(password, "D:/Apps/OEPE-12.2.1.9-Photon/workspace/channel-service/UTC--2021-05-01T10-10-09.034595200Z--53fe24bb8f96e68636d9a1154dc49ada5c333097.json");
//        System.out.println(Numeric.toHexString(Numeric.toBytesPadded(dspCredentials.getEcKeyPair().getPrivateKey(), 32)));
//        Credentials sspCredentials = WalletUtils.loadCredentials(password, "D:/Apps/OEPE-12.2.1.9-Photon/workspace/channel-service/UTC--2021-05-01T13-23-19.034099500Z--53479ca535f1bb3627e4b9cb4a54f2232d0e76c8.json");
//        Credentials auditorCredentials = WalletUtils.loadCredentials(password, "D:/Apps/OEPE-12.2.1.9-Photon/workspace/channel-service/UTC--2021-05-01T10-10-09.034595200Z--53fe24bb8f96e68636d9a1154dc49ada5c333097.json");

//        Credentials dspAddress = Credentials.create(ethProperties.getAccounts().get("dsp").getPrivateKey());
//        Address dspAddress = new Address(dspCredentials.getAddress());
    	Address dspAddress = new Address(dspCredentials.getAddress());

//        Credentials sspAddress = Credentials.create(ethProperties.getAccounts().get("ssp").getPrivateKey());
        Address sspAddress = new Address(sspCredentials.getAddress());

//        Credentials auditorAddress = Credentials.create(ethProperties.getAccounts().get("auditor").getPrivateKey());
        Address auditorAddress = new Address(auditorCredentials.getAddress());
        
//        String myCredentials = "0x710120342284C6B5cf1c98d084311D2F2b13eadb"; 
        String myCredentials = "0xaCF87397Ba9dc23A86A6585DF1AB9c37D7c9286d";

        System.out.println("DSP Address: " + dspAddress);
        System.out.println("SSP Address: " + sspAddress);
        System.out.println("Auditor Address: " + auditorAddress);

		ContractsManager dspManager = contractsManagerFactory
				.createManager(ethereumConfig.createTransactionManager(dspCredentials), dspCredentials);
		ContractsManager sspManager = contractsManagerFactory
				.createManager(ethereumConfig.createTransactionManager(sspCredentials), sspCredentials);
		ContractsManager auditorManager = contractsManagerFactory
				.createManager(ethereumConfig.createTransactionManager(auditorCredentials), auditorCredentials);

        BigDecimal dspBalance = ethereumService.getBalance(dspAddress.toString(), Convert.Unit.ETHER);
        System.out.println("DSP ETH balance: " + dspBalance);
        System.out.println("SSP ETH balance: " + ethereumService.getBalance(sspAddress.toString(), Convert.Unit.ETHER));
        System.out.println("Auditor ETH balance: " + ethereumService.getBalance(auditorAddress.toString(), Convert.Unit.ETHER));


        
    	String tokenCredentials = "5f8483f4ead54e0d59c05eea70bd183e7a32e607c2d0e31baa22c2cd1d699e68"; //0x9FEB109D29C8bAfa6CF205684B9d56b6e2eA6c04
    	Credentials tokenFunding = Credentials.create(tokenCredentials);

//        Transfer.sendFunds(web3j, dspCredentials, myCredentials, new BigDecimal("0.154"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, tokenFunding, dspCredentials.getAddress(), new BigDecimal("1.005"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, tokenFunding, sspCredentials.getAddress(), new BigDecimal("1.006"), Convert.Unit.ETHER);
//        Transfer.sendFunds(web3j, tokenFunding, auditorCredentials.getAddress(), new BigDecimal("1.007"), Convert.Unit.ETHER);
        
        System.out.println("DSP Token balance: " + dspManager.getTokenService().getBalance());
        System.out.println("SSP Token balance: " + sspManager.getTokenService().getBalance());
        System.out.println("Auditor Token balance: " + auditorManager.getTokenService().getBalance());


        long closeTimeout = 6;
        long settleTimeout = 6;
        long auditTimeout = 100;
        BigInteger deposit = TokenConvert.toWei("0.01");
        long nonce = 10;
        BigInteger completedTransfers = TokenConvert.toWei("0.005");
        int auditTotal = 10;
        int auditFraud = 2;
        
        System.out.println("Creating channel");
		TransactionReceipt receipt = dspManager.channelManager().newChannel(dspAddress, sspAddress,
				new Uint256(closeTimeout), new Uint256(settleTimeout), new Uint256(auditTimeout), auditorAddress).get();
		List<ChannelNewEventResponse> events = dspManager.channelManager().getChannelNewEvents(receipt);
		if (events.isEmpty()) {
			throw new IllegalStateException("Channel contract was not created");
			}
		Address address = new Address(events.get(events.size() - 1).channel_address);
		long contractCreated = receipt.getBlockNumber().longValueExact();
		System.out.println("Created contract: " + address + " at block " + contractCreated);
//        Address address = new Address("0xa305bf2ce267eca84efa948be34024eec1e32e6d");
//        Address address = dspAddress;
        
        ChannelContract channelDsp = dspManager.load(ChannelContract.class, address);
        
		System.out.println("Approving deposit");
		dspManager.getTokenService().approve(address, deposit); // approve
		System.out.println("Put deposit");
		RemoteFunctionCall<TransactionReceipt> resultPut = channelDsp.deposit(deposit);		// resultPut.

//        waitBlock( depositPut + 5);

        System.out.println("Requesting close");
        long closeRequested = ((Future<TransactionReceipt>) channelDsp.request_close()).get().getBlockNumber().longValueExact();

        waitBlock(closeRequested + closeTimeout);
        SignedChannelState state = new SignedChannelState(address);
        state.setNonce(nonce);
        state.setCompletedTransfers(completedTransfers);
        state.sign(dspCredentials.getEcKeyPair());
        
        System.out.println("Closing");
        long closed = channelDsp.close(new Uint256(state.getNonce()), new Uint256(state.getCompletedTransfers()), new DynamicBytes(state.getSignature())).get().getBlockNumber().longValueExact();
        waitBlock(closed + settleTimeout);

        System.out.println("Settling");
        long settled = ((Future<TransactionReceipt>) channelDsp.settle()).get().getBlockNumber().longValueExact();
        waitBlock(settled + 1);
        
        System.out.println("Auditor response");
        long audited = auditorManager.channelManager().auditReport(address, new Uint256(auditTotal), new Uint256(auditFraud)).get().getBlockNumber().longValueExact();
        System.out.println("Completed at block " + audited);
        
        System.exit(0);
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
