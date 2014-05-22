/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw0.test;
import hw0.RandomHello;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

/**
 * RandomHelloTest is a simple test of the RandomHello class that is
 * to be written by the students. This test just makes sure that the
 * program does not crash and that it prints at least 5 different
 * greetings.
 */
public class RandomHelloTest {

    /** Number of times to run the greeting test until we're quite sure we'd have seen all the greetings */
    private int TIMES_TO_TEST = 1000;

    /** Required number of greetings */
    private int REQUIRED_NUMBER_OF_GREETINGS = 5;

    /**
     * Tests that RandomHello does not crash.
     */
    @Test
    public void testCrash() {
        /* If RandomHello.main() throws an exception, it will
         * propagate outside testCrash() and JUnit will flag
         * an error. */
        RandomHello.main(new String[0]);
    }


    /**
     * Tests that the greetings are indeed random and that there are
     * at least 5 different ones.
     */
    @Test
    public void testGreetings() {
        RandomHello world = new RandomHello();
        Set<String> set = new HashSet<String>();

        for (int i=0; i< TIMES_TO_TEST; i++) {
            String greeting = world.getGreeting();
            if (!set.contains(greeting)) {
                set.add(greeting);
            }
        }
        assertEquals("Wrong number of greetings in RandomHello",
                     REQUIRED_NUMBER_OF_GREETINGS, set.size());
    }


}
