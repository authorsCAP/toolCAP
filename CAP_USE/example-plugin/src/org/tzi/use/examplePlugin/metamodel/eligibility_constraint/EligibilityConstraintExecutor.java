package org.tzi.use.examplePlugin.metamodel.eligibility_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import java.util.Map;

public class EligibilityConstraintExecutor {
  public static String execute(
      ASTInterface astInterface,
      Map<String, Object> astJson,
      String context,
      String name
  ) {
    EligibilityConstraintDetector eligibilityConstraintDetector = new EligibilityConstraintDetector();
    EligibilityConstraintType type =
        eligibilityConstraintDetector.detectType(astInterface);

    EligibilityConstraintInterface model =
        type.parser().parse(astJson);

    return type.generator()
        .generate(context, name, model);
  }
}
