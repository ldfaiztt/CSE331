package hw7;

import hw4.DGraph;

import java.io.*;
import java.util.*;

/**
 * Parser utility to load the campus buildings and paths datasets.
 * 
 * @author Chun-Wei Chen
 * @version 05/28/13
 */
public class CampusDataParser {
	
	/**
	 * Read campus buildings dataset.
	 * Each line of the input contains abbreviated name of a building, 
	 * full name of a building, and x and y coordinate of location of 
	 * the building's entrance. And these four tokens are separated by 
	 * a tab character.
	 * 
	 * @requires file is well-formed, with each line containing exactly four
     *           tokens separated by a tab character, or else starting with
     *           a # symbol to indicate a comment line.
	 * @param buildings file which contains data of campus buildings
	 * @param bNames a map of building's names that maps building's 
	 * 		  abbreviated name to full name (empty initially)
	 * @param bNaems2 a map of building's names that maps building's 
	 * 		  full name to abbreviated name (empty initially)
	 * @param bLocations a map of building's locations that maps 
	 * 		  building's abbreviated name to its location (empty initially)
	 * @throws Exception if the format of the file does not match the 
	 * 		   expected format
	 */
	public static void parseBuildingData(String buildings, 
			Map<String, String> bNames, Map<String, String> bNames2, 
			Map<String, Coordinates> bLocations) throws Exception {
		
		BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(buildings));

	    	// Construct a map of buildings' names (map abbreviated name to full name) 
	    	// and a map of buildings' locations (map abbreviated name to 
	    	// location of the building).
	    	String inputLine;
	    	while ((inputLine = reader.readLine()) != null) {

	    		// Ignore comment lines.
	    		if (inputLine.startsWith("#")) {
	    			continue;
	    		}

	    		// Parse the data, stripping out quotation marks and throwing
	    		// an exception for malformed lines.
	    		inputLine = inputLine.replace("\"", "");
	    		String[] tokens = inputLine.split("\t");
	    		if (tokens.length != 4) {
	    			throw new Exception("Line should contain one tab between " +
	    					"each pair of tokens: " + inputLine);
	    		}

	    		String abbrev_name = tokens[0];
	    		String full_name = tokens[1];
	    		double x = Double.parseDouble(tokens[2]);
	    		double y = Double.parseDouble(tokens[3]);
	    		
	    		// map building's abbreviated name to its full name
	    		bNames.put(abbrev_name, full_name);
	    		
	    		// map building's full name to its abbreviated name
	    		bNames2.put(full_name, abbrev_name);
	    		
	    		// map building's abbreviated name to its location
	    		bLocations.put(abbrev_name, new Coordinates(x, y));
	    	}
	    } catch (IOException e) {
	    	System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	    } finally {
	    	if (reader != null) {
	    		reader.close();
	    	}
	    }
	}
	
	/**
	 * Read campus paths dataset and construct a campus paths.
	 * 
	 * @requires File is well-formed. File passed in should start with 
	 * 			 a non-indented line (if file is not empty) with 
	 * 			 coordinates of a point, followed by lines of 
	 * 			 coordinates and the distance between the coordinates of 
	 * 			 point in non-indented line and	coordinates of point in 
	 * 			 this line. Format of non-indented lines should be 
	 * 			 x coordinate of the point followed by a comma and then 
	 * 			 the y coordinate of the point (e.g. x,y). Format of 
	 * 			 indented lines should be like non-indented lines 
	 * 			 and followed by a colon and the distance between those 
	 * 			 two points.
	 * @param paths file which contains data of campus paths
	 * @param campusPaths a graph that contains campus paths (empty initially)
	 * @throws Exception if the format of the file does not match the 
	 * 		   expected format
	 */
	public static void buildCampusPaths(String paths, 
			DGraph<Coordinates, Double> campusPaths) throws Exception {
		
		BufferedReader reader = null;
	    try {
	    	reader = new BufferedReader(new FileReader(paths));
	    	
	    	String inputLine;
	    	/*@Nullable*/ Coordinates location = null;
	    	while ((inputLine = reader.readLine()) != null) {
	    		
	    		// Ignore comment lines.
	    		if (inputLine.startsWith("#")) {
	    			continue;
	    		}

	    		// Parse the data, stripping out quotation marks and throwing
	    		// an exception for malformed lines.
	    		inputLine = inputLine.replace("\"", "");
	    		// stripping out tab character from indented lines
	    		inputLine = inputLine.replace("\t", "");
	    		// split coordinate and distance from indented lines
	    		String[] tokens = inputLine.split(": ");
	    		
	    		String[] tokens2 = tokens[0].split(",");
    			double x = Double.parseDouble(tokens2[0]);
    			double y = Double.parseDouble(tokens2[1]);
    			Coordinates coords = new Coordinates(x, y);
    			
	    		// tokens.length == 1 means the line is non-indented line
	    		// tokens.length == 2 means the line is non-indented line
	    		// else means the file is not well-formed
	    		if (tokens.length == 1) {
	    			// add the coordinates to the graph is it
	    			// is not already in the graph
	    			if (!campusPaths.containsNode(coords))
	    				campusPaths.addNode(coords);
	    			
	    			// set location to be the coordinates of non-indented 
	    			// line in order to add edges when parse indented lines
	    			location = coords;
	    		} else if (tokens.length == 2) {
	    			if (location == null)
	    				throw new Exception("File is not well-formed. " +
	    						"Non-indented line should come before " +
	    						"indented line.");

	    			// add the coordinates to the graph is it
	    			// is not already in the graph
	    			if (!campusPaths.containsNode(coords))
	    				campusPaths.addNode(coords);
	    			
	    			double dist = Double.parseDouble(tokens[1]);
	    			campusPaths.addEdge(location, coords, dist);
	    		} else {
	    			throw new Exception("File is not well-formed. " +
	    					"Non-indented line should contain coordinates " +
	    					"of the point, and indented line should contain " +
	    					"coordinates of point followed by a colon and the " +
	    					"distance from start point the that point: " + inputLine);
	    		}
	    		
	    	}
	    } catch (IOException e) {
	    	System.err.println(e.toString());
	    	e.printStackTrace(System.err);
	    } finally {
	    	if (reader != null) {
	    		reader.close();
	    	}
	    }
	}
}