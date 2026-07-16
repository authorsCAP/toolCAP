package org.tzi.use.examplePlugin.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.tzi.use.examplePlugin.util.CommonVar.CAP_ROOT;
import static org.tzi.use.examplePlugin.util.CommonVar.CAP_STORAGE_URL;


public class FileUtils {

  // get the current available CAPs from USE
  public static List<String> getCurrentCAP() {

    List<String> currentCAPs = new ArrayList<>();

    Path parentDir = Paths.get(CAP_STORAGE_URL);

    try (DirectoryStream<Path> stream =
             Files.newDirectoryStream(parentDir, Files::isDirectory)) {
      for (Path path : stream) {
        currentCAPs.add(path.getFileName().toString());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return currentCAPs;
  }

  // delete a CAP folder by name
  public static void deleteCapFolder(String name) throws IOException {
    Path target = CAP_ROOT.resolve(name);

    // Không tồn tại → bỏ qua
    if (!Files.exists(target) || !Files.isDirectory(target)) {
      throw new IOException("CAP folder not found: " + name);
    }

    Files.walk(target)
        .sorted(Comparator.reverseOrder())
        .forEach(path -> {
          try {
            Files.delete(path);
          } catch (IOException e) {
            throw new RuntimeException("Cannot delete: " + path, e);
          }
        });
  }

  // ensure a directory exists, if not create it
  public static Path ensureDirectory(Path path) throws IOException {
    if (!Files.exists(path)) {
      Files.createDirectories(path);
    }
    return path;
  }

  /**
   * Save content to a file, if the file already exists, it will be overwritten.
   * @param content
   * @param path
   * @throws IOException
   */
  public static void saveContentToFixedFile(String content, String path) throws IOException {
    Path filePath = Path.of(
        path
    );

    Files.createDirectories(filePath.getParent());

    Files.write(
        filePath,
        content.getBytes(StandardCharsets.UTF_8),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING
    );
  }

  /**
   * Remove excessive blank lines from the content. A blank line is defined as a line that contains only whitespace characters (spaces, tabs) or is completely empty.
   * @param content
   * @return
   */
  public static String cleanExcessiveBlankLines(String content) {
    if (content == null || content.isEmpty()) {
      return content;
    }

    // convert all types of newlines to \n for easier processing
    String normalized = content.replace("\r\n", "\n");

    // delete all lines that contain only whitespace characters (spaces, tabs)
    normalized = normalized.replaceAll("(?m)^[ \t]+$", "");

    // finally, replace 3 or more consecutive newlines with just 2 newlines
    return normalized.replaceAll("\\n{3,}", "\n\n");
  }
}
