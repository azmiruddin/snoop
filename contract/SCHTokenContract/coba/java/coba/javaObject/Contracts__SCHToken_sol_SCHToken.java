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
public class Contracts__SCHToken_sol_SCHToken extends Contract {
    public static final String BINARY = "60c0604052600b60808190527f53696d706c65546f6b656e00000000000000000000000000000000000000000060a090815262000040916003919062000405565b506040805180820190915260038082527f53494d00000000000000000000000000000000000000000000000000000000006020909201918252620000879160049162000405565b5060126005556127106006556040805180820190915260098082527f53434820546f6b656e00000000000000000000000000000000000000000000006020909201918252620000d99160089162000405565b506040805180820190915260038082527f53434800000000000000000000000000000000000000000000000000000000006020909201918252620001209160099162000405565b506040805180820190915260048082527f48302e310000000000000000000000000000000000000000000000000000000060209092019182526200016791600a9162000405565b50600b805461ff001960ff19909116601217166101001790553480156200018d57600080fd5b5060405162000dbf38038062000dbf83398101604081815282516020840151828501516006547f43505a7500000000000000000000000000000000000000000000000000000000865260006004870181905260248701919091529351928601959190910193909291829173__contracts/_SCHToken.sol:ERC20Lib______916343505a75916044808201928692909190829003018186803b1580156200023357600080fd5b505af415801562000248573d6000803e3d6000fd5b505060078054600160a060020a0319163317905550508351855114801562000271575060008551115b15156200027d57600080fd5b5060009050805b8451821015620002cf57620002c18483815181101515620002a157fe5b602090810290910101518290640100000000620008c66200034082021704565b905081600101915062000284565b633b9aca008114620002e057600080fd5b633b9aca009250600091505b845182101562000335576200032885838151811015156200030957fe5b9060200190602002015162000354640100000000026401000000009004565b50816001019150620002ec565b5050505050620004aa565b818101828110156200034e57fe5b92915050565b604080517fd1717fd4000000000000000000000000000000000000000000000000000000008152600060048201819052600160a060020a0384166024830152915173__contracts/_SCHToken.sol:ERC20Lib______9163d1717fd4916044808301926020929190829003018186803b158015620003d157600080fd5b505af4158015620003e6573d6000803e3d6000fd5b505050506040513d6020811015620003fd57600080fd5b505192915050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106200044857805160ff191683800117855562000478565b8280016001018555821562000478579182015b82811115620004785782518255916020019190600101906200045b565b50620004869291506200048a565b5090565b620004a791905b8082111562000486576000815560010162000491565b90565b61090580620004ba6000396000f3006080604052600436106100c45763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166306fdde0381146100d6578063095ea7b31461016057806318160ddd1461019857806323b872dd146101bf5780632ff2e9dc146101e9578063313ce567146101fe57806354fd4d501461022957806370a082311461023e57806392ff0d311461025f57806395d89b41146102745780639cd2370714610289578063a9059cbb146102a5578063dd62ed3e146102c9575b3480156100d057600080fd5b50600080fd5b3480156100e257600080fd5b506100eb6102f0565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561012557818101518382015260200161010d565b50505050905090810190601f1680156101525780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561016c57600080fd5b50610184600160a060020a036004351660243561037e565b604080519115158252519081900360200190f35b3480156101a457600080fd5b506101ad6103bd565b60408051918252519081900360200190f35b3480156101cb57600080fd5b50610184600160a060020a03600435811690602435166044356103c3565b3480156101f557600080fd5b506101ad610404565b34801561020a57600080fd5b5061021361040a565b6040805160ff9092168252519081900360200190f35b34801561023557600080fd5b506100eb610413565b34801561024a57600080fd5b506101ad600160a060020a036004351661046e565b34801561026b57600080fd5b5061018461051c565b34801561028057600080fd5b506100eb61052a565b34801561029557600080fd5b506102a36004351515610585565b005b3480156102b157600080fd5b50610184600160a060020a0360043516602435610612565b3480156102d557600080fd5b506101ad600160a060020a036004358116906024351661064a565b6008805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103765780601f1061034b57610100808354040283529160200191610376565b820191906000526020600020905b81548152906001019060200180831161035957829003601f168201915b505050505081565b600b54600090610100900460ff16806103a15750600754600160a060020a031633145b15156103ac57600080fd5b6103b68383610701565b9392505050565b60025490565b600b54600090610100900460ff16806103e65750600754600160a060020a031633145b15156103f157600080fd5b6103fc848484610784565b949350505050565b60065481565b600b5460ff1681565b600a805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103765780601f1061034b57610100808354040283529160200191610376565b604080517fd1717fd4000000000000000000000000000000000000000000000000000000008152600060048201819052600160a060020a0384166024830152915173__contracts/_SCHToken.sol:ERC20Lib______9163d1717fd4916044808301926020929190829003018186803b1580156104ea57600080fd5b505af41580156104fe573d6000803e3d6000fd5b505050506040513d602081101561051457600080fd5b505192915050565b600b54610100900460ff1681565b6009805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156103765780601f1061034b57610100808354040283529160200191610376565b600754600160a060020a0316331461059c57600080fd5b600b5460ff61010090910416151581151514156105b857600080fd5b600b805482151561010090810261ff00199092169190911791829055604080519190920460ff161515815290517f6488c20eb299903c41aa1b53c3ad5a3140aca395935e57cc52c1cc8dae8d9e179181900360200190a150565b600b54600090610100900460ff16806106355750600754600160a060020a031633145b151561064057600080fd5b6103b68383610843565b604080517f88fd6510000000000000000000000000000000000000000000000000000000008152600060048201819052600160a060020a03808616602484015284166044830152915173__contracts/_SCHToken.sol:ERC20Lib______916388fd6510916064808301926020929190829003018186803b1580156106ce57600080fd5b505af41580156106e2573d6000803e3d6000fd5b505050506040513d60208110156106f857600080fd5b50519392505050565b604080517f5bada338000000000000000000000000000000000000000000000000000000008152600060048201819052600160a060020a038516602483015260448201849052915173__contracts/_SCHToken.sol:ERC20Lib______91635bada338916064808301926020929190829003018186803b1580156106ce57600080fd5b604080517f45008797000000000000000000000000000000000000000000000000000000008152600060048201819052600160a060020a0380871660248401528516604483015260648201849052915173__contracts/_SCHToken.sol:ERC20Lib______916345008797916084808301926020929190829003018186803b15801561080f57600080fd5b505af4158015610823573d6000803e3d6000fd5b505050506040513d602081101561083957600080fd5b5051949350505050565b604080517f2d0277b9000000000000000000000000000000000000000000000000000000008152600060048201819052600160a060020a038516602483015260448201849052915173__contracts/_SCHToken.sol:ERC20Lib______91632d0277b9916064808301926020929190829003018186803b1580156106ce57600080fd5b818101828110156108d357fe5b929150505600a165627a7a72305820f1a73cb76349fcca30c4baad3127958ecedb833f2d8e0ed75713f87bd761b3a00029";

