package org.tzi.use.examplePlugin.gui;

import org.tzi.use.examplePlugin.gui.create.CapCreateView;
import org.tzi.use.examplePlugin.gui.management.CapManageView;
import org.tzi.use.examplePlugin.gui.parse_file.FileParserView;
import org.tzi.use.examplePlugin.gui.parser.CapParserView;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

import static org.tzi.use.examplePlugin.util.GUIUtils.createCardButton;
import static org.tzi.use.examplePlugin.util.GUIUtils.setMaximumFrameSize;

public class CapCrudView extends JPanel implements View {

  private final List<JComponent> cards = new ArrayList<>();

  public CapCrudView() {
    setLayout(new GridBagLayout());
    setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    setFocusable(true);

    cards.add(createCardButton(
        "Create CAP",
        "Create a new CAP specification",
        UIManager.getIcon("FileView.fileIcon"),
        this::openCreateCap
    ));

    cards.add(createCardButton(
        "Manage CAP",
        "View, edit, delete and open diagrams",
        UIManager.getIcon("FileView.directoryIcon"),
        this::openManageCap
    ));

    cards.add(createCardButton(
        "Convert Annotation to OCL",
        "Convert from Annotation to OCL",
        UIManager.getIcon("FileView.directoryIcon"),
        this::parseAnnotation
    ));

    cards.add(createCardButton(
        "Parse CAP from File",
        "Parse CAP specification from file",
        UIManager.getIcon("FileView.directoryIcon"),
        this::openFileParser
    ));

    rebuildLayout(getColumnCount());

    // Responsive khi resize
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        rebuildLayout(getColumnCount());
      }
    });
  }

  private int getColumnCount() {
    int width = getWidth();
    if (width < 600) {
      return 1;
    }
    return 2;
  }

  private void rebuildLayout(int columns) {
    removeAll();
    setLayout(new GridBagLayout());

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15, 15, 15, 15);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;

    int x = 0;
    int y = 0;

    for (JComponent card : cards) {
      gbc.gridx = x;
      gbc.gridy = y;
      add(card, gbc);

      x++;
      if (x == columns) {
        x = 0;
        y++;
      }
    }

    revalidate();
    repaint();
  }

  private void parseAnnotation() {

    CapParserView parserView = new CapParserView();
    ViewFrame frame = new ViewFrame(
        "CAP Annotation to OCL Converter",
        parserView,
        "CommunicationDiagram.gif"
    );

    parserView.setFrame(frame);
    JComponent content = (JComponent) frame.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(parserView, BorderLayout.CENTER);

    MainWindow.instance().addNewViewFrame(frame);
    setMaximumFrameSize(frame);
  }


  private void openManageCap() {
    CapManageView capManageView = new CapManageView();

    ViewFrame frame = new ViewFrame(
        "Manage CAP",
        capManageView,
        "CommunicationDiagram.gif"
    );

    // link CapView with its ViewFrame
    // required for going back to CRUD view
    capManageView.setFrame(frame);

    // compose frame content
    // add CapView to frame content pane
    JComponent content = (JComponent) frame.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(capManageView, BorderLayout.CENTER);

    MainWindow.instance().addNewViewFrame(frame);
    setMaximumFrameSize(frame);
  }

  // open Create CAP view in new ViewFrame
  private void openCreateCap() {
    CapCreateView capView = new CapCreateView();

    ViewFrame frame = new ViewFrame(
        "Create CAP",
        capView,
        "CommunicationDiagram.gif"
    );

    // link CapView with its ViewFrame
    // required for going back to CRUD view
    capView.setFrame(frame);

    // compose frame content
    // add CapView to frame content pane
    JComponent content = (JComponent) frame.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(capView, BorderLayout.CENTER);

    MainWindow.instance().addNewViewFrame(frame);
    setMaximumFrameSize(frame);
  }

  private void openFileParser() {
    FileParserView parserView = new FileParserView();
    ViewFrame frame = new ViewFrame(
        "CAP File Parser",
        parserView,
        "CommunicationDiagram.gif"
    );

    parserView.setFrame(frame);
    JComponent content = (JComponent) frame.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(parserView, BorderLayout.CENTER);

    MainWindow.instance().addNewViewFrame(frame);
    setMaximumFrameSize(frame);
  }


  @Override
  public void detachModel() {
    // nothing to detach
  }
}