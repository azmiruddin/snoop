package tub.ods.rdf4led.distributed.rdf;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.insight.rdf4led.rdf.dictionary.NodeIdLexTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static tub.ods.rdf4led.distributed.rdf.Dictionary.objectMapper;


/**
 * Created by Anh Le-Tuan
 * Email: anh.letuan@tu-berlin.de
 * <p>
 * Date: 08.05.19
 */
class LTable implements NodeIdLexTable<String, Integer> {

    List<String> stringList;



    LTable() {
        stringList = new ArrayList<>();
    }



    public LTable(byte[] stringList) {
        this.stringList = deserialise(stringList);
    }



    @Override
    public String get(Integer integer) {
        return stringList.get(integer);
    }



    @Override
    public Integer put(String s) {
        if (stringList.contains(s)) {
            return stringList.indexOf(s);
        } else {
            stringList.add(s);
            return stringList.size() - 1;
        }
    }



    public byte[] serialise() {
        try {
            String se = objectMapper.writeValueAsString(stringList);
            return se.getBytes();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    public List<String> deserialise(byte[] stringList) {
        String de = new String(stringList);

        try {
            ArrayList<String> arrayList = objectMapper.readValue(de, ArrayList.class);
            return arrayList;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }



    void reset() {
        stringList.clear();
    }



    @Override
    public String toString() {
        return new String(serialise());
    }

}