package org.tzi.use.examplePlugin.gui.parse_file;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * A component that displays line numbers for a JTextArea. It listens to changes in the text area and updates the line numbers accordingly.
 * This class is used in the ParseFilePanel to provide line numbers for the text area where the user inputs the specification.
 */

public class LineNumberView extends JPanel {

  private final JTextArea textArea;
  private final FontMetrics fontMetrics;
  private static final int PADDING = 8;

  public LineNumberView(JTextArea textArea) {
    this.textArea = textArea;
    setFont(textArea.getFont());
    setBackground(new Color(245, 245, 245));
    setForeground(Color.GRAY);

    fontMetrics = getFontMetrics(getFont());

    textArea.getDocument().addDocumentListener(new DocumentListener() {
      public void insertUpdate(DocumentEvent e) { repaint(); revalidate(); }
      public void removeUpdate(DocumentEvent e) { repaint(); revalidate(); }
      public void changedUpdate(DocumentEvent e) {}
    });
  }

  @Override
  public Dimension getPreferredSize() {
    int lineCount = Math.max(1, textArea.getLineCount());
    int digits = String.valueOf(lineCount).length();
    int width = PADDING * 2 + fontMetrics.charWidth('0') * digits;
    return new Dimension(width, textArea.getHeight());
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Rectangle clip = g.getClipBounds();
    int startLine = clip.y / textArea.getFontMetrics(textArea.getFont()).getHeight();
    int endLine = startLine + (clip.height / textArea.getFontMetrics(textArea.getFont()).getHeight());

    int lineHeight = textArea.getFontMetrics(textArea.getFont()).getHeight();

    for (int i = startLine; i <= endLine; i++) {
      String lineNumber = String.valueOf(i + 1);
      int x = getWidth() - PADDING - fontMetrics.stringWidth(lineNumber);
      int y = (i + 1) * lineHeight - 4;
      g.drawString(lineNumber, x, y);
    }
  }
}
