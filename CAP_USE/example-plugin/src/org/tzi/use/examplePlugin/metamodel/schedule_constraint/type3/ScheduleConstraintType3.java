package org.tzi.use.examplePlugin.metamodel.schedule_constraint.type3;

import org.tzi.use.examplePlugin.metamodel.AttrCondPro;
import org.tzi.use.examplePlugin.metamodel.IfPart;
import org.tzi.use.examplePlugin.metamodel.TimeWindow;
import org.tzi.use.examplePlugin.metamodel.schedule_constraint.ScheduleConstraintInterface;

import java.util.List;

public class ScheduleConstraintType3 implements ScheduleConstraintInterface {

  String assocCls;
  String rolePath;

  public TimeWindow window;

  public LimitAttribute limitAttribute;
}
