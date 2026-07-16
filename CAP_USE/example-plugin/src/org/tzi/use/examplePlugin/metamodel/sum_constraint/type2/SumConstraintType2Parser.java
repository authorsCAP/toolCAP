package org.tzi.use.examplePlugin.metamodel.sum_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.sum_constraint.SumConstraintParser;
import org.tzi.use.examplePlugin.util.enumarate.BoundType;
import org.tzi.use.examplePlugin.util.enumarate.IfFixType;

import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ASSOC_CLS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ATTR;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.COLLECT;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MATCH_ATTR;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.SUM_ATTR;
import static org.tzi.use.examplePlugin.util.UseUtils.asString;

public class SumConstraintType2Parser implements SumConstraintParser<SumConstraintType2> {
  @Override
  public SumConstraintType2 parse(Map<String, Object> astJson) {

    System.out.println("Parsing SumConstraintType2:");
    SumConstraintType2 sc = new SumConstraintType2();

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    sc.assocCls = (String) args.get(ASSOC_CLS);
    sc.rolePath = (String) args.get(ROLE_PATH);
    sc.sumAttr = (String) args.get(SUM_ATTR);

    // collect
    System.out.println("Parsing collect part:");
    parseCollect(args, sc);

    System.out.println("Parsing condition part:");
    // ifPart
    List<Map<String, Object>> ifPart =
        (List<Map<String, Object>>) args.get("ifPart");

    System.out.println("If part is: " + ifPart);

    if (ifPart != null && !ifPart.isEmpty()) {
      Map<String, Object> cond = ifPart.get(0);
      Map<String, Object> condArgs =
          (Map<String, Object>) cond.get(ARGS);

      sc.ifAttr = asString(condArgs.get("attr"));

      if (condArgs.containsKey("fixAttr")) {
        sc.ifFixType = IfFixType.FIX_ATTR;
        sc.ifFixValue = asString(condArgs.get("fixAttr"));
      } else if (condArgs.containsKey("fixNum")) {
        sc.ifFixType = IfFixType.FIX_NUM;
        sc.ifFixValue = asString(condArgs.get("fixNum"));
      } else if (condArgs.containsKey("fixBool")) {
        sc.ifFixType = IfFixType.FIX_BOOL;
        sc.ifFixValue = asString(condArgs.get("fixBool"));
      } else if (condArgs.containsKey("fixStr")) {
        sc.ifFixType = IfFixType.FIX_STR;
        sc.ifFixValue = asString(condArgs.get("fixStr"));
      }
    }

    // bound
    System.out.println("Parsing bound part:");
    for (BoundType t : BoundType.values()) {
      String key = toKey(t);
      if (args.containsKey(key)) {
        sc.boundType = t;
        sc.boundValue = asString(args.get(key));
        break;
      }
    }

    if (sc.boundType == null) {
      throw new RuntimeException("Missing bound in TYPE 2");
    }

    return sc;
  }

  // collect
  private static void parseCollect(
      Map<String, Object> args,
      SumConstraintType2 sc
  ) {

    System.out.println("Inside parseCollect:");
    List<Map<String, Object>> collect =
        (List<Map<String, Object>>) args.get(COLLECT);

    System.out.println("Collect part is: " + collect);
    if (collect == null || collect.isEmpty()) return;

    Map<String, Object> attrCond = collect.get(0);

    System.out.println("Attribute condition: " + attrCond);
    Map<String, Object> condArgs =
        (Map<String, Object>) attrCond.get(ARGS);

    sc.filterAttr = (String) condArgs.get(ATTR);
    sc.matchAttr = (String) condArgs.get(MATCH_ATTR);
  }

  public static String toKey(BoundType t) {

    System.out.println("Converting BoundType to key: " + t);

    String[] parts = t.name().toLowerCase().split("_");
    StringBuilder sb = new StringBuilder(parts[0]);
    for (int i = 1; i < parts.length; i++) {
      sb.append(Character.toUpperCase(parts[i].charAt(0)))
          .append(parts[i].substring(1));
    }
    return sb.toString();
  }
}
