package hw3.test;

import hw3.*;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * This class contains a <em>small</em> set of test cases that can be used to
 * test the implementation of the RatNum class. For a more complete test, see
 * <tt>RatNumTest</tt>. This class, <tt>RatNumSmallTest</tt>, deliberately
 * omits some of the tests from <tt>RatNumTest</tt>, to permit comparing the
 * effects of the two test suites.
 */
public final class RatNumSmallTest {

    // Naming convention used throughout class: spell out number in
    // variable as its constructive form. Unary minus is notated with
    // the prefix "neg", and the solidus ("/") is notated with an 'I'
    // character. Thus, "1 + 2/3" becomes "one_plus_two_I_three".

    // some simple base RatNums
    private RatNum zero = new RatNum(0);
    private RatNum one = new RatNum(1);
    private RatNum negOne = new RatNum(-1);
    private RatNum two = new RatNum(2);
    private RatNum three = new RatNum(3);
    private RatNum one_I_two = new RatNum(1, 2);
    private RatNum one_I_three = new RatNum(1, 3);
    private RatNum one_I_four = new RatNum(1, 4);
    private RatNum two_I_three = new RatNum(2, 3);
    private RatNum three_I_four = new RatNum(3, 4);
    private RatNum negOne_I_two = new RatNum(-1, 2);

    // improper fraction
    private RatNum three_I_two = new RatNum(3, 2);

    // NaNs
    private RatNum one_I_zero = new RatNum(1, 0);
    private RatNum negOne_I_zero = new RatNum(-1, 0);
    private RatNum hundred_I_zero = new RatNum(100, 0);

    /**
     * ratnums: Set of varied ratnums (includes NaNs) set is { 0, 1, -1, 2, 1/2,
     * 3/2, 1/0, -1/0, 100/0 }
     */
    private RatNum[] ratNums = new RatNum[] { zero, one, negOne, two,
            one_I_two, negOne_I_two, three_I_two,
            /* NaNs */one_I_zero, negOne_I_zero, hundred_I_zero };

    /**
     * ratnans: Set of varied NaNs set is { 1/0, -1/0, 100/0 }
     */
    private RatNum[] ratNaNs = new RatNum[] { one_I_zero, negOne_I_zero,
            hundred_I_zero };

    /**
     * ratNonNaNs: Set of varied non-NaN ratNums set is ratNums - ratNaNs
     */
    private RatNum[] ratNonNaNs = new RatNum[] { zero, one, negOne, two,
            one_I_two, three_I_two };

    /**
     * Asserts that RatNum.toString() is equal to rep. This method depends on the
     * implementation of RatNum's "toString" and "equals" methods. Therefore,
     * one should verify (test) those methods before using this method is in
     * tests.
     */
    private void eq(RatNum ratNum, String rep) {
        assertEquals(rep, ratNum.toString());
    }

    // The actual test cases are below.
    //
    // The order of the test cases is important for producing useful
    // output. If a test uses a method of RatNum, it should test that
    // method before hand. For example, suppose one of the test cases
    // for "negate" is:
    //
    // "(new RatNum(1)).negate().equals(new RatNum(-1))"
    //
    // In this case, the test case relies on RatNum's "equals" method
    // in addition to "negate"; therefore, one should test "equals"
    // before "negate". Otherwise, it will be unclear if failing the
    // "negate" test is due "negate" having a bug or "equals" having a
    // bug. (Furthermore, the test depends on RatNum's constructor,
    // so it should also be tested beforehand.)
    //
    // In general, it is best to have as few dependences in your test
    // cases as possible. Doing so, will reduce the number of methods
    // that could cause a test case to fail, making it easier to find
    // the faulty method. In practice, one will usually need to
    // depend on a few core methods such as the constructors and
    // "equals" methods. Also, some of the test cases below depend on
    // the "toString" method because it made the cases easier to write.
    //
    // As a secondary concern to above, if one has access to the
    // source code of a class (as under glass box testing) one should
    // order tests such that a method is tested after all the methods
    // it depends on are tested. For example, in RatNum, the "sub"
    // method calls the "negate" method; therefore, one should test
    // "negate" before "sub". Following this methodology will make it
    // more clear that one should fix bugs in "negate" before looking
    // at the results of "sub" test because, "sub" could be correctly
    // written and the "sub" test case fails only be "negate" is
    // broken.
    //
    // If one does not have access to the source code (as is the case
    // of RatTermTest and RatPolyTest, because you are proving the
    // implementations), one can still take an educated guess as to
    // which methods depend on other methods, but don't worry about
    // getting it perfect.

