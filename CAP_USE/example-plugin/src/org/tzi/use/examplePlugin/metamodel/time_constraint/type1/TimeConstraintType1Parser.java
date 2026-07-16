package org.tzi.use.examplePlugin.metamodel.time_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.time_constraint.TimeConstraintParser;
import org.tzi.use.examplePlugin.util.ParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ROLE_PATH;

public class TimeConstraintType1Parser implements TimeConstraintParser<TimeConstraintType1> {

  @Override
  public TimeConstraintType1 parse(Map<String, Object> astJson) {
    TimeConstraintType1 tc1 = new TimeConstraintType1();

    System.out.println("Parsing ScheduleConstraintType1...");

    // ---- root args ----
    Map<String, Object> args = (Map<String, Object>) astJson.get(ARGS);
    tc1.rolePath = (String) args.get(ROLE_PATH);

    // if parts
    tc1.ifParts = ParserUtil.parseIfPart(astJson);
    System.out.println("Parsed ifParts: " + tc1.ifParts);

    // check for existence parts
    tc1.checkForExi = ParserUtil.parseCheckForExi(args, null);
    System.out.println("Parsed checkForExi: " + tc1.checkForExi);

    return tc1;
  }
}
