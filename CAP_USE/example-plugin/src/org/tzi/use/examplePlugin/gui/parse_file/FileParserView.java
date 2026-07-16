package org.tzi.use.examplePlugin.gui.parse_file;

import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.gui.views.View;

import javax.swing.*;
import java.awt.*;

public class FileParserView extends JPanel implements View {
  private final FileParserPanel parserPanel;
  private ViewFrame viewFrame;

  public FileParserView() {
    setLayout(new BorderLayout());
    setFocusable(true);

    parserPanel = new FileParserPanel();
    add(parserPanel, BorderLayout.CENTER);
  }

  public void setFrame(ViewFrame frame) {
    this.viewFrame = frame;
  }

  @Override
  public void detachModel() {

  }
}
