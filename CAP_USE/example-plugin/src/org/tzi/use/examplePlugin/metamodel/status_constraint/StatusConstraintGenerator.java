package org.tzi.use.examplePlugin.metamodel.status_constraint;

public interface StatusConstraintGenerator<T extends StatusConstraintInterface> {
    String generate(
        String contextClass,
        String invariantName,
        T ec
    );
}
