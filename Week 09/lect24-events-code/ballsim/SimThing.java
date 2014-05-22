import java.awt.*;

/**
 * Basic interface for objects in the simple simulation world.
 * Any object that implements this can be added to the simulation.
 * CSE 331 12au, 13wi
 */

public interface SimThing {
  /**
   * Perform desired action for one simulation cycle.
   */
  public void action( );
  
  /**
   * Draw graphical representation of this SimThing.
   * @param g Graphics context where the drawing should occur
   */
  public void paintThing(Graphics g);
}
