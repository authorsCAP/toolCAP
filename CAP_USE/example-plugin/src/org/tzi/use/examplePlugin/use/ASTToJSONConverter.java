package org.tzi.use.examplePlugin.use;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ASTToJSONConverter {

  public static Map<String, Object> toJsonObject(ASTInterface ast) {
    Map<String, Object> json = new LinkedHashMap<>();

    json.put("type", ast.name);

    Map<String, Object> args = new LinkedHashMap<>();
    for (Map.Entry<String, Object> e : ast.args.entrySet()) {
      args.put(e.getKey(), convertValue(e.getValue()));
    }

    json.put("args", args);
    return json;
  }

  private static Object convertValue(Object v) {
    if (v == null) {
      return null;
    }

    if (v instanceof String ||
        v instanceof Number ||
        v instanceof Boolean) {
      return v;
    }

    if (v instanceof ASTInterface) {
      return toJsonObject((ASTInterface) v);
    }

    if (v instanceof List<?>) {
      List<Object> list = new ArrayList<>();
      for (Object o : (List<?>) v) {
        list.add(convertValue(o));
      }
      return list;
    }

    throw new IllegalArgumentException("Unsupported AST value: " + v.getClass());
  }
}

