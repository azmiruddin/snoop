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
    
	public void updateKeys() {
		for (int i = 0; i < children.length; i++) {
			if (i == 0) {
				if (children[i] != null) {
					if (keys[i] != null && children[i].getKeys()[0] >= keys[i]) {
						if (children[i + 1] != null) {
							keys[i] = children[i + 1].getKeys()[0];
						}
					} else
						keys[i] = children[i].getKeys()[0];

				} else {
					keys[i] = null;
				}
			} else if (children[i] != null) {
				keys[i - 1] = children[i].getKeys()[0];
			} else {
				keys[i - 1] = null;
			}
		}
	}
    
    public void updateChildren() {
        for (int i = 1; i < children.length; i++) {
            if (children[i - 1] == null) {
                children[i - 1] = children[i];
                children[i] = null;
            }
        }
    }

}
