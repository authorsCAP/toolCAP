package org.tzi.use.examplePlugin.metamodel.schedule_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.schedule_constraint.ScheduleConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ALT_PART;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ASSOC_CLS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_FOR_EXI;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.TARGET_ASSOC;

public class ScheduleConstraintType2Parser implements ScheduleConstraintParser<ScheduleConstraintType2> {

  @Override
  public ScheduleConstraintType2 parse(Map<String, Object> astJson) {
    ScheduleConstraintType2 sc2 = new ScheduleConstraintType2();

    System.out.println("Parsing ScheduleConstraintType2...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    sc2.assocCls = (String) args.get(ASSOC_CLS);
    sc2.rolePath = (String) args.get(ROLE_PATH);
    sc2.targetAssoc = (String) args.get(TARGET_ASSOC);

    // if parts
    sc2.ifParts = ParserUtil.parseIfPart(astJson);

    // check for exi parts
    parseAttrCondProFromAttr(args, sc2, CHECK_FOR_EXI);
    parseAttrCondProFromAttr(args, sc2, ALT_PART);

    return sc2;
  }

  /**
   * Parse the list of AttrCondPro from the specific attribute
   * There are 2 cases for this
   * - CHECK_FOR_EXI
   * - ALT_PARTS
   * @param args
   * @param sc2
   */
  private static void parseAttrCondProFromAttr(
      Map<String, Object> args,
      ScheduleConstraintType2 sc2,
      String key
  ) {

    if (!key.equalsIgnoreCase(CHECK_FOR_EXI) && !key.equalsIgnoreCase(ALT_PART)) {
      throw new IllegalArgumentException("Unsupported key for parsing AttrCondPro: " + key);
    }

    List<Map<String, Object>> listOfAttrCondPro =
        (List<Map<String, Object>>) args.get(key);

    if (listOfAttrCondPro == null || listOfAttrCondPro.isEmpty()) return;

    List<AttrCondPro> attrConds = new ArrayList<>();

    for (Map<String, Object> attrCond : listOfAttrCondPro) {

      Map<String, Object> condArgs =
          (Map<String, Object>) attrCond.get(ARGS);

      System.out.println("Parsing collect condition: " + condArgs);

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
      } else if (condArgs.containsKey("fixEnum")) {
        c.type = AttrCondPro.Type.FIX_ENUM;
        c.matchAttr = String.valueOf(condArgs.get("fixEnum"));
      }

      attrConds.add(c);
    }

    if (key.equalsIgnoreCase(CHECK_FOR_EXI)) {
      sc2.checkForExi = attrConds;
    } else if (key.equalsIgnoreCase(ALT_PART)) {
      sc2.altParts = attrConds;
    }
  }

}
