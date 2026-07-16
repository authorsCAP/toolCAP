package org.tzi.use.examplePlugin.metamodel.retake_constraint;

import org.tzi.use.examplePlugin.metamodel.retake_constraint.type1.RetakeConstraintType1Generator;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.type1.RetakeConstraintType1Parser;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.type2.RetakeConstraintType2Generator;
import org.tzi.use.examplePlugin.metamodel.retake_constraint.type2.RetakeConstraintType2Parser;

public enum RetakeConstraintType {
  TYPE1(new RetakeConstraintType1Parser(), new RetakeConstraintType1Generator()),
  TYPE2(new RetakeConstraintType2Parser(), new RetakeConstraintType2Generator()),
  TYPE3(null, null),
  TYPE4(null, null),
  TYPE5(null, null),
  UNSUPPORTED(null, null);

  public final RetakeConstraintParser<?> parser;
  public final RetakeConstraintGenerator<?> generator;

  RetakeConstraintType(
      RetakeConstraintParser<? extends RetakeConstraintInterface> parser,
      RetakeConstraintGenerator<? extends RetakeConstraintInterface> generator
  ) {
    this.parser = parser;
    this.generator = generator;
  }

  @SuppressWarnings("unchecked")
  public <T extends RetakeConstraintInterface> RetakeConstraintParser<T> parser() {
    return (RetakeConstraintParser<T>) parser;
  }

  @SuppressWarnings("unchecked")
  public <T extends RetakeConstraintInterface> RetakeConstraintGenerator<T> generator() {
    return (RetakeConstraintGenerator<T>) generator;
  }
}
