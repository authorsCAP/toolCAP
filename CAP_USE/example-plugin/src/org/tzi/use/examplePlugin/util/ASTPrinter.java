package org.tzi.use.examplePlugin.util;

import org.tzi.use.examplePlugin.ast.ASTInterface;

import java.util.*;

public final class ASTPrinter {

  private ASTPrinter() {}

  public static void print(ASTInterface ast) {
    print(ast, 0);
  }

  private static void print(Object obj, int indent) {
    String pad = "  ".repeat(indent);

    if (obj == null) {
      System.out.println(pad + "null");
      return;
    }

    // Nested annotation
    if (obj instanceof ASTInterface ast) {
      System.out.println(pad + ast.name);
      ast.args.forEach((k, v) -> {
        System.out.print(pad + "  " + k + " = ");
        print(v, indent + 2);
      });
      return;
    }

    // List of values
    if (obj instanceof List<?> list) {
      System.out.println(pad + "[");
      for (Object o : list) {
        print(o, indent + 1);
      }
      System.out.println(pad + "]");
      return;
    }

    // Primitive / string
    System.out.println(pad + obj);
  }
}

