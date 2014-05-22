package hw6;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

import hw4.DGraph;
import hw4.LabeledEdge;

/**
 * This class contains a method to build graph using data 
 * from specified file, and a method to find the 
 * minimum-cost path from one node to another node.
 * 
 * @author Chun-Wei Chen
 * @version 06/06/13
 */
public class MarvelPaths2 {
	/**
	 * Builds graph using the data from the file.
	 * 
	 * @param filename file to be used to build the graph
	 * @requires filename != null
	 * @throws Exception if fail to read data from the specified 
	 * file or the format of the file does not match the 
	 * expected format
	 * @throws IllegalArgumentException if filename == null
	 */
	public static DGraph<String, Double> buildGraph(String filename) throws Exception {
		if (filename == null)
			throw new IllegalArgumentException("filename cannot be null.");
		
		HashMap<String, HashMap<String, Integer>> count_common = 
				new HashMap<String, HashMap<String, Integer>>();
		
		// parse the data in order to build graph
		MarvelParser2.parseData(filename, count_common);
		DGraph<String, Double> doubleGraph = new DGraph<String, Double>();
		
		// add nodes to the graph
		for (String node : count_common.keySet()) {
			doubleGraph.addNode(node);
		}
		
		// add weighted edges of nodes to the graph, 
		// where the weight of the edge between two characters 
		// is the inverse of how many comic books two 
		// characters are in together
		for (String node1 : count_common.keySet()) {
			HashMap<String, Integer> count = count_common.get(node1);
			for (String node2 : count.keySet()) {
				int num = count.get(node2);
				doubleGraph.addEdge(node1, node2, 1.0 / num);
				doubleGraph.addEdge(node2, node1, 1.0 / num);
			}
		}
		
		return doubleGraph;
	}
	
	/**
	 * Finds the minimum-cost path from one character to another character.
	 * 
	 * @param graph the graph used to find shortest path from start to end
	 * @param start a character
	 * @param end another character
	 * @requires graph != null && start != null && end != null
	 * @return the minimum-cost path from start to end, or null if 
	 * no path exists from start to end
	 * @throws IllegalArgumentException if either start or end is 
	 * not in the graph
	 */
	public static <T> /*@Nullable*/ List<LabeledEdge<T, Double>> minimumCostPath(
			DGraph<T, Double> graph, T start, T end) {
		if (graph == null)
			throw new IllegalArgumentException("graph cannot be null.");
		
		if (start == null || end == null)
			throw new IllegalArgumentException("start and end cannot be null.");
		
		if (!(graph.containsNode(start)))
			throw new IllegalArgumentException("Characters " + start + 
					"is not in the graph.");
		
		if (!(graph.containsNode(end)))
			throw new IllegalArgumentException("Characters " + end + 
					"is not in the graph.");
		
		// Each element in active is a path from start to a given node.
		// A path's "priority" in the queue is the total cost of that path.
		// Create the priority queue with specified comparator 
		// to order paths by their total costs.
		PriorityQueue<ArrayList<LabeledEdge<T, Double>>> active = 
				new PriorityQueue<ArrayList<LabeledEdge<T, Double>>>(10, 
						new Comparator<ArrayList<LabeledEdge<T, Double>>>() {
							public int compare(ArrayList<LabeledEdge<T, Double>> path1, 
											   ArrayList<LabeledEdge<T, Double>> path2) {
								LabeledEdge<T, Double> dest1 = path1.get(path1.size() - 1);
								LabeledEdge<T, Double> dest2 = path2.get(path2.size() - 1);
								if (!(dest1.getLabel().equals(dest2.getLabel())))
									return dest1.getLabel().compareTo(dest2.getLabel());
								
								return path1.size() - path2.size();
							}
						});
		
		// known contains nodes for which we know the minimum-cost path from start
		Set<T> known = new HashSet<T>();
		
		// the path from start to itself has zero cost since it contains no edge,
		// so add the edge with zero cost to active
		ArrayList<LabeledEdge<T, Double>> begin = new ArrayList<LabeledEdge<T, Double>>();
		begin.add(new LabeledEdge<T, Double>(start, 0.0));
		active.add(begin);
		
		while (!active.isEmpty()) {
			// minPath is the lowest-cost path in active and 
			// is the minimum-cost path for some node
			ArrayList<LabeledEdge<T, Double>> minPath = active.poll();
			
			// I've checked the PriorityQueue is not empty before calling
			// poll method, so minPath won't be null
			@SuppressWarnings("nullness")
			LabeledEdge<T, Double> endOfMinPath = minPath.get(minPath.size() - 1);
			// minDest is destination of minPath
			T minDest = endOfMinPath.getDest();
			// minCost is the cost of minPath
			double minCost = endOfMinPath.getLabel();
			
			// return minPath if the destination of minPath 
			// is equal to end passed in by client
			if (minDest.equals(end))
				return minPath;
			
			// if the minimum-cost path from start to minDest is already known, 
			// skip this one and examine the next one in active
			if (known.contains(minDest))
				continue;
			
			Set<LabeledEdge<T, Double>> children = graph.childrenOf(minDest);
			for (LabeledEdge<T, Double> e : children) {
				// If we don't know the minimum-cost path from start to child,
	            // examine the path we've just found
				if (!known.contains(e.getDest())) {
					double newCost = minCost + e.getLabel();
					ArrayList<LabeledEdge<T, Double>> newPath = 
							new ArrayList<LabeledEdge<T, Double>>(minPath); 
					newPath.add(new LabeledEdge<T, Double>(e.getDest(), newCost));
					active.add(newPath);
				}
			}
			
			// after examining all the edges begin from minDest, 
			// marked minDest as known
			known.add(minDest);
		}
		
		// no path exists from start to end
		return null;
	}
	
