package org.tzi.use.examplePlugin.metamodel.status_constraint.type3;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.status_constraint.StatusConstraintInterface;

import java.util.List;

public class StatusConstraintType3 implements StatusConstraintInterface {
  String assocCls;
  String rolePath;
  List<IfPart> ifParts;

  List<AttrCondPro> filters;

  // check status need to be [0], otherwise, it is invalid, because it only supports one check status condition
  List<Integer> checkStatus;
}
