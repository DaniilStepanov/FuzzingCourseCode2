package org.itmo.fuzzing.lect4;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.Visitable;

public class ReturnMutator extends Mutator{
    public ReturnMutator(int mutationLocation) {
        super(mutationLocation);
    }

    @Override
    public Node mutationVisit(Node node) {
        return new ReturnStmt(new NullLiteralExpr());
    }

    @Override
    public Visitable visit(ReturnStmt n, Void arg) {
        return mutableVisit(n);
    }
}