	/**
	 * Allows user to type in two characters and find the 
	 * minimum-cost path of those two characters. 
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DGraph<String, Double> mgraph = buildGraph("src/hw6/data/marvel.tsv");
		System.out.println("Find the minimum-cost path for two Marvel characters.");
		Scanner reader = new Scanner(System.in);
		String start, end; 
		boolean again_flag = false;
		do {
			System.out.print("Please type in a character: ");
			start = reader.nextLine();
			System.out.print("Please type in another character: ");
			end = reader.nextLine();
			
			if (!(mgraph.containsNode(start) || mgraph.containsNode(end))) {
				System.out.println("unknown character " + start);
				System.out.println("unknown character " + end);
	        } else if (!(mgraph.containsNode(start))) {
	        	System.out.println("unknown character " + start);
	        } else if (!(mgraph.containsNode(end))) {
	        	System.out.println("unknown character " + end);
	        } else {
		        String result = "path from " + start + " to " + end + ":";
		        List<LabeledEdge<String, Double>> path = 
		        		minimumCostPath(mgraph, start, end);
		        
		        if (path == null) {
		        	result += "\n" + "no path found";
		    	} else {
		    		String currentNode = start;
		    		double midCost = 0.0;
		    		path = path.subList(1, path.size());
		    		for (LabeledEdge<String, Double> edge : path) {
		    			result += "\n" + currentNode + " to " + 
		    					  edge.getDest() + " with weight " + 
		    					  String.format("%.3f", (edge.getLabel() - midCost));
		    			midCost = edge.getLabel();
		    			currentNode = edge.getDest();
		    		}
		    		result += "\n" + "total cost: " + String.format("%.3f", midCost);
		    	}
		        
		        System.out.println(result);
	        }
			System.out.print("Try again? ");
	        String answer = reader.nextLine();
	        answer = answer.toLowerCase();
	        if (answer.length() == 0 || answer.charAt(0) != 'y')
	        	again_flag = false;
	        else
	        	again_flag = true;
		} while (again_flag);
		
		System.out.println("Goodbye!");
		reader.close();
	}
}