    // First, we test the constructors in isolation of (without
    // depending on) all other RatNum methods.
    //
    // Unfortunately, without using any of RatNum's methods, all we
    // can do is call the constructors and ensure that "checkRep"
    // passes. While this is useful, it does not catch many types of
    // errors. For example, the constructor could always return a
    // RatNum, r, where r.numer = 1 and r.denom = 1. Being a valid
    // RatNum, r would pass "checkRep" but would be the wrong RatNum
    // in most cases.
    //
    // Given that we are unable to fully test the constructors, when
    // any other test case fails, it could be due to an error in the
    // constructor instead of an error in method the test case is
    // testing.
    //
    // If RatNum had public fields, this problem would not exist,
    // because we could check if the fields were set to the correct
    // values. This problem is really a case of a more general
    // problem of being unable to test private fields and methods of
    // classes. For example, we are also unable to test the gcd
    // method because it is private. Solutions to this general
    // problem include:
    //
    // (1) Make the private fields and methods public. (For example,
    // make numer, denom, and gcd public.) This in not done in
    // general because private fields have many benefits as will
    // be discussed in class.
    //
    // (2) Move the test suite code into RatNum and, thus, it would
    // have access to private memebers. (Maybe as a static inner
    // class [Don't worry if you don't know what this means yet.])
    // This is not done in general because it clutters the class
    // being tested, making it harder to understand.
    //
    // In practice, while testing, you may find it necessary to do (1)
    // or (2) temporarily with a test case that accesses private
    // fields or methods to track down a bug. But after finding the
    // bug, remember to revert your code back. Also for future
    // problem sets where you will be writing your own test suites,
    // make sure that your test suite runs correctly without (1) or
    // (2) being true.

    // (Note, all of these objects were already constructed above as
    // fields of this class (RatNumTest); thus, one could argue that
    // this test case is redundant. We included this test case anyhow
    // to give you an example of such a test case and because the
    // implementation of this class could change eliminating the
    // fields above.)
    @Test
    public void testOneArgConstructor() {
        new RatNum(0);
        new RatNum(1);
        new RatNum(-1);
        new RatNum(2);
        new RatNum(3);
    }

    @Test
    public void testTwoArgConstructor() {
        new RatNum(1, 2);
        new RatNum(1, 3);
        new RatNum(1, 4);
        new RatNum(2, 3);
        new RatNum(3, 4);

        new RatNum(-1, 2);

        // improper fraction
        new RatNum(3, 2);

        // NaNs
        new RatNum(1, 0);
        new RatNum(-1, 0);
        new RatNum(100, 0);
    }

    // Next, we test isNaN because it can be tested in isolation from
    // everything except the constructors. (All instance method tests
    // will depend on a constructor.)
    @Test
    public void testIsNaN() {
        for (int i = 0; i < ratNaNs.length; i++) {
            assertTrue(ratNaNs[i].isNaN());
        }
        for (int i = 0; i < ratNonNaNs.length; i++) {
            assertFalse(ratNonNaNs[i].isNaN());
        }
    }

    // Next, we test isPos and isNeg because we can easily test these
    // methods without depending on any other methods (except the
    // constructors).
    private void assertPos(RatNum n) {
        assertTrue(n.isPositive());
        assertFalse(n.isNegative());
    }

    private void assertNeg(RatNum n) {
        assertTrue(n.isNegative());
        assertFalse(n.isPositive());
    }

