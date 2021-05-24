package tub.ods.mediator.contract;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
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
public class ChannelLibrary extends Contract {
    public static final String BINARY = "{\r\n"
            + "\t\"linkReferences\": {},\r\n"
            + "\t\"object\": \"610205610030600b82828239805160001a6073146000811461002057610022565bfe5b5030600052607381538281f300730000000000000000000000000000000000000000301460806040526004361061006e576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff1680631d3b9edf1461007357806366098d4f146100be578063f4f3bdc114610109575b600080fd5b81801561007f57600080fd5b506100a86004803603810190808035906020019092919080359060200190929190505050610154565b6040518082815260200191505060405180910390f35b8180156100ca57600080fd5b506100f36004803603810190808035906020019092919080359060200190929190505050610187565b6040518082815260200191505060405180910390f35b81801561011557600080fd5b5061013e60048036038101908080359060200190929190803590602001909291905050506101b1565b6040518082815260200191505060405180910390f35b600080828402905061017d6000851480610178575083858381151561017557fe5b04145b6101ca565b8091505092915050565b60008082840190506101a78482101580156101a25750838210155b6101ca565b8091505092915050565b60006101bf838311156101ca565b818303905092915050565b8015156101d657600080fd5b505600a165627a7a72305820cdc5b1b412cdef4238a861a3678390da2c8094615c1204b03b29a86317a64b4f0029\",\r\n"
            + "\t\"opcodes\": \"PUSH2 0x205 PUSH2 0x30 PUSH1 0xB DUP3 DUP3 DUP3 CODECOPY DUP1 MLOAD PUSH1 0x0 BYTE PUSH1 0x73 EQ PUSH1 0x0 DUP2 EQ PUSH2 0x20 JUMPI PUSH2 0x22 JUMP JUMPDEST INVALID JUMPDEST POP ADDRESS PUSH1 0x0 MSTORE PUSH1 0x73 DUP2 MSTORE8 DUP3 DUP2 RETURN STOP PUSH20 0x0 ADDRESS EQ PUSH1 0x80 PUSH1 0x40 MSTORE PUSH1 0x4 CALLDATASIZE LT PUSH2 0x6E JUMPI PUSH1 0x0 CALLDATALOAD PUSH29 0x100000000000000000000000000000000000000000000000000000000 SWAP1 DIV PUSH4 0xFFFFFFFF AND DUP1 PUSH4 0x1D3B9EDF EQ PUSH2 0x73 JUMPI DUP1 PUSH4 0x66098D4F EQ PUSH2 0xBE JUMPI DUP1 PUSH4 0xF4F3BDC1 EQ PUSH2 0x109 JUMPI JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST DUP2 DUP1 ISZERO PUSH2 0x7F JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH2 0xA8 PUSH1 0x4 DUP1 CALLDATASIZE SUB DUP2 ADD SWAP1 DUP1 DUP1 CALLDATALOAD SWAP1 PUSH1 0x20 ADD SWAP1 SWAP3 SWAP2 SWAP1 DUP1 CALLDATALOAD SWAP1 PUSH1 0x20 ADD SWAP1 SWAP3 SWAP2 SWAP1 POP POP POP PUSH2 0x154 JUMP JUMPDEST PUSH1 0x40 MLOAD DUP1 DUP3 DUP2 MSTORE PUSH1 0x20 ADD SWAP2 POP POP PUSH1 0x40 MLOAD DUP1 SWAP2 SUB SWAP1 RETURN JUMPDEST DUP2 DUP1 ISZERO PUSH2 0xCA JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH2 0xF3 PUSH1 0x4 DUP1 CALLDATASIZE SUB DUP2 ADD SWAP1 DUP1 DUP1 CALLDATALOAD SWAP1 PUSH1 0x20 ADD SWAP1 SWAP3 SWAP2 SWAP1 DUP1 CALLDATALOAD SWAP1 PUSH1 0x20 ADD SWAP1 SWAP3 SWAP2 SWAP1 POP POP POP PUSH2 0x187 JUMP JUMPDEST PUSH1 0x40 MLOAD DUP1 DUP3 DUP2 MSTORE PUSH1 0x20 ADD SWAP2 POP POP PUSH1 0x40 MLOAD DUP1 SWAP2 SUB SWAP1 RETURN JUMPDEST DUP2 DUP1 ISZERO PUSH2 0x115 JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH2 0x13E PUSH1 0x4 DUP1 CALLDATASIZE SUB DUP2 ADD SWAP1 DUP1 DUP1 CALLDATALOAD SWAP1 PUSH1 0x20 ADD SWAP1 SWAP3 SWAP2 SWAP1 DUP1 CALLDATALOAD SWAP1 PUSH1 0x20 ADD SWAP1 SWAP3 SWAP2 SWAP1 POP POP POP PUSH2 0x1B1 JUMP JUMPDEST PUSH1 0x40 MLOAD DUP1 DUP3 DUP2 MSTORE PUSH1 0x20 ADD SWAP2 POP POP PUSH1 0x40 MLOAD DUP1 SWAP2 SUB SWAP1 RETURN JUMPDEST PUSH1 0x0 DUP1 DUP3 DUP5 MUL SWAP1 POP PUSH2 0x17D PUSH1 0x0 DUP6 EQ DUP1 PUSH2 0x178 JUMPI POP DUP4 DUP6 DUP4 DUP2 ISZERO ISZERO PUSH2 0x175 JUMPI INVALID JUMPDEST DIV EQ JUMPDEST PUSH2 0x1CA JUMP JUMPDEST DUP1 SWAP2 POP POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 DUP1 DUP3 DUP5 ADD SWAP1 POP PUSH2 0x1A7 DUP5 DUP3 LT ISZERO DUP1 ISZERO PUSH2 0x1A2 JUMPI POP DUP4 DUP3 LT ISZERO JUMPDEST PUSH2 0x1CA JUMP JUMPDEST DUP1 SWAP2 POP POP SWAP3 SWAP2 POP POP JUMP JUMPDEST PUSH1 0x0 PUSH2 0x1BF DUP4 DUP4 GT ISZERO PUSH2 0x1CA JUMP JUMPDEST DUP2 DUP4 SUB SWAP1 POP SWAP3 SWAP2 POP POP JUMP JUMPDEST DUP1 ISZERO ISZERO PUSH2 0x1D6 JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP JUMP STOP LOG1 PUSH6 0x627A7A723058 KECCAK256 0xcd 0xc5 0xb1 0xb4 SLT 0xcd 0xef TIMESTAMP CODESIZE 0xa8 PUSH2 0xA367 DUP4 SWAP1 0xda 0x2c DUP1 SWAP5 PUSH2 0x5C12 DIV 0xb0 EXTCODESIZE 0x29 0xa8 PUSH4 0x17A64B4F STOP 0x29 \",\r\n"
            + "\t\"sourceMap\": \"27:449:0:-;;132:2:-1;166:7;155:9;146:7;137:37;252:7;246:14;243:1;238:23;232:4;229:33;270:1;265:20;;;;222:63;;265:20;274:9;222:63;;298:9;295:1;288:20;328:4;319:7;311:22;352:7;343;336:24\"\r\n"
            + "}";

