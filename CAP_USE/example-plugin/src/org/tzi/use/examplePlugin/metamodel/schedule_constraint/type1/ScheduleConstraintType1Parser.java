package org.tzi.use.examplePlugin.metamodel.schedule_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.schedule_constraint.ScheduleConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.ArrayList;
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
import static org.tzi.use.examplePlugin.util.UseUtils.asString;

public class ScheduleConstraintType1Parser implements ScheduleConstraintParser<ScheduleConstraintType1> {
  private static final String EMPTY = "EMPTY";

  @Override
  public ScheduleConstraintType1 parse(Map<String, Object> astJson) {
    ScheduleConstraintType1 sc1 = new ScheduleConstraintType1();

    System.out.println("Parsing ScheduleConstraintType1...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    sc1.assocCls = (String) args.get(ASSOC_CLS);
    sc1.rolePath = (String) args.get(ROLE_PATH);
    sc1.targetAssoc = (String) args.get(TARGET_ASSOC);

    // ---- collect attributes ----
    sc1.max = Integer.parseInt(asString(args.get(MAX)));
    sc1.crossReference = Boolean.parseBoolean(asString(args.get(CROSS_REFERENCE)));
    sc1.conflictCheck = (String) args.get(CONFLICT_CHECK);
    sc1.intersectionOp = (String) args.get(INTERSECTION_OP);
    // validate conditions
    if (!sc1.crossReference
        || sc1.max != 0
        || !sc1.intersectionOp.equalsIgnoreCase(EMPTY)) {
      System.out.println("Invalid ScheduleConstraintType1 configuration!");
      return null;
    }

    // collect
    parseCollect(args, sc1);

    return sc1;
  }

  private static void parseCollect(
      Map<String, Object> args,
      ScheduleConstraintType1 sc1
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
}
