package org.tzi.use.examplePlugin.model;

public class CAPVar implements CAPExpr {
    private final String name;

    public CAPVar(String name) {
        this.name = name;
    }

    @Override
    public String print() {
        return name;
    }
}
