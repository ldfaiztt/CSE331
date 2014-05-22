package hw7;

/**
 * This class represents coordinates of a point.
 * 
 * @specfield x_coordinate : double
 * @specfield y_coordinate : double
 * 
 * @author Chun-Wei Chen
 * @version 05/28/13
 */
public class Coordinates {
	public static final double epsilon = 0.00000001;
	
	private Double x; // x coordinate 
	private Double y; // y coordinate
	
	// Rep invariant:
	//		x != null && y != null
	
	// Abstract function:
	// 		AF(this) = coordinates of a point p such that 
	//			p.x_coordinate = this.x;
	//			p.y_coordinate = this.y;
	
	/**
	 * Constructs coordinates of a point. 
	 * 
	 * @param x x coordinate of the point
	 * @param y y coordinate of the point
	 */
	public Coordinates(double x, double y) {
		this.x = x;
		this.y = y;
		checkRep();
	}
	
	/**
	 * Returns x coordinate of the point.
	 * 
	 * @return x coordinate of the point
	 */
	public double getX() {
		checkRep();
		return x;
	}
	
	/**
	 * Returns y coordinate of the point.
	 * 
	 * @return y coordinate of the point
	 */
	public double getY() {
		checkRep();
		return y;
	}
	
	/**
	 * Compares this object against the specified object.
	 * 
	 * @param o object to be compared
	 * @return true if o represents the same coordinates
	 */
	@Override
	public boolean equals(/*@Nullable*/ Object o) {
		checkRep();
		if (!(o instanceof Coordinates)) {
			checkRep();
			return false;
		}
		
		Coordinates c = (Coordinates) o;
		checkRep();
		return (c.x.equals(x)) && (c.y.equals(y));
	}
	
	/**
	 * Returns a hash code for this Coordinates object.
	 * 
	 * @return a hash code for this Coordinates object
	 */
	@Override
	public int hashCode() {
		checkRep();
		return x.hashCode() + y.hashCode();
	}
	
	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() {
		if (x == null)
			throw new RuntimeException("x coordinate cannot be null.");
		
		if (y == null)
			throw new RuntimeException("y coordinate cannot be null.");
	}
}