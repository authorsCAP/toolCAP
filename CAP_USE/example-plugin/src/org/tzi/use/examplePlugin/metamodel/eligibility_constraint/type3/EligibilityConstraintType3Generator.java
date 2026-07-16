package org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type3;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintGenerator;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;

public class EligibilityConstraintType3Generator implements EligibilityConstraintGenerator<EligibilityConstraintType3> {
  @Override
  public String generate(String contextClass, String invariantName, EligibilityConstraintType3 ec) {
    System.out.println("Generating EligibilityConstraintType3...");
    String ifCond = buildIfCondition(ec.ifParts, null);
    String impliesPart = (ifCond == null) ? "" : ifCond + " implies\n  ";

    String checkForExi = buildAllowedCondition(ec.checkForExi, RootScope.ALL, null);

    return """
        context %s
        inv %s:
          %s(
            %s
          )
        """.formatted(
        contextClass,
        invariantName,
        impliesPart,
        checkForExi
    );
  }
}
