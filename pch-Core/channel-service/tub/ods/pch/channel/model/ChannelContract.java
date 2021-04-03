package tub.ods.pch.channel.model;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple3;
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
 * <p>Generated with web3j version 4.5.5.
 */
@SuppressWarnings("rawtypes")
public class ChannelContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161010080610b9483398101604090815281516020830151918301516060840151608085015160a086015160c087015160e090970151949693949293919290919033600160a060020a0389161461006957600080fd5b600160a060020a03878116908616141561008257600080fd5b600160a060020a03868116908616141561009b57600080fd5b60008210156100a957600080fd5b600083116100b657600080fd5b60008410156100c457600080fd5b60098054600160a060020a0319908116600160a060020a03998a1617909155600b8054821697891697909717909655600a8054871695881695909517909455600d8054861694871694909417909355600880549094169590941694909417909155600092835560019190915560025543600355610a4d90819061014790396000f3006080604052600436106101325763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166304fb9e8181146101445780630baff0531461016b578063109e94cf1461018057806311da60b4146101b15780633ec045a6146101c8578063481c6a75146101dd578063597e1fb5146101f25780635f88eade1461020757806367e404ce1461021c5780636d2381b3146102315780637ebdc4781461027057806383197ef01461028557806388897c371461029a5780638f775839146102af578063affed0e0146102c4578063b4def1e7146102d9578063b69ef8a8146102ee578063b6b55f2514610303578063d50726071461032f578063dd00a9dc14610344578063e7084b7e146103a4578063eec5f1dc146103b9578063f7260d3e146103da575b34801561013e57600080fd5b50600080fd5b34801561015057600080fd5b506101596103ef565b60408051918252519081900360200190f35b34801561017757600080fd5b506101596103f5565b34801561018c57600080fd5b506101956103fb565b60408051600160a060020a039092168252519081900360200190f35b3480156101bd57600080fd5b506101c661040a565b005b3480156101d457600080fd5b506101956104c3565b3480156101e957600080fd5b506101956104d2565b3480156101fe57600080fd5b506101596104e1565b34801561021357600080fd5b506101596104e7565b34801561022857600080fd5b506101956104ed565b34801561023d57600080fd5b506102466104fc565b60408051600160a060020a0394851681529290931660208301528183015290519081900360600190f35b34801561027c57600080fd5b50610159610518565b34801561029157600080fd5b506101c661051e565b3480156102a657600080fd5b506101c6610569565b3480156102bb57600080fd5b50610159610629565b3480156102d057600080fd5b5061015961062f565b3480156102e557600080fd5b50610159610635565b3480156102fa57600080fd5b5061015961063b565b34801561030f57600080fd5b5061031b600435610641565b604080519115158252519081900360200190f35b34801561033b57600080fd5b506101596107de565b34801561035057600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526101c69482359460248035953695946064949201919081908401838280828437509497506107e49650505050505050565b3480156103b057600080fd5b5061015961092c565b3480156103c557600080fd5b506101c6600160a060020a0360043516610932565b3480156103e657600080fd5b50610195610a12565b60045490565b60075490565b600b54600160a060020a031690565b604080517f57389014000000000000000000000000000000000000000000000000000000008152600060048201819052915173__ChannelLibrary.sol:ChannelLibrary_____9263573890149260248082019391829003018186803b15801561047357600080fd5b505af4158015610487573d6000803e3d6000fd5b505060065460408051918252517ffe501c6f860c82db0609c0e0e7571f4a08c991e3b653cb35a7f105c84caccb999350908190036020019150a1565b600d54600160a060020a031690565b600854600160a060020a031690565b60055490565b60035490565b600954600160a060020a031690565b600954600a54600c54600160a060020a03928316939290911691565b60015490565b600854600160a060020a0316331461053557600080fd5b60065460001061054457600080fd5b6007546000108061055a57506002546005540143115b151561056557600080fd5b6000ff5b604080517f61fc47d4000000000000000000000000000000000000000000000000000000008152600060048201819052915173__ChannelLibrary.sol:ChannelLibrary_____926361fc47d49260248082019391829003018186803b1580156105d257600080fd5b505af41580156105e6573d6000803e3d6000fd5b505060055460408051338152602081019290925280517f824a51662384e98063aa4168d3389dbd9d8e14e436be432fcc5490758c01d4af945091829003019150a1565b60065490565b600e5490565b600f5490565b600c5490565b60008060008073__ChannelLibrary.sol:ChannelLibrary_____6336da8a209091866040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018083815260200182815260200192505050604080518083038186803b1580156106ba57600080fd5b505af41580156106ce573d6000803e3d6000fd5b505050506040513d60408110156106e457600080fd5b5080516020909101519092509050600182151514156107d757600854604080517ffc0c546a00000000000000000000000000000000000000000000000000000000815290517ffdbb5aee5d86223b95ec2a3ef27bf36cdb0a4ae42a4823bdd7c091f143dbf83d92600160a060020a03169163fc0c546a9160048083019260209291908290030181600087803b15801561077c57600080fd5b505af1158015610790573d6000803e3d6000fd5b505050506040513d60208110156107a657600080fd5b505160408051600160a060020a03909216825233602083015281810184905260006060830152519081900360800190a15b5092915050565b60025490565b6040517fc41168e60000000000000000000000000000000000000000000000000000000081526000600482018181523060248401819052604484018790526064840186905260a060848501908152855160a4860152855173__ChannelLibrary.sol:ChannelLibrary_____9563c41168e695948a938a938a9360c4019060208501908083838c5b8381101561088457818101518382015260200161086c565b50505050905090810190601f1680156108b15780820380516001836020036101000a031916815260200191505b50965050505050505060006040518083038186803b1580156108d257600080fd5b505af41580156108e6573d6000803e3d6000fd5b505060055460408051338152602081019290925280517f770342ee36990141b5b1f4b3b41a184d7968647ac7f0dbfd2d86d566c468027d945091829003019150a1505050565b60005490565b600854600160a060020a0316331461094957600080fd5b604080517f0f454991000000000000000000000000000000000000000000000000000000008152600060048201819052600160a060020a0384166024830152915173__ChannelLibrary.sol:ChannelLibrary_____92630f4549919260448082019391829003018186803b1580156109c157600080fd5b505af41580156109d5573d6000803e3d6000fd5b505060075460408051918252517f57885380de2bb12ea3e34bdf33c55c186dedf37aefebd60551783af2fc1df1689350908190036020019150a150565b600a54600160a060020a0316905600a165627a7a72305820903c7ddd9bc8b8ab7e61aa82b5f25e9c06f8f41ea3285f258d13905082dbc1010029";

    public static final String FUNC_CLOSEREQUESTED = "closeRequested";

    public static final String FUNC_AUDITED = "audited";

    public static final String FUNC_CLIENT = "client";

    public static final String FUNC_SETTLE = "settle";

    public static final String FUNC_AUDITOR = "auditor";

    public static final String FUNC_MANAGER = "manager";

    public static final String FUNC_CLOSED = "closed";

    public static final String FUNC_OPENED = "opened";

    public static final String FUNC_SENDER = "sender";

    public static final String FUNC_ADDRESSANDBALANCE = "addressAndBalance";

    public static final String FUNC_SETTLETIMEOUT = "settleTimeout";

    public static final String FUNC_DESTROY = "destroy";

    public static final String FUNC_REQUEST_CLOSE = "request_close";

    public static final String FUNC_SETTLED = "settled";

    public static final String FUNC_NONCE = "nonce";

    public static final String FUNC_COMPLETEDTRANSFERS = "completedTransfers";

    public static final String FUNC_BALANCE = "balance";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_AUDITTIMEOUT = "auditTimeout";

    public static final String FUNC_CLOSE = "close";

    public static final String FUNC_CLOSETIMEOUT = "closeTimeout";

    public static final String FUNC_AUDIT = "audit";

    public static final String FUNC_RECEIVER = "receiver";

    public static final Event CHANNELNEWBALANCE_EVENT = new Event("ChannelNewBalance", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CHANNELCLOSEREQUESTED_EVENT = new Event("ChannelCloseRequested", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CHANNELCLOSED_EVENT = new Event("ChannelClosed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event TRANSFERUPDATED_EVENT = new Event("TransferUpdated", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CHANNELSETTLED_EVENT = new Event("ChannelSettled", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event CHANNELAUDITED_EVENT = new Event("ChannelAudited", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event CHANNELSECRETREVEALED_EVENT = new Event("ChannelSecretRevealed", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Address>() {}));
    ;

    @Deprecated
    protected ChannelContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChannelContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChannelContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChannelContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<BigInteger> closeRequested() {
        final Function function = new Function(FUNC_CLOSEREQUESTED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> audited() {
        final Function function = new Function(FUNC_AUDITED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> client() {
        final Function function = new Function(FUNC_CLIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> settle() {
        final Function function = new Function(
                FUNC_SETTLE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> auditor() {
        final Function function = new Function(FUNC_AUDITOR, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> manager() {
        final Function function = new Function(FUNC_MANAGER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> closed() {
        final Function function = new Function(FUNC_CLOSED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> opened() {
        final Function function = new Function(FUNC_OPENED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> sender() {
        final Function function = new Function(FUNC_SENDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Tuple3<String, String, BigInteger>> addressAndBalance() {
        final Function function = new Function(FUNC_ADDRESSANDBALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
        return new RemoteFunctionCall<Tuple3<String, String, BigInteger>>(function,
                new Callable<Tuple3<String, String, BigInteger>>() {
                    @Override
                    public Tuple3<String, String, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple3<String, String, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue());
                    }
                });
    }

    public RemoteFunctionCall<BigInteger> settleTimeout() {
        final Function function = new Function(FUNC_SETTLETIMEOUT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> destroy() {
        final Function function = new Function(
                FUNC_DESTROY, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> request_close() {
        final Function function = new Function(
                FUNC_REQUEST_CLOSE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> settled() {
        final Function function = new Function(FUNC_SETTLED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> nonce() {
        final Function function = new Function(FUNC_NONCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> completedTransfers() {
        final Function function = new Function(FUNC_COMPLETEDTRANSFERS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> balance() {
        final Function function = new Function(FUNC_BALANCE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger amount) {
        final Function function = new Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> auditTimeout() {
        final Function function = new Function(FUNC_AUDITTIMEOUT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> close(BigInteger nonce, BigInteger completed_transfers, byte[] signature) {
        final Function function = new Function(
                FUNC_CLOSE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(nonce), 
                new org.web3j.abi.datatypes.generated.Uint256(completed_transfers), 
                new org.web3j.abi.datatypes.DynamicBytes(signature)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> closeTimeout() {
        final Function function = new Function(FUNC_CLOSETIMEOUT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> audit(String auditor) {
        final Function function = new Function(
                FUNC_AUDIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, auditor)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> receiver() {
        final Function function = new Function(FUNC_RECEIVER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<ChannelNewBalanceEventResponse> getChannelNewBalanceEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELNEWBALANCE_EVENT, transactionReceipt);
        ArrayList<ChannelNewBalanceEventResponse> responses = new ArrayList<ChannelNewBalanceEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelNewBalanceEventResponse typedResponse = new ChannelNewBalanceEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.token_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.participant = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelNewBalanceEventResponse> channelNewBalanceEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelNewBalanceEventResponse>() {
            @Override
            public ChannelNewBalanceEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELNEWBALANCE_EVENT, log);
                ChannelNewBalanceEventResponse typedResponse = new ChannelNewBalanceEventResponse();
                typedResponse.log = log;
                typedResponse.token_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.participant = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.balance = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelNewBalanceEventResponse> channelNewBalanceEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELNEWBALANCE_EVENT));
        return channelNewBalanceEventFlowable(filter);
    }

    public List<ChannelCloseRequestedEventResponse> getChannelCloseRequestedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELCLOSEREQUESTED_EVENT, transactionReceipt);
        ArrayList<ChannelCloseRequestedEventResponse> responses = new ArrayList<ChannelCloseRequestedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelCloseRequestedEventResponse typedResponse = new ChannelCloseRequestedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.closing_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelCloseRequestedEventResponse> channelCloseRequestedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelCloseRequestedEventResponse>() {
            @Override
            public ChannelCloseRequestedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELCLOSEREQUESTED_EVENT, log);
                ChannelCloseRequestedEventResponse typedResponse = new ChannelCloseRequestedEventResponse();
                typedResponse.log = log;
                typedResponse.closing_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelCloseRequestedEventResponse> channelCloseRequestedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELCLOSEREQUESTED_EVENT));
        return channelCloseRequestedEventFlowable(filter);
    }

    public List<ChannelClosedEventResponse> getChannelClosedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELCLOSED_EVENT, transactionReceipt);
        ArrayList<ChannelClosedEventResponse> responses = new ArrayList<ChannelClosedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelClosedEventResponse typedResponse = new ChannelClosedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.closing_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelClosedEventResponse> channelClosedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelClosedEventResponse>() {
            @Override
            public ChannelClosedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELCLOSED_EVENT, log);
                ChannelClosedEventResponse typedResponse = new ChannelClosedEventResponse();
                typedResponse.log = log;
                typedResponse.closing_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelClosedEventResponse> channelClosedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELCLOSED_EVENT));
        return channelClosedEventFlowable(filter);
    }

    public List<TransferUpdatedEventResponse> getTransferUpdatedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERUPDATED_EVENT, transactionReceipt);
        ArrayList<TransferUpdatedEventResponse> responses = new ArrayList<TransferUpdatedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferUpdatedEventResponse typedResponse = new TransferUpdatedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.node_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<TransferUpdatedEventResponse> transferUpdatedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, TransferUpdatedEventResponse>() {
            @Override
            public TransferUpdatedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERUPDATED_EVENT, log);
                TransferUpdatedEventResponse typedResponse = new TransferUpdatedEventResponse();
                typedResponse.log = log;
                typedResponse.node_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<TransferUpdatedEventResponse> transferUpdatedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERUPDATED_EVENT));
        return transferUpdatedEventFlowable(filter);
    }

    public List<ChannelSettledEventResponse> getChannelSettledEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELSETTLED_EVENT, transactionReceipt);
        ArrayList<ChannelSettledEventResponse> responses = new ArrayList<ChannelSettledEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelSettledEventResponse typedResponse = new ChannelSettledEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelSettledEventResponse> channelSettledEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelSettledEventResponse>() {
            @Override
            public ChannelSettledEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELSETTLED_EVENT, log);
                ChannelSettledEventResponse typedResponse = new ChannelSettledEventResponse();
                typedResponse.log = log;
                typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelSettledEventResponse> channelSettledEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELSETTLED_EVENT));
        return channelSettledEventFlowable(filter);
    }

    public List<ChannelAuditedEventResponse> getChannelAuditedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELAUDITED_EVENT, transactionReceipt);
        ArrayList<ChannelAuditedEventResponse> responses = new ArrayList<ChannelAuditedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelAuditedEventResponse typedResponse = new ChannelAuditedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelAuditedEventResponse> channelAuditedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelAuditedEventResponse>() {
            @Override
            public ChannelAuditedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELAUDITED_EVENT, log);
                ChannelAuditedEventResponse typedResponse = new ChannelAuditedEventResponse();
                typedResponse.log = log;
                typedResponse.block_number = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelAuditedEventResponse> channelAuditedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELAUDITED_EVENT));
        return channelAuditedEventFlowable(filter);
    }

    public List<ChannelSecretRevealedEventResponse> getChannelSecretRevealedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELSECRETREVEALED_EVENT, transactionReceipt);
        ArrayList<ChannelSecretRevealedEventResponse> responses = new ArrayList<ChannelSecretRevealedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelSecretRevealedEventResponse typedResponse = new ChannelSecretRevealedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.secret = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.receiver_address = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelSecretRevealedEventResponse> channelSecretRevealedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelSecretRevealedEventResponse>() {
            @Override
            public ChannelSecretRevealedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELSECRETREVEALED_EVENT, log);
                ChannelSecretRevealedEventResponse typedResponse = new ChannelSecretRevealedEventResponse();
                typedResponse.log = log;
                typedResponse.secret = (byte[]) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.receiver_address = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelSecretRevealedEventResponse> channelSecretRevealedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELSECRETREVEALED_EVENT));
        return channelSecretRevealedEventFlowable(filter);
    }

    @Deprecated
    public static ChannelContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChannelContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ChannelContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChannelContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ChannelContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ChannelContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ChannelContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ChannelContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ChannelContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String manager_address, String sender, String client, String receiver, BigInteger close_timeout, BigInteger settle_timeout, BigInteger audit_timeout, String auditor) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, manager_address), 
                new org.web3j.abi.datatypes.Address(160, sender), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.Address(160, receiver), 
                new org.web3j.abi.datatypes.generated.Uint256(close_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(settle_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(audit_timeout), 
                new org.web3j.abi.datatypes.Address(160, auditor)));
        return deployRemoteCall(ChannelContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ChannelContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String manager_address, String sender, String client, String receiver, BigInteger close_timeout, BigInteger settle_timeout, BigInteger audit_timeout, String auditor) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, manager_address), 
                new org.web3j.abi.datatypes.Address(160, sender), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.Address(160, receiver), 
                new org.web3j.abi.datatypes.generated.Uint256(close_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(settle_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(audit_timeout), 
                new org.web3j.abi.datatypes.Address(160, auditor)));
        return deployRemoteCall(ChannelContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChannelContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String manager_address, String sender, String client, String receiver, BigInteger close_timeout, BigInteger settle_timeout, BigInteger audit_timeout, String auditor) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, manager_address), 
                new org.web3j.abi.datatypes.Address(160, sender), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.Address(160, receiver), 
                new org.web3j.abi.datatypes.generated.Uint256(close_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(settle_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(audit_timeout), 
                new org.web3j.abi.datatypes.Address(160, auditor)));
        return deployRemoteCall(ChannelContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChannelContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String manager_address, String sender, String client, String receiver, BigInteger close_timeout, BigInteger settle_timeout, BigInteger audit_timeout, String auditor) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, manager_address), 
                new org.web3j.abi.datatypes.Address(160, sender), 
                new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.Address(160, receiver), 
                new org.web3j.abi.datatypes.generated.Uint256(close_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(settle_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(audit_timeout), 
                new org.web3j.abi.datatypes.Address(160, auditor)));
        return deployRemoteCall(ChannelContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ChannelNewBalanceEventResponse extends BaseEventResponse {
        public String token_address;

        public String participant;

        public BigInteger balance;

        public BigInteger block_number;
    }

    public static class ChannelCloseRequestedEventResponse extends BaseEventResponse {
        public String closing_address;

        public BigInteger block_number;
    }

    public static class ChannelClosedEventResponse extends BaseEventResponse {
        public String closing_address;

        public BigInteger block_number;
    }

    public static class TransferUpdatedEventResponse extends BaseEventResponse {
        public String node_address;

        public BigInteger block_number;
    }

    public static class ChannelSettledEventResponse extends BaseEventResponse {
        public BigInteger block_number;
    }

    public static class ChannelAuditedEventResponse extends BaseEventResponse {
        public BigInteger block_number;
    }

    public static class ChannelSecretRevealedEventResponse extends BaseEventResponse {
        public byte[] secret;

        public String receiver_address;
    }
}
