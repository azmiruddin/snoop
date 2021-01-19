package tub.ods.pch.channel;


import java.math.BigDecimal;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;



public class SignedTransaction {
    private Address channelAddress;
    private BigDecimal value;
    private boolean locked;

    public SignedTransaction() {
    }

//    public SignedTransfer(TransferMessage transferMessage) {
//        this(
//                transferMessage.getTransferId(),
//                transferMessage.getChannelAddress(),
//                transferMessage.getValue(),
//                transferMessage.getLock() == MessageLock.AUDITOR
//        );
//        signature = Numeric.hexStringToByteArray(transferMessage.getSignature());
//    }

    public SignedTransaction(String transferId, String channelAddress, String value, boolean locked) {
        this(
                new Uint256(Numeric.toBigInt(transferId)),
                new Address(channelAddress),
                new BigDecimal(value),
                locked
        );
    }

    public SignedTransaction(Uint256 transferId, Address channelAddress, BigDecimal value, boolean locked) {
//        this.transferId = transferId;
        this.channelAddress = channelAddress;
        this.value = value;
        this.locked = locked;
    }

//    public Uint256 getTransferId() {
//        return transferId;
//    }
//
//    public void setTransferId(Uint256 transferId) {
//        this.transferId = transferId;
//    }

    public Address getChannelAddress() {
        return channelAddress;
    }

    public void setChannelAddress(Address channelAddress) {
        this.channelAddress = channelAddress;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

//    @Transient
//    public BigInteger getValueWei() {
//        return TokenConvert.toWei(value);
//    }
//
//    public void setValueWei(BigInteger value) {
//        this.value = TokenConvert.fromWei(value);
//    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

//    public TransferMessage toMessage() {
//        return TransferMessage.newBuilder()
//                .setChannelAddress(Numeric.toHexStringNoPrefix(channelAddress.getValue()))
//                .setSignature(Numeric.toHexStringNoPrefix(signature))
//                .setTransferId(Numeric.toHexStringNoPrefix(transferId.getValue()))
//                .setValue(value.toString())
//                .setLock(locked ? MessageLock.AUDITOR : MessageLock.NONE)
//                .build();
//    }

//    @Override
//    public byte[] hash() {
//        return CryptoUtil.soliditySha3(transferId, channelAddress, value, locked ? BigInteger.ONE : BigInteger.ZERO);
//    }
}