    @Test
    public void testIsPosAndIsNeg() {
        assertFalse(zero.isPositive());
        assertFalse(zero.isNegative());

        assertPos(one);
        assertNeg(negOne);
        assertPos(two);
        assertPos(three);

        assertPos(one_I_two);
        assertPos(one_I_three);
        assertPos(one_I_four);
        assertPos(two_I_three);
        assertPos(three_I_four);

        assertNeg(negOne_I_two);

        assertPos(three_I_two);

        assertPos(one_I_zero);
        assertPos(negOne_I_zero); // non-intuitive; see spec
        assertPos(hundred_I_zero);
    }

    // Next, we test doubleValue because the test does not require any
    // other RatNum methods (other than constructors).

    // asserts that two double's are within .0000001 of one another.
    // It is often impossible to assert that doubles are exactly equal
    // because of the idiosyncrasies of Java's floating point math.
    private void approxEq(double expected, double actual) {
        assertEquals(expected, actual, .0000001);
    }

    @Test
    public void testApprox() {
        approxEq(0.0, zero.doubleValue());
        approxEq(1.0, one.doubleValue());
        approxEq(-1.0, negOne.doubleValue());
        approxEq(2.0, two.doubleValue());
        approxEq(0.5, one_I_two.doubleValue());
        approxEq(2. / 3., two_I_three.doubleValue());
        approxEq(0.75, three_I_four.doubleValue());

        // To understand the use of "new Double(Double.NaN)" instead of
        // "Double.NaN", see the Javadoc for Double.equals().
        assertEquals(new Double(Double.NaN),
                     new Double(one_I_zero.doubleValue()));

        // use left-shift operator "<<" to create integer for 2^30
        RatNum one_I_twoToThirty = new RatNum(1, (1 << 30));
        double quiteSmall = 1. / Math.pow(2, 30);
        approxEq(quiteSmall, one_I_twoToThirty.doubleValue());
    }

    // Next, we test the equals method because that can be tested in
    // isolation from everything except the constructor and maybe
    // isNaN, which we just tested.
    // Additionally, this method will be very useful for testing other
    // methods.

    /**
     * This test check is equals is reflexive. In other words that x.equals(x)
     * is always true.
     */
    @Test
    public void testEqualsReflexive() {
        for (int i = 0; i < ratNums.length; i++) {
            assertEquals(ratNums[i], ratNums[i]);
        }
    }

    @Test
    public void testEquals() {

        // Some simple cases.
        assertEquals(one, one);
        assertEquals(one.add(one), two);
        // including negitives:
        assertEquals(negOne, negOne);

        // Some simple cases where the objects are different but
        // represent the same rational number. That is, x != y but
        // x.equals(y).
        assertEquals(new RatNum(1, 1), new RatNum(1, 1));
        assertEquals(new RatNum(1, 2), new RatNum(1, 2));

        // Check that equals works on fractions that were not
        // constructed in reduced form.
        assertEquals(one, new RatNum(2, 2));
        assertEquals(new RatNum(2, 2), one);
        // including negitives:
        assertEquals(negOne, new RatNum(-9, 9));
        assertEquals(new RatNum(-9, 9), negOne);
        // including double negitives:
        assertEquals(one_I_two, new RatNum(-13, -26));
        assertEquals(new RatNum(-13, -26), one_I_two);

        // Check that all NaN's are equals to one another.
        assertEquals(one_I_zero, one_I_zero);
        assertEquals(one_I_zero, negOne_I_zero);
        assertEquals(one_I_zero, hundred_I_zero);

        // Some simple cases checking for false positives.
        assertThat(one, not(zero));
        assertThat(zero, not(one));
        assertThat(one, not(two));
        assertThat(two, not(one));

        // Check that equals does not neglect sign.
        assertThat(one, not(negOne));
        assertThat(negOne, not(one));

        // Check that equals does not return false positives on
        // fractions.
        assertThat(one, not(one_I_two));
        assertThat(one_I_two, not(one));
        assertThat(one, not(three_I_two));
        assertThat(three_I_two, not(one));
    }

    // Now that we have verified equals, we will use it in the
    // rest or our tests.

