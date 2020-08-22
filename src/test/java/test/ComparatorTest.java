package test;

import dev.insight.rdf4led.storage.block.BlockEntry;

import java.util.Comparator;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 25.03.19
 * TODO Description:
 */
public class ComparatorTest {
    public static void main(String[] args){


        Comparator<BlockEntry<Integer, ?>> blockEntryComparator = new Comparator<BlockEntry<Integer, ?>>() {
            @Override
            public int compare(BlockEntry<Integer, ?> o1, BlockEntry<Integer, ?> o2) {
                return o1.getBlockRecord() - o2.getBlockRecord();
            }
        };

        BlockEntryTest blkEntryTest1 = new BlockEntryTest(0, new BlockTest());
        BlockEntryTest blkEntryTest2 = new BlockEntryTest(0, new BlockTest());

        System.out.println(blockEntryComparator.compare(blkEntryTest1, blkEntryTest2));



    }
}
