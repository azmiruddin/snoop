package de.tuberlin.dima.dbt.exercises.bplustree;

/**
 * This class contains some utility methods to simplify the writing of tests.
 *
 * The methods keys(), values(), and nodes() can be used to construct
 * arrays that can be passed to the LeafNode and InnerNode constructors.
 *
 * The methods newTree, newLeaf, newNode make it easy to construct instances
 * of the corresponding type. The capacity is given by the CAPACITY field. The
 * default capacity is 4.
 */
public class BPlusTreeUtilities {

    /**
     * Constructor for an integer array that can be used for node keys.
     */
    public static Integer[] keys(Integer... keys) {
        return keys;
    }

    /**
     * Constructor for a String array that can be used as leaf values.
     */
    public static String[] values(String... values) {
        return values;
    }

    /**
     * Constructor for a array of Node objects that can be used as children of
     * an inner node.
     */
    public static Node[] nodes(Node... nodes) {
        return nodes;
    }

    /**
     * Capacity of the tree and nodes constructed by newTree, newLeaf,
     * and newNode.
     *
     * The default capacity is 4.
     */
    public static int CAPACITY = 4;

    /**
     * Helper method to construct an empty BPlusTree instance.
     *
     * The tree has a capacity of {CAPACITY}, i.e., each node stores
     * {CAPACITY} keys.
     */
    public static BPlusTree newEmptyTree() {
        return newTree(newLeaf(keys(), values()));
    }

    /**
     * Helper method to construct an empty BPlusTree instance with given
     * capacity.
     *
     * The tree has a capacity of capacity, i.e., each node stores
     * capacity keys.
     */
    public static BPlusTree newEmptyTree(int capacity) {
        return newTree(newLeaf(keys(), values(), capacity), capacity);
    }

    /**
     * Helper method to construct a BPlusTree instance.
     *
     * The tree has a capacity of {CAPACITY}, i.e., each node stores
     * {CAPACITY} keys.
     */
    public static BPlusTree newTree(Node root) {
        return newTree(root, CAPACITY);
    }

    /**
     * Helper method to construct a BPlusTree instance with given capacity.
     *
     * The tree has a capacity of {CAPACITY}, i.e., each node stores
     * {CAPACITY} keys.
     */
    public static BPlusTree newTree(Node root, int capacity) {
        return new BPlusTree(root, capacity);
    }

    /**
     * Helper method to construct a leaf.
     *
     * The node stores {CAPACITY} keys and values.
     */
    public static LeafNode newLeaf(Integer[] keys, String[] values) {
        return newLeaf(keys, values, CAPACITY);
    }

    /**
     * Helper method to construct a leaf with given capacity.
     *
     * The node stores capacity keys and values.
     */
    public static LeafNode newLeaf(Integer[] keys, String[] values,
                                   int capacity) {
        return new LeafNode(keys, values, capacity);
    }

    /**
     * Helper method to construct an inner node.
     *
     * The node stores {CAPACITY} keys and {CAPACITY} + 1 values.
     */
    public static InnerNode newNode(Integer[] keys, Node[] nodes) {
        return newNode(keys, nodes, CAPACITY);
    }

    /**
     * Helper method to construct an inner node with given capacity.
     *
     * The node stores capacity keys and capacity + 1 values.
     */
    public static InnerNode newNode(Integer[] keys, Node[] nodes,
                                    int capacity) {
        return new InnerNode(keys, nodes, capacity);
    }

}
