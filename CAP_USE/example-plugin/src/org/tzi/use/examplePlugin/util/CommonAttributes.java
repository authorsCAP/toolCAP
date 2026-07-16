package org.tzi.use.examplePlugin.util;

import java.util.List;

public class CommonAttributes {

    // Attributes for constraints
    public static final String COLLECT = "collect";
    public static final String FIX_ATTR = "fixAttr";
    public static final String MATCH_ATTR = "matchAttr";
    public static final String FIX_NUM = "fixNum";
    public static final String FIX_BOOL = "fixBool";
    public static final String FIX_STR = "fixStr";
    public static final String FIX_ENUM = "fixEnum";

    // bound conditions
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String MAX_ATTR = "maxAttr";
    public static final String MIN_ATTR = "minAttr";
    public static final String MAX_LIM = "maxLim";
    public static final String MIN_LIM = "minLim";
    public static final String MAX_VALUE = "maxValue";
    public static final String MIN_VALUE = "minValue";
    public static final String MAX_LIM_ATTR = "maxLimAttr";
    public static final String MIN_LIM_ATTR = "minLimAttr";


    public static final String TYPE = "type";
    public static final List<String> BOUND_CONDITIONS = List.of(
        MAX, MIN, MAX_ATTR, MIN_ATTR, MAX_LIM, MIN_LIM, MAX_LIM_ATTR, MIN_LIM_ATTR);
}
