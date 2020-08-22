package test;

import dev.insight.rdf4led.storage.block.BlockEntry;
import dev.insight.rdf4led.storage.cache.CacheBlock;
import dev.insight.rdf4led.db.cache.CacheLRU;

import java.nio.ByteBuffer;
import java.util.ArrayList;


public class CacheBlockTest implements CacheBlock<BlockEntry<?, ?>> {

    private int size = 6;

    private CacheLRU<Integer, BlockEntry<?, ?>> cacheLRU;

    private ArrayList<BlockEntry<?, ?>> waitingList;



    public CacheBlockTest() {
        cacheLRU = new CacheLRU<>(6);
        waitingList = new ArrayList<>();
    }



    @Override
    public boolean init() {
        return false;
    }



    @Override
    public boolean put(BlockEntry<?, ?> blockEntry) {
        BlockEntry<?, ?> drop = cacheLRU.put(blockEntry.hashCode(), blockEntry);

        if (drop != null) {
            waitingList.add(drop);
        }
        return drop != null;
    }



    @Override
    public boolean put(BlockEntry<?, ?>[] blockEntries) {
        for (BlockEntry<?, ?> blockEntryTest : blockEntries) {
            put(blockEntryTest);
        }

        return false;
    }



    @Override
    public BlockEntry<Integer, Integer>[] validate() {
        //TODO validate cache
        if (waitingList.size() == 0) {
            return new BlockEntryTest[0];
        } else {
            BlockEntry<Integer, Integer>[] blockEntries = waitingList.toArray(new BlockEntry[waitingList.size()]);
            waitingList.clear();
            return blockEntries;
        }
    }



    /**
     * Created by Anh Le-Tuan
     * Email: anh.letuan@tu-berlin.de
     * <p>
     * Date: 17.04.19
     */
    public static class BufferLongTest {

        public static void main(String[] args) {

            byte[] bytes  = null;
            ByteBuffer byteBuffer = ByteBuffer.allocate(8 * 3 * 10000);

            for (long i = 0; i < 3000; i++) {
                byteBuffer.putLong(i);
            }

            for (int i = 0; i < 1000; i++){
                long a = byteBuffer.getLong(i * 24);
                long b = byteBuffer.getLong(i * 24 + 8);
                long c = byteBuffer.getLong(i * 24 + 16);

                System.out.println(a + " " + b + " " + c );
            }


    //        bytes = byteBuffer.array();
           // ArrayUtil.println(bytes);
        }

    }

}
