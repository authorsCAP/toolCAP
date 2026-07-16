package org.tzi.use.examplePlugin.metamodel.sum_constraint.type1;

import org.tzi.use.examplePlugin.ast.ASTInterface;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.SumConstraintParser;

import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.*;

@SuppressWarnings("unchecked")
public class SumConstraintType1Parser implements SumConstraintParser<SumConstraintType1> {

  @Override
  public SumConstraintType1 parse(Map<String, Object> astJson) {

    SumConstraintType1 sc = new SumConstraintType1();

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);

    sc.assocCls = (String) args.get(ASSOC_CLS);
    sc.rolePath = (String) args.get(ROLE_PATH);
    sc.sumAttr = (String) args.get(SUM_ATTR);
    sc.fixAttr = (String) args.get(FIX_ATTR);

    // parse collect
    parseCollect(args, sc);

    return sc;
  }

  // collect
  private static void parseCollect(
      Map<String, Object> args,
      SumConstraintType1 sc
  ) {

    List<Map<String, Object>> collect =
        (List<Map<String, Object>>) args.get(COLLECT);

    if (collect == null || collect.isEmpty()) return;

    Map<String, Object> attrCond = collect.get(0);

    Map<String, Object> condArgs =
        (Map<String, Object>) attrCond.get(ARGS);

    sc.filterAttr = (String) condArgs.get(ATTR);
    sc.matchAttr = (String) condArgs.get(MATCH_ATTR);
  }
}