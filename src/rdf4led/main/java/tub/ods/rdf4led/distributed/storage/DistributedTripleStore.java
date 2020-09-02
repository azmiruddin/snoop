package tub.ods.rdf4led.distributed.storage;


import dev.insight.rdf4led.common.util.ArrayUtil;
import dev.insight.rdf4led.rdf.dictionary.codec.HashNodeImpl;
import dev.insight.rdf4led.rdf.dictionary.codec.RDFNodeType;
import dev.insight.rdf4led.rdf.graph.Triple;
import dev.insight.rdf4led.rdf.parser.lang.Tokenizer;
import dev.insight.rdf4led.rdf.parser.lang.chars.PeekReader;
import dev.insight.rdf4led.storage.PhysicalLayer;
import dev.insight.rdf4led.storage.block.BlockData;
import tub.ods.rdf4led.distributed.benchmark.ValidationServiceTest;
import tub.ods.rdf4led.distributed.connector.ValidationService;
import tub.ods.rdf4led.distributed.index.*;
import tub.ods.rdf4led.distributed.rdf.Dictionary;
import tub.ods.rdf4led.distributed.rdf.LangNtriples;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;
import tub.ods.rdf4led.distributed.storage.factory.BufferLayerFactory;
import tub.ods.rdf4led.distributed.storage.factory.PhysicalLayerFactory;
import tub.ods.rdf4led.distributed.storage.factory.ValidationServiceFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Iterator;

import static dev.insight.rdf4led.common.util.ArrayUtil.int0long;
import static dev.insight.rdf4led.common.util.ArrayUtil.int2long;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 27.03.19
 * TODO Description:
 */
public class DistributedTripleStore {

    private static int chunk = 5000;

    //TODO: Note move the index initialise out of storage
    private DistributedPhysicalLayer<long[], OpenDistributedBlockIndex> physicalLayer;
    private ValidationService<long[]> validationService;

    private DistributedBufferLayer<long[]> SPO, POS, OSP;
    //private LangNtriples langNTriples;



//    public DistributedTripleStore() {
//        SPO = new RedisBufferLayer("spo", "localhost", 6379);
//        POS = new RedisBufferLayer("pos", "localhost", 6379);
//        OSP = new RedisBufferLayer("ops", "localhost", 6379);
//
//        physicalLayer = new IPFSPhysicalLayer("/ip4/127.0.0.1/tcp/5001");
//        this.validationService = new ValidationServiceTest();
//    }

    public DistributedTripleStore(BufferLayerFactory bufferLayerFactory,
                                  PhysicalLayerFactory physicalLayerFactory,
                                  ValidationServiceFactory validationServiceFactory) {
//        this.SPO = bufferLayerFactory.createBufferLayer("spo");
//        this.POS = bufferLayerFactory.createBufferLayer("pos");
//        this.OSP = bufferLayerFactory.createBufferLayer("osp");

//        this.physicalLayer = physicalLayerFactory.createPhysicalLayer();
//        this.validationService = validationServiceFactory.createValidationService();
    }

