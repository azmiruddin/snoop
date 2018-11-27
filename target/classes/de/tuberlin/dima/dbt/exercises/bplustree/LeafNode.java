package de.tuberlin.dima.dbt.exercises.bplustree;

import java.util.Arrays;

public class LeafNode extends Node {

    private String[] values;

    public LeafNode(int capacity) {
        this(new Integer[] {}, new String[] {}, capacity);
    }

    public LeafNode(Integer[] keys, String[] values, int capacity) {
        super(keys, capacity);
        assert keys.length == values.length;
        this.values = Arrays.copyOf(values, capacity);
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = Arrays.copyOf(values, this.values.length);
    }

    @Override
    public Object[] getPayload() {
        return getValues();
    }

    @Override
    public void setPayload(Object[] payload) {
        setValues((String[]) payload);
    }

    public String toString() {
        return new BPlusTreePrinter(this).toString();
    }

}
