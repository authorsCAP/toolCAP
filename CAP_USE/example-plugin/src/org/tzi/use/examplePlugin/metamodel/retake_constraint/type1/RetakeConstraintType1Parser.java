package org.tzi.use.examplePlugin.metamodel.retake_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.RetakeConstraintParser;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintParser;
import org.tzi.use.examplePlugin.metamodel.status_constraint.type3.StatusConstraintType3;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ASSOC_CLS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_STATUS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.COLLECT;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MAX;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.SUM_ATTR;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.TARGET_ASSOC;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseCheckForExi;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseIfPart;
import static org.tzi.use.examplePlugin.util.UseUtils.asString;

public class RetakeConstraintType1Parser implements RetakeConstraintParser<RetakeConstraintType1> {

  @Override
  public RetakeConstraintType1 parse(Map<String, Object> astJson) {
    RetakeConstraintType1 rc1 = new RetakeConstraintType1();

    System.out.println("Parsing RetakeConstraintType1...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);

    // assocCls and rolePath
    rc1.assocCls = (String) args.get(ASSOC_CLS);
    rc1.rolePath = (String) args.get(ROLE_PATH);
    rc1.targetAssoc = (String) args.get(TARGET_ASSOC);

    // sumAttr and max
    rc1.sumAttr = asString(args.get(SUM_ATTR));
    rc1.max = Integer.parseInt(asString(args.get(MAX)));

    // collect
    parseCollect(args, rc1);

    return rc1;
  }

  private static void parseCollect(
      Map<String, Object> args,
      RetakeConstraintType1 rc1
  ) {

    List<Map<String, Object>> collect =
        (List<Map<String, Object>>) args.get(COLLECT);

    if (collect == null || collect.isEmpty()) return;

    List<AttrCondPro> attrConds = new ArrayList<>();

    for (Map<String, Object> attrCond : collect) {

      Map<String, Object> condArgs =
          (Map<String, Object>) attrCond.get(ARGS);

      System.out.println("Parsing collect condition: " + condArgs);

      AttrCondPro c = new AttrCondPro();
      c.attrs = ParserUtil.parseAttrsFromCondArgs(condArgs);

      // neg: default is false
      c.neg = false;

      if (condArgs.containsKey("neg")) {
        Object v = condArgs.get("neg");
        c.neg = Boolean.parseBoolean(String.valueOf(v));
      }

      if (condArgs.containsKey("minLim")) {
        c.type = AttrCondPro.Type.MIN_LIM;
        c.matchAttr = String.valueOf(condArgs.get("minLim"));
      } else if (condArgs.containsKey("maxLim")) {
        c.type = AttrCondPro.Type.MAX_LIM;
        c.matchAttr = String.valueOf(condArgs.get("maxLim"));
      } else if (condArgs.containsKey("fixBool")) {
        c.type = AttrCondPro.Type.FIX_BOOL;
        c.matchAttr = String.valueOf(condArgs.get("fixBool"));
      } else if(condArgs.containsKey("fixNum")) {
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
      } else if (condArgs.containsKey("matchAttr")) {
        c.type = AttrCondPro.Type.MATCH_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("matchAttr"));
      } else if (condArgs.containsKey("maxAttr")) {
        c.type = AttrCondPro.Type.MAX_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("maxAttr"));
      } else if (condArgs.containsKey("minAttr")) {
        c.type = AttrCondPro.Type.MIN_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("minAttr"));
      } else if (condArgs.containsKey("minValue")) {
        c.type = AttrCondPro.Type.MIN_LIM;
        c.matchAttr = String.valueOf(condArgs.get("minValue"));
      } else if (condArgs.containsKey("maxValue")) {
        c.type = AttrCondPro.Type.MAX_LIM;
        c.matchAttr = String.valueOf(condArgs.get("maxValue"));
      }

      c.attrs = ParserUtil.parseAttrsFromCondArgs(condArgs);

      attrConds.add(c);
    }

    rc1.filters = attrConds;
  }
}
