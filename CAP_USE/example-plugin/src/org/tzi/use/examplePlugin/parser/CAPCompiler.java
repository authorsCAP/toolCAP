package org.tzi.use.examplePlugin.parser;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.tzi.use.examplePlugin.ast.ASTInterface;
import org.tzi.use.examplePlugin.parser.CAPParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

public class CAPCompiler {
  public static ASTInterface compileSpecification(String inName, PrintWriter err) {
    InputStream in;

    try {
      in = new FileInputStream(inName);
    } catch (FileNotFoundException e) {
      err.println("Could not open input file: " + e.getMessage());
      return null;
    }

    ANTLRInputStream input;
    try {
      input = new ANTLRInputStream(in);
      input.name = inName;
    } catch (IOException e) {
      err.println("Could not read input file: " + e.getMessage());
      return null;
    }

    org.tzi.use.examplePlugin.parser.CAPLexer capLexer = new org.tzi.use.examplePlugin.parser.CAPLexer(input);
    CommonTokenStream commonTokenStream = new CommonTokenStream(capLexer);
    CAPParser capParser = new CAPParser(commonTokenStream);

    try {
      return capParser.annotationFile();
    } catch (RecognitionException e) {
      err.println(capParser.getSourceName() + ": "
          + " at line " + e.line + ", position " + e.charPositionInLine + ": " + e.getMessage());
      return null;
    } finally {
      err.flush();
    }
  }
}
