package org.tzi.use.examplePlugin.model;

public class CAPLet implements CAPExpr {
  private String name;
  private String type;
  private CAPExpr value;
  private CAPExpr in;

  public String print() {
    return """
        let %s : %s =
          %s
        in
          %s
        """.formatted(name, type, value.print(), in.print());
  }
}
