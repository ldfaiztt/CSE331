import java.awt.*;
import javax.swing.*;

/**
 * Simple graphics window (based on ex. 7-1 in Core Java 8th ed.)
 */
public class SimpleFrameMain {
  /** Create a frame and display it. */
  public static void main(String[] args) {
    SimpleFrame frame = new SimpleFrame("A Window");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}

class SimpleFrame extends JFrame {
  public SimpleFrame(String title) {
    super(title);
    setSize(300,200);
  }
}
