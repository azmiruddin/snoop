package tub.ods.rdf4led.distributed.benchmark;

import io.ipfs.api.IPFS;
import io.ipfs.multiaddr.MultiAddress;
import io.ipfs.multihash.Multihash;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.OpenDistributedBlockIndex;

import java.io.IOException;

/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 18.04.19
 */
@RestController
public class HTTPIPFSListener {

    private IPFS ipfs;
    public HTTPIPFSListener(){
        this.ipfs = new IPFS(new MultiAddress("/ip4/127.0.0.1/tcp/5001"));
    }

    @PostMapping("/update")
    public ResponseEntity<String> insertIpfs(@RequestBody DistributedBlockEntry<long[], OpenDistributedBlockIndex> blockEntry) {
        accessIPFS(blockEntry);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    private void accessIPFS(DistributedBlockEntry<long[], OpenDistributedBlockIndex> blockEntry) {
        long[] record = blockEntry.getBlockRecord();
        long startInput = record[0];
        long endInput   = record[1];
        OpenDistributedBlockIndex blockIndex = blockEntry.getBlockIndex();
        String IPFSHash = new String(blockIndex.getRemoteIndex());

        Multihash filepointer = Multihash.fromBase58(IPFSHash);
        try {
            long receivingTimeStamp = System.currentTimeMillis();
            byte[] fileContents = this.ipfs.cat(filepointer);
            if(fileContents != null){
                long readIPFSTimeStamp = System.currentTimeMillis() - receivingTimeStamp;
                System.out.println(IPFSHash + " " + startInput + " " + endInput + " " + receivingTimeStamp + " " + readIPFSTimeStamp);
            }else{
                accessIPFS(blockEntry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(IPFSHash);


    }
}
