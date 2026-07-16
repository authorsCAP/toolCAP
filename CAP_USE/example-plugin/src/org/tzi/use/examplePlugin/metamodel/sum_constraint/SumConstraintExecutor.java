package org.tzi.use.examplePlugin.metamodel.sum_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import java.util.Map;

public class SumConstraintExecutor {

  public static String execute(
      ASTInterface astInterface,
      Map<String, Object> astJson,
      String context,
      String name
  ) {
    SumConstraintDetector sumConstraintDetector = new SumConstraintDetector();
    SumConstraintType type =
        sumConstraintDetector.detectType(astInterface);

    SumConstraintInterface model =
        type.parser().parse(astJson);

    return type.generator()
        .generate(context, name, model);
  }
}
