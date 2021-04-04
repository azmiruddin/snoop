package test.channel.node.integration;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.tx.TransactionManager;
import org.web3j.utils.Numeric;

import papyrus.channel.node.config.ContractsProperties;
import papyrus.channel.node.config.EthProperties;
import papyrus.channel.node.config.EthereumConfig;
import papyrus.channel.node.config.PropertyConvertersConfig;
import papyrus.channel.node.config.Web3jConfigurer;
import papyrus.channel.node.server.channel.SignedChannelState;
import papyrus.channel.node.server.ethereum.CryptoUtil;
import papyrus.channel.node.server.ethereum.EthereumService;

@EnableConfigurationProperties({EthProperties.class, ContractsProperties.class})
@ActiveProfiles({"test", "testrpc", "sender"})
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PropertyConvertersConfig.class, Web3jConfigurer.class, EthereumConfig.class, EthereumService.class})
public class HashTest {
    
    @Autowired
    private Web3j web3j;
    
    @Autowired
    private EthereumConfig cfg;
    
    @Autowired
    private ContractsProperties contractsProperties;
    
    @Test
    public void test() throws IOException, ExecutionException, InterruptedException {
        Address channelAddress = new Address(cfg.getMainCredentials().getAddress());
        long nonce = 123L;
        BigInteger completedTransfers = BigInteger.TEN;
        
        SignedChannelState state = new SignedChannelState(channelAddress);
        state.setCompletedTransfers(completedTransfers);
        state.setNonce(nonce);
        byte[] hash = callHash("hashState", channelAddress, new Uint256(nonce), new Uint256(completedTransfers));
        Assert.assertEquals(Numeric.toHexString(state.hash()), Numeric.toHexString(hash));
    }

    @Test
    public void test1() throws IOException, ExecutionException, InterruptedException {
        Uint256 int1 = new Uint256(new BigInteger("123"));
        byte[] hash = callHash("hash1", int1);
        Assert.assertEquals(Numeric.toHexString(hash), Numeric.toHexString(CryptoUtil.soliditySha3(int1)));
    }

    @Test
    public void testa1() throws IOException, ExecutionException, InterruptedException {
        Address a1 = new Address("b508d41ecb22e9b9bb85c15b5fb3a90cdaddc4ea");
        byte[] hash = callHash("hasha1", a1);
        Assert.assertEquals(Numeric.toHexString(hash), Numeric.toHexString(CryptoUtil.soliditySha3(a1)));
    }

    @Test
    public void testa2() throws IOException, ExecutionException, InterruptedException {
        Address a1 = new Address("b508d41ecb22e9b9bb85c15b5fb3a90cdaddc4ea");
        Address a2 = new Address("bb9208c172166497cd04958dce1a3f67b28e4d7b");
        byte[] hash = callHash("hasha2", a1, a2);
        Assert.assertEquals(Numeric.toHexString(hash), Numeric.toHexString(CryptoUtil.soliditySha3(a1, a2)));
    }

    @Test
    public void test2() throws IOException, ExecutionException, InterruptedException {
        Uint256 int1 = new Uint256(new BigInteger("1"));
        Uint256 int2 = new Uint256(new BigInteger("2"));
        byte[] hash = callHash("hash2", int1, int2);
        Assert.assertEquals(Numeric.toHexString(hash), Numeric.toHexString(CryptoUtil.soliditySha3(int1, int2)));
    }

    @Test
    public void test3() throws IOException, ExecutionException, InterruptedException {
        Uint256 int1 = new Uint256(new BigInteger("1"));
        Uint256 int2 = new Uint256(new BigInteger("2"));
        Uint256 int3 = new Uint256(new BigInteger("3"));
        byte[] hash = callHash("hash3", int1, int2, int3);
        Assert.assertEquals(Numeric.toHexString(hash), Numeric.toHexString(CryptoUtil.soliditySha3(int1, int2, int3)));
        Assert.assertEquals(Numeric.toHexString(hash), Numeric.toHexString(CryptoUtil.soliditySha3(int1, int2, int3)));
    }

    private byte[] callHash(String name, Type...parameters) throws InterruptedException, ExecutionException {

        Function function = new Function(name,
            Arrays.asList(parameters),
            Arrays.asList(new TypeReference<Bytes32>() {})
        );
        String encodedFunction = FunctionEncoder.encode(function);

        TransactionManager transactionManager = cfg.getTransactionManager(cfg.getMainAddress());
        String channelLibraryAddress = contractsProperties.getAddress().get("ChannelLibrary").toString();
        org.web3j.protocol.core.methods.response.EthCall ethCall = web3j.ethCall(
            Transaction.createEthCallTransaction(
                transactionManager.getFromAddress(), channelLibraryAddress, encodedFunction),
            DefaultBlockParameterName.LATEST)
            .sendAsync().get();

        String value = ethCall.getValue();
        List<Type> list = FunctionReturnDecoder.decode(value, function.getOutputParameters());
        return ((Bytes32) list.get(0)).getValue();
    }
}
