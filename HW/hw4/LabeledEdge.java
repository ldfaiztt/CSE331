 package hw4;

/**
 * <b>LabeledEdge</b> represents an outgoing edge with label of a node and 
 * the destination of the edge.
 * <p>
 * @specfield destination : String  // The destination of the edge.
 * @specfield label : String        // The label of the edge.
 * 
 * @author Cun-Wei Chen
 * @version 05/18/13
 */
public class LabeledEdge<T, E extends Comparable<E>> 
			 implements Comparable<LabeledEdge<T, E>> {
	// Rep invariant:
	//     dest != null && label != null
	
	// Abstract function:
	//     AF(this) = a labeled edge without origin le such that
	//                le.destination = this.dest
	//                le.label = this.label
	
	private final T dest;   // destination of this edge
	private final E label;  // label of this edge
	
	/**
	 * Creates a labeled edge.
	 * 
	 * @param d destination of the edge
	 * @param l label of the edge
	 * @requires d != null && l != null
	 * @effects constructs a labeled edge with 
	 * destination <var>d</var> and label <var>l</var>
	 */
	public LabeledEdge(T d, E l) {
		if (d == null || l == null)
			throw new IllegalArgumentException("Arguments cannot be null.");
		
		dest = d;
		label = l;
		checkRep();
	}
	
	/**
	 * Returns the destination of this edge.
	 * 
	 * @return the destination of this edge
	 */
	public T getDest() {
		checkRep();
		return dest;
	}
	
	/**
	 * Returns the label of this edge.
	 * 
	 * @return the label of this edge
	 */
	public E getLabel() {
		checkRep();
		return label;
	}
	
	/**
	 * Returns string representation of this edge.
	 * 
	 * @return string representation of this edge
	 */
	@Override
	public String toString() {
		checkRep();
		String result = dest.toString() + "(" + label.toString() + ")";
		checkRep();
		return result;
	}
	
	/**
	 * Returns true if o represent same edge as this edge.
	 * 
	 * @param o object to be compared
	 * @return true if o represents same edge 
	 * (same destination and same label) as this edge
	 */
	@Override
	public boolean equals(/*@Nullable*/ Object o) {
		checkRep();
		if (!(o instanceof LabeledEdge<?, ?>))
			return false;
		
		LabeledEdge<?, ?> le = (LabeledEdge<?, ?>) o;
		checkRep();
		return dest.equals(le.dest) && label.equals(le.label);
	}
	
	/**
	 * Return hash code of this edge.
	 * 
	 * @return hash code of this edge
	 */
	@Override
	public int hashCode() {
		checkRep();
		return dest.hashCode() + label.hashCode();
	}
	
	/**
	 * Compares this object with the specified object for order. Returns 
	 * a negative integer, zero, or a positive integer as this object 
	 * is less than, equal to, or greater than the specified object.
	 * 
	 * @param le object to be compared
	 * @return a negative integer, zero, or a positive integer as 
	 * this object is less than, equal to, or greater than 
	 * the specified object
	 */
	@Override
	public int compareTo(LabeledEdge<T, E> le) {
		checkRep();
		
		// compare label first
		if (!(label.equals(le.label))) {
			checkRep();
			return label.compareTo(le.label);
		}
		
		// if label is the same as this, compare destination
		// using their hashcode
		if (!(dest.equals(le.dest))) {
			checkRep();
			return dest.hashCode() - le.dest.hashCode();
		}
		
		checkRep();
		return 0;
	}
	
	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() throws RuntimeException {
		if (dest == null)
			throw new RuntimeException("Destination cannot be null.");
		
		if (label == null)
			throw new RuntimeException("Label cannot be null.");
	}
}