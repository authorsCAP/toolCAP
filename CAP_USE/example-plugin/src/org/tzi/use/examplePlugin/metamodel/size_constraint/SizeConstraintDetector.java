package org.tzi.use.examplePlugin.metamodel.size_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

public class SizeConstraintDetector {
  /**
   * Currently, we have 5 types of size constraints.
   * However, the type 1 generator and parser are made abstract enough to use for both type 1 to 4
   *
   * Type 5 is still under development as we need to confirm the requirements.
   * @param astInterface
   * @return
   */
  public SizeConstraintType detectType(ASTInterface astInterface) {
    if (true) {
      return SizeConstraintType.TYPE1;
    }
    // Placeholder implementation
    return SizeConstraintType.UNSUPPORTED;
  }
}
