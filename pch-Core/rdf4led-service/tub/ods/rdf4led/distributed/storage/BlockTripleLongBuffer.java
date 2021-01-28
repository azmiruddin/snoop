package tub.ods.rdf4led.distributed.storage;

import dev.insight.rdf4led.storage.block.Block;
import dev.insight.rdf4led.storage.block.BlockData;

import java.nio.ByteBuffer;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 17.04.19
 */
public class BlockTripleLongBuffer extends BlockData<long[]> {

    private ByteBuffer buffer;
    private ByteBuffer lexBuffer;

    int count;
    private static int num = 5000;



    public BlockTripleLongBuffer() {
        count = 0;
        buffer = ByteBuffer.allocate(num * 24);
    }



    public BlockTripleLongBuffer(byte[] content) {

    }



    @Override
    public byte[] getBytes() {
        return buffer.array();
    }



    @Override
    public long[] getRecord(int i) {
        long[] triple = new long[3];

        triple[0] = buffer.getLong(i * 24);
        triple[1] = buffer.getLong(i * 24 + 8);
        triple[2] = buffer.getLong(i * 24 + 16);

        return new long[0];
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
    public boolean add(long[] longs) {
        for (long l : longs) {
            buffer.putLong(l);
        }
        count++;
        return true;
    }



    @Override
    public Block<long[]>[] split(int i) {
        throw new UnsupportedOperationException();
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
        buffer.clear();
        buffer.rewind();
        count = 0;
    }

}
