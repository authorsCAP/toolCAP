package org.tzi.use.examplePlugin.metamodel.size_constraint;

public interface SizeConstraintGenerator<T extends SizeConstraintInterface> {
    String generate(
        String contextClass,
        String invariantName,
        T ec
    );
}
