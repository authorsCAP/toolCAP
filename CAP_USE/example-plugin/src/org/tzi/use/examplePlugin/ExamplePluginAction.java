package org.tzi.use.examplePlugin;

import org.tzi.use.examplePlugin.gui.CapCrudView;
import org.tzi.use.gui.main.MainWindow;
import org.tzi.use.gui.main.ViewFrame;
import org.tzi.use.runtime.gui.IPluginAction;
import org.tzi.use.runtime.gui.IPluginActionDelegate;

import javax.swing.*;
import java.awt.*;

import static org.tzi.use.examplePlugin.util.GUIUtils.setMaximumFrameSize;

public class ExamplePluginAction implements IPluginActionDelegate {
  @Override
  public void performAction(IPluginAction pluginAction) {

    // 1. Open a USE model file
    System.out.println("Example Plugin Action: performAction called");
//    JFileChooser fc = new JFileChooser();
//    fc.setFileFilter(new ExtFileFilter("use", "USE Model"));
//    int option = fc.showOpenDialog(pluginAction.getParent());
//    if (option != JFileChooser.APPROVE_OPTION) return;
//
//    File useFile = fc.getSelectedFile();
//
//    // 2. Parse the model
//    System.out.println("Example Plugin Action: Parsing model " + useFile.getAbsolutePath());
//    MModel model = null;
//    try (FileInputStream in = new FileInputStream(useFile)) {
//      model = USECompiler.compileSpecification(in, useFile.getName(),
//          new PrintWriter(System.err), new ModelFactory());
//
//
//    } catch (IOException e) {
//      JOptionPane.showMessageDialog(pluginAction.getParent(),
//          "Cannot open file: " + e.getMessage(), "Error",
//          JOptionPane.ERROR_MESSAGE);
//      return;
//    }
//
//    if (model == null) {
//      JOptionPane.showMessageDialog(pluginAction.getParent(),
//          "Cannot parse model", "Error", JOptionPane.ERROR_MESSAGE);
//      return;
//    }
//
//    // 3. Create a system and show a class diagram view
//    System.out.println("Example Plugin Action: Creating system and displaying class diagram");
//    MSystem system = new MSystem(model);
//
//    ClassDiagramView cdv = new ClassDiagramView(
//        MainWindow.instance(), system, true
//    );
//
//    // 4. Create and add a new view frame to the main window
//    System.out.println("Example Plugin Action: Adding class diagram view to main window");
//    ViewFrame frame = new ViewFrame("Class Diagram", cdv, "ClassDiagram.gif");
//    JComponent content = (JComponent) frame.getContentPane();
//    content.setLayout(new BorderLayout());
//    content.add(cdv, BorderLayout.CENTER);

    CapCrudView capCrudView = new CapCrudView();

    ViewFrame frame = new ViewFrame(
        "CAP Creator",
        capCrudView,
        "CommunicationDiagram.gif"
    );

    JComponent content = (JComponent) frame.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(capCrudView, BorderLayout.CENTER);

    MainWindow.instance().addNewViewFrame(frame);
    setMaximumFrameSize(frame);
  }
}
