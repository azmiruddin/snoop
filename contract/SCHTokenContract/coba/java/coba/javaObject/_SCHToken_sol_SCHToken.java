package coba/javaObject;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.abi.datatypes.generated.Uint8;
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
public class _SCHToken_sol_SCHToken extends Contract {
    public static final String BINARY = "60c0604052600960808190527f53434820546f6b656e000000000000000000000000000000000000000000000060a09081526200004091600891906200024b565b506040805180820190915260038082527f5343480000000000000000000000000000000000000000000000000000000000602090920191825262000087916009916200024b565b506040805180820190915260048082527f48302e31000000000000000000000000000000000000000000000000000000006020909201918252620000ce91600a916200024b565b50600b805461ff001960ff1990911660121716610100179055348015620000f457600080fd5b5060405162000afd38038062000afd833981016040908152815160208301519183015160078054600160a060020a0319163317905591830180519190930180519093929160009182911480156200014c575060008551115b15156200015857600080fd5b5060009050805b8451821015620001aa576200019c84838151811015156200017c57fe5b602090810290910101518290640100000000620007ac6200023782021704565b90508160010191506200015f565b633b9aca008114620001bb57600080fd5b633b9aca009250600091505b84518210156200022c578382815181101515620001e057fe5b90602001906020020151600560008785815181101515620001fd57fe5b6020908102909101810151600160a060020a0316825281019190915260400160002055600190910190620001c7565b5050505050620002f0565b818101828110156200024557fe5b92915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200028e57805160ff1916838001178555620002be565b82800160010185558215620002be579182015b82811115620002be578251825591602001919060010190620002a1565b50620002cc929150620002d0565b5090565b620002ed91905b80821115620002cc5760008155600101620002d7565b90565b6107fd80620003006000396000f3006080604052600436106100b95763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100cb578063095ea7b31461015557806318160ddd1461017b57806323b872dd146101a2578063313ce567146101cc57806354fd4d50146101f757806370a082311461020c57806392ff0d311461022d57806395d89b41146102565780639cd237071461026b578063a9059cbb14610285578063dd62ed3e146102a9575b3480156100c557600080fd5b50600080fd5b3480156100d757600080fd5b506100e06102d0565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561011a578181015183820152602001610102565b50505050905090810190601f1680156101475780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561016157600080fd5b50610179600160a060020a036004351660243561035e565b005b34801561018757600080fd5b506101906103c0565b60408051918252519081900360200190f35b3480156101ae57600080fd5b50610179600160a060020a03600435811690602435166044356103c6565b3480156101d857600080fd5b506101e1610529565b6040805160ff9092168252519081900360200190f35b34801561020357600080fd5b506100e0610532565b34801561021857600080fd5b50610190600160a060020a036004351661058d565b34801561023957600080fd5b506102426105a8565b604080519115158252519081900360200190f35b34801561026257600080fd5b506100e06105b6565b34801561027757600080fd5b506101796004351515610611565b34801561029157600080fd5b50610179600160a060020a036004351660243561069e565b3480156102b557600080fd5b50610190600160a060020a0360043581169060243516610780565b6008805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103565780601f1061032b57610100808354040283529160200191610356565b820191906000526020600020905b81548152906001019060200180831161033957829003601f168201915b505050505081565b336000818152600660209081526040808320600160a060020a03871680855290835292819020859055805185815290519293927f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925929181900390910190a35050565b60005481565b600160a060020a03831660009081526005602052604090205481118015906104115750600160a060020a03831660009081526006602090815260408083203384529091529020548111155b801561041d5750600081115b151561042857600080fd5b600160a060020a038216600090815260056020526040902054610451908263ffffffff6107ac16565b600160a060020a038084166000908152600560205260408082209390935590851681522054610486908263ffffffff6107bf16565b600160a060020a03841660009081526005602090815260408083209390935560068152828220338352905220546104c3908263ffffffff6107bf16565b600160a060020a03808516600081815260066020908152604080832033845282529182902094909455805185815290519286169391927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef929181900390910190a3505050565b600b5460ff1681565b600a805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103565780601f1061032b57610100808354040283529160200191610356565b600160a060020a031660009081526005602052604090205490565b600b54610100900460ff1681565b6009805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103565780601f1061032b57610100808354040283529160200191610356565b600754600160a060020a0316331461062857600080fd5b600b5460ff610100909104161515811515141561064457600080fd5b600b805482151561010090810261ff00199092169190911791829055604080519190920460ff161515815290517f6488c20eb299903c41aa1b53c3ad5a3140aca395935e57cc52c1cc8dae8d9e179181900360200190a150565b6040366044146106aa57fe5b3360009081526005602052604090205482118015906106c95750600082115b15156106d457600080fd5b336000908152600560205260409020546106f4908363ffffffff6107bf16565b3360009081526005602052604080822092909255600160a060020a03851681522054610726908363ffffffff6107ac16565b600160a060020a0384166000818152600560209081526040918290209390935580518581529051919233927fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef9281900390910190a3505050565b600160a060020a0380821660009081526006602090815260408083209386168352929052205492915050565b818101828110156107b957fe5b92915050565b6000828211156107cb57fe5b509003905600a165627a7a72305820ce8769643638bb8b3b25af2d81708fcf402a6d9337ac874d1ff9927c98ef4ff70029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_DECIMALS = "decimals";

