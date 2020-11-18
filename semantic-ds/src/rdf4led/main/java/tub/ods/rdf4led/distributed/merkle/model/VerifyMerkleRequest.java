package tub.ods.rdf4led.distributed.merkle.model;

public class VerifyMerkleRequest {

    private int fromId;
    private int toId;
    private long amount;

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

}
