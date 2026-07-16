package org.tzi.use.examplePlugin.model;

public class CAPCollect implements CAPExpr {
    private CAPExpr source;
    private String var;
    private CAPExpr body;

    @Override
    public String print() {
        return source.print()
            + "->collect(" + var + " | " + body.print() + ")";
    }
}
