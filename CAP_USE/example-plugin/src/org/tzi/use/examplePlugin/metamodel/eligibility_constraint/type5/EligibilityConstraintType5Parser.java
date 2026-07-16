package org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type5;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintParser;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type4.EligibilityConstraintType4;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ASSOC_CLS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_FOR_EXI;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MATCH_COLL;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MIN;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.RATIO;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.SCALE;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.TARGET_ASSOC;
import static org.tzi.use.examplePlugin.util.UseUtils.asString;

public class EligibilityConstraintType5Parser implements EligibilityConstraintParser<EligibilityConstraintType5> {
  @Override
  public EligibilityConstraintType5 parse(Map<String, Object> astJson) {
    EligibilityConstraintType5 ec5 = new EligibilityConstraintType5();

    System.out.println("Parsing EligibilityConstraintType5...");
    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    ec5.assocCls = (String) args.get(ASSOC_CLS);
    ec5.rolePath = (String) args.get(ROLE_PATH);

    parseCheckForExi(args, ec5);

    return ec5;
  }

  private static void parseCheckForExi(
      Map<String, Object> args,
      EligibilityConstraintType5 ec5
  ) {

    List<Map<String, Object>> checkForExi =
        (List<Map<String, Object>>) args.get(CHECK_FOR_EXI);

    if (checkForExi == null || checkForExi.isEmpty()) return;

    List<AttrCondPro> attrConds = new ArrayList<>();

    Map<String, Object> attrCond = checkForExi.get(0);

    Map<String, Object> condArgs =
        (Map<String, Object>) attrCond.get(ARGS);

    System.out.println("Parsing collect condition: " + condArgs);

    AttrCondPro c = new AttrCondPro();
    c.attrs = ParserUtil.parseAttrsFromCondArgs(condArgs);

    attrConds.add(c);

    ec5.checkForExi = attrConds;

    ec5.matchColl = (String) condArgs.get(MATCH_COLL);
  }
}
