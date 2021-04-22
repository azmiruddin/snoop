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
 * <p>Generated with web3j version 4.5.16.
 */
@SuppressWarnings("rawtypes")
public class ChannelApiStub extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50610158806100206000396000f30060806040526004361061004b5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663879abc1181146100505780639d3fae041461008c575b600080fd5b34801561005c57600080fd5b5061008a73ffffffffffffffffffffffffffffffffffffffff600435811690602435166044356064356100c3565b005b34801561009857600080fd5b5061008a73ffffffffffffffffffffffffffffffffffffffff60043581169060243516604435610127565b6040805173ffffffffffffffffffffffffffffffffffffffff8087168252851660208201528082018490526060810183905290517fe9304a57050597713c56a840bee91ee69885e97d86cf5d841232def20c4e3f569181900360800190a150505050565b5050505600a165627a7a72305820ea3f97e3997254a0cad25a067409063883a36aafe0c198a662a3d5c3755d33270029";

    public static final String FUNC_APPLYRUNTIMEUPDATE = "applyRuntimeUpdate";

    public static final String FUNC_APPLYAUDITORSCHECKUPDATE = "applyAuditorsCheckUpdate";

    public static final Event CHANNELAUDIT_EVENT = new Event("ChannelAudit", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected ChannelApiStub(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChannelApiStub(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChannelApiStub(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChannelApiStub(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> applyRuntimeUpdate(String from, String to, BigInteger impressionsCount, BigInteger fraudCount) {
        final Function function = new Function(
                FUNC_APPLYRUNTIMEUPDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(impressionsCount), 
                new org.web3j.abi.datatypes.generated.Uint256(fraudCount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> applyAuditorsCheckUpdate(String from, String to, BigInteger fraudCountDelta) {
        final Function function = new Function(
                FUNC_APPLYAUDITORSCHECKUPDATE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, from), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(fraudCountDelta)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<ChannelAuditEventResponse> getChannelAuditEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHANNELAUDIT_EVENT, transactionReceipt);
        ArrayList<ChannelAuditEventResponse> responses = new ArrayList<ChannelAuditEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChannelAuditEventResponse typedResponse = new ChannelAuditEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
            typedResponse.impressionsCount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
            typedResponse.fraudCount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ChannelAuditEventResponse> channelAuditEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, ChannelAuditEventResponse>() {
            @Override
            public ChannelAuditEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHANNELAUDIT_EVENT, log);
                ChannelAuditEventResponse typedResponse = new ChannelAuditEventResponse();
                typedResponse.log = log;
                typedResponse.from = (String) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.to = (String) eventValues.getNonIndexedValues().get(1).getValue();
                typedResponse.impressionsCount = (BigInteger) eventValues.getNonIndexedValues().get(2).getValue();
                typedResponse.fraudCount = (BigInteger) eventValues.getNonIndexedValues().get(3).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ChannelAuditEventResponse> channelAuditEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHANNELAUDIT_EVENT));
        return channelAuditEventFlowable(filter);
    }

    @Deprecated
    public static ChannelApiStub load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChannelApiStub(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ChannelApiStub load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChannelApiStub(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ChannelApiStub load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ChannelApiStub(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ChannelApiStub load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ChannelApiStub(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ChannelApiStub> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ChannelApiStub.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ChannelApiStub> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ChannelApiStub.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ChannelApiStub> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ChannelApiStub.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ChannelApiStub> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ChannelApiStub.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static class ChannelAuditEventResponse extends BaseEventResponse {
        public String from;

        public String to;

        public BigInteger impressionsCount;

        public BigInteger fraudCount;
    }
}
