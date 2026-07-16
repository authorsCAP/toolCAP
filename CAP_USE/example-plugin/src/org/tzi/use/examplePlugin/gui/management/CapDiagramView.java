package org.tzi.use.examplePlugin.gui.management;

import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.View;
import org.tzi.use.gui.views.diagrams.classdiagram.ClassDiagramView;

import javax.swing.*;
import java.awt.*;

public class CapDiagramView extends JPanel implements View {
  private final CapDiagramPanel capDiagramPanel;
  private ViewFrame viewFrame;

  public CapDiagramView (String capName,
                                       String description,
                                       ClassDiagramView diagramView) {
    setLayout(new BorderLayout());
    setFocusable(true);

    capDiagramPanel = new CapDiagramPanel(capName, description, diagramView);
    add(capDiagramPanel, BorderLayout.CENTER);
  }

  public void setFrame(ViewFrame frame) {
    this.viewFrame = frame;
  }
  @Override
  public void detachModel() {

  }
}
