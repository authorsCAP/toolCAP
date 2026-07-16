package org.tzi.use.examplePlugin.metamodel.sum_constraint;

import org.tzi.use.examplePlugin.metamodel.sum_constraint.type1.SumConstraintType1Generator;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.type1.SumConstraintType1Parser;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.type2.SumConstraintType2Generator;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.type2.SumConstraintType2Parser;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.type3_4.SumConstraintType3_4Generator;
import org.tzi.use.examplePlugin.metamodel.sum_constraint.type3_4.SumConstraintType3_4Parser;

public enum SumConstraintType {
  TYPE1(new SumConstraintType1Parser(),
      new SumConstraintType1Generator()),

  TYPE2(new SumConstraintType2Parser(), new SumConstraintType2Generator()),
  TYPE3_4(new SumConstraintType3_4Parser(), new SumConstraintType3_4Generator()),
  UNSUPPORTED(null, null);

  public final SumConstraintParser<?> parser;
  public final SumConstraintGenerator<?> generator;

  SumConstraintType(
      SumConstraintParser<? extends SumConstraintInterface> parser,
      SumConstraintGenerator<? extends SumConstraintInterface> generator
  ) {
    this.parser = parser;
    this.generator = generator;
  }

  @SuppressWarnings("unchecked")
  public <T extends SumConstraintInterface> SumConstraintParser<T> parser() {
    return (SumConstraintParser<T>) parser;
  }

  @SuppressWarnings("unchecked")
  public <T extends SumConstraintInterface> SumConstraintGenerator<T> generator() {
    return (SumConstraintGenerator<T>) generator;
  }
}
