package org.tzi.use.examplePlugin.util;

import org.tzi.use.examplePlugin.ast.ASTInterface;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintExecutor;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.RetakeConstraintExecutor;
import org.tzi.use.examplePlugin.metamodel.schedule_constraint.ScheduleConstraintExecutor;
import org.tzi.use.examplePlugin.metamodel.size_constraint.SizeConstraintExecutor;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintExecutor;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.SumConstraintExecutor;
import org.tzi.use.examplePlugin.metamodel.time_constraint.TimeConstraintExecutor;
import org.tzi.use.examplePlugin.parser.CAPCompiler;
import org.tzi.use.examplePlugin.use.ASTToJSONConverter;
import org.tzi.use.parser.use.CAPAnnotation;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_FOR_EXI;

public class UseUtils {
  public static ASTInterface parseASTFromText(String text) {
    PrintWriter err = new PrintWriter(System.err);
    try {
      // 1. Create temp file
      Path tempFile = Files.createTempFile("cap-test-", ".cap");

      // 2. Write annotation to file
      Files.writeString(tempFile, text);

      // 3. Parse via existing compiler
      ASTInterface astInterface =
          CAPCompiler.compileSpecification(tempFile.toString(), err);

      // 4. Optional: delete temp file
      Files.deleteIfExists(tempFile);

      return astInterface;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static String asString(Object o) {
    return o == null ? null : String.valueOf(o);
  }

  /**
   * Check for has specific key in args
   *
   * @param astInterface
   * @param key
   * @return
   */
  public static boolean hasSpecificKey(ASTInterface astInterface, String key) {
    return astInterface.args.get(key) != null;
  }

  /**
   * Check if value is number
   *
   * @param value the object value
   * @return
   */
  public static boolean isNumber(Object value) {
    if (value == null) return false;
    try {
      Double.parseDouble(value.toString());
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static boolean hasKeyInCheckForExi(ASTInterface astInterface, List<String> key) {

    Object raw = astInterface.args.get(CHECK_FOR_EXI);

    if (!(raw instanceof List<?> list)) {
      return false;
    }
    for (Object o : list) {
      if (!(o instanceof ASTInterface cond)) {
        continue;
      }
      Map<String, Object> args = cond.args;
      if (args == null) {
        continue;
      }
      for (String k : key) {
        if (args.containsKey(k)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Check for key equals to value in specific conditions, such as ifPart, checkForExi... and whatever conditions that we will have in the future
   * <p>
   * E.g: check for matchAttr equals to "self" in checkForExi condition, check for timeAttr equals to "enrollmentDate" in ifPart condition, etc.
   *
   * @param astInterface
   * @param key
   * @param value
   * @return
   */
  public static boolean hasKeyEqualsToValueInSpecificParam(
      ASTInterface astInterface, String key, String value, String param) {

    Object raw = astInterface.args.get(param);

    if (!(raw instanceof List<?> list)) {
      return false;
    }

    for (Object o : list) {
      if (o instanceof ASTInterface cond) {
        Map<String, Object> args = cond.args;

        if (value.equals(asString(args.get(key)))) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Check if any of the keys in the list exists in the specific conditions
   * Check for key in ifPart, checkForExi... and whatever conditions that we will have in the future
   *
   * @param astInterface
   * @param key
   * @return
   */
  public static boolean hasKeyIn(ASTInterface astInterface, List<String> key, String conditionKey) {

    Object raw = astInterface.args.get(conditionKey);

    if (raw == null) {
      System.out.println("Condition key '" + conditionKey + "' does not exist in args.");
      return false;
    }

    if (!(raw instanceof List<?> list)) {
      return false;
    }
    for (Object o : list) {
      if (!(o instanceof ASTInterface cond)) {
        continue;
      }
      Map<String, Object> args = cond.args;
      if (args == null) {
        continue;
      }
      for (String k : key) {
        if (args.containsKey(k)) {
          return true;
        }
      }
    }
    return false;
  }

  public static boolean hasKeyEqualsToValue(ASTInterface astInterface, String key, Object value) {
    if (!hasSpecificKey(astInterface, key)) {
      return false;
    }

    Object v = astInterface.args.get(key);
    if (v == null || value == null) {
      return false;
    }

    return v.toString().equals(value.toString());
  }

  /**
   * Convert CAPAnnotation to ASTInterface
   * CAPAnnotation is the original format parsed from USE,
   * we convert it to ASTInterface for easier processing in our plugin
   * <p>
   * This is a deep conversion, it will recursively convert nested CAPAnnotation and lists
   *
   * @param capAnnotation
   * @return the ASTInterface representation of the CAPAnnotation, it will have the same structure and data as the original CAPAnnotation, but in a more convenient format for our plugin
   */
  public static ASTInterface mappingToASTInterface(CAPAnnotation capAnnotation) {
    ASTInterface ast = new ASTInterface();
    ast.setName(capAnnotation.getName());

    Map<String, Object> mappedArgs = new LinkedHashMap<>();
    for (Map.Entry<String, Object> e : capAnnotation.getCapArgs().entrySet()) {
      mappedArgs.put(e.getKey(), mapValue(e.getValue()));
    }

    ast.setArgs(mappedArgs);
    return ast;
  }

  private static Object mapValue(Object v) {
    if (v == null ||
        v instanceof String ||
        v instanceof Number ||
        v instanceof Boolean) {
      return v;
    }

    if (v instanceof CAPAnnotation cap) {
      return mappingToASTInterface(cap);
    }

    if (v instanceof List<?> list) {
      List<Object> result = new ArrayList<>();
      for (Object o : list) {
        result.add(mapValue(o));
      }
      return result;
    }

    throw new IllegalArgumentException("Unsupported CAP value: " + v.getClass());
  }


  /**
   * The main executor for constraints, it will dispatch to different executors based on the type of constraint
   * Generate the OCL string for the ASTInterface, the context is the class that the constraint is attached to, and the name is the name of the constraint
   *
   * @param astInterface
   * @param astJson
   * @param context
   * @param name
   * @return
   */
  public static String constraintExecutor(ASTInterface astInterface, Map<String, Object> astJson, String context, String name) {

    String type = astJson.get(CommonAttributes.TYPE).toString();

    // Sum Constraint
    if (type.equalsIgnoreCase(ConstraintType.SUM_CONSTRAINT)) {
      System.out.println("This is a Sum Constraint.");
      return SumConstraintExecutor.execute(
          astInterface,
          ASTToJSONConverter.toJsonObject(astInterface),
          context,
          name
      );
    }

    // Eligibility Constraint
    if (type.equalsIgnoreCase(ConstraintType.ELIGIBILITY_CONSTRAINT)) {
      System.out.println("This is an Eligibility Constraint.");
      return EligibilityConstraintExecutor.execute(
          astInterface,
          ASTToJSONConverter.toJsonObject(astInterface),
          context,
          name
      );
    }

    // Schedule Constraint
    if (type.equalsIgnoreCase(ConstraintType.SCHEDULE_CONSTRAINT)) {
      System.out.println("This is a Schedule Constraint.");
      return ScheduleConstraintExecutor.execute(
          astInterface,
          ASTToJSONConverter.toJsonObject(astInterface),
          context,
          name
      );
    }

    // Size Constraint
    if (type.equalsIgnoreCase(ConstraintType.SIZE_CONSTRAINT)) {
      System.out.println("This is a Size Constraint.");
      return SizeConstraintExecutor.execute(
          astInterface,
          ASTToJSONConverter.toJsonObject(astInterface),
          context,
          name
      );
    }

    // Time Constraint
    if (type.equalsIgnoreCase(ConstraintType.TIME_CONSTRAINT)) {
      System.out.println("This is a Time Constraint.");
      return TimeConstraintExecutor.execute(
          astInterface,
          ASTToJSONConverter.toJsonObject(astInterface),
          context,
          name
      );
    }

    // Status Constraint
    if (type.equalsIgnoreCase(ConstraintType.STATUS_CONSTRAINT)) {
      System.out.println("This is a Status Constraint.");
      return StatusConstraintExecutor.execute(
          astInterface,
          ASTToJSONConverter.toJsonObject(astInterface),
          context,
          name
      );
    }

    // Retake Constraint
    if (type.equalsIgnoreCase(ConstraintType.RETAKE_CONSTRAINT)) {
      System.out.println("This is a Retake Constraint.");
      return RetakeConstraintExecutor.execute(
          astInterface,
          ASTToJSONConverter.toJsonObject(astInterface),
          context,
          name
      );
    }

    return null;
  }
}
