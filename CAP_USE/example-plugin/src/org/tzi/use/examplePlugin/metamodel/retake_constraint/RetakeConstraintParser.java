package org.tzi.use.examplePlugin.metamodel.retake_constraint;

import java.util.Map;

public interface RetakeConstraintParser<T extends RetakeConstraintInterface> {
  T parse(Map<String, Object> astJson);
}