    public static final String FUNC_NAME = "name";

    public static final String FUNC_APPROVE = "approve";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFERFROM = "transferFrom";

    public static final String FUNC_INITIAL_SUPPLY = "INITIAL_SUPPLY";

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
    protected Contracts__SCHToken_sol_SCHToken(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Contracts__SCHToken_sol_SCHToken(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Contracts__SCHToken_sol_SCHToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Contracts__SCHToken_sol_SCHToken(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
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

    public RemoteFunctionCall<BigInteger> INITIAL_SUPPLY() {
        final Function function = new Function(FUNC_INITIAL_SUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    public RemoteFunctionCall<BigInteger> balanceOf(String who) {
        final Function function = new Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, who)), 
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

    public RemoteFunctionCall<TransactionReceipt> transfer(String _to, BigInteger _value) {
        final Function function = new Function(
                FUNC_TRANSFER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, _to), 
                new org.web3j.abi.datatypes.generated.Uint256(_value)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> allowance(String owner, String spender) {
        final Function function = new Function(FUNC_ALLOWANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, owner), 
                new org.web3j.abi.datatypes.Address(160, spender)), 
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
            typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse.from = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.spender = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.value = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
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
    public static Contracts__SCHToken_sol_SCHToken load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts__SCHToken_sol_SCHToken(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Contracts__SCHToken_sol_SCHToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Contracts__SCHToken_sol_SCHToken(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Contracts__SCHToken_sol_SCHToken load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Contracts__SCHToken_sol_SCHToken(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Contracts__SCHToken_sol_SCHToken load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Contracts__SCHToken_sol_SCHToken(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Contracts__SCHToken_sol_SCHToken> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, List<String> _wallets, List<BigInteger> _amounts, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_wallets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(Contracts__SCHToken_sol_SCHToken.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Contracts__SCHToken_sol_SCHToken> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, List<String> _wallets, List<BigInteger> _amounts, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_wallets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(Contracts__SCHToken_sol_SCHToken.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Contracts__SCHToken_sol_SCHToken> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, List<String> _wallets, List<BigInteger> _amounts, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_wallets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(Contracts__SCHToken_sol_SCHToken.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Contracts__SCHToken_sol_SCHToken> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, List<String> _wallets, List<BigInteger> _amounts, BigInteger totalSupply) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.Address>(
                        org.web3j.abi.datatypes.Address.class,
                        org.web3j.abi.Utils.typeMap(_wallets, org.web3j.abi.datatypes.Address.class)), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Uint256>(
                        org.web3j.abi.datatypes.generated.Uint256.class,
                        org.web3j.abi.Utils.typeMap(_amounts, org.web3j.abi.datatypes.generated.Uint256.class)), 
                new org.web3j.abi.datatypes.generated.Uint256(totalSupply)));
        return deployRemoteCall(Contracts__SCHToken_sol_SCHToken.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class TransferableChangedEventResponse extends BaseEventResponse {
        public Boolean transferable;
    }

    public static class TransferEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger value;
    }

    public static class ApprovalEventResponse extends BaseEventResponse {
        public String owner;

        public String spender;

        public BigInteger value;
    }
}
