package org.tzi.use.examplePlugin.gui.create;

import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.View;

import javax.swing.*;
import java.awt.*;


public class CapCreateView extends JPanel implements View {

  private final CapCreatePanel capPanel;
  private ViewFrame viewFrame;

  public CapCreateView() {
    setLayout(new BorderLayout());
    setFocusable(true);

    capPanel = new CapCreatePanel();
    add(capPanel, BorderLayout.CENTER);
  }

  public void setFrame(ViewFrame frame) {
    this.viewFrame = frame;
  }

  @Override
  public void detachModel() {
    // nothing to detach
  }
}
