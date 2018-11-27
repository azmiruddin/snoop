package de.tuberlin.dima.dbt.exercises.bplustree;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class BPlusTreePrinter {

    private Node root;

    public BPlusTreePrinter(BPlusTree tree) {
        root = tree.rootNode();
    }

    BPlusTreePrinter(LeafNode node) {
        root = node;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        toString(sb, root, 0);
        return sb.toString().trim();
    }

    private void toString(StringBuffer sb, Node node, int indent) {
        indent(sb, indent);
        if (node instanceof LeafNode) {
            toString(sb, (LeafNode) node);
        } else {
            toString(sb, (InnerNode) node, indent);
        }
    }

    private String join(Object[] array) {
        return Arrays.stream(array)
                     .map(x -> x == null ? "" : String.valueOf(x))
                     .collect(Collectors.joining(","));
    }

    private void toString(StringBuffer sb, InnerNode node, int indent) {
        String keyList = join(node.getKeys());
        sb.append("[");
        sb.append(keyList);
        sb.append("] =>\n");
        for (Node child : node.getChildren()) {
            if (child != null) {
                toString(sb, child, indent + 2);
            }
        }
    }

    private void toString(StringBuffer sb, LeafNode node) {
        String keyList = join(node.getKeys());
        String valueList = join(node.getValues());
        sb.append("[");
        sb.append(keyList);
        sb.append("] => [");
        sb.append(valueList);
        sb.append("]\n");
    }

    private void indent(StringBuffer sb, int indent) {
        sb.append(String.join("", Collections.nCopies(indent, " ")));
    }

}
