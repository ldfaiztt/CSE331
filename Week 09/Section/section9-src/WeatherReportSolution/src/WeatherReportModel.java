import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * A model which fetches weather information for various cities. This is a stub
 * implementation which returns random data - in a real application, this class
 * would handle reading weather data from some online service.
 */
public class WeatherReportModel {

	/**
	 * An enumeration of all possible weather states.
	 * 
	 */
	public enum WeatherState {
		SUNNY, RAINY, CLOUDY;

		/**
		 * Returns a string representation of this weather state.
		 */
		public String toString() {
			switch (this) {
			case SUNNY:
				return "Sunny";
			case RAINY:
				return "Rainy";
			case CLOUDY:
				return "Cloudy";
			}
			return "";
		}
	}

	private Random rand;

	/**
	 * Constructs a new weather report model
	 */
	public WeatherReportModel() {
		rand = new Random();
	}

	/**
	 * Fetches the temperature of a particular city
	 * 
	 * @param city
	 *            the name of the city to fetch.
	 * @return the current temperature in city, in degrees fahrenheit.
	 */
	public int getTemperature(String city) {
		// Randomly choose a temperature between 30 and 100
		return rand.nextInt(70) + 30;
	}

	/**
	 * Fetches the weather state of a particular city.
	 * 
	 * @param city
	 *            the name of the city to fetch
	 * @return the current weather state in city
	 */
	public WeatherState getState(String city) {
		// Randomly choose a state
		WeatherState[] states = { WeatherState.SUNNY, WeatherState.RAINY,
				WeatherState.CLOUDY };
		return states[rand.nextInt(states.length)];

	}

	/**
	 * Fetches the current time of a particular city
	 * 
	 * @param city
	 *            the name of the city to fetch
	 * @return the current time in city, as a string.
	 */
	public String getTime(String city) {
		// Return the current time
		Date now = new Date(System.currentTimeMillis());
		return new SimpleDateFormat("h:mm a").format(now);
	}
}
