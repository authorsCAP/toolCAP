package org.tzi.use.examplePlugin.metamodel.status_constraint.type3;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type1.EligibilityConstraintType1;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ASSOC_CLS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_STATUS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.COLLECT;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseCheckForExi;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseIfPart;

public class StatusConstraintType3Parser implements StatusConstraintParser<StatusConstraintType3> {

  @Override
  public StatusConstraintType3 parse(Map<String, Object> astJson) {
    for (Map.Entry<String, Object> entry : astJson.entrySet()) {
      System.out.println("AST JSON Entry: " + entry.getKey() + " -> " + entry.getValue() + " " + entry.getValue().getClass());
    }

    StatusConstraintType3 sc3 = new StatusConstraintType3();

    System.out.println("Parsing StatusConstraintType3...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);

    sc3.assocCls = (String) args.get(ASSOC_CLS);
    sc3.rolePath = (String) args.get(ROLE_PATH);

    sc3.ifParts = parseIfPart(astJson);
    System.out.println("Parsed ifParts: " + sc3.ifParts);

    // collect
    parseCollect(args, sc3);

    Object object = args.get(CHECK_STATUS);
    if (object != null) {
      System.out.println("Raw checkStatus value: " + object + " of type " + object.getClass());
      sc3.checkStatus = object instanceof List ? (List<Integer>) object : null;
    }
    System.out.println("Parsed checkStatus: " + sc3.checkStatus);
    if (sc3.checkStatus.get(0) != 0) {
      throw new InvalidParameterException("checkStatus must start with 0 for this type");
    }

    return sc3;
  }

  private static void parseCollect(
      Map<String, Object> args,
      StatusConstraintType3 sc3
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
      }

      attrConds.add(c);
    }

    sc3.filters = attrConds;
  }
}
