package tub.ods.pch.channel.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import tub.ods.pch.channel.util.Hasher;

public class TransactionOutput {

    public String id;
    public PublicKey receiver; //also known as the new owner of these coins.
    public float value; //the amount of coins they own
    public String parentTransactionId; //the id of the transaction this output was created in

    //Constructor
    public TransactionOutput(PublicKey receiver, float value, String parentTransactionId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        this.receiver = receiver;
        this.value = value;
        this.parentTransactionId = parentTransactionId;
        this.id = Hasher.getHashFromString(Hasher.getStringFromKey(receiver)+Float.toString(value)+parentTransactionId);
    }

    //Check if coin belongs to you
    public boolean isMine(PublicKey publicKey) {
        return (publicKey == receiver);
    }
}
