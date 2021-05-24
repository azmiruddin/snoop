/*
 * Copyright 2015, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *
 *    * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package tub.ods.pch.channel.node;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.util.List;

import javax.net.ssl.SSLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.ECKeyPair;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyServerBuilder;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.util.internal.NativeLibraryLoader;
import tub.ods.pch.channel.util.ChannelServerProperties;
import tub.ods.pch.channel.node.EthereumConfig;
import tub.ods.pch.channel.node.ContractsManager;
import tub.ods.pch.channel.node.ContractsManagerFactory;
import tub.ods.pch.channel.util.CryptoUtil;
import tub.ods.pch.channel.EndpointRegistry;
import tub.ods.pch.channel.model.EndpointRegistryContract;

@Service
@EnableConfigurationProperties(ChannelServerProperties.class)
public class NodeServer {
    private static final Logger log = LoggerFactory.getLogger(NodeServer.class);
    private final List<BindableService> bindableServices;

    private Server grpcServer;
    private ServerSocket healthCheckSocket;

    private ChannelServerProperties properties;
    private final EthereumConfig ethereumConfig;
    private final ContractsManagerFactory factory;
    private Thread healthChecker;

    public NodeServer(List<BindableService> bindableServices, ChannelServerProperties properties, EthereumConfig ethereumConfig, ContractsManagerFactory factory) {
        this.bindableServices = bindableServices;
        this.properties = properties;
        this.ethereumConfig = ethereumConfig;
        this.factory = factory;
    }

    @EventListener(ContextStartedEvent.class)
    public void start() throws Exception {
        int port = properties.getPort();
        NettyServerBuilder builder = NettyServerBuilder.forPort(port);
        ((Iterable<BindableService>) bindableServices).forEach(builder::addService);
        if (properties.isSecure()) {
            configureSsl(builder);
        }
        grpcServer = builder.build();

        try {
            grpcServer.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        if (properties.getHealthCheckPort() > 0) {
            log.info("Starting health check at port {}", properties.getHealthCheckPort());
            healthCheckSocket = new ServerSocket(properties.getHealthCheckPort());
            healthChecker = new Thread(this::healthCheck, "Health Checker");
            healthChecker.setDaemon(true);
            healthChecker.start();
        }

        log.info("Server started, GRPC API listening on {}", grpcServer.getPort());

        String endpointUrl = properties.getEndpointUrl();
        if (endpointUrl == null) {
            log.warn("No endpoint url provided");
        } else {
            for (Address address : ethereumConfig.getAddresses()) {
                ContractsManager contractManager = factory.getContractManager(address);
                EndpointRegistry registry = new EndpointRegistry(contractManager.endpointRegistry());
                registry.registerEndpoint(address, endpointUrl);
            }
        }
    }

    private void healthCheck() {
        while (!Thread.interrupted() && !healthCheckSocket.isClosed()) {
            try {
                Socket socket = healthCheckSocket.accept();
                socket.getOutputStream().write("OK".getBytes());
                socket.close();
            } catch (IOException e) {
                if (Thread.interrupted()) return;
                log.debug("Health check socket error", e);
            }
        }
    }

    private void configureSsl(NettyServerBuilder builder) throws NoSuchAlgorithmException, CertificateEncodingException, NoSuchProviderException, InvalidKeyException, SignatureException, SSLException {
        NativeLibraryLoader.loadFirstAvailable(ClassLoader.getSystemClassLoader(),
            "netty_tcnative_osx_x86_64",
            "netty_tcnative_linux_x86_64",
            "netty_tcnative_windows_x86_64"
        );
        ECKeyPair ecKeyPair = ethereumConfig.getMainCredentials().getEcKeyPair();
        KeyPair keyPair = CryptoUtil.decodeKeyPair(ecKeyPair);
        SslContextBuilder contextBuilder = SslContextBuilder.forServer(
            keyPair.getPrivate(),
            CryptoUtil.genCert(keyPair)
        );

        builder.sslContext(GrpcSslContexts.configure(contextBuilder).build());
    }

    @EventListener(ContextStoppedEvent.class)
    public void stop() throws InterruptedException {
        if (healthCheckSocket != null) {
            try {
                healthCheckSocket.close();
                healthCheckSocket = null;
            } catch (Exception e) {
                log.warn("Failed to close socket", e);
            }
        }
        if (healthChecker != null) {
            healthChecker.interrupt();
            healthChecker.join();
            healthChecker = null;
        }
        grpcServer.shutdown();
    }

    public void awaitTermination() throws InterruptedException {
        if (grpcServer != null) {
            grpcServer.awaitTermination();
        }
    }
}
