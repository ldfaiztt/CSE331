package hw5;

import hw4.DGraph;
import hw4.LabeledEdge;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class contains a method to build graph using data 
 * from specified file, and a method to find the shortest 
 * path from one node to another node.
 * 
 * @author Chun-Wei Chen
 * @version 05/21/13
 */
public class MarvelPaths {
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
	public static DGraph<String, String> buildGraph(String filename) throws Exception {
		if (filename == null)
			throw new IllegalArgumentException("filename cannot be null.");
		
		DGraph<String, String> mgraph = new DGraph<String, String>();
		HashSet<String> characters = new HashSet<String>();
		HashMap<String, List<String>> books = 
			new HashMap<String, List<String>>();
		MarvelParser.parseData(filename, characters, books);
		
		// add characters (nodes) to the graph
		for (String character : characters)
			mgraph.addNode(character);
		
		// connect characters (nodes) with books (labels of edges)
		for (String book : books.keySet()) {
			// If [a, b, c, d] in book_1, first connect a with b, c, and d. 
			// Since it's directed graph, so connect both direction 
			// (both a to b and b to a via book_1). After adding all 
			// in and outgoing edges of a via book_1, connect b with c, d. 
			// And keep doing this until no edge with label book_1 to be added.
			List<String> chars = books.get(book);
			int i = 1;
			for (String c1 : chars) {
				List<String> chars_sublist = chars.subList(i, chars.size());
		
				for (String c2 : chars_sublist) {
					// don't allow adding reflexive edges
					if (!(c1.equals(c2))) {
						mgraph.addEdge(c1, c2, book);
						mgraph.addEdge(c2, c1, book);
					}
				}
				i++;
			}
		}

		return mgraph;
	}
	
	/**
	 * Finds the shortest path from one character to another character.
	 * 
	 * @param graph the graph used to find shortest path from start to end
	 * @param start a character
	 * @param end another character
	 * @requires graph != null && start != null && end != null
	 * @return the shortest path from start to end, or null if 
	 * no path exists from start to end
	 * @throws IllegalArgumentException if either start or end is 
	 * not in the graph
	 */
	public static /*@Nullable*/ List<LabeledEdge<String, String>> BFS(
			DGraph<String, String> graph, String start, String end) {
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
		
		// nodes to be visited
		LinkedList</*@KeyFor("paths")*/ String> worklist = 
				new LinkedList</*@KeyFor("paths")*/ String>();
		
		// Each key in paths is a visited node and each value is 
		// a path from start to that node.
		HashMap<String, List<LabeledEdge<String, String>>> paths = 
				new HashMap<String, List<LabeledEdge<String, String>>>();
		
		paths.put(start, new ArrayList<LabeledEdge<String, String>>());
		// start is a key of paths since the above code put it in paths
		@SuppressWarnings("keyfor")
		/*@KeyFor("paths")*/ String start2 = start;
		worklist.add(start2);
		
		while (!(worklist.isEmpty())) {
			/*@KeyFor("paths")*/ String character = worklist.removeFirst();
			if (character.equals(end)) {
				List<LabeledEdge<String, String>> path = paths.get(character);
				return new ArrayList<LabeledEdge<String, String>>(path);
			}
			
			// use special comparator to get edge in alphabetical order
			// comparator compare the alphabetical order of destination of edge first,
			// then compare the alphabetical order of label of edge
			Set<LabeledEdge<String, String>> edges = 
					new TreeSet<LabeledEdge<String, String>>(new Comparator<LabeledEdge<String, String>>() {
						public int compare(LabeledEdge<String, String> e1, LabeledEdge<String, String> e2) {
							if(!(e1.getDest().equals(e2.getDest())))
								return e1.getDest().compareTo(e2.getDest());
							
							if (!(e1.getLabel().equals(e2.getLabel())))
								return e1.getLabel().compareTo(e2.getLabel());
							
							return 0;
						}
					});
			
			edges.addAll(graph.childrenOf(character));
			
			for (LabeledEdge<String, String> edge : edges) {
				String dest = edge.getDest();
				
				if (!(paths.containsKey(dest))) {
					// if the node is not already visited, then map the path
					// to this node by appending edge from character to this node 
					// to path from start to character
					List<LabeledEdge<String, String>> path = paths.get(character);
					List<LabeledEdge<String, String>> path_post = 
							new ArrayList<LabeledEdge<String, String>>(path);
					path_post.add(edge);
					paths.put(dest, path_post);
					// dest is a key of paths since the above code put it in paths
					@SuppressWarnings("keyfor")
					/*@KeyFor("paths")*/ String dest2 = dest;
					worklist.add(dest2);  // mark this node as visited
				}
			}
		}
		
		// no path exists from start to end
		return null;
	}
	
	/**
	 * Allows user to type in two characters and find the 
	 * shortest path of those two characters. 
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		DGraph<String, String> mgraph = 
				MarvelPaths.buildGraph("src/hw5/data/marvel.tsv");
		System.out.println("Find the shortest path for two Marvel characters.");
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
				String currentNode = start;
		        String result = "path from " + start + " to " + end + ":";
		        List<LabeledEdge<String, String>> path = 
		        		MarvelPaths.BFS(mgraph, start, end);
		        
		        if (path == null) {
		        	result += "\n" + "no path found";
		    	} else {
		    		for (LabeledEdge<String, String> edge : path) {
		    			result += "\n" + currentNode + " to " + edge.getDest() + 
		    					" via " + edge.getLabel();
		    			currentNode = edge.getDest();
		    		}
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