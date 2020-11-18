package test.distributedTest;

import com.fasterxml.sort.*;
import dev.insight.rdf4led.common.util.ArrayUtil;

import java.io.*;
import java.util.Comparator;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 09.04.19
 */
public class ExternalSortTest {

    private static Comparator<long[]> comparator = (long[] l1, long[] l2) -> {
        for (int i = 0; i < l1.length; i++) {
            if (l1[i] == l2[i]) continue;
            return l1[i] > l2[i] ? 1 : -1;
        }

        return 0;
    };



    public static void main(String[] args) {
        ExternalSortTest externalSortTest = new ExternalSortTest();
        File file = new File("/tmp/test.in");
        try {
            OutputStream outputStream = new FileOutputStream(file);
            DataWriterTriple dataWriterTriple = new DataWriterTriple(outputStream);
            for (long i = 1; i < 101; i++) {
                for (long j = 1; j < 101; j++) {
                    for (long k = 101; k > 0; k--) {
                        dataWriterTriple.writeEntry(new long[]{i, j, k});
                    }
                }
            }
            dataWriterTriple.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            InputStream source = new FileInputStream(new File("/tmp/test.in"));
            OutputStream des = new FileOutputStream(new File("/tmp/test.sorted"));
            Sorter<long[]> sorter = new Sorter<>(new SortConfig(),
                    new DataReaderFactoryTriple(),
                    new DataWriterFactoryTriple(),
                    comparator
            );


            // To be able to print out progress, need to spin one additional thread...
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    final long start = System.currentTimeMillis();
//                    try {
//                        while (!sorter.isCompleted()) {
//                            Thread.sleep(5000L);
//                            if (sorter.isPreSorting()) {
//                                System.err.printf(" pre-sorting: %d files written\n", sorter.getNumberOfPreSortFiles());
//                            } else if (sorter.isSorting()) {
//                                System.err.printf(" sorting, round: %d/%d\n",
//                                        sorter.getSortRound(), sorter.getNumberOfSortRounds());
//                            }
//                        }
//                        double secs = (System.currentTimeMillis() - start) / 1000.0;
//                        System.err.printf("Completed: took %.1f seconds.\n", secs);
//                    } catch (InterruptedException e) {
//                        double secs = (System.currentTimeMillis() - start) / 1000.0;
//                        System.err.printf("[INTERRUPTED] -- took %.1f seconds.\n", secs);
//                    }
//                }
//            }).start();
            sorter.sort(source, des);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        File sorted = new File("/tmp/test.sorted");
//
        try {

            InputStream inputStream = new FileInputStream(new File("/tmp/test.sorted"));
            DataReaderTriple dataReaderTriple = new DataReaderTriple(inputStream);

            for (int i = 0; i < 1000000; i++) {
                long[] longs = dataReaderTriple.readNext();
                ArrayUtil.println(longs);
                System.out.println(i);
            }

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



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

}
