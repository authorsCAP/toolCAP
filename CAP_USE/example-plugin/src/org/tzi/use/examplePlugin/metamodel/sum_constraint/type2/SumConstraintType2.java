package org.tzi.use.examplePlugin.metamodel.sum_constraint.type2;

import org.tzi.use.examplePlugin.metamodel.sum_constraint.SumConstraintInterface;
import org.tzi.use.examplePlugin.util.enumarate.BoundType;
import org.tzi.use.examplePlugin.util.enumarate.IfFixType;

public class SumConstraintType2 implements SumConstraintInterface {
  String assocCls;
  String rolePath;
  String filterAttr;
  String matchAttr;
  String sumAttr;

  BoundType boundType;
  String boundValue;

  String ifAttr;
  IfFixType ifFixType;
  String ifFixValue;
  boolean negated;
}