package tub.ods.pch.channel.util;

import java.math.BigInteger;

import org.web3j.utils.Strings;

public class Utility {
	
	private static final String HEX_PREFIX = "0x";
	
    public static String toHexStringNoPrefix(BigInteger value) {
        return value.toString(16);
    }

	public static String toHexStringNoPrefix(String value) {
		return value.toString();
	}
	
    public static String toHexStringNoPrefixZeroPadded(BigInteger value, int size) {
        return toHexStringZeroPadded(value, size, false);
    }
    
    private static String toHexStringZeroPadded(BigInteger value, int size, boolean withPrefix) {
        String result = toHexStringNoPrefix(value);

        int length = result.length();
        if (length > size) {
            throw new UnsupportedOperationException(
                    "Value " + result + "is larger then length " + size);
        } else if (value.signum() < 0) {
            throw new UnsupportedOperationException("Value cannot be negative");
        }

        if (length < size) {
            result = Strings.zeros(size - length) + result;
        }

        if (withPrefix) {
            return HEX_PREFIX + result;
        } else {
            return result;
        }
    }

	public static String toHexStringNoPrefixZeroPadded(String value, int size) {
		return toHexStringZeroPadded(value, size, false);
	}

	private static String toHexStringZeroPadded(String value, int size, boolean withPrefix) {
		return toHexStringZeroPadded(value, size, false);
	}

}
