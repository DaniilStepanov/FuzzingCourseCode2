package org.itmo.fuzzing.lect4;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.ModifierVisitor;

public abstract class Mutator extends ModifierVisitor<Void> {
    public int mutationLocation = -1;
    int count = 0;

    public Mutator(int mutationLocation) {
        this.mutationLocation = mutationLocation;
    }

    public Node mutableVisit(Node node) {
        count++;
        if (count == mutationLocation) {
            return mutationVisit(node);
        }
        if (node instanceof CompilationUnit) {
            return (Node) super.visit((CompilationUnit) node, null);
        }
        return node;
    }

    public abstract Node mutationVisit(Node node);

    public void reset() {
        mutationLocation = -1;
        count = -1;
    }
}
