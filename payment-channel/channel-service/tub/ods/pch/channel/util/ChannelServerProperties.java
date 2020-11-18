package tub.ods.pch.channel.util;

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
