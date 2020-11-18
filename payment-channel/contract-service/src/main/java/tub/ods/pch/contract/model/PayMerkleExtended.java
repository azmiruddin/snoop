package tub.ods.pch.contract.model;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
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
public class PayMerkleExtended extends Contract {
    public static final String BINARY = "60806040526040516105b93803806105b98339818101604052606081101561002657600080fd5b50805160208201516040909201519091903461004157600080fd5b600180546001600160a01b039094166001600160a01b031994851617905560008054909316331790925542600255600355600455610535806100846000396000f3fe6080604052600436106100865760003560e01c80632ef2d55e116100595780632ef2d55e146101b1578063393fe1cd146101c657806339658245146101db5780635b354c4a146101f0578063c4024d2b1461020d57610086565b806304758e791461008b578063075aa0c4146100bc5780630b97bc86146100d15780631596da74146100f8575b600080fd5b34801561009757600080fd5b506100a06102dd565b604080516001600160a01b039092168252519081900360200190f35b3480156100c857600080fd5b506100a06102ec565b3480156100dd57600080fd5b506100e66102fb565b60408051918252519081900360200190f35b34801561010457600080fd5b506101af6004803603604081101561011b57600080fd5b8135919081019060408101602082013564010000000081111561013d57600080fd5b82018360208201111561014f57600080fd5b8035906020019184602083028401116401000000008311171561017157600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550610301945050505050565b005b3480156101bd57600080fd5b506100e6610398565b3480156101d257600080fd5b506100e661039e565b3480156101e757600080fd5b506101af6103a4565b6101af6004803603602081101561020657600080fd5b50356103c5565b34801561021957600080fd5b506102c96004803603606081101561023057600080fd5b81359160208101359181019060608101604082013564010000000081111561025757600080fd5b82018360208201111561026957600080fd5b8035906020019184602083028401116401000000008311171561028b57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600092019190915250929550610436945050505050565b604080519115158252519081900360200190f35b6001546001600160a01b031681565b6000546001600160a01b031681565b60025481565b6001546001600160a01b0316331461031857600080fd5b6040805160208082018590528251808303820181529183019092528051910120600454610346908284610436565b61034f57600080fd5b6001546040516001600160a01b039091169084156108fc029085906000818181858888f19350505050158015610389573d6000803e3d6000fd5b506000546001600160a01b0316ff5b60035481565b60045481565b600354600254014210156103b757600080fd5b6000546001600160a01b0316ff5b806004541015610403576004805460408051602080820193909352808201859052815180820383018152606090910190915280519101209055610433565b60048054604080516020808201869052818301939093528151808203830181526060909101909152805191012090555b50565b600082815b83518110156104f55783818151811061045057fe5b60200260200101518210156104a8578184828151811061046c57fe5b602002602001015160405160200180838152602001828152602001925050506040516020818303038152906040528051906020012091506104ed565b8381815181106104b457fe5b60200260200101518260405160200180838152602001828152602001925050506040516020818303038152906040528051906020012091505b60010161043b565b50909314939250505056fea265627a7a72315820cb9f0bb2bdac25f0df7c69205d6729246a217acd1e1389ddb61bceaad646e2c164736f6c63430005100032";

    public static final String FUNC_ADDBALANCE = "AddBalance";

    public static final String FUNC_ChannelTimeout = "ChannelTimeout";

    public static final String FUNC_CLOSECHANNEL = "CloseChannel";

    public static final String FUNC_CHANNELRECIPIENT = "channelRecipient";

    public static final String FUNC_CHANNELSENDER = "channelSender";

    public static final String FUNC_channelTimeout = "channelTimeout";

    public static final String FUNC_ROOTS = "roots";

    public static final String FUNC_STARTDATE = "startDate";

    public static final String FUNC_VERIFYMERKLE = "verifyMerkle";

    @Deprecated
    protected PayMerkleExtended(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PayMerkleExtended(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected PayMerkleExtended(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected PayMerkleExtended(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> AddBalance(byte[] _newRoot, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_ADDBALANCE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Bytes32(_newRoot)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteFunctionCall<TransactionReceipt> ChannelTimeout() {
        final Function function = new Function(
                FUNC_ChannelTimeout, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> CloseChannel(BigInteger _amount, List<byte[]> proof) {
        final Function function = new Function(
                FUNC_CLOSECHANNEL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_amount), 
                new org.web3j.abi.datatypes.DynamicArray<org.web3j.abi.datatypes.generated.Bytes32>(
                        org.web3j.abi.datatypes.generated.Bytes32.class,
                        org.web3j.abi.Utils.typeMap(proof, org.web3j.abi.datatypes.generated.Bytes32.class))), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> channelRecipient(String string) {
        final Function function = new Function(FUNC_CHANNELRECIPIENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<String> channelSender() {
        final Function function = new Function(FUNC_CHANNELSENDER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
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

    public RemoteFunctionCall<BigInteger> startDate() {
        final Function function = new Function(FUNC_STARTDATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
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

    @Deprecated
    public static PayMerkleExtended load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PayMerkleExtended(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static PayMerkleExtended load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PayMerkleExtended(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static PayMerkleExtended load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new PayMerkleExtended(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static PayMerkleExtended load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new PayMerkleExtended(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<PayMerkleExtended> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String to, BigInteger _timeout, byte[] _root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.generated.Bytes32(_root)));
        return deployRemoteCall(PayMerkleExtended.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    public static RemoteCall<PayMerkleExtended> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, BigInteger initialWeiValue, String to, BigInteger _timeout, byte[] _root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.generated.Bytes32(_root)));
        return deployRemoteCall(PayMerkleExtended.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<PayMerkleExtended> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String to, BigInteger _timeout, byte[] _root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.generated.Bytes32(_root)));
        return deployRemoteCall(PayMerkleExtended.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

    @Deprecated
    public static RemoteCall<PayMerkleExtended> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue, String to, BigInteger _timeout, byte[] _root) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(_timeout), 
                new org.web3j.abi.datatypes.generated.Bytes32(_root)));
        return deployRemoteCall(PayMerkleExtended.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor, initialWeiValue);
    }

	public void setChannelRecipient(String address) {
	}
}
