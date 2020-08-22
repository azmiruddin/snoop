package test.distributedTest;

import dev.insight.rdf4led.common.util.ArrayUtil;
import dev.insight.rdf4led.storage.block.BlockEntry;
import dev.insight.rdf4led.storage.block.BlockEntryKey;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.ETHBlockIndex;

import java.util.Arrays;
import java.util.Comparator;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 29.03.19
 * TODO Description:
 */
public class BuffeLayerRemoteService  {

    DistributedBlockEntry<long[], ?>[] remoteEntries;
    protected Comparator<BlockEntry<long[], ?>> comparator;



    public BuffeLayerRemoteService() {
        remoteEntries = new DistributedBlockEntry[25];

        int remoteCount = 0;

        for (long i = 0; i < 5; i++) {

            for (long j = 0; j < 5; j++) {    //to remote

                long[] record = new long[]{i, j, i};
                ETHBlockIndex blkIdx = new ETHBlockIndex();
                blkIdx.setRemoteIndex(("txd: " + i).getBytes());
                DistributedBlockEntry<long[], ETHBlockIndex> blockEntry
                        = new DistributedBlockEntry<>(blkIdx, record);
                remoteEntries[remoteCount] = blockEntry;
                remoteCount++;
            }
        }

    }



    protected Comparator<BlockEntry<long[], ?>> getComparator() {
        if (this.comparator == null) {
            comparator = (o1, o2) -> {
                long[] r1 = o1.getBlockRecord();
                long[] r2 = o2.getBlockRecord();

                if (r1.length != r2.length) {
                    throw new IllegalArgumentException("length should be the same");
                }

                for (int i = 0; i < r1.length; i++) {
                    if (r1[i] == r2[i]) {
                        continue;
                    } else {
                        return r1[i] > r2[i] ? 1 : -1;
                    }

                }

                return 0;
            };
        }

        return comparator;
    }



    public DistributedBlockEntry<long[], ?>[] remoteRequest(long[] keyRecord) {
        int idx = Arrays.binarySearch(remoteEntries, BlockEntryKey.createBlockEntryKey(createLowestKey(keyRecord)), getComparator());
        int idxFrom = ArrayUtil.inverseIndex(idx);

        idx = Arrays.binarySearch(remoteEntries, BlockEntryKey.createBlockEntryKey(createHighestKey(keyRecord)), getComparator());
        int idxTo = ArrayUtil.inverseIndex(idx) + 1;

        DistributedBlockEntry<long[], ?>[] result = Arrays.copyOfRange(remoteEntries, idxFrom, idxTo);

        return result;
    }



    public void update(DistributedBlockEntry<long[], ?> blockEntry) {

    }



    public void update(DistributedBlockEntry<long[], ?>[] blockEntries) {

    }



    protected long[] createLowestKey(long[] longs) {
        long[] result = new long[longs.length];

        for (int i = 0; i < longs.length; i++) {
            if (longs[i] == 0) {
                result[i] = Long.MIN_VALUE;
            } else {
                result[i] = longs[i];
            }
        }

        return result;
    }



    protected long[] createHighestKey(long[] longs) {
        long[] result = new long[longs.length];

        for (int i = 0; i < longs.length; i++) {
            if (longs[i] == 0) {
                result[i] = Long.MAX_VALUE;
            } else {
                result[i] = longs[i];
            }
        }

        return result;
    }

}