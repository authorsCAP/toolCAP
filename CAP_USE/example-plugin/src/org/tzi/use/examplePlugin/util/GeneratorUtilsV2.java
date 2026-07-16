package org.tzi.use.examplePlugin.util;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.OperatorValue;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.RootScope;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for code generation, especially for generating condition strings based on IfPart.
 * This class is created only to handle the new logic for RetakeConstraint, and we want to keep the original GeneratorUtils clean and unchanged for now.
 */

public class GeneratorUtilsV2 {
  /**
   * If parts to condition string
   * <p>
   * In this case, accept an ifParts list and a root prefix, and generate a condition string with reference.
   * <p>
   * E.g: @AttrCond(attr="failedAttempts", min=2)  --> the passed param is
   * <p>
   * ifParts, rootPrefix = "self", referenceClass = c
   * <p>
   * generate condition string: self.failedAttempts(c) >= 2
   *
   * @param ifParts
   * @return
   */
  public static String buildIfConditionWithReference(List<IfPart> ifParts, String rootPrefix, String referenceClass) {

    if (ifParts == null || ifParts.isEmpty()) {
      return null;
    }

    String resolvedRoot =
        (rootPrefix == null || rootPrefix.isEmpty())
            ? "self"
            : rootPrefix;

    return ifParts.stream()
        .map(c -> {
          String cond;

          // CASE 1: if refs is specified, use refs to build the condition
          if (/*c.refs != null && !c.refs.isEmpty() &&*/ c.operatorAndValue != null) {
            cond = parseByRefs(c, resolvedRoot, referenceClass);
          }
          // CASE 2: default parse
          else {
            cond = parseByFixType(c, resolvedRoot, referenceClass);
          }
          return c.negated ? "not (" + cond + ")" : cond;
        })
        .collect(Collectors.joining(" and "));
  }

  private static String parseByRefs(IfPart c, String root, String referenceClass) {
    System.out.println("Parsing by refs for IfPart: " + c.ifFixValue + " with root: " + root);
    System.out.println("Calsize: " + c.calSize);

    // build left hand side path: self.course.credits or e.course.credits
    String left = String.join(".", root, c.ifAttr);

    // calSize
    if (c.calSize != null && c.calSize) {
      left = left + "->size()";
    }

    // reference class
    if (referenceClass != null && !referenceClass.isEmpty()) {
      left = left + "(" + referenceClass + ")";
    }

    // build right hand side path
    // String right = root + "." + String.join(".", c.refs);
    String right = root;

    if (c.refs != null && !c.refs.isEmpty()) {
      right = right + "." + String.join(".", c.refs);
    }
    right = right + (c.ifFixValue.isEmpty() ? "" : ("." + c.ifFixValue));

    if (c.operatorAndValue != null) {
      right = right + " " + renderOperatorValue(c.operatorAndValue);
    }

    String cond;
    switch (c.ifFixType) {
      case MIN_LIM, MIN_LIM_ATTR, MIN_VALUE, MIN -> cond = left + " > " + right;

      case MAX_LIM, MAX_LIM_ATTR, MAX_VALUE, MAX -> cond = left + " < " + right;

      case MATCH_ATTR, FIX_ATTR -> cond = left + " = " + right;

      case FIX_BOOL -> cond = Boolean.parseBoolean(c.ifFixValue)
          ? left
          : "not " + left;

      case FIX_STR, FIX_ENUM -> cond = left + " = '" + c.ifFixValue + "'";

      default -> throw new RuntimeException("Unsupported AttrCondPro type: " + c.ifFixType);
    }

    return c.negated ? "not (" + cond + ")" : cond;
  }


  private static String parseByFixType(IfPart c, String root, String referenceClass) {
    System.out.println("Parsing by fix type for IfPart: " + c.ifFixValue + " with root: " + root);
    System.out.println("ifAttr: " + c.ifAttr + ", ifFixType: " + c.ifFixType + ", ifFixValue: " + c.ifFixValue);
    System.out.println("Calsize: " + c.calSize);

    // calSize
    if (c.calSize != null && c.calSize) {
      root = root + "." + c.ifAttr + "->size()";
    } else if (referenceClass != null && !referenceClass.isEmpty()) {
      root = root + "." + c.ifAttr + "(" + referenceClass + ")";

    } else {
      root = root + "." + c.ifAttr;
    }

    return switch (c.ifFixType) {
      case FIX_NUM, FIX_BOOL, FIX_ATTR -> root + " = " + c.ifFixValue;

      case FIX_STR, FIX_ENUM -> root + " = '" + c.ifFixValue + "'";

      case MAX_LIM, MAX, MAX_VALUE -> root + " <= " + c.ifFixValue;

      case MIN_LIM, MIN, MIN_VALUE -> root + " >= " + c.ifFixValue;

      default -> throw new RuntimeException("Invalid ifPart");
    };
  }

  /**
   * Normalize operator
   */
  private static String renderOperatorValue(OperatorValue ov) {
    String operator = ov.getOperator();
    Object value = ov.getValue();

    if (value instanceof Number) {
      double v = ((Number) value).doubleValue();

      if (v < 0) {
        operator = invertOperator(operator);
        v = Math.abs(v);

        if (value instanceof Integer || value instanceof Long) {
          return operator + " " + (long) v;
        }
        return operator + " " + v;
      }
    }

    return operator + " " + value;
  }

  private static String invertOperator(String operator) {
    return switch (operator) {
      case "+" -> "-";
      case "-" -> "+";
      default -> operator;
    };
  }

  /**
   * Build exists check condition for a collection based on the given conditions.
   * @return
   */
  public static String buildExistsCheckConditionForRetake(
      String rolePath,
      AttrCondPro cond,
      String rootReferenceClass,
      String referenceClass
  ) {
    if (cond == null) {
      return "true";
    }

    String left = rolePath + "." + String.join(".", cond.attrs);

    String right = referenceClass + "|" + referenceClass + "." + cond.insideExistValue + "=" + rootReferenceClass;

    return left + "->exists(" + right + ")";
  }
}
