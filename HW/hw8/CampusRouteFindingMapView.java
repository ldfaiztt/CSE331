package hw8;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import hw7.CampusRouteFindingModel;
import hw7.Coordinates;

/**
 * <b>CampusRouteFindingMapView</b> is a GUI view 
 * for campus route finding tool.
 * <p>
 * 
 * @author Chun-Wei Chen
 * @version 06/06/13
 */
public class CampusRouteFindingMapView extends JComponent {
	private static final long serialVersionUID = 1L;
	
	// image of campus map
	private static final String CAMPUS_MAP = "src/hw7/data/campus_map.jpg";
	
	// the width of image displayed
	private int displayed_width;
	
	// the height of image displayed
	private int displayed_height;
	
	// ratio of width of display image to actual image's width
	private double width_ratio;
	
	// ratio of height of display image to actual image's height
	private double height_ratio;
	
	// model of campus route finding tool
	private CampusRouteFindingModel model;
	
	// the shortest route to display on the map
	private Map<Coordinates, Double> route;
	
	// list of Coordinates in the shortest route
	private List<Coordinates> coords;
	
	private BufferedImage img;
	
	/**
	 * Constructs a GUI view of campus route finding tool.
	 * 
	 * @param m the model of campus route finding tool
	 * @requires m != null
	 */
	public CampusRouteFindingMapView(CampusRouteFindingModel m) {
		if (m == null)
			throw new IllegalArgumentException("Model cannot be null.");
		
		model = m;
		
		displayed_width = 1008;
		displayed_height = 669;
		
		// route and coords are initially null
		route = null;
		coords = null;
		
		this.setPreferredSize(new Dimension(displayed_width, displayed_height));
		
		// load the image
		try {
			img = ImageIO.read(new File(CAMPUS_MAP));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// get the ratio of display/actual width and height 
		// in order to draw the route on the correct position 
		// of the map
		width_ratio = displayed_width * 1.0 / img.getWidth();
		height_ratio = displayed_height * 1.0 / img.getHeight();
	}

	/**
	 * Gets the shortest walking route from one building to another building on campus.
	 * 
	 * @param start starting building's full name of the walking route
	 * @param end ending building's full name of the walking route
	 * @return total distance of the shortest route
	 */
	public Double getShortestWalkingRoute(String start, String end) {
		Coordinates start_location = 
				model.getLocationOfBuilding(model.getAbbreviatedNameOfBuilding(start));
		Coordinates end_location = 
				model.getLocationOfBuilding(model.getAbbreviatedNameOfBuilding(end));
		
		route = model.findShortestWalkingRoute(start_location, end_location);
		coords = new ArrayList<Coordinates>(route.keySet());
		Double distance = route.get(coords.get(coords.size() - 1));
		repaint();
		return distance;
	}
	
	/**
	 * Sets the view to its initial state.
	 */
	public void reset() {
		route = null;
		repaint();
	}
	
	/**
	 * Paints this component on the given Graphics.
	 * 
	 * @param g the graphics to use when painting
	 * @modifies GUI the user sees
	 * @effect either display the route between two buildings the user asks, 
	 * 		   clear the route displayed on the map, or resize the map and 
	 * 		   route (if displays) when the frame is resized
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		displayed_width = getWidth();
		displayed_height = getHeight();
		width_ratio = displayed_width * 1.0 / img.getWidth();
		height_ratio = displayed_height * 1.0 / img.getHeight();
		
		// draw campus map
		g2d.drawImage(img, 0, 0, displayed_width, displayed_height, 
					  0, 0, img.getWidth(), img.getHeight(), null);
		
		if (route != null) {
			coords = new ArrayList<Coordinates>(route.keySet());
			// store starting points in order to mark the point later
			int start_x = (int) Math.round(coords.get(0).getX() * width_ratio);
			int start_y = (int) Math.round(coords.get(0).getY() * height_ratio);
			
			// get the starting building's position of the shortest route
			int current_x = start_x;
			int current_y = start_y;
			
			// draw the shortest route on the map
			g2d.setColor(Color.RED);
			for (Coordinates coord : coords) {
				int dest_x = (int) Math.round(coord.getX() * width_ratio);
				int dest_y = (int) Math.round(coord.getY() * height_ratio);
				g2d.drawLine(current_x, current_y, dest_x, dest_y);
				
				current_x = dest_x;
				current_y = dest_y;
			}
			
			// mark the ending building's position of the shortest route by a yellow circle
			g2d.setColor(Color.CYAN);
			g2d.fillOval(start_x - 2, start_y - 2, 4, 4);
			
			// mark the ending building's position of the shortest route by a blue circle
			g2d.setColor(Color.YELLOW);
			g2d.fillOval(current_x - 2, current_y - 2, 4, 4);
		}
	}
}