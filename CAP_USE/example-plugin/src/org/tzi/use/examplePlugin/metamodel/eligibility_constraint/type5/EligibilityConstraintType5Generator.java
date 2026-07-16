package org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type5;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintGenerator;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAttrPath;


public class EligibilityConstraintType5Generator implements EligibilityConstraintGenerator<EligibilityConstraintType5> {
  @Override
  public String generate(String contextClass, String invariantName, EligibilityConstraintType5 ec) {
    System.out.println("Generating EligibilityConstraintType5...");

    return """
        context %s
        inv %s:
          self.%s.%s -> forAll( req | self.%s -> includes(req))
        """.formatted(
        contextClass,
        invariantName,
        ec.rolePath,
        ec.matchColl,
        buildAttrPath(ec.checkForExi.get(0).attrs, null)
    );
  }
}
