/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw0.test;
import hw0.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.greaterThan;

/**
 * BoxTest is a glassbox test of the Box class.
 *
 * Recall that like a BallContainer, the Box is a container for Balls and you
 * can only put a Ball into a Box once. After you put the Ball into
 * the Box, further attempts to do so will fail, since the Ball is
 * already in the Box! Similarly, you cannot expect to remove a Ball
 * from a Box if it is not inside the Box.
 *
 * In addition, a Box differs from a ballcontainer because it only has a finite
 * volume. As Balls get put into a Box, it gets filled up. Once a Box
 * is full, further attempts to put Balls into the Box will also fail.
 *
 * @see hw0.Ball
 * @see hw0.BallContainer
 * @see hw0.Box
 */
public class BoxTest {

    private static Box box = null;
    private static Ball[] b = null;

    private static final int NUM_BALLS_TO_TEST = 5;
    private static final int BOX_CAPACITY = NUM_BALLS_TO_TEST - 1;
    private static final double BALL_UNIT_VOLUME = 10.0;
    private static final double JUNIT_DOUBLE_DELTA = 0.0001;
    private static final int TRIES_FOR_BALLS_TEST = 3;

    @BeforeClass
    public static void setupBeforeTests() throws Exception {
        assertThat("Test case error, you must test at least 1 Ball!!",
                   NUM_BALLS_TO_TEST, greaterThan(0));
        assertThat("This test case is set up assuming that the box cannot contain all the balls, please check and change parameters!",
                   NUM_BALLS_TO_TEST, greaterThan(BOX_CAPACITY));

        double box_volume = 0;

        // Let's create the balls we need.
        b = new Ball[NUM_BALLS_TO_TEST];
        for (int i=0; i<NUM_BALLS_TO_TEST; i++) {
            if (i<BOX_CAPACITY) {
                box_volume += (i+1)*BALL_UNIT_VOLUME;
            }
            b[i] = new Ball((i+1)*BALL_UNIT_VOLUME);
        }

        // Now, we create the box once we figure out how big a box we
        // need.
        box = new Box(box_volume);

    }

    /** Test to check that Box.add(Ball) is implemented correctly */
    @Test
    public void testAdd() {
        box.clear();
        for (int i=0; i<BOX_CAPACITY; i++) {
            assertTrue("Box.add(Ball) failed to add a new Ball!",
                       box.add(b[i]));
            assertFalse("Box.add(Ball) seems to allow the same Ball to be added twice!",
                        box.add(b[i]));
            assertTrue("Box does not contain a ball after it is supposed to have been added!",
                       box.contains(b[i]));
        }
        for (int i=BOX_CAPACITY; i<NUM_BALLS_TO_TEST; i++) {
            assertFalse("Box.add(Ball) allows a Ball to be added even though it is already full!",
                        box.add(b[i]));
        }
    }

    /** Test to check that Box.getBallsFromSmallest() is implemented correctly */
    @Test
    public void testGetBalls() {
        Random rnd = new Random();

        for (int k=0; k<TRIES_FOR_BALLS_TEST; k++) {

            box.clear();

            // Let's put all the balls into a list.
            LinkedList<Ball> list = new LinkedList<Ball>();
            for (int i=0; i<BOX_CAPACITY; i++) {
                list.add(b[i]);
            }

            // First we add the balls to the box in some random order.
            for (int i=0; i<BOX_CAPACITY; i++) {
                box.add(list.remove(rnd.nextInt(list.size())));
            }

            int contentsSize = box.size();
            // Next we call the iterator and check that the balls come out
            // in the correct order.
            Iterator<Ball> it = box.getBallsFromSmallest();
            int count = 0;
            while (it.hasNext() && count < BOX_CAPACITY) {
                Ball ball = it.next();
                assertEquals("Balls are not returned by Box.getBallsFromSmallest() iterator in the correct order",
                             b[count], ball);
                if (b[count] != ball) {
                    break;
                }
                count++;
            }
            assertEquals("Box.getBallsFromSmallest() did not return all the balls",
                         BOX_CAPACITY, count);
            assertEquals("Number of balls in box was modified",
                         contentsSize, box.size());
        }
    }

