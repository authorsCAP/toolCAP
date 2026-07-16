package org.tzi.use.examplePlugin.metamodel.sum_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.sum_constraint.SumConstraintGenerator;

public class SumConstraintType2Generator implements SumConstraintGenerator<SumConstraintType2> {
  @Override
  public String generate(String contextClass, String invariantName, SumConstraintType2 sc) {
    String ifCond = buildIfCondition(sc);
    String bound  = buildBound(sc);

    String impliesPart = (ifCond == null) ? "" : ifCond + " implies\n";

    return String.format(
        """
        context %s
        inv %s:
          %slet v : Integer =
              self.%s
                  ->select(e | e.%s = self.%s)
                  ->collect(e | e.%s.%s)
                  ->sum()
          in
              %s
        """,
        contextClass,
        invariantName,
        impliesPart,
        sc.assocCls,
        sc.filterAttr,
        sc.matchAttr,
        sc.rolePath,
        sc.sumAttr,
        bound
    ).trim();
  }

  // ---------- helpers ----------

  private static String buildIfCondition(SumConstraintType2 sc) {
    String cond;

    if (sc.ifFixType == null) {
      return null;
    }
    switch (sc.ifFixType) {
      case FIX_NUM, FIX_BOOL, FIX_ATTR ->
          cond = "self." + sc.ifAttr + " = " + sc.ifFixValue;
      case FIX_STR ->
          cond = "self." + sc.ifAttr + " = '" + sc.ifFixValue + "'";
      default ->
          throw new RuntimeException("Invalid ifPart");
    }

    return sc.negated ? "not (" + cond + ")" : cond;
  }

  private static String buildBound(SumConstraintType2 sc) {
    return switch (sc.boundType) {
      case MAX ->
          "v < " + sc.boundValue;
      case MAX_LIM ->
          "v <= " + sc.boundValue;
      case MIN ->
          "v >= " + sc.boundValue;
      case MIN_LIM ->
          "v > " + sc.boundValue;
      case MAX_ATTR ->
          "v <= self." + sc.boundValue;
      case MAX_LIM_ATTR ->
          "v < self." + sc.boundValue;
      case MIN_ATTR ->
          "v >= self." + sc.boundValue;
      case MIN_LIM_ATTR ->
          "v > self." + sc.boundValue;
      case EQUALS -> null;
    };
  }
}
