package test;

import dev.insight.rdf4led.storage.*;
import dev.insight.rdf4led.storage.block.BlockEntry;
import dev.insight.rdf4led.storage.cache.CacheBlock;

import java.util.Iterator;


/**
 * Created by Anh Le Tuan
 * Email: anh.letuan@tu-berlin.de
 * Date: 17.03.19
 * <p>
 * Description:
 * <p>
 * TODO:
 */


public class LayerIndexTest extends LayerIndexAbstract<Integer, Integer> {

    public LayerIndexTest(BufferLayer<Integer> bufferLayer,
                          PhysicalLayer<Integer, Integer> physicalLayer,
                          CacheBlock cacheBlock){
        super(bufferLayer, physicalLayer, cacheBlock);
    }

    @Override
    protected Integer createLowestKey(Integer record) {
        return Integer.MIN_VALUE + 10;
    }



    @Override
    protected Integer createHighestKey(Integer record) {
        return Integer.MAX_VALUE - 10;
    }





//    @Override
//    protected RangeIterator<Integer> createRangeIterator(LayerIndex<Integer> index,
//                                                         BlockEntry<Integer, ?>[] blockEntries,
//                                                         int firstRecordId,
//                                                         int lastRecordIdx)
//    {
//    }


    private static void stopForDebugging(){}


    public static void test1(LayerIndex<Integer> layerIndex){
        //add 100 random number
        for (int i=0; i<201; i++){
            layerIndex.add((i));
        }
    }


    public static void main(String[] args){

        BlockEntry<Integer, ?> blockEntry = new BlockEntryTest(0, new BlockTest());

        int[] testcase = {01, 02, 03, 04, 05, 06, 07,  8,  9, 10,
                          11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                          21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
                          31, 32, 33, 34, 35, 36, 37, 38, 39, 40};

//        BufferLayer<Integer> bufferLayer                = new BufferLayerTest();
//
//        PhysicalLayer<Integer, Integer> physicalLayer   = new PhysicalLayerTest();
//        CacheBlock cacheBlock                           = new CacheBlockTest();
//
//        LayerIndexAbstract<Integer, Integer> layerIndex = new LayerIndexTest(bufferLayer,
//                                                                             physicalLayer,
//                                                                             cacheBlock);
//
//        test1(layerIndex);
//
//        System.out.println(bufferLayer.toString());
//        System.out.println(physicalLayer.toString());
//
//        Iterator<Integer> it = layerIndex.search(1);
//
//        while (it.hasNext()){
//            System.out.println(it.next() + " ");
//        }
//
//        System.out.println(bufferLayer.toString());
//        System.out.println(physicalLayer.toString());

    }




}
