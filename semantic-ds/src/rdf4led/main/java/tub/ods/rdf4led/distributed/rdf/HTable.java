package tub.ods.rdf4led.distributed.rdf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.insight.rdf4led.rdf.dictionary.HashNodeIdTable;

import java.io.IOException;
import java.util.HashMap;

import static tub.ods.rdf4led.distributed.rdf.Dictionary.objectMapper;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 08.05.19
 */
class HTable implements HashNodeIdTable<Integer> {


    HashMap<Integer, Integer> hashMap;



    HTable() {
        hashMap = new HashMap<>();
    }



    private HTable(byte[] hashTable) {
        this.hashMap = deserialise(hashTable);
    }



    @Override
    public boolean put(Integer hash, Integer node) {
        hashMap.put(hash, node);
        return true;
    }



    @Override
    public Integer get(Integer hash) {
        return hashMap.get(hash);
    }



    @Override
    public void sync() {

    }



    public byte[] serialise() {
        try {
            String se = objectMapper.writeValueAsString(hashMap);
            return se.getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    public HashMap<Integer, Integer> deserialise(byte[] hashMap) {
        String de = new String(hashMap);

        try {
            HashMap<Integer, Integer> arrayList = objectMapper.readValue(de, HashMap.class);
            return arrayList;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    @Override
    public String toString() {
        return hashMap.size() + " " + hashMap.toString();
    }



    void reset() {
        hashMap.clear();
    }

}