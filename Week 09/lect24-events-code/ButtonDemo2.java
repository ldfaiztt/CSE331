import java.awt.*;         // basic AWT classes
import java.awt.event.*;   // event classes
import javax.swing.*;      // Swing classes

/**
 * Very simple demo of Swing event handling.  
 * Create a window with a single button that prints a message when it's clicked.
 * Version 2 with anonymous inner class to handle button events.
 */ 
public class ButtonDemo2 {
  
  public static void main(String[] args) {
    // Create new window and set it to exit from application when closed.
    JFrame frame = new JFrame("Button Demo");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    
    // Create a new button with label "Hit me" and string "OUCH!" to be
    // returned as part of each action event.
    JButton button = new JButton("Hit me");
    button.setActionCommand("OUCH!");

    // Old version, replaced by the below call:
    // button.addActionListener(new MyButtonListener());
    
    // Create and register a new button listener to handle clicks.
    button.addActionListener(new ActionListener () {
      int nEvents = 0; // number of events handled
      String id = "Listener1";
      public void actionPerformed(ActionEvent e) {
        nEvents++;
        System.out.println(id + " " + e.getActionCommand() + " " + nEvents);
      }
    });
    
    // Add button to the window and make it visible.
    frame.add(button);
    frame.pack();
    frame.setVisible(true);
  }
}
