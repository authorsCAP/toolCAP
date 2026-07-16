package org.tzi.use.examplePlugin.metamodel.size_constraint;

import org.tzi.use.examplePlugin.metamodel.size_constraint.type1.SizeConstraintType1Generator;
import org.tzi.use.examplePlugin.metamodel.size_constraint.type1.SizeConstraintType1Parser;

public enum SizeConstraintType {
  TYPE1(new SizeConstraintType1Parser(), new SizeConstraintType1Generator()),
  TYPE2(null, null),
  TYPE3(null, null),
  TYPE4(null, null),
  UNSUPPORTED(null, null);

  public final SizeConstraintParser<?> parser;
  public final SizeConstraintGenerator<?> generator;

  SizeConstraintType(
      SizeConstraintParser<? extends SizeConstraintInterface> parser,
      SizeConstraintGenerator<? extends SizeConstraintInterface> generator
  ) {
    this.parser = parser;
    this.generator = generator;
  }

  @SuppressWarnings("unchecked")
  public <T extends SizeConstraintInterface> SizeConstraintParser<T> parser() {
    return (SizeConstraintParser<T>) parser;
  }

  @SuppressWarnings("unchecked")
  public <T extends SizeConstraintInterface> SizeConstraintGenerator<T> generator() {
    return (SizeConstraintGenerator<T>) generator;
  }
}
