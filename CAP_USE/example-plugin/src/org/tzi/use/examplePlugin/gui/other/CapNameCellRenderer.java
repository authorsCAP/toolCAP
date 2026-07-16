package org.tzi.use.examplePlugin.gui.other;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CapNameCellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(
      JTable table,
      Object value,
      boolean isSelected,
      boolean hasFocus,
      int row,
      int column
  ) {
    JLabel label = (JLabel) super.getTableCellRendererComponent(
        table, value, isSelected, false, row, column
    );

    label.setFont(label.getFont().deriveFont(Font.BOLD, 14f));
    label.setHorizontalAlignment(SwingConstants.CENTER);
    label.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 12));

    return label;
  }
}
