package org.tzi.use.examplePlugin.util;

import org.tzi.use.examplePlugin.gui.other.CapType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

import static org.tzi.use.examplePlugin.util.CommonVar.CAP_ROOT;
import static org.tzi.use.examplePlugin.util.FileUtils.ensureDirectory;

public class CapTypeStorage {
  public static Path typeRoot(String capName) {
    return CAP_ROOT.resolve(capName).resolve("types");
  }

  // show all types of a CAP
  public static List<String> listTypes(String capName) {
    Path root = typeRoot(capName);
    try {
      ensureDirectory(root);
    } catch (IOException e) {
      System.out.println("Cannot ensure type directory: " + e.getMessage());
      throw new RuntimeException(e);
    }
    if (!Files.exists(root)) return List.of();

    try (var s = Files.list(root)) {
      return s.filter(Files::isDirectory)
          .map(p -> p.getFileName().toString())
          .toList();
    } catch (IOException e) {
      return List.of();
    }
  }

  // save a specific type of a CAP
  public static void save(String capName, CapType type) throws IOException {
    Path dir = typeRoot(capName).resolve(type.name);
    Files.createDirectories(dir);

    write(dir, "name.txt", type.name);
    write(dir, "annotation.txt", type.annotationSpec);
    write(dir, "ocl.txt", type.oclSpec);
    write(dir, "example_annotation.txt", type.exampleAnnotation);
    write(dir, "example_ocl.txt", type.exampleOcl);
    write(dir, "description.txt", type.description);
  }

  // load a specific type of a CAP
  public static CapType load(String capName, String typeName) throws IOException {
    Path dir = typeRoot(capName).resolve(typeName);

    CapType t = new CapType();
    t.name = typeName;
    t.annotationSpec = read(dir, "annotation.txt");
    t.oclSpec = read(dir, "ocl.txt");
    t.exampleAnnotation = read(dir, "example_annotation.txt");
    t.exampleOcl = read(dir, "example_ocl.txt");
    t.description = read(dir, "description.txt");

    return t;
  }

  public static void delete(String capName, String typeName) throws IOException {
    Path dir = typeRoot(capName).resolve(typeName);
    if (!Files.exists(dir)) return;

    Files.walk(dir)
        .sorted(Comparator.reverseOrder())
        .forEach(p -> {
          try { Files.delete(p); } catch (IOException ignored) {}
        });
  }

  private static void write(Path dir, String file, String content) throws IOException {
    Files.writeString(dir.resolve(file), content == null ? "" : content);
  }

  private static String read(Path dir, String file) throws IOException {
    Path p = dir.resolve(file);
    return Files.exists(p) ? Files.readString(p) : "";
  }
}
