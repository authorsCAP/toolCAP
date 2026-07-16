package org.tzi.use.examplePlugin.util;

import org.tzi.use.examplePlugin.CaculatorEnum;
import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.OperatorValue;
import org.tzi.use.examplePlugin.util.enumarate.IfFixType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ARGS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.ATTR_EXISTS;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.CHECK_FOR_EXI;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.RATIO;
import static org.tzi.use.examplePlugin.metamodel.CommonAttributes.SCALE;
import static org.tzi.use.examplePlugin.util.CommonAttributes.FIX_ATTR;
import static org.tzi.use.examplePlugin.util.CommonAttributes.FIX_BOOL;
import static org.tzi.use.examplePlugin.util.CommonAttributes.FIX_ENUM;
import static org.tzi.use.examplePlugin.util.CommonAttributes.FIX_NUM;
import static org.tzi.use.examplePlugin.util.CommonAttributes.FIX_STR;
import static org.tzi.use.examplePlugin.util.CommonAttributes.MATCH_ATTR;
import static org.tzi.use.examplePlugin.util.CommonAttributes.MAX_ATTR;
import static org.tzi.use.examplePlugin.util.CommonAttributes.MAX_LIM;
import static org.tzi.use.examplePlugin.util.CommonAttributes.MAX_LIM_ATTR;
import static org.tzi.use.examplePlugin.util.CommonAttributes.MAX_VALUE;
import static org.tzi.use.examplePlugin.util.CommonAttributes.MIN_LIM;
import static org.tzi.use.examplePlugin.util.CommonAttributes.MIN_LIM_ATTR;
import static org.tzi.use.examplePlugin.util.CommonAttributes.MIN_VALUE;
import static org.tzi.use.examplePlugin.util.UseUtils.asString;

