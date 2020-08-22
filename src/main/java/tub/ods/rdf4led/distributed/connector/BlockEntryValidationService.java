package tub.ods.rdf4led.distributed.connector;

import org.springframework.util.StringUtils;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;
import tub.ods.rdf4led.distributed.contract.StoreTripleData;
import tub.ods.rdf4led.distributed.storage.block.*;
import tub.ods.rdf4led.distributed.util.EthContractGeneration;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 29.03.19
 * TODO Description:
 */
public class BlockEntryValidationService implements ValidationService<long[]> {


    private Web3j web3j;
    public String myAddress;
    private StoreTripleData smartContract;
    private Credentials credentials;



    public BlockEntryValidationService(Web3j web3j, String smartContractAddress, String fundingAddress) {
        this.web3j = web3j;
        try {
            init(smartContractAddress, fundingAddress);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }



    private void init(String contractAddress, String fundingAdress) throws IOException, CipherException {

        File keyStore = EthContractGeneration.checkStorage();
        String password = EthContractGeneration.generatePassword(keyStore);
        System.out.println("users passsword: " + password);

        String wallet = EthContractGeneration.keyStoreExists(password, keyStore);

        System.out.println("Clients Private Key: " + keyStore + File.separator + wallet);
        System.out.println("Clients Password: " + password);

        this.credentials = WalletUtils.loadCredentials(password, keyStore + File.separator + wallet);

        if (this.credentials == null) {
            System.out.println("Could not load Wallet");
            System.exit(0);
        } else {
            System.out.println("Wallet Loaded");
        }

        this.myAddress = this.credentials.getAddress();
        System.out.println("Client's Address: " + this.myAddress);

        EthGetBalance oldBalance = this.web3j.ethGetBalance(this.myAddress, DefaultBlockParameterName.LATEST).send();

        BigInteger old = oldBalance.getBalance();
        System.out.println("Initial Ether Balance: " + old);

        System.out.println("Initializing Funding Process");
        boolean status = EthContractGeneration.fundAccount(this.web3j, fundingAdress, this.myAddress);
        System.out.println("Funding Completed");

        if (status) {
            EthGetBalance myNewEthers = this.web3j.ethGetBalance(this.myAddress, DefaultBlockParameterName.LATEST).send();

            BigInteger newBalance = myNewEthers.getBalance();
            System.out.println("New Ether Balance after funding: " + newBalance);
        } else {
            System.out.println("Funding Unsuccessful");
            System.exit(0);
        }

        RawTransactionManager transactionManager = new RawTransactionManager(this.web3j, this.credentials, 4224, 50);
        this.smartContract = StoreTripleData.load(contractAddress, this.web3j, transactionManager, new DefaultGasProvider());
        if (this.smartContract == null) {
            System.out.println("Could not load Smart Contract");
            System.exit(0);
        } else {
            System.out.println("Loaded Smart Contract");
        }
    }



    private boolean remoteValidation(String ownerAddress, byte[] tripleIndex, BigInteger ethers) {
        boolean isValid = false;

        try {
            TransactionReceipt transactionReceipt = this.smartContract.buyData(tripleIndex, ownerAddress, ethers).send();
            if (transactionReceipt.isStatusOK()) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isValid;
    }



    @Override
    public DistributedBlockEntry<long[], PrivateDistributedBlockIndex> encrypt(DistributedBlockEntry<long[], OpenDistributedBlockIndex> blockEntry) {

        DistributedBlockEntry<long[], PrivateDistributedBlockIndex> entry = new DistributedBlockEntry<>();

        long[] record = blockEntry.getBlockRecord();
        String ipfs = new String(blockEntry.getBlockIndex().getRemoteIndex());

        byte[] tripleIndex = stringToBytes32(record);

        if (this.web3j == null) {
            System.out.println("Web3j is null");
            System.exit(0);
            return null;
        }

        if (this.smartContract == null) {
            System.out.println("Smart Contract is null");
            System.exit(0);
            return null;
        }

        try {
            TransactionReceipt insertTransaction = this.smartContract.insertIPFS(tripleIndex, ipfs).send();
            if (insertTransaction.isStatusOK()) {
                entry.setBlockRecord(record);
                PrivateDistributedBlockIndex privateDistributedBlockIndex = new ETHBlockIndex();
                ((ETHBlockIndex) privateDistributedBlockIndex).setClientID(this.myAddress);
                entry.setBlockIndex(privateDistributedBlockIndex);

                return entry;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }



    @Override
    public DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] encrypt(DistributedBlockEntry<long[], OpenDistributedBlockIndex>[] blockEntry) {


        DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] entries = new DistributedBlockEntry[blockEntry.length];

        for (int i = 0; i < blockEntry.length; i++) {

            DistributedBlockEntry<long[], PrivateDistributedBlockIndex> encryptedData = encrypt(blockEntry[i]);

            if (encryptedData != null) {
                entries[i] = encryptedData;
            }
        }

        return entries;
    }



    @Override
    public DistributedBlockEntry<long[], OpenDistributedBlockIndex> validate(DistributedBlockEntry<long[], PrivateDistributedBlockIndex> blockEntry) {
        return null;
    }



    @Override
    public DistributedBlockEntry<long[], OpenDistributedBlockIndex>[] validate(DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] blockEntries) {

        DistributedBlockEntry<long[], OpenDistributedBlockIndex>[] validated = new DistributedBlockEntry[blockEntries.length];

        for (int i = 0; i < blockEntries.length; i++) {

            long[] record = blockEntries[i].getBlockRecord();

            String ownerAddress = ((ETHBlockIndex) blockEntries[i].getBlockIndex()).getClientID();

            byte[] tripleIndex = stringToBytes32(record);

            String ipfs = "";

            if (this.web3j == null) {
                return validated;
            }

            if (this.smartContract == null) {
                return validated;
            }


//            if (ownerAddress.equals(this.myAddress)) {
//                ipfs = fetchIPFS(tripleIndex);
//            } else {
            // Paying 10000000000000 wei which is equal to 0.00001 ether,  0.002 cents for 150Euro/ether
            if (remoteValidation(ownerAddress, tripleIndex, new BigInteger("10000000000000"))) {
                ipfs = fetchIPFS(tripleIndex);
            }
//            }

            if (!StringUtils.isEmpty(ipfs)) {
                IPFSBlockIndex ipfsBlockIndex = new IPFSBlockIndex();
                ipfsBlockIndex.setIPFSHash(ipfs);
                validated[i] = new DistributedBlockEntry<>(ipfsBlockIndex, record);
            }
        }

        return validated;
    }



    private String fetchIPFS(byte[] tripleIndex) {
        String data = "";

        try {
            data = this.smartContract.fetchIPFS(tripleIndex).send();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        if (StringUtils.isEmpty(data)) {
            return "";
        }

        return data;
    }



    private byte[] stringToBytes32(long[] record) {

        byte[] result = new byte[record.length * 8];

        for (int i = 0; i < record.length; i++) {
            result[i * 8] = (byte) (record[i] >> 56);
            result[i * 8 + 1] = (byte) (record[i] >> 48);
            result[i * 8 + 2] = (byte) (record[i] >> 40);
            result[i * 8 + 3] = (byte) (record[i] >> 32);
            result[i * 8 + 4] = (byte) (record[i] >> 24);
            result[i * 8 + 5] = (byte) (record[i] >> 16);
            result[i * 8 + 6] = (byte) (record[i] >> 8);
            result[i * 8 + 7] = (byte) (record[i]);
        }

        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(result, 0, byteValueLen32, 0, result.length);
        return byteValueLen32;
    }

}
