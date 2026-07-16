package org.tzi.use.examplePlugin.metamodel.retake_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.RetakeConstraintGenerator;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintGenerator;

import java.util.List;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;

public class RetakeConstraintType1Generator implements RetakeConstraintGenerator<RetakeConstraintType1> {
  @Override
  public String generate(String contextClass, String invariantName, RetakeConstraintType1 rc1) {

    System.out.println("Generating RetakeConstraintType1...");


    // collect part
    String allowedCond1 = null;
    String allowedCond2 = null;

    if (rc1.filters != null && !rc1.filters.isEmpty()) {

      // forAll(1)
      allowedCond1 =
          buildAllowedCondition(
              List.of(rc1.filters.get(0)),
              RootScope.NONE,
              null
          );

      // forAll(2)
      if (rc1.filters.size() > 1) {
        allowedCond2 =
            buildAllowedCondition(
                rc1.filters.subList(1, rc1.filters.size()),
                RootScope.FIRST_ONLY,
                "a"
            );
      }
    }

    System.out.println("Check forAll(1): " + allowedCond1);
    System.out.println("Check forAll(2): " + allowedCond2);


    return """
        context %s inv %s:
          self.%s->forAll(e | %s(self)
            ->forAll(a | %s))
        """.formatted(
        contextClass,
        invariantName,
        rc1.targetAssoc,
        allowedCond1,
        allowedCond2
    );
  }
}
