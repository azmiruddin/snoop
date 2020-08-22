//package test.distributedTest;
//
//import com.google.gson.Gson;
//import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
//import tub.ods.rdf4led.distributed.storage.block.IPFSBlockIndex;
//
//
///**
// * Created by Anh Le-Tuan
// * Email: anh.letuan@tu-berlin.de
// * <p>
// * Date: 30.03.19
// * TODO Description:
// */
//public class GsonTest {
//    public static void main(String[] args){
//
//        IPFSBlockIndex ipfsBlockIndex = new IPFSBlockIndex();
//                       ipfsBlockIndex.setRemoteIndex("abcdedf".getBytes());
//
//
//        DistributedBlockEntry<long[], IPFSBlockIndex> ipfsBlockEntry
//                = new DistributedBlockEntry<>(ipfsBlockIndex, new long[]{1,2,3});
//
//        Gson gson = new Gson();
//
//        System.out.println(gson.toJson(ipfsBlockEntry));
//
//    }
//
//}
