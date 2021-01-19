package tub.ods.rdf4led.distributed.index;

import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockIndex;
import tub.ods.rdf4led.distributed.storage.block.PrivateDistributedBlockIndex;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 09.04.19
 */
public abstract class DistributedBufferLayerSecure<R> implements  DistributedBufferLayer<R>{


    @Override
    public abstract DistributedBlockEntry<R, ?>[] remoteRequest(R keyRecord);



    @Override
    public void updateRemote(DistributedBlockEntry<R, ?>[] blockEntries) {
        for (DistributedBlockEntry blockEntry:blockEntries){
            updateRemote(blockEntry);
        }
    }
    
    @Override
    public void updateRemote(DistributedBlockEntry<R, ?> blockEntry,byte[] valueRedis) {
        DistributedBlockIndex blockIndex = blockEntry.getBlockIndex();

        if (!(blockIndex instanceof PrivateDistributedBlockIndex)){
            throw new IllegalArgumentException("Block Index must be private");
        }

        updateSecure(blockEntry,valueRedis);
    }


    protected abstract boolean updateSecure(DistributedBlockEntry<R, ?> prBlockEntry,byte[] valueRedis);
}
