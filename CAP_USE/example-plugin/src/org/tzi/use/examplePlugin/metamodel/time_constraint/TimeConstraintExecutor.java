package org.tzi.use.examplePlugin.metamodel.time_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import java.util.Map;

public class TimeConstraintExecutor {
  public static String execute(
      ASTInterface astInterface,
      Map<String, Object> astJson,
      String context,
      String name
  ) {
    TimeConstraintDetector timeConstraintDetector = new TimeConstraintDetector();
    TimeConstraintType type =
        timeConstraintDetector.detectType(astInterface);

    TimeConstraintInterface model =
        type.parser().parse(astJson);

    return type.generator()
        .generate(context, name, model);
  }
}
