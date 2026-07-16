package org.tzi.use.examplePlugin.gui.other;

import javax.swing.*;

import java.awt.*;

import static org.tzi.use.examplePlugin.util.GUIUtils.createFlatButton;

public class ActionCellPanel extends JPanel {

  public final JButton openBtn;
  public final JButton editBtn;
  public final JButton deleteBtn;
  public final JButton typesBtn = new JButton("Types ▼");

  public ActionCellPanel() {
    setLayout(new FlowLayout(FlowLayout.CENTER, 8, 6));
    setOpaque(true);

    openBtn   = createFlatButton("Open");
    editBtn   = createFlatButton("Edit");
    deleteBtn = createFlatButton("Delete");

    // styling delete button
    deleteBtn.setBackground(new Color(220, 53, 69)); // đỏ kiểu Bootstrap danger
    deleteBtn.setForeground(Color.WHITE);
    deleteBtn.setOpaque(true);
    deleteBtn.setBorderPainted(false);

    add(openBtn);
    add(editBtn);
    add(deleteBtn);
    add(typesBtn);
  }
}

