package org.tzi.use.examplePlugin.metamodel.status_constraint.type5;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintInterface;

import java.util.List;

public class StatusConstraintType5 implements StatusConstraintInterface {
  String assocCls;
  String rolePath;
  List<IfPart> ifParts;

  List<AttrCondPro> checkStatus;
}
