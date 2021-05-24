package tub.ods.node.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.springframework.beans.factory.BeanFactory;
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
import org.web3j.tx.Transfer;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import papyrus.channel.ChannelPropertiesMessage;
import papyrus.channel.node.ChannelPoolMessage;
import tub.ods.pch.channel.model.ChannelManagerContract;
import tub.ods.pch.channel.node.ContractsProperties;
import tub.ods.pch.channel.node.EthProperties;
import tub.ods.pch.channel.node.EthereumConfig;
import tub.ods.pch.channel.node.PropertyConvertersConfig;
import tub.ods.pch.channel.node.Web3jConfigurer;
import tub.ods.pch.channel.state.IncomingChannelState;
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
public class StateChannelDemo extends BaseChannelTest {
	
	ConfigurableApplicationContext sender;
	ConfigurableApplicationContext receiver;
	
	private static final String PROFILE = "demo";
	// private static final String PROFILE = "demomain";
	private static final BigInteger GAS_LIMIT = BigInteger.valueOf(500_000L);
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
		// TODO Auto-generated method stub
		SpringApplicationBuilder builder = new SpringApplicationBuilder().sources(StateChannelDemo.class)
				.main(StateChannelDemo.class).profiles(PROFILE);
		ConfigurableApplicationContext context = builder.build().run();
		context.getBean(StateChannelDemo.class).run();

	}

	private void run() throws Exception {

		String pkdspCredentials = "128e586c279071779998bb6a9fd9e2e93eccb36bac83eba60a3dac6273014fe7"; // 0x95Cb5B3436E5A4Fc45A7eafb2135C40d00f52320
		Credentials dspCredentials = Credentials.create(pkdspCredentials);

		String pksspCredentials = "b4fe12264ad31e7c1a0e9de7fa4c2b0a07d5d6b9d1342c1f979a2a3134bf3ffc"; // 0x3165811587992715A55996abbdc4F128C6C04BEc
		Credentials sspCredentials = Credentials.create(pksspCredentials);

		String pkauditorCredentials = "c310e0ac9782bcf4d52cb8a2c0ef4566e2d411d3f1f3b324163de00f30e347aa"; // 0x0AFe0b64C384976905e6398d4eE1161B0aF80693
		Credentials auditorCredentials = Credentials.create(pkauditorCredentials);

		System.out.println(Numeric.toHexString(Numeric.toBytesPadded(dspCredentials.getEcKeyPair().getPrivateKey(), 32)));

//       Credentials dspCredentials = WalletUtils.loadCredentials(password, "D:/Apps/OEPE-12.2.1.9-Photon/workspace/channel-service/UTC--2021-05-01T10-10-09.034595200Z--53fe24bb8f96e68636d9a1154dc49ada5c333097.json");
//       System.out.println(Numeric.toHexString(Numeric.toBytesPadded(dspCredentials.getEcKeyPair().getPrivateKey(), 32)));
//       Credentials sspCredentials = WalletUtils.loadCredentials(password, "D:/Apps/OEPE-12.2.1.9-Photon/workspace/channel-service/UTC--2021-05-01T13-23-19.034099500Z--53479ca535f1bb3627e4b9cb4a54f2232d0e76c8.json");
//       Credentials auditorCredentials = WalletUtils.loadCredentials(password, "D:/Apps/OEPE-12.2.1.9-Photon/workspace/channel-service/UTC--2021-05-01T10-10-09.034595200Z--53fe24bb8f96e68636d9a1154dc49ada5c333097.json");

//       Credentials dspAddress = Credentials.create(ethProperties.getAccounts().get("dsp").getPrivateKey());
//       Address dspAddress = new Address(dspCredentials.getAddress());
		Address dspAddress = new Address(dspCredentials.getAddress());

//       Credentials sspAddress = Credentials.create(ethProperties.getAccounts().get("ssp").getPrivateKey());
		Address sspAddress = new Address(sspCredentials.getAddress());

//       Credentials auditorAddress = Credentials.create(ethProperties.getAccounts().get("auditor").getPrivateKey());
		Address auditorAddress = new Address(auditorCredentials.getAddress());

//       String myCredentials = "0x710120342284C6B5cf1c98d084311D2F2b13eadb"; 
		String myCredentials = "0x273166dD8c7999558974e31bD88ED96208197AE9";

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


//       Transfer.sendFunds(web3j, tokenFunding, dspCredentials.getAddress(), new BigDecimal("1.005"), Convert.Unit.ETHER);
//       Transfer.sendFunds(web3j, tokenFunding, sspCredentials.getAddress(), new BigDecimal("1.006"), Convert.Unit.ETHER);
//       Transfer.sendFunds(web3j, tokenFunding, auditorCredentials.getAddress(), new BigDecimal("1.007"), Convert.Unit.ETHER);

		System.out.println("DSP Token balance: " + dspManager.getTokenService().getBalance());
		System.out.println("SSP Token balance: " + sspManager.getTokenService().getBalance());
		System.out.println("Auditor Token balance: " + auditorManager.getTokenService().getBalance());

		long closeTimeout = 6;
		long settleTimeout = 6;
		long auditTimeout = 100;
		BigInteger deposit = BigInteger.valueOf(10);
		BigInteger transfer = BigInteger.valueOf(15);
		long nonce = 5;
		BigInteger completedTransfers = BigInteger.valueOf(10);
		int auditTotal = 10;
		int auditFraud = 2;
		
		// create new channel
		System.out.println("Creating channel");
		TransactionReceipt receipt = dspManager.channelManager().newChannel(dspAddress, sspAddress, 
				new Uint256(closeTimeout), new Uint256(settleTimeout), new Uint256(auditTimeout), auditorAddress).get(); 
		List<ChannelNewEventResponse> events = dspManager.channelManager().getChannelNewEvents(receipt);
		String contractAddress = dspManager.channelManager().token2().toString();

		if (events.isEmpty()) {
			throw new IllegalStateException("Channel contract was not created");
		}
		Address address = new Address(events.get(events.size() - 1).channel_address);
		long contractCreated = receipt.getBlockNumber().longValueExact();
		System.out.println("Created contract: " + address + " at block " + contractCreated);

		ChannelContract channelDsp = dspManager.load(ChannelContract.class, address);
		
		// Set channel state
		String auditAddress = auditorCredentials.getAddress();
		IncomingChannelState channelState = createPool(ChannelPoolMessage.newBuilder().setDeposit(deposit.toString())
				.setProperties(ChannelPropertiesMessage.newBuilder().setAuditorAddress(auditAddress).build()).build());
		

		System.out.println("Transfer 1");
		BigInteger blok = dspManager.token().transfer(sspAddress.toString(), deposit).send().getBlockNumber(); // transfer

		System.out.println("Approving deposit");
//		long depositPut = channelDsp.deposit(new Uint256(deposit)).get().getBlockNumber().longValueExact();
//		long depositPut = channelDsp.deposit(deposit).send().getBlockNumber().longValueExact();
		BigInteger blokAppr = dspManager.token().approve(sspAddress.toString(), deposit).send().getBlockNumber(); // approve
		
//		waitBlock( depositPut.add(new BigInteger(String.valueOf("2"))));
		
//		Transfer token balance to other address 		
//		Transfer.sendFunds(web3j, dspCredentials, myCredentials, new BigDecimal("0.154"), Convert.Unit.ETHER).send();
		System.out.println("Transfer 2");
		BigInteger blok2 = sspManager.token().transfer(dspAddress.toString(), transfer).send().getBlockNumber(); // transfer
		System.out.println("Transfer 2 Amount" +transfer);

		System.out.println("Requesting close");

		// long closeRequested = (RemoteFunctionCall<TransactionReceipt>)
		// channelDsp.request_close()).get().getBlockNumber().longValueExact();
//		TransactionReceipt closeRequestedTemp = channelDsp.request_close().send();
//		BigInteger closeRequested = closeRequestedTemp.getBlockNumber();

		// waitBlock(closeRequested.add(new BigInteger(String.valueOf(closeTimeout))));

		// claim
		SignedChannelState state = new SignedChannelState(address);
		state.setNonce(nonce); //number of transactions sent from a given address.
		state.setCompletedTransfers(completedTransfers);
		state.sign(dspCredentials.getEcKeyPair());

		System.out.println("Closing -->");
//		TransactionReceipt closedTemp = channelDsp.close(new Uint256(new BigInteger(String.valueOf(state.getNonce()))),
//				new Uint256(state.getCompletedTransfers()), new DynamicBytes(state.getSignature())).get();
//		BigInteger closed = closedTemp.getBlockNumber();
		// waitBlock(closed.add(new BigInteger(String.valueOf(settleTimeout))));

		System.out.println("Settling");
//		TransactionReceipt res = channelDsp.settle().send();
		// waitBlock(settled.add(BigInteger.ONE));

		System.out.println("Destroy");
//		TransactionReceipt destroy = dspManager.channelManager().destroyChannel(address.toString()).send();
		destroyChannel(channelState);

		// cek kontrak
		System.out.println("Auditor response");
		System.exit(0);
	}

	private void waitBlock(BigInteger blockNumber) throws InterruptedException {
		do {
			long currentBlockTemp = ethereumService.getBlockNumber();
			BigInteger currentBlock = BigInteger.valueOf(currentBlockTemp);
			// if (currentBlock >= blockNumber) break;
			if (currentBlock.compareTo(blockNumber) >= 0)
				break;
			System.out.printf("Waiting %d more blocks (%d / %d)%n", blockNumber.subtract(currentBlock), currentBlock,
					blockNumber);
			Thread.sleep(5000L);
		} while (true);
	}
	
	private void destroyChannel(IncomingChannelState channelState)
			throws ExecutionException, InterruptedException, IOException {
		
		ContractsManager contractManager = sender.getBean(ContractsManagerFactory.class)
				.getContractManager(channelState.getSenderAddress());
		Assert.assertTrue(contractManager.contractExists(channelState.getChannelAddress().toString()));
		TransactionReceipt receipt = contractManager.channelManager().destroyChannel(channelState.getChannelAddress()).get();
		List<ChannelManagerContract.ChannelDeletedEventResponse> events = contractManager.channelManager()
				.getChannelDeletedEvents(receipt);
//		Assert.assertEquals(1, events.size());
//		Assert.assertFalse(contractManager.contractExists(channelState.getChannelAddress().toString()));
	}

}
