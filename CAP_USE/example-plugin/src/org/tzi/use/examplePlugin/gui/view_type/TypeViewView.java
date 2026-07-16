package org.tzi.use.examplePlugin.gui.view_type;

import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.View;

import javax.swing.*;
import java.awt.*;

public class TypeViewView  extends JPanel implements View {
  private final TypeViewPanel typeViewPanel;
  private ViewFrame viewFrame;

  public TypeViewView(String capName, String typeName) {
    setLayout(new BorderLayout());
    setFocusable(true);

    typeViewPanel = new TypeViewPanel(capName, typeName);
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    add(typeViewPanel);
  }

  public void setFrame(ViewFrame frame) {
    this.viewFrame = frame;
  }

  @Override
  public void detachModel() {

  }
}
