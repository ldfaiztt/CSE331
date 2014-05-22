package hw3.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * SpecificationTests is a simple TestSuite that includes and runs all the tests
 * in {@link RatNumTest}, {@link RatPolyTest}, and {@link RatPolyStackTest}.
 */

@RunWith(Suite.class)
@SuiteClasses({ RatNumTest.class,
                RatTermTest.class,
                RatPolyTest.class,
		RatPolyStackTest.class })
public final class SpecificationTests {
    // This class is a placeholder for the suite, so it has no members.
    // The @SuiteClasses annotation lists the elements of the suite.
}
