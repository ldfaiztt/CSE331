package section3;

import java.awt.Point;

/**
 * Represents a circle located on the Cartesian coordinate plane.
 * @specfield center : Point    // The midpoint of the circle.
 * @specfield radius : double   // The radius of the circle.
 * @derivedfield circumference : double // circumference = 2*pi*radius
 *                                      // The circumference of the circle.  
 * @derivedfield area : double // area = pi*radius^2. The area of the circle.
 * 
 * @author Krysta Yousoufian
 *
 */
public class Circle3 {
    // Opposite corners of a square inscribed in this circle.
    // The line segment between them passes through the centerpoint of the circle
    // and has a length equal to the diameter of the circle.
    private Point corner1, corner2;
    
    // Abstraction function:
    // AF(this) = a circle c such that
    //     c.center.x = (corner1.x + corner2.x) / 2
    //     c.center.y = (corner.y + corner2.y) / 2
    //     c.radius = (1/2)*sqrt((corner1.x-corner2.x)^2 + (corner1.y-corner2.y)^2)
    //                i.e., half the Euclidean distance between corner1 and corner2
    
    // Rep invariant:
    // corner1 != null && corner2 != null && !corner1.equals(corner2)
    // (Note: your rep invariant doesn't need to look like Java code, but in this
    // case that was the simplest way to express it)
    
//    ...
}
