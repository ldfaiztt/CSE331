package hw4;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <b>DGraph</b> represents a mutable, directed graph.
 * <p>
 * @specfield nodes : Set<<param>String</param>>
 *                    // The nodes (vertices) of the graph.
 * @specfield outgoing_edges : 
 *            Collection<<param>HashSet<<param>LabeledEdge</param>></param>>
 *            // The edges (with label and destination, but without origin) 
 *		         of the graph.
 *
 * @author Chun-Wei Chen
 * @version 05/22/13
 */
public class DGraph<T, E extends Comparable<E>> {
	// Rep invariant:
	//     dgraph != null
	//     Every node and every edge in dgraph are not null.
	//     dgraph must contain node n if n is included in 
	//     any edge in the dgraph.
	
	// Abstract function:
	//     AF(this) = directed graph g such that
	//         {} if g is an empty graph
	//         if a is a node in g.nodes, then
	//         {a=[], ...} if a has no outgoing edge
	//         {a=[b(e), c(f), ...], b=[...], c=[...]} o.w.
	//         where b, c are destinations of a's outgoing edges, 
	//         and e, f are label of those edges, respectively
	
	// constant variable for checkRep
	private final static boolean CHECK_OK = false; 
	
	// directed graph
	private final Map<T, HashSet<LabeledEdge<T, E>>> dgraph;
	
	/**
	 * Creates an empty directed graph.
	 * 
	 * @effect constructs an empty directed graph
	 */
	public DGraph() {
		dgraph = new HashMap<T, HashSet<LabeledEdge<T, E>>>();
		checkRep();
	}
	
	/**
	 * Adds node <var>n</var> to the graph if it is not already present. 
	 * 
	 * @param n node to be added
	 * @modifies this.nodes
	 * @effects adds node <var>n</var> to this.nodes if 
	 * it is not already present
	 * @return true if this graph did not already contain node <var>n</var>
	 */
	public boolean addNode(T n) {
		checkRep();
		
		if (n == null)
			throw new IllegalArgumentException("Node cannot be null.");
		
		boolean add_success = false;
		// check if the node passed in already exists in the graph
		if (!(dgraph.containsKey(n))) {
			dgraph.put(n, new HashSet<LabeledEdge<T, E>>());
			add_success = true;
		}
		
		checkRep();
		return add_success;
	}
	
	/**
	 * Adds edge from <var>from</var> to <var>to</var> with label 
	 * <var>label</var> to the graph if both nodes <var>from</var> 
	 * and <var>to</var> exists in graph, and edge from <var>from</var> 
	 * to <var>to</var> with label <var>label</var> is not already 
	 * present in the graph.
	 * 
	 * @param from origin of the edge
	 * @param to destination of the edge
	 * @param label label of the edge
	 * @requires from, to, label != null
	 * @modifies this.outgoing_edges
	 * @effects Adds edge from <var>from</var> to <var>to</var> with label 
	 * <var>label</var> to the graph if the same edge is not already 
	 * present in the graph.
	 * @throws IllegalArgumentException if either node <var>from</var> 
	 * or <var>to</var> is not in this.nodes
	 * @return true if this graph did not already contain edge from 
	 * <var>from</var> to <var>to</var> with label <var>label</var>
	 */
	public boolean addEdge(T from, T to, E label) {
		checkRep();
		
		if (from == null || to == null || label == null)
			throw new IllegalArgumentException("Node and label " +
					"cannot be null.");
		
		// check if nodes of the edge passed in already exist in the graph
		if (!(dgraph.containsKey(from)))
				throw new IllegalArgumentException("Node " + from + 
						" passed in is not present in the graph.");
		
		if (!(dgraph.containsKey(to)))
			throw new IllegalArgumentException("Node " + to + 
					" passed in is not present in the graph.");
		
		boolean add_success = false;
		
		HashSet<LabeledEdge<T, E>> from_edges = dgraph.get(from);
		LabeledEdge<T, E> e = new LabeledEdge<T, E>(to, label);
		
		// check if specified edge already exists in the graph
		if (!(from_edges.contains(e))) {
			from_edges.add(e);
			add_success = true;
		}
		
		checkRep();
		return add_success;
	}
	
	/**
	 * Return true if node <var>n</var> is in the graph.
	 * 
	 * @param n a node
	 * @requires n != null
	 * @return true if node <var>n</var> is in this.nodes
	 */
	/*@AssertNonNullIfTrue("dgraph.get(#1)")*/
	public boolean containsNode(T n) {
		checkRep();
		
		if (n == null)
			throw new IllegalArgumentException("Node cannot be null.");
		
		checkRep();
		return dgraph.containsKey(n);
	}
	
	/**
	 * Return a set of nodes.
	 * 
	 * @return a set of nodes
	 */
	public Set<T> getNodes() {
		checkRep();
		return new HashSet<T>(dgraph.keySet());
	}
	
	/**
	 * Returns number of nodes in the graph.
	 * 
	 * @return number of nodes in the graph
	 */
	public int size() {
		checkRep();
		return dgraph.size();
	}
	
	/**
	 * Returns true if the graph is empty.
	 * 
	 * @return true if the graph is empty
	 */
	public boolean isEmpty() {
		checkRep();
		return dgraph.isEmpty();
	}
	
