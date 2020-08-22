package tub.ods.rdf4led.distributed.benchmark;

import com.fasterxml.jackson.databind.ObjectMapper;
import tub.ods.rdf4led.distributed.index.IPFSPhysicalLayer;
import tub.ods.rdf4led.distributed.storage.BlockTripleLongBuffer;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 17.04.19
 */
public class IPFSTest {

    public static void main(String[] args){
        //TODO change IPFS Address
        String IPFSAdress = "/ip4/127.0.0.1/tcp/5001";
        //The querier is using HTTP service
        //Turn the HTTP service ... measure time to read from IPFS when receiving ipfs has.
        String endpoint  = "http://192.168.1.155:3000";

        IPFSPhysicalLayer ipfsPhysicalLayer = new IPFSPhysicalLayer(IPFSAdress);
        HttpBufferLayerService httpBufferLayerService = new HttpBufferLayerService(endpoint);

        long numberOfTriple = 100000000; // 100 millions.
        long startIndex = Integer.parseInt(args[0]);; //Integer.parseInt(args[0]); // <- read from argument
        long endIndex = startIndex + numberOfTriple;

        ObjectMapper objectMapper = new ObjectMapper();
        BlockTripleLongBuffer blockTripleLongBuffer = new BlockTripleLongBuffer();

        for (long index=startIndex; index<endIndex; index++){
            long[] triple = new long[]{index * 3, index * 3  + 1 , index * 3 + 2};
            blockTripleLongBuffer.add(triple);


            if (blockTripleLongBuffer.isFull()){

                DistributedBlockEntry<long[], OpenDistributedBlockIndex> oBlockEntry;
                long startTime = System.currentTimeMillis();
                oBlockEntry = ipfsPhysicalLayer.commit(blockTripleLongBuffer);
                long endTime = System.currentTimeMillis();
                long diff = endTime - startTime;
                System.out.println(diff);
                //PACK start time and end time then send to HTTP SERVICE
                //See HTTPBufferLayerSerivce class...
                oBlockEntry.setBlockRecord(new long[]{startTime, endTime});
                httpBufferLayerService.update(oBlockEntry);
                blockTripleLongBuffer.reset();
            }
        }
    }
}
