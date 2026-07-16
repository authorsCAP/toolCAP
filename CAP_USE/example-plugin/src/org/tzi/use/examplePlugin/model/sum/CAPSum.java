package org.tzi.use.examplePlugin.model.sum;

import org.tzi.use.examplePlugin.model.CAPExpr;

public class CAPSum implements CAPExpr {
  private CAPExpr source;
  @Override
  public String print() {
    return source.print() + "->sum()";
  }
}
