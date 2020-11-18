package tub.ods.pch.contract;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.web3j.protocol.Web3j;

import tub.ods.pch.channel.BlockchainChannel;
import tub.ods.pch.channel.TransactionRepository;
import tub.ods.pch.contract.service.ContractMerkleService;

@SpringBootApplication
public class ContractApp {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContractApp.class);
	
    @Autowired
    Web3j web3j;
    @Autowired
    ContractMerkleService serviceContract;
    BlockchainChannel serviceChannel;
    TransactionRepository serviceStore;
    
    public static void main(String[] args) {
        SpringApplication.run(ContractApp.class, args);
    }
    
    @PostConstruct
    public void listen() {
        web3j.transactionFlowable().subscribe(tx -> {
    		if (tx.getTo() != null && tx.getTo().equals(serviceContract.getOwnerAccount())) {
    			LOGGER.info("New tx: id={}, block={}, from={}, to={}, value={}", tx.getHash(), tx.getBlockHash(), tx.getFrom(), tx.getTo(), tx.getValue().intValue());
    			//channel state service
    			serviceChannel.getChannelAddress();    			
                serviceContract.MerkleVerification();
    			serviceChannel.getClosingAddress();
    			serviceChannel.getClosed();
    			serviceChannel.getCompletedTransfers();
				LOGGER.info("Get channel service: address={}, closedAddress={}, state={}, value={}", serviceChannel.getChannelAddress(),
						serviceChannel.getClosingAddress(), serviceChannel.getClosed(), serviceChannel.getCompletedTransfers());
                //store service
//                serviceStore.getClass(SignedTransfer.class);

//    			service.setChannelSender(toString());

//    			service.timeout();
//    			service.closeChannel(tx.getValue(), null);
    		}else {
    			LOGGER.info("Not matched: id={}, to={}", tx.getHash(), tx.getTo());
    		}
    	});
    }
/*
    @PostConstruct
    public void listen0() {
        web3j.transactionFlowable().subscribe(tx -> {
    		if (tx.getTo() != null && tx.getTo().equals(service.getOwnerAccount())) {
    			LOGGER.info("New tx: id={}, block={}, from={}, to={}, value={}", tx.getHash(), tx.getBlockHash(), tx.getFrom(), tx.getTo(), tx.getValue().intValue());
    			service.processContracts(tx.getValue().longValue());
    			service.merkleVerification(tx.getBlockNumber().toByteArray(), tx.getNonce().toByteArray(), null);
    			service.timeout();
    			service.closeChannel(tx.getValue(), null);
    			LOGGER.info("Channel State");
    		}else {
    			LOGGER.info("Not matched: id={}, to={}", tx.getHash(), tx.getTo());
    		}
    	});
    }
    

    @PostConstruct
    public void listen1() {
        web3j.transactionFlowable().subscribe(tx -> {
    		if (tx.getTo() != null && tx.getTo().equals(serviceContract.getOwnerAccount())) {
    			LOGGER.info("New tx: id={}, block={}, from={}, to={}, value={}", tx.getHash(), tx.getBlockHash(), tx.getFrom(), tx.getTo(), tx.getValue().intValue());
    			//channel state service
    			serviceChannel.getChannelAddress();
    			serviceChannel.getClosingAddress();
    			serviceChannel.getClosed();
    			serviceChannel.getCompletedTransfers();
                //store service
                serviceStore.getClass(SignedTransaction.class);

    			service.setChannelSender(toString());
    			service.merkleVerification(tx.getBlockNumber().toByteArray(), tx.getNonce().toByteArray(), null);
    			service.timeout();
    			service.closeChannel(tx.getValue(), null);
    		}else {
    			LOGGER.info("Not matched: id={}, to={}", tx.getHash(), tx.getTo());
    		}
    	});
    }
    */
    
}
