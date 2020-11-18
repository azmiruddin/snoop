//package test.distributedTest;
//
//import dev.insight.rdf4led.storage.block.BlockEntry;
//import tub.ods.rdf4led.distributed.connector.BlockEntryValidationService;
//import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
//import tub.ods.rdf4led.distributed.storage.block.IPFSBlockIndex;
//
//import java.util.Comparator;
//
//
///**
// * Created by Anh Le-Tuan
// * Email: anh.letuan@tu-berlin.de
// * <p>
// * Date: 28.03.19
// * TODO Description:
// */
//
//public class DistributedBufferLayerTest extends DistributedBufferLayerAbstract<long[]> {
//
//    //Call to remote service
//    BufferLayerService<long[]> buffeLayerRemoteService;
//
//
//    BlockEntryValidationService validationService;
//
//
//    public DistributedBufferLayerTest(BufferLayerService<long[]>  buffeLayerRemoteService,
//                                      BlockEntryValidationService validationService) {
//        super(buffeLayerRemoteService, validationService);
//
//        this.blockEntries = new BlockEntry[5];
//        this.buffeLayerRemoteService = buffeLayerRemoteService;
//        this.validationService = validationService;
//
//        init();
//    }
//
//
//
//    @Override
//    protected Comparator<BlockEntry<long[], ?>> getComparator() {
//        if (super.comparator == null) {
//
//            comparator = (o1, o2) -> {
//                long[] r1 = o1.getBlockRecord();
//                long[] r2 = o2.getBlockRecord();
//
//                if (r1.length != r2.length) {
//                    throw new IllegalArgumentException("length should be the same");
//                }
//
//                for (int i = 0; i < r1.length; i++) {
//                    if (r1[i] == r2[i]) {
//                        continue;
//                    } else {
//                        return r1[i] > r2[i] ? 1 : -1;
//                    }
//
//                }
//
//                return 0;
//            };
//        }
//
//        return comparator;
//    }
//
//    @Override
//    protected boolean updateOne(BlockEntry<long[], ?> blockEntry) {
//        return false;
//    }
//
//
//    @Override
//    public boolean init() {
//        for (long i = 0; i < 10; i++) {
//            if (i % 2 == 0) {
//
//            } else {
//                long[] record = new long[]{i, i, i};
//                IPFSBlockIndex blkIdx = new IPFSBlockIndex();
//                blkIdx.setRemoteIndex(("hash: " + i).getBytes());
//                DistributedBlockEntry<long[], IPFSBlockIndex> blockEntry
//                        = new DistributedBlockEntry<>(blkIdx, record);
//                updateRemote(blockEntry);
//            }
//        }
//        return true;
//    }
//
//
//
//    @Override
//    public DistributedBlockEntry<long[], ?>[] remoteRequest(long[] keyRecord) {
//        return buffeLayerRemoteService.remoteRequest(keyRecord);
//    }
//
//
//
////    @Override
////    public BlockEntry<long[], OpenDistributedBlockIndex>[] validateBlockEntries(BlockEntry<long[], PrivateDistributedBlockIndex>[] blockEntries) {
////        return validationService.validate((blockEntries);
////    }
////
////
////
////    @Override
////    public BlockEntry<long[], ?> validateBlockEntry(BlockEntry<long[], ?> blockEntry) {
////        return null;
////    }
//
//
//}
