package tub.ods.pch.channel.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;

import tub.ods.pch.channel.util.Hasher;

public class TransactionProcessor {

    public ArrayList<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
    private static int sequence = 0;

    public Transaction createTransaction(PublicKey sender, PublicKey receiver, float value)
    {
        return new Transaction(sender, receiver, value);
    }

    public boolean processTransaction(Transaction transaction) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        for(TransactionInput i : inputs) {
            i.UTXO = GenesisBlock.allUnspentTransactions.get(i.transactionOutputId);
        }

        float leftOver = getInputsValue() - transaction.value;
        transaction.transactionId = calulateHash(transaction);
        outputs.add(new TransactionOutput( transaction.receiver, transaction.value,transaction.transactionId));
        outputs.add(new TransactionOutput( transaction.sender, leftOver,transaction.transactionId));

        for(TransactionOutput o : outputs) {
            GenesisBlock.allUnspentTransactions.put(o.id , o);
        }

        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;
            GenesisBlock.allUnspentTransactions.remove(i.UTXO.id);
        }
        return true;
    }

    public float getInputsValue() {
        float total = 0;
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue;
            total += i.UTXO.value;
        }
        return total;
    }

    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;
    }

	private String calulateHash(Transaction transaction) throws NoSuchAlgorithmException {
		sequence++;
		return Hasher.getHashFromString(Hasher.getStringFromKey(transaction.sender)
				+ Hasher.getStringFromKey(transaction.receiver) + Float.toString(transaction.value) + sequence);
	}
}
