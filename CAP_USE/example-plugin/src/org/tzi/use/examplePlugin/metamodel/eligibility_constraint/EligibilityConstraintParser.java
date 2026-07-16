package org.tzi.use.examplePlugin.metamodel.eligibility_constraint;

import java.util.Map;

public interface EligibilityConstraintParser<T extends EligibilityConstraintInterface> {
  T parse(Map<String, Object> astJson);
}
