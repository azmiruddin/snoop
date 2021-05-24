package tub.ods.mediator.rest;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthAccounts;
import org.web3j.protocol.core.methods.response.EthCoinbase;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import common.data.json.custome.JSONDataUtils;
import common.data.json.model.ObjectFileTransaction;
import common.data.json.model.ObjectFileTransactionOffChain;

//import common.data.json.custome.JSONDataCustome;
//import common.data.json.model.ObjectFileOtherValues;
//import common.data.json.model.ObjectFileTransaction;
//import common.data.json.serialized.SerializeUtils;
//import src.java.storage.DataStorage;
//import tub.ods.pch.payment.controller.TransactionController;

import src.java.storage.DataStorage;
import tub.ods.mediator.contract.ChannelContract;
import tub.ods.mediator.contract.ChannelManagerContract.ChannelNewEventResponse;
import tub.ods.mediator.contract.SCHToken;
import tub.ods.mediator.controller.ContractsManager;
import tub.ods.mediator.controller.ContractsManagerFactory;
import tub.ods.mediator.controller.ContractsProperties;
import tub.ods.mediator.controller.EthProperties;
import tub.ods.mediator.controller.EthereumConfig;
import tub.ods.mediator.controller.EthereumService;
import tub.ods.mediator.controller.PropertyConvertersConfig;
import tub.ods.mediator.controller.Web3jConfigurer;
import tub.ods.mediator.model.ConfigStorageRequest;
import tub.ods.mediator.model.ConfigStorageResponse;
import tub.ods.mediator.model.ConfigValueRequest;
import tub.ods.mediator.model.ConfigValueResponse;
import tub.ods.mediator.model.DataStoreRequest;
import tub.ods.mediator.model.DataStoreResponse;
import tub.ods.mediator.model.ResponseInit;
import tub.ods.mediator.model.TransactionInputApi;
import tub.ods.mediator.model.TransactionOutputApi;
import tub.ods.mediator.model.TransactionOutputOffchain;
import tub.ods.pch.channel.SignedChannelState;

@RestController
@RequestMapping("/mediatorApi")
@EnableConfigurationProperties({ EthProperties.class, ContractsProperties.class })
@Import({ PropertyConvertersConfig.class, EthereumConfig.class, Web3jConfigurer.class, ContractsManagerFactory.class,
		EthereumService.class })
public class MediatorController {

	@Autowired
	EthProperties ethProperties;

	ContractsProperties contractsProperties;

	@Autowired
	EthereumConfig ethereumConfig;

	@Autowired
	EthereumService ethereumService;

	@Autowired
	ContractsManagerFactory contractsManagerFactory;

	@Autowired
	Web3j web3j;
	Credentials credentials;
	DataStorage pushData;

	private static final String PASSWORD = "qwerty";
	private static BigInteger GAS_PRICE;
	private static BigInteger GAS_LIMIT;
	private final List<String> contracts = new ArrayList<>();
	private final byte[] root = null;
	private static final BigInteger WEI_VALUE = BigInteger.valueOf(100_000L);

	@PostConstruct
	public void init() throws Exception {
		pushData = new DataStorage("localhost", 6379, "/ip4/127.0.0.1/tcp/5001"); // next to be parametrized
		GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
		GAS_LIMIT = BigInteger.valueOf(21_000);
		System.out.println("Default config GAS_PRICE: \t" + GAS_PRICE);
		System.out.println("Default config GAS_LIMIT: \t" + GAS_LIMIT);
		System.out.println("Default config data store :" + "redis host : \t" + "localhost " + "redis port: \t" + "6379 "
				+ "ipfs link :\t" + "/ip4/127.0.0.1/tcp/5001");

	}

