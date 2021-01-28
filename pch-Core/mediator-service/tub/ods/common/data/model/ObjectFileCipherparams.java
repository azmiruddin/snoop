package tub.ods.common.data.model;

import java.io.Serializable;

public class ObjectFileCipherparams implements Serializable {
	private static final long serialVersionUID = 1L;
	private String iv;

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
	}
	
	

}
