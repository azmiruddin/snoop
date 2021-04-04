package test.channel.node.server.ethereum;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.web3j.abi.datatypes.Address;
import org.web3j.crypto.Hash;
import org.web3j.utils.Numeric;

import tub.ods.pch.channel.util.CryptoUtil;

public class TestCryptoUtil {
    //from https://github.com/raineorshine/solidity-sha3/blob/master/test/spec.js
    @Test
    public void soliditySha3() throws Exception {
        Assert.assertEquals(
            "0xb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6",
            Numeric.toHexString(CryptoUtil.soliditySha3(1))
        );
        Assert.assertEquals(
            "0xb10e2d527612073b26eecdfd717e6a320cf44b4afac2b0732d9fcbe2b7fa0cf6",
            Numeric.toHexString(CryptoUtil.soliditySha3(1))
        );
        Assert.assertEquals(
            "0xa9c584056064687e149968cbab758a3376d22aedc6a55823d1b3ecbee81b8fb9",
            Numeric.toHexString(CryptoUtil.soliditySha3(-1))
        );
        Assert.assertEquals(
            "0x26700e13983fefbd9cf16da2ed70fa5c6798ac55062a4803121a869731e308d2",
            Numeric.toHexString(CryptoUtil.soliditySha3(BigInteger.valueOf(100)))
        );
        Assert.assertEquals(
            "0x26700e13983fefbd9cf16da2ed70fa5c6798ac55062a4803121a869731e308d2",
            Numeric.toHexString(CryptoUtil.soliditySha3(BigInteger.valueOf(100)))
        );
        Address address = new Address("abcdef");
        Assert.assertEquals(
            Numeric.toHexStringNoPrefixZeroPadded(address.getValue(), 40),
            Numeric.toHexStringNoPrefix(Numeric.toBytesPadded(address.getValue(), 20))
        );
        Assert.assertEquals(
            Hash.sha3(Numeric.toHexStringNoPrefixZeroPadded(address.getValue(), 40)),
            Numeric.toHexString(CryptoUtil.soliditySha3(address))
        );
    }

    @Test
    public void testContractAddress() {
        String address = "6ac7ea33f8831ea9dcc53393aaa88b25a785dbf0";
        Assert.assertEquals("cd234a471b72ba2f1ccf0a70fcaba648a5eecd8d", CryptoUtil.getContractAddress(address, 0));
        Assert.assertEquals("343c43a37d37dff08ae8c4a11544c718abb4fcf8", CryptoUtil.getContractAddress(address, 1));
        Assert.assertEquals("f778b86fa74e846c4f0a1fbd1335fe81c00a0c91", CryptoUtil.getContractAddress(address, 2));
    }
}