public class ParserUtil {
  public static List<IfPart> parseIfPart(Map<String, Object> astJson) {

    List<IfPart> result = new ArrayList<>();

    Map<String, Object> args =
        (Map<String, Object>) astJson.get(ARGS);

    System.out.println();

    if (args == null || !args.containsKey("ifPart")) {
      System.out.println("No ifPart found in AST JSON.");
      return List.of();
    }

    System.out.println("Parsing ifPart from args: " + args);

    List<Map<String, Object>> ifParts =
        (List<Map<String, Object>>) args.get("ifPart");

    System.out.println("Found ifParts: " + ifParts);

    for (Map<String, Object> cond : ifParts) {

      Map<String, Object> condArgs =
          (Map<String, Object>) cond.get(ARGS);

      IfPart ip = new IfPart();

      // attrs
      List<String> attrs = condArgs.entrySet().stream()
          .filter(e -> e.getKey().startsWith("attr"))
          .sorted(Map.Entry.comparingByKey())
          .map(e -> asString(e.getValue()))
          .toList();

      if (!attrs.isEmpty()) {
        ip.ifAttr = String.join(".", attrs);
      }

      // refs
      List<String> refs = condArgs.entrySet().stream()
          .filter(e -> e.getKey().startsWith("ref"))
          .sorted(Map.Entry.comparingByKey())
          .map(e -> asString(e.getValue()))
          .toList();

      if (!refs.isEmpty()) {
        ip.refs = String.join(".", refs);
      }

      if (condArgs.containsKey(FIX_ATTR) || condArgs.containsKey(MATCH_ATTR)) {
        ip.ifFixType = IfFixType.FIX_ATTR;
        ip.ifFixValue = asString(condArgs.get(FIX_ATTR) != null ? condArgs.get(FIX_ATTR) : condArgs.get(MATCH_ATTR));
      } else if (condArgs.containsKey(FIX_NUM)) {
        ip.ifFixType = IfFixType.FIX_NUM;
        ip.ifFixValue = asString(condArgs.get(FIX_NUM));
      } else if (condArgs.containsKey(FIX_BOOL)) {
        ip.ifFixType = IfFixType.FIX_BOOL;
        ip.ifFixValue = asString(condArgs.get(FIX_BOOL));
      } else if (condArgs.containsKey(FIX_STR)) {
        ip.ifFixType = IfFixType.FIX_STR;
        ip.ifFixValue = asString(condArgs.get(FIX_STR));
      } else if (condArgs.containsKey(FIX_ENUM)) {
        ip.ifFixType = IfFixType.FIX_ENUM;
        ip.ifFixValue = asString(condArgs.get(FIX_ENUM));
      } else if (condArgs.containsKey(MAX_LIM)) {
        ip.ifFixType = IfFixType.MAX_VALUE;
        ip.ifFixValue = asString(condArgs.get(MAX_LIM));
      } else if (condArgs.containsKey(MIN_LIM)) {
        ip.ifFixType = IfFixType.MIN_VALUE;
        ip.ifFixValue = asString(condArgs.get(MIN_LIM));
      } else if (condArgs.containsKey(MAX_VALUE)) {
        ip.ifFixType = IfFixType.MAX_LIM;
        ip.ifFixValue = asString(condArgs.get(MAX_VALUE));
      } else if (condArgs.containsKey(MIN_VALUE)) {
        ip.ifFixType = IfFixType.MIN_LIM;
        ip.ifFixValue = asString(condArgs.get(MIN_VALUE));
      } else if (condArgs.containsKey(MAX_LIM_ATTR)) {
        ip.ifFixType = IfFixType.MAX_LIM_ATTR;
        ip.ifFixValue = asString(condArgs.get(MAX_LIM_ATTR));
      } else if (condArgs.containsKey(MIN_LIM_ATTR)) {
        ip.ifFixType = IfFixType.MIN_LIM_ATTR;
        ip.ifFixValue = asString(condArgs.get(MIN_LIM_ATTR));
      } else if (condArgs.containsKey("min")) {
        ip.ifFixType = IfFixType.MIN;
        ip.ifFixValue = asString(condArgs.get("min"));
      } else if (condArgs.containsKey("max")) {
        ip.ifFixType = IfFixType.MAX;
        ip.ifFixValue = asString(condArgs.get("max"));
      }

      // parsing plus=21 or minus=45 or times=42 or div=47...
      Arrays.stream(CaculatorEnum.values())
          .filter(e -> condArgs.containsKey(e.name))
          .findFirst()
          .ifPresent(e ->
              ip.operatorAndValue =
                  new OperatorValue(e.symbol, condArgs.get(e.name))
          );

      // negated
      if (condArgs.containsKey("negated")) {
        ip.negated = Boolean.TRUE.equals(condArgs.get("negated"));
      }

      // calSize
      ip.calSize = false;

      if (condArgs.containsKey("calSize")) {
        Object v = condArgs.get("calSize");
        ip.calSize = Boolean.parseBoolean(String.valueOf(v));
      }

      result.add(ip);
    }

    return result;
  }

  /**
   * Parse attributes from condition arguments. It looks for keys like "attr", "attr2", "attr3", etc. and collects their values in order.
   * @param condArgs
   * @return
   */
  public static List<String> parseAttrsFromCondArgs(Map<String, Object> condArgs) {
    System.out.println("Parsing attrs from condArgs: " + condArgs);
    List<String> attrs = new ArrayList<>();

    if (condArgs.containsKey("attr")) {
      attrs.add(asString(condArgs.get("attr")));
    }

    int i = 2;
    while (condArgs.containsKey("attr" + i)) {
      attrs.add(asString(condArgs.get("attr" + i)));
      i++;
    }

    return attrs;
  }

  /**
   * Parse references from condition arguments. It looks for keys like "ref", "ref2", "ref3", etc. and collects their values in order.
   * @param condArgs
   * @return
   */
  public static List<String> parseRefsFromCondArgs(Map<String, Object> condArgs) {
    System.out.println("Parsing refs from condArgs: " + condArgs);
    List<String> refs = new ArrayList<>();

    if (condArgs.containsKey("ref")) {
      refs.add(asString(condArgs.get("ref")));
    }

    int i = 2;
    while (condArgs.containsKey("ref" + i)) {
      refs.add(asString(condArgs.get("ref" + i)));
      i++;
    }

    return refs;
  }

