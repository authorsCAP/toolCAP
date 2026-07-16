package org.tzi.use.examplePlugin.metamodel.status_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintInterface;

import java.util.List;

public class StatusConstraintType1 implements StatusConstraintInterface {
  List<IfPart> ifParts;
  List<AttrCondPro> checkStatus;
}
