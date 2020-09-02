package tub.ods.rdf4led.distributed.benchmark;

import dev.insight.rdf4led.common.util.ArrayUtil;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import tub.ods.rdf4led.distributed.connector.BlockEntryValidationService;
import tub.ods.rdf4led.distributed.storage.block.DistributedBlockEntry;
import tub.ods.rdf4led.distributed.storage.block.ETHBlockIndex;
import tub.ods.rdf4led.distributed.storage.block.PrivateDistributedBlockIndex;

import static dev.insight.rdf4led.common.util.ArrayUtil.bytes2longs;


public class EthValidationTest {
    public static void main(String[] args) {
        System.out.println(args[0] + " "+ args[1] +" " +  args[2]);
        String contractAddress = "0xCCd947950B3361ef2F21243dB5E8987C7881b6fA";
        String fundersAddress = args[2];

        int startIndex = Integer.parseInt(args[0]);
        int endIndex = startIndex + 2500;
//        String ethereum = "http://192.168.1.250:8545";
        String ethereum = args[1];
//        String ethereum = System.getenv("ETHHOST");
//        String contractAddress = System.getenv("CONTRACT");
//        String fundersAddress = System.getenv("FUNDER");

        Web3j web3j = Web3j.build(new HttpService(ethereum));

        BlockEntryValidationService blockEntryValidationService = new BlockEntryValidationService(web3j, contractAddress, fundersAddress);

        int ctr = 0;

        ETHBlockIndex ethBlockIndex = new ETHBlockIndex();
        ethBlockIndex.setClientID(blockEntryValidationService.myAddress);

        DistributedBlockEntry<long[], PrivateDistributedBlockIndex>[] blockEntries = new DistributedBlockEntry[2500];

        for (long index = startIndex; index<endIndex; index ++){
            long[] triple = new long[]{index % 1000, index % 100, index % 10};
            byte[] triple_byte = ArrayUtil.longs2bytes(triple);
            blockEntries[ctr] = new DistributedBlockEntry<>(ethBlockIndex, bytes2longs(triple_byte));
            ctr++;
        }

        blockEntryValidationService.validate(blockEntries);

    }
}
