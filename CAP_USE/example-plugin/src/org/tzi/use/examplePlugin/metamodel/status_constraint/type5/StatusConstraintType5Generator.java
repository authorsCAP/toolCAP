package org.tzi.use.examplePlugin.metamodel.status_constraint.type5;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintGenerator;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;

public class StatusConstraintType5Generator implements StatusConstraintGenerator<StatusConstraintType5> {
  @Override
  public String generate(String contextClass, String invariantName, StatusConstraintType5 sc5) {

    System.out.println("Generating StatusConstraintType5...");

    // if part
    String ifCond = buildIfCondition(sc5.ifParts, null);
    String impliesPart = (ifCond == null) ? "" : ifCond + " implies ";
    System.out.println("Implies Part: " + impliesPart);
    boolean hasIfPart = impliesPart != null && !impliesPart.isBlank();


    // check status part
    String allowedCond = null;
    if (sc5.checkStatus != null) {
      allowedCond = buildAllowedCondition(sc5.checkStatus, RootScope.ALL, null);
    }
    System.out.println("Status Condition: " + allowedCond);

    String body =
        hasIfPart
            ? """
            %s
                %s
            """.formatted(impliesPart, allowedCond)
            : allowedCond;

    return """
    context %s
    inv %s:
      %s
    """.formatted(
        contextClass,
        invariantName,
        body
    );
  }
}
