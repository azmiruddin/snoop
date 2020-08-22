package tub.ods.rdf4led.distributed.storage.factory;

import tub.ods.rdf4led.distributed.index.DistributedPhysicalLayer;
import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 06.05.19
 */
public interface PhysicalLayerFactory {
    public DistributedPhysicalLayer<long[], OpenDistributedBlockIndex> createPhysicalLayer();
}
