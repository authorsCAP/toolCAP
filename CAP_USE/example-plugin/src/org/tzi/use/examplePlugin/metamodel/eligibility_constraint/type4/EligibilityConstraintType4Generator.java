package org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type4;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintGenerator;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildExistsCheckCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildExistsIfCondition;

public class EligibilityConstraintType4Generator implements EligibilityConstraintGenerator<EligibilityConstraintType4> {
  @Override
  public String generate(String contextClass, String invariantName, EligibilityConstraintType4 ec) {
    System.out.println("Generating EligibilityConstraintType4...");

    String ifExists = buildExistsIfCondition(ec.targetAssoc, ec.ifParts);
    String checkExists = buildExistsCheckCondition(ec.targetAssoc, ec.checkForExi);

    return """
        context %s
        inv %s:
          %s
          implies
          %s
        """.formatted(
        contextClass,
        invariantName,
        ifExists,
        checkExists
    );
  }
}
