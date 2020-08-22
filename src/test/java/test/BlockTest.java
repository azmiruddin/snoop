package test;

import dev.insight.rdf4led.common.util.ArrayUtil;
import dev.insight.rdf4led.storage.block.Block;

import java.util.Arrays;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 19.03.19
 * Description: TODO
 */

public class BlockTest implements Block<Integer> {

    public int[] buff;
    public int count;



    public BlockTest() {
        buff = new int[6];
        count = 0;
    }



    @Override
    public Integer getRecord(int pos) {
        return buff[pos];
    }



    @Override
    public int findRecordIdx(Integer record) {
        int idx = Arrays.binarySearch(buff, record);
        return ArrayUtil.inverseIndex(idx);
    }



    @Override
    public boolean add(Integer[] records) {
        return false;
    }



    @Override
    public boolean add(Integer record) {
        buff[count] = record;
        count++;
        return true;
    }



    @Override
    public Block<Integer>[] split(int factor) {
        BlockTest[] blocks = new BlockTest[1];
        blocks[0] = new BlockTest();
        blocks[0].buff[0] = buff[4];
        blocks[0].buff[1] = buff[5];
        blocks[0].count = 2;

        this.count = 4;
        this.buff[4] = 0;
        this.buff[5] = 0;

        return blocks;
    }



    @Override
    public boolean isFull() {
        if (count < 6) {
            return false;
        } else {
            buff = ArrayUtil.sortAndRemoveDuplication(buff);
        }

        return count >= 6;
    }



    @Override
    public boolean isDirty() {
        return false;
    }



    @Override
    public int getNumRecords() {
        return count;
    }



    @Override
    public String toString() {
        return "<" + buff[0] + "," +
                buff[1] + "," +
                buff[2] + "," +
                buff[3] + "," +
                buff[4] + "," +
                buff[5] + ">";
    }

}
