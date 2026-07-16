package org.tzi.use.examplePlugin.metamodel.time_constraint;

import org.tzi.use.examplePlugin.metamodel.time_constraint.type1.TimeConstraintType1Generator;
import org.tzi.use.examplePlugin.metamodel.time_constraint.type1.TimeConstraintType1Parser;
import org.tzi.use.examplePlugin.metamodel.time_constraint.type2.TimeConstraintType2Generator;
import org.tzi.use.examplePlugin.metamodel.time_constraint.type2.TimeConstraintType2Parser;
import org.tzi.use.examplePlugin.metamodel.time_constraint.type4.TimeConstraintType4Generator;
import org.tzi.use.examplePlugin.metamodel.time_constraint.type4.TimeConstraintType4Parser;

public enum TimeConstraintType {
  TYPE1(new TimeConstraintType1Parser(), new TimeConstraintType1Generator()),
  TYPE2(new TimeConstraintType2Parser(), new TimeConstraintType2Generator()),
  TYPE3(null, null),
  TYPE4(new TimeConstraintType4Parser(), new TimeConstraintType4Generator()),
  UNSUPPORTED(null, null);

  public final TimeConstraintParser<?> parser;
  public final TimeConstraintGenerator<?> generator;

  TimeConstraintType(
      TimeConstraintParser<? extends TimeConstraintInterface> parser,
      TimeConstraintGenerator<? extends TimeConstraintInterface> generator
  ) {
    this.parser = parser;
    this.generator = generator;
  }

  @SuppressWarnings("unchecked")
  public <T extends TimeConstraintInterface> TimeConstraintParser<T> parser() {
    return (TimeConstraintParser<T>) parser;
  }

  @SuppressWarnings("unchecked")
  public <T extends TimeConstraintInterface> TimeConstraintGenerator<T> generator() {
    return (TimeConstraintGenerator<T>) generator;
  }
}
