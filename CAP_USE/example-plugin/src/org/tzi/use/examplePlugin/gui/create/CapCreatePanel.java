package org.tzi.use.examplePlugin.gui.create;

import org.tzi.use.examplePlugin.gui.management.CapManagePanel;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static org.tzi.use.examplePlugin.util.CommonVar.CAP_ROOT;
import static org.tzi.use.examplePlugin.util.FileUtils.getCurrentCAP;
import static org.tzi.use.examplePlugin.util.GUIUtils.showErrorDialog;

/**
 * Panel for creating and saving CAPs.
 */

public class CapCreatePanel extends JPanel {
  private JTextField capNameField;
  private JTextArea grammarArea;
  private JTextArea descriptionArea;

  private final boolean editMode;
  private final String originalCapName;
  private JPanel previousPanel;

  public CapCreatePanel() {
    this.editMode = false;
    this.originalCapName = null;
    initUI();
  }

  public CapCreatePanel(String capName, JPanel previousPanel) {
    this.editMode = true;
    this.originalCapName = capName;
    this.previousPanel = previousPanel;

    initUI();
    loadExistingCap(capName);
  }

  private void initUI() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

    // ===== NAME =====
    JPanel namePanel = new JPanel(new BorderLayout(5, 5));
    namePanel.add(new JLabel("CAP Name:"), BorderLayout.WEST);
    capNameField = new JTextField();
    namePanel.add(capNameField, BorderLayout.CENTER);

    // ===== GRAMMAR =====
    grammarArea = new JTextArea();
    grammarArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
    grammarArea.setLineWrap(false);
    grammarArea.setTabSize(2);

    JScrollPane grammarScroll = new JScrollPane(grammarArea);
    grammarScroll.setBorder(
        BorderFactory.createTitledBorder("CAP Grammar (.use)")
    );

    // ===== DESCRIPTION =====
    descriptionArea = new JTextArea(5, 20);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);

    JScrollPane descScroll = new JScrollPane(descriptionArea);
    descScroll.setBorder(
        BorderFactory.createTitledBorder("Description")
    );

    JSplitPane splitPane = new JSplitPane(
        JSplitPane.VERTICAL_SPLIT,
        grammarScroll,
        descScroll
    );
    splitPane.setResizeWeight(0.75);

    // ===== BUTTONS =====
    JButton loadFileButton = new JButton("Load .use file");
    loadFileButton.addActionListener(e -> loadUseFile());

    JButton saveButton = new JButton("Add CAP");
    saveButton.addActionListener(e -> saveCap());

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
    buttonPanel.add(loadFileButton);
    buttonPanel.add(saveButton);

    // ===== COMPOSE =====
    add(namePanel, BorderLayout.NORTH);
    add(splitPane, BorderLayout.CENTER);
    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void saveCap() {
    if (!editMode) {
      createCap();
    } else {
      updateCap();
    }
  }

  private void createCap() {
    String capName = capNameField.getText().trim();

    if (getCurrentCAP().contains(capName)) {
      showErrorDialog(this,
          "CAP with name '" + capName + "' already exists. Please choose a different name.");
      return;
    }

    String grammar = grammarArea.getText();
    String description = descriptionArea.getText();

    MModel model = compileUseModelFromString(grammar, this);
    if (model == null) {
      return;
    }

    if (capName.isEmpty()) {
      showErrorDialog(this,
          "CAP name cannot be empty.");
      return;
    }

    try {
      Path capFolder = CAP_ROOT.resolve(capName);
      Files.createDirectories(capFolder);

      writeFile(capFolder.resolve("cap_name.txt"), capName);
      writeFile(capFolder.resolve("grammar.use"), grammar);
      writeFile(capFolder.resolve("description.txt"), description);

      JOptionPane.showMessageDialog(this,
          "CAP saved successfully!",
          "Success",
          JOptionPane.INFORMATION_MESSAGE);

    } catch (IOException ex) {
      showErrorDialog(this,
          "Failed to save CAP:\n" + ex.getMessage());
    }
  }

  private void updateCap() {
    String grammar = grammarArea.getText();
    String description = descriptionArea.getText();

    MModel model = compileUseModelFromString(grammar, this);
    if (model == null) return;

    try {
      Path capFolder = CAP_ROOT.resolve(originalCapName);

      writeFile(capFolder.resolve("grammar.use"), grammar);
      writeFile(capFolder.resolve("description.txt"), description);

      JOptionPane.showMessageDialog(this,
          "CAP updated successfully!");

      backToManagement();

    } catch (IOException ex) {
      showErrorDialog(this,
          "Failed to update CAP:\n" + ex.getMessage());
    }
  }

  private void writeFile(Path path, String content) throws IOException {
    Files.write(path,
        content.getBytes(),
        StandardOpenOption.CREATE,
        StandardOpenOption.TRUNCATE_EXISTING);
  }

  /**
   * Compile a USE model from a string, also validating it and showing errors.
   * @param useText
   * @param parent
   * @return
   */
  private MModel compileUseModelFromString(
      String useText,
      Component parent
  ) {

    StringWriter errorBuffer = new StringWriter();
    PrintWriter err = new PrintWriter(errorBuffer);

    InputStream in = new ByteArrayInputStream(
        useText.getBytes(StandardCharsets.UTF_8)
    );

    MModel model = USECompiler.compileSpecification(
        in,
        "CAP_Input.use",
        err,
        new ModelFactory()
    );

    err.flush();

    if (model == null) {
      JOptionPane.showMessageDialog(parent,
          "USE model is invalid:\n\n" + errorBuffer,
          "USE Validation Error",
          JOptionPane.ERROR_MESSAGE);
      return null;
    }

    return model;
  }

  private void loadUseFile() {
    JFileChooser chooser = new JFileChooser();
    chooser.setFileFilter(new FileNameExtensionFilter(
        "USE Model (*.use)", "use"
    ));

    int option = chooser.showOpenDialog(this);
    if (option != JFileChooser.APPROVE_OPTION) {
      return;
    }

    Path file = chooser.getSelectedFile().toPath();

    try {
      String content = Files.readString(file, StandardCharsets.UTF_8);
      grammarArea.setText(content);

      // Optional: validate immediately
      MModel model = compileUseModelFromString(content, this);
      if (model != null) {
        JOptionPane.showMessageDialog(this,
            "USE model loaded and validated successfully.",
            "OK",
            JOptionPane.INFORMATION_MESSAGE);
      }

    } catch (IOException ex) {
      showErrorDialog(this,
          "Failed to load file:\n" + ex.getMessage());
    }
  }

  // Load existing CAP data into the form for editing
  private void loadExistingCap(String capName) {
    try {
      Path capFolder = CAP_ROOT.resolve(capName);

      capNameField.setText(capName);
      grammarArea.setText(
          Files.readString(capFolder.resolve("grammar.use"))
      );
      descriptionArea.setText(
          Files.readString(capFolder.resolve("description.txt"))
      );

    } catch (IOException e) {
      showErrorDialog(this,
          "Failed to load CAP data:\n" + e.getMessage());
    }
  }

  // Navigate back to the CAP management panel
  private void backToManagement() {
    Container parent = getParent();
    parent.remove(this);
    parent.add(new CapManagePanel());
    parent.revalidate();
    parent.repaint();
  }
}
