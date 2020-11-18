package tub.ods.pch.channel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock.TransactionObject;

import tub.ods.pch.channel.controller.TransactionBlock;
import tub.ods.pch.channel.util.Converter;
import tub.ods.pch.channel.controller.GenesisBlock;
import tub.ods.pch.channel.controller.Transaction;

public class ChannelServer implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ChannelServer.class);
    private final String myName = "PayChainServer|||";
    public int SOCKET_PORT = 8545;
    GenesisBlock genesisBlock;
    TransactionObject trxObject;
    
    public ChannelServer(int socketPort) throws UnsupportedEncodingException, NoSuchAlgorithmException, JSONException {
        SOCKET_PORT = socketPort;
        genesisBlock = new GenesisBlock();
    }

    public void startServer() throws IOException, JSONException {

        OutputStream os = null;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try {

            serverSocket = new ServerSocket(SOCKET_PORT);
            while (true) {
                LOGGER.info(myName,"Waiting for client...");
                try {
                    clientSocket = serverSocket.accept();
                    LOGGER.info(myName,"Accepted connection : " + clientSocket);

                    DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
                    String header = dis.readUTF();
                    boolean readData = dis.readBoolean();
                    LOGGER.info(myName,"Request received: " + header + "readData=" + readData);
                    DataOutputStream dos = null;
                    String data = null;
                    JSONObject jsonData;
                    switch (header)
                    {
                        case "FeedWallet":

                            if(readData)
                            {
                                data = dis.readUTF();
                                LOGGER.info(myName,"Data from message: " + data);
                            }
                            jsonData = new JSONObject(data);
                            TransactionBlock transactionBlock = Converter.getBlockFromJSON(jsonData);
                            JSONObject deal = jsonData.getJSONObject("transaction");
                            Transaction transaction = Converter.getTransactionFromJSON(deal);
                            transactionBlock.addTransaction(genesisBlock.transactionProcessor,transaction);
                            genesisBlock.addNewBlock(transactionBlock);
                            dos = new DataOutputStream(clientSocket.getOutputStream());
                            dos.writeUTF("GenesisBlockAdded");
                            dos.writeBoolean(false);
                            break;

                        case "NewTransaction":
                            if(readData)
                            {
                                data = dis.readUTF();
                                LOGGER.info(myName,"Data from message: " + data);
                            }
                            jsonData = new JSONObject(data);
                            TransactionBlock block2 = Converter.getBlockFromJSON(jsonData);
                            JSONObject deal2 = jsonData.getJSONObject("transaction");
                            Transaction transaction2 = Converter.getTransactionFromJSON(deal2);
                            block2.addTransaction(genesisBlock.transactionProcessor,transaction2);
                            genesisBlock.addNewBlock(block2);
                            if(genesisBlock.validateBlockchain())
                            {
                                dos = new DataOutputStream(clientSocket.getOutputStream());
                                dos.writeUTF("BlockAdded");
                                dos.writeBoolean(false);
                            }
                            else
                            {
                                genesisBlock.removeLastBlock();
                                dos = new DataOutputStream(clientSocket.getOutputStream());
                                dos.writeUTF("FakeBlock");
                                dos.writeBoolean(false);
                            }

                            break;
                        default:

                            if(readData)
                            {
                                 data = dis.readUTF();
                            }

                            dos = new DataOutputStream(clientSocket.getOutputStream());
                            dos.writeUTF("Wrong header");
                            dos.writeBoolean(false);
                            break;
                    }

                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } finally {

                    if (os != null) os.close();
                    if (clientSocket!=null) clientSocket.close();
                }
            }
        }
        finally {
            if (serverSocket != null) serverSocket.close();
        }
    }



    public void run() {
        try {
            startServer();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
