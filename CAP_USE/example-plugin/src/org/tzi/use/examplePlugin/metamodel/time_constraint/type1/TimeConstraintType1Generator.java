package org.tzi.use.examplePlugin.metamodel.time_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.time_constraint.TimeConstraintGenerator;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;

public class TimeConstraintType1Generator implements TimeConstraintGenerator<TimeConstraintType1> {
  @Override
  public String generate(String contextClass, String invariantName, TimeConstraintType1 sc1) {
    System.out.println("Generating ScheduleConstraintType1...");

    // if part
    String ifCond = buildIfCondition(sc1.ifParts, null);
    String impliesPart = (ifCond == null) ? "" : ifCond + " implies ";
    System.out.println("Implies Part: " + impliesPart);
    boolean hasIfPart = impliesPart != null && !impliesPart.isBlank();

    String checkForExi = buildAllowedCondition(sc1.checkForExi, RootScope.ALL, null);
    System.out.println("CheckForExi Condition: " + checkForExi);


    String body =
        hasIfPart
            ? """
            %s(
                %s
            )
            """.formatted(impliesPart, checkForExi)
            : checkForExi;

    return """
    context %s
    inv %s:
      %s
    """.formatted(
        contextClass,
        invariantName,
        body);
  }
}
