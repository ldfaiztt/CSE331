package hw5.test;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;

import hw4.*;
import hw5.*;


/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph, the Marvel parser, and your BFS
 * algorithm.
 **/
public class HW5TestDriver {
	public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW5TestDriver td;

            if (args.length == 0) {
                td = new HW5TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW5TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw5.test.HW5TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw5.test.HW5TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    private final Map<String, DGraph<String, String>> graphs = 
    		new HashMap<String, DGraph<String, String>>();
    private final PrintWriter output;
    private final BufferedReader input;
    
    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW4TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW5TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }
    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }
    

    private void executeCommand(String command, List<String> arguments) {
        try {
        	if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else if (command.equals("LoadGraph")) {
                loadGraph(arguments);
            } else if (command.equals("FindPath")) {
            	findPath(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }
    
    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName, new DGraph<String, String>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        DGraph<String, String> g = graphs.get(graphName);
        g.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            String edgeLabel) {
        DGraph<String, String> g = graphs.get(graphName);
        g.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + 
        		" to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
    	DGraph<String, String> g = graphs.get(graphName);
    	String result = graphName + " contains:";
    	
    	Set<String> nodes = new TreeSet<String>(g.getNodes());
    	for (String node : nodes)
    		result += " " + node;
    	
    	output.println(result);
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
    	DGraph<String, String> g = graphs.get(graphName);
    	String result = "the children of " + parentName + " in " + graphName + " are:";
    	
    	// use special comparator to get edge in alphabetical order
    	// comparator compare the alphabetical order of destination of edge first,
    	// then compare the alphabetical order of label of edge
    	Set<LabeledEdge<String, String>> node_edges = 
    			new TreeSet<LabeledEdge<String, String>>(new Comparator<LabeledEdge<String, String>>() {
					public int compare(LabeledEdge<String, String> e1, LabeledEdge<String, String> e2) {
						if(!(e1.getDest().equals(e2.getDest())))
							return e1.getDest().compareTo(e2.getDest());
						
						if (!(e1.getLabel().equals(e2.getLabel())))
							return e1.getLabel().compareTo(e2.getLabel());
						
						return 0;
					}
				});
    	
    	node_edges.addAll(g.childrenOf(parentName));
    	
    	for (LabeledEdge<String, String> edge : node_edges)
    		result += " " + edge;
    	
        output.println(result);
    }
    
    private void loadGraph(List<String> arguments) throws Exception {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to loadGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String filename = arguments.get(1);
        
        loadGraph(graphName, filename);
    }

    private void loadGraph(String graphName, String filename) throws Exception {
    	filename = "src/hw5/data/" + filename;
        graphs.put(graphName, MarvelPaths.buildGraph(filename));
        output.println("loaded graph " + graphName);
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String node1Name = arguments.get(1).replace('_', ' ');
        String node2Name = arguments.get(2).replace('_', ' ');
        
        findPath(graphName, node1Name, node2Name);
    }

    private void findPath(String graphName, String node1Name, String node2Name) {
        DGraph<String, String> g = graphs.get(graphName);
        
        if ((!g.containsNode(node1Name)) && (!g.containsNode(node2Name))) {
        	output.println("unknown character " + node1Name);
        	output.println("unknown character " + node2Name);
        } else if (!(g.containsNode(node1Name))) {
        	output.println("unknown character " + node1Name);
        } else if (!(g.containsNode(node2Name))) {
        	output.println("unknown character " + node2Name);
        } else {
	        String currentNode = node1Name;
	        String result = "path from " + node1Name + " to " + node2Name + ":";
	        List<LabeledEdge<String, String>> path = 
	        		MarvelPaths.BFS(g, node1Name, node2Name);
	        
	        if (path == null) {
	        	result += "\n" + "no path found";
	    	} else {
	    		for (LabeledEdge<String, String> edge : path) {
	    			result += "\n" + currentNode + " to " + edge.getDest() + 
	    					" via " + edge.getLabel();
	    			currentNode = edge.getDest();
	    		}
	    	}
	        
	        output.println(result);
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}