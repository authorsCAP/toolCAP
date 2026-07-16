package org.tzi.use.examplePlugin.model;

public class CAPProperty implements CAPExpr {

    private CAPExpr expr;
    private String property;

    @Override
    public String print() {
        return expr.print() + "." + property;
    }
}
