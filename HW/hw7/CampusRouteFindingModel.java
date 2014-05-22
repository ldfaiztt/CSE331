package hw7;

import hw4.DGraph;
import hw4.LabeledEdge;
import hw6.MarvelPaths2;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * CampusRouteFindingModel represents the model of route-finding tool.
 * <p>
 * 
 * @specfield building_names : Map<String, String>  
 * 							   // abbreviated building's name associated with its full name
 * @specfield building_locations : Map<String, Coordinates>
 * 								   // abbreviated building's name associated with its location
 * @specfield campus_paths : DGraph<Coordinates, Double>
 * 							 // a graph of all the possible paths with the associated distance on campus 
 * @author Chun-Wei Chen
 * @version 06/06/13
 */
public class CampusRouteFindingModel {
	// Rep invariant:
	//     bNames, bLocations, campusPaths != null
	//     Every key of bNames and the value associated with it are not null.
	//     Every key of bLocations and the value associated with it are not null.
	
	// Abstract function: 
	//     AF(this) = a CampusRouteFindingModel m such that
	//		   m.building_names = this.bNames
	//		   m.building_locations = this.bLocations
	//		   m.campus_paths = this.campusPaths
	
	// constant variable for checkRep
	private static final boolean CHECK_OK = false;

	// a map of building's names that maps 
	// building's abbreviated name to full name
	private Map<String, String> bNames;
	
	// a map of building's names that maps 
	// building's full name to abbreviated name
	private Map<String, String> bNames2;
	
	// a map of building's locations that maps 
	// building's abbreviated name to its location
	private Map<String, Coordinates> bLocations;
	
	// a graph contains campus paths
	private DGraph<Coordinates, Double> campusPaths;
	
	/**
	 * Constructs a campus graph.
	 * 
	 * @requires Files are well-formed.	each line of <var>building</var> passed in 
	 * 			 should contain exactly four tokens separated by a 
	 * 			 tab character, or else starting with a # symbol indicates 
	 * 			 a comment line. <var>paths</var> passed in should start with 
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
	 * @param buildings file which contains data of campus buildings
	 * @param paths file which contains data of campus paths
	 * @throws Exception if the format of the files does not match the 
	 * 		   expected format
	 */
	public CampusRouteFindingModel(String buildings, String paths) throws Exception {
		if (buildings == null && paths == null)
			throw new IllegalArgumentException("buildings and paths files passed in " +
					"cannot be null.");
		if (buildings == null)
			throw new IllegalArgumentException("buildings file passed in cannot be null");
		if (paths == null)
			throw new IllegalArgumentException("paths file passed in cannot be null");
		
		// a map thats maps building's abbreviated name to its full name
		bNames = new HashMap<String, String>();
		
		// a map thats maps building's full name to its abbreviated name
		bNames2 = new HashMap<String, String>();
		
		// a map thats maps building's abbreviated name to its location
		bLocations = new HashMap<String, Coordinates>();
		
		// a graph to hold all the paths with the distance associated with them
		campusPaths = new DGraph<Coordinates, Double>();
		
		CampusDataParser.parseBuildingData(buildings, bNames, bNames2, bLocations);
		CampusDataParser.buildCampusPaths(paths, campusPaths);
		checkRep();
	}
	
	/**
	 * Returns a map that contains buildings abbreviated name 
	 * associated with its full name.
	 * 
	 * @return a map contains buildings abbreviated name associated with 
	 * 		   its full name
	 */
	public Map<String, String> getBuildings() {
		checkRep();
		return new HashMap<String, String> (bNames);
	}
	
	/**
	 * Returns the full name of the specified building.
	 * 
	 * @require buildingName != null and building is one of 
	 * 			the buildings on campus
	 * @param buildingName abbreviated name of a building on campus
	 * @return the full name of the specified building
	 */
	public String getFullNameOfBuilding(String buildingName) {
		checkRep();
		if (buildingName == null)
			throw new IllegalArgumentException("building name cannot " +
					"be null: " + buildingName);
		
		if (!bNames.containsKey(buildingName))
			throw new IllegalArgumentException("specified building name " +
					"is not one of the building on campus: " + buildingName);
		
		checkRep();
		return bNames.get(buildingName);
	}
	
