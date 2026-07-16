package org.tzi.use.examplePlugin.metamodel.sum_constraint;

public interface SumConstraintGenerator<T extends SumConstraintInterface> {
    String generate(
        String contextClass,
        String invariantName,
        T sc
    );
}
