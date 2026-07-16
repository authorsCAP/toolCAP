package org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.AttrCond;
import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintInterface;

import java.util.List;

public class EligibilityConstraintType1 implements EligibilityConstraintInterface {

  String assocCls;
  String rolePath;

  public List<AttrCondPro> filters;

  // ifPart
  List<IfPart> ifParts;

  // indicate that this is EligibilityConstraintType1
  // sumAttr need to be set to "1" and max need to be set to 0
  String sumAttr;
  int max;
}
