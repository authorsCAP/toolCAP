package org.tzi.use.examplePlugin.metamodel;

import java.util.List;

public enum OperatorEnum {

  // operator for value comparison only
  // e.g: 3, 4, 5.6, "abc",...
  MAX("max", "<"),
  MIN("min", ">"),
  MAX_LIMIT("maxLim", "<="),
  MIN_LIMIT("minLim", ">="),
  MAX_ATTR("maxAttr", "<"),
  MIN_ATTR("minAttr", ">"),
  MAX_LIM_ATTR("maxLimAttr", "<="),
  MIN_LIM_ATTR("minLimAttr", ">="),
  EQUALS("fixAttr", "="),;



  public final String name;
  public final String symbol;

  OperatorEnum(String name, String symbol) {
    this.name = name;
    this.symbol = symbol;
  }

  public static final List<OperatorEnum> OPERATORS = List.of(values());


  public String getName() {
    return name;
  }

  public String getSymbol() {
    return symbol;
  }
}
