package tub.ods.pch.channel;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import papyrus.channel.node.AddChannelPoolRequest;
import papyrus.channel.node.AddChannelPoolResponse;
import papyrus.channel.node.ChannelAdminGrpc;
import papyrus.channel.node.ChannelPoolMessage;
import papyrus.channel.node.CloseChannelRequest;
import papyrus.channel.node.CloseChannelResponse;
import papyrus.channel.node.GetChannelPoolsRequest;
import papyrus.channel.node.GetChannelPoolsResponse;
import papyrus.channel.node.HealthCheckRequest;
import papyrus.channel.node.HealthCheckResponse;
import papyrus.channel.node.RemoveChannelPoolRequest;
import papyrus.channel.node.RemoveChannelPoolResponse;

import tub.ods.pch.channel.state.IncomingChannelManagers;
import tub.ods.pch.channel.ChannelPoolProperties;
import tub.ods.pch.channel.state.OutgoingChannelPoolManager;
import tub.ods.pch.channel.node.TokenConvert;
import tub.ods.pch.channel.node.ServerIdService;

//TODO client authentication
@Component
public class ChannelAdminImpl extends ChannelAdminGrpc.ChannelAdminImplBase {
    private static final Logger log = LoggerFactory.getLogger(ChannelAdminImpl.class);
    public static final int MAX_CHANNELS_PER_ADDRESS = 100;
    
    private OutgoingChannelPoolManager outgoingChannelPoolManager;
    private IncomingChannelManagers incomingChannelManagers;
    private final ServerIdService nodeServer;

    public ChannelAdminImpl(
        OutgoingChannelPoolManager outgoingChannelPoolManager, 
        IncomingChannelManagers incomingChannelManagers,
        ServerIdService serverIdService
    ) {
        this.outgoingChannelPoolManager = outgoingChannelPoolManager;
        this.incomingChannelManagers = incomingChannelManagers;
        this.nodeServer = serverIdService;
    }

    @Override
    public void addChannelPool(AddChannelPoolRequest poolRequest, StreamObserver<AddChannelPoolResponse> responseObserver) {
        ChannelPoolMessage pool = poolRequest.getPool();
        if (pool.getMinActiveChannels() < 0 || pool.getMinActiveChannels() > MAX_CHANNELS_PER_ADDRESS) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(String.format("Illegal min active channels: %d", pool.getMinActiveChannels())).asException());
            return;
        }
        if (pool.getMaxActiveChannels() < 0 || pool.getMaxActiveChannels() > MAX_CHANNELS_PER_ADDRESS) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(String.format("Illegal min active channels: %d", pool.getMaxActiveChannels())).asException());
            return;
        }
        
        ChannelPoolProperties properties = new ChannelPoolProperties(pool);
        
        if (properties.getBlockchainProperties().getSettleTimeout() < 6) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(String.format("Illegal settle timeout: %d", properties.getBlockchainProperties().getSettleTimeout())).asException());
            return;
        }
        
        if (properties.getPolicy().getDeposit().signum() <= 0) {
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(String.format("Illegal deposit: %d", properties.getPolicy().getDeposit())).asException());
            return;
        }
        
        outgoingChannelPoolManager.addPool(new Address(pool.getSenderAddress()), new Address(pool.getReceiverAddress()), properties);
        
        responseObserver.onNext(AddChannelPoolResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getChannelPools(GetChannelPoolsRequest request, StreamObserver<GetChannelPoolsResponse> responseObserver) {
        Address senderAddress = new Address(request.getSenderAddress());
        Address receiverAddress = !request.getReceiverAddress().isEmpty() ? new Address(request.getReceiverAddress()) : null;
        Collection<ChannelPoolProperties> pools = outgoingChannelPoolManager.getPools(senderAddress, receiverAddress);
        GetChannelPoolsResponse.Builder builder = GetChannelPoolsResponse.newBuilder();
        for (ChannelPoolProperties properties : pools) {
            builder.addPool(ChannelPoolMessage.newBuilder()
                .setDeposit(TokenConvert.fromWei(properties.getPolicy().getDeposit()).toString())
                .setMinActiveChannels(properties.getMinActiveChannels())
                .setMaxActiveChannels(properties.getMaxActiveChannels())
                .setProperties(properties.getBlockchainProperties().toMessage())
                .setReceiverAddress(properties.getReceiver().toString())
                .setSenderAddress(properties.getSender().toString())
                .build());
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void removeChannelPool(RemoveChannelPoolRequest request, StreamObserver<RemoveChannelPoolResponse> responseObserver) {
        outgoingChannelPoolManager.removePool(new Address(request.getSenderAddress()), new Address(request.getReceiverAddress()));
        responseObserver.onNext(RemoveChannelPoolResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void requestCloseChannel(CloseChannelRequest request, StreamObserver<CloseChannelResponse> responseObserver) {
        incomingChannelManagers.requestCloseChannel(new Address(request.getChannelAddress()));
        outgoingChannelPoolManager.requestCloseChannel(new Address(request.getChannelAddress()));
        responseObserver.onNext(CloseChannelResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void healthCheck(HealthCheckRequest request, StreamObserver<HealthCheckResponse> responseObserver) {
        responseObserver.onNext(HealthCheckResponse.newBuilder()
            .setServerUid(nodeServer.getRunId().toString())
            .build());
        responseObserver.onCompleted();
    }
}
