package hw7.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * SpecificationTests is a test suite used to encapsulate all
 * tests specific to the specification of this homework.
 *
 * ScriptFileTests should be the only test class listed in
 * SpecificationTests. If you are tempted to add other classes, recall that
 * any tests you add to SpecificationTests must be valid tests for any other
 * student's implementation for this assignment, even though other students
 * will have designed a different public API.
 * 
 **/
@RunWith(Suite.class)
@SuiteClasses({ ScriptFileTests.class})
public final class SpecificationTests
{
    //this class is a placeholder for the suite, so it has no members.
}

