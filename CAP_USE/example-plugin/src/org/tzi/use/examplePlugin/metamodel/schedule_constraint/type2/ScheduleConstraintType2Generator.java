package org.tzi.use.examplePlugin.metamodel.schedule_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.schedule_constraint.ScheduleConstraintGenerator;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.AND;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedOrCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildExistsCheckConditionEach;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.indent;

public class ScheduleConstraintType2Generator implements ScheduleConstraintGenerator<ScheduleConstraintType2> {
  @Override
  public String generate(String contextClass, String invariantName, ScheduleConstraintType2 sc2) {
    String ifCond = buildIfCondition(sc2.ifParts, "iter");
    String checkForExi = buildExistsCheckConditionEach(sc2.rolePath, sc2.checkForExi, AND);
    String altPart = buildAllowedOrCondition(sc2.altParts, RootScope.ALL, null);
    String iter = "iter";

    String body;

    if (ifCond != null) {
      body = """
        (%s) implies (
          (
            %s
          )
          or
          (
            %s
          )
        )
        """.formatted(
          ifCond,
          checkForExi,
          altPart
      );
    } else {
      body = """
        (
          %s
        )
        or
        (
          %s
        )
        """.formatted(
          checkForExi,
          altPart
      );
    }

    return """
      context %s
      inv %s:
        self.%s->forAll(%s |
      %s
        )
      """.formatted(
        contextClass,
        invariantName,
        sc2.targetAssoc,
        iter,
        indent(body, 4)
    );
  }
}
