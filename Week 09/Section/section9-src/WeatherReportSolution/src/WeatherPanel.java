import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Displays weather information for a particular city
 */
public class WeatherPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	// The sub-components of this panel
	private JLabel title;
	private JLabel cityTimeLabel;
	private Thermometer thermometer;
	private WeatherIcon weatherIcon;

	/**
	 * Constructs a WeatherPanel
	 */
	public WeatherPanel() {

		// Initialize the layout of this panel
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		// Construct the sub-components of this panel
		title = new JLabel(WeatherReportGUI.WINDOW_TITLE);
		cityTimeLabel = new JLabel();
		thermometer = new Thermometer();
		weatherIcon = new WeatherIcon();
		
		// Add the subcomponents to the panel
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 2;
		this.add(title, c);

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 2;
		this.add(cityTimeLabel, c);

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 1;
		this.add(thermometer, c);

		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		this.add(weatherIcon, c);
	}

	/**
	 * Displays the weather information for a particular city
	 * 
	 * @param city
	 *            the name of the city to display
	 * @param model
	 *            the model to use when fetching weather information
	 */
	public void displayCity(String city, WeatherReportModel model) {
		cityTimeLabel.setText(city + ", " + model.getTime(city));
		thermometer.setPercentFull(model.getTemperature(city));
		weatherIcon.setState(model.getState(city));
	}
}
