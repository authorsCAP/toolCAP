package org.tzi.use.examplePlugin.metamodel.time_constraint;

import org.tzi.use.examplePlugin.CaculatorEnum;
import org.tzi.use.examplePlugin.ast.ASTInterface;

import java.util.Arrays;
import java.util.List;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.IF_PART;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.WINDOW;
import static org.tzi.use.examplePlugin.util.UseUtils.hasKeyIn;
import static org.tzi.use.examplePlugin.util.UseUtils.hasKeyInCheckForExi;
import static org.tzi.use.examplePlugin.util.UseUtils.hasSpecificKey;

public class TimeConstraintDetector {
  public TimeConstraintType detectType(ASTInterface astInterface) {

    List<String> keys = Arrays.stream(CaculatorEnum.values())
        .map(e -> e.name().toLowerCase())
        .toList();

    if (!hasKeyInCheckForExi(astInterface, keys) && !hasKeyIn(astInterface, keys, IF_PART)) {
      return TimeConstraintType.TYPE1;
    } else if (hasKeyInCheckForExi(astInterface, keys)) {
      // type 2, 3, 5 is the same, so we only need to use 1 parser and generator for both of them
      return TimeConstraintType.TYPE2;
    } else if (hasKeyIn(astInterface, keys, IF_PART)) {
      return TimeConstraintType.TYPE4;
    } else if (hasSpecificKey(astInterface, WINDOW)) {
      return TimeConstraintType.TYPE3;
    }
    // Placeholder implementation
    return TimeConstraintType.UNSUPPORTED;
  }
}
