package tub.ods.rdf4led.distributed.benchmark;

import dev.insight.rdf4led.common.util.ArrayUtil;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import tub.ods.rdf4led.distributed.connector.BlockEntryValidationService;
import tub.ods.rdf4led.distributed.storage.block.IPFSBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.IPFSBlockIndex;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 10.04.19
 */
public class EthTest {

    public static void main(String[] args) {
        System.out.println(args[0] + " "+ args[1] +" " +  args[2]);
        String contractAddress = "0xCCd947950B3361ef2F21243dB5E8987C7881b6fA";
        String fundersAddress = args[2];

        //Darshan's Laptop
//        String contractAddress = "0xAC982A31531Bf507D863bb54979c73ae3cd31fb8";
//        String fundersAddress = "0xB28c1C55bC91fBa0E0878ed47C6331C9763AAFe8";

        int startIndex = Integer.parseInt(args[0]);
        int endIndex = startIndex + 2500;
//        String ethereum = "http://192.168.1.250:8545";
        String ethereum = args[1];

        Web3j web3j = Web3j.build(new HttpService(ethereum));

        BlockEntryValidationService blockEntryValidationService = new BlockEntryValidationService(web3j, contractAddress, fundersAddress);

        for (long index = startIndex; index<endIndex; index ++){
            long[] triple = new long[]{index % 1000, index % 100, index % 10};
            byte[] triple_byte = ArrayUtil.longs2bytes(triple);
            byte[] ipfsHash = new byte[46];
            System.arraycopy(triple_byte, 0, ipfsHash, 0, 24);
            String ipfsString = new String(ipfsHash);

            IPFSBlockIndex blockIndex = new IPFSBlockIndex();
            blockIndex.setIPFSHash(ipfsString);

            IPFSBlockEntry ipfsBlockEntry = new IPFSBlockEntry();
            ipfsBlockEntry.setBlockRecord(triple);
            ipfsBlockEntry.setBlockIndex(blockIndex);

            long start = System.currentTimeMillis();

            blockEntryValidationService.encrypt(ipfsBlockEntry);

            long elapsed = System.currentTimeMillis() - start;

            System.out.println(index + "  " + elapsed);
        }

    }

}
