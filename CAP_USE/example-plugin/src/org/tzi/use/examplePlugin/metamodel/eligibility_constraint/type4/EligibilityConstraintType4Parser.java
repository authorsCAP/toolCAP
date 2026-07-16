package org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type4;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ASSOC_CLS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_FOR_EXI;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MIN;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.RATIO;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.SCALE;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.TARGET_ASSOC;
import static org.tzi.use.examplePlugin.util.UseUtils.asString;

public class EligibilityConstraintType4Parser implements EligibilityConstraintParser<EligibilityConstraintType4> {
  @Override
  public EligibilityConstraintType4 parse(Map<String, Object> astJson) {
    EligibilityConstraintType4 ec4 = new EligibilityConstraintType4();

    System.out.println("Parsing EligibilityConstraintType4...");
    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    ec4.assocCls = (String) args.get(ASSOC_CLS);
    ec4.rolePath = (String) args.get(ROLE_PATH);
    ec4.targetAssoc = (String) args.get(TARGET_ASSOC);

    ec4.min = Integer.parseInt(asString(args.get(MIN)));

    // if parts
    ec4.ifParts = ParserUtil.parseIfPart(astJson);

    parseCheckForExi(args, ec4);

    return ec4;
  }

  private static void parseCheckForExi(
      Map<String, Object> args,
      EligibilityConstraintType4 ec4
  ) {

    List<Map<String, Object>> checkForExi =
        (List<Map<String, Object>>) args.get(CHECK_FOR_EXI);

    if (checkForExi == null || checkForExi.isEmpty()) return;

    List<AttrCondPro> attrConds = new ArrayList<>();

    for (Map<String, Object> attrCond : checkForExi) {

      Map<String, Object> condArgs =
          (Map<String, Object>) attrCond.get(ARGS);

      System.out.println("Parsing collect condition: " + condArgs);

      AttrCondPro c = new AttrCondPro();
      c.attrs = ParserUtil.parseAttrsFromCondArgs(condArgs);
      c.neg = Boolean.TRUE.equals(condArgs.get("neg"));

      Object scaleObj = condArgs.get(SCALE);
      Object ratioObj = condArgs.get(RATIO);

      if (scaleObj != null) {
        c.scale = scaleObj.toString();
      } else if (ratioObj != null) {
        c.scale = ratioObj.toString();
      } else {
        c.scale = null;
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
      }
      attrConds.add(c);
    }

    ec4.checkForExi = attrConds;
  }
}
