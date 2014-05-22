/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw0.test;
import hw0.*;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * BallTest is a simple glassbox test of the Ball class.
 *
 * @see hw0.Ball
 */
public class BallTest {

    private static Ball b = null;

    private static final double BALL_VOLUME = 20.0;
    private static final double JUNIT_DOUBLE_DELTA = 0.0001;

    @BeforeClass 
    public static void setupBeforeTests() throws Exception {
        b = new Ball(BALL_VOLUME);
    }

    /** Test to see that Ball returns the correct volume when queried. */
    @Test
    public void testVolume() {
        assertEquals("b.getVolume()",
                     BALL_VOLUME, b.getVolume(), JUNIT_DOUBLE_DELTA);
    }

}
