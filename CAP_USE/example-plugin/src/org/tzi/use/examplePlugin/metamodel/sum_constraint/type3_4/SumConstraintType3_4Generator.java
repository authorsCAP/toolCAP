package org.tzi.use.examplePlugin.metamodel.sum_constraint.type3_4;

import org.tzi.use.examplePlugin.metamodel.AttrCond;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.SumConstraintGenerator;

import java.util.List;
import java.util.stream.Collectors;

public class SumConstraintType3_4Generator implements SumConstraintGenerator<SumConstraintType3_4> {
  @Override
  public String generate(String contextClass, String invariantName, SumConstraintType3_4 sc) {
    String ifCond = buildIfCondition(sc);
    String bound = buildBound(sc);

    String impliesPart = (ifCond == null) ? "" : ifCond + " implies\n";

    String selectCond = buildSelect(sc.filters);

    return """
        context %s
        inv %s:
          %slet s : Integer =
            self.%s
              ->select(e | %s)
              ->collect(e | e.%s.%s)
              ->sum()
          in
            %s
        """.formatted(
        contextClass,
        invariantName,
        impliesPart,
        sc.assocCls,
        selectCond,
        sc.rolePath,
        sc.sumAttr,
        bound
    );
  }

  // ---------- helpers ----------

  private static String buildIfCondition(SumConstraintType3_4 sc) {
    String cond;

    if (sc.ifFixType == null) {
      return null;
    }
    switch (sc.ifFixType) {
      case FIX_NUM, FIX_BOOL, FIX_ATTR -> cond = "self." + sc.ifAttr + " = " + sc.ifFixValue;
      case FIX_STR -> cond = "self." + sc.ifAttr + " = '" + sc.ifFixValue + "'";
      default -> throw new RuntimeException("Invalid ifPart");
    }

    return sc.negated ? "not (" + cond + ")" : cond;
  }

  private static String buildBound(SumConstraintType3_4 sc) {
    if (sc.boundType == null) {
      return "";
    }
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

  private static String buildSelect(List<AttrCond> filters) {
    return filters.stream()
        .map(c -> "e." + c.attr + " = self." + c.matchAttr)
        .collect(Collectors.joining(" and "));
  }
}
