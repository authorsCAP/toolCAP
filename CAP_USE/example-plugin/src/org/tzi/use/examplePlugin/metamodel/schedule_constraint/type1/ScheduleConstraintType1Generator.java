package org.tzi.use.examplePlugin.metamodel.schedule_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.schedule_constraint.ScheduleConstraintGenerator;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;

public class ScheduleConstraintType1Generator implements ScheduleConstraintGenerator<ScheduleConstraintType1> {
  @Override
  public String generate(String contextClass, String invariantName, ScheduleConstraintType1 sc1) {
    System.out.println("Generating ScheduleConstraintType1...");

    String allowedCond = null;
    if (sc1.filters != null) {
      allowedCond = buildAllowedCondition(sc1.filters, RootScope.NONE, null);
    }

    String selectPart = "";
    if (allowedCond != null && !allowedCond.isEmpty()) {
      selectPart = "-> select(e | " + allowedCond + ")";
    }

    String conflictPath = "%s.%s".formatted(
        sc1.rolePath,
        sc1.conflictCheck
    );

    return """
        context %s
        inv %s:
          self.%s%s
            -> forAll(e1, e2 | 
                e1 <> e2 implies
                e1.%s 
                -> intersection(e2.%s)
                -> isEmpty()
               )
        """.formatted(
        contextClass,
        invariantName,
        sc1.targetAssoc,
        selectPart,
        conflictPath,
        conflictPath
    );
  }
}
