package org.tzi.use.examplePlugin.metamodel.retake_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_FOR_EXI;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MATCH_ATTR;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.MAX;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.SUM_ATTR;
import static org.tzi.use.examplePlugin.util.UseUtils.hasKeyEqualsToValue;
import static org.tzi.use.examplePlugin.util.UseUtils.hasKeyEqualsToValueInSpecificParam;

public class RetakeConstraintDetector {

  public RetakeConstraintType detectType(ASTInterface astInterface) {

    if (hasKeyEqualsToValue(astInterface, SUM_ATTR, "1")
        && hasKeyEqualsToValue(astInterface, MAX, 0)) {
      // if sumAttr == "1" and max == 0, then it's type 1
      return RetakeConstraintType.TYPE1;
    } else if (hasKeyEqualsToValueInSpecificParam(astInterface, MATCH_ATTR, "self", CHECK_FOR_EXI)) {
      // if matchAttr == "self" in CHECK_FOR_EXI, then it's type 2
      return RetakeConstraintType.TYPE2;
    } else {
      return RetakeConstraintType.TYPE3;
    }
    // Placeholder implementation
//    return RetakeConstraintType.UNSUPPORTED;
  }
}
