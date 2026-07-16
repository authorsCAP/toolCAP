package org.tzi.use.examplePlugin.metamodel.status_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import java.util.List;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_STATUS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.IF_PART;
import static org.tzi.use.examplePlugin.util.CommonAttributes.COLLECT;
import static org.tzi.use.examplePlugin.util.UseUtils.hasKeyIn;
import static org.tzi.use.examplePlugin.util.UseUtils.hasSpecificKey;

public class StatusConstraintDetector {
  private static final String CAL_SIZE = "calSize";
  /**
   * Currently, we have 5 types of size constraints.
   * However, the type 1 generator and parser are made abstract enough to use for both type 1 and 2
   *
   * Type 5 is still under development as we need to confirm the requirements.
   * @param astInterface
   * @return
   */
  public StatusConstraintType detectType(ASTInterface astInterface) {

    if (!hasSpecificKey(astInterface, COLLECT) && !hasKeyIn(astInterface, List.of(CAL_SIZE), IF_PART)) {
      return StatusConstraintType.TYPE1;
    } else if (hasSpecificKey(astInterface, COLLECT)) {
      return StatusConstraintType.TYPE3;
    } else if (hasKeyIn(astInterface, List.of(CAL_SIZE), IF_PART)) {
      return StatusConstraintType.TYPE5;
    }
    // Placeholder implementation
    return StatusConstraintType.UNSUPPORTED;
  }
}