  /**
   * Parse checkForExi conditions from the given arguments. It looks for a list of conditions under the specified key (defaulting to CHECK_FOR_EXI) and converts each condition into an AttrCondPro object.
   * @param args
   * @param key by default, it looks for CHECK_FOR_EXI, but it can be customized to look for other keys that have the same structure.
   * @return
   */
  public static List<AttrCondPro> parseCheckForExi(
      Map<String, Object> args, String key
  ) {

    for (Map.Entry<String, Object> entry : args.entrySet()) {
      System.out.println("Checking key in args: " + entry.getKey());
    }

    List<Map<String, Object>> checkForExi =
        (List<Map<String, Object>>) args.get(key == null ? CHECK_FOR_EXI : key);

    if (checkForExi == null || checkForExi.isEmpty()) return null;

    List<AttrCondPro> attrConds = new ArrayList<>();

    for (Map<String, Object> attrCond : checkForExi) {

      Map<String, Object> condArgs =
          (Map<String, Object>) attrCond.get(ARGS);

      System.out.println("Parsing collect condition: " + condArgs);

      AttrCondPro c = new AttrCondPro();
      c.attrs = ParserUtil.parseAttrsFromCondArgs(condArgs);
      c.refs = ParserUtil.parseRefsFromCondArgs(condArgs);
      c.neg = Boolean.TRUE.equals(condArgs.get("neg"));
      c.insideExistValue = asString(condArgs.get(ATTR_EXISTS));

      // scale or ratio
      Object scale = condArgs.get(SCALE);
      Object ratio = condArgs.get(RATIO);

      c.scale = scale != null
          ? scale.toString()
          : (ratio != null ? ratio.toString() : null);

      // parsing plus=21 or minus=45 or times=42 or div=47...
      System.out.println("Looking for calculator operator in condArgs: " + condArgs);
      Arrays.stream(CaculatorEnum.values())
          .filter(e -> condArgs.containsKey(e.name))
          .findFirst()
          .ifPresent(e ->
              c.operatorAndValue =
                  new OperatorValue(e.symbol, condArgs.get(e.name))
          );

      if (condArgs.containsKey("minLim")) {
        c.type = AttrCondPro.Type.MIN_LIM;
        c.matchAttr = String.valueOf(condArgs.get("minLim"));
      } else if (condArgs.containsKey("maxLim")) {
        c.type = AttrCondPro.Type.MAX_LIM;
        c.matchAttr = String.valueOf(condArgs.get("maxLim"));
      } else if (condArgs.containsKey("fixBool")) {
        c.type = AttrCondPro.Type.FIX_BOOL;
        c.matchAttr = String.valueOf(condArgs.get("fixBool"));
      } else if (condArgs.containsKey("fixNum")) {
        c.type = AttrCondPro.Type.FIX_NUM;
        c.matchAttr = String.valueOf(condArgs.get("fixNum"));
      } else if (condArgs.containsKey("minLimAttr")) {
        c.type = AttrCondPro.Type.MIN_LIM_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("minLimAttr"));
      } else if (condArgs.containsKey("maxLimAttr")) {
        c.type = AttrCondPro.Type.MAX_LIM_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("maxLimAttr"));
      } else if (condArgs.containsKey("min")) {
        c.type = AttrCondPro.Type.MIN;
        c.matchAttr = String.valueOf(condArgs.get("min"));
      } else if (condArgs.containsKey("max")) {
        c.type = AttrCondPro.Type.MAX;
        c.matchAttr = String.valueOf(condArgs.get("max"));
      } else if (condArgs.containsKey("maxAttr")) {
        c.type = AttrCondPro.Type.MAX_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("maxAttr"));
      } else if (condArgs.containsKey("minAttr")) {
        c.type = AttrCondPro.Type.MIN_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("minAttr"));
      } else if (condArgs.containsKey("fixStr")) {
        c.type = AttrCondPro.Type.FIX_STR;
        c.matchAttr = String.valueOf(condArgs.get("fixStr"));
      } else if (condArgs.containsKey("matchAttr")) {
        c.type = AttrCondPro.Type.MATCH_ATTR;
        c.matchAttr = String.valueOf(condArgs.get("matchAttr"));
      }

      attrConds.add(c);
    }

    return attrConds;
  }
}
