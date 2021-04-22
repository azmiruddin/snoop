package tub.ods.pch.channel.model;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
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
 */
@SuppressWarnings("rawtypes")
public class EndpointRegistryContract extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610539806100206000396000f30060806040526004361061004b5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663028a582e811461005057806308b5a85a146100f3575b600080fd5b34801561005c57600080fd5b5061007e73ffffffffffffffffffffffffffffffffffffffff6004351661014e565b6040805160208082528351818301528351919283929083019185019080838360005b838110156100b85781810151838201526020016100a0565b50505050905090810190601f1680156100e55780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b3480156100ff57600080fd5b506040805160206004803580820135601f810184900484028501840190955284845261014c9436949293602493928401919081908401838280828437509497506102049650505050505050565b005b73ffffffffffffffffffffffffffffffffffffffff81166000908152602081815260409182902080548351601f60026000196101006001861615020190931692909204918201849004840281018401909452808452606093928301828280156101f85780601f106101cd576101008083540402835291602001916101f8565b820191906000526020600020905b8154815290600101906020018083116101db57829003601f168201915b50505050509050919050565b60008161022181602060405190810160405280600081525061039e565b15156001141561023057600080fd5b336000908152602081815260409182902080548351601f600260001961010060018616150201909316929092049182018490048402810184019094528084529094506102d692918591908301828280156102cb5780601f106102a0576101008083540402835291602001916102cb565b820191906000526020600020905b8154815290600101906020018083116102ae57829003601f168201915b50505050508461039e565b156102e057610399565b3360009081526020818152604090912084516102fe92860190610472565b50604080516020808252855181830152855133937f3a62a9d7855df5303e50b0440124304fefafde7f677fc33787b784fc92cfa6189388939092839283019185019080838360005b8381101561035e578181015183820152602001610346565b50505050905090810190601f16801561038b5780820380516001836020036101000a031916815260200191505b509250505060405180910390a25b505050565b6000816040518082805190602001908083835b602083106103d05780518252601f1990920191602091820191016103b1565b51815160209384036101000a6000190180199092169116179052604051919093018190038120885190955088945090928392508401908083835b602083106104295780518252601f19909201916020918201910161040a565b6001836020036101000a03801982511681845116808217855250505050505090500191505060405180910390206000191614156104685750600161046c565b5060005b92915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106104b357805160ff19168380011785556104e0565b828001600101855582156104e0579182015b828111156104e05782518255916020019190600101906104c5565b506104ec9291506104f0565b5090565b61050a91905b808211156104ec57600081556001016104f6565b905600a165627a7a72305820fb0d0cba2b304346c1c592248fff9ca78810c68bb19c9ace3a0ef7bbc4d7006b0029";

    public static final String FUNC_FINDENDPOINTBYADDRESS = "findEndpointByAddress";

    public static final String FUNC_REGISTERENDPOINT = "registerEndpoint";

    public static final Event ADDRESSREGISTERED_EVENT = new Event("AddressRegistered", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}));
    ;

    @Deprecated
    protected EndpointRegistryContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected EndpointRegistryContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected EndpointRegistryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected EndpointRegistryContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> findEndpointByAddress(String eth_address) {
        final Function function = new Function(FUNC_FINDENDPOINTBYADDRESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, eth_address)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> registerEndpoint(String socket) {
        final Function function = new Function(
                FUNC_REGISTERENDPOINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(socket)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<AddressRegisteredEventResponse> getAddressRegisteredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ADDRESSREGISTERED_EVENT, transactionReceipt);
        ArrayList<AddressRegisteredEventResponse> responses = new ArrayList<AddressRegisteredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AddressRegisteredEventResponse typedResponse = new AddressRegisteredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.eth_address = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.socket = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<AddressRegisteredEventResponse> addressRegisteredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, AddressRegisteredEventResponse>() {
            @Override
            public AddressRegisteredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ADDRESSREGISTERED_EVENT, log);
                AddressRegisteredEventResponse typedResponse = new AddressRegisteredEventResponse();
                typedResponse.log = log;
                typedResponse.eth_address = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.socket = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<AddressRegisteredEventResponse> addressRegisteredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ADDRESSREGISTERED_EVENT));
        return addressRegisteredEventFlowable(filter);
    }

    @Deprecated
    public static EndpointRegistryContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new EndpointRegistryContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static EndpointRegistryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new EndpointRegistryContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static EndpointRegistryContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new EndpointRegistryContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static EndpointRegistryContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new EndpointRegistryContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<EndpointRegistryContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EndpointRegistryContract.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EndpointRegistryContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EndpointRegistryContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<EndpointRegistryContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(EndpointRegistryContract.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<EndpointRegistryContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(EndpointRegistryContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class AddressRegisteredEventResponse extends BaseEventResponse {
        public String eth_address;

        public String socket;
    }
}
