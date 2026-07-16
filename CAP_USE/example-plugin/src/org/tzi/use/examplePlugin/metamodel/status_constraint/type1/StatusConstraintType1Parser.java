package org.tzi.use.examplePlugin.metamodel.status_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintParser;

import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_STATUS;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseCheckForExi;
import static org.tzi.use.examplePlugin.util.ParserUtil.parseIfPart;

public class StatusConstraintType1Parser implements StatusConstraintParser<StatusConstraintType1> {

  @Override
  public StatusConstraintType1 parse(Map<String, Object> astJson) {
    StatusConstraintType1 sc1 = new StatusConstraintType1();

    System.out.println("Parsing StatusConstraintType1...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);

    sc1.ifParts = parseIfPart(astJson);
    System.out.println("Parsed ifParts: " + sc1.ifParts);

    sc1.checkStatus = parseCheckForExi(args, CHECK_STATUS);
    System.out.println("Parsed checkStatus: " + sc1.checkStatus);

    return sc1;
  }
}
