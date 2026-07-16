package org.tzi.use.examplePlugin.metamodel;

public class CommonAttributes {
  // Joining attributes
  public static final String AND = "and";
  public static final String OR = "or";

  public static final String ARGS = "args";
  public static final String ASSOC_CLS = "assocCls";
  public static final String ROLE_PATH = "rolePath";
  public static final String SUM_ATTR = "sumAttr";
  public static final String FIX_ATTR = "fixAttr";
  public static final String COLLECT = "collect";
  public static final String IF_PART = "ifPart";
  public static final String ATTR = "attr";
  public static final String MATCH_ATTR = "matchAttr";
  public static final String MAX = "max";
  public static final String MIN = "min";

  // eligibility constraint specific
  public static final String CHECK_FOR_EXI = "checkForExi";
  public static final String SCALE = "scale";
  public static final String RATIO = "ratio";
  public static final String TARGET_ASSOC = "targetAssoc";
  public static final String MATCH_COLL = "matchColl";

  // schedule constraint specific
  public static final String CROSS_REFERENCE = "crossReference";
  public static final String CONFLICT_CHECK = "conflictCheck";
  public static final String INTERSECTION_OP = "intersectionOp";
  public static final String ALT_PART = "altPart";
  public static final String WINDOW = "window";
  public static final String TIME_ATTR = "timeAttr";
  public static final String BASE_TIME = "baseTime";
  public static final String DURATION = "duration";
  public static final String UNIT = "unit";

  // size constraint specific
  public static final String TARGET_COLLECTION = "targetCollection";

  // status constraint specific
  public static final String CHECK_STATUS = "checkStatus";

  // retake constraint specific
  public static final String ATTR_EXISTS = "attrExists";
}
