package org.tzi.use.examplePlugin.metamodel.status_constraint.type3;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintGenerator;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;

public class StatusConstraintType3Generator implements StatusConstraintGenerator<StatusConstraintType3> {
  @Override
  public String generate(String contextClass, String invariantName, StatusConstraintType3 sc3) {

    System.out.println("Generating StatusConstraintType3...");

    // if part
    String ifCond = buildIfCondition(sc3.ifParts, null);
    String impliesPart = (ifCond == null) ? "" : ifCond + " implies ";
    System.out.println("Implies Part: " + impliesPart);
    boolean hasIfPart = impliesPart != null && !impliesPart.isBlank();


    // collect
    String allowedCond = null;
    if (sc3.filters != null) {
      allowedCond = buildAllowedCondition(sc3.filters, RootScope.ALL, null);
    }
    String selectPart = "";
    if (allowedCond != null && !allowedCond.isEmpty()) {
      selectPart = "-> select(e | " + allowedCond + ")";
    }

    String body =
        hasIfPart
            ? """
            %s
                self.%s%s"""
            .formatted(impliesPart, sc3.assocCls, selectPart)
            : allowedCond;

    return """
    context %s
    inv %s:
      %s->isEmpty()
    """.formatted(
        contextClass,
        invariantName,
        body
    );
  }
}
