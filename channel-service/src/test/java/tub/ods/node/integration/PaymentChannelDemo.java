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
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

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

public class PaymentChannelDemo {
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

		SpringApplicationBuilder builder = new SpringApplicationBuilder().sources(PaymentChannelDemo.class)
				.main(PaymentChannelDemo.class).profiles(PROFILE);
		ConfigurableApplicationContext context = builder.build().run();
		context.getBean(PaymentChannelDemo.class).run();

	}

	private void run() throws Exception {

		String pkdspCredentials = "128e586c279071779998bb6a9fd9e2e93eccb36bac83eba60a3dac6273014fe7"; // 0x95Cb5B3436E5A4Fc45A7eafb2135C40d00f52320
		Credentials dspCredentials = Credentials.create(pkdspCredentials);

		String pksspCredentials = "b4fe12264ad31e7c1a0e9de7fa4c2b0a07d5d6b9d1342c1f979a2a3134bf3ffc"; // 0x3165811587992715A55996abbdc4F128C6C04BEc
		Credentials sspCredentials = Credentials.create(pksspCredentials);

		String pkauditorCredentials = "c310e0ac9782bcf4d52cb8a2c0ef4566e2d411d3f1f3b324163de00f30e347aa"; // 0x0AFe0b64C384976905e6398d4eE1161B0aF80693
		Credentials auditorCredentials = Credentials.create(pkauditorCredentials);

		System.out.println(Numeric.toHexString(Numeric.toBytesPadded(dspCredentials.getEcKeyPair().getPrivateKey(), 32)));

//       Credentials dspAddress = Credentials.create(ethProperties.getAccounts().get("dsp").getPrivateKey());
//       Address dspAddress = new Address(dspCredentials.getAddress());
		Address dspAddress = new Address(dspCredentials.getAddress());

//       Credentials sspAddress = Credentials.create(ethProperties.getAccounts().get("ssp").getPrivateKey());
		Address sspAddress = new Address(sspCredentials.getAddress());

//       Credentials auditorAddress = Credentials.create(ethProperties.getAccounts().get("auditor").getPrivateKey());
		Address auditorAddress = new Address(auditorCredentials.getAddress());



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

		String tokenCredentials = "801feeae6cec79bd81f9d5631ca00cf5d09cdd853608f8ec39ee72a2a587975b"; // 0x4c471984F36faB9A1D27Bc83C62d1f656F7E65d5
		Credentials tokenFunding = Credentials.create(tokenCredentials);

		System.out.println("DSP Token balance: " + dspManager.getTokenService().getBalance());
		System.out.println("SSP Token balance: " + sspManager.getTokenService().getBalance());
		System.out.println("Auditor Token balance: " + auditorManager.getTokenService().getBalance());

		long closeTimeout = 6;
		long settleTimeout = 6;
		long auditTimeout = 100;
		BigInteger deposit = TokenConvert.toWei("0.0000001");
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

		ChannelContract channelDsp = dspManager.load(ChannelContract.class, address);
		
		System.out.println("Transfer");
	    BigInteger blok =  dspManager.token().transfer(sspAddress.toString(), deposit).send().getBlockNumber();	

		System.out.println("Approving deposit");
//		dspManager.getTokenService().approve(address, deposit); // approve
		BigInteger blokAppr =  dspManager.token().approve(sspAddress.toString(), deposit).send().getBlockNumber();		// approve	
		
		System.out.println("Put deposit");
		RemoteFunctionCall<TransactionReceipt> resultPut = channelDsp.deposit(deposit);		// resultPut.

		// waitBlock( depositPut + 2);

		System.out.println("Requesting close");
//		RemoteFunctionCall<TransactionReceipt> closeRequested = channelDsp.request_close();
		TransactionReceipt closeRequestedTemp = channelDsp.request_close().send();
		BigInteger closeRequested = closeRequestedTemp.getBlockNumber();


		// waitBlock(closeRequested + closeTimeout);

		SignedChannelState state = new SignedChannelState(address);
		state.setNonce(nonce);
		state.setCompletedTransfers(completedTransfers);
		state.sign(dspCredentials.getEcKeyPair()); //CryptoUtil.java
		
		System.out.println("Completed Transfer" +completedTransfers);

		System.out.println("Closing");
//		channelDsp.close(new BigInteger(String.valueOf(state.getNonce())), state.getCompletedTransfers(), state.getSignature());
		TransactionReceipt closedTemp = channelDsp.close(new Uint256(new BigInteger(String.valueOf(state.getNonce()))), new Uint256(state.getCompletedTransfers()),new DynamicBytes(state.getSignature())).get();
	    BigInteger closed = closedTemp.getBlockNumber();

		// waitBlock(closed + settleTimeout);
		System.out.printf("Channel closed (%d / %d)%n", state.getNonce(), state.getCompletedTransfers());
		
		System.out.println("Settling");
//		channelDsp.settle();
//		waitBlock(settled + 1);
		TransactionReceipt res =  channelDsp.settle().send();
		
//		Transfer ether
		

		System.out.println("Auditor response");
		auditorManager.channelManager().auditReport(address.toString(), new BigInteger(String.valueOf(auditTotal)),
				new BigInteger(String.valueOf(auditFraud)));
		System.out.println("Completed at block " + address.toString() +String.valueOf(auditTotal) );

//		closeAndSettleChannel(state.getSignerAddress(), state.getCompletedTransfers());

        //now channel may be destroyed
		
		System.out.println("Destroy");
		TransactionReceipt destroy = dspManager.channelManager().destroyChannel(address.toString()).send();
        destroyChannel(state);
        
        System.out.printf("Channel destroy (%d / %d)%n", state.getSignerAddress(), state.getChannelAddress());
        
		System.exit(0);
        
		
	}

	private void waitBlock(long blockNumber) throws InterruptedException {
		do {
			long currentBlock = ethereumService.getBlockNumber();
			if (currentBlock >= blockNumber)
				break;
			System.out.printf("Waiting %d more blocks (%d / %d)%n", blockNumber - currentBlock, currentBlock,
					blockNumber);
			Thread.sleep(5000L);
		} while (true);
	}
	
	private void destroyChannel(SignedChannelState state)
			throws ExecutionException, InterruptedException, IOException {
		
		String pkdspCredentials = "128e586c279071779998bb6a9fd9e2e93eccb36bac83eba60a3dac6273014fe7"; // 0x95Cb5B3436E5A4Fc45A7eafb2135C40d00f52320
		Credentials dspCredentials = Credentials.create(pkdspCredentials);
		
		ContractsManager dspManager = contractsManagerFactory
				.createManager(ethereumConfig.createTransactionManager(dspCredentials), dspCredentials);
		
		ContractsManager contractManager = ((BeanFactory) dspManager).getBean(ContractsManagerFactory.class)
				.getContractManager(state.getChannelAddress());
				TransactionReceipt receipt = contractManager.channelManager().destroyChannel(state.getChannelAddress())
				.get();
		List<ChannelManagerContract.ChannelDeletedEventResponse> events = contractManager.channelManager()
				.getChannelDeletedEvents(receipt);

	}

}