	/**
	 * Returns a set of outgoing edges of node <var>n</var>.
	 * 
	 * @param n a node
	 * @requires n != null
	 * @return a set of outgoing edges of node <var>n</var>
	 * @throws IllegalArgumentException if node <var>n</var> 
	 * is not in this.nodes
	 */
	public Set<LabeledEdge<T, E>> childrenOf(T n) {
		checkRep();
		
		if (n == null)
			throw new IllegalArgumentException("Argument cannot be null.");
		
		if (!(containsNode(n)))
			throw new IllegalArgumentException("Node " + n + 
					" is not in the graph.");
		
		HashSet<LabeledEdge<T, E>> edges = dgraph.get(n);
		checkRep();
		return new HashSet<LabeledEdge<T, E>>(edges);
	}
	
	/**
	 * Returns number of edges from one node to another node.
	 * 
	 * @param from origin of the edge
	 * @param to destination of the edge
	 * @requires from, to != null
	 * @throws IllegalArgumentException if either node <var>from</var> 
	 * or <var>to</var> is not in this.nodes
	 * @return number of edges from <var>node1</var> to <var>node2</var>
	 */
	public int numberOfEdges(T node1, T node2) {
		checkRep();
		
		if (node1 == null || node2 == null)
			throw new IllegalArgumentException("Node cannot be null.");
		
		// check if nodes of the edge passed in already exist in the graph
		if (!(dgraph.containsKey(node1)))
			throw new IllegalArgumentException("Node " + node1 + 
					" passed in is not present in the graph.");
			
		if (!(dgraph.containsKey(node2)))
			throw new IllegalArgumentException("Node " + node2 + 
					" passed in is not present in the graph.");
		
		Set<LabeledEdge<T, E>> children = dgraph.get(node1);
		int count = 0;
		for (LabeledEdge<T, E> e : children) {
			// add 1 to count if the destination of the edge
			// is equal to node2
			if (e.getDest().equals(node2))
				count++;
		}
		
		checkRep();
		return count;
	}
	
	/**
	 * Removes an edge from <var>from</var> to <var>to</var> 
	 * with label <var>label</var> from the graph and returns 
	 * the edge. Returns null if the specified edge doesn't exist.
	 * 
	 * @param from origin of the edge
	 * @param to destination of the edge
	 * @param label label of the edge
	 * @requires from, to, label != null
	 * @modifies this.outgoing_edges
	 * @effects removes specified edge from this.outgoing_edges
	 * @throws IllegalArgumentException if either node <var>from</var> 
	 * or <var>to</var> is not in this.nodes
	 * @return the edge removed from the graph, or null 
	 * if the specified edge doesn't exist
	 */
	public /*@Nullable*/ LabeledEdge<T, E> removeEdge(T from, T to, 
													  E label) {
		checkRep();
		
		if (from == null || to == null || label == null)
			throw new IllegalArgumentException("Node and label cannot " +
					"be null.");
		
		// check if nodes of the edge passed in already exist in the graph
		if (!(dgraph.containsKey(from)))
				throw new IllegalArgumentException("Node " + from + 
						" passed in is not present in the graph.");
		
		if (!(dgraph.containsKey(to)))
			throw new IllegalArgumentException("Node " + to + 
					" passed in is not present in the graph.");
		
		HashSet<LabeledEdge<T, E>> from_edges = dgraph.get(from);
		LabeledEdge<T, E> e = new LabeledEdge<T, E>(to, label);
		
		// check if the specified edge is in the graph
		if (!(from_edges.contains(e)))
			return null;
		
		from_edges.remove(e);
		checkRep();
		return e;
	}
	
	/**
	 * Returns a set view of the graph.
	 * 
	 * @return a set view of the graph
	 */
	public Set<Map.Entry<T, HashSet<LabeledEdge<T, E>>>> entrySet() {
		checkRep();
		return Collections.unmodifiableSet(dgraph.entrySet());
	}
	
	/**
	 * Returns string representation of the graph.
	 * 
	 * @return string representation of the graph
	 */
	public String toString() {
		checkRep();
		return dgraph.toString();
	}
	
	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() throws RuntimeException {
		if (CHECK_OK) {
			// check if the graph itself is null
			if (dgraph == null)
				throw new RuntimeException("The graph cannot be null.");
			
			Set</*@KeyFor("dgraph")*/ T> nodes = dgraph.keySet();
			
			// check if any node in graph is null
			for (/*@KeyFor("dgraph")*/ T n : nodes) {
				if (n == null)
					throw new RuntimeException("Node cannot be null.");
				
				HashSet<LabeledEdge<T, E>> node_edges = dgraph.get(n);
				// check if any edge in graph is null
				for (LabeledEdge<T, E> le : node_edges) {
					if (le == null)
						throw new RuntimeException("Edge cannot be null.");
					
					// check if any destination node of edges 
					// in the graph doesn't exist 
					if (!(dgraph.containsKey(le.getDest())))
						throw new RuntimeException("Destination node of edge " +
								"must be in the graph before the edge exists.");
				}
			}
		}
	}
}