package tub.ods.rdf4led.distributed.storage;

import dev.insight.rdf4led.common.util.ArrayUtil;
import dev.insight.rdf4led.storage.block.Block;
import dev.insight.rdf4led.storage.block.BlockData;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 01.04.19
 * TODO Description:
 */
public class BlockTripleLong extends BlockData<long[]> {

    private long[][] longs;
    private int count;
    private static int num = 10000;


    public BlockTripleLong() {
        longs = new long[num][3];
    }



    @Override
    public byte[] getBytes() {

        return ArrayUtil.longs2bytes(getRecord(10));
    }



    @Override
    public long[] getRecord(int i) {
        return longs[i];
    }



    @Override
    public int findRecordIdx(long[] longs) {
        return 0;
    }



    @Override
    public boolean add(long[][] triples) {
        for (long[] triple : triples) {
            add(triple);
        }

        return true;
    }



    @Override
    public boolean add(long[] triple) {
        longs[count] = triple;
        count++;
        return true;
    }



    @Override
    public Block<long[]>[] split(int i) {
        return new Block[0];
    }



    @Override
    public boolean isFull() {
        return count == num;
    }



    @Override
    public boolean isDirty() {
        return false;
    }



    @Override
    public int getNumRecords() {
        return 0;
    }



    public void reset() {
        longs = new long[num][3];
        count = 0;
    }

}
