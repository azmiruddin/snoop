package tub.ods.rdf4led.distributed.storage.block;



/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 20.03.19
 */
public class DistributedBlockIndex {

    private byte[] remoteIndex;



    public DistributedBlockIndex() {
    }



    public byte[] getRemoteIndex() {
        return remoteIndex;
    }



    public void setRemoteIndex(byte[] remoteIndex) {
        this.remoteIndex = remoteIndex;
    }


    @Override
    public String toString(){
        return new String(remoteIndex);
    }
}
