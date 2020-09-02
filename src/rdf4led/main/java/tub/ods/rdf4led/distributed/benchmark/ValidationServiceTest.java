package tub.ods.rdf4led.distributed.benchmark;

import tub.ods.rdf4led.distributed.connector.ValidationService;
import tub.ods.rdf4led.distributed.index.DistributedBufferLayer;
import tub.ods.rdf4led.distributed.storage.block.*;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 09.04.19
 */
public class ValidationServiceTest implements ValidationService<long[]> {

    @Override
    public DistributedBlockEntry<long[], PrivateDistributedBlockIndex> encrypt(DistributedBlockEntry<long[], OpenDistributedBlockIndex> blockEntry) {

        IPFSBlockIndex ipfsBlockIndex = (IPFSBlockIndex) blockEntry.getBlockIndex();
        String ipfsHash = ipfsBlockIndex.getIPFSHash();

        ETHBlockIndex ethBlockIndex = new ETHBlockIndex();
        ethBlockIndex.setClientID("client --> " + ipfsHash);

        ETHBlockEntry ethBlockEntry = new ETHBlockEntry();
        ethBlockEntry.setBlockIndex(ethBlockIndex);
        ethBlockEntry.setBlockRecord(blockEntry.getBlockRecord());


        return ethBlockEntry;
    }



    @Override
    public DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] encrypt(DistributedBlockEntry<long[], OpenDistributedBlockIndex>[] blockEntries) {
        DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] prBlockEntries = new DistributedBlockEntry[blockEntries.length];

        for (int i = 0; i < blockEntries.length; i++) {
            DistributedBlockEntry<long[], ?> ethBlockEntry = encrypt(blockEntries[i]);
            prBlockEntries[i] = (ETHBlockEntry) ethBlockEntry;
        }

        return prBlockEntries;
    }



    @Override
    public DistributedBlockEntry<long[], OpenDistributedBlockIndex> validate(DistributedBlockEntry<long[], PrivateDistributedBlockIndex> blockEntry) {
        ETHBlockIndex ethBlockIndex = (ETHBlockIndex) blockEntry.getBlockIndex();
        String clientId = ethBlockIndex.getClientID();

        IPFSBlockIndex ipfsBlockIndex = new IPFSBlockIndex();
        String ipfsHash = clientId.replace("client --> ", "");
        ipfsBlockIndex.setIPFSHash(ipfsHash);

        IPFSBlockEntry ipfsBlockEntry = new IPFSBlockEntry();
        ipfsBlockEntry.setBlockRecord(blockEntry.getBlockRecord());
        ipfsBlockEntry.setBlockIndex(ipfsBlockIndex);

        return ipfsBlockEntry;
    }



    @Override
    public DistributedBlockEntry<long[], OpenDistributedBlockIndex>[] validate(DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] blockEntries) {
        DistributedBlockEntry<long[], OpenDistributedBlockIndex>[] oBlockEntries = new DistributedBlockEntry[blockEntries.length];

        for (int i = 0; i < blockEntries.length; i++) {
            DistributedBlockEntry<long[], ?> ethBlockEntry = validate(blockEntries[i]);
            oBlockEntries[i] = (IPFSBlockEntry) ethBlockEntry;
        }

        return oBlockEntries;
    }

}
