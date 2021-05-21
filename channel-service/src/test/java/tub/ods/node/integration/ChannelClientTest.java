package tub.ods.node.integration;

import java.math.BigDecimal;
import java.security.SignatureException;

import org.junit.Assert;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Credentials;

import papyrus.channel.ChannelPropertiesMessage;
import papyrus.channel.node.AddChannelPoolRequest;
import papyrus.channel.node.ChannelPoolMessage;
import papyrus.channel.node.ChannelStatusRequest;
import papyrus.channel.node.ChannelStatusResponse;
import papyrus.channel.node.HealthCheckRequest;
import papyrus.channel.node.OutgoingChannelClientGrpc;
import papyrus.channel.node.RegisterTransfersRequest;
import papyrus.channel.node.RemoveChannelPoolRequest;
import tub.ods.pch.channel.SignedTransfer;

public class ChannelClientTest {
    public static final String SSP_ADDRESS = "0x9FEB109D29C8bAfa6CF205684B9d56b6e2eA6c04"; 
    public static final String DSP_ADDRESS = "0x3165811587992715A55996abbdc4F128C6C04BEc"; 
    public static final String DSP_CLIENT_ADDRESS = "0x0AFe0b64C384976905e6398d4eE1161B0aF80693";

    public static void main(String[] args) throws Exception {
    	
//    	Address Account 0x0AFe0b64C384976905e6398d4eE1161B0aF80693
        Credentials dspClientCredentials = Credentials.create("c310e0ac9782bcf4d52cb8a2c0ef4566e2d411d3f1f3b324163de00f30e347aa");
//        Assert.assertEquals(DSP_CLIENT_ADDRESS, dspClientCredentials.getAddress());
        
        NodeClientFactory dsp = new NodeClientFactory("grpc://localhost:8081");
        System.out.println("DSP uid: " + dsp.getClientAdmin().healthCheck(HealthCheckRequest.newBuilder().build()).getServerUid());

        NodeClientFactory ssp = new NodeClientFactory("grpc://localhost:8081");
        System.out.println("SSP uid: " + ssp.getClientAdmin().healthCheck(HealthCheckRequest.newBuilder().build()).getServerUid());

        System.out.printf("Opening channel %s->%s%n", DSP_ADDRESS, SSP_ADDRESS);

        AddChannelPoolRequest.Builder requestBuilder = AddChannelPoolRequest.newBuilder();
        ChannelPoolMessage.Builder builder = ChannelPoolMessage.newBuilder();
        builder.setSenderAddress(DSP_ADDRESS);
        builder.setReceiverAddress(SSP_ADDRESS);
        builder.setMinActiveChannels(1);
        builder.setMaxActiveChannels(1);
        builder.setDeposit("0.01");
        ChannelPropertiesMessage.Builder propertiesBuilder = builder.getPropertiesBuilder();
        propertiesBuilder.setCloseTimeout(1);
        propertiesBuilder.setSettleTimeout(6);

        requestBuilder.setPool(builder.build());
        dsp.getClientAdmin().addChannelPool(requestBuilder.build());

        ChannelStatusResponse response = Util.waitFor(() ->
                dsp.getOutgoingChannelClient().getChannels(
                    ChannelStatusRequest.newBuilder()
                        .setSenderAddress(DSP_ADDRESS)
                        .setReceiverAddress(SSP_ADDRESS)
                        .build()
                ),
            r -> !r.getChannelList().isEmpty()
        );

        String channelAddress = response.getChannel(0).getChannelAddress();
        System.out.printf("Channel address: %s%n", channelAddress);

        for (int i = 0; i < 10; i++) {
            sendTransfer("" + i, channelAddress, new BigDecimal("0.00001"), dspClientCredentials, dsp.getOutgoingChannelClient());
        }

        //Remove channel pool
        Util.assertNoError(
            dsp.getClientAdmin().removeChannelPool(
                RemoveChannelPoolRequest.newBuilder()
                    .setSenderAddress(DSP_ADDRESS)
                    .setReceiverAddress(SSP_ADDRESS)
                    .build()
            ).getError()
        );

        //Wait for close
        Util.waitFor(() ->
                dsp.getOutgoingChannelClient().getChannels(
                    ChannelStatusRequest.newBuilder()
                        .setSenderAddress(DSP_ADDRESS)
                        .setReceiverAddress(SSP_ADDRESS)
                        .build()
                ),
            r -> r.getChannelList().isEmpty()
        );
    }

    private static SignedTransfer sendTransfer(String transferId, String channelAddress, BigDecimal sum, Credentials clientCredentials, OutgoingChannelClientGrpc.OutgoingChannelClientBlockingStub channelClient) throws InterruptedException, SignatureException {
        SignedTransfer transfer = new SignedTransfer(transferId, channelAddress, sum.toString(), false);
        transfer.sign(clientCredentials.getEcKeyPair());
        Assert.assertEquals(new Address(clientCredentials.getAddress()), transfer.getSignerAddress());
        Assert.assertEquals(transfer, new SignedTransfer(transfer.toMessage()));

        Util.assertNoError(channelClient.registerTransfers(RegisterTransfersRequest.newBuilder()
            .addTransfer(transfer.toMessage())
            .build()
        ).getError());
        
        return transfer;
    }
}
