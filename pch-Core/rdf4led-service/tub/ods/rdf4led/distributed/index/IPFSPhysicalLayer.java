package tub.ods.rdf4led.distributed.index;

import dev.insight.rdf4led.common.util.ArrayUtil;
import dev.insight.rdf4led.storage.block.BlockData;
import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import tub.ods.rdf4led.distributed.storage.BlockTripleBuffer;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.IPFSBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.IPFSBlockIndex;
import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 01.04.19
 * TODO Description:
 */
public class IPFSPhysicalLayer implements DistributedPhysicalLayer<long[], OpenDistributedBlockIndex> {

    private IPFS ipfs;
    private String IPFSAddress;
    private static int ctr = 0;



    public IPFSPhysicalLayer(String IPFSAddress) {
        this.IPFSAddress = IPFSAddress;
        init();
    }



    public void init() {
        try {
        	MultiAddress address = new MultiAddress(IPFSAddress);
            this.ipfs = new IPFS(address);
        } catch (Exception e) {
            waitforConnection();
        }
    }



    private void waitforConnection() {
        if (ctr < 4) {
            try {
                Thread.sleep(5000);
                ctr++;
            } catch (Exception e) {

            } finally {
                init();
            }
        }
    }



    @Override
    public DistributedBlockEntry<long[], OpenDistributedBlockIndex> commit(BlockData<long[]> blockData) {
        NamedStreamable raw = new NamedStreamable.ByteArrayWrapper(blockData.getBytes());
        try {
            MerkleNode addResult = ipfs.add(raw).get(0);
            String hash = addResult.hash.toString();
            
          //  System.out.println("key simpan ke IPFS : "+hash);

            IPFSBlockIndex ipfsBlockIndex = new IPFSBlockIndex();
            ipfsBlockIndex.setIPFSHash(hash);

            IPFSBlockEntry blockEntry = new IPFSBlockEntry();

            //TODO check if block is sorted
            long[] blockMin = blockData.getRecord(0);
            long[] blockMax = blockData.getRecord(blockData.getNumRecords()-1);
            long[] record   = ArrayUtil.concat(blockMin, blockMax);


            blockEntry.setBlockRecord(record);
            blockEntry.setBlockIndex(ipfsBlockIndex);

            return blockEntry;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public BlockData<long[]> read(DistributedBlockEntry<long[], OpenDistributedBlockIndex> blockEntry) {
        byte[] remoteKey = blockEntry.getBlockIndex().getRemoteIndex();

        Multihash ipfsFileLocation = Multihash.fromBase58(new String(remoteKey));

        try {
            byte[] fileContents = ipfs.cat(ipfsFileLocation);

            BlockTripleBuffer blockTripleBuffer = new BlockTripleBuffer(fileContents);

            blockEntry.setBlockRecord(blockTripleBuffer.getRecord(0));

            return blockTripleBuffer;

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



	@Override
	public String readDataFromIPFS(DistributedBlockEntry<long[], OpenDistributedBlockIndex> blockEntry) {
		 byte[] remoteKey = blockEntry.getBlockIndex().getRemoteIndex();
		 String result ;
		  ObjectMapper mapper = new ObjectMapper();

	        Multihash ipfsFileLocation = Multihash.fromBase58(new String(remoteKey));

	        try {
	            byte[] fileContents = ipfs.cat(ipfsFileLocation);
	        	//byte[] fileContents = ipfs.get(ipfsFileLocation);
	            
	            String hasil = new String(fileContents);

	           /* BlockTripleBuffer blockTripleBuffer = new BlockTripleBuffer(fileContents);
	            
	            IPFSBlockIndex ipfsBlockIndex = new IPFSBlockIndex();
	            ipfsBlockIndex.setIPFSHash(ipfsFileLocation.toString());

	            IPFSBlockEntry ipfsBlockEntry = new IPFSBlockEntry();
	            ipfsBlockEntry.setBlockRecord(blockTripleBuffer.getRecord(0));
	            ipfsBlockEntry.setBlockIndex(ipfsBlockIndex);*/
	            
	            /*result = mapper.writeValueAsString(ipfsBlockEntry);*/

	            

	            return hasil;

	        } catch (IOException e) {
	            throw new RuntimeException(e.getMessage());
	        }
	}



	@Override
	public String readDataFromIPFS(String key) {
		// TODO Auto-generated method stub
		 Multihash ipfsFileLocation = Multihash.fromBase58(key);
		 try {
	            byte[] fileContents = ipfs.cat(ipfsFileLocation);
	        	
	            String hasil = new String(fileContents);
	            
	            
	            String resultz = hasil.substring(hasil.indexOf("["), hasil.length()-4);
	            
	            return resultz;

	        } catch (IOException e) {
	            throw new RuntimeException(e.getMessage());
	        }
	}


}
