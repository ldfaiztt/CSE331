/**
 * Basic interface for viewers of a SimModel.  A SimView can be added
 * to a SimModel, which will periodically notify the SimView so it can
 * update its display after changes occur in the model.  
 * CSE 331 12au, 13wi
 */

public interface SimView {
  /**
   * Notify this SimView so it can update its display of the SimModel.
   */
  public void notifyViewer();
}
