package test.channel.node.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SignatureException;
import java.util.concurrent.ExecutionException;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.beans.BeansException;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.utils.Convert;

import com.google.common.base.Throwables;

import papyrus.channel.ChannelPropertiesMessage;
import papyrus.channel.node.AddChannelPoolRequest;
import papyrus.channel.node.ChannelNodeApplication;
import papyrus.channel.node.ChannelPoolMessage;
import papyrus.channel.node.ChannelStatusRequest;
import papyrus.channel.node.ChannelStatusResponse;
import papyrus.channel.node.RegisterTransfersRequest;
import papyrus.channel.node.RemoveChannelPoolRequest;
import papyrus.channel.node.UnlockTransferRequest;
import papyrus.channel.node.config.EthProperties;
import papyrus.channel.node.config.EthereumConfig;
import papyrus.channel.node.contract.SCHToken;
import papyrus.channel.node.server.NodeServer;
import papyrus.channel.node.server.channel.SignedTransfer;
import papyrus.channel.node.server.channel.SignedTransferUnlock;
import papyrus.channel.node.server.channel.incoming.IncomingChannelManagers;
import papyrus.channel.node.server.channel.incoming.IncomingChannelState;
import papyrus.channel.node.server.channel.outgoing.OutgoingChannelCoordinator;
import papyrus.channel.node.server.channel.outgoing.OutgoingChannelState;
import papyrus.channel.node.server.ethereum.ContractsManagerFactory;
import papyrus.channel.node.server.ethereum.TokenConvert;
import papyrus.channel.node.server.peer.PeerConnection;
import papyrus.channel.node.server.persistence.DatabaseCleaner;

/**
 * Before test run: <br>
 * <code>
 *     truffle migrate --reset
 * </code> the copy contract addresses to application-devnet.yaml
 */
public class BaseChannelTest {
	ConfigurableApplicationContext sender;
	ConfigurableApplicationContext receiver;
	PeerConnection senderClient;
	PeerConnection receiverClient;
	Credentials clientCredentials;
	SCHToken token;
	Credentials senderCredentials;
	Credentials receiverCredentials;
	Address senderAddress;
	Address receiverAddress;
	BigInteger senderStartBalance;
	BigInteger receiverStartBalance;

	@Before
	public void init() throws IOException, CipherException, ExecutionException, InterruptedException {
		initSender(true);
		initReceiver();
	}

	void initSender() throws InterruptedException, ExecutionException {
		initSender(false);
	}

	private void initSender(boolean clean) throws InterruptedException, ExecutionException {
		sender = createContext("sender");
		if (clean)
			sender.getBean(DatabaseCleaner.class).clean();
		sender.start();
		senderClient = sender.getBean(PeerConnection.class);
		Assert.assertNotNull(senderClient);
		senderCredentials = sender.getBean(EthereumConfig.class).getMainCredentials();
		senderAddress = new Address(senderCredentials.getAddress());
		clientCredentials = Credentials.create(
				sender.getBean(EthProperties.class).getAccounts().values().iterator().next().getClientPrivateKey());
		Address clientAddress = sender.getBean(EthereumConfig.class).getClientAddress(senderAddress);
		Assert.assertThat(clientAddress.toString(), CoreMatchers.equalTo(clientCredentials.getAddress()));
		token = sender.getBean(ContractsManagerFactory.class).getMainContractManager().token();
		senderStartBalance = token.balanceOf(senderAddress).get().getValue();
	}

