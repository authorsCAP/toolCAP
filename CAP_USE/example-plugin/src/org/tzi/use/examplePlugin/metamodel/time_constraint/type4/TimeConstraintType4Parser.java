package org.tzi.use.examplePlugin.metamodel.time_constraint.type4;

import org.tzi.use.examplePlugin.metamodel.time_constraint.TimeConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;

public class TimeConstraintType4Parser implements TimeConstraintParser<TimeConstraintType4> {

  @Override
  public TimeConstraintType4 parse(Map<String, Object> astJson) {
    TimeConstraintType4 tc4 = new TimeConstraintType4();

    System.out.println("Parsing TimeConstraintType4...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    tc4.rolePath = (String) args.get(ROLE_PATH);

    // if parts
    tc4.ifParts = ParserUtil.parseIfPart(astJson);
    System.out.println("Parsed ifParts: " + tc4.ifParts);

    // check for existence parts
    tc4.checkForExi = ParserUtil.parseCheckForExi(args, null);
    System.out.println("Parsed checkForExi: " + tc4.checkForExi);

    return tc4;
  }
}
