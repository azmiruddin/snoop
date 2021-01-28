package tub.ods.pch.channel.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tub.ods.pch.channel.controller.Transaction;
import tub.ods.pch.channel.controller.TransactionBlock;

public class Converter {

    public static byte[] getBytesFromJSONObject(JSONObject json)
    {
        return json.toString().getBytes();
    }

    public static JSONObject getJSONObjectFromBytes(byte[] data) throws JSONException
    {
        String jsonString = new String(data);
        JSONObject json = new JSONObject(jsonString);
        return json;
    }

    public static JSONObject getJSONFromTransaction(Transaction transaction) throws JSONException
    {
        JSONObject object = new JSONObject();
        object.put("id", transaction.transactionId);
        object.put("value", transaction.value);
        object.put("sender",getJSONFromPublicKey(transaction.sender));
        object.put("receiver", getJSONFromPublicKey(transaction.receiver));
        return object;
    }
    public static JSONObject getJSONFromBlock(TransactionBlock trxBlock) throws JSONException
    {
        JSONObject object = new JSONObject();
        object.put("index",trxBlock.getIndex());
        object.put("prevBlockHash", trxBlock.getPreviousBlockHash());
        object.put("data", trxBlock.getData());
        object.put("hash", trxBlock.getHash());
        object.put("timestamp", trxBlock.getTimestamp());
        object.put("transaction", getJSONFromTransaction(trxBlock.getTransaction()));
        return object;
    }

    public static TransactionBlock getBlockFromJSON(JSONObject object) throws JSONException
    {

        TransactionBlock transactionBlock = new TransactionBlock(object.getInt("index"),object.getString("hash"), object.getString("prevBlockHash"),
                object.getString("data"), object.getString("timestamp"));
        //block.transaction = getTransactionFromJSON(new JSONObject(object.getString("transaction")));
        return transactionBlock;
    }

    public static Transaction getTransactionFromJSON(JSONObject object) throws InvalidKeySpecException, NoSuchAlgorithmException, JSONException {

        JSONObject jsonSender = object.getJSONObject("sender");
        JSONObject jsonReceiver = object.getJSONObject("receiver");
        PublicKey sender = getPublicKeyFromJSON(jsonSender);
        PublicKey receiver = getPublicKeyFromJSON(jsonReceiver);
        Transaction transaction = new Transaction(sender, receiver, object.getInt("value"));
        transaction.transactionId = object.getString("id");

        return transaction;
    }

    public static JSONObject getJSONFromPublicKey(PublicKey publicKey) throws JSONException
    {
        byte[] key = publicKey.getEncoded();
        JSONArray array = new JSONArray(key);
        JSONObject object = new JSONObject();
        String tmp = Base64.getEncoder().encodeToString(key);
        object.put("key", tmp);
        return object;
    }

    public static PublicKey getPublicKeyFromJSON(JSONObject object) throws NoSuchAlgorithmException, InvalidKeySpecException, JSONException {

        //JSONArray array = object.getJSONArray("key");

        String tmp = object.getString("key");
        byte[] byteKey = Base64.getDecoder().decode(tmp);
        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");


        return kf.generatePublic(X509publicKey);
    }
}
