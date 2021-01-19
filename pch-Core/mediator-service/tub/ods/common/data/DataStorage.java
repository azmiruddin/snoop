package tub.ods.common.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

import tub.ods.common.data.model.ObjectFileTransaction;
import tub.ods.common.data.SerializeUtils;
import tub.ods.rdf4led.distributed.benchmark.ValidationServiceTest;
import tub.ods.rdf4led.distributed.index.IPFSPhysicalLayer;
import tub.ods.rdf4led.distributed.index.RedisBufferLayer;
import tub.ods.rdf4led.distributed.storage.DistributedTripleStore;
import tub.ods.rdf4led.distributed.storage.factory.BufferLayerFactory;
import tub.ods.rdf4led.distributed.storage.factory.PhysicalLayerFactory;
import tub.ods.rdf4led.distributed.storage.factory.ValidationServiceFactory;

public class DataStorage {

	DistributedTripleStore distributedTripleStore;

	public DataStorage(String redisServer, int redisPort, String ipfsAddress) {
		BufferLayerFactory bufferLayerFactory = (name) -> { // test 2 using sematic-ds
			// TODO host and port are subjects to be modified
			return new RedisBufferLayer(name, redisServer, redisPort);
		};

		PhysicalLayerFactory physicalLayerFactory = () -> {
			// TODO IPAddress is subject to be modified.
			return new IPFSPhysicalLayer(ipfsAddress);
		};

		ValidationServiceFactory validationServiceFactory = () -> {
			// TODO create appropriated validation service
			return new ValidationServiceTest();
		};

		distributedTripleStore = new DistributedTripleStore(bufferLayerFactory, physicalLayerFactory,
				validationServiceFactory, redisServer, redisPort, ipfsAddress);
	}

	public String storeDataToIPFSandRedis(String file) throws FileNotFoundException {
		System.out.println("File name: ");
		BufferedReader br = new BufferedReader(new FileReader(file));
		ObjectFileTransaction objectTrx = new Gson().fromJson(br, ObjectFileTransaction.class);

		byte[] value = SerializeUtils.serialize(objectTrx);

		File inputFile = new File(file);
		return distributedTripleStore.parseFromFile(inputFile, value);
	}

	public String readDataIPFS(String hash) {
		return distributedTripleStore.readIPFS(hash);
	}

	public ObjectFileTransaction readDataFromRedis(String key) {
		String send = "client --> " + key;
		return distributedTripleStore.readRedis(send);
	}

}