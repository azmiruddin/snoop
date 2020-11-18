package tub.ods.rdf4led.distributed.storage;

import com.fasterxml.sort.*;
import dev.insight.rdf4led.common.iterator.NullIterator;
import dev.insight.rdf4led.common.util.ArrayUtil;
import dev.insight.rdf4led.storage.block.BlockData;
import tub.ods.rdf4led.distributed.connector.ValidationService;
import tub.ods.rdf4led.distributed.index.DistributedBufferLayer;
import tub.ods.rdf4led.distributed.index.DistributedBufferLayerSecure;
import tub.ods.rdf4led.distributed.index.DistributedPhysicalLayer;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;
import tub.ods.rdf4led.distributed.storage.block.PrivateDistributedBlockIndex;

import java.io.*;
import java.util.Comparator;
import java.util.Iterator;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 27.03.19
 * TODO Description:
 */
public class DistributedTripleIndex { //extends LayerIndexAbstract<long[], DistributedBlockIndex> {

    //SPO POS or OSP
    private String indexName;
    private DistributedPhysicalLayer<long[], OpenDistributedBlockIndex> physicalLayer;
    private DistributedBufferLayer<long[]> bufferLayer;
    private ValidationService<long[]> validationService;

    private static File tmp;
    private static File tmp_index;
    DataWriterTriple dataWriterTriple;



    public DistributedTripleIndex(String indexName,
                                  DistributedBufferLayerSecure<long[]> bufferLayer,
                                  DistributedPhysicalLayer<long[], OpenDistributedBlockIndex> physicalLayer,
                                  ValidationService<long[]> validationService) {

        tmp = new File("/tmp/" + indexName.hashCode() + ".tmp");
        tmp_index = new File("/tmp/" + indexName + indexName.hashCode() + ".tmp");
        this.physicalLayer = physicalLayer;
        this.bufferLayer = bufferLayer;
        this.validationService = validationService;
        this.indexName = indexName;

        try {
            dataWriterTriple = new DataWriterTriple(new FileOutputStream(tmp));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }



    //Distributed triple Index does not support update
    //Init
    public boolean add(long l1, long l2, long l3) {

        try {
            dataWriterTriple.writeEntry(new long[]{l1, l2, l3});
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }



    //build index using external sorted
    public boolean commit() {

        try {
            dataWriterTriple.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        SortConfig sortConfig = new SortConfig();
        sortConfig = sortConfig.withMaxMemoryUsage(15 * 1024 * 1024);

        Sorter<long[]> sorter = new Sorter<>(sortConfig,
                new DataReaderFactoryTriple(),
                new DataWriterFactoryTriple(),
                comparator);
        try {
            sorter.sort(new FileInputStream(tmp), new FileOutputStream(tmp_index));
        } catch (IOException e) {
            return false;
        }

        try {
            InputStream inputStream = new FileInputStream(tmp_index);

            BlockTripleLongBuffer blockTripleLong = new BlockTripleLongBuffer();

            DataReaderTriple dataReaderTriple = new DataReaderTriple(inputStream);

            long[] triple;

            while ((triple = dataReaderTriple.readNext()) != null) {

                if (blockTripleLong.isFull()) {

                    commit(blockTripleLong);
                }

                blockTripleLong.add(triple);
            }

            commit(blockTripleLong);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }



    private void commit(BlockData<long[]> blockTripleLong) {

        DistributedBlockEntry<long[], OpenDistributedBlockIndex> oBlockEntry;

        oBlockEntry = physicalLayer.commit(blockTripleLong);

        DistributedBlockEntry<long[], PrivateDistributedBlockIndex> pBlockEntry = validationService.encrypt(oBlockEntry);

        bufferLayer.updateRemote(pBlockEntry);
    }



    public Iterator<long[]> search(long[] record) {

        DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] pblockEntries = (DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[]) bufferLayer.remoteRequest(record);

        for (DistributedBlockEntry<long[], PrivateDistributedBlockIndex> blockEntry : pblockEntries) {
            System.out.println("Receiving private block: " + (blockEntry).toString());
        }

        long start = System.currentTimeMillis();
        System.out.println("Beginning : " + start);

        DistributedBlockEntry<long[], OpenDistributedBlockIndex>[] oblockEntries = validationService.validate(pblockEntries);

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Darshan End of All " + elapsed);

        for (DistributedBlockEntry<long[], OpenDistributedBlockIndex> blockEntry : oblockEntries) {
            String s = new String(blockEntry.getBlockIndex().getRemoteIndex());
            System.out.println("Receiving open block: " + s);
        }

        //TODO  need to review this code
//        DistributedBlockEntry<long[], ?>[] validatedBlockEntries = distributedBufferLayer.validateBlockEntries((DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] )blockEntries);
//
//        distributedBufferLayer.update(validatedBlockEntries);
//
//        return super.search(record);

        return new NullIterator();
    }



    //================================================================================================================//
    private static Comparator<long[]> comparator = (long[] l1, long[] l2) -> {
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] == l2[i]) continue;
            return l1[i] > l2[i] ? 1 : -1;
        }

        return 0;
    };




    private static class DataReaderFactoryTriple extends DataReaderFactory<long[]> {

        public DataReaderFactoryTriple() {
        }



        @Override
        public DataReader<long[]> constructReader(InputStream inputStream) throws IOException {

            return new DataReaderTriple(inputStream);
        }

    }




    private static class DataReaderTriple extends DataReader<long[]> {

        private final int length = 24; //size of 3 long in bytes
        private InputStream inputStream;



        private DataReaderTriple(InputStream inputStream) {
            this.inputStream = inputStream;
        }



        @Override
        public long[] readNext() throws IOException {
            if (inputStream.available() > 0) {
                byte[] next_byte = new byte[24];
                inputStream.read(next_byte);
                long[] next_long = ArrayUtil.bytes2longs(next_byte);
                return next_long;
            } else {

                return null;
            }
        }



        @Override
        public int estimateSizeInBytes(long[] longs) {
            return 24;
        }



        @Override
        public void close() throws IOException {
            inputStream.close();
        }

    }




    private static class DataWriterFactoryTriple extends DataWriterFactory<long[]> {

        @Override
        public DataWriter<long[]> constructWriter(OutputStream outputStream) throws IOException {
            return new DataWriterTriple(outputStream);
        }

    }




    private static class DataWriterTriple extends DataWriter<long[]> {

        private OutputStream outputStream;



        public DataWriterTriple(OutputStream outputStream) {
            this.outputStream = outputStream;
        }



        @Override
        public void writeEntry(long[] longs) throws IOException {
            outputStream.write(ArrayUtil.longs2bytes(longs));
        }



        @Override
        public void close() throws IOException {
            outputStream.close();
        }

    }

    public void setValidationService(ValidationService<long[]> validationService) {
        this.validationService = validationService;

    }
}
