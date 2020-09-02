package tub.ods.rdf4led.distributed.storage.block;


import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 27.03.19
 */
public class IPFSBlockIndex extends OpenDistributedBlockIndex {

    public IPFSBlockIndex() {
    }


    @JsonIgnore
    public void setIPFSHash(String hash) {
        super.setRemoteIndex(hash.getBytes());
    }

    @JsonIgnore
    public String getIPFSHash() {
        byte[] index = super.getRemoteIndex();
        return new String(index);
    }
}
