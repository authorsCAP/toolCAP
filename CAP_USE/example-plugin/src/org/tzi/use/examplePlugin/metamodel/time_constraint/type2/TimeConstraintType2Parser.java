package org.tzi.use.examplePlugin.metamodel.time_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.time_constraint.TimeConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;

public class TimeConstraintType2Parser implements TimeConstraintParser<TimeConstraintType2> {

  @Override
  public TimeConstraintType2 parse(Map<String, Object> astJson) {
    TimeConstraintType2 tc2 = new TimeConstraintType2();

    System.out.println("Parsing TimeConstraintType2...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    tc2.rolePath = (String) args.get(ROLE_PATH);

    // if parts
    tc2.ifParts = ParserUtil.parseIfPart(astJson);
    System.out.println("Parsed ifParts: " + tc2.ifParts);

    // check for existence parts
    tc2.checkForExi = ParserUtil.parseCheckForExi(args, null);
    System.out.println("Parsed checkForExi: " + tc2.checkForExi);

    return tc2;
  }
}
