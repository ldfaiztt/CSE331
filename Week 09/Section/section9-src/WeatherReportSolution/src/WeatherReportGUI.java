import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A GUI view/controller for the weather report swing example.
 */
public class WeatherReportGUI {
	// Some constants that define the look and feel of the view
	static final String WINDOW_TITLE = "Weather Report";
	static final Dimension SIZE = new Dimension(250, 200);

	// The model instance that this view/controller will use to fetch data
	WeatherReportModel model;

	// The cities this view can read from
	List<String> cities;

	// The frame that encompasses all the other components
	JFrame contentFrame;

	// The panel that displays the weather report
	WeatherPanel weatherPanel;

	// The drop-down menu that selects a city
	JComboBox citySelectionBox;

	// The panel that displays the status of the application. This is currently
	// unused
	JLabel statusBarLabel;

	/**
	 * Initializes a new WeatherReportGUI for the given model instance, and
	 * displays it on the screen.
	 */
	public WeatherReportGUI(WeatherReportModel model, List<String> cities) {
		// Store the model instance
		this.model = model;

		// Store the cities
		this.cities = cities;

		// Initialize the frame, which will hold the other components.
		contentFrame = new JFrame(WINDOW_TITLE);
		contentFrame.setSize(SIZE);
		contentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set the layout manager of the frame
		contentFrame.setLayout(new BoxLayout(contentFrame.getContentPane(),
				BoxLayout.Y_AXIS));

		// Initialize the weather panel
		weatherPanel = new WeatherPanel();
		contentFrame.add(weatherPanel);

		// Initialize the city selection panel
		JPanel citySelectionPanel = new JPanel();
		citySelectionPanel.add(new JLabel("City: "));
		citySelectionBox = new JComboBox(cities.toArray());
		citySelectionPanel.add(citySelectionBox);
		JButton button = new JButton("Update");
		button.addActionListener(new UpdateActionListener());
		citySelectionPanel.add(button);
		contentFrame.add(citySelectionPanel);

		// Initialize the status bar panel
		JPanel statusBarPanel = new JPanel();
		statusBarLabel = new JLabel("Status Bar");
		statusBarPanel.add(statusBarLabel);
		contentFrame.add(statusBarPanel);

		// Fire the button event handler, to initialize the state of the weather
		// panel
		button.doClick();

		// Display the frame
		contentFrame.pack();
		contentFrame.setVisible(true);
	}

	/**
	 * An action listener which updates the weather information that is
	 * displayed.
	 */
	class UpdateActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			String currentCity = citySelectionBox.getSelectedItem().toString();
			weatherPanel.displayCity(currentCity, model);
		}
	}
}
