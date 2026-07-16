package org.tzi.use.examplePlugin.gui.management;

import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.View;

import javax.swing.*;
import java.awt.*;

public class CapManageView extends JPanel implements View {

  private final CapManagePanel capManagePanel;
  private ViewFrame viewFrame;

  public CapManageView() {
    setLayout(new BorderLayout());
    setFocusable(true);

    capManagePanel = new CapManagePanel();
    add(capManagePanel, BorderLayout.CENTER);
  }

  public void setFrame(ViewFrame frame) {
    this.viewFrame = frame;
  }
  @Override
  public void detachModel() {
    // nothing to detach
  }
}
