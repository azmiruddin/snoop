package tub.ods.rdf4led.distributed.benchmark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.ETHBlockIndex;

import java.io.IOException;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 31.03.19
 * TODO Description:
 */
public class HttpBufferLayerService {

    private String endpoint;

    public HttpBufferLayerService(String endpoint) {
        this.endpoint = endpoint;
    }



    public DistributedBlockEntry<long[], ?>[] remoteRequest(long[] keyRequest) {
        String endpoint = this.endpoint + "requestBlockEntry/";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(keyRequest);
            ResponseEntity<String> response = httpPost(endpoint, message);

            DistributedBlockEntry<long[], ETHBlockIndex>[] blockEntries
                    = objectMapper.readValue(response.getBody(), DistributedBlockEntry[].class);

            return blockEntries;

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new DistributedBlockEntry[0];
    }



    public void update(DistributedBlockEntry<long[], ?> blockEntry) {
        String endpoint = this.endpoint + "/update";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String message = objectMapper.writeValueAsString(blockEntry);

            httpPost(endpoint, message);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


//
//    public void update(DistributedBlockEntry<long[], ?>[] blockEntries) {
//        String endpoint = this.endpoint + "updateBlockEntry/";
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String message = objectMapper.writeValueAsString(blockEntries);
//            ResponseEntity<String> response = httpPost(endpoint, message);
//            System.out.println(response);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }



    private ResponseEntity<String> httpPost(String endpoint, String message) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(message,httpHeaders);
        return  restTemplate.exchange(
                endpoint,
                HttpMethod.POST,
                entity,
                String.class);
    }
}
