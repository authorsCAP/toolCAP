package org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintGenerator;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;

public class EligibilityConstraintType2Generator implements EligibilityConstraintGenerator<EligibilityConstraintType2> {
  @Override
  public String generate(String contextClass, String invariantName, EligibilityConstraintType2 ec) {
    System.out.println("Generating EligibilityConstraintType2...");
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
