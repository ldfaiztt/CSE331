package hw3.test;

import hw3.*;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the RatPoly class.
 * <p>
 */
public final class RatPolyTest {
  private final double JUNIT_DOUBLE_DELTA = 0.00001;

  // get a RatNum for an integer
  private static RatNum num(int i) {
    return new RatNum(i);
  }

  // convenient way to make a RatPoly
  private RatPoly poly(int coef, int expt) {
    return new RatPoly(coef, expt);
  }

  // Convenient way to make a quadratic polynomial, arguments
  // are just the coefficients, highest degree term to lowest
  private RatPoly quadPoly(int x2, int x1, int x0) {
    RatPoly ratPoly = new RatPoly(x2, 2);
    return ratPoly.add(poly(x1, 1)).add(poly(x0, 0));
  }

  // convenience for valueOf
  private RatPoly valueOf(String s) {
    return RatPoly.valueOf(s);
  }

  // convenience for zero RatPoly
  private RatPoly zero() {
    return new RatPoly();
  }

  // only toString is tested here
  private void eq(RatPoly p, String target) {
    String t = p.toString();
    assertEquals(target, t);
  }

  private void eq(RatPoly p, String target, String message) {
    String t = p.toString();
    assertEquals(message, target, t);
  }

  // parses s into p, and then checks that it is as anticipated
  // forall i, valueOf(s).coeff(anticipDegree - i) = anticipCoeffForExpts(i)
  // (anticipDegree - i) means that we expect coeffs to be expressed
  // corresponding to decreasing expts
  private void eqP(String s, int anticipDegree, RatNum[] anticipCoeffs) {
    RatPoly p = valueOf(s);
    assertEquals(anticipDegree, p.degree());
    for (int i = 0; i <= anticipDegree; i++) {
      assertTrue("wrong coeff; \n" + "anticipated: " + anticipCoeffs[i]
          + "; received: " + p.getTerm(anticipDegree - i).getCoeff()
          + "\n" + " received: " + p + " anticipated:" + s, p
          .getTerm(anticipDegree - i).getCoeff().equals(
              anticipCoeffs[i]));
    }
  }

  // added convenience: express coeffs as ints
  private void eqP(String s, int anticipDegree, int[] intCoeffs) {
    RatNum[] coeffs = new RatNum[intCoeffs.length];
    for (int i = 0; i < coeffs.length; i++) {
      coeffs[i] = num(intCoeffs[i]);
    }
    eqP(s, anticipDegree, coeffs);
  }

  // make sure that unparsing a parsed string yields the string itself
  private void assertToStringWorks(String s) {
    assertEquals(s, valueOf(s).toString());
  }

  RatPoly poly1, neg_poly1, poly2, neg_poly2, poly3, neg_poly3;

