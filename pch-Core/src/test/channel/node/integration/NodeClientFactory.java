package test.channel.node.integration;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import papyrus.channel.node.ChannelAdminGrpc;
import papyrus.channel.node.IncomingChannelClientGrpc;
import papyrus.channel.node.OutgoingChannelClientGrpc;
import papyrus.channel.node.config.ChannelServerProperties;
import papyrus.channel.node.server.peer.PeerConnection;
import papyrus.channel.protocol.ChannelPeerGrpc;

@Lazy
@Service
@EnableConfigurationProperties(ChannelServerProperties.class)
public class NodeClientFactory {
    private final PeerConnection peerConnection;

    @Autowired
    public NodeClientFactory(ChannelServerProperties serverProperties) throws URISyntaxException {
        this(serverProperties.getEndpointUrl());
    }

    public NodeClientFactory(String endpointUrl) throws URISyntaxException {
        peerConnection = new PeerConnection(new URI(endpointUrl));
    }

    public ManagedChannel getChannel() {
        return peerConnection.getChannel();
    }

    @Bean
    public PeerConnection getPeerConnection() {
        return peerConnection;
    }

    @Bean
    public ChannelAdminGrpc.ChannelAdminBlockingStub getClientAdmin() {
        return peerConnection.getClientAdmin();
    }

    @Bean
    public OutgoingChannelClientGrpc.OutgoingChannelClientBlockingStub getOutgoingChannelClient() {
        return peerConnection.getOutgoingChannelClient();
    }

    @Bean
    public IncomingChannelClientGrpc.IncomingChannelClientBlockingStub getIncomingChannelClient() {
        return peerConnection.getIncomingChannelClient();
    }

    @Bean
    public ChannelPeerGrpc.ChannelPeerBlockingStub getChannelPeer() {
        return peerConnection.getChannelPeer();
    }
}
