package org.tzi.use.examplePlugin.gui.other;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ActionCellEditor extends AbstractCellEditor
    implements TableCellEditor {

  private final ActionCellPanel panel = new ActionCellPanel();
  private String capName;

  private int row;
  private QuadConsumer<String, JTable, Integer, JButton> showTypes;
  private JTable table;

  public ActionCellEditor(
      Consumer<String> open,
      Consumer<String> edit,
      Consumer<String> delete,
      QuadConsumer<String, JTable, Integer, JButton> showTypes) {

    panel.openBtn.addActionListener(e -> {
      open.accept(capName);
      stopCellEditing();
    });

    panel.editBtn.addActionListener(e -> {
      edit.accept(capName);
      stopCellEditing();
    });

    panel.deleteBtn.addActionListener(e -> {
      delete.accept(capName);
      stopCellEditing();
    });

    panel.typesBtn.addActionListener(e -> {
      JButton btn = (JButton) e.getSource();
      JTable table = (JTable) SwingUtilities.getAncestorOfClass(JTable.class, btn);

      int row = table.getEditingRow();

      showTypes.accept(capName, table, row, btn);
      stopCellEditing();
    });
  }

  @Override
  public Component getTableCellEditorComponent(
      JTable table, Object value,
      boolean isSelected, int row, int column) {

    capName = table.getValueAt(row, 0).toString();
    panel.setBackground(table.getSelectionBackground());
    return panel;
  }

  @Override
  public Object getCellEditorValue() {
    return capName;
  }
}
