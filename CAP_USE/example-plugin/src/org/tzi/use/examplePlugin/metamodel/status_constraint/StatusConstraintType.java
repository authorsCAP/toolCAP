package org.tzi.use.examplePlugin.metamodel.status_constraint;

import org.tzi.use.examplePlugin.metamodel.status_constraint.type1.StatusConstraintType1Generator;
import org.tzi.use.examplePlugin.metamodel.status_constraint.type1.StatusConstraintType1Parser;
import org.tzi.use.examplePlugin.metamodel.status_constraint.type3.StatusConstraintType3Generator;
import org.tzi.use.examplePlugin.metamodel.status_constraint.type3.StatusConstraintType3Parser;
import org.tzi.use.examplePlugin.metamodel.status_constraint.type5.StatusConstraintType5Generator;
import org.tzi.use.examplePlugin.metamodel.status_constraint.type5.StatusConstraintType5Parser;

public enum StatusConstraintType {
  TYPE1(new StatusConstraintType1Parser(), new StatusConstraintType1Generator()),
  TYPE2(null, null),
  TYPE3(new StatusConstraintType3Parser(), new StatusConstraintType3Generator()),
  TYPE4(null, null),
  TYPE5(new StatusConstraintType5Parser(), new StatusConstraintType5Generator()),
  UNSUPPORTED(null, null);

  public final StatusConstraintParser<?> parser;
  public final StatusConstraintGenerator<?> generator;

  StatusConstraintType(
      StatusConstraintParser<? extends StatusConstraintInterface> parser,
      StatusConstraintGenerator<? extends StatusConstraintInterface> generator
  ) {
    this.parser = parser;
    this.generator = generator;
  }

  @SuppressWarnings("unchecked")
  public <T extends StatusConstraintInterface> StatusConstraintParser<T> parser() {
    return (StatusConstraintParser<T>) parser;
  }

  @SuppressWarnings("unchecked")
  public <T extends StatusConstraintInterface> StatusConstraintGenerator<T> generator() {
    return (StatusConstraintGenerator<T>) generator;
  }
}
