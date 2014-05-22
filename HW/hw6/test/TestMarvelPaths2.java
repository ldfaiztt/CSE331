package hw6.test;

import static org.junit.Assert.*;
import hw4.DGraph;
import hw6.MarvelPaths2;

import org.junit.Before;
import org.junit.Test;

public class TestMarvelPaths2 {
	private static final int TIMEOUT = 2000;
	private DGraph<String, Double> g;

	@Before
	public void setUp() throws Exception {
		g = MarvelPaths2.buildGraph("src/hw5/data/zoos.tsv");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testBuildGraphWithNullInput() throws Exception {
		MarvelPaths2.buildGraph(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testminimumCostPathWithNullGraph() {
		MarvelPaths2.minimumCostPath(null, "Elephant", "Tiger");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testminimumCostPathWithNullStartOfPath() {
		MarvelPaths2.minimumCostPath(g, null, "Tiger");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testminimumCostPathWithNullEndOfPath() {
		MarvelPaths2.minimumCostPath(g, "Tiger", null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testminimumCostPathWithStartNotInGraph() {
		MarvelPaths2.minimumCostPath(g, "Bear", "Tiger");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testminimumCostPathWithEndNotInGraph() {
		MarvelPaths2.minimumCostPath(g, "Penguin", "Lion");
	}
}