package org.tzi.use.examplePlugin.metamodel.size_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;
import org.tzi.use.examplePlugin.metamodel.size_constraint.SizeConstraintGenerator;

import java.util.stream.Collectors;

import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildAllowedCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildIfCondition;
import static org.tzi.use.examplePlugin.util.GeneratorUtils.buildSizePathWithOperator;

public class SizeConstraintType1Generator implements SizeConstraintGenerator<SizeConstraintType1> {
  @Override
  public String generate(String contextClass, String invariantName, SizeConstraintType1 sc1) {

    System.out.println("Generating SizeConstraintType1...");

    // if part
    String ifCond = buildIfCondition(sc1.ifParts, null);
    String impliesPart = (ifCond == null) ? "" : ifCond + " implies\n  ";
    System.out.println("Implies Part: " + impliesPart);
    boolean hasIfPart = impliesPart != null && !impliesPart.isBlank();


    // collect part
    String allowedCond = null;
    if (sc1.filters != null) {
      allowedCond = buildAllowedCondition(sc1.filters, RootScope.NONE, null);
    }
    System.out.println("Allowed Condition: " + allowedCond);

    final String selectPart =
        (allowedCond != null && !allowedCond.isEmpty())
            ? "->select(e | " + allowedCond + ")"
            : null;
    System.out.println("Select Part: " + selectPart);


    // bound
    String bound =
        sc1.bounds.stream()
            .map(b -> buildSizePathWithOperator(
                sc1.rolePath,
                sc1.targetCollection,
                b.getType().symbol,
                b.getValue(),
                selectPart
            ))
            .collect(Collectors.joining(" and "));

    String body =
        hasIfPart
            ? """
            %s(
              %s
            )
            """.formatted(impliesPart, bound)
            : bound;

    return """
    context %s
    inv %s:
      %s
    """.formatted(
        contextClass,
        invariantName,
        body
    );
  }
}
