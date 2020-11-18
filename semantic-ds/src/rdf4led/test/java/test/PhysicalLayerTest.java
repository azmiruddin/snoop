package test;

import dev.insight.rdf4led.storage.block.Block;
import dev.insight.rdf4led.storage.block.BlockEntry;
import dev.insight.rdf4led.storage.PhysicalLayerAbstract;

/**
 * Created by Anh Le Tuan
 * Email: anh.letuan@tu-berlin.de
 * Date: 17.03.19
 * <p>
 * Description:
 * <p>
 * TODO:
 */


public class PhysicalLayerTest extends PhysicalLayerAbstract<Integer, Integer> {

    private Block<Integer>[] buff;
    private int count;
    public PhysicalLayerTest(){
        buff = new BlockTest[100];
        count = 0;
    }

    @Override
    protected int getRecordSize() {
        return 1;
    }



    @Override
    public void init() {

    }



    @Override
    public BlockEntry<Integer, Integer> writeBlock(BlockEntry<Integer, Integer> blockEntry) {
        return null;
    }



    @Override
    protected BlockEntry<Integer, Integer> writeOne(BlockEntry<Integer, Integer> blockEntry) {
        return null;
    }



    @Override
    public BlockEntry<Integer, Integer> writeBlocks(BlockEntry<Integer, Integer>[] blockEntries) {
        for (BlockEntry<Integer, Integer> blkEnrty:blockEntries){
            buff[blkEnrty.getBlockIndex()] = blkEnrty.getBlock();
            blkEnrty.setBlock(null);
        }
        return null;
    }



    @Override
    protected BlockEntry<Integer, Integer> writeMany(BlockEntry<Integer, Integer>[] blockEntries) {
        return null;
    }



    @Override
    public Block<Integer> readBlock(BlockEntry<Integer, Integer> blockEntry) {
        //TODO need to create abstract function
        int blkId = blockEntry.getBlockIndex();

        if (buff[blkId] == null) {
            throw new IllegalStateException();
        }

        blockEntry.setBlock(buff[blkId]);

        return buff[blkId];
    }



    @Override
    public BlockEntry<Integer, Integer>[] split(BlockEntry<Integer, Integer> blockEntry) {

        Block<Integer>[] blocks = blockEntry.getBlock().split(1);

        BlockEntry[] blockEntries = new BlockEntry[blocks.length];

        for (int i = 0; i<blocks.length; i++){
                BlockEntryTest entry = new BlockEntryTest(++count, blocks[i]);
                blockEntries[i] = entry;
        }
        count = count + blocks.length - 1;

        return blockEntries;
    }



    @Override
    public String toString(){
        String toString = "Physical Layer: ";
        for (Block<Integer> block:buff){
            if (block != null)
            toString = toString + "[" + block.toString() + "]";
        }
        return toString;
    };
}
