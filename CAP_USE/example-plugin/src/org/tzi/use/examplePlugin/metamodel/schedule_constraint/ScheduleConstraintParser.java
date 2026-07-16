package org.tzi.use.examplePlugin.metamodel.schedule_constraint;

import java.util.Map;

public interface ScheduleConstraintParser<T extends ScheduleConstraintInterface> {
  T parse(Map<String, Object> astJson);
}