  //SetUp Method depends on RatPoly add and negate
  //  Because all tests run this method before executing, ALL TESTS WILL FAIL
  //  until .add and .negate do not throw exceptions. Also, any incorrectness
  //  in .add and .negate may have unforseen consiquences elsewhere in the tests,
  //  so it is a good idea to make sure these two methods are correct before
  //  moving on to others.
  //
  //Tests that are intended to verify add or negate should not use variables
  //declared in this setUp method
  @Before
  public void setUp(){
    //poly1 = 1*x^1 + 2*x^2 + 3*x^3 + 4*x^4 + 5*x^5
    poly1 = RatPoly.valueOf("1*x^1+2*x^2+3*x^3+4*x^4+5*x^5");

    //neg_poly1 = -1*x^1 + -2*x^2 + -3*x^3 + -4*x^4 + -5*x^5
    neg_poly1 = poly1.negate();

    //poly2 = 6*x^2 + 7*x^3 + 8*x^4
    poly2 = RatPoly.valueOf("6*x^2+7*x^3+8*x^4");

    //neg_poly2 = -6*x^2 + -7*x^3 + -8*x^4
    neg_poly2 = poly2.negate();

    // poly3 = 9*x^3 + 10*x^4
    poly3 = RatPoly.valueOf("9*x^3+10*x^4");

    // neg_poly3 = -9*x^3 + -10*x^4
    neg_poly3 = poly3.negate();
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Constructor
  ///////////////////////////////////////////////////////////////////////////////////////   

  @Test
  public void testNoArgCtor() {
    eq(new RatPoly(), "0");
  }

  @Test
  public void testTwoArgCtorWithZeroExp() {
    eq(poly(0, 0), "0");
    eq(poly(0, 1), "0");
    eq(poly(1, 0), "1");
    eq(poly(-1, 0), "-1");
  }

  @Test
  public void testTwoArgCtorWithOneExp() {
    eq(poly(1, 1), "x");
    eq(poly(-1, 1), "-x");
  }

  @Test
  public void testTwoArgCtorWithLargeExp() {
    eq(poly(1, 2), "x^2");
    eq(poly(2, 2), "2*x^2");
    eq(poly(2, 3), "2*x^3");
    eq(poly(-2, 3), "-2*x^3"); 
    eq(poly(-1, 3), "-x^3");
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  isNaN Test
  ///////////////////////////////////////////////////////////////////////////////////////     

  @Test
  public void testIsNaN() {
    assertTrue(RatPoly.valueOf("NaN").isNaN());   
  }

  @Test
  public void testIsNotNaN() {
    assertFalse(RatPoly.valueOf("1").isNaN());
    assertFalse(RatPoly.valueOf("1/2").isNaN());
    assertFalse(RatPoly.valueOf("x+1").isNaN());
    assertFalse(RatPoly.valueOf("x^2+x+1").isNaN());
  }

  @Test
  public void testIsNaNEmptyPolynomial() {
    RatPoly empty = new RatPoly();
    assertTrue(empty.div(empty).isNaN());
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Value Of Test
  ///////////////////////////////////////////////////////////////////////////////////////     

  @Test
  public void testValueOfSimple() {
    eqP("0", 0, new int[] { 0 });
    eqP("x", 1, new int[] { 1, 0 });
    eqP("x^2", 2, new int[] { 1, 0, 0 });
  }

  @Test
  public void testValueOfMultTerms() {
    eqP("x^3+x^2", 3, new int[] { 1, 1, 0, 0 });
    eqP("x^3-x^2", 3, new int[] { 1, -1, 0, 0 });
    eqP("x^10+x^2", 10, new int[] { 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0 });
  }

  @Test
  public void testValueOfLeadingNeg() {
    eqP("-x^2", 2, new int[] { -1, 0, 0 });
    eqP("-x^2+1", 2, new int[] { -1, 0, 1 });
    eqP("-x^2+x", 2, new int[] { -1, 1, 0 });
  }

  @Test
  public void testValueOfLeadingConstants() {
    eqP("10*x", 1, new int[] { 10, 0 });
    eqP("10*x^4+x^2", 4, new int[] { 10, 0, 1, 0, 0 });
    eqP("10*x^4+100*x^2", 4, new int[] { 10, 0, 100, 0, 0 });
    eqP("-10*x^4+100*x^2", 4, new int[] { -10, 0, 100, 0, 0 });
  }

  @Test
  public void testValueOfRationalsSingleTerms() {
    eqP("1/2", 0, new RatNum[] { num(1).div(num(2)) });
    eqP("1/2*x", 1, new RatNum[] { num(1).div(num(2)), num(0) });
    eqP("1/1000", 0, new RatNum[] { num(1).div(num(1000)) });
    eqP("1/1000*x", 1, new RatNum[] { num(1).div(num(1000)), num(0) });
  }

  @Test
  public void testValueOfRationalsMultipleTerms() {
    eqP("x+1/3", 1, new RatNum[] { num(1), num(1).div(num(3)) });
    eqP("1/2*x+1/3", 1, new RatNum[] { num(1).div(num(2)),
        num(1).div(num(3)) });
    eqP("1/2*x+3/2", 1, new RatNum[] { num(1).div(num(2)),
        num(3).div(num(2)) });
    eqP("1/2*x^3+3/2", 3, new RatNum[] { num(1).div(num(2)), num(0),
        num(0), num(3).div(num(2)) });
    eqP("1/2*x^3+3/2*x^2+1", 3, new RatNum[] { num(1).div(num(2)),
        num(3).div(num(2)), num(0), num(1) });
  }

  @Test
  public void testValueOfNaN() {
    assertTrue(valueOf("NaN").isNaN());
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  To String Test
  ///////////////////////////////////////////////////////////////////////////////////////

  @Test
  public void testToStringSimple() {
    assertToStringWorks("0");
    assertToStringWorks("x");
    assertToStringWorks("x^2");
  }

  @Test
  public void testToStringMultTerms() {
    assertToStringWorks("x^3+x^2");
    assertToStringWorks("x^3-x^2");
    assertToStringWorks("x^100+x^2");
  }

  @Test
  public void testToStringLeadingNeg() {
    assertToStringWorks("-x^2");
    assertToStringWorks("-x^2+1");
    assertToStringWorks("-x^2+x");
  }

  @Test
  public void testToStringLeadingConstants() {
    assertToStringWorks("10*x");
    assertToStringWorks("10*x^100+x^2");
    assertToStringWorks("10*x^100+100*x^2");
    assertToStringWorks("-10*x^100+100*x^2");
  }

  @Test
  public void testToStringRationalsSingleElems() {
    assertToStringWorks("1/2");
    assertToStringWorks("1/2*x");
  }

  @Test
  public void testToStringRationalsMultiplElems() {
    assertToStringWorks("x+1/3");
    assertToStringWorks("1/2*x+1/3");
    assertToStringWorks("1/2*x+3/2");
    assertToStringWorks("1/2*x^10+3/2");
    assertToStringWorks("1/2*x^10+3/2*x^2+1");
  }

  @Test
  public void testToStringNaN() {
    assertToStringWorks("NaN");
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Degree Test
  ///////////////////////////////////////////////////////////////////////////////////////    

  @Test // test degree is zero when it should be
  public void testDegreeZero() {
    assertEquals("x^0 degree 0", 0, poly(1, 0).degree());
    assertEquals("0*x^100 degree 0", 0, poly(0, 100).degree());
    assertEquals("0*x^0 degree 0", 0, poly(0, 0).degree());
  }

  @Test
  public void testDegreeNonZero() {
    assertEquals("x^1 degree 1", 1, poly(1, 1).degree());
    assertEquals("x^100 degree 100", 100, poly(1, 100).degree());
  }

  @Test // test degree for multi termed polynomial
  public void testDegreeNonZeroMultiTerm() {
    assertEquals(poly1.toString() + " has Correct Degree", 5, poly1.degree());
    assertEquals(poly2.toString() + " has Correct Degree", 4, poly2.degree());
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Negate Tests
  /////////////////////////////////////////////////////////////////////////////////////// 

  @Test // test degree is zero when it should be
  public void testNegateZero() {
    assertEquals(RatPoly.ZERO, RatPoly.ZERO.negate());
  }
  
  @Test // test degree is zero when it should be
  public void testNegateNaN() {
    assertEquals(RatPoly.NaN, RatPoly.NaN.negate());
  }
  
  @Test // test degree is zero when it should be
  public void testNegatePosToNeg() {
    assertEquals(RatPoly.valueOf("-x-2*x^2-3*x^3-4*x^4-5*x^5"), poly1.negate());
    assertEquals(RatPoly.valueOf("-6*x^2-7*x^3-8*x^4"), poly2.negate());
    assertEquals(RatPoly.valueOf("-9*x^3-10*x^4"), poly3.negate());
  }
  
  @Test // test degree is zero when it should be
  public void testNegatNegToPos() {
    assertEquals(poly1, RatPoly.valueOf("-x-2*x^2-3*x^3-4*x^4-5*x^5").negate());
    assertEquals(poly2, RatPoly.valueOf("-6*x^2-7*x^3-8*x^4").negate());
    assertEquals(poly3, RatPoly.valueOf("-9*x^3-10*x^4").negate());
  }
  
  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Addition Test
  ///////////////////////////////////////////////////////////////////////////////////////     
  
  @Test
  public void testAddSingleTerm() {
    eq(poly(1, 0).add(poly(1, 0)), "2");
    eq(poly(1, 0).add(poly(5, 0)), "6");
    eq(poly(1, 0).add(poly(-1, 0)), "0");
    eq(poly(1, 1).add(poly(1, 1)), "2*x");
    eq(poly(1, 2).add(poly(1, 2)), "2*x^2"); 
  }

  @Test
  public void testAddMultipleTerm() {
    RatPoly _XSqPlus2X = poly(1, 2).add(poly(1, 1)).add(poly(1, 1));
    RatPoly _2XSqPlusX = poly(1, 2).add(poly(1, 2)).add(poly(1, 1));

    eq(_XSqPlus2X, "x^2+2*x");
    eq(_2XSqPlusX, "2*x^2+x");

    eq(poly(1, 2).add(poly(1, 1)), "x^2+x");
    eq(poly(1, 3).add(poly(1, 1)), "x^3+x");
  }

  // Note Polynomial is annotated as p
  @Test // p1 + p2 = p3 , p1 != 0 && p2 != 0, p1.degree == p2.degree
  public void testAddSameDegree(){
    RatPoly temp = RatPoly.valueOf("3*x^1+4*x^2+5*x^3+6*x^4+7*x^5");
    assertEquals(RatPoly.valueOf("4*x^1+6*x^2+8*x^3+10*x^4+12*x^5"), poly1.add(temp));

    RatPoly temp2 = RatPoly.valueOf("-1*x^2-2*x^3-3*x^4");
    assertEquals(RatPoly.valueOf("-7*x^2-9*x^3-11*x^4"), neg_poly2.add(temp2));
  }

  @Test // p1 + p2 = p3 , p1 != 0 && p2 != 0, p1.degree != p2.degree
  public void testAddDifferentDegree(){
    assertEquals(RatPoly.valueOf("1*x^1+8*x^2+10*x^3+12*x^4+5*x^5"), poly1.add(poly2));      
    assertEquals(RatPoly.valueOf("-6*x^2-16*x^3-18*x^4"), neg_poly2.add(neg_poly3));
  }

  @Test // p + p = 2p
  public void testAddWithItSelf() {
    assertEquals(RatPoly.valueOf("2*x^1+4*x^2+6*x^3+8*x^4+10*x^5"), poly1.add(poly1));  
    assertEquals(RatPoly.valueOf("-12*x^2-14*x^3-16*x^4"), neg_poly2.add(neg_poly2));
  }

  @Test // Addition Associativity (p1 + p2) + p3 = p1 + (p2 + p3)
  public void testAddAssociativity() {
    RatPoly operation1 = (poly1.add(poly2)).add(poly3);
    RatPoly operation2 = (poly3.add(poly2)).add(poly1);
    assertEquals(operation1, operation2);

    operation1 = (poly1.add(neg_poly2)).add(neg_poly3);
    operation2 = (neg_poly3.add(neg_poly2)).add(poly1);
    assertEquals(operation1, operation2);
  }

  @Test // Addition Commutative Rule p1 + p2 = p2 + p1
  public void testAddCommutativity() {
    assertEquals(poly1.add(neg_poly2), neg_poly2.add(poly1));
    assertEquals(neg_poly3.add(poly2), poly2.add(neg_poly3));
  }

  @Test // Zero Polynomial + Zero Polynomial == Zero Polynomial
  public void testAddZeroToZero() {
    assertEquals(RatPoly.ZERO, RatPoly.ZERO.add(RatPoly.ZERO));
  }

  @Test // Additive Identity p + Zero Polynomial == p && Zero Polynomial + p == p
  public void testAddZeroToNonZero() {
    assertEquals(poly1, RatPoly.ZERO.add(poly1));
    assertEquals(poly1, poly1.add(RatPoly.ZERO));
  }

  @Test // Additive Inverse p + (-p) = 0
  public void testAddInverse() {
    assertEquals(RatPoly.ZERO, poly1.add(neg_poly1));
    assertEquals(RatPoly.ZERO, poly2.add(neg_poly2));
    assertEquals(RatPoly.ZERO, poly3.add(neg_poly3));
  }

  @Test // NaN + NaN == NaN
  public void testAddNaNtoNaN() {
    assertEquals(RatPoly.NaN, RatPoly.NaN.add(RatPoly.NaN));
  }

  @Test // t + NaN == NaN
  public void testAddNaNtoNonNaN() {
    assertEquals(RatPoly.NaN, RatPoly.NaN.add(poly1));
    assertEquals(RatPoly.NaN, poly1.add(RatPoly.NaN));
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Subtraction Test
  /////////////////////////////////////////////////////////////////////////////////////// 

  //Also Tests Addition inverse property

  @Test // p1 - p2 = p3 , p1 != 0 && p2 != 0, p1.degree == p2.degree
  public void testSubtractSameDegree() {
    RatPoly temp = RatPoly.valueOf("3*x^1+4*x^2+5*x^3+6*x^4+7*x^5");
    assertEquals(RatPoly.valueOf("2*x^1+2*x^2+2*x^3+2*x^4+2*x^5"), temp.sub(poly1));

    RatPoly temp2 = RatPoly.valueOf("-1*x^2-2*x^3-3*x^4");
    assertEquals(RatPoly.valueOf("7*x^2+9*x^3+11*x^4"), poly2.sub(temp2));
  }

  @Test // p1 - p2 = p3 , p1 != 0 && p2 != 0, p1.degree != p2.degree
  public void testSubtractDiffDegree() {
    assertEquals(RatPoly.valueOf("1*x^1-4*x^2-4*x^3-4*x^4+5*x^5"), poly1.sub(poly2));
    assertEquals(RatPoly.valueOf("-6*x^2-16*x^3-18*x^4"), neg_poly2.sub(poly3));
  }

  @Test // Zero Polynomial - Zero Polynomial == Zero Polynomial
  public void testSubtractZeroFromZero() {
    assertEquals(RatPoly.ZERO, RatPoly.ZERO.sub(RatPoly.ZERO));
  }

  //Following test method depends on correctness of negate
  @Test // p - ZeroPolynomial == t && ZeroPolynomial - p == -p
  public void testSubtractZeroAndNonZero() {
    assertEquals(neg_poly1, RatPoly.ZERO.sub(poly1));
    assertEquals(poly1, poly1.sub(RatPoly.ZERO));
  }

  @Test // NaN - NaN == NaN
  public void testSubtractNaNtoNaN() {
    assertEquals(RatPoly.NaN, RatPoly.NaN.sub(RatPoly.NaN));
  }

  @Test // p - NaN == NaN && NaN - p == NaN
  public void testSubtractNaNtoNonNaN() {
    assertEquals(RatPoly.NaN, RatPoly.NaN.sub(poly1));
    assertEquals(RatPoly.NaN, poly1.sub(RatPoly.NaN));
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Remove zero when appropriate test
  /////////////////////////////////////////////////////////////////////////////////////// 

  @Test 
  public void testZeroElim() {
    // make sure zeros are removed from poly
    eqP("1+0", 0, new int[] { 1 });
    // test zero-elimination from intermediate result of sub
    eq(quadPoly(1, 1, 1).sub(poly(1, 1)), "x^2+1");
    // test internal cancellation of terms in mul.  (x+1)*(x-1)=x^2-1
    eq(poly(1, 1).add(poly(1, 0)).mul(poly(1, 1).sub(poly(1, 0))), "x^2-1");
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Small Value Test
  /////////////////////////////////////////////////////////////////////////////////////// 

  @Test
  public void testSmallCoeff() {
    // try to flush out errors when small coefficients are in use.
    eq(quadPoly(1, 1, 1).sub(poly(999, 1).div(poly(1000, 0))),
        "x^2+1/1000*x+1");
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Multiplication Test
  /////////////////////////////////////////////////////////////////////////////////////// 

  @Test // p1 + p2 = p3 , p1 != 0 && p2 != 0, p1.degree == p2.degree
  public void testMultiplicationSameDegree() {
    eq(poly(0, 0).mul(poly(0, 0)), "0");
    eq(poly(1, 0).mul(poly(1, 0)), "1");
    eq(poly(1, 0).mul(poly(2, 0)), "2");
    eq(poly(2, 0).mul(poly(2, 0)), "4");
    RatPoly temp = RatPoly.valueOf("3*x^4+2");
    assertEquals(RatPoly.valueOf("30*x^8+27*x^7+20*x^4+18*x^3"), temp.mul(poly3));
  }

  @Test // p1 + p2 = p3 , p1 != 0 && p2 != 0, p1.degree != p2.degree
  public void testMultiplicationDiffDegree() {
    RatPoly temp = RatPoly.valueOf("3*x^2");
    assertEquals(RatPoly.valueOf("18*x^4+21*x^5+24*x^6"), temp.mul(poly2));
    assertEquals(RatPoly.valueOf("27*x^5+30*x^6"), temp.mul(poly3));
  }

  @Test // Multiplication Associativity
  public void testMultiplicationAssociativity() {
    assertEquals(poly1.mul(poly2).mul(poly3), 
        poly3.mul(poly2).mul(poly1));
    assertEquals(poly1.mul(neg_poly2).mul(neg_poly3), 
        neg_poly3.mul(neg_poly2).mul(poly1));
  }

  @Test // Multiplication Commutative
  public void testMultiplicationCommutativity() {
    assertEquals(poly1.mul(poly2), poly2.mul(poly1));
    assertEquals(neg_poly3.mul(poly2), poly2.mul(neg_poly3));    
  }

  @Test // ZeroPolynomial * ZeroPolynomial == ZeroPolynomial
  public void testMultiplicationZeroToZero() {
    assertEquals(RatPoly.ZERO, RatPoly.ZERO.mul(RatPoly.ZERO));
  }

  @Test // p * ZeroPolynomial == ZeroPolynomial && ZeroPolynomial * p == ZeroPolynomial
  public void testMultiplicationZeroToNonZero() {
    assertEquals(RatPoly.ZERO, RatPoly.ZERO.mul(poly2));
    assertEquals(RatPoly.ZERO, poly2.mul(RatPoly.ZERO));
  }

  @Test // NaN * NaN == NaN
  public void testMultiplicationNaNtoNaN() {
    assertEquals(RatPoly.NaN, RatPoly.NaN.mul(RatPoly.NaN));
  }

  @Test // p * NaN == NaN
  public void testMultiplicationNaNtoNonNaN() {
    assertEquals(RatPoly.NaN, RatPoly.NaN.mul(poly1));
    assertEquals(RatPoly.NaN, poly1.mul(RatPoly.NaN));
  }

  @Test // p * 1 == p
  public void testMultiplicationIdentity() {
    assertEquals(poly2, poly2.mul(RatPoly.valueOf("1")));
    assertEquals(neg_poly3, neg_poly3.mul(RatPoly.valueOf("1")));
  }

  @Test
  public void testMulMultiplElem() {
    eq(poly(1, 1).sub(poly(1, 0)).mul(poly(1, 1).add(poly(1, 0))), "x^2-1");
  }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Division Test
    ///////////////////////////////////////////////////////////////////////////////////////     
    
  @Test
  public void testDivEvaltoSingleCoeff() {
    // 0/x = 0
    eq(poly(0, 1).div(poly(1, 1)), "0");

    // 2/1 = 2
    eq(poly(2, 0).div(poly(1, 0)), "2");

    // x/x = 1
    eq(poly(1, 1).div(poly(1, 1)), "1");

    // -x/x = -1
    eq(poly(-1, 1).div(poly(1, 1)), "-1");

    // x/-x = -1
    eq(poly(1, 1).div(poly(-1, 1)), "-1");

    // -x/-x = 1
    eq(poly(-1, 1).div(poly(-1, 1)), "1");

    // x^100/x^1000 = 0
    eq(poly(1, 100).div(poly(1, 1000)), "0");
  }

  @Test
  public void testDivtoSingleTerm() {

    // 5x/5 = x
    eq(poly(5, 1).div(poly(5, 0)), "x");

    // -x^2/x = -x
    eq(poly(-1, 2).div(poly(1, 1)), "-x");

    // x^100/x = x^99
    eq(poly(1, 100).div(poly(1, 1)), "x^99");

    // x^99/x^98 = x
    eq(poly(1, 99).div(poly(1, 98)), "x");

    // x^10 / x = x^9 (r: 0)
    eq(poly(1, 10).div(poly(1, 1)), "x^9");
  }

  @Test
  public void testDivtoMultipleTerms() {
    // x^10 / x^3+x^2 = x^7-x^6+x^5-x^4+x^3-x^2+x-1 (r: -x^2)
    eq(poly(1, 10).div(poly(1, 3).add(poly(1, 2))),
        "x^7-x^6+x^5-x^4+x^3-x^2+x-1");

    // x^10 / x^3+x^2+x = x^7-x^6+x^4-x^3+x-1 (r: -x)
    eq(poly(1, 10).div(poly(1, 3).add(poly(1, 2).add(poly(1, 1)))),
        "x^7-x^6+x^4-x^3+x-1");

    // 5x^2+5x/5 = x^2+x
    eq(poly(5, 2).add(poly(5, 1)).div(poly(5, 0)), "x^2+x");

    // x^10+x^5 / x = x^9+x^4 (r: 0)
    eq(poly(1, 10).add(poly(1, 5)).div(poly(1, 1)), "x^9+x^4");

    // x^10+x^5 / x^3 = x^7+x^2 (r: 0)
    eq(poly(1, 10).add(poly(1, 5)).div(poly(1, 3)), "x^7+x^2");

    // x^10+x^5 / x^3+x+3 = x^7-x^5-3*x^4+x^3+7*x^2+8*x-10
    // (with remainder: 29*x^2+14*x-30)
    eq(poly(1, 10).add(poly(1, 5)).div(
        poly(1, 3).add(poly(1, 1)).add(poly(3, 0))),
        "x^7-x^5-3*x^4+x^3+7*x^2+8*x-10");
  }

  @Test
  public void testDivComplexI() {
    // (x+1)*(x+1) = x^2+2*x+1
    eq(poly(1, 2).add(poly(2, 1)).add(poly(1, 0)).div(
        poly(1, 1).add(poly(1, 0))), "x+1");

    // (x-1)*(x+1) = x^2-1
    eq(poly(1, 2).add(poly(-1, 0)).div(poly(1, 1).add(poly(1, 0))), "x-1");
  }

  @Test
  public void testDivComplexII() {
    // x^8+2*x^6+8*x^5+2*x^4+17*x^3+11*x^2+8*x+3 =
    // (x^3+2*x+1) * (x^5+7*x^2+2*x+3)
    RatPoly large = poly(1, 8).add(poly(2, 6)).add(poly(8, 5)).add(
        poly(2, 4)).add(poly(17, 3)).add(poly(11, 2)).add(poly(8, 1))
        .add(poly(3, 0));

    // x^3+2*x+1
    RatPoly sub1 = poly(1, 3).add(poly(2, 1)).add(poly(1, 0));
    // x^5+7*x^2+2*x+3
    RatPoly sub2 = poly(1, 5).add(poly(7, 2)).add(poly(2, 1)).add(
        poly(3, 0));

    // just a last minute typo check...
    eq(sub1.mul(sub2), large.toString());
    eq(sub2.mul(sub1), large.toString());

    eq(large.div(sub2), "x^3+2*x+1");
    eq(large.div(sub1), "x^5+7*x^2+2*x+3");
  }

  @Test
  public void testDivExamplesFromSpec() {
    // seperated this test case out because it has a dependency on
    // both "valueOf" and "div" functioning properly

    // example 1 from spec
    eq(valueOf("x^3-2*x+3").div(valueOf("3*x^2")), "1/3*x");
    // example 2 from spec
    eq(valueOf("x^2+2*x+15").div(valueOf("2*x^3")), "0");
  }

  @Test
  public void testDivExampleFromPset() {
    eq(valueOf("x^8+x^6+10*x^4+10*x^3+8*x^2+2*x+8").div(
        valueOf("3*x^6+5*x^4+9*x^2+4*x+8")), "1/3*x^2-2/9");
  }

  @Test
  public void testBigDiv() {
    // don't "fix" the "infinite loop" in div by simply stopping after
    // 50 terms!
    eq(
        valueOf("x^102").div(valueOf("x+1")),
        "x^101-x^100+x^99-x^98+x^97-x^96+x^95-x^94+x^93-x^92+x^91-x^90+"
            + "x^89-x^88+x^87-x^86+x^85-x^84+x^83-x^82+x^81-x^80+x^79-x^78+"
            + "x^77-x^76+x^75-x^74+x^73-x^72+x^71-x^70+x^69-x^68+x^67-x^66+"
            + "x^65-x^64+x^63-x^62+x^61-x^60+x^59-x^58+x^57-x^56+x^55-x^54+"
            + "x^53-x^52+x^51-x^50+x^49-x^48+x^47-x^46+x^45-x^44+x^43-x^42+"
            + "x^41-x^40+x^39-x^38+x^37-x^36+x^35-x^34+x^33-x^32+x^31-x^30+"
            + "x^29-x^28+x^27-x^26+x^25-x^24+x^23-x^22+x^21-x^20+x^19-x^18+"
            + "x^17-x^16+x^15-x^14+x^13-x^12+x^11-x^10+x^9-x^8+x^7-x^6+x^5-"
            + "x^4+x^3-x^2+x-1");
  }
  
  @Test // p / 0 = NaN
  public void testDivByZero() {
     assertEquals(RatPoly.NaN, poly2.div(RatPoly.ZERO));
     assertEquals(RatPoly.NaN, neg_poly1.div(RatPoly.ZERO));
     assertEquals(RatPoly.NaN, poly1.div(RatPoly.ZERO));
  }
    
    @Test // Zero Polynomial / Zero Polynomial == NaN
    public void testDivisionZeroFromZero() {
      assertEquals(RatPoly.NaN, RatPoly.ZERO.div(RatPoly.ZERO));
    }
    
    //Following test method depends on correctness of negate
    @Test // p / Zero Polynomial == NaN && Zero Polynomial / p == 0
    public void testDivisionZeroAndNonZero() {
      assertEquals(RatPoly.ZERO, RatPoly.ZERO.div(poly1));
    }
    
    @Test // NaN / NaN == NaN
    public void testDivisionNaNtoNaN() {
      assertEquals(RatPoly.NaN, RatPoly.NaN.div(RatPoly.NaN));
    }
    
    @Test // p / NaN == NaN && NaN / p == NaN
    public void testDivisionNaNtoNonNaN() {
      assertEquals(RatPoly.NaN, RatPoly.NaN.div(poly1));
        assertEquals(RatPoly.NaN, poly1.div(RatPoly.NaN));
    }
    
    @Test // p / 1 == p
    public void testDivisionByOne() {
      assertEquals(poly2, poly2.div(RatPoly.valueOf("1")));
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Immutable Test
    ///////////////////////////////////////////////////////////////////////////////////////    
    
  @Test
  public void testImmutabilityOfOperations() {
    // not the most thorough test possible, but hopefully will
    // catch the easy cases early on...
    RatPoly one = poly(1, 0);
    RatPoly two = poly(2, 0);
    RatPoly empty = new RatPoly();

    one.degree();
    two.degree();
    eq(one, "1", "Degree mutates receiver!");
    eq(two, "2", "Degree mutates receiver!");

    one.getTerm(0);
    two.getTerm(0);
    eq(one, "1", "Coeff mutates receiver!");
    eq(two, "2", "Coeff mutates receiver!");

    one.isNaN();
    two.isNaN();
    eq(one, "1", "isNaN mutates receiver!");
    eq(two, "2", "isNaN mutates receiver!");

    one.eval(0.0);
    two.eval(0.0);
    eq(one, "1", "eval mutates receiver!");
    eq(two, "2", "eval mutates receiver!");

    one.negate();
    two.negate();
    eq(one, "1", "Negate mutates receiver!");
    eq(two, "2", "Negate mutates receiver!");

    one.add(two);
    eq(one, "1", "Add mutates receiver!");
    eq(two, "2", "Add mutates argument!");

    one.sub(two);
    eq(one, "1", "Sub mutates receiver!");
    eq(two, "2", "Sub mutates argument!");

    one.mul(two);
    eq(one, "1", "Mul mutates receiver!");
    eq(two, "2", "Mul mutates argument!");

    one.div(two);
    eq(one, "1", "Div mutates receiver!");
    eq(two, "2", "Div mutates argument!");

    empty.div(new RatPoly());
    assertFalse("Div Mutates reciever", empty.isNaN());
  }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Eval Test
    /////////////////////////////////////////////////////////////////////////////////////// 
  
  @Test
  public void testEvalZero() {
    RatPoly zero = new RatPoly();
    assertEquals(" 0 at 0 ", 0.0, zero.eval(0.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" 0 at 1 ", 0.0, zero.eval(1.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" 0 at 2 ", 0.0, zero.eval(2.0), JUNIT_DOUBLE_DELTA);
  }

  @Test
  public void testEvalOne() {
    RatPoly one = new RatPoly(1, 0);

    assertEquals(" 1 at 0 ", 1.0, one.eval(0.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" 1 at 1 ", 1.0, one.eval(1.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" 1 at 1 ", 1.0, one.eval(2.0), JUNIT_DOUBLE_DELTA);
  }

  @Test
  public void testEvalX() {
    RatPoly _X = new RatPoly(1, 1);

    assertEquals(" x at 0 ", 0.0, _X.eval(0.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" x at 1 ", 1.0, _X.eval(1.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" x at 2 ", 2.0, _X.eval(2.0), JUNIT_DOUBLE_DELTA);
  }

  @Test
  public void testEval2X() {
    RatPoly _2X = new RatPoly(2, 1);

    assertEquals(" 2*x at 0 ", 0.0, _2X.eval(0.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" 2*x at 1 ", 2.0, _2X.eval(1.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" 2*x at 2 ", 4.0, _2X.eval(2.0), JUNIT_DOUBLE_DELTA);
  }

  @Test
  public void testEvalXsq() {
    RatPoly _XSq = new RatPoly(1, 2);
    assertEquals(" x^2 at 0 ", 0.0, _XSq.eval(0.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" x^2 at 1 ", 1.0, _XSq.eval(1.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" x^2 at 2 ", 4.0, _XSq.eval(2.0), JUNIT_DOUBLE_DELTA);
  }

  @Test
  public void testEvalXSq_minus_2X() {
    RatPoly _2X = new RatPoly(2, 1);
    RatPoly _XSq = new RatPoly(1, 2);
    RatPoly _XSq_minus_2X = _XSq.sub(_2X);

    assertEquals(" x^2-2*x at 0 ", 0.0, _XSq_minus_2X.eval(0.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" x^2-2*x at 1 ", -1.0, _XSq_minus_2X.eval(1.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" x^2-2*x at 2 ", 0.0, _XSq_minus_2X.eval(2.0), JUNIT_DOUBLE_DELTA);
    assertEquals(" x^2-2*x at 3 ", 3.0, _XSq_minus_2X.eval(3.0), JUNIT_DOUBLE_DELTA);
  }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Get Term Test
    ///////////////////////////////////////////////////////////////////////////////////////   
  
  @Test
  public void testGetTerm() {
    // getTerm already gets some grunt testing in eqP; checking an
    // interesting
    // input here...
    RatPoly _XSqPlus2X = poly(1, 2).add(poly(1, 1)).add(poly(1, 1));
    RatPoly _2XSqPlusX = poly(1, 2).add(poly(1, 2)).add(poly(1, 1));

    assertEquals(RatTerm.ZERO, _XSqPlus2X.getTerm(-1));
    assertEquals(RatTerm.ZERO, _XSqPlus2X.getTerm(-10));
    assertEquals(RatTerm.ZERO, _2XSqPlusX.getTerm(-1));
    assertEquals(RatTerm.ZERO, _2XSqPlusX.getTerm(-10));
    assertEquals(RatTerm.ZERO, zero().getTerm(-10));
    assertEquals(RatTerm.ZERO, zero().getTerm(-1));
  }


  private void assertIsNaNanswer(RatPoly nanAnswer) {
    eq(nanAnswer, "NaN");
  }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Differentiate Test
    /////////////////////////////////////////////////////////////////////////////////////// 
  
  // (NaN)' = NaN
    @Test
    public void testDifferentiateNaN(){
      assertEquals(RatPoly.NaN, RatPoly.NaN.differentiate());
    }
    
    // (RatPoly.ZERO)' = RatPoly.ZERO
    @Test
    public void testDifferentiateZero(){
      assertEquals(RatPoly.ZERO, RatPoly.ZERO.differentiate());
    }
    
    // constant a => (a)' = 0
    @Test
    public void testDifferentiateConstantNonZero(){
      assertEquals(RatPoly.ZERO, RatPoly.valueOf("1").differentiate());
      assertEquals(RatPoly.ZERO, RatPoly.valueOf("999").differentiate());
    }
    
  @Test  //f(x) = x => f' = 1 
  public void testDifferentiatetoOne() {
    eq(RatPoly.valueOf("x").differentiate(), "1");
  }
    
    // Constant Multiple Rule (af)' = af'
    @Test
    public void testDifferentiateMultiplicationRule(){
      RatPoly a_constant = RatPoly.valueOf("2");
      assertEquals(a_constant.mul(poly1.differentiate()), 
          (a_constant.mul(poly1)).differentiate());
      assertEquals(a_constant.mul(neg_poly2.differentiate()), 
          (a_constant.mul(neg_poly2)).differentiate());
    }
    
    // Polynomial Power Rule (ax^b) = (a*b)*x^(b-1) 
    @Test
    public void testDifferentiatePowerRule(){
        assertEquals(RatPoly.valueOf("1+4*x+9*x^2+16*x^3+25*x^4"), poly1.differentiate());
        assertEquals(RatPoly.valueOf("12*x+21*x^2+32*x^3"), poly2.differentiate());
    }
    
    // Sum rule (f + g)' = f' + g'
    @Test
    public void testDifferentiateSumRule(){
      assertEquals(((poly2).add(neg_poly3)).differentiate(), 
          (poly2.differentiate()).add(neg_poly3.differentiate()));
      assertEquals(((poly1).add(poly3)).differentiate(), 
          (poly1.differentiate()).add(poly3.differentiate()));
    }
    
    // Subtraction rule (f - g)' = f' - g'
    @Test
    public void testDifferentiateSubtractionRule(){
      assertEquals(((poly2).sub(neg_poly3)).differentiate(), 
          (poly2.differentiate()).sub(neg_poly3.differentiate()));
      assertEquals(((poly1).sub(poly3)).differentiate(), 
          (poly1.differentiate()).sub(poly3.differentiate()));
    }
    
    // Product Rule h(x) = f(x)*g(x) => h'(x) = f'(x)g(x) + f(x)g'(x)
    @Test
    public void testDifferentiateProductRule(){
      // Whole Number Coefficient
      RatPoly init_product = poly1.mul(poly2);
      RatPoly deriv_pt1 = (poly1.differentiate()).mul(poly2);
      RatPoly deriv_pt2 = poly1.mul(poly2.differentiate());
      
      assertEquals(init_product.differentiate() , deriv_pt1.add(deriv_pt2));
      
      // Fractional Number Coefficient
      init_product = neg_poly2.mul(poly3);
      deriv_pt1 = (neg_poly2.differentiate()).mul(poly3);
      deriv_pt2 = neg_poly2.mul(poly3.differentiate());
      
      assertEquals(init_product.differentiate() , deriv_pt1.add(deriv_pt2));
    }

  @Test
  public void testDifferentiatetoMultipleTerms() {
    eq(quadPoly(7, 5, 99).differentiate(), "14*x+5");
    eq(quadPoly(3, 2, 1).differentiate(), "6*x+2");
    eq(quadPoly(1, 0, 1).differentiate(), "2*x");
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Anti Differentiate Test
  /////////////////////////////////////////////////////////////////////////////////////// 
  //As stated in specification for any term b is assumed >= 0 and Integration Constant is Zero
    //Note : AntiDerivative of f(x) = F(x) + c , f = F
    //Note : c = Integration Constant
    
  @Test //AntiDifferentiate Basic functionality
  public void testAntiDifferentiate() {
    eq(poly(1, 0).antiDifferentiate(new RatNum(1)), "x+1");
    eq(poly(2, 1).antiDifferentiate(new RatNum(1)), "x^2+1");      
  }

  @Test
  public void testAntiDifferentiateWithQuadPoly() {
    eq(quadPoly(0, 6, 2).antiDifferentiate(new RatNum(1)), "3*x^2+2*x+1");
    eq(quadPoly(4, 6, 2).antiDifferentiate(new RatNum(0)),
        "4/3*x^3+3*x^2+2*x");
  }
  
  @Test // Constant Rule with zero f(x) = 0 => F = c
    public void testAntiDifferentiateFromZero() {
    // Zero
      assertEquals(RatPoly.ZERO, RatPoly.ZERO.antiDifferentiate(RatNum.ZERO));
      // Zero with integration constant 5
      assertEquals(RatPoly.valueOf("5"), RatPoly.ZERO.antiDifferentiate(RatNum.valueOf("5")));
  }
  
    // Constant Rule f(x) = c => F = c*x
    @Test
    public void testAntiDifferentiateConstantRule() {
      // Zero Integration Constant
      assertEquals(RatPoly.valueOf("5*x"), RatPoly.valueOf("5").antiDifferentiate(RatNum.ZERO));
      
      // Non Zero Integration Constant
      assertEquals(RatPoly.valueOf("5*x+10"), RatPoly.valueOf("5").antiDifferentiate(RatNum.valueOf("10")));
    }
    
    // Constant Multiple Rule f(x) = c*g(x) => F = c*G(x)
    @Test
    public void testAntiDifferentiateConstantMultipleRule() {
      RatPoly a_constant = RatPoly.valueOf("7");
      RatPoly b_constant = RatPoly.valueOf("13");
      RatNum i_constant = RatNum.valueOf("11");
  
      assertEquals(((a_constant).mul(poly1)).antiDifferentiate(i_constant), 
           a_constant.mul(poly1.antiDifferentiate(RatNum.ZERO)).add(new RatPoly(new RatTerm(i_constant , 0))));
      
      assertEquals(((b_constant).mul(poly3)).antiDifferentiate(RatNum.ZERO), 
          b_constant.mul(poly3.antiDifferentiate(RatNum.ZERO)));
    }
    
    // Power Rule f(x) = x^a => F = (x^(a+1))/(a+1)
    @Test
    public void testAntiDifferentiatePowerRule() {
        assertEquals(RatPoly.valueOf("9/4*x^4+2*x^5"), poly3.antiDifferentiate(RatNum.ZERO));
        assertEquals(RatPoly.valueOf("2*x^3+7/4*x^4+8/5*x^5+1"), poly2.antiDifferentiate(RatNum.valueOf("1")));
    }
    
    // Sum Rule if h(x) = f(x) + g(x) => H(x) = F(x) + G(x)
    @Test
    public void testAntiDifferentiateSumRule() {
      assertEquals((poly1.add(poly2)).antiDifferentiate(RatNum.ZERO), 
          poly1.antiDifferentiate(RatNum.ZERO).add(poly2.antiDifferentiate(RatNum.ZERO)));

      assertEquals((neg_poly3.add(neg_poly1)).antiDifferentiate(RatNum.valueOf("3")), 
          neg_poly3.antiDifferentiate(RatNum.ZERO).add(neg_poly1.antiDifferentiate(RatNum.ZERO))
          .add(new RatPoly(new RatTerm(RatNum.valueOf("3") , 0))));
    }
    
    // Difference Rule if h(x) = f(x) - g(x) => H(x) = F(x) - G(x)
    @Test
    public void testAntiDifferentiateDifferenceRule() {
      assertEquals((poly1.sub(poly2)).antiDifferentiate(RatNum.ZERO), 
          poly1.antiDifferentiate(RatNum.ZERO).sub(poly2.antiDifferentiate(RatNum.ZERO)));

      assertEquals((neg_poly3.sub(neg_poly1)).antiDifferentiate(RatNum.valueOf("3")), 
          neg_poly3.antiDifferentiate(RatNum.ZERO).sub(neg_poly1.antiDifferentiate(RatNum.ZERO))
          .add(new RatPoly(new RatTerm(RatNum.valueOf("3") , 0))));
    }
    
    

  @Test
  public void testAntiDifferentiateWithNaN() {
    assertIsNaNanswer(RatPoly.valueOf("NaN").antiDifferentiate(
        new RatNum(1)));
    assertIsNaNanswer(poly(1, 0).antiDifferentiate(new RatNum(1, 0)));
  }

  ///////////////////////////////////////////////////////////////////////////////////////
  ////  Integrate Test
  /////////////////////////////////////////////////////////////////////////////////////// 
  
  @Test
  public void testIntegrateEqualBounds() {
    assertEquals( 0.0 , poly3.integrate(1, 1), JUNIT_DOUBLE_DELTA);
    assertEquals( 0.0 , poly1.integrate(0,0), JUNIT_DOUBLE_DELTA);
  }
  
  @Test
  public void testIntegrateBoundsDiffBy1() {
    assertEquals( 17.0 / 4.0 , poly3.integrate(0, 1), JUNIT_DOUBLE_DELTA);
    assertEquals( 71.0 / 20.0 , poly1.integrate(0,1), JUNIT_DOUBLE_DELTA);
  }
  
  @Test
  public void testIntegrateLowBoundGreaterThanHigh() {
    assertEquals( -19375.0 / 4.0 , poly3.integrate(0, -5), JUNIT_DOUBLE_DELTA);
    assertEquals( -5683.0 / 60.0 , poly1.integrate(2,1), JUNIT_DOUBLE_DELTA);
  }
  
  @Test
  public void testIntegrateLargeBoundDiff() {
    assertEquals( 20225000000.0, poly3.integrate(0, 100), JUNIT_DOUBLE_DELTA);
    assertEquals( 841409005000.0 , poly1.integrate(0,100), JUNIT_DOUBLE_DELTA);
  }
  
  @Test
  public void testIntegrateZero() {
    assertEquals("Integrate f(x) = 0 from 0 to 10", 0.0, RatPoly.ZERO.integrate(0, 10), JUNIT_DOUBLE_DELTA);
  }
  
  @Test
  public void testIntegrateOne() {
    assertEquals("Integrate f(x) = 1 from 0 to 10", 10.0, RatPoly.valueOf("1").integrate(0, 10), JUNIT_DOUBLE_DELTA);
  }
  
  @Test
  public void testIntegrateNaN() {
    assertEquals("NaN", RatPoly.valueOf("NaN").integrate(0, 1), Double.NaN, JUNIT_DOUBLE_DELTA);
  }
}
