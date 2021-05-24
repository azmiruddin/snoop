package tub.ods.pch.channel;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.web3j.abi.datatypes.Address;

import com.google.common.base.Preconditions;

@Component
public class PeerConnectionManager {
    private EndpointRegistry endpointRegistry;
    private Map<Address, PeerConnection> connections = new ConcurrentHashMap<>();

    public PeerConnectionManager(EndpointRegistry endpointRegistry) {
        this.endpointRegistry = endpointRegistry;
    }
    
    public PeerConnection getConnection(Address address) {
        return connections.computeIfAbsent(address, this::open);
    }

    private PeerConnection open(Address address) {
        String endpoint = endpointRegistry.lookupEndpoint(address).orElseThrow(() -> new IllegalStateException("Endpoint not registered for " + address));
        try {
            URI uri = new URI(endpoint);
            Preconditions.checkState("grpc".equals(uri.getScheme()), "Scheme not supported: %s", uri);
            return new PeerConnection(uri);
        } catch (URISyntaxException e) {
            throw new IllegalStateException("Failed to parse endpoint", e);
        }
    }
}