    // Next, we test the toString and valueOf methods because we can test
    // them isolation of everything except the constructor and equals,
    // and they will be useful methods to aid with testing other
    // methods. (In some cases, it is easier to use valueOf("1/2") than
    // new RatNum(1, 2) as you will see below.)

    // Note that "eq" calls "toString" on its first argument.
    @Test
    public void testToStringSimple() {
        eq(zero, "0");

        eq(one, "1");

        RatNum four = new RatNum(4);
        eq(four, "4");

        eq(negOne, "-1");

        RatNum negFive = new RatNum(-5);
        eq(negFive, "-5");

        RatNum negZero = new RatNum(-0);
        eq(negZero, "0");
    }

    @Test
    public void testToStringFractions() {
        RatNum one_I_two = new RatNum(1, 2);
        eq(one_I_two, "1/2");

        RatNum three_I_two = new RatNum(3, 2);
        eq(three_I_two, "3/2");

        RatNum negOne_I_thirteen = new RatNum(-1, 13);
        eq(negOne_I_thirteen, "-1/13");

        RatNum fiftyThree_I_seven = new RatNum(53, 7);
        eq(fiftyThree_I_seven, "53/7");
    }

    @Test
    public void testToStringNaN() {
        RatNum one_I_zero = new RatNum(1, 0);
        eq(one_I_zero, "NaN");

        RatNum two_I_zero = new RatNum(2, 0);
        eq(two_I_zero, "NaN");

        RatNum negOne_I_zero = new RatNum(-1, 0);
        eq(negOne_I_zero, "NaN");

        RatNum zero_I_zero = new RatNum(0, 0);
        eq(zero_I_zero, "NaN");

        RatNum negHundred_I_zero = new RatNum(-100, 0);
        eq(negHundred_I_zero, "NaN");

        RatNum two_I_one = new RatNum(2, 1);
        eq(two_I_one, "2");

        RatNum zero_I_one = new RatNum(0, 1);
        eq(zero_I_one, "0");

        RatNum negOne_I_negTwo = new RatNum(-1, -2);
        eq(negOne_I_negTwo, "1/2");

        RatNum two_I_four = new RatNum(2, 4);
        eq(two_I_four, "1/2");

        RatNum six_I_four = new RatNum(6, 4);
        eq(six_I_four, "3/2");

        RatNum twentySeven_I_thirteen = new RatNum(27, 13);
        eq(twentySeven_I_thirteen, "27/13");

        RatNum negHundred_I_negHundred = new RatNum(-100, -100);
        eq(negHundred_I_negHundred, "1");
    }

    // helper function, "decode-and-check"
    private void decChk(String s, RatNum expected) {
        RatNum.valueOf(s).equals(expected);
    }

    // Note that decChk calls valueOf.
    @Test
    public void testValueOf() {
        decChk("0", zero);

        decChk("1", one);
        decChk("1/1", one);
        decChk("2/2", one);
        decChk("-1/-1", one);

        decChk("-1", negOne);
        decChk("1/-1", negOne);
        decChk("-3/3", negOne);

        decChk("2", two);
        decChk("2/1", two);
        decChk("-4/-2", two);

        decChk("1/2", one_I_two);
        decChk("2/4", one_I_two);

        decChk("3/2", three_I_two);
        decChk("-6/-4", three_I_two);

        decChk("NaN", one_I_zero);
        decChk("NaN", negOne_I_zero);
    }

    // Next, we test the arithmetic operations.
    //
    // We test them in our best guess of increasing difficultly and
    // likelihood of having depend on a previous method. (For
    // example, add could use sub as a subroutine.
    //
    // Note that our tests depend on toString and
    // equals, which we have already tested.

    @Test
    public void testNegate() {
        eq(zero.negate(), "0");
        eq(one.negate(), "-1");
        eq(negOne.negate(), "1");
        eq(two.negate(), "-2");
        eq(three.negate(), "-3");

        eq(one_I_two.negate(), "-1/2");
        eq(one_I_three.negate(), "-1/3");
        eq(one_I_four.negate(), "-1/4");
        eq(two_I_three.negate(), "-2/3");
        eq(three_I_four.negate(), "-3/4");

        eq(three_I_two.negate(), "-3/2");

        eq(one_I_zero.negate(), "NaN");
        eq(negOne_I_zero.negate(), "NaN");
        eq(hundred_I_zero.negate(), "NaN");
    }

