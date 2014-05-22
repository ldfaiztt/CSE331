/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw0;

/**
 * HolaWorld is like HelloWorld except it can say hello in Spanish!
 * 
 * @author Chun-Wei Chen
 * @version 04/01/13
 */
public class HolaWorld extends HelloWorld {

    /** Greeting in Spanish */
    public static final String SPANISH_GREETING = "Hola Mundo!";

    /**
     * Shows what happens when the getGreeting() method
     * of both HelloWorld and HolaWorld are invoked
     */
    public static void main(String[] argv) {

        // Create the Hello World objects.
        HelloWorld myFirstHW = new HelloWorld();
        HolaWorld world = new HolaWorld();

        // Print out greetings
        System.out.println(myFirstHW.getGreeting());
        System.out.println(world.getGreeting());
    }

    /**
     @return Returns a greeting (in Spanish).
     */
    public String getGreeting() {
        return SPANISH_GREETING;
    }

}
