package tub.ods.pch.contract.model;

public class MerkleTransaction {
	private String contract;
	private long _amount;

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public long getAmount() {
		return _amount;
	}

	public void setAmount(long amount) {
		this._amount = amount;
	}
}
