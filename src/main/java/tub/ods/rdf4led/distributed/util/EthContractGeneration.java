package tub.ods.rdf4led.distributed.util;

import org.springframework.util.StringUtils;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;

import java.io.*;
import java.math.BigInteger;
import java.util.Random;

public class EthContractGeneration {

    public static File checkStorage() {
        String directory = "/home/pirate"+ File.separator + "ethnet";
        System.out.println("Directory: "+ directory);
        File storageDirectory = new File(directory);
        if (!storageDirectory.exists()){
            storageDirectory.mkdir();
        }
        return storageDirectory;
    }

    public static String generatePassword(File storageDirectory){
        String password = "";

        String passwordFilePath = storageDirectory + File.separator + "nodepassword.txt";
        System.out.println("Password File: "+ storageDirectory);
        //Check if password file Exists
        File passwordFile = new File(passwordFilePath);
        if(passwordFile.length() == 0){
            try {
                password = makePassword();
                passwordFile.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(passwordFile.getAbsolutePath()));
                out.write(password);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else{
            try {
                FileReader fileReader = new FileReader(passwordFilePath);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                password = bufferedReader.readLine();
                bufferedReader.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return  password;
    }

    public static String makePassword(){
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            builder.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
        }

        return builder.toString();
    }

    public static String keyStoreExists(String password ,File storageDirectory) {
        System.out.println(storageDirectory.getAbsoluteFile());
        File[] listFiles = new File("/home/pirate/ethnet").listFiles();
        String keyStoreFileName = "";
        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isFile()) {
                System.out.println("Files in Directory: " + listFiles[i].getName());
                String fileName = listFiles[i].getName();
                if (fileName.startsWith("UTC")) {

                    keyStoreFileName = fileName;
                    break;
                }
            }
        }

        if(StringUtils.isEmpty(keyStoreFileName)) {
            System.out.println("Keystore doesn't Exists");
            try {
                System.out.println("Before Keystore Generate");
                keyStoreFileName = WalletUtils.generateLightNewWalletFile(password, new File("/home/pirate/ethnet/"));
                System.out.println("After Keystore Generate");
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }

        System.out.println("Keystore " + keyStoreFileName);
        return  keyStoreFileName;
    }

    public static boolean fundAccount(Web3j web3j, String funderAddress, String myAddress){

        try {
            EthGetTransactionCount ethGetTransactionCount = web3j
                    .ethGetTransactionCount(funderAddress, DefaultBlockParameterName.LATEST).send();

            BigInteger nounce = ethGetTransactionCount.getTransactionCount();
            BigInteger gasprice = web3j.ethGasPrice().send().getGasPrice();
            BigInteger gaslimit = BigInteger.valueOf(4704588);
            BigInteger ether = new BigInteger("10000000000000000000000");
            System.out.println("Nounce: "+ethGetTransactionCount.getTransactionCount());

            Transaction transaction = new Transaction(funderAddress,nounce,gasprice,gaslimit,myAddress,ether,null);

            EthSendTransaction transfer = web3j.ethSendTransaction(transaction).send();
            if(!transfer.hasError()){
                return true;
            }else{
                System.out.println(transfer.getError().getMessage());
                return false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
