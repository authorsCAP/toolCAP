package org.tzi.use.examplePlugin.gui.other;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActionCellRenderer implements TableCellRenderer {

  private final ActionCellPanel panel = new ActionCellPanel();

  @Override
  public Component getTableCellRendererComponent(
      JTable table, Object value,
      boolean isSelected, boolean hasFocus,
      int row, int column) {

    panel.setBackground(
        isSelected ? table.getSelectionBackground() : Color.WHITE
    );

    panel.setBorder(BorderFactory.createMatteBorder(
        0, 0, 1, 0,
        new Color(230, 230, 230)
    ));

    return panel;
  }
}
