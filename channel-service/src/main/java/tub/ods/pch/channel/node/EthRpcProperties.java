package tub.ods.pch.channel.node;

import java.math.BigInteger;
import java.time.Duration;

public class EthRpcProperties {
    
    private String nodeUrl;
    private BigInteger gasPrice;
    private BigInteger gasLimit;
    private int attempts = 40;
    private Duration sleep = Duration.ofSeconds(5);

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public BigInteger getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(BigInteger gasPrice) {
        this.gasPrice = gasPrice;
    }

    public BigInteger getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(BigInteger gasLimit) {
        this.gasLimit = gasLimit;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Duration getSleep() {
        return sleep;
    }

    public void setSleep(Duration sleep) {
        this.sleep = sleep;
    }
}
