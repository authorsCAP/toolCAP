package org.tzi.use.examplePlugin.ast;

import org.antlr.runtime.Token;
import org.tzi.use.parser.use.ASTModel;

import java.util.ArrayList;
import java.util.List;

public class CAPModel extends ASTModel {

  private final List<ASTInterface> fCaps;

  public CAPModel(Token name) {
    super(name);
    this.fCaps = new ArrayList<>();
  }

  public void addCAP(ASTInterface cap) {
    fCaps.add(cap);
  }

}