    /**
     * Test to check that Box.remove(Ball) is implemented
     * correctly. Depending on how <code>getBallsFromSmallest()</code>
     * is implemented, remove() might have to be overridden and this
     * test helps ensure that remove() is not broken in the process.
     */
    @Test
    public void testRemove() {
        box.clear();
        assertFalse("Box.remove(Ball) should fail because box is empty, but it didn't!",
                    box.remove(b[0]));
        for (int i=0; i<BOX_CAPACITY; i++) {
            box.clear();
            for (int j=0; j<i; j++) {
                box.add(b[j]);
            }
            for (int j=0; j<i; j++) {
                assertTrue("Box.remove(Ball) failed to remove a Ball that is supposed to be inside",
                           box.remove(b[j]));
                assertFalse("Box still contains a ball after it is supposed to have been removed!",
                            box.contains(b[j]));
            }
            for (int j=i; j<NUM_BALLS_TO_TEST; j++) {
                assertFalse("Box.remove(Ball) did not fail for a Ball that is not inside",
                            box.remove(b[j]));
            }
        }
    }


    /** Test to check that Box.clear() is implemented correctly */
    @Test
    public void testClear() {
        box.clear();
        assertEquals("Box is not empty after being cleared!", 0, box.size());
        box.add(b[0]);
        box.clear();
        assertEquals("Box is not empty after being cleared!", 0, box.size());
    }

    /** Test to check that check that we can put a Ball into a Box */
    @Test
    public void testVolume() {
        box.clear();
        assertEquals("Volume of empty Box is not zero!",
                     0, box.getVolume(), JUNIT_DOUBLE_DELTA);
        for (int i=0; i<BOX_CAPACITY; i++) {
            box.add(b[i]);
            assertEquals("Volume of Box with one ball",
                         (i+1)*(i+2)*BALL_UNIT_VOLUME/2, box.getVolume(),
                         JUNIT_DOUBLE_DELTA);
        }
    }

    /** Test to check that size() returns the correct number. */
    @Test
    public void testSize() {
        box.clear();
        assertEquals("size() of empty Box is not zero!", 0, box.size());
        for (int i=0; i<BOX_CAPACITY; i++) {
            box.add(b[i]);
            assertEquals("size() of Box with "+(i+1)+" ball(s)",
                         i+1, box.size());
        }
    }

    /** Test to check that size() returns the correct number. */
    @Test
    public void testContains() {
        box.clear();
        for (int i=0; i<BOX_CAPACITY; i++) {
            assertFalse("Empty Box seems to contain a ball!",
                        box.contains(b[i]));
        }
        for (int i=0; i<BOX_CAPACITY; i++) {
            box.add(b[i]);
            assertTrue("Box does not contain a Ball that is supposed to be inside!",
                       box.contains(b[i]));
            for (int j=i+1; j<BOX_CAPACITY; j++) {
                assertFalse("Box seems to contain a Ball that is not inside!",
                            box.contains(b[j]));
            }
        }
    }

    /**
     * Test to check that iterator() is implemented correctly.
     */
    @Test
    public void testIterator() {
        Set<Ball> allBalls = new HashSet<Ball>();
        Set<Ball> seenBalls = new HashSet<Ball>();
        box.clear();
        for (Ball aBall : b) {
            box.add(aBall);
            allBalls.add(aBall);
        }
        int i = 0;
        for (Ball aBall : box) {
            assertTrue("Iterator returned a ball that isn't in the container: " + aBall,
                       allBalls.contains(aBall));
            assertFalse("Iterator returned the same ball twice: " + aBall,
                        seenBalls.contains(aBall));
            seenBalls.add(aBall);
            i++;
        }
        assertEquals("BallContainer iterator did not return enough items!",
                     b.length-1, i);
    }
}
