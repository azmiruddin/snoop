package test;


import dev.insight.rdf4led.storage.BufferLayerAbstract;
import dev.insight.rdf4led.storage.block.BlockEntry;

import java.util.Comparator;


/**
 * Created by Anh Le Tuan
 * Email: anh.letuan@tu-berlin.de
 * Date: 17.03.19
 * <p>
 * Description:
 * <p>
 * TODO:
 */


public class BufferLayerTest extends BufferLayerAbstract<Integer> {

    protected static Comparator<BlockEntry<Integer, ?>> blockEntryComparator;



    @Override
    protected void sync() {

    }



    @Override
    protected Comparator<BlockEntry<Integer, ?>> getComparator() {
        if (blockEntryComparator == null) {
            blockEntryComparator = new Comparator<BlockEntry<Integer, ?>>() {
                @Override
                public int compare(BlockEntry<Integer, ?> o1, BlockEntry<Integer, ?> o2) {
                    int blk1 = o1.getBlockRecord();
                    int blk2 = o2.getBlockRecord();
                    return blk1 == blk2 ? 0 : (blk1 > blk2 ? 1 : -1);
                }
            };
        }
        return blockEntryComparator;
    }



    @Override
    protected boolean updateOne(BlockEntry<Integer, ?> blockEntry) {
        return false;
    }

    @Override
    public boolean init() {
        return false;
    }



    public static void main(String[] args){


    }





}
