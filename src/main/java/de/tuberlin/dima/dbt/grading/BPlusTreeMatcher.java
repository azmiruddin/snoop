package de.tuberlin.dima.dbt.grading;

import de.tuberlin.dima.dbt.exercises.bplustree.*;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;

public class BPlusTreeMatcher extends TypeSafeMatcher<BPlusTree> {

    private BPlusTree expectedTree;

    public BPlusTreeMatcher(BPlusTree tree) {
        this.expectedTree = tree;
    }

    @Override
    protected boolean matchesSafely(BPlusTree tree) {
        if (expectedTree == null || tree == null) {
            return false;
        }
        return isEqualNode(expectedTree.rootNode(), tree.rootNode());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(new BPlusTreePrinter(expectedTree).toString());
    }

    private boolean isEqualNode(Node expected, Node actual) {
        if (expected instanceof LeafNode && actual instanceof LeafNode) {
            return isEqualNode((LeafNode) expected, (LeafNode) actual);
        } else if (expected instanceof InnerNode && actual instanceof InnerNode) {
            return isEqualNode((InnerNode) expected, (InnerNode) actual);
        } else {
            return expected == null && actual == null;
        }
    }

    private boolean isEqualNode(LeafNode expected, LeafNode actual) {
        return Arrays.equals(expected.getKeys(), actual.getKeys()) &&
                Arrays.equals(expected.getValues(), actual.getValues());
    }

    private boolean isEqualNode(InnerNode expected, InnerNode actual) {
        if ( ! Arrays.equals(expected.getKeys(), actual.getKeys()) ) {
            return false;
        }
        Node[] expectedChildren = expected.getChildren();
        Node[] actualChildren = actual.getChildren();
        for (int i = 0; i < expectedChildren.length; ++i) {
            if ( ! isEqualNode(expectedChildren[i], actualChildren[i]) ) {
                return false;
            }
        }
        return true;
    }

    public static BPlusTreeMatcher isTree(BPlusTree tree) {
        return new BPlusTreeMatcher(tree);
    }

}
