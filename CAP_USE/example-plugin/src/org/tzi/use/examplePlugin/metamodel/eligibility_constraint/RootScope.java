package org.tzi.use.examplePlugin.metamodel.eligibility_constraint;

public enum RootScope {
  // for all conditions, the root scope is the "self"
  ALL,

  // for all conditions except the last one, the root scope is the iterator ("a", "b",...) or default is "e".
  // For the last condition, the root scope is the "self" of the previous conditions
  LAST_ONLY,

  // for all conditions, the root scope is the iterator ("a", "b",...) or default is "e".
  NONE,

  // for all conditions except the first one, the root scope is the "self" of the previous conditions.
  // For the first condition, the root scope is the iterator ("a", "b",...) or default is "e".
  FIRST_ONLY
}
