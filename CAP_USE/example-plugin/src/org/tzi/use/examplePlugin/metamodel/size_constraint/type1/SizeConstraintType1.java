package org.tzi.use.examplePlugin.metamodel.size_constraint.type1;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.schedule_constraint.ScheduleConstraintInterface;
import org.tzi.use.examplePlugin.metamodel.size_constraint.Bound;
import org.tzi.use.examplePlugin.metamodel.size_constraint.SizeConstraintInterface;

import java.util.List;

public class SizeConstraintType1 implements SizeConstraintInterface {
  String assocCls;
  String rolePath;
  String targetCollection;

  List<IfPart> ifParts;

  List<AttrCondPro> filters;

  List<Bound> bounds;
}
