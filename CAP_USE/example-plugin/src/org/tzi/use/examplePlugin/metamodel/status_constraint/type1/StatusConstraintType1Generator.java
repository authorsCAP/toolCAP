package org.tzi.use.examplePlugin.metamodel.status_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintGenerator;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;

public class StatusConstraintType1Generator implements StatusConstraintGenerator<StatusConstraintType1> {
  @Override
  public String generate(String contextClass, String invariantName, StatusConstraintType1 sc1) {

    System.out.println("Generating StatusConstraintType1...");

    // if part
    String ifCond = buildIfCondition(sc1.ifParts, null);
    String impliesPart = (ifCond == null) ? "" : ifCond + " implies ";
    System.out.println("Implies Part: " + impliesPart);
    boolean hasIfPart = impliesPart != null && !impliesPart.isBlank();


    // check status part
    String allowedCond = null;
    if (sc1.checkStatus != null) {
      allowedCond = buildAllowedCondition(sc1.checkStatus, RootScope.ALL, null);
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
