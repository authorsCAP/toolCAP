package org.tzi.use.examplePlugin.gui.parser;

import org.tzi.use.examplePlugin.ast.ASTInterface;

public class ConstraintKindDetector {

  public static ConstraintKind detect(String type) {
    return switch (type) {
      case "TimeConstraint" -> ConstraintKind.TIME;
      case "SumConstraint" -> ConstraintKind.SUM;
      case "ScheduleConstraint" -> ConstraintKind.SCHEDULE;
      case "SizeConstraint" -> ConstraintKind.SIZE;
      case "EligibilityConstraint" -> ConstraintKind.ELIGIBILITY;
      case "StatusConstraint" -> ConstraintKind.STATUS;
      case "RetakeConstraint" -> ConstraintKind.RETAKE;

      default -> ConstraintKind.UNKNOWN;
    };
  }
}