    public static final String FUNC_VERSION = "version";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_TRANSFERABLE = "transferable";

    public static final String FUNC_SYMBOL = "symbol";

    public static final String FUNC_SETTRANSFERABLE = "setTransferable";

    public static final String FUNC_TRANSFER = "transfer";

    public static final String FUNC_ALLOWANCE = "allowance";

    public static final Event TRANSFERABLECHANGED_EVENT = new Event("TransferableChanged", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
    ;

    public static final Event TRANSFER_EVENT = new Event("Transfer", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event APPROVAL_EVENT = new Event("Approval", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected _SCHToken_sol_SCHToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected _SCHToken_sol_SCHToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected _SCHToken_sol_SCHToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected _SCHToken_sol_SCHToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<String> name() {
        final Function function = new Function(FUNC_NAME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> approve(String _spender, BigInteger _value) {
        final Function function = new Function(
                FUNC_APPROVE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _spender), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final Function function = new Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferFrom(String _from, String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFERFROM, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _from), 
                new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> decimals() {
        final Function function = new Function(FUNC_DECIMALS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint8>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> version() {
        final Function function = new Function(FUNC_VERSION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String _owner) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<Boolean> transferable() {
        final Function function = new Function(FUNC_TRANSFERABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<String> symbol() {
        final Function function = new Function(FUNC_SYMBOL, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setTransferable(Boolean _transferable) {
        final Function function = new Function(
                FUNC_SETTRANSFERABLE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Bool(_transferable)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transfer(String _recipient, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _recipient), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> allowance(String _spender, String _owner) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _spender), 
                new org.web3j.abi.datatypes.Address(160, _owner)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public List<TransferableChangedEventResponse> getTransferableChangedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERABLECHANGED_EVENT, transactionReceipt);
        ArrayList<TransferableChangedEventResponse> responses = new ArrayList<TransferableChangedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferableChangedEventResponse typedResponse = new TransferableChangedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.transferable = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferableChangedEventResponse> transferableChangedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferableChangedEventResponse>() {
            @Override
            public TransferableChangedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERABLECHANGED_EVENT, log);
                TransferableChangedEventResponse typedResponse = new TransferableChangedEventResponse();
                typedResponse.log = log;
                typedResponse.transferable = (Boolean) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferableChangedEventResponse> transferableChangedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERABLECHANGED_EVENT));
        return transferableChangedEventFlowable(filter);
    }

    public List<TransferEventResponse> getTransferEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFER_EVENT, transactionReceipt);
        ArrayList<TransferEventResponse> responses = new ArrayList<TransferEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferEventResponse typedResponse = new TransferEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferEventResponse> transferEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferEventResponse>() {
            @Override
            public TransferEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFER_EVENT, log);
                TransferEventResponse typedResponse = new TransferEventResponse();
                typedResponse.log = log;
                typedResponse._from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferEventResponse> transferEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFER_EVENT));
        return transferEventFlowable(filter);
    }

    public List<ApprovalEventResponse> getApprovalEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(APPROVAL_EVENT, transactionReceipt);
        ArrayList<ApprovalEventResponse> responses = new ArrayList<ApprovalEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ApprovalEventResponse typedResponse = new ApprovalEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ApprovalEventResponse>() {
            @Override
            public ApprovalEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(APPROVAL_EVENT, log);
                ApprovalEventResponse typedResponse = new ApprovalEventResponse();
                typedResponse.log = log;
                typedResponse._owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse._spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse._value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ApprovalEventResponse> approvalEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(APPROVAL_EVENT));
        return approvalEventFlowable(filter);
    }

    @Deprecated
    public static _SCHToken_sol_SCHToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new _SCHToken_sol_SCHToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static _SCHToken_sol_SCHToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new _SCHToken_sol_SCHToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static _SCHToken_sol_SCHToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new _SCHToken_sol_SCHToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static _SCHToken_sol_SCHToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new _SCHToken_sol_SCHToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<_SCHToken_sol_SCHToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> _wallets, List<BigInteger> _amounts, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_wallets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(_SCHToken_sol_SCHToken.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<_SCHToken_sol_SCHToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> _wallets, List<BigInteger> _amounts, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_wallets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(_SCHToken_sol_SCHToken.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<_SCHToken_sol_SCHToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> _wallets, List<BigInteger> _amounts, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_wallets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(_SCHToken_sol_SCHToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<_SCHToken_sol_SCHToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> _wallets, List<BigInteger> _amounts, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_wallets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(_SCHToken_sol_SCHToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class TransferableChangedEventResponse extends BaseEventResponse {
        public Boolean transferable;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String _from;

        public String _to;

        public BigInteger _value;
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String _owner;

        public String _spender;

        public BigInteger _value;
    }
}