	@GetMapping("/init")
	public ResponseInit initAccount() throws NoSuchAlgorithmException, NoSuchProviderException,
			InvalidAlgorithmParameterException, IOException, CipherException {

		ResponseInit response = new ResponseInit();

		ArrayList<String> listTempAccount = new ArrayList<String>();
		ArrayList<String> listTempBalance = new ArrayList<String>();
		ArrayList<String> listTempAddressContract = new ArrayList<String>();
		ArrayList<String> listTempBalanceAddressContract = new ArrayList<String>();

		EthCoinbase coinbase = web3j.ethCoinbase().send();
		EthAccounts accounts = web3j.ethAccounts().send();

		for (int i = 1; i < accounts.getAccounts().size(); i++) { // looping sebanyak account main ganache

			String file = WalletUtils.generateLightNewWalletFile(PASSWORD, null); // generate wallet
			//
			credentials = WalletUtils.loadCredentials(PASSWORD, file);
			System.out.print("adress kredinsial" + credentials.getAddress());

			// get balance address generate
			EthGetBalance balance = web3j.ethGetBalance(credentials.getAddress(), DefaultBlockParameterName.LATEST)
					.send(); // get balance address generate
			EthGetBalance balanceAddressMainGarnace = web3j
					.ethGetBalance(accounts.getAccounts().get(i), DefaultBlockParameterName.LATEST).send(); 

			String account = accounts.getAccounts().get(i);
			String balanceAcctGarnace = String.valueOf(balanceAddressMainGarnace.getBalance());

			listTempAccount.add(account);
			listTempBalance.add(balanceAcctGarnace);

		}

		EthGetBalance balanceCoinbase = web3j.ethGetBalance(coinbase.getAddress(), DefaultBlockParameterName.LATEST).send();

		response.setEthCoinbaseAddress(coinbase.getAddress());
		response.setBalanceEthCoinbaseAddress(String.valueOf(balanceCoinbase.getBalance()));

		String[] stockAcct = new String[listTempAccount.size()];
		stockAcct = listTempAccount.toArray(stockAcct);

		String[] stockBalance = new String[listTempBalance.size()];
		stockBalance = listTempBalance.toArray(stockBalance);

		response.setListEthAccount(stockAcct);
		response.setBalanceListEthAccount(stockBalance);
//    	
		return response;
	}

	@PostMapping("/configStorage")
	public ConfigStorageResponse configStorage(@RequestBody ConfigStorageRequest input) {
		ConfigStorageResponse response = new ConfigStorageResponse();
		pushData = null;

		if (null != input) {
			if (input.getHostRedis() != null && input.getPortRedis() != 0 && input.getAddressIpfs() != null) {
				pushData = new DataStorage(input.getHostRedis(), input.getPortRedis(), input.getAddressIpfs());
			}
		}

		if (null != pushData) {
			response.setResponseCode("01");
			response.setResponseMessage("Config has been successfully");
		} else {
			response.setResponseCode("02");
			response.setResponseMessage("Config is not successfully");
		}

		return response;

	}

	@PostMapping("/configValue")
	public ConfigValueResponse configValue(@RequestBody ConfigValueRequest request) {
		ConfigValueResponse response = new ConfigValueResponse();

		if (null != request) {
			if (request.getPrice() != 0 && request.getLimit() != 0) {
				GAS_PRICE = BigInteger.valueOf(request.getPrice());
				GAS_LIMIT = BigInteger.valueOf(request.getLimit());

				response.setResponseCode("01");
				response.setResponseMessage("Config value has been successfully");
			} else {
				response.setResponseCode("02");
				response.setResponseMessage("No Data Define");
			}

		} else {
			response.setResponseCode("03");
			response.setResponseMessage("No Data Request");
		}

		return response;

	}

