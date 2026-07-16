package org.tzi.use.examplePlugin.use;

import org.tzi.use.examplePlugin.enumarate.Phase;
import org.tzi.use.uml.mm.MAttribute;
import org.tzi.use.uml.ocl.expr.*;

import java.util.List;

public class CAPVisitor implements ExpressionVisitor {

  private static final List<String> OPERATION_LIST = List.of("<=", ">=", ">", "<", "=");

  private Phase currentPhase = Phase.ROOT;
  @Override
  public void visitAllInstances(ExpAllInstances exp) {

  }

  @Override
  public void visitAny(ExpAny exp) {
    System.out.println("Visiting ExpAny node");
  }

  @Override
  public void visitAsType(ExpAsType exp) {
    System.out.println("Visiting ExpAsType node");
  }

  @Override
  public void visitAttrOp(ExpAttrOp exp) {
    MAttribute attr = exp.attr();
    Expression source = exp.objExp();

    System.out.println("  Attr: " + attr.name());
    System.out.println("  Source exp: " + source.getClass().getSimpleName());
    System.out.println("  Attr owner:" + attr.owner().name());
    System.out.println("  Attr type:" + attr.type().toString());

    if (currentPhase == Phase.COLLECT) {
      System.out.println("  [COLLECT phase] Processing attribute in COLLECT phase." + attr.name());
      extractRolePath(source);
    }
    source.processWithVisitor(this);
  }
  private void extractRolePath(Expression exp) {
    if (exp instanceof ExpNavigation) {
      ExpNavigation nav = (ExpNavigation) exp;
      extractRolePath((Expression) nav.getSource());
      System.out.println("  Role: " + nav.getDestination().nameAsRolename());
    }
  }

  @Override
  public void visitBagLiteral(ExpBagLiteral exp) {
    System.out.println("Visiting ExpBagLiteral node");
  }

  @Override
  public void visitCollect(ExpCollect exp) {
    System.out.println("Visiting ExpCollect node");
    System.out.println("  Collect result type: " + exp.type().toString());
    System.out.println("  Visiting fElemVarDecls:" + exp.getVariableDeclarations());
    exp.getVariableDeclarations().processWithVisitor(this);

    System.out.println("  Visiting range expression:" + exp.getRangeExpression());
    exp.getRangeExpression().processWithVisitor(this);

    System.out.println("  Visiting query expression:" + exp.getQueryExpression());
    currentPhase = Phase.COLLECT;
    exp.getQueryExpression().processWithVisitor(this);
  }

  @Override
  public void visitCollectNested(ExpCollectNested exp) {
    System.out.println("Visiting ExpCollectNested node");
  }

  @Override
  public void visitConstBoolean(ExpConstBoolean exp) {
    System.out.println("Visiting ExpConstBoolean node");
  }

  @Override
  public void visitConstEnum(ExpConstEnum exp) {
    System.out.println("Visiting ExpConstEnum node");
    System.out.println("  Enum literal: " + exp.value());
    System.out.println("  Enum type: " + exp.type().toString());
  }

  @Override
  public void visitConstInteger(ExpConstInteger exp) {
    System.out.println("Visiting ExpConstInteger node");
  }

  @Override
  public void visitConstReal(ExpConstReal exp) {
    System.out.println("Visiting ExpConstReal node");
  }

  @Override
  public void visitConstString(ExpConstString exp) {
    System.out.println("Visiting ExpConstString node");
  }

  @Override
  public void visitEmptyCollection(ExpEmptyCollection exp) {
    System.out.println("Visiting ExpEmptyCollection node");
  }

  @Override
  public void visitExists(ExpExists exp) {
    System.out.println("Visiting ExpExists node");
  }

  @Override
  public void visitForAll(ExpForAll exp) {
    System.out.println("Visiting ExpForAll node");
  }

  @Override
  public void visitIf(ExpIf exp) {
    System.out.println("Visiting ExpIf node");
  }

  @Override
  public void visitIsKindOf(ExpIsKindOf exp) {
    System.out.println("Visiting ExpIsKindOf node");
  }

  @Override
  public void visitIsTypeOf(ExpIsTypeOf exp) {
    System.out.println("Visiting ExpIsTypeOf node");
  }

  @Override
  public void visitIsUnique(ExpIsUnique exp) {
    System.out.println("Visiting ExpIsUnique node");
  }

  @Override
  public void visitIterate(ExpIterate exp) {
    System.out.println("Visiting ExpIterate node");
  }

  @Override
  public void visitLet(ExpLet exp) {
    System.out.println("Visiting ExpLet node");
  }

  @Override
  public void visitNavigation(ExpNavigation exp) {
    System.out.println("Visiting ExpNavigation node");
  }

  @Override
  public void visitObjAsSet(ExpObjAsSet exp) {
    System.out.println("Visiting ExpObjAsSet node");
  }

