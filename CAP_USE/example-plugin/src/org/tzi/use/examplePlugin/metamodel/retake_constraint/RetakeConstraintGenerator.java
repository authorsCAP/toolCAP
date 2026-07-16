package org.tzi.use.examplePlugin.metamodel.retake_constraint;

public interface RetakeConstraintGenerator<T extends RetakeConstraintInterface> {
    String generate(
        String contextClass,
        String invariantName,
        T ec
    );
}