    public static final String FUNC_TIMES = "times";

    public static final String FUNC_PLUS = "plus";

    public static final String FUNC_MINUS = "minus";

    @Deprecated
    protected ChannelLibrary(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ChannelLibrary(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected ChannelLibrary(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected ChannelLibrary(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteFunctionCall<TransactionReceipt> times(BigInteger a, BigInteger b) {
        final Function function = new Function(
                FUNC_TIMES, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a), 
                new org.web3j.abi.datatypes.generated.Uint256(b)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> plus(BigInteger a, BigInteger b) {
        final Function function = new Function(
                FUNC_PLUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a), 
                new org.web3j.abi.datatypes.generated.Uint256(b)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> minus(BigInteger a, BigInteger b) {
        final Function function = new Function(
                FUNC_MINUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(a), 
                new org.web3j.abi.datatypes.generated.Uint256(b)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static ChannelLibrary load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChannelLibrary(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static ChannelLibrary load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ChannelLibrary(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static ChannelLibrary load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new ChannelLibrary(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static ChannelLibrary load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new ChannelLibrary(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<ChannelLibrary> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ChannelLibrary.class, web3j, credentials, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ChannelLibrary> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ChannelLibrary.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ChannelLibrary> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return deployRemoteCall(ChannelLibrary.class, web3j, transactionManager, contractGasProvider, BINARY, "");
    }

    @Deprecated
    public static RemoteCall<ChannelLibrary> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ChannelLibrary.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }
}
