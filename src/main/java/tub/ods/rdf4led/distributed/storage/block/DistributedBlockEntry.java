package tub.ods.rdf4led.distributed.storage.block;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.insight.rdf4led.storage.block.BlockEntryAbstract;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 21.03.19
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DistributedBlockEntry<R, I extends DistributedBlockIndex> extends BlockEntryAbstract<R, I> {


    public DistributedBlockEntry(I blkIdx, R record) {
        super(blkIdx, record);
    }



    public DistributedBlockEntry() {
        super();
    }


}
