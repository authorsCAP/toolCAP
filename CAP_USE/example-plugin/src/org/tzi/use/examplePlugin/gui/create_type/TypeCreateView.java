package org.tzi.use.examplePlugin.gui.create_type;

import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.View;

import javax.swing.*;
import java.awt.*;

public class TypeCreateView extends JPanel implements View {

  private final TypeCreatePanel typePanel;
  private ViewFrame viewFrame;

  public TypeCreateView(String capName) {
    setLayout(new BorderLayout());
    setFocusable(true);

    typePanel = new TypeCreatePanel(capName);
    add(typePanel, BorderLayout.CENTER);
  }

  public void setFrame(ViewFrame frame) {
    this.viewFrame = frame;
  }

  @Override
  public void detachModel() {
    // nothing to detach
  }
}
