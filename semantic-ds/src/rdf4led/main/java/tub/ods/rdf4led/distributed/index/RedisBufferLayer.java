package tub.ods.rdf4led.distributed.index;

import dev.insight.rdf4led.common.util.ArrayUtil;
import redis.clients.jedis.Jedis;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockIndex;
import tub.ods.rdf4led.distributed.storage.block.ETHBlockIndex;
import tub.ods.rdf4led.distributed.storage.block.PrivateDistributedBlockIndex;

import java.util.Set;

import static dev.insight.rdf4led.common.util.ArrayUtil.*;
import static tub.ods.rdf4led.distributed.storage.DistributedTripleStore.*;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 03.04.19
 * TODO Description:
 */
public class RedisBufferLayer extends DistributedBufferLayerSecure<long[]> {


    private String index;
    private Jedis jedis;



    public RedisBufferLayer(String index, String localhost, int port) {
        this.jedis = new Jedis(localhost, port);
        this.index = index;
    }



    @Override
    protected boolean updateSecure(DistributedBlockEntry<long[], ?> prBlockEntry) {

        long[] record = prBlockEntry.getBlockRecord();
        int[] recordKey = makeKey(record);

        String key = new String(ints2chars(recordKey));

        DistributedBlockIndex blockIndex = prBlockEntry.getBlockIndex();

        String value = ((ETHBlockIndex) blockIndex).getClientID();

        jedis.zadd(index, 0, key + value);

        return true;
    }



    @Override
    public DistributedBlockEntry<long[], ?>[] remoteRequest(long[] keyRecord) {
        int[] key = makeKey(keyRecord);

        int[] lowKey = createLowestKey(key);
        int[] highKey = createHighestKey(key);

        Set<String> results0 = search0(lowKey, highKey);
        Set<String> results  = search1(lowKey);
        results.addAll(results0);

        DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] blockEntries = new DistributedBlockEntry[results.size()];

        int count = 0;

        for (String result : results) {
            String record = result.substring(0, 24);
            String blockIdx = result.substring(24);

            ETHBlockIndex privateDistributedBlockIndex = new ETHBlockIndex();
            privateDistributedBlockIndex.setClientID(blockIdx);

            DistributedBlockEntry<long[], PrivateDistributedBlockIndex> blockEntry
                    = new DistributedBlockEntry<>(privateDistributedBlockIndex, makeRecord(chars2ints(record.toCharArray())));

            blockEntries[count] = blockEntry;
            count++;
        }


        return blockEntries;
    }



    //RANK SEARCH
    private Set<String> search0(int[] lowestKey, int[] highestKey) {

        ArrayUtil.println(lowestKey);
        ArrayUtil.println(highestKey);

        String lowKeyStr = new String(ints2chars(lowestKey));
        String highKeyStr = new String(ints2chars(highestKey));

        return jedis.zrangeByLex(this.index, "[" + lowKeyStr, "[" + highKeyStr);
    }



    //SEARCH FOR THE LOWER BOUND
    private Set<String> search1(int[] lowestkey) {
        String lowKeyStr = new String(ints2chars(lowestkey));

        jedis.zadd(this.index, 0, lowKeyStr);
        long idx = jedis.zrank(this.index, lowKeyStr);
        jedis.zrem(this.index, lowKeyStr);
        idx = idx == 0 ? idx : idx - 1;
        Set<String> ss = jedis.zrange(this.index, idx, idx);

        String s = (String) ss.toArray()[0];
        String maxKeyStr = s.substring(12,24);
        int[]  maxKey = chars2ints(maxKeyStr.toCharArray());

        int i = intsComparator.compare(maxKey, lowestkey);

        if (i>=0){
            return ss;
        } else {
            ss.clear();
            return ss;
        }
    }

}
