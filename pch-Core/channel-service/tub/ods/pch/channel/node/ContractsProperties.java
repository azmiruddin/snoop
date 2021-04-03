package tub.ods.pch.channel.node;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.web3j.abi.datatypes.Address;

@ConfigurationProperties("contract")
public class ContractsProperties {
    private Map<String, Address> address = new HashMap<>();

    public Map<String, Address> getAddress() {
        return address;
    }

    public void setAddress(Map<String, Address> address) {
        this.address = address;
    }
}
