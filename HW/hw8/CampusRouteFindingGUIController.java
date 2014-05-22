package hw8;

import hw7.CampusRouteFindingModel;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.Set;
import java.util.TreeSet;

/**
 * <b>CampusRouteFindingGUIController</b> is a GUI controller 
 * for campus route finding tool.
 * <p>
 * 
 * @author Chun-Wei Chen
 * @version 06/06/13
 */
public class CampusRouteFindingGUIController extends JPanel {
	private static final long serialVersionUID = 1L;
	
	// model of campus route finding tool
	private CampusRouteFindingModel model;

	// GUI view of the for campus route finding tool
	private CampusRouteFindingMapView view;
	
	// a set of the abbreviated name of all the buildings on campus
	private Set<String> buildings;
	
	// description for startingSelectionBox
	private JLabel startingBuilding;
	
	// drop-down menu for starting building of route finding
	private JComboBox<String> startingSelectionBox;
	
	// description for endingSelectionBox
	private JLabel endingBuilding;
	
	// drop-down menu for ending building of route finding
	private JComboBox<String> endingSelectionBox;
	
	// display distance of the shortest route
	private JLabel distance;
	
	/**
	 * Constructs a GUI controller for campus route finding tool.
	 * 
	 * @param m model of campus route finding tool
	 * @param v GUI view of the for campus route finding tool
	 * @requires m, v != null
	 */
	public CampusRouteFindingGUIController(CampusRouteFindingModel m, CampusRouteFindingMapView v) {
		if (m == null)
			throw new IllegalArgumentException("Model cannot be null.");
		
		if (v == null)
			throw new IllegalArgumentException("View cannot be null.");
		
		model = m;
		view = v;
		
		// get full name of all the buildings on campus
		buildings = new TreeSet<String>(model.getBuildings().values());
		
		// create a panel to hold all the labels and combo boxes
		JPanel selection_panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// create a panel to hold all the buttons
		JPanel button_panel = new JPanel(new GridLayout(2, 1));
		
		// create all the labels and combo boxes
		startingBuilding = new JLabel("Starting building (cyan dot): ");
		startingSelectionBox = new JComboBox<String>(buildings.toArray(new String[0]));
		endingBuilding = new JLabel("Ending building (yellow dot): ");
		endingSelectionBox = new JComboBox<String>(buildings.toArray(new String[0]));
		
		// create all the buttons and then add ActionListener 
		// in order to update the view when the user click
		// the buttons
		JButton getRoute = new JButton("Get route!");
		getRoute.addActionListener(new UpdateActionListener());
		JButton reset = new JButton("Reset");
		reset.addActionListener(new UpdateActionListener());
		
		// add components to the selection_panel
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 3;
		selection_panel.add(startingBuilding, c);
		c.gridx = 4;
		c.gridy = 1;
		c.gridwidth = 5;
		selection_panel.add(startingSelectionBox, c);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		selection_panel.add(endingBuilding, c);
		c.gridx = 4;
		c.gridy = 2;
		c.gridwidth = 5;
		selection_panel.add(endingSelectionBox, c);
		
		// add components to the button_panel
		button_panel.add(getRoute);
		button_panel.add(reset);
		
		// add selection_panel to the controller panel
		this.add(selection_panel);
		
		// add button_panel to the controller panel
		this.add(button_panel);
		
		// create a panel to hold the labels that displays 
		// the distance of the route
		JPanel distance_panel = new JPanel(new GridLayout(2, 1));
		JLabel distanceDiscription = new JLabel("  Total distance:");
		distance = new JLabel();
		distance_panel.add(distanceDiscription);
		distance_panel.add(distance);
		
		// add distance_panel to the controller panel
		this.add(distance_panel);
	}
	
	/**
	 * An action listener which either updates the new shortest route
	 * between two buildings currently selected by the user or sets 
	 * the view to its initial state.
	 */
	private class UpdateActionListener implements ActionListener {
		/**
		 * Invoked when an action occurs.
		 * 
		 * @param e an event
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// get the string of the action
			String action = e.getActionCommand();
			
			if (action.equals("Get route!")) {
				// When the user click "Get route!", get the name of the buildings 
				// the user selected and find the shortest path between them. 
				String startingBuilding = startingSelectionBox.getSelectedItem().toString();
				String endingBuilding = endingSelectionBox.getSelectedItem().toString();
				Double d = view.getShortestWalkingRoute(startingBuilding, endingBuilding);
				distance.setText(String.format("  %.0f feet", d));
			} else {
				// Since only two kinds of buttons take UpdateActionListener, 
				// the action is "Reset" if it's not "Get route!" 
				// When this action happened, set the view to its initial state. 		
				view.reset();
				distance.setText("");
			}
		}
	}
}