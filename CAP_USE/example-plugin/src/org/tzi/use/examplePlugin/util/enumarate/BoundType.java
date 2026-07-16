package org.tzi.use.examplePlugin.util.enumarate;

public enum BoundType {
  MAX("max"),
  MIN("min"),
  MAX_ATTR("maxAttr"),
  MIN_ATTR("minAttr"),
  MAX_LIM("maxLim"),
  MIN_LIM("minLim"),
  MAX_LIM_ATTR("maxLimAttr"),
  MIN_LIM_ATTR("minLimAttr"),
  EQUALS("fixAttr"),;

  public String getKey() {
    return key;
  }

  private final String key;
  BoundType(String key) {
    this.key = key;
  }
}