package com.mediator.common;



import java.io.Serializable;

public class ObjectFileCrypto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String cipher;
	private String ciphertext;
	private ObjectFileCipherparams cipherparams;
	private String kdf;
	private ObjectFileKdfparams kdfparams;
	private String mac;
	public String getCipher() {
		return cipher;
	}
	public void setCipher(String cipher) {
		this.cipher = cipher;
	}
	public String getCiphertext() {
		return ciphertext;
	}
	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}
	public ObjectFileCipherparams getCipherparams() {
		return cipherparams;
	}
	public void setCipherparams(ObjectFileCipherparams cipherparams) {
		this.cipherparams = cipherparams;
	}
	public String getKdf() {
		return kdf;
	}
	public void setKdf(String kdf) {
		this.kdf = kdf;
	}
	public ObjectFileKdfparams getKdfparams() {
		return kdfparams;
	}
	public void setKdfparams(ObjectFileKdfparams kdfparams) {
		this.kdfparams = kdfparams;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	
	
	
	
}