	@PostMapping("/transactionOffChain")
	public TransactionOutputOffchain transactionOffChain(@RequestBody TransactionInputApi input) throws Exception {

		JSONDataUtils customJson = new JSONDataUtils();
		HashMap<String, Object> push = new HashMap<String, Object>();
		TransactionOutputOffchain resultFix = new TransactionOutputOffchain();
		ObjectFileTransactionOffChain result = new ObjectFileTransactionOffChain();

		String pkdspCredentials = input.getAddressFromPk();
		Credentials dspCredentials = Credentials.create(pkdspCredentials);

		System.out.println(Numeric.toHexString(Numeric.toBytesPadded(dspCredentials.getEcKeyPair().getPrivateKey(), 32)));

		Address dspAddress = new Address(dspCredentials.getAddress());

		Address sspAddress = new Address(input.getAddressTo());

		Address auditorAddress = new Address(input.getAddressAudit());

		System.out.println("DSP Address: " + dspAddress);
		System.out.println("SSP Address: " + sspAddress);
		System.out.println("Auditor Address: " + auditorAddress);

		ContractsManager dspManager = contractsManagerFactory
				.createManager(ethereumConfig.createTransactionManager(dspCredentials), dspCredentials);

		System.out.println(Numeric.toHexString(Numeric.toBytesPadded(dspCredentials.getEcKeyPair().getPrivateKey(), 32)));

		BigDecimal dspBalance = ethereumService.getBalance(dspAddress.toString(), Convert.Unit.ETHER);
		System.out.println("DSP ETH balance: " + dspBalance);
		System.out.println("SSP ETH balance: " + ethereumService.getBalance(sspAddress.toString(), Convert.Unit.ETHER));
		System.out.println("Auditor ETH balance: " + ethereumService.getBalance(auditorAddress.toString(), Convert.Unit.ETHER));

		System.out.println("DSP Token balance: " + dspManager.getTokenService().getBalance());

		long closeTimeout = 6;
		long settleTimeout = 6;
		long auditTimeout = 100;
		BigInteger deposit = BigInteger.valueOf(input.getValueTrx());
		long nonce = 5;
		BigInteger completedTransfers = BigInteger.valueOf(10);
		int auditTotal = 10;
		int auditFraud = 2;

		System.out.println("Creating channel");
		// create new channel
		TransactionReceipt receipt = dspManager.channelManager().newChannel(dspAddress, sspAddress, 
				new Uint256(closeTimeout), new Uint256(settleTimeout), new Uint256(auditTimeout), auditorAddress).get();
		List<ChannelNewEventResponse> events = dspManager.channelManager().getChannelNewEvents(receipt);
		String tokenAddress = dspManager.channelManager().token2().toString();

		if (events.isEmpty()) {
			throw new IllegalStateException("Channel contract was not created");
		}
		Address address = new Address(events.get(events.size() - 1).channel_address);
		long contractCreated = receipt.getBlockNumber().longValueExact();
		System.out.println("Created contract: " + address + " at block " + contractCreated);
		
		SignedChannelState state = new SignedChannelState(address);

		ChannelContract channelDsp = dspManager.load(ChannelContract.class, address);

		System.out.println("Transfer");
		TransactionReceipt responTransfer = dspManager.token().transfer(sspAddress.toString(), deposit).send();
		BigInteger blok = responTransfer.getBlockNumber(); // transfer
		String trxHashTransfer = responTransfer.getTransactionHash();

		System.out.println("Approving deposit");
		TransactionReceipt responeApprove = dspManager.token().approve(sspAddress.toString(), deposit).send();
		BigInteger blokAppr = responeApprove.getBlockNumber(); // approve
		String trxHashApprove = responeApprove.getTransactionHash();

		BigInteger balanceFrom = dspManager.getTokenService().getBalance();
		BigInteger balanceTo = dspManager.token().balanceOf(sspAddress.toString()).send();

		push.put("addressFrom", dspCredentials.getAddress()); // done
		push.put("trxNonce", state.getNonce()); //get number of transactions sent from a given address.
		push.put("addressTo", sspAddress);
		push.put("addressAudit", input.getAddressAudit());

		push.put("blockNumberTransfer", String.valueOf(blok));
		push.put("trxHashTransfer", trxHashTransfer);

		push.put("blockNumberApprove", String.valueOf(blokAppr));
		push.put("trxHashApprove", trxHashApprove);

		push.put("balanceFrom", balanceFrom);
		push.put("balanceTo", balanceTo);
		push.put("amountTransaction", input.getValueTrx());
		push.put("tokenAddress", tokenAddress);
		push.put("channelAddress", address.toString());
		push.put("amountTransaction", String.valueOf(deposit));

		String file = customJson.createDataToFile(receipt.getTransactionHash(), push);

		String key = pushData.storeDataToIPFSandRedisTransaction(file);

		result = (ObjectFileTransactionOffChain) pushData.readDataObjectFromRedis(key);

		resultFix.setDataTrx(result);
		resultFix.setKey(key);

		return resultFix;
	}

