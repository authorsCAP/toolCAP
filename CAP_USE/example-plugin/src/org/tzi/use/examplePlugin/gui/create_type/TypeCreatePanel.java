package org.tzi.use.examplePlugin.gui.create_type;

import org.tzi.use.examplePlugin.gui.management.CapManagePanel;
import org.tzi.use.examplePlugin.gui.other.CapType;
import org.tzi.use.examplePlugin.util.CapTypeStorage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.tzi.use.examplePlugin.util.CommonVar.CAP_ROOT;
import static org.tzi.use.examplePlugin.util.GUIUtils.showErrorDialog;

public class TypeCreatePanel extends JPanel {

  private JTextField nameField;

  private JTextArea annotationSpecArea, oclSpecArea, annotationExampleArea, oclExampleArea, descriptionArea;

  private final boolean editMode;

  private JPanel previousPanel;

  public TypeCreatePanel(String capName) {
    this.editMode = false;
    initUI(capName);
  }

  public TypeCreatePanel(String capName, String typeName, JPanel previousPanel) {
    this.editMode = true;
    this.previousPanel = previousPanel;
    initUI(capName);
    loadExistingType(capName, typeName);
  }

  private void loadExistingType(String capName, String typeName) {
    try {
      Path capFolder = CAP_ROOT.resolve(capName);
      Path specificPath = capFolder.resolve("types").resolve(typeName);

      nameField.setText(typeName);
      annotationSpecArea.setText(
          Files.readString(specificPath.resolve("annotation.txt"))
      );
      oclSpecArea.setText(
          Files.readString(specificPath.resolve("ocl.txt"))
      );
      annotationExampleArea.setText(
          Files.readString(specificPath.resolve("example_annotation.txt"))
      );
      oclExampleArea.setText(
          Files.readString(specificPath.resolve("example_ocl.txt"))
      );

      descriptionArea.setText(
          Files.readString(capFolder.resolve("description.txt"))
      );

    } catch (IOException e) {
      showErrorDialog(this,
          "Failed to load existing Type:\n" + e.getMessage());
    }
  }

  private void initUI(String capName) {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

    // ===== NAME =====
    JPanel namePanel = new JPanel(new BorderLayout(5, 5));
    namePanel.add(new JLabel("Type Name:"), BorderLayout.WEST);

    nameField = new JTextField();
    namePanel.add(nameField, BorderLayout.CENTER);

    add(namePanel, BorderLayout.NORTH);

    // ===== SPEC & EXAMPLE (BIG AREA) =====
    annotationSpecArea = createCodeArea();
    oclSpecArea = createCodeArea();
    annotationExampleArea = createCodeArea();
    oclExampleArea = createCodeArea();

    JScrollPane capSpecScroll =
        createScroll(annotationSpecArea, "CAP Spec");
    JScrollPane oclSpecScroll =
        createScroll(oclSpecArea, "OCL Spec");

    JScrollPane capExampleScroll =
        createScroll(annotationExampleArea, "CAP Example");
    JScrollPane oclExampleScroll =
        createScroll(oclExampleArea, "OCL Example");

    JPanel specPanel = new JPanel(new GridLayout(1, 2, 10, 0));
    specPanel.add(capSpecScroll);
    specPanel.add(oclSpecScroll);

    JPanel examplePanel = new JPanel(new GridLayout(1, 2, 10, 0));
    examplePanel.add(capExampleScroll);
    examplePanel.add(oclExampleScroll);

    // Make them TALL
    specPanel.setPreferredSize(new Dimension(0, 240));
    examplePanel.setPreferredSize(new Dimension(0, 240));

    // ===== DESCRIPTION (SMALLER) =====
    descriptionArea = new JTextArea(4, 20);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);

    JScrollPane descScroll = new JScrollPane(descriptionArea);
    descScroll.setBorder(
        BorderFactory.createTitledBorder("Description")
    );

    // ===== CENTER LAYOUT =====
    JPanel center = new JPanel();
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

    center.add(specPanel);
    center.add(Box.createVerticalStrut(10));
    center.add(examplePanel);
    center.add(Box.createVerticalStrut(10));
    center.add(descScroll);

    add(center, BorderLayout.CENTER);

    // ===== BUTTONS =====
    JButton saveBtn = new JButton("Save Type");
    saveBtn.addActionListener(e -> {
      try {
        saveType(capName);
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
      }
    });

    JPanel buttonPanel = new JPanel(
        new FlowLayout(FlowLayout.RIGHT, 10, 5)
    );
    buttonPanel.add(saveBtn);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  // ===== Helpers =====

  private JTextArea createCodeArea() {
    JTextArea area = new JTextArea();
    area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
    area.setLineWrap(false);
    area.setTabSize(2);
    return area;
  }

  private JScrollPane createScroll(JTextArea area, String title) {
    JScrollPane scroll = new JScrollPane(area);
    scroll.setBorder(BorderFactory.createTitledBorder(title));
    return scroll;
  }

  private void saveType(String capName) {
    String typeName = nameField.getText().trim();

    if (typeName.isEmpty()) {
      JOptionPane.showMessageDialog(this,
          "Type name cannot be empty",
          "Validation Error",
          JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      CapType type = new CapType();
      type.name = typeName;
      type.annotationSpec = annotationSpecArea.getText();
      type.oclSpec = oclSpecArea.getText();
      type.exampleAnnotation = annotationExampleArea.getText();
      type.exampleOcl = oclExampleArea.getText();
      type.description = descriptionArea.getText();

      // ===== SAVE =====
      CapTypeStorage.save(capName, type);

      // ===== SUCCESS =====
      JOptionPane.showMessageDialog(this,
          "Type updated successfully!",
          "Success",
          JOptionPane.INFORMATION_MESSAGE);

      // ===== BACK =====
      backToManagement();

    } catch (Exception ex) {
      JOptionPane.showMessageDialog(this,
          "Failed to save Type:\n" + ex.getMessage(),
          "Save Error",
          JOptionPane.ERROR_MESSAGE);
    }
  }

  private void backToManagement() {
    Container parent = getParent();
    parent.remove(this);
    parent.add(new CapManagePanel());
    parent.revalidate();
    parent.repaint();
  }
}
