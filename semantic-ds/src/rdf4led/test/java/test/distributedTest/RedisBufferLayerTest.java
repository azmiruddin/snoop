package test.distributedTest;

import tub.ods.rdf4led.distributed.index.RedisBufferLayer;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.ETHBlockIndex;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 04.04.19
 */
public class RedisBufferLayerTest {

    public static void main(String[] args){

        RedisBufferLayer redisBufferLayer = new RedisBufferLayer("spo","localhost", 6379);


//        for (long i = 1; i<10; i ++){
//            for (long j = 1; j<10; j++){
//                DistributedBlockEntry<long[], PrivateDistributedBlockIndex> ethBlockEntry = new ETHBlockEntry();
//                              ethBlockEntry.setBlockRecord(new long[]{i,j,0});
//                ETHBlockIndex ethBlockIndex = new ETHBlockIndex();
//                              ethBlockIndex.setClientID("client" + i + j);
//                              ethBlockEntry.setBlockIndex(ethBlockIndex);
//
//                redisBufferLayer.update(ethBlockEntry);
//            }
//        }


        DistributedBlockEntry[] blockEntries = redisBufferLayer.remoteRequest(new long[]{1, 0, 0});

        int i = 0;
        for (DistributedBlockEntry blockEntry:blockEntries){
            System.out.println(blockEntry.toString());
            i++;
        }

        System.out.println(i);
    }
}
