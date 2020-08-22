package test.distributedTest;

import dev.insight.rdf4led.common.util.ArrayUtil;
import org.web3j.abi.datatypes.Int;
import redis.clients.jedis.Jedis;

import java.util.Set;

import static dev.insight.rdf4led.common.util.ArrayUtil.chars2ints;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 03.05.19
 */
public class SpaceQueryWithRedis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        int[] keyl, keyh;
        //blk1;
        jedis.flushAll();
        insert(jedis);
        keyl = string2ints("0000000000000000000000000000000000000000000000000000000000000000");
        keyh = string2ints("0000000000000000000000000000000000000000000000000000000011111111");
        search(jedis, keyl, keyh);


        keyl = string2ints("0000000000000000000000000000000000000000000000000000000000010000");
        keyh = string2ints("0000000000000000000000000000000000000000000000000000000000011111");
        search(jedis, keyl, keyh);

        keyl = string2ints("0000000000000000000000000000000000000000000000000000000001000000");
        keyh = string2ints("0000000000000000000000000000000000000000000000000000000001001111");
        search(jedis, keyl, keyh);

        keyl = string2ints("0000000000000000000000000000000000000000000000000000000000110000");
        keyh = string2ints("0000000000000000000000000000000000000000000000000000000000111111");
        search(jedis, keyl, keyh);

        keyl = string2ints("0000000000000000000000000000000000000000000000000000000001100000");
        keyh = string2ints("0000000000000000000000000000000000000000000000000000000001101111");
        search(jedis, keyl, keyh);

        //printSet(jedis.zrange("test", 0, 1000));
    }


    private static void insert(Jedis jedis){
        for (int i = 0; i<50; i++){
            for (int j=i+1; j<50; j++){
                String index = getString(i,j);
                jedis.zadd("test", 0, index + ":index" + "[" + i + "," + j + "]");
            }
        }
    }


    private static String getString(int x1, int y1) {
        String sx1 = make32binary(x1);
        String sy1 = make32binary(y1);
        return merge2Strings(sx1, sy1);
    }

    private static void testSearch(Jedis jedis) {
        search(jedis, new int[]{0,6},  new int[]{4,8});
        search(jedis, new int[]{0,8},  new int[]{4,12});
        search(jedis, new int[]{0,12}, new int[]{4,16});
        search(jedis, new int[]{0,16}, new int[]{4,18});

        search(jedis, new int[]{4,6},  new int[]{6,8});
        search(jedis, new int[]{4,8},  new int[]{6,12});
        search(jedis, new int[]{4,12}, new int[]{6,16});
        search(jedis, new int[]{4,16}, new int[]{6,18});

        search(jedis, new int[]{4,7}, new int[]{12,15});
    }



    private static void search(Jedis jedis, int[] low, int[] high){
        System.out.println("search -----");
        ArrayUtil.println(low);
        ArrayUtil.println(high);

        String lkx = make32binary(low[0]);
        String lky = make32binary(low[1]);

        String hkx = make32binary(high[0]);
        String hky = make32binary(high[1]);

        String indexl = merge2Strings(lkx, lky);
        String indexh = merge2Strings(hkx, hky);

        printSet(jedis.zrangeByLex("test", "[" + indexl, "[" + indexh));
    }


    private static String make8binary(int integer){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(Integer.toBinaryString(integer));

        while (strBuilder.length() < 8) {
            strBuilder.insert(0, "0");
        }
        return strBuilder.toString();
    }

    private static String make32binary(int integer){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(Integer.toBinaryString(integer));

        while (strBuilder.length() < 32) {
            strBuilder.insert(0, "0");
        }
        return strBuilder.toString();
    }

    private static String ints2binaryString(int[] ints){
        StringBuilder strBuilder = new StringBuilder();

        for (int i = 0; i < ints.length; i++){
            strBuilder.append(make32binary(ints[i]));
        }

        return strBuilder.toString();
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

    private static void printSet(Set<String> ss) {
        int i = 0;
        for (String s : ss) {
            System.out.println(s);
//            System.out.print(s.substring(65));
//            ArrayUtil.println(string2ints(s.substring(0, 64)));
            i++;
        }

        System.out.println("total: " + i);
    }

    private static void printSet1(Set<String> ss) {
        for (String s : ss) {
            //System.out.println(s);
            System.out.println(s);
        }
    }

    private static void spaceQuery(int x0, int y0, int x1, int y1, int exp){
        int i = 1 << exp;
        System.out.println(i);

        int bits = exp*2;


        int x_start = x0/i;
        int x_end   = x1/i;

        int y_start = y0/i;
        int y_end   = y1/i;

        for (int x = x_start; x< x_end; x++){
            for (int y = y_start; y< y_end; y++) {
                int x_range_start = x*(i);
                int x_range_end   = x_range_start | (i - 1);

                int y_rang_start  = y*i;
                int y_rang_end    = y_rang_start | (i - 1);
                System.out.println(x_range_start + " sx --> ex " + x_range_end);
                System.out.println(y_rang_start  + " sy --> ey " + y_rang_end );

            }
        }
    }

    private static int[] string2ints(String string){
        String s1 = "";
        String s2 = "";

        for (int i = 0; i < string.length()/2; i++ ){
            s1 = s1 + string.charAt(i*2);
            s2 = s2 + string.charAt(i*2 + 1);
        }

        int int1 = Integer.parseInt(s1, 2);
        int int2 = Integer.parseInt(s2, 2);

        return new int[]{int1 , int2};
    }
}

