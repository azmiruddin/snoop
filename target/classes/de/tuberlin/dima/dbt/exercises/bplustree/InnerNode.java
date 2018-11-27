package de.tuberlin.dima.dbt.exercises.bplustree;

import java.util.Arrays;
import java.util.stream.Collectors;

public class InnerNode extends Node {

    private Node[] children;

    public InnerNode(int capacity) {
        this(new Integer[] {}, new Node[] {null}, capacity);
    }

    public InnerNode(Integer[] keys, Node[] children, int capacity) {
        super(keys, capacity);
        assert keys.length == children.length - 1;
        this.children = Arrays.copyOf(children, capacity + 1);
    }

    public Node[] getChildren() {
        return children;
    }

    public void setChildren(Node[] children) {
        this.children = Arrays.copyOf(children, this.children.length);
    }

    @Override
    public Object[] getPayload() {
        return getChildren();
    }

    @Override
    public void setPayload(Object[] payload) {
        setChildren((Node[]) payload);
    }

    public String toString() {
        String keyList = Arrays.stream(keys).map(String::valueOf)
                               .collect(Collectors.joining(", "));
        String childrenList = Arrays.stream(children).map(String::valueOf)
                                    .collect(Collectors.joining(", "));
        return "keys: [" + keyList + "]; " + "children: [" + childrenList + "]";
    }

}
