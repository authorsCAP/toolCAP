package org.tzi.use.examplePlugin.metamodel.schedule_constraint.type3;

import org.tzi.use.examplePlugin.metamodel.schedule_constraint.ScheduleConstraintGenerator;

public class ScheduleConstraintType3Generator implements ScheduleConstraintGenerator<ScheduleConstraintType3> {
  @Override
  public String generate(String contextClass, String invariantName, ScheduleConstraintType3 sc3) {
    System.out.println("Generating ScheduleConstraintType3...");

    return """
        context %s
        inv %s:
          self.%s->select(e |
            e.%s.isDefined() and
            e.%s >= self.%s and
            e.%s <= self.%s + %s
          )->size() %s %s
        """.formatted(
        contextClass,
        invariantName,
        sc3.rolePath,
        sc3.window.timeAttr,
        sc3.window.timeAttr,
        sc3.window.baseTime,
        sc3.window.timeAttr,
        sc3.window.baseTime,
        sc3.window.duration,
        sc3.limitAttribute.operator.symbol,
        sc3.limitAttribute.value
    );
  }
}
