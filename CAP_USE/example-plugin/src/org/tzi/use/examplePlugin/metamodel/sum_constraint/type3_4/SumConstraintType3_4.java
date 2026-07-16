package org.tzi.use.examplePlugin.metamodel.sum_constraint.type3_4;

import org.tzi.use.examplePlugin.metamodel.AttrCond;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.SumConstraintInterface;
import org.tzi.use.examplePlugin.util.enumarate.BoundType;
import org.tzi.use.examplePlugin.util.enumarate.IfFixType;

import java.util.List;

public class SumConstraintType3_4 implements SumConstraintInterface {
  String assocCls;
  String rolePath;
  String sumAttr;

  public List<AttrCond> filters;

  BoundType boundType;
  String boundValue;

  // ifPart
  String ifAttr;
  IfFixType ifFixType;
  String ifFixValue;
  boolean negated;
}
