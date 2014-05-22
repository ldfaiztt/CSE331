import java.awt.*;
import java.util.*;

/**
 * A small ball object that wanders randomly around a bounded area.
 * CSE 331 12au, 13wi
 */
public class Ball implements SimThing {
  // instance variables
  private int x, y;           // current coordinates of this Ball
  private int dx, dy;         // current motion direction in x and y
                              //    (sign gives direction, magnitude
                              //    gives speed)
  private Color color;        // color of this ball
  private int diameter;       // size of this ball
  private int maxx, maxy;     // max x and y coordiantes
  
  
  /**
   * Construct a new Ball with the given coordinates, initial direction, and model.
   * @param x initial x coordinate of center of the ball
   * @param y initial y coordinate of center of the ball
   * @param dx initial change in x value on each simulation cycle
   * @param dy initial change in y value on each simulation cycle
   * @param c color of the ball
   * @param diameter diameter of the ball
   * @param model the model that this Ball is a part of
   */
  public Ball(int x, int y, int dx, int dy, 
              Color c, int diameter, SimModel model) {
    this.x = x;
    this.y = y;
    this.dx = dx;
    this.dy = dy;
    this.color = c;
    this.diameter = diameter;
    this.maxx = model.getWidth();
    this.maxy = model.getHeight();
  }
  
  /**
   * Perform an appropriate action on each cycle of the simulation,
   * in this case advancing by dx,dy and reversing either direction
   * if we hit an edge of the simulation.
   */
  public void action() {
    x = x + dx;
    if (x < diameter/2 || x > maxx - diameter/2) {
      dx = -dx;
    }
    y = y + dy;
    if (y < diameter/2 || y > maxy - diameter/2) {
      dy = -dy;
    }
  }
  
  /**
   * Display this ball on the given graphics context
   * @param g the graphics context where this ball should be drawn
   */
  public void paintThing(Graphics g) {
    g.setColor(color);
    g.fillOval(x-diameter/2, y-diameter/2, diameter, diameter);
  }
}
