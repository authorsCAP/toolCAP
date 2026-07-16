package org.tzi.use.examplePlugin.gui.type;

import org.tzi.use.examplePlugin.gui.other.CapType;
import org.tzi.use.examplePlugin.util.CapTypeStorage;

import javax.swing.*;
import java.awt.*;
import java.util.stream.Stream;

public class CapTypeEditorPanel extends JPanel {

  private JTextField name;
  private JTextArea annotation, ocl, exAnno, exOcl, desc;

  public CapTypeEditorPanel(
      String capName,
      CapType type,
      boolean readOnly,
      Runnable onClose
  ) {
    setLayout(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

    name = new JTextField(type.name);
    annotation = area(type.annotationSpec);
    ocl = area(type.oclSpec);
    exAnno = area(type.exampleAnnotation);
    exOcl = area(type.exampleOcl);
    desc = area(type.description);

    JPanel form = new JPanel(new GridLayout(0, 1, 6, 6));
    form.add(label("Type name", name));
    form.add(label("Annotation spec", annotation));
    form.add(label("OCL spec", ocl));
    form.add(label("Example annotation", exAnno));
    form.add(label("Example OCL", exOcl));
    form.add(label("Description", desc));

    add(new JScrollPane(form), BorderLayout.CENTER);

    if (readOnly) {
      Stream.of(name, annotation, ocl, exAnno, exOcl, desc)
          .forEach(c -> c.setEnabled(false));
    }

    JButton save = new JButton("Save");
    save.setEnabled(!readOnly);
    save.addActionListener(e -> {
      try {
        type.name = name.getText();
        type.annotationSpec = annotation.getText();
        type.oclSpec = ocl.getText();
        type.exampleAnnotation = exAnno.getText();
        type.exampleOcl = exOcl.getText();
        type.description = desc.getText();
        CapTypeStorage.save(capName, type);
        onClose.run();
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage());
      }
    });

    JButton close = new JButton(readOnly ? "Close" : "Cancel");
    close.addActionListener(e -> onClose.run());

    JPanel bottom = new JPanel();
    bottom.add(save);
    bottom.add(close);

    add(bottom, BorderLayout.SOUTH);
  }

  private JTextArea area(String t) {
    JTextArea a = new JTextArea(t);
    a.setLineWrap(true);
    return a;
  }

  private JPanel label(String title, JComponent c) {
    JPanel p = new JPanel(new BorderLayout());
    p.add(new JLabel(title), BorderLayout.NORTH);
    p.add(new JScrollPane(c), BorderLayout.CENTER);
    return p;
  }
}