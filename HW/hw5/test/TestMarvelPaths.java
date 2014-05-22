package hw5.test;

import org.junit.*;
import hw4.DGraph;
import hw5.MarvelPaths;

import org.junit.Before;
import org.junit.Test;

public class TestMarvelPaths {
	private static final int TIMEOUT = 2000;
	private DGraph<String, String> g;
	
	@Before
	public void setUp() throws Exception {
		g = MarvelPaths.buildGraph("src/hw5/data/zoos.tsv");
	}

	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testBuildGraphWithNullInput() throws Exception {
		MarvelPaths.buildGraph(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testBFSWithNullGraph() {
		MarvelPaths.BFS(null, "Elephant", "Tiger");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testBFSWithNullStartOfPath() {
		MarvelPaths.BFS(g, null, "Tiger");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testBFSWithNullEndOfPath() {
		MarvelPaths.BFS(g, "Tiger", null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testBFSWithStartNotInGraph() {
		MarvelPaths.BFS(g, "Bear", "Tiger");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testBFSWithEndNotInGraph() {
		MarvelPaths.BFS(g, "Penguin", "Lion");
	}
}