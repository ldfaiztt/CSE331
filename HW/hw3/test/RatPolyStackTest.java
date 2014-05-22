package hw3.test;

import hw3.*;

import org.junit.Test;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the RatPolyStack class.
 * 
 * Some test methods are relatively longer in order to simulate proper stack functionality.
 */
public final class RatPolyStackTest {
    // create a new poly that is a constant (doesn't depend on x)
    private RatPoly constantPoly(int constant) {
        return new RatPoly(constant, 0);
    }

    // create a new poly that is a constant (doesn't depend on x)
    // taking a char allows us to represent stacks as strings
    private RatPoly constantPoly(char constant) {
        return constantPoly(Integer.valueOf("" + constant).intValue());
    }

    /** @return a new RatPolyStack instance */
    private RatPolyStack stack() {
        return new RatPolyStack();
    }

    // create stack of single-digit constant polys
    private RatPolyStack stack(String desc) {
        RatPolyStack s = new RatPolyStack();

        // go backwards to leave first element in desc on _top_ of stack
        for (int i = desc.length() - 1; i >= 0; i--) {
            char c = desc.charAt(i);
            s.push(constantPoly(c));
        }
        return s;
    }

    // RatPoly equality check
    // (getting around non-definition of RatPoly.equals)
    private boolean eqv(RatPoly p1, RatPoly p2) {
        return p1.toString().equals(p2.toString());
    }

    // Compares 's' to a string describing its values.
    // desc MUST be a sequence of decimal number chars.
    // Example call: assertStackIs(myStack, "123")
    //
    // NOTE: THIS CAN FAIL WITH A WORKING STACK IF RatPoly.toString IS BROKEN!
    private void assertStackIs(RatPolyStack s, String desc) {
        assertEquals(desc.length(), s.size());

        for (int i = 0; i < desc.length(); i++) {
            RatPoly p = s.getNthFromTop(i);
            char c = desc.charAt(i);
            String asstr
              = String.format("Elem(%d): %s, Expected %c, (Expected Stack:%s)",
                              i, p, c, desc);

            assertTrue(asstr, eqv(p, constantPoly(c)));
        }
    }

	///////////////////////////////////////////////////////////////////////////////////////
	////	Constructor
	///////////////////////////////////////////////////////////////////////////////////////     
    
    @Test
    public void testCtor() {
        RatPolyStack stk1 = stack();
        assertEquals(0, stk1.size());
    }

	///////////////////////////////////////////////////////////////////////////////////////
	////	Push
	/////////////////////////////////////////////////////////////////////////////////////// 
    
    @Test
    public void testPush() {
        RatPolyStack stk1 = stack();
        stk1.push(constantPoly(0));

        assertStackIs(stk1, "0");

        stk1.push(constantPoly(0));
        assertStackIs(stk1, "00");

        stk1.push(constantPoly(1));
        assertStackIs(stk1, "100");

        stk1 = stack("3");
        assertStackIs(stk1, "3");

        stk1 = stack("23");
        assertStackIs(stk1, "23");

        stk1 = stack("123");
        assertStackIs(stk1, "123");
    }

    @Test
    public void testPushCheckForSharingTwixtStacks() {
        RatPolyStack stk1 = stack();
        RatPolyStack stk2 = stack("123");
        assertStackIs(stk1, "");
        assertStackIs(stk2, "123");

        stk1.push(constantPoly(0));
        assertStackIs(stk1, "0");
        assertStackIs(stk2, "123");

        stk1.push(constantPoly(0));
        assertStackIs(stk1, "00");
        assertStackIs(stk2, "123");

        stk1.push(constantPoly(1));
        assertStackIs(stk1, "100");
        assertStackIs(stk2, "123");

        stk2.push(constantPoly(8));
        assertStackIs(stk1, "100");
        assertStackIs(stk2, "8123");
    }

	///////////////////////////////////////////////////////////////////////////////////////
	////	Pop
	///////////////////////////////////////////////////////////////////////////////////////     
    
    @Test
    public void testPop() {
        RatPolyStack stk1 = stack("123");

        RatPoly poly = stk1.pop();
        assertTrue(eqv(poly, constantPoly(1)));
        assertStackIs(stk1, "23");

        poly = stk1.pop();
        assertTrue(eqv(poly, constantPoly(2)));
        assertStackIs(stk1, "3");

        poly = stk1.pop();
        assertStackIs(stk1, "");
    }

	///////////////////////////////////////////////////////////////////////////////////////
	////	Duplicate
	///////////////////////////////////////////////////////////////////////////////////////  
    
    @Test
    public void testDupWithOneVal() {
        RatPolyStack stk1 = stack("3");
        stk1.dup();
        assertStackIs(stk1, "33");
        stk1 = stack("123");
        stk1.dup();
        assertStackIs(stk1, "1123");
    }
    
    @Test
    public void testDupWithTwoVal() {
    	RatPolyStack stk1 = stack("23");
        stk1.dup();
        assertStackIs(stk1, "223");
        assertEquals(3, stk1.size());
        assertTrue(eqv(stk1.getNthFromTop(0), constantPoly(2)));
        assertTrue(eqv(stk1.getNthFromTop(1), constantPoly(2)));
        assertTrue(eqv(stk1.getNthFromTop(2), constantPoly(3)));
    }
    
    @Test
    public void testDupWithMultVal() {
    	RatPolyStack stk1 = stack("123");
        stk1.dup();
        assertStackIs(stk1, "1123");
    }

	///////////////////////////////////////////////////////////////////////////////////////
	////	Swap
	///////////////////////////////////////////////////////////////////////////////////////  
    