	@PostMapping("/transaction")
	public TransactionOutputOffchain transaction(@RequestBody TransactionInputApi input)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException,
			CipherException, IOException {

		String tokenAddress = input.getTokenAddress();
		JSONDataUtils customJson = new JSONDataUtils();
		HashMap<String, Object> push = new HashMap<String, Object>();
		ObjectFileTransactionOffChain result = new ObjectFileTransactionOffChain();
		TransactionOutputOffchain resultFix = new TransactionOutputOffchain();

		Credentials credentials = Credentials.create(input.getAddressFromPk());

		SCHToken contract = SCHToken.load(tokenAddress, web3j, credentials,
				Convert.toWei("0.001", Convert.Unit.GWEI).toBigInteger(), BigInteger.valueOf(100000));

		try {

			TransactionReceipt receipt = contract
					.transfer(input.getAddressTo(), new BigInteger(String.valueOf(input.getValueTrx()))).send();

			BigInteger balanceFrom = contract.balanceOf(credentials.getAddress()).send();
			BigInteger balanceTo = contract.balanceOf(input.getAddressTo()).send();

			System.out.println("trx hash : " + receipt.getTransactionHash());
			System.out.println("trx block number : " + receipt.getBlockNumber());
			System.out.println("balance from : " + balanceFrom);
			System.out.println("balance to: " + balanceTo);

			List<SCHToken.TransferEventResponse> events = contract.getTransferEvents(receipt);
			events.forEach(event -> System.out
					.println("\n from: " + event._from + " \n to: " + event._to + ",  \n value: " + event._value));

			push.put("addressFrom", credentials.getAddress());
			push.put("addressTo", input.getAddressTo());
			push.put("blockNumber", receipt.getBlockNumber());
			push.put("trxHash", receipt.getTransactionHash());
			push.put("balanceFrom", balanceFrom);
			push.put("balanceTo", balanceTo);
			push.put("amountTransaction", input.getValueTrx());
			push.put("contractAddress", tokenAddress);

			String file = customJson.createDataToFile(receipt.getTransactionHash(), push);

			String key = pushData.storeDataToIPFSandRedisTransaction(file);

			result = (ObjectFileTransactionOffChain) pushData.readDataObjectFromRedis(key);

			resultFix.setDataTrx(result);
			resultFix.setKey(key);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultFix;
	}

	@PostMapping("/simpleTransaction")
	public TransactionOutputApi simpleTransaction(@RequestBody TransactionInputApi input)
			throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException,
			CipherException, IOException {
		ObjectFileTransaction result = new ObjectFileTransaction();
		TransactionOutputApi resutFix = new TransactionOutputApi();
		JSONDataUtils jsonDataUtils = new JSONDataUtils();

		String file = WalletUtils.generateLightNewWalletFile(PASSWORD, null);
		BigInteger valueTrx = BigInteger.valueOf(input.getValueTrx()); // dari fontend

		// credentials = WalletUtils.loadCredentials(PASSWORD, file);

		EthGetTransactionCount transactionCount = web3j
				.ethGetTransactionCount(input.getAddressFrom(), DefaultBlockParameterName.LATEST).send();
		Transaction transaction = Transaction.createEtherTransaction(input.getAddressFrom(),
				transactionCount.getTransactionCount(), GAS_PRICE, GAS_LIMIT, input.getAddressTo(), valueTrx);

		EthSendTransaction response = web3j.ethSendTransaction(transaction).send();
		EthGetBalance balanceTo = web3j.ethGetBalance(input.getAddressTo(), DefaultBlockParameterName.LATEST).send();
		EthGetBalance balanceFrom = web3j.ethGetBalance(input.getAddressFrom(), DefaultBlockParameterName.LATEST)
				.send();

		if (response.getTransactionHash() != null) { // jika tidak null trx sukses
			/*
			 * JSONDataCustome custome = new JSONDataCustome( // format lama file,
			 * input.getAddressFrom(), transactionCount.getTransactionCount(),
			 * input.getAddressTo(), GAS_PRICE, GAS_LIMIT, valueTrx,
			 * balanceFrom.getBalance() , balanceTo.getBalance());
			 */

			HashMap<String, Object> push = new HashMap<String, Object>();

			push.put("coinbaseAddress", input.getAddressFrom());
			push.put("transactionCount", transactionCount.getTransactionCount());
			push.put("credentialsAddress", input.getAddressTo());
			push.put("gasPrice", GAS_PRICE);
			push.put("gasLimit", GAS_LIMIT);
			push.put("valueTrx", valueTrx);
			push.put("balanceFrom", balanceFrom.getBalance());
			push.put("balanceTo", balanceTo.getBalance());

			boolean status = jsonDataUtils.editDataToFile(file, push);

			if (status) {
				String key = pushData.storeDataToIPFSandRedis(file);

				result = pushData.readDataFromRedis(key);

				resutFix.setAddress(result.getAddress());
				resutFix.setCrypto(result.getCrypto());
				resutFix.setId(result.getId());
				resutFix.setVersion(result.getVersion());
				resutFix.setOther_values(result.getOther_values());
				resutFix.setKey(key);
				resutFix.setNameFile(file);
			}

		}

		return resutFix;
	}

	@PostMapping("/trxInquiryRedis")
	public Object readDataFromRedis(@RequestBody DataStoreRequest dataStoreReq) {
		// ObjectFileTransaction result =
		// pushData.readDataFromRedis(dataStoreReq.getKey());
		// return result;

		ObjectFileTransaction result = null;
		ObjectFileTransactionOffChain result2 = null;

		Object resultTemp = pushData.readDataObjectFromRedis(dataStoreReq.getKey());

		if (resultTemp instanceof ObjectFileTransaction) {
			result = (ObjectFileTransaction) resultTemp;

		} else if (resultTemp instanceof ObjectFileTransactionOffChain) {
			result2 = (ObjectFileTransactionOffChain) resultTemp;

		}

		if (result != null) {
			return result;
		}

		else {
			return result2;
		}

	}

	@PostMapping("/trxInquiryIPFS")
	public DataStoreResponse readDataFromIPFS(@RequestBody DataStoreRequest dataStoreReq) {
		DataStoreResponse response = new DataStoreResponse();
		String result = pushData.readDataIPFS(dataStoreReq.getKey());

		if (null != result) {
			response.setKey(dataStoreReq.getKey());
			response.setValue(result);
		} else {
			response.setKey(dataStoreReq.getKey());
			response.setValue("no data found");
		}

		return response;

	}
}
