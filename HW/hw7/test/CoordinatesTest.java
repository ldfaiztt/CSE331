package hw7.test;

import static org.junit.Assert.*;
import hw7.Coordinates;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the Coordinates class.
 * 
 * @author Chun-Wei Chen
 * @version 05/28/13
 */
public class CoordinatesTest {
	private static final int TIMEOUT = 2000;
	public static final double epsilon = 0.00000001;
	private Coordinates coords;
	
	@Before
	public void setUp() throws Exception {
		coords = new Coordinates(1.23, 4.56);
	}

	@Test(timeout = TIMEOUT)
	public void testGetX() {
		assertTrue(Math.abs(coords.getX() - 1.23) < epsilon);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetY() {
		assertTrue(Math.abs(coords.getY() - 4.56) < epsilon);
	}
	
	@Test(timeout = TIMEOUT)
	public void testEqualsWithNull() {
		assertFalse(coords.equals(null));
	}
	
	@Test(timeout = TIMEOUT)
	public void testEqualsWithNonCoordinatesObject() {
		assertFalse(coords.equals("(1.23, 4.56)"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testEqualsWithSameCoordinates() {
		assertTrue(coords.equals(new Coordinates(1.23, 4.56)));
	}
	
	@Test(timeout = TIMEOUT)
	public void testHashCode() {
		assertEquals((new Coordinates(1.23, 4.56)).hashCode(), coords.hashCode());
	}
}