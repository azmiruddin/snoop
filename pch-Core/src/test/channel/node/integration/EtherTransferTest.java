package test.channel.node.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SignatureException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;

import com.google.common.base.Throwables;

import papyrus.channel.ChannelPropertiesMessage;
import papyrus.channel.node.AddChannelPoolRequest;
import papyrus.channel.node.ChannelPoolMessage;
import papyrus.channel.node.ChannelStatusRequest;
import papyrus.channel.node.ChannelStatusResponse;
import papyrus.channel.node.RegisterTransfersRequest;
import papyrus.channel.node.RemoveChannelPoolRequest;
import papyrus.channel.node.UnlockTransferRequest;
import tub.ods.pch.channel.node.EthereumConfig;
import tub.ods.pch.channel.model.ChannelApiStub;
import tub.ods.pch.channel.model.ChannelContract;
import tub.ods.pch.channel.model.ChannelManagerContract;
import tub.ods.pch.channel.model.SCHToken;
import tub.ods.common.data.model.SignedTransfer;
import tub.ods.common.data.model.SignedTransferUnlock;
import tub.ods.pch.channel.PeerConnection;
import tub.ods.pch.channel.state.IncomingChannelState;
import tub.ods.pch.channel.node.ContractsManager;
import tub.ods.pch.channel.node.ContractsManagerFactory;
import tub.ods.pch.channel.node.EthereumService;
import tub.ods.pch.channel.node.ThreadsafeTransactionManager;
import tub.ods.pch.channel.node.TokenConvert;

public class EtherTransferTest extends BaseChannelTest {
	
	@Autowired
	Web3j web3j;
	// Credentials credentials;
	IncomingChannelState channelState;
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
	
