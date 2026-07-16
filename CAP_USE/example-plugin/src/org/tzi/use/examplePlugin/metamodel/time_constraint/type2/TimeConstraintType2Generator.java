package org.tzi.use.examplePlugin.metamodel.time_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.time_constraint.TimeConstraintGenerator;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedConditionWithOperator;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;

public class TimeConstraintType2Generator implements TimeConstraintGenerator<TimeConstraintType2> {
  @Override
  public String generate(String contextClass, String invariantName, TimeConstraintType2 tc2) {
    System.out.println("Generating TimeConstraintType2...");

    // if part
    String ifCond = buildIfCondition(tc2.ifParts, null);
    String impliesPart = (ifCond == null) ? "" : ifCond + " implies ";
    System.out.println("Implies Part: " + impliesPart);
    boolean hasIfPart = impliesPart != null && !impliesPart.isBlank();

    String checkForExi = buildAllowedConditionWithOperator(tc2.checkForExi, RootScope.ALL);
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
