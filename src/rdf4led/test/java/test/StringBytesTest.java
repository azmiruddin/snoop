package test;

import dev.insight.rdf4led.common.util.ArrayUtil;

import java.util.Arrays;
import java.util.Base64;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 26.04.19
 */
public class StringBytesTest {

    public static void main(String[] args) {
        long l1  = Long.MAX_VALUE;

        System.out.println(String.valueOf(l1).length());


    }



    static byte[] fromChar(char[] chars) {
        byte[] bytes = new byte[chars.length];
        for (int i = 0; i < chars.length; i++) {
            bytes[i] = (byte) chars[i];
        }

        return bytes;
    }



    static char[] fromByte(byte[] bytes) {
        char[] chars = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            chars[i] = (char) bytes[i];
        }
        return chars;
    }

}
