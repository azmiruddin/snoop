package de.tuberlin.dima.dbt.exercises.bplustree;

import java.util.Arrays;

public abstract class Node {

    protected Integer[] keys;

    public Node(Integer[] keys, int capacity) {
        assert keys.length <= capacity;
        this.keys = Arrays.copyOf(keys, capacity);
    }

    public Integer[] getKeys() {
        return keys;
    }

    public void setKeys(Integer[] keys) {
        this.keys = Arrays.copyOf(keys, this.keys.length);;
    }

    public abstract Object[] getPayload();

    public abstract void setPayload(Object[] payload);

}
