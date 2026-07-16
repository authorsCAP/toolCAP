package org.tzi.use.examplePlugin.metamodel.sum_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import java.util.Map;

public interface SumConstraintParser <T extends SumConstraintInterface> {
    T parse(Map<String, Object> astJson);
}
