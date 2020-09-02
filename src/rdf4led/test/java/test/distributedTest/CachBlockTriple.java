package test.distributedTest;

import dev.insight.rdf4led.db.cache.CacheLRU;
import dev.insight.rdf4led.storage.block.BlockEntry;
import dev.insight.rdf4led.storage.cache.CacheBlock;
import test.BlockEntryTest;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockIndex;

import java.util.ArrayList;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 28.03.19
 * TODO Description:
 */
public class CachBlockTriple implements CacheBlock<BlockEntry<long[], DistributedBlockIndex>> {
    private int size = 6;

    private CacheLRU<Integer, BlockEntry<?, ?>> cacheLRU;

    private ArrayList<BlockEntry<?, ?>> waitingList;


    public CachBlockTriple() {
        cacheLRU = new CacheLRU<>(6);
        waitingList = new ArrayList<>();
    }



    @Override
    public boolean init() {
        return false;
    }



    @Override
    public boolean put(BlockEntry<long[], DistributedBlockIndex> blockEntry) {

        BlockEntry<?, ?> drop = cacheLRU.put(blockEntry.hashCode(), blockEntry);

        if (drop != null) {
            waitingList.add(drop);
        }
        return drop != null;
    }



    @Override
    public boolean put(BlockEntry<long[], DistributedBlockIndex>[] blockEntries) {
        for (BlockEntry<long[], DistributedBlockIndex> blockEntryTest : blockEntries) {
            put(blockEntryTest);
        }
        return false;
    }







    @Override
    public BlockEntry<long[], DistributedBlockIndex>[] validate() {
        if (waitingList.size() == 0) {
            BlockEntry<long[], DistributedBlockIndex>[] a;
            a = new BlockEntry[0];
            return a;
        } else {
            BlockEntry<long[], DistributedBlockIndex>[] blockEntries = waitingList.toArray(new BlockEntry[waitingList.size()]);
            waitingList.clear();
            return blockEntries;
        }
    }
}
