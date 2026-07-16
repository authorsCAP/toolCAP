package org.tzi.use.examplePlugin.metamodel.eligibility_constraint;

public interface EligibilityConstraintGenerator<T extends EligibilityConstraintInterface> {
    String generate(
        String contextClass,
        String invariantName,
        T ec
    );
}
