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
public class Circle1 {
    private Point center;  // Center point of the circle.
    private double rad;    // Radius of the circle.
    
    // Abstraction function:
    // AF(this) = a circle c such that
    //     c.center = this.center
    //     c.radius == this.rad
    
    // Rep invariant:
    // center != null && rad > 0
    
//  ...
}
