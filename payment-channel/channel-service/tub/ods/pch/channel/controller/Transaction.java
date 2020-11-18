package tub.ods.pch.channel.controller;

import java.security.PublicKey;

public class Transaction {
    public String transactionId;
    public PublicKey sender;
    public PublicKey receiver;
    public float value;


    public Transaction(PublicKey from, PublicKey to, float value) {
        this.sender = from;
        this.receiver = to;
        this.value = value;

    }
}
