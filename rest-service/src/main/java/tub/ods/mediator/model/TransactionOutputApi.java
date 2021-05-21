package tub.ods.mediator.model;

import common.data.json.model.ObjectFileTransaction;

public class TransactionOutputApi extends ObjectFileTransaction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String key;
	String nameFile;
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getNameFile() {
		return nameFile;
	}
	public void setNameFile(String nameFile) {
		this.nameFile = nameFile;
	}
	
	
	
}
