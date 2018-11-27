package de.tuberlin.dima.dbt.exercises.bplustree;

import java.util.Deque;
import java.util.LinkedList;



/**
 * Implementation of a B+ tree.
 *
 * The capacity of the tree is given by the capacity argument to the
 * constructor. Each node has at least {capacity/2} and at most {capacity} many
 * keys. The values are strings and are stored at the leaves of the tree.
 *
 * For each inner node, the following conditions hold:
 *
 * {pre}
 * Integer[] keys = innerNode.getKeys();
 * Node[] children = innerNode.getChildren();
 * {pre}
 *
 * - All keys in {children[i].getKeys()} are smaller than {keys[i]}.
 * - All keys in {children[j].getKeys()} are greater or equal than {keys[i]}
 *   if j > i.
 */
public class BPlusTree {
	
	protected int degree;

    ///// Implement these methods

    private LeafNode findLeafNode(Integer key, Node node,
                                  Deque<InnerNode> parents) {
        if (node instanceof LeafNode) {
            return (LeafNode) node;
        } else {
            InnerNode innerNode = (InnerNode) node;
            if (parents != null) {
                parents.push(innerNode);
            }
            // TODO: traverse inner nodes to find leaf node
            node = (Node) parents;
            return null;
        }
    }

    private String lookupInLeafNode(Integer key, LeafNode node) {
        // TODO: lookup value in leaf node
    	String value = null;
        for (int i = 0; i < node.getValues().length; i++) {
            if (node.keys[i] != null) {
                if (key == node.keys[i]) {
                    value = node.getValues()[i];
                }
            }
        }
        return value;    	
    }

    private void insertIntoLeafNode(Integer key, String value,
                                    LeafNode node, Deque<InnerNode> parents) {
        // TODO: insert value into leaf node (and propagate changes up)
    	LeafNode leafNode = findLeafNode(key, node);
        if (node instanceof LeafNode) {
            for (int i = 0; i < leafNode.getKeys().length; i++) {
                String[] leafNodeValues = leafNode.getValues();
                Integer[] leafNodeKeys = leafNode.getKeys();
                if (leafNode.getKeys()[i] == null) { //to make sure there is space
                    if (key > leafNode.getKeys()[i-1]){
                        leafNodeValues[i] = value;
                        leafNodeKeys[i] = key;
                        break;
                    } else {
                        String tempVal;
                        Integer tempKey;
                        tempVal = leafNodeValues[i-1];
                        tempKey = leafNodeKeys[i-1];
                        leafNodeValues[i] = tempVal;
                        leafNodeKeys[i] = tempKey;
                        leafNodeValues[i-1] = value;
                        leafNodeKeys[i-1] = key;
                        break;
                    }
                }
            }
        }
    }

	private String deleteFromLeafNode(Integer key, LeafNode node,
			Deque<InnerNode> parents) {
		// TODO: delete value from leaf node (and propagate changes up)
		
		return null;
	}
    ///// Public API
    ///// These can be left unchanged

    /**
     * Lookup the value stored under the given key.
     * @return The stored value, or {null} if the key does not exist.
     */
    public String lookup(Integer key) {
        LeafNode leafNode = findLeafNode(key, root);
        return lookupInLeafNode(key, leafNode);
    }

    /**
     * Insert the key/value pair into the B+ tree.
     */
    public void insert(int key, String value) {
        Deque<InnerNode> parents = new LinkedList<>();
        LeafNode leafNode = findLeafNode(key, root, parents);
        insertIntoLeafNode(key, value, leafNode, parents);
    }

    /**
     * Delete the key/value pair from the B+ tree.
     * @return The original value, or {null} if the key does not exist.
     */
    public String delete(Integer key) {
        Deque<InnerNode> parents = new LinkedList<>();
        LeafNode leafNode = findLeafNode(key, root, parents);
        return deleteFromLeafNode(key, leafNode, parents);
    }

    ///// Leave these methods unchanged

    private int capacity = 0;

    private Node root;

    public BPlusTree(int capacity) {
        this(new LeafNode(capacity), capacity);
    }

    public BPlusTree(Node root, int capacity) {
        assert capacity % 2 == 0;
        this.capacity = capacity;
        this.root = root;
    }

    public Node rootNode() {
        return root;
    }

    public String toString() {
        return new BPlusTreePrinter(this).toString();
    }

    private LeafNode findLeafNode(Integer key, Node node) {
        return findLeafNode(key, node, null);
    }

}
