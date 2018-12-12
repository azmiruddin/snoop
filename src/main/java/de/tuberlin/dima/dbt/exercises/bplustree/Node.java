package de.tuberlin.dima.dbt.exercises.bplustree;

import java.util.Arrays;

public abstract class Node {

	protected Integer[] keys;
    private int indexInParent;
    private Node rightSibling;
    private Node leftSibling;
    private InnerNode parent;

    public Node(Integer[] keys, int capacity) {
        assert keys.length <= capacity;
        this.keys = Arrays.copyOf(keys, capacity);
    }

    public Integer[] getKeys() {
        return keys;
    }

    public void setKeys(Integer[] keys) {
        this.keys = Arrays.copyOf(keys, this.keys.length);
    }

    public abstract Object[] getPayload();

    public abstract void setPayload(Object[] payload);

    public Node getRightSibling() {
        if (rightSibling != null)
            return rightSibling;
        InnerNode parent = this.parent;
        if (parent == null || keys[0] >= parent.getHighestKey()) // Parent is root or I am most right sibling
            return null;
        else {
            Integer myHighestKey = getHighestKey();
            for (int i = 0; i < parent.getKeys().length; i++) {
                if (myHighestKey < parent.getKeys()[i]) {
                    indexInParent = i;
                    rightSibling = parent.getChildren()[i + 1];
                    return rightSibling;
                }
            }
            return null;
        }
    }

    public void setRightSibling(Node rightSibling) {
        this.rightSibling = rightSibling;
    }

    public Node getLeftSibling() {
        if (leftSibling != null)
            return leftSibling;
        InnerNode parent = this.parent;

        if (parent == null || parent.getKeys()[0] > getHighestKey()) // Parent is root or I am most left sibling
            return null;
        else {
            Integer mySmallestKey = keys[0];
            for (int i = 0; i < parent.getKeys().length; i++) {
                if (parent.getKeys()[i] == null) {
                    indexInParent = i - 1;
                    leftSibling = parent.getChildren()[i - 1];
                    return leftSibling;
                }
                if (parent.getKeys()[i] == null || mySmallestKey < parent.getKeys()[i]) {
                    indexInParent = i;
                    leftSibling = parent.getChildren()[i - 1];
                    return leftSibling;
                }
            }
            return null;
        }
    }

    public void setLeftSibling(Node leftSibling) {
        this.leftSibling = leftSibling;
    }

    public int getUsedSlots() {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] == null)
                return i;
        }

        return 0;
    }

    public Integer getHighestKey() {
        Integer topKey = null;
        for (Integer key : keys) {
            if (key == null)
                break;
            topKey = key;
        }
        return topKey;
    }

    public boolean isStealable() {
        return getUsedSlots() > keys.length / 2;
    }

    public void setIndexInParent(int indexInParent) {
        this.indexInParent = indexInParent;
    }

    public int getIndexInParent() {
        return indexInParent;
    }

    public boolean acceptsMerge() {
        return getUsedSlots() == keys.length / 2;
    }

    public boolean hasToStealOrMerge() {
        return getUsedSlots() < keys.length / 2;
    }

    public void setParent(InnerNode parent) {
        this.parent = parent;
    }

    public InnerNode getParent() {
        return parent;
    }

}
