package org.tzi.use.examplePlugin.ast;

import java.util.LinkedHashMap;
import java.util.Map;

public class ASTInterface {
  public String name;
  public Map<String, Object> args = new LinkedHashMap<>();


  public void setName(String name) {
    this.name = name;
  }

  public void setArgs(Map<String, Object> args) {
    this.args = args;
  }
}
