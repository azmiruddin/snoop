package tub.ods.common.data.model;

import java.io.Serializable;

public class ObjectFileTransaction implements Serializable {

	private static final long serialVersionUID = 1L;

	private String address;
	private String id;
	private long version;
	private ObjectFileCrypto crypto;
	private ObjectFileOtherValues other_values;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public ObjectFileCrypto getCrypto() {
		return crypto;
	}

	public void setCrypto(ObjectFileCrypto crypto) {
		this.crypto = crypto;
	}

	public ObjectFileOtherValues getOther_values() {
		return other_values;
	}

	public void setOther_values(ObjectFileOtherValues other_values) {
		this.other_values = other_values;
	}

}
