import java.util.ArrayList;
import java.util.List;

public class WeatherReportMain {

	/*
	 * Main's purpose is to link together the model, view and controller
	 * components, and start the GUI running.
	 */
	public static void main(String[] args) {
		// Create a model instance
		WeatherReportModel model = new WeatherReportModel();

		// Create the set of cities available to the GUI
		List<String> cities = new ArrayList<String>();
		cities.add("Boston");
		cities.add("Seattle");
		cities.add("London");

		// Create the GUI
		new WeatherReportGUI(model, cities);
	}
}
