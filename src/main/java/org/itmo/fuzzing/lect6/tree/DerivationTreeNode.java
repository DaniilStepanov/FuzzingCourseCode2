package org.itmo.fuzzing.lect6.tree;

import java.util.List;
import java.util.Optional;

// Define the DerivationTreeNode class for children nodes
public class DerivationTreeNode {
    private final String value;
    private final List<DerivationTreeNode> children;

    public DerivationTreeNode(String value, List<DerivationTreeNode> children) {
        this.value = value;
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public List<DerivationTreeNode> getChildren() {
        return children;
    }

    public String allTerminals() {
        if (this.getChildren() == null || this.getChildren().isEmpty()) {
            return this.getValue();
        }

        StringBuilder result = new StringBuilder();
        for (var child : this.getChildren()) {
            result.append(child.allTerminals());
        }

        return result.toString();
    }

    @Override
    public String toString() {
        return "DerivationTreeNode{" +
                "value='" + value + '\'' +
                ", children=" + children +
                '}';
    }
}
