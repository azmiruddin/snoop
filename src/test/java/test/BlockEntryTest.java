package test;

import dev.insight.rdf4led.storage.block.Block;
import dev.insight.rdf4led.storage.block.BlockEntryAbstract;

public class BlockEntryTest extends BlockEntryAbstract<Integer, Integer> {

    public BlockEntryTest(Integer blkIdx, Block<Integer> block) {
        super(blkIdx, block);
    }


    @Override
    public void setBlock(Block<Integer> block) {
        //TODO verify record
        this.block = block;
    }
}