	/**
	 * Returns the abbreviated name of the specified building.
	 * 
	 * @require buildingName != null and building is one of 
	 * 			the buildings on campus
	 * @param buildingName full name of a building on campus
	 * @return the abbreviated name of the specified building
	 */
	public String getAbbreviatedNameOfBuilding(String buildingName) {
		checkRep();
		if (buildingName == null)
			throw new IllegalArgumentException("building name cannot " +
					"be null: " + buildingName);
		
		if (!bNames2.containsKey(buildingName))
			throw new IllegalArgumentException("specified building name " +
					"is not one of the building on campus: " + buildingName);
		
		checkRep();
		return bNames2.get(buildingName);
	}
	
	/**
	 * Returns the location of the specified building.
	 * 
	 * @require building != null
	 * @param buildingName the name of a building on campus
	 * @return the location of the specified building or null 
	 * 		   if buildingName is not one of the building's 
	 * 		   name on campus
	 */
	public /*@Nullable*/ Coordinates getLocationOfBuilding(String buildingName) {
		checkRep();
		if (buildingName == null)
			throw new IllegalArgumentException("building name cannot " +
					"be null: " + buildingName);
		
		checkRep();
		return bLocations.get(buildingName);
	}
	
	/**
	 * Finds the shortest walking route from one point to another point on campus.
	 * 
	 * @param start starting point of the walking route
	 * @param end end point of the walking route
	 * @requires start, end != null, and start and end are nodes in campusPaths
	 * @return the shortest walking route from start to end, or null if 
	 * 		   no path exists from start to end
	 */
	public /*@Nullable*/ Map<Coordinates, Double> findShortestWalkingRoute(
			Coordinates start, Coordinates end) {
		List<LabeledEdge<Coordinates, Double>> route = 
				MarvelPaths2.minimumCostPath(campusPaths, start, end);
		
		// return null if no path was found
		if (route == null)
			return null;
		
		Map<Coordinates, Double> route_map = new LinkedHashMap<Coordinates, Double>();
		
		// store the shortest walking route to LinkedHashMap
		for (LabeledEdge<Coordinates, Double> path : route)
			route_map.put(path.getDest(), path.getLabel());
		
		return route_map;
	}
	
	/**
	 * Checks if representation invariant holds.
	 */
	private void checkRep() {
		if (CHECK_OK) {
			if (bNames == null)
				throw new RuntimeException("map of buildings' names is null");
			
			if (bLocations == null)
				throw new RuntimeException("map of buildings' locations is null");
			
			if (campusPaths == null)
				throw new RuntimeException("graph of campus paths is null");
			
			Set</*@KeyFor("bNames")*/ String> names = bNames.keySet();
			for (/*@KeyFor("bNames")*/ String name : names) {
				if (name == null) 
					throw new RuntimeException("abbreviated name of the building cannot be null.");
				
				if (bNames.get(name) == null)
					throw new RuntimeException("full name of the building cannot be null.");
			}
			
			Set</*@KeyFor("bNames2")*/ String> names2 = bNames2.keySet();
			for (/*@KeyFor("bNames2")*/ String name2 : names2) {
				if (name2 == null) 
					throw new RuntimeException("full name of the building cannot be null.");
				
				if (bNames2.get(name2) == null)
					throw new RuntimeException("abbreviated name of the building cannot be null.");
			}
			
			Set</*@KeyFor("bLocations")*/ String> locations = bLocations.keySet();
			for (/*@KeyFor("bLocations")*/ String name : locations) {
				if (name == null)
					throw new RuntimeException("abbreviated name of the building cannot be null.");
				
				if (bLocations.get(name) == null)
					throw new RuntimeException("location of the building cannot be null.");
			}
		}
	}
}