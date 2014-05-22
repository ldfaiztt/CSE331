/**
 * This is part of HW0: Environment Setup and Java Introduction for CSE 331.
 */
package hw0.test;
import static org.junit.Assert.assertEquals;
import hw0.HelloWorld;
import hw0.HolaWorld;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import org.junit.Test;

/**
 * HolaWorldTest is a simple test of the HolaWorld class that you
 * are to fix.  This test just makes sure that the program
 * does not crash and that the correct greeting is printed.
 */
public class HolaWorldTest {

    /**
     * Tests that HolaWorld does not crash
     */
    @Test
    public void testCrash() {
        /* Any exception thrown will be caught by JUnit. */
        HolaWorld.main(new String[0]);
    }

    /**
     * Tests that the HolaWorld greeting is correct.
     */
    @Test
    public void testGreeting() {
        HolaWorld world = new HolaWorld();
        assertEquals(HolaWorld.SPANISH_GREETING, world.getGreeting());
    }

    /**
     * Tests that the output of HolaWorld.main() is correct.
     * @throws FileNotFoundException 
     */
    @Test
    public void testMainOutput() throws FileNotFoundException {
        
        // Needed for windows line endings
        System.setProperty("line.separator", "\n");
        
        // Redirect System.out to an OutputStream
        PrintStream sysoutStream = System.out;
        ByteArrayOutputStream mainOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(mainOutput));
        
        HolaWorld.main(new String[0]);
        System.setOut(sysoutStream);

        assertEquals(HelloWorld.GREETING + "\n" + HolaWorld.SPANISH_GREETING + "\n",
                    mainOutput.toString());
    }
}
