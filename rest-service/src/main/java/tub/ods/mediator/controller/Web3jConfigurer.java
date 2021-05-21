package tub.ods.mediator.controller;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@EnableConfigurationProperties({EthProperties.class})
@Configuration
public class Web3jConfigurer {
    @Bean
    public Web3j getWeb3j(EthProperties properties) {
        return Web3j.build(new HttpService(properties.getRpc().getNodeUrl()));
    }
}
