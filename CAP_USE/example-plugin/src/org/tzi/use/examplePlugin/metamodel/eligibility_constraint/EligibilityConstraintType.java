package org.tzi.use.examplePlugin.metamodel.eligibility_constraint;

import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type1.EligibilityConstraintType1Generator;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type1.EligibilityConstraintType1Parser;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type2.EligibilityConstraintType2Generator;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type2.EligibilityConstraintType2Parser;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type3.EligibilityConstraintType3Generator;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type3.EligibilityConstraintType3Parser;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type4.EligibilityConstraintType4Generator;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type4.EligibilityConstraintType4Parser;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type5.EligibilityConstraintType5Generator;
import org.tzi.use.examplePlugin.metamodel.eligibility_constraint.type5.EligibilityConstraintType5Parser;

public enum EligibilityConstraintType {
  TYPE1(new EligibilityConstraintType1Parser(), new EligibilityConstraintType1Generator()),
  TYPE2(new EligibilityConstraintType2Parser(), new EligibilityConstraintType2Generator()),
  TYPE3(new EligibilityConstraintType3Parser(), new EligibilityConstraintType3Generator()),
  TYPE4(new EligibilityConstraintType4Parser(), new EligibilityConstraintType4Generator()),
  TYPE5(new EligibilityConstraintType5Parser(), new EligibilityConstraintType5Generator()),
  UNSUPPORTED(null, null);

  public final EligibilityConstraintParser<?> parser;
  public final EligibilityConstraintGenerator<?> generator;

  EligibilityConstraintType(
      EligibilityConstraintParser<? extends EligibilityConstraintInterface> parser,
      EligibilityConstraintGenerator<? extends EligibilityConstraintInterface> generator
  ) {
    this.parser = parser;
    this.generator = generator;
  }

  @SuppressWarnings("unchecked")
  public <T extends EligibilityConstraintInterface> EligibilityConstraintParser<T> parser() {
    return (EligibilityConstraintParser<T>) parser;
  }

  @SuppressWarnings("unchecked")
  public <T extends EligibilityConstraintInterface> EligibilityConstraintGenerator<T> generator() {
    return (EligibilityConstraintGenerator<T>) generator;
  }
}
