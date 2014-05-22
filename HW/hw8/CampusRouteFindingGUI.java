package hw8;

import java.awt.*;

import javax.swing.*;

import hw7.CampusRouteFindingModel;

/**
 * <b>CampusRouteFindingGUI</b> is a GUI view/controller for campus route 
 * finding tool.
 * <p>
 * 
 * @author Chun-Wei Chen
 * @version 06/06/13
 */
public class CampusRouteFindingGUI {
	private static final String WINDOW_TITLE = "Campus Route Finding Tool";
	private CampusRouteFindingModel model;  // the model of campus route finding tool
	private JFrame contentFrame;  // the frame of the GUI
	
	/**
	 * Constructs a GUI view/controller.
	 * 
	 * @param m the model of campus route finding tool
	 * @requires m != null
	 */
	public CampusRouteFindingGUI(CampusRouteFindingModel m) {
		if (m == null)
			throw new IllegalArgumentException("Model cannot be null.");
		
		// store the model
		model = m;
		
		// initialize the frame that will hold other components 
		contentFrame = new JFrame(WINDOW_TITLE);
		contentFrame.setPreferredSize(new Dimension(1024, 768));
		contentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Set the layout manager of the frame
		contentFrame.setLayout(new BoxLayout(contentFrame.getContentPane(),
							   BoxLayout.Y_AXIS));
		
		// create map view for this GUI
		CampusRouteFindingMapView view = new CampusRouteFindingMapView(model);
		
		// create controller panel
		CampusRouteFindingGUIController control = 
				new CampusRouteFindingGUIController(model, view);
		control.setPreferredSize(new Dimension(1024, 60));
		
		// add map view and controller panel to the frame
		contentFrame.add(control);
		contentFrame.add(view);
		
		// display the frame
		contentFrame.pack();
		contentFrame.setVisible(true);
	}
}