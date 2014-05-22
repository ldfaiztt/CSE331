package hw4.test;

import hw4.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the LabeledEdge class.
 * 
 * @author Chun-Wei Chen
 * @version 05/17/13
 */
public class LabeledEdgeTest {
	private static final int TIMEOUT = 2000;
	private LabeledEdge<String, String> lEdge;
	
	@Before
	public void setUp() throws Exception {
		lEdge = new LabeledEdge<String, String>("b", "AB");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructEdgeWithNullDestination() {
		new LabeledEdge<String, String>(null, "AB");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructEdgeWithNullLabel() {
		new LabeledEdge<String, String>("b", null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetDestination() {
		assertEquals("b", lEdge.getDest());
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLabel() {
		assertEquals("AB", lEdge.getLabel());
	}
	
	@Test(timeout = TIMEOUT)
	public void testToString() {
		assertEquals("b(AB)", lEdge.toString());
	}
	
	@Test(timeout = TIMEOUT)
	public void testEqualsWithNonLabeledEdgeObject() {
		assertFalse(lEdge.equals("b(AB)"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testEqualsWithDifferentLabeledEdge() {
		assertFalse(lEdge.equals(new LabeledEdge<String, String>("a", "AB")));
	}
	
	@Test(timeout = TIMEOUT)
	public void testEqualsWithSameLabeledEdge() {
		assertTrue(lEdge.equals(new LabeledEdge<String, String>("b", "AB")));
	}
	
	@Test(timeout = TIMEOUT)
	public void testHashCodeForSameLabeledEdge() {
		int hc = "b".hashCode() + "AB".hashCode();
		assertEquals(hc, lEdge.hashCode());
	}
	
	@Test(timeout = TIMEOUT)
	public void testCompareToLexicographicallyGreaterDestination() {
		assertTrue(lEdge.compareTo(new LabeledEdge<String, String>("c", "AB")) < 0);
	}
	
	@Test(timeout = TIMEOUT)
	public void testCompareToLexicographicallyLessDestination() {
		assertTrue(lEdge.compareTo(new LabeledEdge<String, String>("a", "AB")) > 0);
	}
	
	@Test(timeout = TIMEOUT)
	public void testCompareToLexicographicallyGreaterLabel() {
		assertTrue(lEdge.compareTo(new LabeledEdge<String, String>("b", "BB")) < 0);
	}
	
	@Test(timeout = TIMEOUT)
	public void testCompareToLexicographicallyLessLabel() {
		assertTrue(lEdge.compareTo(new LabeledEdge<String, String>("b", "AA")) > 0);
	}
	
	@Test(timeout = TIMEOUT)
	public void testCompareToSameLabeledEdge() {
		assertTrue(lEdge.compareTo(new LabeledEdge<String, String>("b", "AB")) == 0);
	}
}