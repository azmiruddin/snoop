package tub.ods.rdf4led.distributed.contract;

import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class StoreTripleData extends Contract {

    private static final String BINARY = "0x608060405234801561001057600080fd5b5061041a806100206000396000f3fe6080604052600436106100565763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416636c0602de811461005b57806394450d48146100fa578063eedbedc2146101b6575b600080fd5b34801561006757600080fd5b506100856004803603602081101561007e57600080fd5b50356101ef565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100bf5781810151838201526020016100a7565b50505050905090810190601f1680156100ec5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561010657600080fd5b506101b46004803603604081101561011d57600080fd5b8135919081019060408101602082013564010000000081111561013f57600080fd5b82018360208201111561015157600080fd5b8035906020019184600183028401116401000000008311171561017357600080fd5b91908080601f016020809104026020016040519081016040528093929190818152602001838380828437600092019190915250929550610297945050505050565b005b6101b4600480360360408110156101cc57600080fd5b508035906020013573ffffffffffffffffffffffffffffffffffffffff166102e7565b60008181526020818152604091829020600180820180548551600293821615610100026000190190911692909204601f810185900485028301850190955284825260609492939192909183018282801561028a5780601f1061025f5761010080835404028352916020019161028a565b820191906000526020600020905b81548152906001019060200180831161026d57829003601f168201915b5050505050915050919050565b61029f61033b565b50604080518082018252838152602080820184815260008681528083529390932082518155925180519293849390926102df926001850192910190610353565b505050505050565b600034116102f457600080fd5b60405173ffffffffffffffffffffffffffffffffffffffff8216903480156108fc02916000818181858888f19350505050158015610336573d6000803e3d6000fd5b505050565b60408051808201909152600081526060602082015290565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061039457805160ff19168380011785556103c1565b828001600101855582156103c1579182015b828111156103c15782518255916020019190600101906103a6565b506103cd9291506103d1565b5090565b6103eb91905b808211156103cd57600081556001016103d7565b9056fea165627a7a723058208904d649eba9824e039c195a810b6972c672c71f2f9ff4ff7bbf88d624e3ea190029";

    public static final String FUNC_INSERTIPFS = "insertIPFS";

    public static final String FUNC_BUYDATA = "buyData";

    public static final String FUNC_FETCHIPFS = "fetchIPFS";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
        _addresses.put("4224", "0xCCd947950B3361ef2F21243dB5E8987C7881b6fA");
    }

    @Deprecated
    protected StoreTripleData(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }



    protected StoreTripleData(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }



    @Deprecated
    protected StoreTripleData(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }



    protected StoreTripleData(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }



    public RemoteCall<TransactionReceipt> insertIPFS(byte[] _triple, String _digest) {
        final Function function = new Function(
                FUNC_INSERTIPFS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_triple),
                        new org.web3j.abi.datatypes.Utf8String(_digest)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }



    public RemoteCall<TransactionReceipt> buyData(byte[] _triple, String receiver, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYDATA,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_triple),
                        new org.web3j.abi.datatypes.Address(receiver)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }



    public RemoteCall<String> fetchIPFS(byte[] _triple) {
        final Function function = new Function(FUNC_FETCHIPFS,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_triple)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {
                }));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }



    @Deprecated
    public static StoreTripleData load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new StoreTripleData(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }



    @Deprecated
    public static StoreTripleData load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new StoreTripleData(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }



    public static StoreTripleData load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new StoreTripleData(contractAddress, web3j, credentials, contractGasProvider);
    }



    public static StoreTripleData load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new StoreTripleData(contractAddress, web3j, transactionManager, contractGasProvider);
    }



    public static RemoteCall<StoreTripleData> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StoreTripleData.class, web3j, credentials, contractGasProvider, BINARY, "");
    }



    @Deprecated
    public static RemoteCall<StoreTripleData> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StoreTripleData.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }



    public static RemoteCall<StoreTripleData> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(StoreTripleData.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }



    @Deprecated
    public static RemoteCall<StoreTripleData> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(StoreTripleData.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }



    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }



    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

}
