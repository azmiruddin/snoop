package de.tuberlin.dima.dbt.exercises.bplustree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

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

	private LeafNode findLeafNode(Integer key, Node node, Deque<InnerNode> parents) {
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

			if (key < keys[0])
				return findLeafNode(key, children[0], parents);

			for (int i = 0; i < keys.length; i++) {
				if (keys[i] != null) {
					if (key < keys[i]) {
						return findLeafNode(key, children[i], parents);						
					} 
				} else
					return findLeafNode(key, children[i], parents);
			}
			return findLeafNode(key, children[children.length - 1], parents);
		}
	}  
    
    
	private String lookupInLeafNode(Integer key, LeafNode node) {

		if (node == null) {
//			System.out.println("null find leaf");
			return null;
		}
//		System.out.println(node);
		// TODO: lookup value in leaf node
		String value = null;
		for (int i = 0; i < node.getValues().length; i++) {
			if (node.keys[i] != null) {
				if (Objects.equals(key, node.keys[i])) {
					value = node.getValues()[i];
				}
			} else
				break;
		}
		return value;
	}

    
	private void insertIntoLeafNode(Integer key, String value, LeafNode node, Deque<InnerNode> parents) {
		
		constructParentChildRelation(parents);

		if (node != null) {
			Integer tempKey;
			Object tempValue;

			// System.out.println("Going to insert [" + key + ", " + value + "] into node "
			// + node.toString() + " in tree\n" + this.toString());

			for (int i = 0; i < node.getKeys().length; i++) {
				// System.out.println("i = " + i);
				if (node.getKeys()[i] != null) {
					if (key > node.getKeys()[i]) { // look for bigger key if any
						if (keyNotNull(node, i) && key < node.getKeys()[i + 1]) {
							tempKey = node.getKeys()[i + 1];
							tempValue = node.getValues()[i + 1];
							node.getKeys()[i + 1] = key;
							node.getValues()[i + 1] = value;							
							relocateEntries(node, tempKey, tempValue, i + 2);
							break;
						} else if (i + 1 == node.getKeys().length) { 
							
							LeafNode newNode = new LeafNode(node.getKeys().length);
							int newNodeCounter = 0;
							for (int originNodeCounter = newNode.getKeys().length / 2; originNodeCounter < newNode
									.getKeys().length; originNodeCounter++) {
								newNode.getKeys()[newNodeCounter] = node.getKeys()[originNodeCounter];
								newNode.getValues()[newNodeCounter++] = node.getValues()[originNodeCounter];
								node.getKeys()[originNodeCounter] = null;
								node.getValues()[originNodeCounter] = null;
							}
							newNode.getKeys()[newNodeCounter] = key;
							newNode.getValues()[newNodeCounter] = value;
							if (node.getParent() == null) { // I am the root, need to create a new root
								InnerNode newParent = new InnerNode(node.getKeys().length);
								newParent.getChildren()[0] = node;
								newParent.getChildren()[1] = newNode;
								node.setParent(newParent);
								newNode.setParent(newParent);
								newParent.updateChildren();
								newParent.updateKeys();
								root = newParent;
								break;
							}
							// add new node to parent
							InnerNode parent = node.getParent();
							Integer nextNullIndexInParent = findNextNull(parent);

							if (nextNullIndexInParent != null) {
								parent.getKeys()[nextNullIndexInParent] = newNode.getKeys()[0];
								parent.getChildren()[nextNullIndexInParent + 1] = newNode;
							} else { // need to split parent
								InnerNode newInnerNode = new InnerNode(node.getKeys().length);
								newInnerNode.setParent(parent.getParent());

								Integer newRootKey = null;
								// take half of the parent's keys and children into a new node
								for (int j = parent.getKeys().length / 2; j < parent.getKeys().length; j++) {
									if (j - parent.getKeys().length / 2 != 0)
										newInnerNode.getKeys()[j - 1 - parent.getKeys().length / 2] = parent
												.getKeys()[j];
									else
										newRootKey = parent.getKeys()[j];
									newInnerNode.getChildren()[j - parent.getKeys().length / 2] = parent.getChildren()[j + 1];

									parent.getKeys()[j] = null;
									parent.getChildren()[j + 1] = null;
								}

								// TODO place neInnerNode in the right place in the parent's parent and shift
								// others if needed
								int newInnerNodeIndex = parent.getIndexInParent() + 1;
								if (parent.getParent() != null) {
									insertIntoInnerNode(newInnerNode.getKeys()[0], newInnerNode, parent.getParent(),
											parents);
									// TODO might be wrong tempKey and tempValue
									relocateEntriesIndex(parent.getParent(),
											parent.getParent().getKeys()[newInnerNodeIndex + 1],
											parent.getParent().getChildren()[newInnerNodeIndex + 1],
											newInnerNodeIndex + 1);
								} else { // parent is root create a new root
									InnerNode newRoot = new InnerNode(node.getKeys().length);
									newRoot.getKeys()[0] = newRootKey;
									newRoot.getChildren()[0] = parent;
									newRoot.getChildren()[1] = newInnerNode;

									newNode.setParent(newInnerNode);
									newInnerNode.setParent(newRoot);

									root = newRoot;
								}

								parents = getNewParentsList(newNode);
								constructParentChildRelation(parents);

								// update new node into new inner node
								nextNullIndexInParent = searchNullIndex(newInnerNode);
								newInnerNode.getKeys()[nextNullIndexInParent] = newNode.getKeys()[0];
								newInnerNode.getChildren()[nextNullIndexInParent + 1] = newNode;

							}
							break;
						}
					} else { // key < node.getKeys()[i]
						tempKey = node.getKeys()[i];
						tempValue = node.getValues()[i];
						node.getKeys()[i] = key;
						node.getValues()[i] = value;
						// shift following keys and values
						relocateEntriesIndex(node, tempKey, tempValue, i + 1);
						break;
					}
				} else { // node.getKeys()[i] == null
					node.getKeys()[i] = key;
					node.getValues()[i] = value;
					break;
				}
			}
			// System.out.println("Resulting tree:\n" + this.toString());

			if (node.getParent() != null) {
				node.getParent().updateChildren();
				node.getParent().updateKeys();
			}
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
    
    private void insertIntoInnerNode(Integer keyToInsert, Node childToInsert, InnerNode node,
			Deque<InnerNode> parents) {

		constructParentChildRelation(parents);

		if (node != null) {
			Integer tempKey = null;
			Node tempValue = null;

			for (int i = 0; i < node.getKeys().length; i++) {
				if (node.getKeys()[i] != null) {
					if (node.getKeys()[i] > keyToInsert) {
						tempKey = node.getKeys()[i];
						node.getKeys()[i] = keyToInsert;

						if (i == 0) {
							tempValue = node.getChildren()[i];
							node.getChildren()[i] = childToInsert;
						} else {
							tempValue = node.getChildren()[i + 1];
							node.getChildren()[i + 1] = childToInsert;
						}

						relocateEntriesIndex(node, tempKey, tempValue, i + 1);
						break;
					}
				} else if (node.getKeys()[i] == null) {
					node.getKeys()[i] = keyToInsert;
					node.getChildren()[i + 1] = childToInsert;

					relocateEntriesIndex(node, tempKey, tempValue, i + 1);
					break;
				} else if (i == node.getKeys().length - 1) { // split
					throw new RuntimeException("Split area of the code reached.");

				} else {
					throw new RuntimeException("Unexpected area of the code reached.");
				}

			}

			if (node.getParent() != null) {
				node.getParent().updateChildren();
				node.getParent().updateKeys();
			}
		}

	}
    
//    Object Helper 
    
	private void constructParentChildRelation(Deque<InnerNode> parents) {
		for (InnerNode parentNode : parents) {
			for (Node childNode : parentNode.getChildren())
				if (childNode != null)
					childNode.setParent(parentNode);
		}
	}
	
	private boolean keyNotNull(LeafNode node, int i) {
        return i + 1 < node.getKeys().length && node.getKeys()[i + 1] != null;
    }
	
	private void relocateEntries(Node node, Integer tempKey, Object tempValue, int index) {
        Integer key;
        Object value;
        for (int j = index; j < node.getKeys().length; j++) {
            //System.out.println("j = " + j);
            key = node.getKeys()[j];
            value = node.getPayload()[j];
            node.getKeys()[j] = tempKey;
            node.getPayload()[j] = tempValue;
            // prepare for next round
            tempKey = key;
            tempValue = value;
        }
    }
	
	private Integer findNextNull(Node node) {
        if (node != null) {
            for (int i = 0; i < node.getKeys().length; i++) {
                if (node.getKeys()[i] == null)
                    return i;
            }
        }
        return null;
    }
	
	 private void relocateEntriesIndex(Node node, Integer tempKey, Object tempValue, int index) {
	        Integer key;
	        Object value;
	        for (int j = index; j < node.getKeys().length; j++) {
	            //System.out.println("j = " + j);
	            key = node.getKeys()[j];
	            value = node.getPayload()[j];
	            node.getKeys()[j] = tempKey;
	            node.getPayload()[j] = tempValue;
	            // prepare for next round
	            tempKey = key;
	            tempValue = value;
	        }
	    }
	 
	 private Integer searchNullIndex(Node node) {
	        if (node != null) {
	            for (int i = 0; i < node.getKeys().length; i++) {
	                if (node.getKeys()[i] == null)
	                    return i;
	            }
	        }
	        return null;
	    }
	
    
//  End Object Helper   
	 
	private Deque<InnerNode> getNewParentsList(LeafNode newNode) {
		Deque<InnerNode> newParentsList = new LinkedList<>();

		InnerNode node = newNode.getParent();
		while (node != null) {
			newParentsList.add(node);
			node = node.getParent();
		}
		return newParentsList;
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
