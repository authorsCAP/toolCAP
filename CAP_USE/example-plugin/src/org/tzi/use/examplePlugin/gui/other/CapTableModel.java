package org.tzi.use.examplePlugin.gui.other;

import javax.swing.table.AbstractTableModel;
import java.util.List;
public class CapTableModel extends AbstractTableModel {
  private final List<String> caps;

  public CapTableModel(List<String> caps) {
    this.caps = caps;
  }

  @Override public int getRowCount() { return caps.size(); }
  @Override public int getColumnCount() { return 2; }
  @Override public String getColumnName(int c) {
    return c == 0 ? "CAP Name" : "Actions";
  }

  @Override public Object getValueAt(int r, int c) {
    return caps.get(r);
  }

  @Override public boolean isCellEditable(int r, int c) {
    return c == 1;
  }
}
