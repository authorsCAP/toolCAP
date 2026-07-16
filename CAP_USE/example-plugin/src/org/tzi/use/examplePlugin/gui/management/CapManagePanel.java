package org.tzi.use.examplePlugin.gui.management;

import org.tzi.use.examplePlugin.gui.create.CapCreatePanel;
import org.tzi.use.examplePlugin.gui.create.CapCreateView;
import org.tzi.use.examplePlugin.gui.create_type.TypeCreatePanel;
import org.tzi.use.examplePlugin.gui.create_type.TypeCreateView;
import org.tzi.use.examplePlugin.gui.other.ActionCellEditor;
import org.tzi.use.examplePlugin.gui.other.ActionCellRenderer;
import org.tzi.use.examplePlugin.gui.other.CapNameCellRenderer;
import org.tzi.use.examplePlugin.gui.other.CapTableModel;
import org.tzi.use.examplePlugin.gui.type.CapTypePopup;
import org.tzi.use.examplePlugin.gui.view_type.TypeViewView;
import org.tzi.use.examplePlugin.util.CapTypeStorage;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;
import org.tzi.use.parser.use.USECompiler;
import org.tzi.use.uml.mm.MModel;
import org.tzi.use.uml.mm.ModelFactory;
import org.tzi.use.uml.sys.MSystem;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.tzi.use.examplePlugin.util.CommonVar.CAP_ROOT;
import static org.tzi.use.examplePlugin.util.FileUtils.deleteCapFolder;
import static org.tzi.use.examplePlugin.util.FileUtils.getCurrentCAP;
import static org.tzi.use.examplePlugin.util.GUIUtils.setMaximumFrameSize;
import static org.tzi.use.examplePlugin.util.GUIUtils.showErrorDialog;
// test commit
/**
 * Panel for managing CAP (read - update - delete) operations.
 */
public class CapManagePanel extends JPanel {

  private JPanel previousPanel;
  private CapTypePopup popup;

  public CapManagePanel() {
    initUI();
  }

  private void initUI() {
    setLayout(new BorderLayout());

    JTable table = new JTable(new CapTableModel(getCurrentCAP()));
    table.setRowHeight(36);
    table.setShowGrid(false);
    table.setIntercellSpacing(new Dimension(0, 0));
    table.setFocusable(false);
    table.setSelectionBackground(new Color(240, 245, 250));
    table.setShowHorizontalLines(true);
    table.setShowVerticalLines(false);

    // CAP NAME
    table.getColumnModel()
        .getColumn(0)
        .setCellRenderer(new CapNameCellRenderer());

    // ACTIONS
    TableColumn actionCol = table.getColumnModel().getColumn(1);
    actionCol.setPreferredWidth(260);
    actionCol.setMinWidth(260);
    actionCol.setMaxWidth(260);

    actionCol.setCellRenderer(new ActionCellRenderer());
    actionCol.setCellEditor(new ActionCellEditor(
        this::openDiagram,
        this::editCap,
        this::deleteCap,
        this::showTypesPopup));
    table.putClientProperty(
        "terminateEditOnFocusLost",
        Boolean.TRUE
    );

    JScrollPane scroll = new JScrollPane(table);
    scroll.setBorder(BorderFactory.createEmptyBorder());
    scroll.getVerticalScrollBar().setUnitIncrement(16);

    add(scroll, BorderLayout.CENTER);
  }

