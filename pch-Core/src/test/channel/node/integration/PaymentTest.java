package test.channel.node.integration;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.SignatureException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.Keys;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import papyrus.channel.ChannelPropertiesMessage;
import papyrus.channel.node.ChannelPoolMessage;
import papyrus.channel.node.RegisterTransfersRequest;
import papyrus.channel.node.UnlockTransferRequest;
import papyrus.channel.node.config.EthereumConfig;
import papyrus.channel.node.contract.ChannelApiStub;
import papyrus.channel.node.contract.ChannelContract;
import papyrus.channel.node.contract.ChannelManagerContract;
import papyrus.channel.node.server.channel.SignedTransfer;
import papyrus.channel.node.server.channel.SignedTransferUnlock;
import papyrus.channel.node.server.channel.incoming.IncomingChannelState;
import papyrus.channel.node.server.ethereum.ContractsManager;
import papyrus.channel.node.server.ethereum.ContractsManagerFactory;
import papyrus.channel.node.server.ethereum.EthereumService;
import papyrus.channel.node.server.ethereum.ThreadsafeTransactionManager;
import papyrus.channel.node.server.ethereum.TokenConvert;

public class PaymentTest extends BaseChannelTest {
    @Test
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

        //check for double sending
        Util.assertNoError(senderClient.getOutgoingChannelClient().registerTransfers(RegisterTransfersRequest.newBuilder()
            .addTransfer(transfer.toMessage())
            .build()
        ).getError());

        Thread.sleep(100);

        Util.assertEquals(transferSum, TokenConvert.fromWei(channelState.getSenderState().getCompletedTransfers()));

        BigDecimal transferSum2 = new BigDecimal("0.0002");

        SignedTransfer transfer2 = sendTransfer("2", channelState, transferSum2);

        Util.assertNoError(receiverClient.getIncomingChannelClient().registerTransfers(RegisterTransfersRequest.newBuilder()
            .addTransfer(transfer.toMessage())
            .addTransfer(transfer2.toMessage())
            .build()
        ).getError());

        Util.waitFor(() -> channelState.getOwnState().getCompletedTransfers().signum() > 0);

        closeAndSettleChannel(channelState, transferSum.add(transferSum2));
    }

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
}
