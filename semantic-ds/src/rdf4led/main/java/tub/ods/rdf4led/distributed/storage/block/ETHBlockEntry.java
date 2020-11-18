package tub.ods.rdf4led.distributed.storage.block;

/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 01.04.19
 * TODO Description:
 */
public class ETHBlockEntry extends DistributedBlockEntry<long[], PrivateDistributedBlockIndex>{

    public ETHBlockEntry(){};

    @Override
    public String toString(){
        String result = "{ [ ";

        for (int i = 0; i< blockRecord.length -1; i++){
            result = result + blockRecord[i] + ", ";
        }

        result = result + blockRecord[blockRecord.length-1] + "] : " + blockIndex.toString() + " }";


        return result;
    }

}
