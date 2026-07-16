package org.tzi.use.examplePlugin.util;

import org.tzi.use.gui.main.ViewFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GUIUtils extends JPanel {

  public static void setMaximumFrameSize(ViewFrame frame) {
    try {
      frame.setMaximum(true);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void showErrorDialog(Component parent, String message) {
    JDialog dialog = new JDialog(
        SwingUtilities.getWindowAncestor(parent),
        "Error",
        Dialog.ModalityType.APPLICATION_MODAL
    );

    JPanel root = new JPanel(new BorderLayout(12, 12));
    root.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));
    root.setBackground(Color.WHITE);

    // ICON
    JLabel icon = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));
    root.add(icon, BorderLayout.WEST);

    // MESSAGE
    JLabel msg = new JLabel("<html>" + message + "</html>");
    msg.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    root.add(msg, BorderLayout.CENTER);

    // BUTTON
    JButton ok = new JButton("OK");
    ok.setFocusPainted(false);
    ok.addActionListener(e -> dialog.dispose());

    JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    btnPanel.setOpaque(false);
    btnPanel.add(ok);

    root.add(btnPanel, BorderLayout.SOUTH);

    dialog.setContentPane(root);
    dialog.pack();
    dialog.setLocationRelativeTo(parent);
    dialog.setResizable(false);
    dialog.setVisible(true);
  }

  public static JPanel createCardButton(
      String title,
      String description,
      Icon icon,
      Runnable action
  ) {
    JPanel card = new JPanel();
    card.setLayout(new BorderLayout());
    card.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(Color.LIGHT_GRAY),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));
    card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // ===== ICON =====
    JLabel iconLabel = new JLabel(icon);
    iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

    // ===== TITLE =====
    JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
    titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 18f));

    // ===== DESCRIPTION =====
    JLabel descLabel = new JLabel(
        "<html><div style='text-align:center;'>" + description + "</div></html>",
        SwingConstants.CENTER
    );
    descLabel.setFont(descLabel.getFont().deriveFont(14f));
    descLabel.setForeground(Color.DARK_GRAY);

    // ===== CENTER PANEL =====
    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
    centerPanel.setOpaque(false);

    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    centerPanel.add(iconLabel);
    centerPanel.add(Box.createVerticalStrut(10));
    centerPanel.add(titleLabel);
    centerPanel.add(Box.createVerticalStrut(6));
    centerPanel.add(descLabel);

    card.add(centerPanel, BorderLayout.CENTER);

    // ===== CLICK =====
    card.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        action.run();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        card.setBackground(new Color(245, 245, 245));
        card.setOpaque(true);
      }

      @Override
      public void mouseExited(MouseEvent e) {
        card.setOpaque(false);
      }
    });

    return card;
  }

  public static JButton createFlatButton(String text) {
    JButton btn = new JButton(text);
    btn.setFocusPainted(false);
    btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
    btn.setBackground(new Color(245, 245, 245));
    btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    btn.setMargin(new Insets(2, 10, 2, 10));
    return btn;
  }

}
