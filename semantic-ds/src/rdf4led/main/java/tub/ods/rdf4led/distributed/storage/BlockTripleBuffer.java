package tub.ods.rdf4led.distributed.storage;

import dev.insight.rdf4led.common.util.ArrayUtil;
import dev.insight.rdf4led.storage.block.Block;
import dev.insight.rdf4led.storage.block.BlockData;
import tub.ods.rdf4led.distributed.rdf.Dictionary;

import java.util.Arrays;
import java.util.Comparator;

import static dev.insight.rdf4led.common.util.ArrayUtil.*;
import static tub.ods.rdf4led.distributed.storage.DistributedTripleStore.makeKey;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 23.04.19
 */
public class BlockTripleBuffer extends BlockData<long[]> {


    private long[][] triplesBuffer;
    private Dictionary dictionary;
    private int count = 0;

    public static Comparator<long[]> comparator = (long[] long1, long[] long2) -> {

        int[] ints1 = makeKey(long1);
        int[] ints2 = makeKey(long2);

        return ArrayUtil.intsComparator.compare(ints1, ints2);

    };

    int chunk;

    public BlockTripleBuffer(int chunk, Dictionary dictionary) {
        this.chunk = chunk;
        this.triplesBuffer = new long[chunk][3];
        this.count = 0;
        this.dictionary = dictionary;
    }



    public BlockTripleBuffer(byte[] data) {
        int length = bytes2int(data[data.length - 4],
                data[data.length - 3],
                data[data.length - 2],
                data[data.length - 1]);
        byte[] triples = new byte[length];
        System.arraycopy(data, 0, triples, 0, length);
        this.triplesBuffer = deserialize(triples);
        this.chunk = length;
        byte[] lex = new byte[data.length - length - 4];
        System.arraycopy(data, length, lex, 0, data.length - length - 4);
        this.dictionary = new Dictionary(lex);
    }



    @Override
    public byte[] getBytes() {
        byte[] lex = dictionary.getLtb();
        byte[] triplesBuffer = serialize(this.triplesBuffer);


        byte[] buffer = new byte[triplesBuffer.length + lex.length + 4];
        byte[] length = int2bytes(triplesBuffer.length);

        System.arraycopy(triplesBuffer, 0, buffer, 0, triplesBuffer.length);
        System.arraycopy(lex, 0, buffer, triplesBuffer.length, lex.length);
        System.arraycopy(length, 0, buffer, triplesBuffer.length + lex.length, 4);

        return buffer;
    }



    @Override
    public long[] getRecord(int i) {
        return triplesBuffer[i];
    }



    @Override
    public int findRecordIdx(long[] triple) {
        int idx = Arrays.binarySearch(triplesBuffer, 0, count, triple, comparator);
        return inverseIndex(idx);
    }


    @Override
    public boolean add(long[][] triples) {
        for (long[] tripe : triples)
            add(tripe);
        return true;
    }



    @Override
    public boolean add(long[] triple) {
        triplesBuffer[count] = triple;
        count++;
        return true;
    }


    public Dictionary getDictionary() {
        return this.dictionary;
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
        return count;
    }



    public void reset() {
        count = 0;
        this.triplesBuffer = new long[chunk][3];
        //Arrays.fill(triplesBuffer, new long[3]);
    }




    public void printAll( ){
        System.out.println("================================================");
        for (int i = 0; i < count; i++){
            print(i);
        }
        System.out.println("================================================");
    }

    public void print(int i) {
        long[] triple = getRecord(i);

        ArrayUtil.println(makeKey(triple));

        String s = getLexical(triple[0]);
        String p = getLexical(triple[1]);
        String o = getLexical(triple[2]);

        System.out.println(s + " -- " + p + " -- " + o);

    }



    public String getLexical(long nodeL) {
        int nodeI = int1long(nodeL);
        return dictionary.getLexicalForm(nodeI);
    }



    public void sort() {
        Arrays.sort(triplesBuffer, comparator);
    }



    private long[][] deserialize(byte[] triples) {
        long[][] longs2 = new long[triples.length / 24][3];

        for (int i = 0; i < longs2.length; i++) {
            longs2[i] = bytes2longs(triples[i * 24],
                    triples[i * 24 + 1],
                    triples[i * 24 + 2],
                    triples[i * 24 + 3],
                    triples[i * 24 + 4],
                    triples[i * 24 + 5],
                    triples[i * 24 + 6],
                    triples[i * 24 + 7],
                    triples[i * 24 + 8],
                    triples[i * 24 + 9],
                    triples[i * 24 + 10],
                    triples[i * 24 + 11],
                    triples[i * 24 + 12],
                    triples[i * 24 + 13],
                    triples[i * 24 + 14],
                    triples[i * 24 + 15],
                    triples[i * 24 + 16],
                    triples[i * 24 + 17],
                    triples[i * 24 + 18],
                    triples[i * 24 + 19],
                    triples[i * 24 + 20],
                    triples[i * 24 + 21],
                    triples[i * 24 + 22],
                    triples[i * 24 + 23]);
        }
        this.count = triples.length / 24;
        return longs2;
    }



    private byte[] serialize(long[][] longs2) {
        byte[] bytes = new byte[longs2.length * 24];

        for (int i = 0; i < longs2.length; i++) {
            bytes[i * 24    ] = (byte) (longs2[i][0] >> 56);
            bytes[i * 24 + 1] = (byte) (longs2[i][0] >> 48);
            bytes[i * 24 + 2] = (byte) (longs2[i][0] >> 40);
            bytes[i * 24 + 3] = (byte) (longs2[i][0] >> 32);
            bytes[i * 24 + 4] = (byte) (longs2[i][0] >> 24);
            bytes[i * 24 + 5] = (byte) (longs2[i][0] >> 16);
            bytes[i * 24 + 6] = (byte) (longs2[i][0] >> 8);
            bytes[i * 24 + 7] = (byte) (longs2[i][0]);
            bytes[i * 24 + 8] = (byte) (longs2[i][1] >> 56);
            bytes[i * 24 + 9] = (byte) (longs2[i][1] >> 48);
            bytes[i * 24 + 10] = (byte) (longs2[i][1] >> 40);
            bytes[i * 24 + 11] = (byte) (longs2[i][1] >> 32);
            bytes[i * 24 + 12] = (byte) (longs2[i][1] >> 24);
            bytes[i * 24 + 13] = (byte) (longs2[i][1] >> 16);
            bytes[i * 24 + 14] = (byte) (longs2[i][1] >> 8);
            bytes[i * 24 + 15] = (byte) (longs2[i][1]);
            bytes[i * 24 + 16] = (byte) (longs2[i][2] >> 56);
            bytes[i * 24 + 17] = (byte) (longs2[i][2] >> 48);
            bytes[i * 24 + 18] = (byte) (longs2[i][2] >> 40);
            bytes[i * 24 + 19] = (byte) (longs2[i][2] >> 32);
            bytes[i * 24 + 20] = (byte) (longs2[i][2] >> 24);
            bytes[i * 24 + 21] = (byte) (longs2[i][2] >> 16);
            bytes[i * 24 + 22] = (byte) (longs2[i][2] >> 8);
            bytes[i * 24 + 23] = (byte) (longs2[i][2]);
        }

        return bytes;
    }

}
