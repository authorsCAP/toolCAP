package org.tzi.use.examplePlugin.metamodel;

public class OperatorValue {
  public final String operator;
  public final Object value;

  public OperatorValue(String operator, Object value) {
    this.operator = operator;
    this.value = value;
  }

  public String getOperator() {
    return operator;
  }

  public Object getValue() {
    return value;
  }
}