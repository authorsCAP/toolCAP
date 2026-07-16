package org.tzi.use.examplePlugin.metamodel.retake_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.RetakeConstraintInterface;

import java.util.List;

public class RetakeConstraintType1 implements RetakeConstraintInterface {
  String assocCls;
  String rolePath;
  String targetAssoc;

  List<AttrCondPro> filters;
  String sumAttr;
  Integer max;
}
