package test.distributedTest;

import dev.insight.rdf4led.common.util.ArrayUtil;
import org.web3j.abi.datatypes.Int;
import redis.clients.jedis.Jedis;

import java.util.Set;

import static dev.insight.rdf4led.common.util.ArrayUtil.*;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 03.04.19
 * TODO Description:
 */
public class JedisTest {

    String endpoint;

    public JedisTest(String endpoint) {
        this.endpoint = endpoint;
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);


        //block 1:
        int[] lr1 = new int[]{1, 1, 1};
        int[] lh1 = new int[]{1, 1, 5};
        String h1 = "block1";

        //block 2:
        int[] lr2 = new int[]{1, 1, 3};
        int[] lh2 = new int[]{1, 1, 8};
        String h2 = "block2";

        //block 3:
        int[] lr3 = new int[]{1, 1, 8};
        int[] lh3 = new int[]{2, 1, 0};
        String h3 = "block3";

        //block 4:
        int[] lr4 = new int[]{6, 4, 1};
        int[] lh4 = new int[]{7, 4, 9};
        String h4 = "block4";

        jedis.flushAll();
        jadd(jedis, lr1, lh1, h1);
        jadd(jedis, lr2, lh2, h2);
        //jadd(jedis, lr3, lh3, h3);
        //jadd(jedis, lr4, lh4, h4);



    }


    private static void jadd(Jedis jedis, int[] low, int[] high, String index){

        String stringKeyLow = new String(ints2chars(low));
        String stringKeyHigh = new String(ints2chars(high));

        String key = stringKeyLow + stringKeyHigh;
        jedis.zadd("test", 0, key + index);


        long lowId;
        long highId;

        try {
            lowId = jedis.zrank("test", stringKeyLow);
        } catch (NullPointerException e){
            jedis.zadd("test", 0, stringKeyLow);
            lowId = jedis.zrank("test", stringKeyLow);
        }


        try {
            highId = jedis.zrank("test", stringKeyHigh);
        } catch (NullPointerException e){
            jedis.zadd("test", 0, stringKeyHigh);
            highId = jedis.zrank("test", stringKeyHigh);
        }


        Set<String> ss = jedis.zrange("test", lowId, highId);

        System.out.println("--------------------------------------------------------");
        printSet(ss);


    }



    private static void checkPrevious(Jedis jedis, int[] low, String index, long id) {
        Set<String> previousKeys = jedis.zrange("test", id-1, id-1);

        for (String previousKey:previousKeys)
        {
            int[] preKeyInts    = chars2ints((previousKey.substring(0, 24)).toCharArray());
            int[] preHighKey    = new int[]{preKeyInts[3], preKeyInts[4], preKeyInts[5]};

            if (intsComparator.compare(preHighKey, low) > 0){
                jadd(jedis, low, preHighKey, index);
            }

        }
    }



    private static void printSet(Set<String> ss) {
        for (String s : ss) {
            String r = s.substring(0, 12);
            System.out.print(s + "--> key ");ArrayUtil.println(chars2ints(r.toCharArray()));
        }
    }






























    private static String make10digitInt(int integer){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(Integer.toString(integer));

        while (strBuilder.length() != 10){
            strBuilder.insert(0, "0");
        }

        return strBuilder.toString();
    }


    private static String append(String...strings){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i<strings.length; i++){
            stringBuilder.append(strings[i]);
        }

        return stringBuilder.toString();
    }


    private static String merge2Strings(String s1, String s2){
        if (s1.length() != s2.length()) throw new RuntimeException("s1.length != s2.length");

        StringBuilder strBuilder = new StringBuilder();

        for (int i=0; i<s1.length(); i++){
            strBuilder.append(s1.charAt(i));
            strBuilder.append(s2.charAt(i));
        }

        return strBuilder.toString();
    }


    private static void add(Jedis jedis, int[] low, int[] high, String index){
        String lowS  = append(make10digitInt(low[0] ), make10digitInt(low[1] ), make10digitInt(low[2] ));
        String highS = append(make10digitInt(high[0]), make10digitInt(high[1]), make10digitInt(high[2]));


        String key = merge2Strings(lowS, highS);
        System.out.println(key);
        jedis.zadd("test", 0, key + index );
    }
}
