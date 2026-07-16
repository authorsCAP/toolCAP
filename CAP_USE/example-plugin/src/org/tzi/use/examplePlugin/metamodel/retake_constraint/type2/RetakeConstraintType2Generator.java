package org.tzi.use.examplePlugin.metamodel.retake_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.RetakeConstraintGenerator;

import java.util.List;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildExistsCheckCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildExistsIfCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtilsV2.buildExistsCheckConditionForRetake;
import static org.tzi.use.examplePlugin.util.GeneratorUtilsV2.buildIfConditionWithReference;

public class RetakeConstraintType2Generator implements RetakeConstraintGenerator<RetakeConstraintType2> {
  @Override
  public String generate(String contextClass, String invariantName, RetakeConstraintType2 rc2) {
    System.out.println("Generating RetakeConstraintType2...");

    String var1 = "c";
    String var2 = "e";

    String ifCondition = buildIfConditionWithReference(rc2.ifParts, null, var1);

    String existsCondition = buildExistsCheckConditionForRetake(
        rc2.rolePath,
        rc2.checkForExi.get(0),
        var1,
        var2
    );

    return """
        context %s inv %s:
            self.failedCourses->forAll(%s |
                %s
                implies %s
            )
        """.formatted(
        contextClass,
        invariantName,
        var1,
        ifCondition,
        existsCondition
    );
  }
}
