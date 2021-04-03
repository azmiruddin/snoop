package tub.ods.pch.channel.node;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.web3j.utils.Convert;

public class TokenConvert {
    public static final int DECIMALS = 18;
    
    public static BigDecimal fromWei(long wei) {
        return fromWei(BigInteger.valueOf(wei));
    }
    
    public static BigDecimal fromWei(BigInteger wei) {
        return new BigDecimal(wei, DECIMALS);
    }
    
    public static BigInteger toWei(String tokens) {
        return toWei(new BigDecimal(tokens));
    }
    
    public static BigInteger toWei(BigDecimal tokens) {
        try {
            return tokens.movePointRight(DECIMALS).toBigIntegerExact();
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException("Illegal tokens value: " + tokens);
        }
    }

    public static void main(String[] args) {
        System.out.println(Convert.fromWei("10000000000", Convert.Unit.GWEI));
    }
}
