package tub.ods.rdf4led.distributed.rdf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.insight.rdf4led.rdf.dictionary.HashNodeIdTable;
import dev.insight.rdf4led.rdf.dictionary.NodeIdLexTable;
import dev.insight.rdf4led.rdf.dictionary.RDFDictionaryAbstract;
import dev.insight.rdf4led.rdf.dictionary.codec.EncoderInt;
import dev.insight.rdf4led.rdf.dictionary.codec.HashNodeImpl;
import dev.insight.rdf4led.rdf.dictionary.codec.RDFNodeType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 23.04.19
 */
public class Dictionary extends RDFDictionaryAbstract<Integer> {

    static ObjectMapper objectMapper = new ObjectMapper();

    private byte[] ltb;



    public Dictionary() {
        super(HashNodeImpl.MD5_Hash_Int, new HTable(), new LTable(), EncoderInt.encodeInt);
    }



    public Dictionary(byte[] ltb) {
        super(HashNodeImpl.MD5_Hash_Int, null, new LTable(ltb), EncoderInt.encodeInt);
    }



    public Integer getHash(String lex, byte lang, byte nodeType) {
        return Math.abs(this.hashNode.getHash(lex, lang, nodeType));
    }



    @Override
    protected Integer doEncode(Byte nodeType, Byte prefix, Integer suffix) {
        return encoder.encode(nodeType, prefix, suffix);
    }



    @Override
    protected Integer from_byte_to_TNode(byte suffix) {
        return Integer.valueOf(suffix);
    }



    @Override
    protected byte from_TNode_to_byte(Integer suffix) {
        return (byte) (suffix.byteValue());
    }



    @Override
    public Integer getNodeAny() {
        return 0;
    }



    public void reset() {
        ((LTable) this.lexTable).reset();
        ((HTable) this.hashTable).reset();

    }



    public byte[] getLtb() {
        this.ltb = ((LTable) this.lexTable).serialise();
        return ltb;
    }


}
