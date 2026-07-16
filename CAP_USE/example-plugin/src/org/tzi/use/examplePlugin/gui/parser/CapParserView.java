package org.tzi.use.examplePlugin.gui.parser;

import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.View;

import javax.swing.*;
import java.awt.*;

public class CapParserView extends JPanel implements View {
  private CapPaserPanel capPaserPanel;
  private ViewFrame viewFrame;

  public CapParserView() {
    setLayout(new BorderLayout());
    setFocusable(true);

    capPaserPanel = new CapPaserPanel();
    add(capPaserPanel, BorderLayout.CENTER);
  }

  public void setFrame(ViewFrame frame) {
    this.viewFrame = frame;
  }

  @Override
  public void detachModel() {

  }
}
