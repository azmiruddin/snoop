package tub.ods.pch.channel.util;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;


public class Hasher{


    public static String getHashFromString(String data) throws NoSuchAlgorithmException {

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hash = messageDigest.digest(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public static byte[] createSignature(PrivateKey privateKey, JSONObject toSign) {
        Signature rsa;
        byte[] signature;
        try {
            rsa = Signature.getInstance("SHA256withRSA");
            rsa.initSign(privateKey);
            byte[] inputJSONAsBytes = toSign.toString().getBytes();
            rsa.update(inputJSONAsBytes);
            signature = rsa.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return signature;
    }

    /*public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] cipherText = encryptCipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }*/

   /* public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(cipherText);

        Cipher decriptCipher = Cipher.getInstance("RSA");
        decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

        return new String(decriptCipher.doFinal(bytes));
    }*/

    public static boolean verifySignature(PublicKey publicKey, JSONObject data, byte[] signature) {
        try {
            Signature rsa = Signature.getInstance("SHA256withRSA");
            rsa.initVerify(publicKey);
            rsa.update(data.toString().getBytes());
            return rsa.verify(signature);
        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getStringFromKey(Key key) {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            return keyGen.generateKeyPair();

        }catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
