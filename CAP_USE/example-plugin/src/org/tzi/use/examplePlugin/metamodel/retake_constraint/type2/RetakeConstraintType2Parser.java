package org.tzi.use.examplePlugin.metamodel.retake_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.RetakeConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ASSOC_CLS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.COLLECT;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MAX;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.SUM_ATTR;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.TARGET_ASSOC;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseCheckForExi;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseIfPart;
import static org.tzi.use.examplePlugin.util.UseUtils.asString;

public class RetakeConstraintType2Parser implements RetakeConstraintParser<RetakeConstraintType2> {

  @Override
  public RetakeConstraintType2 parse(Map<String, Object> astJson) {
    RetakeConstraintType2 rc2 = new RetakeConstraintType2();

    System.out.println("Parsing RetakeConstraintType2...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);

    // assocCls and rolePath
    rc2.assocCls = (String) args.get(ASSOC_CLS);
    rc2.rolePath = (String) args.get(ROLE_PATH);
    rc2.targetAssoc = (String) args.get(TARGET_ASSOC);

    // ---- ifParts ----
    rc2.ifParts = parseIfPart(astJson);

    // ---- checkForExi ----
    // the key for checkForExi is null because it is directly parsing checkForExi
    rc2.checkForExi = parseCheckForExi(args, null);

    return rc2;
  }
}
