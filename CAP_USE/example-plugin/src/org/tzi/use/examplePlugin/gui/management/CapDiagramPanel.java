package org.tzi.use.examplePlugin.gui.management;

import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;

import javax.swing.*;
import java.awt.*;

public class CapDiagramPanel extends JPanel {

  public CapDiagramPanel(
      String capName,
      String description,
      ClassDiagramView diagramView
  ) {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // ===== TITLE (CAP NAME) =====
    JLabel titleLabel = new JLabel(capName);
    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(titleLabel, BorderLayout.NORTH);

    // ===== DESCRIPTION =====
    JTextArea descArea = new JTextArea(description);
    descArea.setEditable(false);
    descArea.setLineWrap(true);
    descArea.setWrapStyleWord(true);
    descArea.setBackground(UIManager.getColor("Panel.background"));
    descArea.setBorder(
        BorderFactory.createTitledBorder("Description")
    );

    JScrollPane descScroll = new JScrollPane(descArea);
    descScroll.setPreferredSize(new Dimension(200, 120));

    // ===== SPLIT DIAGRAM + DESCRIPTION =====
    JSplitPane splitPane = new JSplitPane(
        JSplitPane.VERTICAL_SPLIT,
        diagramView,
        descScroll
    );
    splitPane.setResizeWeight(0.8);

    add(splitPane, BorderLayout.CENTER);
  }
}