    @Test
    public void testAddSimple() {
        eq(zero.add(zero), "0");
        eq(zero.add(one), "1");
        eq(one.add(zero), "1");
        eq(one.add(one), "2");
        eq(one.add(negOne), "0");
        eq(one.add(two), "3");
        eq(two.add(two), "4");
    }

    @Test
    public void testAddComplex() {
        eq(one_I_two.add(zero), "1/2");
        eq(one_I_two.add(one), "3/2");
        eq(one_I_two.add(one_I_two), "1");
        eq(one_I_two.add(one_I_three), "5/6");
        eq(one_I_two.add(negOne), "-1/2");
        eq(one_I_two.add(two), "5/2");
        eq(one_I_two.add(two_I_three), "7/6");
        eq(one_I_two.add(three_I_four), "5/4");

        eq(one_I_three.add(zero), "1/3");
        eq(one_I_three.add(two_I_three), "1");
        eq(one_I_three.add(three_I_four), "13/12");
    }

    @Test
    public void testAddImproper() {
        eq(three_I_two.add(one_I_two), "2");
        eq(three_I_two.add(one_I_three), "11/6");
        eq(three_I_four.add(three_I_four), "3/2");

        eq(three_I_two.add(three_I_two), "3");
    }

    @Test
    public void testAddOnNaN() {
        // each test case (addend, augend) drawn from the set
        // ratNums x ratNaNs

        for (int i = 0; i < ratNums.length; i++) {
            for (int j = 0; j < ratNaNs.length; j++) {
                eq(ratNums[i].add(ratNaNs[j]), "NaN");
                eq(ratNaNs[j].add(ratNums[i]), "NaN");
            }
        }

    }

    @Test
    public void testAddTransitively() {
        eq(one.add(one).add(one), "3");
        eq(one.add(one.add(one)), "3");
        eq(zero.add(zero).add(zero), "0");
        eq(zero.add(zero.add(zero)), "0");
        eq(one.add(two).add(three), "6");
        eq(one.add(two.add(three)), "6");

        eq(one_I_three.add(one_I_three).add(one_I_three), "1");
        eq(one_I_three.add(one_I_three.add(one_I_three)), "1");

        eq(one_I_zero.add(one_I_zero).add(one_I_zero), "NaN");
        eq(one_I_zero.add(one_I_zero.add(one_I_zero)), "NaN");

        eq(one_I_two.add(one_I_three).add(one_I_four), "13/12");
        eq(one_I_two.add(one_I_three.add(one_I_four)), "13/12");
    }

    @Test
    public void testSubSimple() {
        eq(zero.sub(one), "-1");
        eq(zero.sub(zero), "0");
        eq(one.sub(zero), "1");
        eq(one.sub(one), "0");
        eq(two.sub(one), "1");
        eq(one.sub(negOne), "2");
        eq(one.sub(two), "-1");
        eq(one.sub(three), "-2");
    }

    @Test
    public void testSubComplex() {
        eq(one.sub(one_I_two), "1/2");
        eq(one_I_two.sub(one), "-1/2");
        eq(one_I_two.sub(zero), "1/2");
        eq(one_I_two.sub(two_I_three), "-1/6");
        eq(one_I_two.sub(three_I_four), "-1/4");
    }

    @Test
    public void testSubImproper() {
        eq(three_I_two.sub(one_I_two), "1");
        eq(three_I_two.sub(one_I_three), "7/6");
    }

