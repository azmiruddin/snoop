package tub.ods.rdf4led.distributed.connector;

import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;
import tub.ods.rdf4led.distributed.storage.block.PrivateDistributedBlockIndex;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 29.03.19
 *
 *
 */
public interface ValidationService<R> {

    public DistributedBlockEntry<R, PrivateDistributedBlockIndex> encrypt(DistributedBlockEntry<R, OpenDistributedBlockIndex> blockEntry);

    public DistributedBlockEntry<R, PrivateDistributedBlockIndex>[] encrypt(DistributedBlockEntry<R, OpenDistributedBlockIndex>[] blockEntry);

    public DistributedBlockEntry<R, OpenDistributedBlockIndex> validate(DistributedBlockEntry<R, PrivateDistributedBlockIndex> blockEntry);

    public DistributedBlockEntry<R, OpenDistributedBlockIndex>[] validate(DistributedBlockEntry<R, PrivateDistributedBlockIndex>[] blockEntries);

}
