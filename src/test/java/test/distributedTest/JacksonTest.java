package test.distributedTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import tub.ods.rdf4led.distributed.storage.block.*;

import java.io.IOException;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 30.03.19
 * TODO Description:
 */
public class JacksonTest {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        //Test IPFS Block Entry
        IPFSBlockIndex ipfsBlockIndex = new IPFSBlockIndex();
        ipfsBlockIndex.setIPFSHash("abcdef");

        IPFSBlockEntry ipfsBlockEntry = new IPFSBlockEntry();
        ipfsBlockEntry.setBlockRecord(new long[]{1, 2, 3});
        ipfsBlockEntry.setBlockIndex(ipfsBlockIndex);

        try {
            String message = mapper.writeValueAsString(ipfsBlockEntry);

            System.out.println(message);

            IPFSBlockEntry blockEntry1 = mapper.readValue(message, IPFSBlockEntry.class);

            System.out.println(blockEntry1);

        } catch (IOException e) {
            e.printStackTrace();
        }

        ETHBlockIndex ethBlockIndex = new ETHBlockIndex();
        ethBlockIndex.setClientID("abcdef");

        ETHBlockEntry ethBlockEntry = new ETHBlockEntry();
        ethBlockEntry.setBlockRecord(new long[]{1, 2, 3});
        ethBlockEntry.setBlockIndex(ethBlockIndex);

        try {
            String message = mapper.writeValueAsString(ethBlockEntry);

            System.out.println(message);

            ETHBlockEntry blockEntry1 = mapper.readValue(message, ETHBlockEntry.class);

            System.out.println(blockEntry1);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
