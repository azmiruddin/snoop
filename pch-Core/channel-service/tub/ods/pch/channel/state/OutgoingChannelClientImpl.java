package tub.ods.pch.channel.state;

import java.security.SignatureException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;

import io.grpc.stub.StreamObserver;
import papyrus.channel.node.ChannelStatusMessage;
import papyrus.channel.node.ChannelStatusRequest;
import papyrus.channel.node.ChannelStatusResponse;
import papyrus.channel.node.OutgoingChannelClientGrpc;
import papyrus.channel.node.RegisterTransfersRequest;
import papyrus.channel.node.RegisterTransfersResponse;
import papyrus.channel.node.TransferMessage;
import papyrus.channel.node.UnlockTransferMessage;
import papyrus.channel.node.UnlockTransferRequest;
import papyrus.channel.node.UnlockTransferResponse;
import tub.ods.common.data.model.SignedTransfer;
import tub.ods.common.data.model.SignedTransferUnlock;

@Component
public class OutgoingChannelClientImpl extends OutgoingChannelClientGrpc.OutgoingChannelClientImplBase {
    private static final Logger log = LoggerFactory.getLogger(OutgoingChannelClientImpl.class);
    
    private OutgoingChannelPoolManager manager;

    public OutgoingChannelClientImpl(OutgoingChannelPoolManager manager) {
        this.manager = manager;
    }

    @Override
    public void getChannels(ChannelStatusRequest request, StreamObserver<ChannelStatusResponse> responseObserver) {
        ChannelStatusResponse.Builder builder = ChannelStatusResponse.newBuilder();
        for (OutgoingChannelState state : manager.getChannels(new Address(request.getSenderAddress()), new Address(request.getReceiverAddress()))) {
            if (state.isActive()) {
                builder.addChannel(ChannelStatusMessage.newBuilder()
                    .setActive(true)
                    .setChannelAddress(state.getChannelAddress().toString())
                    .setProperties(
                        state.getChannel().getProperties().toMessage()
                    )
                    .build()
                );
            }
        }
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void registerTransfers(RegisterTransfersRequest request, StreamObserver<RegisterTransfersResponse> responseObserver) {
        for (TransferMessage transferMessage : request.getTransferList()) {
            try {
                SignedTransfer signedTransfer = new SignedTransfer(transferMessage);
                manager.registerTransfer(signedTransfer);
            } catch (IllegalArgumentException | SignatureException e) {
                log.warn("Invalid transfer", e);
            }
        }
        responseObserver.onNext(RegisterTransfersResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void unlockTransfer(UnlockTransferRequest request, StreamObserver<UnlockTransferResponse> responseObserver) {
        for (UnlockTransferMessage unlockTransferMessage : request.getUnlockList()) {
            try {
                SignedTransferUnlock signedTransferUnlock = new SignedTransferUnlock();
                manager.registerTransferUnlock(signedTransferUnlock);
            } catch (Exception e) {
                log.warn("Invalid transfer", e);
            }
        }
        responseObserver.onNext(UnlockTransferResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}
