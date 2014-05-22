package hw7;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * CampusRouteFindingTool is the view, controller, and the main 
 * of the CampusRouteFindingTool. This class allows user to 
 * type in some command to call appropriate method from view 
 * to get information from the model.
 * <p>
 * 
 * @author Chun-Wei Chen
 * @version 06/06/13
 */
public class CampusRouteFindingTool {
	private static final double epsilon = 0.00000001;
	private static final double oneEighthPI = Math.PI / 8;
	private static final double threeEighthsPI = 3 * oneEighthPI;
	private static final double fiveEighthsPI = 5 * oneEighthPI;
	private static final double sevenEighthsPI = 7 * oneEighthPI;
	private static final double negOneEighthPI = -1 * oneEighthPI;
	private static final double negThreeEighthsPI = -1 * threeEighthsPI;
	private static final double negFiveEighthsPI = -1 * fiveEighthsPI;
	private static final double negSevenEighthsPI = -1 * sevenEighthsPI;
	
	/**
	 * Lists all buildings' names (both abbreviated name and full name).
	 * 
	 * @requires model != null
	 * @param model model of the CampusRouteFindingTool
	 */
	public static void getAllBuildings(CampusRouteFindingModel model) {
		if (model == null)
			throw new IllegalArgumentException("model cannot be null.");
		
		String buildingsNames = "Buildings:\n";
		
		Map<String, String> buildings = model.getBuildings();
		
		// use TreeSet to sort the keys in lexicographic order
		TreeSet<String> buildingsKeys = new TreeSet<String>(buildings.keySet());
		
		for (String abbrev_name : buildingsKeys) {
			String full_name = buildings.get(abbrev_name);
			buildingsNames += "\t" + abbrev_name + ": " + full_name + "\n";
		}
		
		System.out.println(buildingsNames);
	}
	
	/**
	 * Gets the shortest walking route from one building to another building on campus.
	 * 
	 * @requires model, start, end != null and start and end to be buildings' abbreviated names
	 * @param model model of the CampusRouteFindingTool
	 * @param start starting building's abbreviated name of the walking route
	 * @param end ending building's abbreviated name of the walking route
	 */
	public static void getShortestWalkingRoute(CampusRouteFindingModel model,
			String start, String end) {
		if (model == null)
			throw new IllegalArgumentException("model cannot be null.");
		
		if (start == null)
			throw new IllegalArgumentException("start cannot be null.");
		
		if (end == null)
			throw new IllegalArgumentException("end cannot be null.");
		
		// get the location of starting and ending buildings
		/*@Nullable*/ Coordinates start_location = model.getLocationOfBuilding(start);
		/*@Nullable*/ Coordinates end_location = model.getLocationOfBuilding(end);

		if (start_location == null && end_location == null) {
			System.out.println("Unknown building: " + start);
			System.out.println("Unknown building: " + end + "\n");
		} else if (start_location == null) {
			System.out.println("Unknown building: " + start  + "\n");
		} else if (end_location == null) {
			System.out.println("Unknown building: " + end  + "\n");
		} else {
			String route = "";
			
			// get full name of the buildings
			String start_full = model.getFullNameOfBuilding(start);
			String end_full = model.getFullNameOfBuilding(end);
			
			route += "Path from " + start_full + " to " + end_full + ":\n";
			
			Map<Coordinates, Double> shortest_route = 
					model.findShortestWalkingRoute(start_location, end_location);
			
			double current_x = start_location.getX();
			double current_y = start_location.getY();
			double current_dist = 0.0;  // store the total distance up to this point
			
			// suppress warnings here since the spec says assume there exists a path
			// between any pair of buildings
			@SuppressWarnings("nullness")
			List<Coordinates> coords = new ArrayList<Coordinates>(shortest_route.keySet());
			coords = coords.subList(1, coords.size());
			
			for (Coordinates edge : coords) {
				double dest_x = edge.getX();
				double dest_y = edge.getY();
				double distance = shortest_route.get(edge).doubleValue();
				// get the angle for next direction
				double theta = Math.atan2(dest_y - current_y, dest_x - current_x);
				String direction = determineDirection(theta);
				
				route += String.format("\tWalk %.0f feet %s to (%.0f, %.0f)\n", 
						               (distance - current_dist), direction, 
						               dest_x, dest_y);
				
				// update the current coordinates in order to compute 
				// theta for the next edge
				current_x = dest_x;
				current_y = dest_y;
				current_dist = distance;
			}
			route += String.format("Total distance: %.0f feet\n", current_dist);
			
			System.out.println(route);
		}
	}
	
	/**
	 * Determine the direction based on the angle theta passed in.
	 * Possible output is N, E, S, W, NE, NW, SE, or SW
	 * 
	 * @param theta angle from the polar coordinates
	 * @return the direction based on the angle theta passed in
	 */
	private static String determineDirection(double theta) {
		if (Math.abs(theta) < epsilon || Math.abs(theta - oneEighthPI) < epsilon || 
			Math.abs(theta - negOneEighthPI) < epsilon || 
			(theta > 0 && theta < oneEighthPI) || (theta > negOneEighthPI && theta < 0)) {
			return "E";
		} else if (theta > oneEighthPI && theta < threeEighthsPI) {
			return "SE";
		} else if (theta > negThreeEighthsPI && theta < negOneEighthPI) {
			return "NE";
		} else if (Math.abs(theta - threeEighthsPI) < epsilon || 
				   Math.abs(theta - fiveEighthsPI) < epsilon || 
				   (theta > threeEighthsPI && theta < fiveEighthsPI)) {
			return "S";
		} else if (Math.abs(theta - negThreeEighthsPI) < epsilon || 
				   Math.abs(theta - negFiveEighthsPI) < epsilon || 
				   (theta > negFiveEighthsPI && theta < negThreeEighthsPI)) {
			return "N";
		} else if (theta > fiveEighthsPI && theta < sevenEighthsPI) {
			return "SW";
		} else if (theta > negSevenEighthsPI && theta < negFiveEighthsPI) {
			return "NW";
		} else {
			return "W";
		}
	}
	
	/**
	 * Main method allows user to find shortest walking route 
	 * between two buildings and get all the buildings' names 
	 * on campus.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CampusRouteFindingModel model = 
					new CampusRouteFindingModel("src/hw7/data/campus_buildings.dat", 
												"src/hw7/data/campus_paths.dat");
			
			String menu = "Menu:\n" + "\t" + "r to find a route\n" +
						  "\t" + "b to see a list of all buildings\n" +
					      "\t" + "q to quit\n";
			
			System.out.println(menu);
			Scanner reader = new Scanner(System.in);
			System.out.print("Enter an option ('m' to see the menu): ");
			
			while (true) {
				String input = reader.nextLine();
				
				// echo empty input lines or lines beginning with # 
				if (input.length() == 0 || input.startsWith("#")) {
					System.out.println(input);
					continue;
				}
				
				if (input.equals("m")) {
					System.out.println(menu);
				} else if (input.equals("b")) {
					getAllBuildings(model);
				} else if (input.equals("r")) {
					System.out.print("Abbreviated name of starting building: ");
					String start = reader.nextLine();
					System.out.print("Abbreviated name of ending building: ");
					String end = reader.nextLine();
					getShortestWalkingRoute(model, start, end);
				} else if (input.equals("q")) {
					reader.close();
					return;
				} else {
					System.out.println("Unknown option\n");
				}
				System.out.print("Enter an option ('m' to see the menu): ");
			}
		} catch (Exception e) {
			System.err.println(e.toString());
	    	e.printStackTrace(System.err);
		}
	}
}