package test.distributedTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.insight.rdf4led.storage.block.Block;
import dev.insight.rdf4led.storage.block.BlockEntry;
import tub.ods.rdf4led.distributed.index.DistributedPhysicalLayer;
import tub.ods.rdf4led.distributed.index.IPFSPhysicalLayer;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.IPFSBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.IPFSBlockIndex;
import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 01.04.19
 * TODO Description:
 */
public class IPFSPhysicalLayerTest {

    private static String IPFSAdress = "/ip4/127.0.0.1/tcp/5001";

    public static void main(String[] args){
      write();

    }

    private static void write(){
        DistributedPhysicalLayer<long[], OpenDistributedBlockIndex> physicalLayer = new IPFSPhysicalLayer(IPFSAdress);
        Block<long[]> block = new BlockTestLong();
        block.add(new long[]{1,2,3});
        block.add(new long[]{2,2,4});
        block.add(new long[]{3,2,3});
        block.add(new long[]{3,2,4});

        DistributedBlockEntry<long[], OpenDistributedBlockIndex>  blockEntry = new IPFSBlockEntry();
        blockEntry.setBlockRecord(new long[]{1,2,3});
        blockEntry.setBlock(block);

        BlockEntry<long[], OpenDistributedBlockIndex>  ipfsBlockEntry = null;

        ObjectMapper mapper = new ObjectMapper();
        try {
            String message = mapper.writeValueAsString(ipfsBlockEntry);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
