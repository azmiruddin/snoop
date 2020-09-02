package tub.ods.rdf4led.distributed.benchmark;


import tub.ods.rdf4led.distributed.index.IPFSPhysicalLayer;
import tub.ods.rdf4led.distributed.index.RedisBufferLayer;
import tub.ods.rdf4led.distributed.storage.DistributedTripleStore;
import tub.ods.rdf4led.distributed.storage.factory.BufferLayerFactory;
import tub.ods.rdf4led.distributed.storage.factory.PhysicalLayerFactory;
import tub.ods.rdf4led.distributed.storage.factory.ValidationServiceFactory;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 28.03.19
 * TODO Description:
 */
public class DistributedTripleIndexTest {

    public static void main(String[] args) {

        BufferLayerFactory bufferLayerFactory = (name) -> {
            //TODO host and port are subjects to be modified
            return new RedisBufferLayer(name, "localhost", 6379);
        };


        PhysicalLayerFactory physicalLayerFactory = () -> {
            //TODO IPAddress is subject to be modified.
            return new IPFSPhysicalLayer("/ip4/127.0.0.1/tcp/5001");
        };


        ValidationServiceFactory validationServiceFactory = () -> {
            //TODO create appropriated valiation service
            return new ValidationServiceTest();
        };


        DistributedTripleStore distributedTripleStore = new DistributedTripleStore(
                bufferLayerFactory, physicalLayerFactory, validationServiceFactory
        );


        //TODO Read all files from storage folder read file by file
        // Record time to commit each file.

//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                File inputFile = new File(args[0]);
//                distributedTripleStore.parseFromFile(inputFile);
//
//            }
//        };
//
//        Runnable runnable1 = new Runnable() {
//            @Override
//            public void run() {
//                File inputFile = new File(args[1]);
//                distributedTripleStore.parseFromFile(inputFile);
//            }
//        };


//        new Thread(runnable).run();
//        new Thread(runnable1).run();

        //
        ExecutorService pool = Executors.newFixedThreadPool(8);

        for (String fn : args) {
            File inputFile = new File(fn);
            pool.execute(() -> {
                distributedTripleStore.parseFromFile(inputFile);
            });
        }

    }

}
