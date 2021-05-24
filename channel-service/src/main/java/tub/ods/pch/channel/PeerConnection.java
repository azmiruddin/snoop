package tub.ods.pch.channel;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import papyrus.channel.node.ChannelAdminGrpc;
import papyrus.channel.node.IncomingChannelClientGrpc;
import papyrus.channel.node.OutgoingChannelClientGrpc;
import papyrus.channel.protocol.ChannelPeerGrpc;

public class PeerConnection implements Closeable {
    private final ManagedChannel channel;
    private final ChannelAdminGrpc.ChannelAdminBlockingStub clientAdmin;
    private final OutgoingChannelClientGrpc.OutgoingChannelClientBlockingStub outgoingChannelClient;
    private final IncomingChannelClientGrpc.IncomingChannelClientBlockingStub incomingChannelClient;
    private final ChannelPeerGrpc.ChannelPeerBlockingStub channelPeer;

    public PeerConnection(URI nodeUrl) {
        Protocol protocol = Protocol.valueOf(nodeUrl.getScheme());
        channel = NettyChannelBuilder
            .forAddress(nodeUrl.getHost(), nodeUrl.getPort())
            .usePlaintext(protocol == Protocol.grpc)
            .build();
        clientAdmin = ChannelAdminGrpc.newBlockingStub(channel);
        outgoingChannelClient = OutgoingChannelClientGrpc.newBlockingStub(channel);
        incomingChannelClient = IncomingChannelClientGrpc.newBlockingStub(channel);
        channelPeer = ChannelPeerGrpc.newBlockingStub(channel);
    }

    public ManagedChannel getChannel() {
        return channel;
    }

    public ChannelAdminGrpc.ChannelAdminBlockingStub getClientAdmin() {
        return clientAdmin;
    }

    public OutgoingChannelClientGrpc.OutgoingChannelClientBlockingStub getOutgoingChannelClient() {
        return outgoingChannelClient;
    }

    public IncomingChannelClientGrpc.IncomingChannelClientBlockingStub getIncomingChannelClient() {
        return incomingChannelClient;
    }

    public ChannelPeerGrpc.ChannelPeerBlockingStub getChannelPeer() {
        return channelPeer;
    }

    @Override
    public void close() throws IOException {
        channel.shutdown();
    }
    
    private enum Protocol {
        grpc, grpcs
    }
}
