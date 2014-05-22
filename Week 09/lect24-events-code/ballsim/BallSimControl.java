import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * Viewer/controller for ball world simulation.
 * This panel contains the view plus buttons and mouse input controllers
 * that allow the user to pause and stop the simulation, and add new
 * objects to the simulation.
 * CSE 331 12au, 13wi
 */
public class BallSimControl extends JPanel {
  
  // instance variables
  private BallGraphicsView viewPane;      // the panel displaying the model
  private SimModel world;                 // the simulation world model
  private static Random random;           // random number generator (used to
  // initialize new balls added to model)
  
  /**
   * Construct a viewer/controller for the given world
   * @param w the model object this object is controlling and viewing
   */
  public BallSimControl(SimModel w) {
    
    // save reference to model
    this.world = w;
    
    // initialize random number sequence with system clock to get different
    // results each time.  (For debugging, would be better to use the same seed
    // value each time.)
    random = new Random(System.currentTimeMillis());
    
    
    // default layout manager for JPanel is flow layout - we want a border layout
    setLayout(new BorderLayout());
    
    // create paneel with ball display and place it in the center
    viewPane = new BallGraphicsView(world);
    viewPane.setPreferredSize(new Dimension(world.getWidth(), world.getHeight()));
    add(viewPane, BorderLayout.CENTER);
    
    // register the view with the model
    world.addView(viewPane);
    
    // set up listener for mouse clicks on the view
    viewPane.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        world.add(randomBall(e.getX(), e.getY()));
      }
    });
    
    
    // create panel with buttons and add it at the bottom
    JButton pause = new JButton("pause");
    JButton resume = new JButton("resume");
    JButton stop = new JButton("stop");
    JPanel buttons = new JPanel();
    buttons.add(pause);
    buttons.add(resume);
    buttons.add(stop);
    pause.setBackground(Color.yellow);
    resume.setBackground(Color.green);
    stop.setBackground(Color.red);
    buttons.setBackground(Color.lightGray);
    add(buttons, BorderLayout.SOUTH);
    
    // set up listener for the buttons
    SimButtonListener buttonListener = new SimButtonListener();
    pause.addActionListener(buttonListener);
    resume.addActionListener(buttonListener);
    stop.addActionListener(buttonListener);
  }
  
  /**
   * Create a new ball with random color, size, and velocity
   * @param x initial x coordinate of the new ball
   * @param y initial y coordinate of the new ball
   */
  private Ball randomBall(int x, int y) {
    return new Ball(x, y,
                    random.nextInt(21) - 11,       // dx
                    random.nextInt(21) - 11,       // dy
                    new Color(random.nextInt(256), // color
                              random.nextInt(256),
                              random.nextInt(256)),
                    random.nextInt(20) + 5,        // diameter
                    world);                        // model containing window size
  }
  
/////////////// Inner Classes /////////////////
  
  
  /**
   * Handle button clicks for the BallSimControl window.
   */
  class SimButtonListener implements ActionListener {
    
    /**
     * Process button clicks by turning the simulation on and off
     * @param e the event created by the button click
     */
    public void actionPerformed(ActionEvent e) {
      if (e.getActionCommand().equals("pause")) {
        world.pause();
      } else if (e.getActionCommand().equals("resume")) {
        world.resume();
      } else if (e.getActionCommand().equals("stop")) {
        world.stop();
      }
    }
  }
  
}
