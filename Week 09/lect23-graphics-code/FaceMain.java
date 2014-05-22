import java.awt.*;
import javax.swing.*;

/**
 * Basic Java graphics demo: Draw a face in a new window. Exit on window close. 
 */
public class FaceMain {
  
  public static void main(String[] args) {
    
    // Create frame and give it a name; set it to automatically
    // terminate the application when the window is closed.
    JFrame frame = new JFrame("A Face");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    // Add the face and a label to the window.
    // setPreferredSize ensures the face will have the size we
    // want when the window is packed.
    Face f = new Face();
    f.setPreferredSize(new Dimension(200,200));
    f.setBackground(Color.cyan);
    frame.add(f, BorderLayout.CENTER);
    JLabel jlbl = new JLabel("Don't worry, be happy!", SwingConstants.CENTER);
    frame.add(jlbl, BorderLayout.SOUTH);
    
    // pack layout to natural sizes of components, then display
    frame.pack();
    frame.setVisible(true);
  }
}
