package hw0;
/** 
 * Import the class Random.
 */
import java.util.Random;

/**
 * RandomHello selects a random greeting to display to the user.
 * 
 * @author Chun-Wei Chen
 * @version 04/01/13
 */
public class RandomHello {

	/**
     * Uses a RandomHello object to print
     * a random greeting to the console.
     */
	public static void main(String[] args) {
		RandomHello randomHello = new RandomHello();
        System.out.println(randomHello.getGreeting());
	}

	/**
     * @return a random greeting from a list of five different greetings.
     */
    public String getGreeting() {
    	Random randomGenerator = new Random();
    	String[] greetings = new String[5];
    	greetings[0] = "Hello World";
    	greetings[1] = "Hola Mundo";
    	greetings[2] = "Ciao Mondo";
    	greetings[3] = "Bonjour Monde";
    	greetings[4] = "Hallo Welt";
    	return greetings[randomGenerator.nextInt(5)];
    }
}
