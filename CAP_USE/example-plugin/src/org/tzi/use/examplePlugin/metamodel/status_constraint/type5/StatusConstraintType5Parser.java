package org.tzi.use.examplePlugin.metamodel.status_constraint.type5;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
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

public class StatusConstraintType5Parser implements StatusConstraintParser<StatusConstraintType5> {

  @Override
  public StatusConstraintType5 parse(Map<String, Object> astJson) {
    for (Map.Entry<String, Object> entry : astJson.entrySet()) {
      System.out.println("AST JSON Entry: " + entry.getKey() + " -> " + entry.getValue() + " " + entry.getValue().getClass());
    }

    StatusConstraintType5 sc5 = new StatusConstraintType5();

    System.out.println("Parsing StatusConstraintType5...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);

    sc5.assocCls = (String) args.get(ASSOC_CLS);
    sc5.rolePath = (String) args.get(ROLE_PATH);

    sc5.ifParts = parseIfPart(astJson);
    System.out.println("Parsed ifParts: " + sc5.ifParts);

    // check status
    sc5.checkStatus = parseCheckForExi(args, CHECK_STATUS);
    System.out.println("Parsed checkStatus: " + sc5.checkStatus);

    return sc5;
  }
}
