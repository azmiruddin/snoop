package tub.ods.rdf4led.distributed.index;

import dev.insight.rdf4led.storage.block.BlockData;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockIndex;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 28.03.19
 * TODO Description:
 */
public interface DistributedPhysicalLayer<R, I extends DistributedBlockIndex> { //extends PhysicalLayer<R, I> {

    public DistributedBlockEntry<R, I> commit(BlockData<R> blockData);

    public BlockData<R> read(DistributedBlockEntry<R, I> blockEntry);
}