    /*
     * public void testSubOnNaN() { // analogous to testAddOnNaN()
     *
     * for (int i=0; i<ratNums.length; i++) { for ( int j=0; j<ratNaNs.length;
     * j++) { eq( ratNums[i].sub(ratNaNs[j]), "NaN" ); eq(
     * ratNaNs[j].sub(ratNums[i]), "NaN" ); } } }
     */
    @Test
    public void testSubTransitively() {
        // subtraction is not transitive; testing that operation is
        // correct when *applied transitivitely*, not that it obeys
        // the transitive property

        eq(one.sub(one).sub(one), "-1");
        eq(one.sub(one.sub(one)), "1");
        eq(zero.sub(zero).sub(zero), "0");
        eq(zero.sub(zero.sub(zero)), "0");
        eq(one.sub(two).sub(three), "-4");
        eq(one.sub(two.sub(three)), "2");

        eq(one_I_three.sub(one_I_three).sub(one_I_three), "-1/3");
        eq(one_I_three.sub(one_I_three.sub(one_I_three)), "1/3");

        // eq( one_I_zero.sub(one_I_zero).sub(one_I_zero), "NaN" );
        // eq( one_I_zero.sub(one_I_zero.sub(one_I_zero)), "NaN" );

        eq(one_I_two.sub(one_I_three).sub(one_I_four), "-1/12");
        eq(one_I_two.sub(one_I_three.sub(one_I_four)), "5/12");
    }

    @Test
    public void testMulProperties() {
        // zero property
        for (int i = 0; i < ratNonNaNs.length; i++) {
            eq(zero.mul(ratNonNaNs[i]), "0");
            eq(ratNonNaNs[i].mul(zero), "0");
        }

        // one property
        for (int i = 0; i < ratNonNaNs.length; i++) {
            eq(one.mul(ratNonNaNs[i]), ratNonNaNs[i].toString());
            eq(ratNonNaNs[i].mul(one), ratNonNaNs[i].toString());
        }

        // negOne property
        for (int i = 0; i < ratNonNaNs.length; i++) {
            eq(negOne.mul(ratNonNaNs[i]), ratNonNaNs[i].negate().toString());
            eq(ratNonNaNs[i].mul(negOne), ratNonNaNs[i].negate().toString());
        }
    }

    @Test
    public void testMulSimple() {
        eq(two.mul(two), "4");
        eq(two.mul(three), "6");
        eq(three.mul(two), "6");
    }

    @Test
    public void testMulComplex() {
        eq(one_I_two.mul(two), "1");
        eq(two.mul(one_I_two), "1");
        eq(one_I_two.mul(one_I_two), "1/4");
        eq(one_I_two.mul(one_I_three), "1/6");
        eq(one_I_three.mul(one_I_two), "1/6");
    }

    @Test
    public void testMulImproper() {
        eq(three_I_two.mul(one_I_two), "3/4");
        eq(three_I_two.mul(one_I_three), "1/2");
        eq(three_I_two.mul(three_I_four), "9/8");
        eq(three_I_two.mul(three_I_two), "9/4");
    }

    @Test
    public void testMulOnNaN() {
        // analogous to testAddOnNaN()

        for (int i = 0; i < ratNums.length; i++) {
            for (int j = 0; j < ratNaNs.length; j++) {
                eq(ratNums[i].mul(ratNaNs[j]), "NaN");
                eq(ratNaNs[j].mul(ratNums[i]), "NaN");
            }
        }
    }

    @Test
    public void testMulTransitively() {
        eq(one.mul(one).mul(one), "1");
        eq(one.mul(one.mul(one)), "1");
        eq(zero.mul(zero).mul(zero), "0");
        eq(zero.mul(zero.mul(zero)), "0");
        eq(one.mul(two).mul(three), "6");
        eq(one.mul(two.mul(three)), "6");

        eq(one_I_three.mul(one_I_three).mul(one_I_three), "1/27");
        eq(one_I_three.mul(one_I_three.mul(one_I_three)), "1/27");

        eq(one_I_zero.mul(one_I_zero).mul(one_I_zero), "NaN");
        eq(one_I_zero.mul(one_I_zero.mul(one_I_zero)), "NaN");

        eq(one_I_two.mul(one_I_three).mul(one_I_four), "1/24");
        eq(one_I_two.mul(one_I_three.mul(one_I_four)), "1/24");
    }

