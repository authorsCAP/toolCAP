package org.tzi.use.examplePlugin.metamodel;

import java.util.List;

public class AttrCondPro {
  public List<String> attrs;
  public List<String> refs;
  public String matchAttr;
  public boolean neg;
  public Type type;
  public String scale;

  // like plus = 21, minus = 45, times = 42, div = 47...
  public OperatorValue operatorAndValue;

  // inside exist check, e.g: exists(e|e.course=c))
  // course is the value @AttrCond(attr="enrolments", attrExists="course", matchAttr="self") }
  public String insideExistValue;

  public enum Type {
    MIN_LIM,
    MAX_LIM,
    FIX_BOOL,
    FIX_NUM,
    FIX_STR,
    MIN_LIM_ATTR,
    MAX_LIM_ATTR,
    MIN,
    MAX,
    MATCH_STR,
    FIX_ENUM,
    MAX_ATTR,
    MIN_ATTR,
    MATCH_ATTR
  }
}
