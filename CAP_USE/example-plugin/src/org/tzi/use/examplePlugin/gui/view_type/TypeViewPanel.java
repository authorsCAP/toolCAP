package org.tzi.use.examplePlugin.gui.view_type;

import org.tzi.use.examplePlugin.gui.management.CapManagePanel;
import org.tzi.use.examplePlugin.util.CapTypeStorage;
import org.tzi.use.examplePlugin.gui.other.CapType;

import javax.swing.*;
import java.awt.*;

public class TypeViewPanel extends JPanel {

  private JTextField nameField;
  private JTextArea annotationSpecArea, oclSpecArea;
  private JTextArea annotationExampleArea, oclExampleArea;
  private JTextArea descriptionArea;

  public TypeViewPanel(String capName, String typeName) {
    initUI();
    loadType(capName, typeName);
  }

  private void initUI() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

    // ===== NAME =====
    JPanel namePanel = new JPanel(new BorderLayout(5, 5));
    namePanel.add(new JLabel("Type Name:"), BorderLayout.WEST);

    nameField = new JTextField();
    nameField.setEditable(false);
    namePanel.add(nameField, BorderLayout.CENTER);

    add(namePanel, BorderLayout.NORTH);

    // ===== AREAS =====
    annotationSpecArea = createViewCodeArea();
    oclSpecArea = createViewCodeArea();
    annotationExampleArea = createViewCodeArea();
    oclExampleArea = createViewCodeArea();

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

    specPanel.setPreferredSize(new Dimension(0, 240));
    examplePanel.setPreferredSize(new Dimension(0, 240));

    // ===== DESCRIPTION =====
    descriptionArea = new JTextArea(4, 20);
    descriptionArea.setEditable(false);
    descriptionArea.setLineWrap(true);
    descriptionArea.setWrapStyleWord(true);

    JScrollPane descScroll = new JScrollPane(descriptionArea);
    descScroll.setBorder(
        BorderFactory.createTitledBorder("Description")
    );

    // ===== CENTER =====
    JPanel center = new JPanel();
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

    center.add(specPanel);
    center.add(Box.createVerticalStrut(10));
    center.add(examplePanel);
    center.add(Box.createVerticalStrut(10));
    center.add(descScroll);

    add(center, BorderLayout.CENTER);

    // ===== BUTTON =====
    JButton closeBtn = new JButton("Close");
    closeBtn.addActionListener(e -> back());

    JPanel buttonPanel = new JPanel(
        new FlowLayout(FlowLayout.RIGHT, 10, 5)
    );
    buttonPanel.add(closeBtn);

    add(buttonPanel, BorderLayout.SOUTH);
  }

  private void loadType(String capName, String typeName) {
    try {
      CapType type = CapTypeStorage.load(capName, typeName);

      nameField.setText(type.name);
      annotationSpecArea.setText(type.annotationSpec);
      oclSpecArea.setText(type.oclSpec);
      annotationExampleArea.setText(type.exampleAnnotation);
      oclExampleArea.setText(type.exampleOcl);
      descriptionArea.setText(type.description);

      // Scroll to top
      annotationSpecArea.setCaretPosition(0);
      oclSpecArea.setCaretPosition(0);
      annotationExampleArea.setCaretPosition(0);
      oclExampleArea.setCaretPosition(0);

    } catch (Exception e) {
      JOptionPane.showMessageDialog(
          this,
          "Failed to load Type:\n" + e.getMessage(),
          "Load Error",
          JOptionPane.ERROR_MESSAGE
      );
    }
  }

  private void back() {
    Container parent = getParent();
    parent.remove(this);
    parent.add(new CapManagePanel());
    parent.revalidate();
    parent.repaint();
  }

  // ===== Helpers =====

  private JTextArea createViewCodeArea() {
    JTextArea area = new JTextArea();
    area.setEditable(false);
    area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
    area.setLineWrap(false);
    area.setTabSize(2);
    area.setBackground(new Color(248, 248, 248));
    return area;
  }

  private JScrollPane createScroll(JTextArea area, String title) {
    JScrollPane scroll = new JScrollPane(area);
    scroll.setBorder(BorderFactory.createTitledBorder(title));
    return scroll;
  }
}
