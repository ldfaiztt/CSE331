package section3;

import java.awt.Point;

/**
 * Represents a circle located on the Cartesian coordinate plane.
 * @specfield center : Point    // The midpoint of the circle.
 * @specfield radius : double   // The radius of the circle.
 * @derivedfield circumference : double // circumference = pi*radius^2
 *                                      // The circumference of the circle.  
 * @derivedfield area : double // area = pi*radius^2. The area of the circle.
 * 
 * @author Krysta Yousoufian
 *
 */
public class Circle2 {
    private Point center;   // Center point of the circle.
    private Point edge;     // Point on the edge of the circle.
    
    // Abstraction function:
    // AF(this) = a circle c such that
    //     c.center = this.center
    //     c.radius == sqrt((center.x-edge.x)^2 + (center.y-edge.y)^2)
    //                 i.e., the Euclidean distance between center and edge
    
    // Rep invariant:
    // center != null && edge ! null && !center.equals(edge)
    // (Note: your rep invariant doesn't need to look like Java code, but in this
    // case that was the simplest way to express it)
    
//    ...
}
