package org.tzi.use.examplePlugin.metamodel.schedule_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ALT_PART;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_FOR_EXI;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CONFLICT_CHECK;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CROSS_REFERENCE;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.INTERSECTION_OP;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MAX;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.WINDOW;
import static org.tzi.use.examplePlugin.util.UseUtils.hasSpecificKey;

public class ScheduleConstraintDetector {
  public ScheduleConstraintType detectType(ASTInterface astInterface) {
    if (hasSpecificKey(astInterface, CROSS_REFERENCE)
        && hasSpecificKey(astInterface, CONFLICT_CHECK)
        && hasSpecificKey(astInterface, INTERSECTION_OP)
        && hasSpecificKey(astInterface, MAX)) {
      return ScheduleConstraintType.TYPE1;
    } else if (hasSpecificKey(astInterface, CHECK_FOR_EXI) && hasSpecificKey(astInterface, ALT_PART)) {
      return ScheduleConstraintType.TYPE2;
    } else if (hasSpecificKey(astInterface, WINDOW)) {
      return ScheduleConstraintType.TYPE3;
    }
    // Placeholder implementation
    return ScheduleConstraintType.UNSUPPORTED;
  }
}
