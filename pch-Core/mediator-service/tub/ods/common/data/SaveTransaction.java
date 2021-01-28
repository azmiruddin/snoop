package tub.ods.common.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.Gson;

import tub.ods.common.data.model.ObjectFileTransaction;
import tub.ods.common.data.SerializeUtils;
import redis.clients.jedis.Jedis;

public class SaveTransaction {
	
	Jedis jedis;
	
	/*public StoreData(String server, int port){
		super();
		jedis = new Jedis(server,port); 
	}*/
	
	public void saveDataToRedis(String file, String input) throws FileNotFoundException{
		//ObjectFileTransaction sampleTrx = new ObjectFileTransaction();
    	BufferedReader br = new BufferedReader(new FileReader(file)); 
    	ObjectFileTransaction objectTrx = new Gson().fromJson(br, ObjectFileTransaction.class);  
    	
    	jedis.set(input.getBytes(), SerializeUtils.serialize(objectTrx));	// save data to redis
    	
	}
	
	public ObjectFileTransaction readDataFromRedis(String input){
		
		ObjectFileTransaction sampleTrx = new ObjectFileTransaction();
    	byte[] bytes=jedis.get(input.getBytes());	// get data from redis
    	
    	sampleTrx = (ObjectFileTransaction)SerializeUtils.deSerialize(bytes);
    	
		return sampleTrx;
	}
	/*public void closeConnectionToRedis(){
		jedis.close();
	}*/
}
