//package tub.ods.rdf4led.distributed.storage;
//
//import tub.ods.rdf4led.distributed.connector.ValidationService;
//import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
//import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;
//import tub.ods.rdf4led.distributed.storage.block.PrivateDistributedBlockIndex;
//
//
///**
// * Created by Anh Le-Tuan
// * Email: anh.letuan@tu-berlin.de
// * <p>
// * Date: 31.03.19
// * TODO Description:
// */
//public class HttpValidationService implements ValidationService {
//
//
//    @Override
//    public DistributedBlockEntry<?, PrivateDistributedBlockIndex> encrypt(DistributedBlockEntry<?, OpenDistributedBlockIndex> blockEntry) {
//        return null;
//    }
//
//
//
//    @Override
//    public DistributedBlockEntry<?, PrivateDistributedBlockIndex>[] encrypt(DistributedBlockEntry<?, OpenDistributedBlockIndex>[] blockEntry) {
//        return new DistributedBlockEntry[0];
//    }
//
//
//
//    @Override
//    public DistributedBlockEntry<?, OpenDistributedBlockIndex> validate(DistributedBlockEntry<?, PrivateDistributedBlockIndex> blockEntry)
//    {
//        return null;
//    }
//
//
//
//    @Override
//    public DistributedBlockEntry<?, OpenDistributedBlockIndex>[] validate(DistributedBlockEntry<?, PrivateDistributedBlockIndex>[] blockEntries) {
//        return new DistributedBlockEntry[0];
//    }
//
//}
