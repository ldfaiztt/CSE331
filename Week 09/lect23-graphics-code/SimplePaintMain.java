import java.awt.*;
import javax.swing.*;

/**
 * Example overriding paintComponent to create a static drawing.
 */
public class SimplePaintMain {
  
  /** Create a labeled panel with a couple of shapes and display it. */
  public static void main(String[] args) {
    JFrame frame = new JFrame("Simple Painting Example");
    JPanel panel = new SimplePainting();
    panel.setPreferredSize(new Dimension(300,200));
    JLabel label = new JLabel("A Work of Art");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    frame.add(panel,BorderLayout.CENTER);
    frame.add(label,BorderLayout.SOUTH);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}

class SimplePainting extends JPanel {
  
  /** Paint some simple shapes whenever this is called by the window manager. */
  public void paintComponent(Graphics g) {
    
    // ensure any background belonging to container is painted
    super.paintComponent(g);
    
    // Cast g to its actual class to make graphics2d methods available.
    // We don't use them here, but this cast is often necessary.
    Graphics2D g2 = (Graphics2D) g;
    
    // draw a couple of shapes
    g2.setColor(Color.green);
    g2.fillOval(40,30,120,100);
    g2.setColor(Color.red);
    g2.drawRect(60,50,60,60);
  }
}
