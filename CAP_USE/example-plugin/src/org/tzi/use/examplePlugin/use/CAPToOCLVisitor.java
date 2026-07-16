package org.tzi.use.examplePlugin.use;


import org.tzi.use.examplePlugin.ast.ASTInterface;

public class CAPToOCLVisitor {

  public String visitSumConstraint(ASTInterface ast) {
    String assocCls = visitAssocCls((ASTInterface) ast.args.get("assocCls"));
    String rolePath = visitRolePath((ASTInterface) ast.args.get("rolePath"));
    String sumAttr = getArgValue(ast, "sumAttr");
    Object collect = ast.args.get("collect");

    Object collect1 = ast.args.get("collect1");
    Object collect2 = ast.args.get("collect2");

    // Case 1: Single sumAttr and collect
    if (collect != null && sumAttr != null) {
      String collectCondition = visitCollection(collect);
      Object fixAttrObj = ast.args.get("fixAttr");
      String fixAttr = "";

      if (fixAttrObj instanceof Iterable<?>) {
        StringBuilder fixAttrBuilder = new StringBuilder();
        for (Object cond : (Iterable<?>) fixAttrObj) {
          fixAttrBuilder.append(visitFixAttr((ASTInterface) cond)).append(" and ");
        }
        fixAttr = fixAttrBuilder.substring(0, fixAttrBuilder.length() - 5); // Remove trailing " and "
      } else if (fixAttrObj instanceof ASTInterface) {
        fixAttr = visitFixAttr((ASTInterface) fixAttrObj);
      }

      return "self." + assocCls + "->select(" + collectCondition + ")->collect(" + sumAttr + ")->sum()" + (fixAttr.isEmpty() ? "" : " " + fixAttr);
    }

    // Case 2: Multiple sumAttr or collect (use let constructs)
    String total = "self." + assocCls + "->select(e | e." + rolePath + "." + visitCollection(collect1) + ")->collect(e | e." + rolePath + "." + sumAttr + ")->sum()";
    String core = "self." + assocCls + "->select(e | e." + rolePath + "." + visitCollection(collect2) + ")->collect(e | e." + rolePath + "." + sumAttr + ")->sum()";

    return "let total : Integer = " + total + " in\n" +
        "let core : Integer = " + core + " in\n" +
        "core <= total";
  }

  public String visitAttrCond(ASTInterface ast) {
    String attr = getArgValue(ast, "attr");
    String minLim = getArgValue(ast, "minLim");
    String matchVal = getArgValue(ast, "matchVal");

    if (minLim != null) {
      return attr + " >= " + minLim;
    } else if (matchVal != null) {
      return attr + " = " + matchVal;
    }
    return "";
  }

  public String visitRolePath(ASTInterface ast) {
    String r1 = getArgValue(ast, "r1");
    String r2 = getArgValue(ast, "r2");
    return r1 + "." + r2;
  }

  private String visitCollection(Object collect) {
    if (collect instanceof Iterable<?>) {
      StringBuilder collection = new StringBuilder();
      for (Object cond : (Iterable<?>) collect) {
        collection.append(visitAttrCond((ASTInterface) cond)).append(" and ");
      }
      return collection.substring(0, collection.length() - 5); // Remove trailing " and "
    } else if (collect instanceof ASTInterface) {
      return visitAttrCond((ASTInterface) collect);
    }
    return "";
  }

  private String visitFixAttr(ASTInterface ast) {
    if (ast == null) return "";
    String minLim = getArgValue(ast, "minLim");
    String maxLim = getArgValue(ast, "maxLim");

    if (minLim != null) {
      return " >= " + minLim;
    } else if (maxLim != null) {
      return " <= " + maxLim;
    }
    return "";
  }

  private String visitAssocCls(ASTInterface ast) {
    return getArgValue(ast, "as1") != null ? getArgValue(ast, "as1") : "";
  }

  private String getArgValue(ASTInterface ast, String argName) {
    Object value = ast.args.get(argName);
    return value != null ? value.toString() : null;
  }
}


