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

    ///// Implement these methods

    private LeafNode findLeafNode(Integer key, Node node,
                                  Deque<InnerNode> parents) {
        Node foundNode = node;
        if (node instanceof LeafNode) {
            return (LeafNode) node;
        } else {
            InnerNode innerNode = (InnerNode) node;
            if (parents != null) {
                parents.push(innerNode);
            }
            // TODO: traverse inner nodes to find leaf node
            Integer[] keys = innerNode.getKeys();
            Node[] children = innerNode.getChildren();
            for (int i = 0; i < keys.length; i++) {
                if (keys[i] != null) {
                    if (key < keys[i]){
                        if (children[i].keys[i] != null) {
                            System.out.println("key " + key + " is LESS than " + keys[i]);
                            //parents.push((InnerNode) children[i]);
                            findLeafNode(key, children[i], parents);
                            foundNode = children[i];
                        }
                    } else {
                        findLeafNode(key, children[i+1], parents);
                        foundNode = children[i+1];
                    }
                }
            }
        }
        return (LeafNode) foundNode;
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

    @SuppressWarnings("unused")
	private void insertIntoLeafNode(Integer key, String value,
                                    LeafNode node, Deque<InnerNode> parents) {
        // TODO: insert value into leaf node (and propagate changes up)
        LeafNode leafNode = findLeafNode(key, node);
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
            } else { //there is no space, then split and propagate
                Integer[] newKeysArray = new Integer[capacity+1];
                String[] newValuesArray = new String[capacity+1];
                Integer[] newKeysForNewNode = new Integer[capacity];
                String[] newValuesForNewNode = new String[capacity];
                LeafNode newLeafNode = new LeafNode(capacity);
                int median;
                //create a new array for all keys included the new key and sort them
                for (int x = 0; x < newKeysArray.length - 1; x++) {
                    newKeysArray[x] = leafNodeKeys[x];
                    newValuesArray[x] = leafNodeValues[x];
                    if (key > newKeysArray[x]) {
                        newKeysArray[x+1] = key;
                        newValuesArray[x+1] = value;
                    } else {
                        String tempVal;
                        Integer tempKey;
                        tempVal = leafNodeValues[x-1];
                        tempKey = newKeysArray[x-1];
                        newKeysArray[x] = tempKey;
                        newValuesArray[x] = tempVal;
                        newKeysArray[x-1] = key;
                        newValuesArray [x-1] = value;
                    }
                }
                //calculate the median of the newKeysArray
                if (newKeysArray.length % 2 == 0){
                    median = (newKeysArray[newKeysArray.length/2] + newKeysArray[newKeysArray.length/2 - 1])/2;
                } else {
                    median = (newKeysArray[newKeysArray.length/2]);
                }
                //split the new array to two arrays and push the half keys to both, then update the original leafNode and the new one
                for (int y = 0,count=capacity/2; y < newKeysForNewNode.length; y++,count++) {
                    if (count <= newKeysForNewNode.length) {
                        if (newKeysArray[count] != null) {
                            newKeysForNewNode[y] = newKeysArray[count];
                            newValuesForNewNode[y] = newValuesArray[count];
                            newKeysArray[count] = null;
                            newValuesArray[count] = "";
                            leafNode.setKeys(newKeysArray);
                            leafNode.setValues(newValuesArray);
                            newLeafNode.setKeys(newKeysForNewNode);
                            newLeafNode.setValues(newValuesForNewNode);
                        }
                    }
                }
                for (int j = 0; j < parents.element().keys.length; j++) {
                    if (parents.element().keys[j] == null) {
                        Integer[] parentKeys = parents.element().getKeys();
                        for (int h = 0; h < parentKeys.length; h++) {
                            if (median > parentKeys[h]) {
                                parentKeys[h+1] = median;
                                break;
                            } else {
                                Integer tempKey;
                                tempKey = parentKeys[h-1];
                                parentKeys[h] = tempKey;
                                parentKeys[h-1] = median;
                                break;
                            }
                        }
                        parents.element().setKeys(parentKeys);
                        Node [] newLeafNodes = parents.element().getChildren();
                        for (int m = 0; m < newLeafNodes.length; m++) {
                            if (newLeafNodes[m] == null) {
                                newLeafNodes[m] = newLeafNode;
                                break;
                            }
                        }
                        parents.element().setChildren(newLeafNodes);
                        break;
                    }
                }
            }
            break;
        }
    }

    private String deleteFromLeafNode(Integer key, LeafNode node,
                                      Deque<InnerNode> parents) {
        // TODO: delete value from leaf node (and propagate changes up)
        LeafNode leafNode = findLeafNode(key, node);
        String value = null;
        for (int i = 0; i < leafNode.getKeys().length; i++) {
            if (leafNode.getKeys()[i] != null) {
                if (key == leafNode.getKeys()[i]) {
                    value = leafNode.getValues()[i];
                    leafNode.getValues()[i] = null;
                    if (leafNode.getValues()[i+1] != null) {
                        leafNode.getValues()[i] = leafNode.getValues()[i+1];
                        leafNode.getValues()[i+1] = null;
                    }
                    leafNode.getKeys()[i] = null;
                    if (leafNode.getKeys()[i+1] != null) {
                        leafNode.getKeys()[i] = leafNode.getKeys()[i+1];
                        leafNode.getKeys()[i+1] = null;
                    }
                }
            }
        }
        return value;
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