    @Test
    public void testDivSimple() {
        eq(zero.div(zero), "NaN");
        eq(zero.div(one), "0");
        eq(one.div(zero), "NaN");
        eq(one.div(one), "1");
        eq(one.div(negOne), "-1");
        eq(one.div(two), "1/2");
        eq(two.div(two), "1");
    }

    @Test
    public void testDivComplex() {
        eq(one_I_two.div(zero), "NaN");
        eq(one_I_two.div(one), "1/2");
        eq(one_I_two.div(one_I_two), "1");
        eq(one_I_two.div(one_I_three), "3/2");
        eq(one_I_two.div(negOne), "-1/2");
        eq(one_I_two.div(two), "1/4");
        eq(one_I_two.div(two_I_three), "3/4");
        eq(one_I_two.div(three_I_four), "2/3");

        eq(one_I_three.div(zero), "NaN");
        eq(one_I_three.div(two_I_three), "1/2");
        eq(one_I_three.div(three_I_four), "4/9");
    }

    @Test
    public void testDivImproper() {
        eq(three_I_two.div(one_I_two), "3");
        eq(three_I_two.div(one_I_three), "9/2");
        eq(three_I_two.div(three_I_two), "1");
    }

    @Test
    public void testDivOnNaN() {
        // each test case (addend, augend) drawn from the set
        // ratNums x ratNaNs

        for (int i = 0; i < ratNums.length; i++) {
            for (int j = 0; j < ratNaNs.length; j++) {
                eq(ratNums[i].div(ratNaNs[j]), "NaN");
                eq(ratNaNs[j].div(ratNums[i]), "NaN");
            }
        }

    }

    @Test
    public void testDivTransitively() {
        // (same note as in testSubTransitively re: transitivity property)

        eq(one.div(one).div(one), "1");
        eq(one.div(one.div(one)), "1");
        eq(zero.div(zero).div(zero), "NaN");
        eq(zero.div(zero.div(zero)), "NaN");
        eq(one.div(two).div(three), "1/6");
        eq(one.div(two.div(three)), "3/2");

        eq(one_I_three.div(one_I_three).div(one_I_three), "3");
        eq(one_I_three.div(one_I_three.div(one_I_three)), "1/3");

        eq(one_I_zero.div(one_I_zero).div(one_I_zero), "NaN");
        eq(one_I_zero.div(one_I_zero.div(one_I_zero)), "NaN");

        eq(one_I_two.div(one_I_three).div(one_I_four), "6");
        eq(one_I_two.div(one_I_three.div(one_I_four)), "3/8");

    }

    // Finally, we test compare. We do so last, because compare may
    // depend on sub, isNaN, and/or equals, so we want to test those
    // methods first.

    private void assertGreater(RatNum larger, RatNum smaller) {
        assertTrue(larger.compareTo(smaller) > 0);
        assertTrue(smaller.compareTo(larger) < 0);
    }

    @Test
    public void testCompareToReflexive() {
        // reflexivitiy: x.compare(x) == 0.
        for (int i = 0; i < ratNums.length; i++) {
            assertEquals(ratNums[i], ratNums[i]);
        }
    }

    @Test
    public void testCompareToNonFract() {
        assertGreater(one, zero);
        assertGreater(one, negOne);
        assertGreater(two, one);
        assertGreater(two, zero);
        assertGreater(zero, negOne);
    }

    @Test
    public void testCompareToFract() {
        assertGreater(one, one_I_two);
        assertGreater(two, one_I_three);
        assertGreater(one, two_I_three);
        assertGreater(two, two_I_three);
        assertGreater(one_I_two, zero);
        assertGreater(one_I_two, negOne);
        assertGreater(one_I_two, negOne_I_two);
        assertGreater(zero, negOne_I_two);
    }

    @Test
    public void testCompareToNaNs() {
        for (int i = 0; i < ratNaNs.length; i++) {
            for (int j = 0; j < ratNaNs.length; j++) {
                assertEquals(ratNaNs[i], ratNaNs[j]);
            }
            for (int j = 0; j < ratNonNaNs.length; j++) {
                assertGreater(ratNaNs[i], ratNonNaNs[j]);
            }
        }
    }

}
