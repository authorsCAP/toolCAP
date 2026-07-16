package org.tzi.use.examplePlugin.metamodel.size_constraint;

import org.tzi.use.examplePlugin.metamodel.OperatorEnum;

public class Bound {
  OperatorEnum type;
  Object value;

  public Bound(OperatorEnum boundType, String string) {
    this.type = boundType;
    this.value = string;
  }

  public OperatorEnum getType() {
    return type;
  }

  public void setType(OperatorEnum type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }
}
