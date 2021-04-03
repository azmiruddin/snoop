package tub.ods.pch.channel.node;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("eth")
public class EthProperties {
    private EthRpcProperties rpc = new EthRpcProperties();
    private Map<String, EthKeyProperties> accounts = new LinkedHashMap<>();
    private boolean sync;

    public EthRpcProperties getRpc() {
        return rpc;
    }

    public void setRpc(EthRpcProperties rpc) {
        this.rpc = rpc;
    }

    public Map<String, EthKeyProperties> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, EthKeyProperties> accounts) {
        this.accounts = accounts;
    }

    public boolean isSync() {
        return sync;
    }

    public void setSync(boolean sync) {
        this.sync = sync;
    }
}
