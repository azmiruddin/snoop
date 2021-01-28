package tub.ods.pch.contract.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.5.16.
 * 
 * Deploy commitment and request channel
 */
@SuppressWarnings("rawtypes")
public class MerkleContract extends Contract {
    public static final String BINARY = "60806040526040516060806106ac8339810160409081528151602083015191909201516000341161002f57600080fd5b60018054600160a060020a03948516600160a060020a03199182161790915560008054339095169490911693909317909255426002556003556004556106328061007a6000396000f3006080604052600436106100985763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166304758e79811461009d578063075aa0c4146100ce5780630b97bc86146100e35780631596da741461010a5780632ef2d55e14610166578063393fe1cd1461017b57806339658245146101905780635b354c4a146101a5578063c4024d2b146101b0575b600080fd5b3480156100a957600080fd5b506100b2610222565b60408051600160a060020a039092168252519081900360200190f35b3480156100da57600080fd5b506100b2610231565b3480156100ef57600080fd5b506100f8610240565b60408051918252519081900360200190f35b34801561011657600080fd5b50604080516020600460248035828101358481028087018601909752808652610164968435963696604495919490910192918291850190849080828437509497506102469650505050505050565b005b34801561017257600080fd5b506100f8610343565b34801561018757600080fd5b506100f8610349565b34801561019c57600080fd5b5061016461034f565b610164600435610370565b3480156101bc57600080fd5b50604080516020600460443581810135838102808601850190965280855261020e95833595602480359636969560649593949201929182918501908490808284375094975061048c9650505050505050565b604080519115158252519081900360200190f35b600154600160a060020a031681565b600054600160a060020a031681565b60025481565b60015460009033600160a060020a0390811691161461026457600080fd5b82604051602001808281526020019150506040516020818303038152906040526040518082805190602001908083835b602083106102b35780518252601f199092019160209182019101610294565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902090506102ef600454828461048c565b15156102fa57600080fd5b600154604051600160a060020a039091169084156108fc029085906000818181858888f19350505050158015610334573d6000803e3d6000fd5b50600054600160a060020a0316ff5b60035481565b60045481565b6003546002540142101561036257600080fd5b600054600160a060020a0316ff5b6004548111156104045760045460408051602080820193909352808201849052815180820383018152606090910191829052805190928291908401908083835b602083106103cf5780518252601f1990920191602091820191016103b0565b5181516020939093036101000a6000190180199091169216919091179052604051920182900390912060045550610489915050565b60045460408051602080820185905281830193909352815180820383018152606090910191829052805190928291908401908083835b602083106104595780518252601f19909201916020918201910161043a565b5181516020939093036101000a600019018019909116921691909117905260405192018290039091206004555050505b50565b600082815b83518110156105fa5783818151811015156104a857fe5b60209081029091010151821015610558578184828151811015156104c857fe5b6020908102909101810151604080518084019490945283810191909152805180840382018152606090930190819052825190918291908401908083835b602083106105245780518252601f199092019160209182019101610505565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902091506105f2565b838181518110151561056657fe5b6020908102909101810151604080518084019290925281810185905280518083038201815260609092019081905281519192909182918401908083835b602083106105c25780518252601f1990920191602091820191016105a3565b6001836020036101000a038019825116818451168082178552505050505050905001915050604051809103902091505b600101610491565b509390931493925050505600a165627a7a723058207d1167f14dda254411fa0121fdc65f4a69852db955403e5f3384b28a46dde1d70029";

    public static final String FUNC_CHANNELRECIPIENT = "channelRecipient";

    public static final String FUNC_CHANNELSENDER = "channelSender";

    public static final String FUNC_STARTDATE = "startDate";

    public static final String FUNC_CLOSECHANNEL = "CloseChannel";

    public static final String FUNC_channelTimeout = "channelTimeout";

    public static final String FUNC_ROOTS = "roots";

    public static final String FUNC_ChannelTimeout = "ChannelTimeout";

    public static final String FUNC_ADDBALANCE = "AddBalance";

    public static final String FUNC_VERIFYMERKLE = "verifyMerkle";
    
    public static final String FUNC_GREET = "greet";
    
    public static final String FUNC_NEWGREETING = "newGreeting";
    
