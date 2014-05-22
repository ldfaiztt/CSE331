import java.awt.*;
import javax.swing.*;

/**
 * Very simple layout example:  create a panel in the center of
 * a window and a label at the bottom.
 */
public class SimpleLayoutMain {
  
  /** Create an empty, labeled panel and display it */
  public static void main(String[] args) {
    JFrame frame = new JFrame("Simple Layout Example");
    JPanel panel = new JPanel();
    panel.setPreferredSize(new Dimension(300,200));
    JLabel label = new JLabel("Smile!!");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    frame.add(panel,BorderLayout.CENTER);
    frame.add(label,BorderLayout.SOUTH);
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}