	public static void main(String[] args) throws Exception {
		new EtherTransferTest().testChannel();
	}
	
//    @Test
    public void testChannel() throws InterruptedException, ExecutionException, SignatureException {
        //add participant - this will initiate channels opening

        BigDecimal deposit = new BigDecimal("0.01");

        IncomingChannelState channelState = createPool(ChannelPoolMessage.newBuilder()
            .setDeposit(deposit.toString())
            .setCloseBlocksCount(10)
            .build()
        );

        BigDecimal transferSum = new BigDecimal("0.0001");

        SignedTransfer transfer = sendTransfer("1", channelState, transferSum);
/*
        //check for double sending
        Util.assertNoError(senderClient.getOutgoingChannelClient().registerTransfers(RegisterTransfersRequest.newBuilder()
            .addTransfer(transfer.toMessage())
            .build()
        ).getError());
*/
        Thread.sleep(100);

//        Util.assertEquals(transferSum, TokenConvert.fromWei(channelState.getSenderState().getCompletedTransfers()));

        BigDecimal transferSum2 = new BigDecimal("0.0002");

        SignedTransfer transfer2 = sendTransfer("2", channelState, transferSum2);
/*
        Util.assertNoError(receiverClient.getIncomingChannelClient().registerTransfers(RegisterTransfersRequest.newBuilder()
            .addTransfer(transfer.toMessage())
            .addTransfer(transfer2.toMessage())
            .build()
        ).getError());

        Util.waitFor(() -> channelState.getOwnState().getCompletedTransfers().signum() > 0);
*/
        closeAndSettleChannel(channelState, transferSum.add(transferSum2));
    }
/*
    private void auditChannel(IncomingChannelState state, Credentials auditorCredentials) throws InterruptedException, ExecutionException, IOException {
        sender.getBean(EthereumService.class).refill(new BigDecimal("0.1"), auditorCredentials.getAddress());
        ThreadsafeTransactionManager transactionManager = sender.getBean(EthereumConfig.class).createTransactionManager(auditorCredentials);
        ContractsManager contractManager = sender.getBean(ContractsManagerFactory.class).createManager(transactionManager, auditorCredentials);
        ChannelContract channelContract = contractManager.load(ChannelContract.class, state.getChannelAddress());
        Assert.assertEquals(new Address(auditorCredentials.getAddress()), channelContract.auditor().get());
        Assert.assertEquals(0, channelContract.audited().get().getValue().intValueExact());
        int closed = channelContract.closed().get().getValue().intValueExact();
        Assert.assertTrue(closed > 0);
        int auditTimeout = channelContract.auditTimeout().get().getValue().intValueExact();
        Assert.assertTrue(auditTimeout > 0);
        long blockNumber = sender.getBean(EthereumService.class).getBlockNumber();
        Assert.assertTrue(blockNumber <= closed + auditTimeout);

        TransactionReceipt receipt = contractManager.channelManager().auditReport(state.getChannelAddress(), new Uint256(10), new Uint256(5)).get();
        Address channelApi = contractManager.channelManager().channel_api().get();
        ChannelApiStub channelApiStub = contractManager.load(ChannelApiStub.class, channelApi);

        List<ChannelApiStub.ChannelAuditEventResponse> channelAuditEvents = channelApiStub.getChannelAuditEvents(receipt);
        Assert.assertEquals(1, channelAuditEvents.size());
        ChannelApiStub.ChannelAuditEventResponse response = channelAuditEvents.get(0);
        Assert.assertEquals(state.getSenderAddress(), response.from);
        Assert.assertEquals(state.getReceiverAddress(), response.to);
        Assert.assertEquals(5, response.fraudCount.getValue().intValueExact());
        Assert.assertEquals(10, response.impressionsCount.getValue().intValueExact());
    }

    private void destroyChannel(IncomingChannelState channelState) throws ExecutionException, InterruptedException, IOException {
        ContractsManager contractManager = sender.getBean(ContractsManagerFactory.class).getContractManager(channelState.getSenderAddress());
        Assert.assertTrue(contractManager.contractExists(channelState.getChannelAddress().toString()));
        TransactionReceipt receipt = contractManager.channelManager().destroyChannel(channelState.getChannelAddress()).get();
        List<ChannelManagerContract.ChannelDeletedEventResponse> events = contractManager.channelManager().getChannelDeletedEvents(receipt);
        Assert.assertEquals(1, events.size());
        Assert.assertFalse(contractManager.contractExists(channelState.getChannelAddress().toString()));
    }

    @Test
    public void testLockedTransfers() throws Exception {
        Credentials auditorCredentials = Credentials.create(Keys.createEcKeyPair());
        String auditorAddress = auditorCredentials.getAddress();

        BigDecimal deposit = new BigDecimal("0.01");

        IncomingChannelState channelState = createPool(ChannelPoolMessage.newBuilder()
            .setDeposit(deposit.toString())
            .setProperties(
                ChannelPropertiesMessage.newBuilder()
                    .setAuditorAddress(auditorAddress)
                    .build())
            .build()
        );

        BigDecimal transferSum = new BigDecimal("0.0003");

        SignedTransfer transfer = sendTransferLocked("1", channelState, transferSum);

        SignedTransferUnlock transferUnlock = unlockTransfer("1", channelState, auditorCredentials, transferSum);

        Util.assertNoError(receiverClient.getIncomingChannelClient().registerTransfers(RegisterTransfersRequest.newBuilder()
            .addTransfer(transfer.toMessage())
            .build()
        ).getError());
        Util.assertNoError(receiverClient.getIncomingChannelClient().unlockTransfer(UnlockTransferRequest.newBuilder()
            .addUnlock(transferUnlock.toMessage())
            .build()
        ).getError());

        Util.waitFor(() -> channelState.getOwnState().getCompletedTransfers().signum() > 0);

        closeAndSettleChannel(channelState, transferSum);
        
        auditChannel(channelState, auditorCredentials);
        
        //now channel may be destroyed
        destroyChannel(channelState);
    }
*/

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
/*
		Util.waitFor(() -> {
			try {
				return channelState.getChannel().getContract().settled().get().getValue().signum() > 0;
			} catch (Exception e) {
				throw Throwables.propagate(e);
			}
		});
*/
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
/*
		IncomingChannelState channelState = Util.waitFor(() -> {
			try {
				IncomingChannelManagers managers = receiver.getBean(IncomingChannelManagers.class);
				return managers.getManager(receiverAddress).getChannels(senderAddress);
			} catch (BeansException e) {
				throw Throwables.propagate(e);
			}
		}, ch -> ch.size() >= 1).iterator().next();
*/
		return channelState;
	}  
}