	public static final Event MODIFIED_EVENT = new Event("Modified",
			Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {
			}, new TypeReference<Utf8String>(true) {
			}, new TypeReference<Utf8String>() {
			}, new TypeReference<Utf8String>() {
			}));;

    @Deprecated
    protected MerkleContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected MerkleContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected MerkleContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected MerkleContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> channelRecipient(String string) {
        final Function function = new Function(FUNC_CHANNELRECIPIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> channelSender(String string) {
        final Function function = new Function(FUNC_CHANNELSENDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> startDate() {
        final Function function = new Function(FUNC_STARTDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    /*public RemoteFunctionCall<TransactionReceipt> CloseChannel(BigInteger _amount, List<byte[]> proof) {
        final Function function = new Function(
                FUNC_CLOSECHANNEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }*/
    
    public Future<Uint256> CloseChannel(BigInteger _amount, List<byte[]> proof) {
        final Function function = new Function(
                FUNC_CLOSECHANNEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return (Future<Uint256>) executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> channelTimeout() {
        final Function function = new Function(FUNC_channelTimeout, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<byte[]> roots() {
        final Function function = new Function(FUNC_ROOTS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}));
        return executeRemoteCallSingleValueReturn(function, byte[].class);
    }

    public RemoteFunctionCall<TransactionReceipt> ChannelTimeout() {
        final Function function = new Function(
                FUNC_ChannelTimeout, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> AddBalance(byte[] _newRoot, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ADDBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_newRoot)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<Boolean> verifyMerkle(byte[] root, byte[] leaf, List<byte[]> proof) {
        final Function function = new Function(FUNC_VERIFYMERKLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(root), 
                new org.web3j.abi.datatypes.generated.Bytes32(leaf), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }
    
    public static RemoteCall<MerkleContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _MerkleContract) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_MerkleContract)));
        return deployRemoteCall(MerkleContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }
    
    public RemoteFunctionCall<String> greet() {
        final Function function = new Function(FUNC_GREET, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }
    
    public RemoteFunctionCall<TransactionReceipt> newGreeting(String _greeting) {
        final Function function = new Function(
                FUNC_NEWGREETING, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(_greeting)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }
    
    public static class ModifiedEventResponse extends BaseEventResponse {
        public byte[] oldGreetingIdx;

        public byte[] newGreetingIdx;

        public String oldGreeting;

        public String newGreeting;
    }
    
    public List<ModifiedEventResponse> getModifiedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(MODIFIED_EVENT, transactionReceipt);
        ArrayList<ModifiedEventResponse> responses = new ArrayList<ModifiedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ModifiedEventResponse typedResponse = new ModifiedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.oldGreetingIdx = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newGreetingIdx = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.oldGreeting = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.newGreeting = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }
    
    public List<Object> SeeBalance() {
        return (List<Object>) web3j.ethGetBalance(contractAddress, DefaultBlockParameter.valueOf(contractBinary));
    }
    
    public Future<Address> ReceiverAddress() {
        return (Future<Address>) web3j.ethAccounts();
    }

    public Future<Address> SenderAddress() {
        return (Future<Address>) web3j.ethAccounts();
    }
    
    public static class verifyMerkle extends MerkleContract {
        public verifyMerkle(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
            super(contractAddress, web3j, transactionManager, contractGasProvider);
        }

        @Override
        public RemoteFunctionCall<Boolean> verifyMerkle(byte[] root, byte[] leaf, List<byte[]> proof) {
            return super.verifyMerkle(root, leaf, proof);
        }
    }


    @Deprecated
    public static MerkleContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new MerkleContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static MerkleContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new MerkleContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static MerkleContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new MerkleContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static MerkleContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new MerkleContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<MerkleContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String to, BigInteger _timeout, byte[] _root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.generated.Bytes32(_root)));
        return deployRemoteCall(MerkleContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<MerkleContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String to, BigInteger _timeout, byte[] _root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.generated.Bytes32(_root)));
        return deployRemoteCall(MerkleContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<MerkleContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String to, BigInteger _timeout, byte[] _root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.generated.Bytes32(_root)));
        return deployRemoteCall(MerkleContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<MerkleContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String to, BigInteger _timeout, byte[] _root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.generated.Bytes32(_root)));
        return deployRemoteCall(MerkleContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }
}
