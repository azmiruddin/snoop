package tub.ods.pch.channel;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tub.ods.pch.channel.controller.TransactionBlock;
import tub.ods.pch.channel.controller.GenesisBlock;
import tub.ods.pch.channel.controller.Transaction;
import tub.ods.pch.channel.controller.Wallet;
import tub.ods.pch.channel.util.Converter;

public class Main {

    private final static String myName = "Main|||";
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    
    public static void main(String[] args) throws Exception {

		GenesisBlock genesisBlock = new GenesisBlock();
        new Thread(new ChannelServer(15000)).start();
        ChannelClient client = new ChannelClient("127.0.0.1", 15000);

		Wallet testWallet = new Wallet("TestWallet");
		TransactionBlock tmp = genesisBlock.feedWallet(testWallet, 15);
		JSONObject obj = Converter.getJSONFromBlock(tmp);
		client.sendMessageWithData("NewTransaction", obj);

		LOGGER.info(testWallet.getName() + ": " + testWallet.getBalance());


		Wallet receiver = new Wallet("receiver");
		TransactionBlock tmp2 = genesisBlock.feedWallet(receiver, 20);
		JSONObject obj2 = Converter.getJSONFromBlock(tmp2);
		client.sendMessageWithData("NewTransaction", obj2);
		LOGGER.info(receiver.getName() + ": " + receiver.getBalance());


		Transaction deal = new Transaction(testWallet.getPublicKey(), receiver.getPublicKey(), 10);
		JSONObject tmp3 = new JSONObject();
		tmp3.put("TMP3", "tmp3");
		TransactionBlock block = genesisBlock.generateNewBlock(tmp3);
		block.addTransaction(genesisBlock.transactionProcessor,deal);
		LOGGER.info(testWallet.getName() + ": " + testWallet.getBalance());
		System.out.println(receiver.getName() + ": " + receiver.getBalance());
		boolean test = genesisBlock.validateBlockchain();
		LOGGER.info("Main", "Channel established "+test);
    }
}
