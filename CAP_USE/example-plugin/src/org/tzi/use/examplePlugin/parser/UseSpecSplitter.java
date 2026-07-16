package org.tzi.use.examplePlugin.parser;

public final class UseSpecSplitter {

  public static final class Result {
    public final String coreSpec;
    public final String annotations;

    public Result(String coreSpec, String annotations) {
      this.coreSpec = coreSpec;
      this.annotations = annotations;
    }
  }

  public static Result split(String input) {
    StringBuilder core = new StringBuilder();
    StringBuilder anno = new StringBuilder();

    boolean inAnno = false;
    int parenBalance = 0;

    for (String line : input.split("\\R")) {
      String trimmed = line.trim();

      // start detecting annotation
      if (!inAnno && trimmed.startsWith("@")) {
        inAnno = true;
        parenBalance = count(line, '(') - count(line, ')');
        anno.append(line).append('\n');

        if (parenBalance == 0) {
          inAnno = false;
        }
        continue;
      }

      // inside annotation, keep counting parentheses to determine when it ends
      if (inAnno) {
        parenBalance += count(line, '(') - count(line, ')');
        anno.append(line).append('\n');

        if (parenBalance == 0) {
          inAnno = false;
        }
        continue;
      }

      // if not in annotation, it's part of the core spec
      core.append(line).append('\n');
    }

    return new Result(core.toString(), anno.toString());
  }

  private static int count(String s, char c) {
    int n = 0;
    for (char x : s.toCharArray()) {
      if (x == c) n++;
    }
    return n;
  }
}
