package org.tzi.use.examplePlugin.metamodel.size_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.OperatorEnum;
import org.tzi.use.examplePlugin.metamodel.size_constraint.Bound;
import org.tzi.use.examplePlugin.metamodel.size_constraint.SizeConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;
import org.tzi.use.examplePlugin.util.enumarate.BoundType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ASSOC_CLS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.COLLECT;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CONFLICT_CHECK;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CROSS_REFERENCE;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.INTERSECTION_OP;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MAX;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.TARGET_ASSOC;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.TARGET_COLLECTION;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseIfPart;
import static org.tzi.use.examplePlugin.util.UseUtils.asString;

public class SizeConstraintType1Parser implements SizeConstraintParser<SizeConstraintType1> {

  @Override
  public SizeConstraintType1 parse(Map<String, Object> astJson) {
    SizeConstraintType1 sc1 = new SizeConstraintType1();

    System.out.println("Parsing SizeConstraintType1...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    sc1.assocCls = (String) args.get(ASSOC_CLS);
    sc1.rolePath = (String) args.get(ROLE_PATH);
    sc1.targetCollection = (String) args.get(TARGET_COLLECTION);

    System.out.println("After parsing root args: assocCls="
        + sc1.assocCls + ", rolePath=" + sc1.rolePath
        + ", targetCollection=" + sc1.targetCollection);

    sc1.ifParts = parseIfPart(astJson);
    System.out.println("Parsed ifParts: " + sc1.ifParts);


    // collect
    parseCollect(args, sc1);
    System.out.println("After parsing collect, filters: " + sc1.filters);

    // parse bounds
    parseBound(args, sc1);
    System.out.println("After parsing bounds, bounds: " + sc1.bounds);

    return sc1;
  }

  private static void parseCollect(
      Map<String, Object> args,
      SizeConstraintType1 sc1
  ) {

    List<Map<String, Object>> collect =
        (List<Map<String, Object>>) args.get(COLLECT);

    if (collect == null || collect.isEmpty()) return;

    List<AttrCondPro> attrConds = new ArrayList<>();

    for (Map<String, Object> attrCond : collect) {

      Map<String, Object> condArgs =
          (Map<String, Object>) attrCond.get(ARGS);

      AttrCondPro c = new AttrCondPro();
      c.attrs = ParserUtil.parseAttrsFromCondArgs(condArgs);
      c.neg = Boolean.TRUE.equals(condArgs.get("neg"));

      if (condArgs.containsKey("minLim")) {
        c.type = AttrCondPro.Type.MIN_LIM;
        c.matchAttr = String.valueOf(condArgs.get("minLim"));
      } else if (condArgs.containsKey("maxLim")) {
        c.type = AttrCondPro.Type.MAX_LIM;
        c.matchAttr = String.valueOf(condArgs.get("maxLim"));
      } else if (condArgs.containsKey("fixBool")) {
        c.type = AttrCondPro.Type.FIX_BOOL;
        c.matchAttr = String.valueOf(condArgs.get("fixBool"));
      } else if (condArgs.containsKey("fixNum")) {
        c.type = AttrCondPro.Type.FIX_NUM;
        c.matchAttr = String.valueOf(condArgs.get("fixNum"));
      } else if (condArgs.containsKey("minLimAttr")) {
        c.type = AttrCondPro.Type.MIN_LIM_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("minLimAttr"));
      } else if (condArgs.containsKey("maxLimAttr")) {
        c.type = AttrCondPro.Type.MAX_LIM_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("maxLimAttr"));
      } else if (condArgs.containsKey("min")) {
        c.type = AttrCondPro.Type.MIN;
        c.matchAttr = String.valueOf(condArgs.get("min"));
      } else if (condArgs.containsKey("max")) {
        c.type = AttrCondPro.Type.MAX;
        c.matchAttr = String.valueOf(condArgs.get("max"));
      } else if (condArgs.containsKey("matchStr") || condArgs.containsKey("fixStr")) {
        c.type = AttrCondPro.Type.MATCH_STR;
        c.matchAttr = String.valueOf(
            condArgs.containsKey("matchStr")
                ? condArgs.get("matchStr")
                : condArgs.get("fixStr")
        );
      }

      attrConds.add(c);
    }

    sc1.filters = attrConds;
    System.out.println("Parsed filters: " + sc1.filters);
  }

  private void parseBound(Map<String, Object> args,
                          SizeConstraintType1 sc1) {
    List<Bound> bounds = new ArrayList<>();
    Arrays.stream(OperatorEnum.values()).forEach(boundType -> {
      String boundKey = boundType.getName();
      if (args.containsKey(boundKey)) {
        bounds.add(new Bound(boundType, asString(args.get(boundKey))));
      }
    });
    sc1.bounds = bounds;
  }
}