	protected ConfigurableApplicationContext createContext(String context) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder().sources(ChannelNodeApplication.class)
				.main(ChannelNodeApplication.class).profiles("test", "devnet", context);
		configureContext(builder);
		return builder.build().run();
	}

	protected void configureContext(SpringApplicationBuilder builder) {
	}

	void initReceiver() throws InterruptedException, ExecutionException {
		receiver = createContext("receiver");
		receiver.start();

		receiverClient = receiver.getBean(PeerConnection.class);
		Assert.assertNotNull(receiverClient);

		receiverCredentials = receiver.getBean(EthereumConfig.class).getMainCredentials();
		receiverAddress = new Address(receiverCredentials.getAddress());

		receiverStartBalance = token.balanceOf(receiverAddress).get().getValue();
	}

	@After
	public void finish() throws IOException, CipherException {
		stopContext(sender);
		stopContext(receiver);
	}

	void stopContext(ConfigurableApplicationContext context) {
		if (context != null)
			try {
				context.stop();
				context.getBean(NodeServer.class).awaitTermination();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

	void closeAndSettleChannel(IncomingChannelState channelState, BigDecimal expectedTotalTransfer)
			throws InterruptedException, ExecutionException {
		Util.assertEquals(expectedTotalTransfer,
				TokenConvert.fromWei(channelState.getOwnState().getCompletedTransfers()));
		Assert.assertTrue(channelState.getSenderState() != null);
		Util.assertEquals(expectedTotalTransfer,
				TokenConvert.fromWei(channelState.getSenderState().getCompletedTransfers()));

		Util.assertNoError(senderClient.getClientAdmin().removeChannelPool(RemoveChannelPoolRequest.newBuilder()
				.setSenderAddress(senderAddress.toString()).setReceiverAddress(receiverAddress.toString()).build())
				.getError());

		Util.waitFor(() -> {
			try {
				return channelState.getChannel().getContract().settled().get().getValue().signum() > 0;
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		});

		// must be settled
		BigInteger expectedTotalTransferWei = Convert.toWei(expectedTotalTransfer, Convert.Unit.ETHER)
				.toBigIntegerExact();
		Util.assertBalance(expectedTotalTransferWei,
				senderStartBalance.subtract(token.balanceOf(senderAddress).get().getValue()));
		Util.assertBalance(expectedTotalTransferWei,
				token.balanceOf(receiverAddress).get().getValue().subtract(receiverStartBalance));
	}

	IncomingChannelState createPool(ChannelPoolMessage request) throws InterruptedException, ExecutionException {
		BigDecimal deposit = new BigDecimal(request.getDeposit());

		AddChannelPoolRequest.Builder requestBuilder = AddChannelPoolRequest.newBuilder();
		ChannelPoolMessage.Builder builder = ChannelPoolMessage.newBuilder();
		builder.setSenderAddress(senderAddress.toString());
		builder.setReceiverAddress(receiverAddress.toString());
		builder.setMinActiveChannels(1);
		builder.setMaxActiveChannels(1);
		builder.setCloseBlocksCount(10);
		ChannelPropertiesMessage.Builder propertiesBuilder = builder.getPropertiesBuilder();
		propertiesBuilder.setCloseTimeout(6);
		propertiesBuilder.setSettleTimeout(6);
		propertiesBuilder.setAuditTimeout(100);
		builder.mergeFrom(request);

		requestBuilder.setPool(builder.build());

		senderClient.getClientAdmin().addChannelPool(requestBuilder.build());

		ChannelStatusResponse response = Util.waitFor(
				() -> senderClient.getOutgoingChannelClient()
						.getChannels(ChannelStatusRequest.newBuilder().setSenderAddress(senderAddress.toString())
								.setReceiverAddress(receiverAddress.toString()).build()),
				r -> !r.getChannelList().isEmpty());

		Assert.assertEquals(1, response.getChannelCount());
		response.getChannel(0).getChannelAddress();

		IncomingChannelState channelState = Util.waitFor(() -> {
			try {
				IncomingChannelManagers managers = receiver.getBean(IncomingChannelManagers.class);
				return managers.getManager(receiverAddress).getChannels(senderAddress);
			} catch (BeansException e) {
				throw Throwables.propagate(e);
			}
		}, ch -> ch.size() >= 1).iterator().next();

		Util.assertEquals(BigInteger.ZERO, channelState.getOwnState().getCompletedTransfers());

		Util.assertBalance(Convert.toWei(deposit, Convert.Unit.ETHER).toBigIntegerExact(),
				senderStartBalance.subtract(token.balanceOf(senderAddress).get().getValue()));
		return channelState;
	}

	SignedTransfer sendTransfer(String transferId, IncomingChannelState channelState, BigDecimal sum)
			throws InterruptedException, SignatureException {
		BigInteger completedTransfers = channelState.getSenderState() != null
				? channelState.getSenderState().getCompletedTransfers()
				: BigInteger.ZERO;
		SignedTransfer transfer = new SignedTransfer(transferId, channelState.getChannelAddress().toString(),
				sum.toString(), false);
		transfer.sign(clientCredentials.getEcKeyPair());
		Assert.assertEquals(new Address(clientCredentials.getAddress()), transfer.getSignerAddress());
		Assert.assertEquals(transfer, new SignedTransfer(transfer.toMessage()));

		Util.assertNoError(senderClient.getOutgoingChannelClient()
				.registerTransfers(RegisterTransfersRequest.newBuilder().addTransfer(transfer.toMessage()).build())
				.getError());

		Util.waitFor(() -> channelState.getSenderState() != null
				&& channelState.getSenderState().getCompletedTransfers().compareTo(completedTransfers) > 0);

		Util.assertEquals(completedTransfers.add(Convert.toWei(sum, Convert.Unit.ETHER).toBigIntegerExact()),
				channelState.getSenderState().getCompletedTransfers());
		return transfer;
	}

	SignedTransfer sendTransferLocked(String transferId, IncomingChannelState channelState, BigDecimal sum)
			throws InterruptedException, SignatureException {
		SignedTransfer transfer = new SignedTransfer(transferId, channelState.getChannelAddress().toString(),
				sum.toString(), true);
		transfer.sign(clientCredentials.getEcKeyPair());
		Assert.assertEquals(new Address(clientCredentials.getAddress()), transfer.getSignerAddress());
		Assert.assertEquals(transfer, new SignedTransfer(transfer.toMessage()));

		Util.assertNoError(senderClient.getOutgoingChannelClient()
				.registerTransfers(RegisterTransfersRequest.newBuilder().addTransfer(transfer.toMessage()).build())
				.getError());

		OutgoingChannelState outgoingChannelState = sender.getBean(OutgoingChannelCoordinator.class)
				.getChannel(channelState.getChannelAddress()).get();

		Util.waitFor(() -> !outgoingChannelState.getTransfers().isEmpty());

		Util.assertEquals(sum,
				outgoingChannelState.getTransfers().get(new Uint256(new BigInteger(transferId))).getValue());
		return transfer;
	}

	SignedTransferUnlock unlockTransfer(String transferId, IncomingChannelState channelState,
			Credentials auditorCredentials, BigDecimal sum) throws InterruptedException, SignatureException {
		BigInteger completedTransfers = channelState.getSenderState() != null
				? channelState.getSenderState().getCompletedTransfers()
				: BigInteger.ZERO;
		SignedTransferUnlock transferUnlock = new SignedTransferUnlock(transferId,
				channelState.getChannelAddress().toString());
		transferUnlock.sign(auditorCredentials.getEcKeyPair());
		Assert.assertEquals(new Address(auditorCredentials.getAddress()), transferUnlock.getSignerAddress());
		Assert.assertEquals(transferUnlock, new SignedTransferUnlock(transferUnlock.toMessage()));

		Util.assertNoError(senderClient.getOutgoingChannelClient()
				.unlockTransfer(UnlockTransferRequest.newBuilder().addUnlock(transferUnlock.toMessage()).build())
				.getError());

		Util.waitFor(() -> channelState.getSenderState() != null
				&& channelState.getSenderState().getCompletedTransfers().compareTo(completedTransfers) > 0);

		Util.assertEquals(completedTransfers.add(Convert.toWei(sum, Convert.Unit.ETHER).toBigIntegerExact()),
				channelState.getSenderState().getCompletedTransfers());
		return transferUnlock;
	}

}
