package org.tzi.use.examplePlugin.gui.type;

import org.tzi.use.examplePlugin.util.CapTypeStorage;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class CapTypePopup extends JPopupMenu {

  public CapTypePopup(
      String capName,
      Consumer<String> onView,
      Consumer<String> onEdit,
      Consumer<String> onDelete,
      Runnable onAdd
  ) {
//    JPanel list = new JPanel();
//    list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
//
//    for (String type : CapTypeStorage.listTypes(capName)) {
//      list.add(createRow(type, onView, onEdit, onDelete));
//    }
//
//    JScrollPane scroll = new JScrollPane(list);
//    scroll.setPreferredSize(new Dimension(420, 200));
//    add(scroll);
//
//    JButton addBtn = new JButton("+ Add new type");
//    addBtn.addActionListener(e -> onAdd.run());
//    add(addBtn);
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    container.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));

    // ===== HEADER =====
    JLabel header = new JLabel(capName);
    header.setFont(header.getFont().deriveFont(Font.BOLD));
    header.setBorder(BorderFactory.createEmptyBorder(4, 8, 8, 8));
    container.add(header);

    // ===== TYPES =====
    for (String type : CapTypeStorage.listTypes(capName)) {
      container.add(createTypeRow(type, onView, onEdit, onDelete));
    }

    container.add(Box.createVerticalStrut(6));
    container.add(new JSeparator());

    // ===== ADD BUTTON =====
    JButton addBtn = new JButton("+ Add new type");
    addBtn.setFocusPainted(false);
    addBtn.setHorizontalAlignment(SwingConstants.LEFT);
    addBtn.addActionListener(e -> onAdd.run());
    container.add(addBtn);

    JScrollPane scroll = new JScrollPane(container);
    scroll.setBorder(null);
    scroll.setPreferredSize(new Dimension(480, 240));
    add(scroll);
  }

  private JPanel createTypeRow(
      String type,
      Consumer<String> onView,
      Consumer<String> onEdit,
      Consumer<String> onDelete
  ) {
    JPanel row = new JPanel(new GridBagLayout());
    row.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
    row.setBackground(Color.WHITE);

    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;
    c.insets = new Insets(0, 4, 0, 4);

    // ▶ type name
    c.gridx = 0;
    c.weightx = 1;
    c.anchor = GridBagConstraints.WEST;
    row.add(new JLabel("▶ " + type), c);

    // buttons
    c.weightx = 0;
    c.anchor = GridBagConstraints.EAST;

    c.gridx++;
    row.add(btn("View", () -> onView.accept(type)), c);

    c.gridx++;
    row.add(btn("Edit", () -> onEdit.accept(type)), c);

    c.gridx++;
    row.add(btn("Delete", () -> onDelete.accept(type)), c);

    // hover effect
    row.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseEntered(java.awt.event.MouseEvent e) {
        row.setBackground(new Color(230, 240, 255));
      }
      public void mouseExited(java.awt.event.MouseEvent e) {
        row.setBackground(Color.WHITE);
      }
    });

    return row;
  }

  private JButton btn(String text, Runnable r) {
    JButton b = new JButton(text);
    b.setMargin(new Insets(2, 8, 2, 8));
    b.setFocusPainted(false);
    b.addActionListener(e -> r.run());
    return b;
  }
}