  @Override
  public void visitObjOp(ExpObjOp exp) {
    System.out.println("Visiting ExpObjOp node");
  }

  @Override
  public void visitInstanceConstructor(ExpInstanceConstructor exp) {
    System.out.println("Visiting ExpInstanceConstructor node");
  }

  @Override
  public void visitObjRef(ExpObjRef exp) {
    System.out.println("Visiting ExpObjRef node");
  }

  @Override
  public void visitOne(ExpOne exp) {
    System.out.println("Visiting ExpOne node");
  }

  @Override
  public void visitOrderedSetLiteral(ExpOrderedSetLiteral exp) {
    System.out.println("Visiting ExpOrderedSetLiteral node");
  }

  @Override
  public void visitQuery(ExpQuery exp) {
    System.out.println("Visiting ExpQuery node");
  }

  @Override
  public void visitReject(ExpReject exp) {
    System.out.println("Visiting ExpReject node");
  }

  @Override
  public void visitWithValue(ExpressionWithValue exp) {
    System.out.println("Visiting ExpressionWithValue node");
  }

  @Override
  public void visitSelect(ExpSelect exp) {
    System.out.println("Visiting ExpSelect node");
    currentPhase = Phase.SELECT;
    exp.getQueryExpression().processWithVisitor(this);
  }

  @Override
  public void visitSequenceLiteral(ExpSequenceLiteral exp) {
    System.out.println("Visiting ExpSequenceLiteral node");
  }

  @Override
  public void visitSetLiteral(ExpSetLiteral exp) {
    System.out.println("Visiting ExpSetLiteral node");
  }

  @Override
  public void visitSortedBy(ExpSortedBy exp) {
    System.out.println("Visiting ExpSortedBy node");
  }

  @Override
  public void visitStdOp(ExpStdOp exp) {
    System.out.println("Visiting ExpStdOp node");
//    System.out.println("Operation: " + exp.opname());
//
//    for (Expression expression : exp.args()) {
//      System.out.println("  Visiting argument of ExpStdOp:");
//      expression.processWithVisitor(this);
//    }
    String op = exp.opname();

    if (op.equals("implies")) {
      exp.args()[0].processWithVisitor(this);
      currentPhase = Phase.LHS;
      exp.args()[1].processWithVisitor(this);
    }

    else if (OPERATION_LIST.contains(op)) {
      currentPhase = Phase.LHS;
      exp.args()[0].processWithVisitor(this);

      currentPhase = Phase.RHS;
      exp.args()[1].processWithVisitor(this);
    }

    else if (op.equals("sum")) {
      currentPhase = Phase.SUM;
      exp.args()[0].processWithVisitor(this);
    }
  }

  @Override
  public void visitTupleLiteral(ExpTupleLiteral exp) {
    System.out.println("Visiting ExpTupleLiteral node");
  }

  @Override
  public void visitTupleSelectOp(ExpTupleSelectOp exp) {
    System.out.println("Visiting ExpTupleSelectOp node");
  }

  @Override
  public void visitUndefined(ExpUndefined exp) {
    System.out.println("Visiting ExpUndefined node");
  }

  @Override
  public void visitVariable(ExpVariable exp) {
    System.out.println("Visiting ExpVariable node");
  }

  @Override
  public void visitClosure(ExpClosure exp) {
    System.out.println("Visiting ExpClosure node");
  }

  @Override
  public void visitOclInState(ExpOclInState exp) {
    System.out.println("Visiting ExpOclInState node");
  }

  @Override
  public void visitVarDeclList(VarDeclList varDeclList) {
    System.out.println("Visiting VarDeclList node");
  }

  @Override
  public void visitVarDecl(VarDecl varDecl) {
    System.out.println("Visiting VarDecl node");
  }

  @Override
  public void visitObjectByUseId(ExpObjectByUseId exp) {
    System.out.println("Visiting ExpObjectByUseId node");
  }

  @Override
  public void visitConstUnlimitedNatural(ExpConstUnlimitedNatural exp) {
    System.out.println("Visiting ExpConstUnlimitedNatural node");
  }

  @Override
  public void visitSelectByKind(ExpSelectByKind exp) {
    System.out.println("Visiting ExpSelectByKind node");
  }

  @Override
  public void visitExpSelectByType(ExpSelectByType exp) {
    System.out.println("Visiting ExpSelectByType node");
  }

  @Override
  public void visitRange(ExpRange exp) {
    System.out.println("Visiting ExpRange node");
  }

  @Override
  public void visitNavigationClassifierSource(ExpNavigationClassifierSource exp) {
    System.out.println("Visiting ExpNavigationClassifierSource node");
    if (currentPhase == Phase.RHS) {
      System.out.println("  [RHS phase] Processing navigation with classifier source in RHS phase." + exp.getDestination().nameAsRolename());
    }
  }
}