    @Test
    public void testSwapWithTwoElems() {
        RatPolyStack stk1 = stack("23");
        stk1.swap();
        assertStackIs(stk1, "32");    
    }
    
    @Test
    public void testSwapWithMultElems() {
    	RatPolyStack stk1 = stack("123");
        stk1.swap();
        assertStackIs(stk1, "213");
    }

    @Test
    public void testSwapWitSameElems() {
    	RatPolyStack stk1 = stack("112");
        stk1.swap();
        assertStackIs(stk1, "112");
    }

	///////////////////////////////////////////////////////////////////////////////////////
	////	Clear
	///////////////////////////////////////////////////////////////////////////////////////    
    
    @Test
    public void testClear() {
        RatPolyStack stk1 = stack("123");
        stk1.clear();
        assertStackIs(stk1, "");
        RatPolyStack stk2 = stack("112");
        stk2.clear();
        assertStackIs(stk2, "");
    }

	///////////////////////////////////////////////////////////////////////////////////////
	////	Add
	///////////////////////////////////////////////////////////////////////////////////////  
    
    @Test
    public void testAddTwoElems() {
    	RatPolyStack stk1 = stack("11");
        stk1.add();
        assertStackIs(stk1, "2");
    }
    
    @Test
    public void testAddMultiElems() {
        RatPolyStack stk1 = stack("123");
        stk1.add();
        assertStackIs(stk1, "33");
        stk1.add();
        assertStackIs(stk1, "6");

        stk1 = stack("112");
        stk1.add();
        assertStackIs(stk1, "22");
        stk1.add();
        assertStackIs(stk1, "4");
        stk1.push(constantPoly(5));
        assertStackIs(stk1, "54");
        stk1.add();
        assertStackIs(stk1, "9");

    }
    
    ///////////////////////////////////////////////////////////////////////////////////////
    ////	Subtract
    ///////////////////////////////////////////////////////////////////////////////////////
    
    @Test
    public void testSubTwoElems() {
    	RatPolyStack stk1 = stack("12");
        stk1.sub();
        assertStackIs(stk1, "1");
    }

    @Test
    public void testSubMultiElems() {
        RatPolyStack stk1 = stack("123");
        stk1.sub();
        assertStackIs(stk1, "13");
        stk1.sub();
        assertStackIs(stk1, "2");

        stk1 = stack("5723");
        stk1.sub();
        assertStackIs(stk1, "223");
        stk1.sub();
        assertStackIs(stk1, "03");
        stk1.sub();
        assertStackIs(stk1, "3");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////	Multiplication
    ///////////////////////////////////////////////////////////////////////////////////////    
    
    @Test
    public void testMulTwoElems() {
    	RatPolyStack stk1 = stack("23");
        stk1.mul();
        assertStackIs(stk1, "6");
    }
    
    @Test
    public void testMulMultiElems() {
        RatPolyStack stk1 = stack("123");
        stk1.mul();
        assertStackIs(stk1, "23");
        stk1.mul();
        assertStackIs(stk1, "6");

        stk1 = stack("112");
        stk1.mul();
        assertStackIs(stk1, "12");
        stk1.mul();
        assertStackIs(stk1, "2");
        stk1.push(constantPoly(4));
        assertStackIs(stk1, "42");
        stk1.mul();
        assertStackIs(stk1, "8");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////	Division
    ///////////////////////////////////////////////////////////////////////////////////////  
    
    @Test
    public void testDivTwoElems() {
    	RatPolyStack stk1 = stack("28");
        stk1.div();
        assertStackIs(stk1, "4");
    }
    
    @Test
    public void testDivMultiElems() {
        RatPolyStack stk1 = stack("123");
        stk1.div();
        assertStackIs(stk1, "23");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////	Differentiate
    ///////////////////////////////////////////////////////////////////////////////////////  
    
    @Test
    public void testDifferentiate() {
        RatPolyStack stk1 = stack("123");
        stk1.differentiate();
        stk1.differentiate();
        stk1.differentiate();
        stk1.differentiate();
        assertEquals("Test if stack size changes", 3, stk1.size());
        assertStackIs(stk1, "023");

        RatPoly rp1 = new RatPoly(3, 5);
        RatPoly rp2 = new RatPoly(7, 0);
        RatPoly rp3 = new RatPoly(4, 1);
        stk1.push(rp1);
        stk1.push(rp2);
        stk1.push(rp3);

        stk1.differentiate();
        assertEquals("Test simple differentiate1", "4", stk1.pop().toString());
        stk1.differentiate();
        assertEquals("Test simple differentiate2", "0", stk1.pop().toString());
        stk1.differentiate();
        assertEquals("Test simple differentiate3", "15*x^4", stk1.pop().toString());
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////	Integrate
    ///////////////////////////////////////////////////////////////////////////////////////  
    
    @Test
    public void testIntegrate() {
        RatPolyStack stk1 = stack("123");
        stk1.integrate();
        stk1.integrate();
        stk1.integrate();
        stk1.integrate();
        assertEquals("Test if stack size changes", 3, stk1.size());
        assertEquals("Test simple integrate1", "1/24*x^4", stk1.pop().toString());
        RatPoly rp1 = new RatPoly(15, 4);
        RatPoly rp2 = new RatPoly(7, 0);
        RatPoly rp3 = new RatPoly(4, 0);
        stk1.push(rp1);
        stk1.push(rp2);
        stk1.push(rp3);

        stk1.integrate();
        assertEquals("Test simple integrate1", "4*x", stk1.pop().toString());
        stk1.integrate();
        assertEquals("Test simple integrate2", "7*x", stk1.pop().toString());
        stk1.integrate();
        assertEquals("Test simple integrate3", "3*x^5", stk1.pop().toString());
    }

}
