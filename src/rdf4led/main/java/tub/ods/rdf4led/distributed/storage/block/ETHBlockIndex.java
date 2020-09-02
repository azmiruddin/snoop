package tub.ods.rdf4led.distributed.storage.block;


import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 27.03.19
 */
public class ETHBlockIndex extends PrivateDistributedBlockIndex {

    public ETHBlockIndex(){

    }

    @JsonIgnore
    public void setClientID(String txID){
        super.setRemoteIndex(txID.getBytes());
    }

    @JsonIgnore
    public String getClientID(){
        byte[] id = super.getRemoteIndex();
        return new String(id);
    }
}
