package test.distributedTest;

import dev.insight.rdf4led.storage.block.Block;
import dev.insight.rdf4led.storage.block.BlockData;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 28.03.19
 * TODO Description:
 */
public class BlockTestLong extends BlockData<long[]> {
    int count = 0;

    long[][] buff;

    BlockTestLong(){
        buff = new long[10][3];
    }


    @Override
    public long[] getRecord(int i) {
        return buff[i];
    }



    @Override
    public int findRecordIdx(long[] longs) {
        return 0;
    }



    @Override
    public boolean add(long[][] longs) {
        return false;
    }



    @Override
    public boolean add(long[] longs) {
        buff[count] = longs;
        count++;
        return true;
    }



    @Override
    public Block<long[]>[] split(int i) {
        return new Block[0];
    }



    @Override
    public boolean isFull() {
        return false;
    }



    @Override
    public boolean isDirty() {
        return false;
    }



    @Override
    public int getNumRecords() {
        return 0;
    }



    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

}
