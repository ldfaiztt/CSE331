package hw4.test;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import hw4.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the DGraph class.
 * 
 * @author Chun-Wei Chen
 * @version 05/17/13
 */
public class DGraphTest {
	private static final int TIMEOUT = 2000;
	
	private final String NODE_A = "a";
	private final String NODE_B = "b";
	private final String EDGE_AA = "AA";
	private final String EDGE_AB = "AB";
	private final String EDGE_AB2 = "AB2";
	private final String EDGE_BA = "BA";
	private final String EDGE_BB = "BB";
	
	private DGraph<String, String> dgraph;
	private Set<String> nodes;
	private Set<LabeledEdge<String, String>> edges;

	@Before
	public void setUp() throws Exception {
		dgraph = new DGraph<String, String>();
		nodes = new HashSet<String>();
		edges = new HashSet<LabeledEdge<String, String>>();
	}

	@Test(timeout = TIMEOUT)
	public void testIsEmptyWhenConstructed() {
		assertTrue(dgraph.isEmpty());
	}
	
	@Test(timeout = TIMEOUT)
	public void testSizeWhenConstructed() {
		assertEquals(0, dgraph.size());
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetNodesWhenConstructed() {
		assertEquals(nodes, dgraph.getNodes());
	}
	
	@Test(timeout = TIMEOUT)
	public void testToStringWhenConstructed() {
		assertEquals("{}", dgraph.toString());
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testAddingNullNode() {
		dgraph.addNode(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testContainsNodeWithNullNode() {
		dgraph.containsNode(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testChildrenOfWithNullNode() {
		dgraph.childrenOf(null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testContainsNodeOnNodeAWithoutAddingNodeA() {
		assertFalse(dgraph.containsNode(NODE_A));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testChildrenOfNodeAWithoutAddingNodeA() {
		dgraph.childrenOf(NODE_A);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testAddEdgeWithFromToLabelNull() {
		dgraph.addEdge(null, null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testAddEdgeWithToLabelNull() {
		dgraph.addEdge(NODE_A, null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testAddEdgeWithLabelNull() {
		dgraph.addEdge(NODE_A, NODE_B, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testRemoveEdgeWithFromToLabelNull() {
		dgraph.removeEdge(null, null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testRemoveEdgeWithToLabelNull() {
		dgraph.removeEdge(NODE_A, null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testRemoveEdgeWithLabelNull() {
		dgraph.removeEdge(NODE_A, NODE_B, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void tesNumberOfEdgesWithNode1Null() {
		dgraph.numberOfEdges(null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void tesNumberOfEdgesWithNode2Null() {
		dgraph.numberOfEdges(NODE_A, null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testAddingOneNode() {
		assertTrue(dgraph.addNode(NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testIsEmptyAfterAddingOneNode() {
		testAddingOneNode();
		assertFalse(dgraph.isEmpty());
	}
	
	@Test(timeout = TIMEOUT)
	public void testSizeAfterAddingOneNode() {
		testAddingOneNode();
		assertEquals(1, dgraph.size());
	}
	
	@Test(timeout = TIMEOUT)
	public void testToStringAfterAddingOneNode() {
		testAddingOneNode();
		assertEquals("{a=[]}", dgraph.toString());
	}
	
	@Test(timeout = TIMEOUT)
	public void testContainsNodeOnNodeAAfterAddingNodeA() {
		testAddingOneNode();
		assertTrue(dgraph.containsNode("a"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testContainsNodeOnNodeBAfterAddingNodeA() {
		testAddingOneNode();
		assertFalse(dgraph.containsNode(NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeAWithoutAddingEdge() {
		testAddingOneNode();
		assertTrue(dgraph.childrenOf(NODE_A).isEmpty());
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetNodesAfterAddingOneNode() {
		testAddingOneNode();
		nodes.add("a");
		assertEquals(nodes, dgraph.getNodes());
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testAddEdgeABWithoutAddingNodeB() {
		testAddingOneNode();
		dgraph.addEdge(NODE_A, NODE_B, EDGE_AB);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testAddEdgeBAWithoutAddingNodeB() {
		testAddingOneNode();
		dgraph.addEdge(NODE_B, NODE_A, EDGE_BA);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testNumberOfEdgesFromNodeAToNodeBWithoutAddingNodeB() {
		testAddingOneNode();
		dgraph.numberOfEdges(NODE_A, NODE_B);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testNumberOfEdgesFromNodeBToNodeAWithoutAddingNodeB() {
		testAddingOneNode();
		dgraph.numberOfEdges(NODE_B, NODE_A);
	}
	
	@Test(timeout = TIMEOUT)
	public void testAddingSameNodeTwice() {
		testAddingOneNode();
		assertFalse(dgraph.addNode(NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSizeAfterAddingSameNodeTwice() {
		testAddingSameNodeTwice();
		assertEquals(1, dgraph.size());
	}
	
	@Test(timeout = TIMEOUT)
	public void testToStringAfterAddingSameNodeTwice() {
		testAddingSameNodeTwice();
		assertEquals("{a=[]}", dgraph.toString());
	}
	
	@Test(timeout = TIMEOUT)
	public void testAddingTwoDifferentNodes() {
		testAddingOneNode();
		assertTrue(dgraph.addNode("b"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testSizeAfterAddingTwoDifferentNodes() {
		testAddingTwoDifferentNodes();
		assertEquals(2, dgraph.size());
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetNodesAfterAddingTwoDifferentNodes() {
		testAddingTwoDifferentNodes();
		nodes.add(NODE_A);
		nodes.add(NODE_B);
		assertEquals(nodes, dgraph.getNodes());
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeAToNodeBWithoutAddingEdge() {
		testAddingTwoDifferentNodes();
		assertEquals(0, dgraph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeBToNodeAWithoutAddingEdge() {
		testAddingTwoDifferentNodes();
		assertEquals(0, dgraph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testAddingReflexiveEdgeOnNodeA() {
		testAddingOneNode();
		assertTrue(dgraph.addEdge(NODE_A, NODE_A, EDGE_AA));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeAAfterAddingReflexiveEdgeOnNodeA() {
		testAddingReflexiveEdgeOnNodeA();
		edges.add(new LabeledEdge<String, String>("a", "AA"));
		assertEquals(edges, dgraph.childrenOf(NODE_A));
		
	}
	
	@Test(timeout = TIMEOUT)
	public void testToStringAfterAddingReflexiveEdgeOnNodeA() {
		testAddingReflexiveEdgeOnNodeA();
		assertEquals("{a=[a(AA)]}", dgraph.toString());
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeAToNodeAAfterAddingReflexiveEdge() {
		testAddingReflexiveEdgeOnNodeA();
		assertEquals(1, dgraph.numberOfEdges(NODE_A, NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testRemoveReflexiveEdgeOnNodeA() {
		testAddingReflexiveEdgeOnNodeA();
		assertEquals(new LabeledEdge<String, String>("a", "AA"), 
				     dgraph.removeEdge(NODE_A, NODE_A, EDGE_AA));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testRemoveEdgeOnNodeAWithNonExistingEdge() {
		testAddingReflexiveEdgeOnNodeA();
		dgraph.removeEdge(NODE_A, NODE_B, EDGE_AA);
	}
	
	@Test(timeout = TIMEOUT)
	public void testRemoveEdgeWithExistingEdgeButDifferentLabel() {
		testAddingReflexiveEdgeOnNodeA();
		assertTrue(dgraph.removeEdge(NODE_A, NODE_A, EDGE_AB) == null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfAfterRemoveReflexiveEdgeOnNodeA() {
		testRemoveReflexiveEdgeOnNodeA();
		assertEquals(edges, dgraph.childrenOf(NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testToStringAfterRemoveReflexiveEdgeOnNodeA() {
		testRemoveReflexiveEdgeOnNodeA();
		assertEquals("{a=[]}", dgraph.toString());
	}
	
	@Test(timeout = TIMEOUT)
	public void testAddingOneEdgeBetweenTwoNodes() {
		testAddingTwoDifferentNodes();
		assertTrue(dgraph.addEdge(NODE_A, NODE_B, EDGE_AB));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeAAfterAddingOneEdgeBetweenNodeAAndNodeB() {
		testAddingOneEdgeBetweenTwoNodes();
		edges.add(new LabeledEdge<String, String>("b", "AB"));
		assertEquals(edges, dgraph.childrenOf(NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeBAfterAddingOneEdgeBetweenNodeAAndNodeB() {
		testAddingOneEdgeBetweenTwoNodes();
		assertEquals(edges, dgraph.childrenOf(NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeAToNodeBAfterAddingOneEdgeBetweenNodeAAndNodeB() {
		testAddingOneEdgeBetweenTwoNodes();
		assertEquals(1, dgraph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeBToNodeAAfterAddingOneEdgeBetweenNodeAAndNodeB() {
		testAddingOneEdgeBetweenTwoNodes();
		assertEquals(0, dgraph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testAddingSameEdgeAfterAddingOneEdgeBetweenTwoNodes() {
		testAddingOneEdgeBetweenTwoNodes();
		assertFalse(dgraph.addEdge(NODE_A, NODE_B, EDGE_AB));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeAAfterAddingSameEdge() {
		testAddingSameEdgeAfterAddingOneEdgeBetweenTwoNodes();
		edges.add(new LabeledEdge<String, String>("b", "AB"));
		assertEquals(edges, dgraph.childrenOf(NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeBAfterAddingSameEdge() {
		testChildrenOfNodeAAfterAddingSameEdge();
		assertEquals(new HashSet<LabeledEdge<String, String>>(), 
				     dgraph.childrenOf(NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testAddingTwoInverseDirectionsEdgesBetweenNodeAAndNodeB() {
		testAddingOneEdgeBetweenTwoNodes();
		assertTrue(dgraph.addEdge(NODE_B, NODE_A, EDGE_BA));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeAAfterAddingTwoInverseDirectionsEdges() {
		testAddingTwoInverseDirectionsEdgesBetweenNodeAAndNodeB();
		edges.add(new LabeledEdge<String, String>("b", "AB"));
		assertEquals(edges, dgraph.childrenOf(NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeBAfterAddingTwoInverseDirectionsEdges() {
		testAddingTwoInverseDirectionsEdgesBetweenNodeAAndNodeB();
		edges.add(new LabeledEdge<String, String>("a", "BA"));
		assertEquals(edges, dgraph.childrenOf(NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testMakeCompleteGraphWithTwoNodes() {
		// To my understanding, complete graph of n nodes 
		// directed graph has n^2 edges.
		testAddingReflexiveEdgeOnNodeA();
		assertTrue(dgraph.addNode(NODE_B));
		assertTrue(dgraph.addEdge(NODE_A, NODE_B, EDGE_AB));
		assertTrue(dgraph.addEdge(NODE_B, NODE_A, EDGE_BA));
		assertTrue(dgraph.addEdge(NODE_B, NODE_B, EDGE_BB));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeAAfterMakingCompleteGraphWithTwoNodes() {
		testMakeCompleteGraphWithTwoNodes();
		edges.add(new LabeledEdge<String, String>("a", "AA"));
		edges.add(new LabeledEdge<String, String>("b", "AB"));
		assertEquals(edges, dgraph.childrenOf(NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeBAfterMakingCompleteGraphWithTwoNodes() {
		testMakeCompleteGraphWithTwoNodes();
		edges.add(new LabeledEdge<String, String>("a", "BA"));
		edges.add(new LabeledEdge<String, String>("b", "BB"));
		assertEquals(edges, dgraph.childrenOf(NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeAToNodeAAfterMakingCompleteGraph() {
		testMakeCompleteGraphWithTwoNodes();
		assertEquals(1, dgraph.numberOfEdges(NODE_A, NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeAToNodeBAfterMakingCompleteGraph() {
		testMakeCompleteGraphWithTwoNodes();
		assertEquals(1, dgraph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeBToNodeAAfterMakingCompleteGraph() {
		testMakeCompleteGraphWithTwoNodes();
		assertEquals(1, dgraph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeBToNodeBAfterMakingCompleteGraph() {
		testMakeCompleteGraphWithTwoNodes();
		assertEquals(1, dgraph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testMakingMultigraphWithTwoNodes() {
		testMakeCompleteGraphWithTwoNodes();
		assertTrue(dgraph.addEdge(NODE_A, NODE_B, EDGE_AB2));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeAAfterMakingMultigraphWithTwoNodes() {
		testMakingMultigraphWithTwoNodes();
		edges.add(new LabeledEdge<String, String>("a", "AA"));
		edges.add(new LabeledEdge<String, String>("b", "AB"));
		edges.add(new LabeledEdge<String, String>("b", "AB2"));
		assertEquals(edges, dgraph.childrenOf(NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testChildrenOfNodeBAfterMakingMultigraphWithTwoNodes() {
		testMakingMultigraphWithTwoNodes();
		edges.add(new LabeledEdge<String, String>("a", "BA"));
		edges.add(new LabeledEdge<String, String>("b", "BB"));
		assertEquals(edges, dgraph.childrenOf(NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeAToNodeAAfterMakingMakingMultigraph() {
		testMakingMultigraphWithTwoNodes();
		assertEquals(1, dgraph.numberOfEdges(NODE_A, NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeAToNodeBAfterMakingMakingMultigraph() {
		testMakingMultigraphWithTwoNodes();
		assertEquals(2, dgraph.numberOfEdges(NODE_A, NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeBToNodeAAfterMakingMakingMultigraph() {
		testMakingMultigraphWithTwoNodes();
		assertEquals(1, dgraph.numberOfEdges(NODE_B, NODE_A));
	}
	
	@Test(timeout = TIMEOUT)
	public void testNumberOfEdgesFromNodeBToNodeBAfterMakingMakingMultigraph() {
		testMakingMultigraphWithTwoNodes();
		assertEquals(1, dgraph.numberOfEdges(NODE_B, NODE_B));
	}
	
	@Test(timeout = TIMEOUT)
	public void testEntrySetAfterMakingMultigraphWithTwoNodes() {
		testMakingMultigraphWithTwoNodes();
		Map<String, LinkedHashSet<LabeledEdge<String, String>>> map = 
				new LinkedHashMap<String, LinkedHashSet<LabeledEdge<String, String>>>();
		LinkedHashSet<LabeledEdge<String, String>> edges1 = 
				new LinkedHashSet<LabeledEdge<String, String>>();
		edges1.add(new LabeledEdge<String, String>(NODE_A, EDGE_AA));
		edges1.add(new LabeledEdge<String, String>(NODE_B, EDGE_AB));
		edges1.add(new LabeledEdge<String, String>(NODE_B, EDGE_AB2));
		map.put(NODE_A, edges1);
		LinkedHashSet<LabeledEdge<String, String>> edges2 = 
				new LinkedHashSet<LabeledEdge<String, String>>();
		edges2.add(new LabeledEdge<String, String>(NODE_A, EDGE_BA));
		edges2.add(new LabeledEdge<String, String>(NODE_B, EDGE_BB));
		map.put(NODE_B, edges2);
		
		for(Map.Entry<String, HashSet<LabeledEdge<String, String>>> entry : dgraph.entrySet()) {
			String node = entry.getKey();
			HashSet<LabeledEdge<String, String>> expected_edges = entry.getValue();
			Set<LabeledEdge<String, String>> actual_edges = map.get(node);
			assertEquals(expected_edges, actual_edges);
		}
	}
}