    public void parseFromFile(File file) {


        try {
            Tokenizer tokenizer = new Tokenizer(PeekReader.make(new FileReader(file)));
            Dictionary dictionary = new Dictionary();
            LangNtriples langNTriples = new LangNtriples(tokenizer, dictionary);

            int count = 0;
            int blk_count = 0;
            BlockTripleBuffer spo = new BlockTripleBuffer(chunk, dictionary);
            BlockTripleBuffer pos = new BlockTripleBuffer(chunk, dictionary);
            BlockTripleBuffer osp = new BlockTripleBuffer(chunk, dictionary);

            long start_parsing = System.currentTimeMillis();

            while (langNTriples.hasNext()) {

                Triple<Long> triple = langNTriples.parseOne();

                if (triple == null) continue;

                spo.add(new long[]{triple.getSubject(), triple.getPredicate(), triple.getObject()});
                pos.add(new long[]{triple.getPredicate(), triple.getObject(), triple.getSubject()});
                osp.add(new long[]{triple.getObject(), triple.getSubject(), triple.getPredicate()});

                count++;

                if (count == chunk) {
                    long start_sorting = System.currentTimeMillis();
                    long parsing_time  = start_sorting - start_parsing;

                    spo.sort();
                    pos.sort();
                    osp.sort();

                    long end_sorting = System.currentTimeMillis();
                    long sorting_time = end_sorting - start_sorting;


//                    DistributedBlockEntry<long[], OpenDistributedBlockIndex> spoEntry = commit(spo);
//                    DistributedBlockEntry<long[], OpenDistributedBlockIndex> posEntry = commit(pos);
//                    DistributedBlockEntry<long[], OpenDistributedBlockIndex> ospEntry = commit(osp);

                    long end_committing = System.currentTimeMillis();
                    long committing_time = end_committing - end_sorting;

//                    SPO.updateRemote(validationService.encrypt(spoEntry));
//                    POS.updateRemote(validationService.encrypt(posEntry));
//                    OSP.updateRemote(validationService.encrypt(ospEntry));

                    long encrypting_time = System.currentTimeMillis() - end_committing;

                    count = 0;

                    start_parsing = System.currentTimeMillis();

                    spo.reset();
                    pos.reset();
                    osp.reset();
                    dictionary.reset();

                    System.out.println("Block " + blk_count ++ + "==================================");
                    System.out.println("parsing: " + parsing_time);
                    System.out.println("sorting: " + sorting_time);
                    System.out.println("committing: " + committing_time);
                    System.out.println("encrypting: " + encrypting_time);
                    System.out.println(Thread.currentThread().getId());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    public DistributedBlockEntry<long[], OpenDistributedBlockIndex> commit(BlockData<long[]> blockTripleLong) {
        return physicalLayer.commit(blockTripleLong);
    }



    public static int[] makeKey(long[] keyRecord) {
        int[] key = new int[keyRecord.length];
        for (int i = 0; i < keyRecord.length; i++) {
            key[i] = int0long(keyRecord[i]);
        }
        return key;
    }



    public static long[] makeRecord(int[] record) {
        long[] longs = new long[record.length];

        for (int i = 0; i < longs.length; i++) {
            longs[i] = int2long(record[i], 0);
        }
        return longs;
    }

    public static int[] createLowestKey(int[] ints) {
        int[] ints1 = new int[ints.length];
        System.arraycopy(ints, 0, ints1, 0, ints1.length);
        return ints1;
    }



    public static int[] createHighestKey(int[] ints) {
        int[] ints1 = new int[ints.length];
        for (int i = 0; i < ints.length; i++) {
            ints1[i] = ints[i] == 0 ? Integer.MAX_VALUE : ints[i];
        }
        return ints1;
    }


//    public void test() {
//
//        String s1 = "http://iot.org/data/noaa/observation//011830/201809080050/WindSpeedRate/";
//        String p1 = "http://www.w3.org/ns/sosa/resultTime";
//        String s2 = "http://iot.org/data/noaa/observation//011830/201801141750/VISDistance/";
//
//        int hashs1;
//        int hashp1;
//        int node;
//        hashs1 = (int) HashNodeImpl.MD5_Hash_Int.getHash(s1, (byte) 0, RDFNodeType.URI);
//        node = 0;
//        long ls1 = int2long(Math.abs(hashs1), node);
//        hashp1 = (int) HashNodeImpl.MD5_Hash_Int.getHash(p1, (byte) 0, RDFNodeType.URI);
//        long lp1 = int2long(Math.abs(hashp1), node);
//
//        int hashs2 = (int) HashNodeImpl.MD5_Hash_Int.getHash(s2, (byte) 0, RDFNodeType.URI);
//        long ls2   = int2long(Math.abs(hashs2), node);
//
//        Iterator<long[]> iterator = find(0, lp1 , 0);
//
//        DistributedBlockEntry[] pBlockEntries = POS.remoteRequest(new long[]{0,0,0});
//
//        for (int i = 0; i < pBlockEntries.length; i++) {
//            ArrayUtil.println(makeKey((long[]) pBlockEntries[i].getBlockRecord()));
//        }
//    }



    private Iterator<long[]> find(DistributedBufferLayer<long[]> bufferLayer, long[] key) {

        DistributedBlockEntry[] pBlockEntries = bufferLayer.remoteRequest(key);

        DistributedBlockEntry[] oBlockEntries = new DistributedBlockEntry[pBlockEntries.length];

        for (int i = 0; i < pBlockEntries.length; i++) {

            //TODO system.out
            ArrayUtil.println(makeKey((long[]) pBlockEntries[i].getBlockRecord()));

            oBlockEntries[i] = validationService.validate(pBlockEntries[i]);
        }

        Iterator iterator = new RangeIterator(oBlockEntries, key);

        return iterator;

    }



    public Iterator<long[]> find(long s, long p, long o) {
        if (s == 0) {
            if (p == 0) {
                if (o == 0) {
                    // (? ? ?)
                    return find(SPO, new long[]{s, p, o});
                } else {
                    // (? ? O)
                    return find(OSP, new long[]{o, s, p});
                }
            } else {
                // (? P O) and (? P ?)
                return find(POS, new long[]{p, o, s});
            }
        } else {
            if (p == 0) {
                if (o == 0) {
                    // (S ? ?)
                    return find(SPO, new long[]{s, p, o});
                } else {
                    // S ? O)TripleJ2SE
                    return find(OSP, new long[]{o, s, p});
                }
            } else {
                // (S P ?) and (S P O)
                if (o == 0) {
                    return find(SPO, new long[]{s, p, o});
                } else {
                    return find(SPO, new long[]{s, p, o});
                }
            }
        }
    }



    private class RangeIterator implements Iterator<long[]> {

        private DistributedBlockEntry[] blockEntries;

        private int curBlockId;
        private int curRecordId;
        private int lastRecordId;
        private BlockTripleBuffer curBlkBuffer;
        private long[] searchKey;


        private RangeIterator(DistributedBlockEntry[] blockEntries, long[] searchKey) {
            this.blockEntries = blockEntries;
            this.searchKey = searchKey;
            curBlkBuffer = (BlockTripleBuffer) physicalLayer.read(blockEntries[curBlockId]);
            curRecordId = curBlkBuffer.findRecordIdx(searchKey) + 1;
            lastRecordId = curBlkBuffer.findRecordIdx(createLastKey(searchKey));
        }


        @Override
        public boolean hasNext() {
            if (curBlockId == blockEntries.length - 1) {
                if (curRecordId == lastRecordId + 1) {
                    return false;
                }
            }
            return true;
        }



        @Override
        public long[] next() {
            if (!hasNext()) {
                throw new RuntimeException("Iterator has not started yet or already ended");
            }

            if (curRecordId == lastRecordId + 1) {
                curBlockId++;
                curBlkBuffer = (BlockTripleBuffer) physicalLayer.read(blockEntries[curBlockId]);
                curRecordId = 0;
                lastRecordId = curBlkBuffer.findRecordIdx(createLastKey(searchKey));
            }

            long[] t = curBlkBuffer.getRecord(curRecordId);

            //TODO System.out
            curBlkBuffer.print(curRecordId);

            curRecordId++;

            return t;
        }

        private long[] createLastKey(long[] key){
            int[] keyI = makeKey(key);

            for (int i=0; i<keyI.length; i++){
                keyI[i] = keyI[i] == 0 ? Integer.MAX_VALUE : keyI[i];
            }

            return makeRecord(keyI);
        }
    }
}
