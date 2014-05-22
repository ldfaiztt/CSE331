package hw8;

import hw7.CampusRouteFindingModel;

/**
 * <b>CampusPathsMain</b> puts together all the MVC components and 
 * allows the user to ask for the shortest route between two 
 * buildings on campus.
 * <p>
 * 
 * @author Chun-Wei Chen
 * @version 06/06/13
 */
public class CampusPathsMain {
	
	/**
	 * Links together the model, view and controller components, 
	 * and start the GUI running.
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// create model using campus data
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/campus_buildings.dat", 
											"src/hw7/data/campus_paths.dat");
		
		// create GUI
		new CampusRouteFindingGUI(model);
	}
}