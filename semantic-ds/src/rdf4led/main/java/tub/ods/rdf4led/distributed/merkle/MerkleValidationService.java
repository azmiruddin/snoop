package tub.ods.rdf4led.distributed.merkle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MerkleValidationService {

    public static void main(String[] args) {
        SpringApplication.run(MerkleValidationService.class, args);
    }

    @Bean
    RestTemplate rest() {
        return new RestTemplate();
    }

}
