package tub.ods.pch.channel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChannelClient {
    private final String myName = "PayChainClient|||";
    private int SERVER_PORT = 0;
    private String SERVER_IP = "127.0.0.1";
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelClient.class);

    public ChannelClient(String serverIP, int serverPort)
    {
        SERVER_IP = serverIP;
        SERVER_PORT = serverPort;
    }

    public JSONObject sendMessage(String header, boolean additionalData) {
        final JSONObject[] responseData = new JSONObject[1];
        Thread t = new Thread() {

            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket(SERVER_IP, SERVER_PORT);
                    DataOutputStream dos = null;
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(header);
                    dos.writeBoolean(additionalData);
                    DataInputStream dis = new DataInputStream(socket.getInputStream());

                    String response = dis.readUTF();
                    boolean readAdditionalResponseData = dis.readBoolean();
                    if(readAdditionalResponseData) {
                        String data = dis.readUTF();
                        responseData[0] = new JSONObject(data);
                    }
                    else {
                        responseData[0] = new JSONObject();
                    }
                    dos.flush();
                    dos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
        return responseData[0];
    }

    public JSONObject sendMessageWithData(String header, JSONObject data) {

        final JSONObject[] responseData = new JSONObject[1];
        Thread t = new Thread() {

            public void run() {
                Socket socket = null;
                try {

                    socket = new Socket(SERVER_IP, SERVER_PORT);
                    DataOutputStream dos = null;
                    dos = new DataOutputStream(socket.getOutputStream());
                    System.out.println("JSONObject sendMessageWithData: " + header + " do " + SERVER_IP + " " + SERVER_PORT);
                    LOGGER.info(myName, "JSONObject sendMessageWithData: " + header + " do " + SERVER_IP + " " + SERVER_PORT);
                    dos.writeUTF(header);
                    dos.writeBoolean(true);
                    LOGGER.info(myName, "JSONObject sendMessageWithData: " + data.toString() + " do " + SERVER_IP + " " + SERVER_PORT);
                    dos.writeUTF(data.toString());
                    System.out.println("JSONObject sendMessageWithData: " + data.toString() + " do " + SERVER_IP + " " + SERVER_PORT);

                    LOGGER.info(myName, "JSONObject sendMessageWithData...");
                    DataInputStream dis = new DataInputStream(socket.getInputStream());

                    //Nagłówek wiadomości
                    String response = dis.readUTF();
                    //Czy sa dane
                    boolean readData = dis.readBoolean();
                    switch (response)
                    {
                        case "GenesisBlockAdded":
                        	LOGGER.info(myName, "JSONObject sendMessageWithData: " + response);
                            break;
                        case "BlockAdded":
                        	LOGGER.info(myName, "JSONObject sendMessageWithData: " + response);
                            break;

                        case "FakeBlock":
                        	LOGGER.info(myName, "JSONObject sendMessageWithData: " + response);

                            break;
                        default:
                        	LOGGER.info(myName, "JSONObject sendMessageWithData: Wrong response header");
                            break;
                    }


                    dos.flush();
                    dos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        t.start();
        return responseData[0];
    }
}
