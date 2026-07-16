package org.tzi.use.examplePlugin.metamodel.sum_constraint;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import static org.tzi.use.examplePlugin.util.CommonAttributes.BOUND_CONDITIONS;
import static org.tzi.use.examplePlugin.util.CommonAttributes.COLLECT;
import static org.tzi.use.examplePlugin.util.CommonAttributes.FIX_ATTR;

/**
 * Detects sum constraints in the AST representation of a model.
 *
 * <p> If sum constraints are detected, they can be further processed
 * to generate appropriate OCL constraints or other representations.
 *
 * Case 1: If there is a fixed attribute sum constraint (and no bounds), returns true for EQUALITY constraint type.
 * Case 2: If there are bound attribute sum constraints, returns true for BOUND constraint type.
 * Case 3: If there are multiple collect statements, indicates type 3 4 sum constraints.
 *
 * How to classify:
 *  - EQUALITY: hasFixAttributeSumConstraint == true && hasBoundAttribute == false && hasMoreThanOneCollectStatements == false
 *  - BOUND: hasBoundAttribute == true
 *  - TYPE3_4: hasMoreThanOneCollectStatements == true
 */
public class SumConstraintDetector {

  public SumConstraintType detectType(ASTInterface ast) {
    if (hasBoundAttribute(ast) && hasFixAttributeSumConstraint(ast)) {
      throw new RuntimeException("Both fixed attribute sum constraint and bound attribute sum constraint detected. Invalid state.");
    }
    if (hasFixAttributeSumConstraint(ast) && !hasBoundAttribute(ast)
        && !hasMoreThanOneCollectStatements(ast)) {
      System.out.println("Type 1 sum constraint detected.");
      return SumConstraintType.TYPE1;
    } else if (hasBoundAttribute(ast) && !hasMoreThanOneCollectStatements(ast)) {
      System.out.println("Type 2 sum constraint detected.");
      return SumConstraintType.TYPE2;
    } else if (hasMoreThanOneCollectStatements(ast)) {
      System.out.println("Type 3/4 sum constraint detected.");
      return SumConstraintType.TYPE3_4;
    }
    return SumConstraintType.UNSUPPORTED;
  }

  // if there is a fixed attribute sum constraint in the model
  // if true, detected as EQUALITY constraint type
  // return true, else false
  public boolean hasFixAttributeSumConstraint(ASTInterface ast) {
    return ast.args.get(FIX_ATTR) != null;
  }

  // if there is a bound attribute sum constraint in the model
  // if true, detected as BOUND constraint type
  public boolean hasBoundAttribute(ASTInterface astInterface) {
    return BOUND_CONDITIONS.stream()
        .anyMatch(condition -> astInterface.args.get(condition) != null);
  }

  public boolean hasMoreThanOneCollectStatements(ASTInterface astInterface) {
    Object collect = astInterface.args.get(COLLECT);

    int count = 0;
    if (collect instanceof Iterable<?>) {
      for (Object o : (Iterable<?>) collect) {
        count++;
        if (count > 1) {
          return true;
        }
      }
    }
    return false;
  }
}
