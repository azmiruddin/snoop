package tub.ods.pch.channel.model;

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
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class ChannelManagerContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b50604051604080611336833981016040528051602090910151600160a060020a038216151561003e57600080fd5b600160a060020a038116151561005357600080fd5b60008054600160a060020a03938416600160a060020a031991821617909155600180549290931691161790556112a88061008e6000396000f3006080604052600436106100535763ffffffff60e060020a6000350416631aea558a8114610058578063d94123b814610081578063efa1c34c146100b2578063f201da0d146100d3578063fc0c546a1461010c575b600080fd5b34801561006457600080fd5b5061007f600160a060020a0360043516602435604435610121565b005b34801561008d57600080fd5b506100966103b8565b60408051600160a060020a039092168252519081900360200190f35b3480156100be57600080fd5b5061007f600160a060020a03600435166103c7565b3480156100df57600080fd5b50610096600160a060020a0360043581169060243581169060443590606435906084359060a435166105e5565b34801561011857600080fd5b506100966106c9565b60008084915030600160a060020a031682600160a060020a031663481c6a756040518163ffffffff1660e060020a028152600401602060405180830381600087803b15801561016f57600080fd5b505af1158015610183573d6000803e3d6000fd5b505050506040513d602081101561019957600080fd5b5051600160a060020a0316146101ae57600080fd5b50604080517feec5f1dc00000000000000000000000000000000000000000000000000000000815233600482018190529151600160a060020a0384169163eec5f1dc91602480830192600092919082900301818387803b15801561021157600080fd5b505af1158015610225573d6000803e3d6000fd5b50505050600160009054906101000a9004600160a060020a0316600160a060020a031663879abc1183600160a060020a03166367e404ce6040518163ffffffff1660e060020a028152600401602060405180830381600087803b15801561028b57600080fd5b505af115801561029f573d6000803e3d6000fd5b505050506040513d60208110156102b557600080fd5b5051604080517ff7260d3e0000000000000000000000000000000000000000000000000000000081529051600160a060020a0387169163f7260d3e9160048083019260209291908290030181600087803b15801561031257600080fd5b505af1158015610326573d6000803e3d6000fd5b505050506040513d602081101561033c57600080fd5b50516040805160e060020a63ffffffff8616028152600160a060020a039384166004820152929091166024830152604482018890526064820187905251608480830192600092919082900301818387803b15801561039957600080fd5b505af11580156103ad573d6000803e3d6000fd5b505050505050505050565b600154600160a060020a031681565b600081905030600160a060020a031681600160a060020a031663481c6a756040518163ffffffff1660e060020a028152600401602060405180830381600087803b15801561041457600080fd5b505af1158015610428573d6000803e3d6000fd5b505050506040513d602081101561043e57600080fd5b5051600160a060020a03161461045357600080fd5b80600160a060020a031663f7260d3e6040518163ffffffff1660e060020a028152600401602060405180830381600087803b15801561049157600080fd5b505af11580156104a5573d6000803e3d6000fd5b505050506040513d60208110156104bb57600080fd5b5051604080517f67e404ce0000000000000000000000000000000000000000000000000000000081529051600160a060020a03928316928416916367e404ce9160048083019260209291908290030181600087803b15801561051c57600080fd5b505af1158015610530573d6000803e3d6000fd5b505050506040513d602081101561054657600080fd5b505160408051600160a060020a038681168252915191909216917f91cd7e9ad7c88602bc7b06adc62de54cb670665c75894414c01ead5f2baf0930919081900360200190a380600160a060020a03166383197ef06040518163ffffffff1660e060020a028152600401600060405180830381600087803b1580156105c957600080fd5b505af11580156105dd573d6000803e3d6000fd5b505050505050565b60008030338989898989896105f86106d8565b600160a060020a03988916815296881660208801529487166040808801919091529387166060870152608086019290925260a085015260c0840152921660e082015290519081900361010001906000f08015801561065a573d6000803e3d6000fd5b5060408051600160a060020a0380841682528b811660208301528183018a9052606082018990526080820188905291519293509089169133917f43fd377a345e73027615c3f395c7b5dcb113038abd2281f8f3ae5e936c857742919081900360a00190a3979650505050505050565b600054600160a060020a031681565b604051610b94806106e9833901905600608060405234801561001057600080fd5b5060405161010080610b9483398101604090815281516020830151918301516060840151608085015160a086015160c087015160e090970151949693949293919290919033600160a060020a0389161461006957600080fd5b600160a060020a03878116908616141561008257600080fd5b600160a060020a03868116908616141561009b57600080fd5b60008210156100a957600080fd5b600083116100b657600080fd5b60008410156100c457600080fd5b60098054600160a060020a0319908116600160a060020a03998a1617909155600b8054821697891697909717909655600a8054871695881695909517909455600d8054861694871694909417909355600880549094169590941694909417909155600092835560019190915560025543600355610a4d90819061014790396000f3006080604052600436106101325763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166304fb9e8181146101445780630baff0531461016b578063109e94cf1461018057806311da60b4146101b15780633ec045a6146101c8578063481c6a75146101dd578063597e1fb5146101f25780635f88eade1461020757806367e404ce1461021c5780636d2381b3146102315780637ebdc4781461027057806383197ef01461028557806388897c371461029a5780638f775839146102af578063affed0e0146102c4578063b4def1e7146102d9578063b69ef8a8146102ee578063b6b55f2514610303578063d50726071461032f578063dd00a9dc14610344578063e7084b7e146103a4578063eec5f1dc146103b9578063f7260d3e146103da575b34801561013e57600080fd5b50600080fd5b34801561015057600080fd5b506101596103ef565b60408051918252519081900360200190f35b34801561017757600080fd5b506101596103f5565b34801561018c57600080fd5b506101956103fb565b60408051600160a060020a039092168252519081900360200190f35b3480156101bd57600080fd5b506101c661040a565b005b3480156101d457600080fd5b506101956104c3565b3480156101e957600080fd5b506101956104d2565b3480156101fe57600080fd5b506101596104e1565b34801561021357600080fd5b506101596104e7565b34801561022857600080fd5b506101956104ed565b34801561023d57600080fd5b506102466104fc565b60408051600160a060020a0394851681529290931660208301528183015290519081900360600190f35b34801561027c57600080fd5b50610159610518565b34801561029157600080fd5b506101c661051e565b3480156102a657600080fd5b506101c6610569565b3480156102bb57600080fd5b50610159610629565b3480156102d057600080fd5b5061015961062f565b3480156102e557600080fd5b50610159610635565b3480156102fa57600080fd5b5061015961063b565b34801561030f57600080fd5b5061031b600435610641565b604080519115158252519081900360200190f35b34801561033b57600080fd5b506101596107de565b34801561035057600080fd5b50604080516020600460443581810135601f81018490048402850184019095528484526101c69482359460248035953695946064949201919081908401838280828437509497506107e49650505050505050565b3480156103b057600080fd5b5061015961092c565b3480156103c557600080fd5b506101c6600160a060020a0360043516610932565b3480156103e657600080fd5b50610195610a12565b60045490565b60075490565b600b54600160a060020a031690565b604080517f57389014000000000000000000000000000000000000000000000000000000008152600060048201819052915173__ChannelLibrary.sol:ChannelLibrary_____9263573890149260248082019391829003018186803b15801561047357600080fd5b505af4158015610487573d6000803e3d6000fd5b505060065460408051918252517ffe501c6f860c82db0609c0e0e7571f4a08c991e3b653cb35a7f105c84caccb999350908190036020019150a1565b600d54600160a060020a031690565b600854600160a060020a031690565b60055490565b60035490565b600954600160a060020a031690565b600954600a54600c54600160a060020a03928316939290911691565b60015490565b600854600160a060020a0316331461053557600080fd5b60065460001061054457600080fd5b6007546000108061055a57506002546005540143115b151561056557600080fd5b6000ff5b604080517f61fc47d4000000000000000000000000000000000000000000000000000000008152600060048201819052915173__ChannelLibrary.sol:ChannelLibrary_____926361fc47d49260248082019391829003018186803b1580156105d257600080fd5b505af41580156105e6573d6000803e3d6000fd5b505060055460408051338152602081019290925280517f824a51662384e98063aa4168d3389dbd9d8e14e436be432fcc5490758c01d4af945091829003019150a1565b60065490565b600e5490565b600f5490565b600c5490565b60008060008073__ChannelLibrary.sol:ChannelLibrary_____6336da8a209091866040518363ffffffff167c01000000000000000000000000000000000000000000000000000000000281526004018083815260200182815260200192505050604080518083038186803b1580156106ba57600080fd5b505af41580156106ce573d6000803e3d6000fd5b505050506040513d60408110156106e457600080fd5b5080516020909101519092509050600182151514156107d757600854604080517ffc0c546a00000000000000000000000000000000000000000000000000000000815290517ffdbb5aee5d86223b95ec2a3ef27bf36cdb0a4ae42a4823bdd7c091f143dbf83d92600160a060020a03169163fc0c546a9160048083019260209291908290030181600087803b15801561077c57600080fd5b505af1158015610790573d6000803e3d6000fd5b505050506040513d60208110156107a657600080fd5b505160408051600160a060020a03909216825233602083015281810184905260006060830152519081900360800190a15b5092915050565b60025490565b6040517fc41168e60000000000000000000000000000000000000000000000000000000081526000600482018181523060248401819052604484018790526064840186905260a060848501908152855160a4860152855173__ChannelLibrary.sol:ChannelLibrary_____9563c41168e695948a938a938a9360c4019060208501908083838c5b8381101561088457818101518382015260200161086c565b50505050905090810190601f1680156108b15780820380516001836020036101000a031916815260200191505b50965050505050505060006040518083038186803b1580156108d257600080fd5b505af41580156108e6573d6000803e3d6000fd5b505060055460408051338152602081019290925280517f770342ee36990141b5b1f4b3b41a184d7968647ac7f0dbfd2d86d566c468027d945091829003019150a1505050565b60005490565b600854600160a060020a0316331461094957600080fd5b604080517f0f454991000000000000000000000000000000000000000000000000000000008152600060048201819052600160a060020a0384166024830152915173__ChannelLibrary.sol:ChannelLibrary_____92630f4549919260448082019391829003018186803b1580156109c157600080fd5b505af41580156109d5573d6000803e3d6000fd5b505060075460408051918252517f57885380de2bb12ea3e34bdf33c55c186dedf37aefebd60551783af2fc1df1689350908190036020019150a150565b600a54600160a060020a0316905600a165627a7a72305820903c7ddd9bc8b8ab7e61aa82b5f25e9c06f8f41ea3285f258d13905082dbc1010029a165627a7a7230582014ddfc785730b5019be6d8fde838aa292701aaa0d9500ee8b659dd9120a7c4690029";

    public static final String FUNC_AUDITREPORT = "auditReport";

    public static final String FUNC_CHANNEL_API = "channel_api";

    public static final String FUNC_DESTROYCHANNEL = "destroyChannel";

    public static final String FUNC_NEWCHANNEL = "newChannel";

    public static final String FUNC_TOKEN = "token";

    public static final Event CHANNELNEW_EVENT = new Event("ChannelNew", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event CHANNELDELETED_EVENT = new Event("ChannelDeleted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected ChannelManagerContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChannelManagerContract(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChannelManagerContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChannelManagerContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> auditReport(String contract_address, BigInteger total, BigInteger fraud) {
        final Function function = new Function(
                FUNC_AUDITREPORT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, contract_address), 
                new org.web3j.abi.datatypes.generated.Uint256(total), 
                new org.web3j.abi.datatypes.generated.Uint256(fraud)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> channel_api() {
        final Function function = new Function(FUNC_CHANNEL_API, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> destroyChannel(String channel_address) {
        final Function function = new Function(
                FUNC_DESTROYCHANNEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, channel_address)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> newChannel(String client, String receiver, BigInteger close_timeout, BigInteger settle_timeout, BigInteger audit_timeout, String auditor) {
        final Function function = new Function(
                FUNC_NEWCHANNEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, client), 
                new org.web3j.abi.datatypes.Address(160, receiver), 
                new org.web3j.abi.datatypes.generated.Uint256(close_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(settle_timeout), 
                new org.web3j.abi.datatypes.generated.Uint256(audit_timeout), 
                new org.web3j.abi.datatypes.Address(160, auditor)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> token() {
        final Function function = new Function(FUNC_TOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<ChannelNewEventResponse> getChannelNewEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELNEW_EVENT, transactionReceipt);
        ArrayList<ChannelNewEventResponse> responses = new ArrayList<ChannelNewEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelNewEventResponse typedResponse = new ChannelNewEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.channel_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.client = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.close_timeout = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.settle_timeout = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            typedResponse.audit_timeout = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelNewEventResponse> channelNewEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelNewEventResponse>() {
            @Override
            public ChannelNewEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELNEW_EVENT, log);
                ChannelNewEventResponse typedResponse = new ChannelNewEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.channel_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.client = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.close_timeout = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.settle_timeout = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                typedResponse.audit_timeout = (BigInteger) eventValues.getNonIndexedValues().get(4).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelNewEventResponse> channelNewEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELNEW_EVENT));
        return channelNewEventFlowable(filter);
    }

    public List<ChannelDeletedEventResponse> getChannelDeletedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELDELETED_EVENT, transactionReceipt);
        ArrayList<ChannelDeletedEventResponse> responses = new ArrayList<ChannelDeletedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelDeletedEventResponse typedResponse = new ChannelDeletedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.channel_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelDeletedEventResponse> channelDeletedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelDeletedEventResponse>() {
            @Override
            public ChannelDeletedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELDELETED_EVENT, log);
                ChannelDeletedEventResponse typedResponse = new ChannelDeletedEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.receiver = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.channel_address = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelDeletedEventResponse> channelDeletedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELDELETED_EVENT));
        return channelDeletedEventFlowable(filter);
    }

    @Deprecated
    public static ChannelManagerContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChannelManagerContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ChannelManagerContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChannelManagerContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ChannelManagerContract load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ChannelManagerContract(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ChannelManagerContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ChannelManagerContract(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ChannelManagerContract> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String token_address, String channel_api_address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token_address), 
                new org.web3j.abi.datatypes.Address(160, channel_api_address)));
        return deployRemoteCall(ChannelManagerContract.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<ChannelManagerContract> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String token_address, String channel_api_address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token_address), 
                new org.web3j.abi.datatypes.Address(160, channel_api_address)));
        return deployRemoteCall(ChannelManagerContract.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChannelManagerContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String token_address, String channel_api_address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token_address), 
                new org.web3j.abi.datatypes.Address(160, channel_api_address)));
        return deployRemoteCall(ChannelManagerContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<ChannelManagerContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String token_address, String channel_api_address) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, token_address), 
                new org.web3j.abi.datatypes.Address(160, channel_api_address)));
        return deployRemoteCall(ChannelManagerContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class ChannelNewEventResponse extends BaseEventResponse {
        public String sender;

        public String receiver;

        public String channel_address;

        public String client;

        public BigInteger close_timeout;

        public BigInteger settle_timeout;

        public BigInteger audit_timeout;
    }

    public static class ChannelDeletedEventResponse extends BaseEventResponse {
        public String sender;

        public String receiver;

        public String channel_address;
    }
}
