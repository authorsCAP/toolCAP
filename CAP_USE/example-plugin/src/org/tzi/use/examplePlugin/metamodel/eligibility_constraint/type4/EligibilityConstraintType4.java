package org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type4;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.EligibilityConstraintInterface;

import java.util.List;

public class EligibilityConstraintType4 implements EligibilityConstraintInterface {
  public String assocCls;
  public String rolePath;
  public String targetAssoc;
  List<IfPart> ifParts;
  List<AttrCondPro> checkForExi;
  int min;
}