  private void deleteCap(String capName) {
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Delete CAP '" + capName + "'?",
        "Confirm delete",
        JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
      try {
        deleteCapFolder(capName);
        refreshUI();
      } catch (Exception e) {
        showErrorDialog(
            this,
            "Error deleting CAP '" + capName + "': " + e.getMessage()
        );
      }
    }
  }

  private void editCap(String capName) {
    Container parent = getParent();

    previousPanel = this;

    parent.remove(this);
    parent.add(new CapCreatePanel(capName, previousPanel));

    parent.revalidate();
    parent.repaint();
  }

  private void openDiagram(String capName) {
    try {
      // ===== 1. Load CAP files =====
      Path capFolder = CAP_ROOT.resolve(capName);

      String grammar = Files.readString(
          capFolder.resolve("grammar.use"),
          StandardCharsets.UTF_8
      );

      String description = Files.readString(
          capFolder.resolve("description.txt"),
          StandardCharsets.UTF_8
      );

      // ===== 2. Compile USE model from STRING =====
      InputStream in = new ByteArrayInputStream(
          grammar.getBytes(StandardCharsets.UTF_8)
      );

      StringWriter errorBuffer = new StringWriter();
      PrintWriter err = new PrintWriter(errorBuffer);

      MModel model = USECompiler.compileSpecification(
          in,
          capName + ".use",
          err,
          new ModelFactory()
      );

      if (model == null) {
        JOptionPane.showMessageDialog(this,
            "USE model error:\n\n" + errorBuffer,
            "USE Error",
            JOptionPane.ERROR_MESSAGE);
        return;
      }

      // ===== 3. Create system & diagram =====
      MSystem system = new MSystem(model);

      ClassDiagramView cdv = new ClassDiagramView(
          MainWindow.instance(),
          system,
          true
      );

      // ===== 4. Wrap into CAP panel =====
      CapDiagramView capDiagramView = new CapDiagramView(
          capName,
          description,
          cdv
      );

      // ===== 5. Create and add ViewFrame =====
      ViewFrame frame = new ViewFrame(
          "CAP - " + capName,
          capDiagramView,
          "ClassDiagram.gif"
      );

      capDiagramView.setFrame(frame);
      frame.setContentPane(capDiagramView);
      setMaximumFrameSize(frame);
      MainWindow.instance().addNewViewFrame(frame);

    } catch (IOException e) {
      showErrorDialog(this,
          "Failed to load CAP:\n" + e.getMessage());
    }
  }

  private void refreshUI() {
    removeAll();
    initUI();
    revalidate();
    repaint();
  }

  private void showTypesPopup(
      String capName,
      JTable table,
      int row,
      JButton sourceBtn
  ) {
    popup = new CapTypePopup(
        capName,
        t -> {
          popup.setVisible(false);
          openTypeView(capName, t);
        },
        t -> {
          popup.setVisible(false);
          openTypeEdit(capName, t);
        },
        t -> {
          popup.setVisible(false);
          deleteType(capName, t);
        },
        () -> {
          popup.setVisible(false);
          openTypeAdd(capName);
        }
    );

    Rectangle cellRect = table.getCellRect(row, 0, true);

    Point p = new Point(cellRect.x, cellRect.y + cellRect.height);
    SwingUtilities.convertPointToScreen(p, table);

    popup.setInvoker(table);
    popup.setLocation(p);
    popup.setVisible(true);
  }
  private void openTypeView(String capName, String typeName) {
    System.out.println("View type " + typeName + " of CAP " + capName);
    TypeViewView typeView = new TypeViewView(capName, typeName);
    ViewFrame frame = new ViewFrame(
        "View type '" + typeName + "' of CAP '" + capName + "'",
        typeView,
        "CommunicationDiagram.gif"
    );

    typeView.setFrame(frame);
    frame.setContentPane(typeView);
    setMaximumFrameSize(frame);
    MainWindow.instance().addNewViewFrame(frame);
  }

  private void openTypeEdit(String capName, String typeName) {
    System.out.println("Edit type " + typeName + " of CAP " + capName);
    Container parent = getParent();

    previousPanel = this;

    parent.remove(this);
    parent.add(new TypeCreatePanel(capName, typeName, previousPanel));

    parent.revalidate();
    parent.repaint();
  }

  private void deleteType(String capName, String typeName) {

    int choice = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to delete type '" + typeName + "'\n"
            + "from CAP '" + capName + "'?\n\n"
            + "This action cannot be undone.",
        "Confirm Delete",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE
    );

    if (choice != JOptionPane.YES_OPTION) {
      return;
    }

    try {
      CapTypeStorage.delete(capName, typeName);

      JOptionPane.showMessageDialog(
          this,
          "Type '" + typeName + "' was deleted successfully.",
          "Delete Successful",
          JOptionPane.INFORMATION_MESSAGE
      );

      refreshUI();
    } catch (Exception e) {
      showErrorDialog(
          this,
          "Failed to delete type '" + typeName + "':\n" + e.getMessage()
      );
    }
  }

  private void openTypeAdd(String capName) {
    System.out.println("Add new type for CAP " + capName);
    TypeCreateView typeCreateView = new TypeCreateView(capName);

    ViewFrame frame = new ViewFrame(
        "Create new type for CAP '" + capName + "'",
        typeCreateView,
        "CommunicationDiagram.gif"
    );

    // link CapView with its ViewFrame
    // required for going back to CRUD view
    typeCreateView.setFrame(frame);

    // compose frame content
    // add CapView to frame content pane
    JComponent content = (JComponent) frame.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(typeCreateView, BorderLayout.CENTER);

    MainWindow.instance().addNewViewFrame(frame);
    setMaximumFrameSize(frame);
  }
}
