package org.tzi.use.examplePlugin.metamodel.time_constraint;

public interface TimeConstraintGenerator<T extends TimeConstraintInterface> {
    String generate(
        String contextClass,
        String invariantName,
        T ec
    );
}
