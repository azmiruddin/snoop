package tub.ods.pch.channel.node;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("channel.server")
public class ChannelServerProperties {
    private int port;
    private int healthCheckPort;
    private boolean secure;
    private String endpointUrl;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getHealthCheckPort() {
        return healthCheckPort;
    }

    public void setHealthCheckPort(int healthCheckPort) {
        this.healthCheckPort = healthCheckPort;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }
}
