package hw7.test;

import hw7.CampusRouteFindingTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * This class can be used to test your Campus Paths main application. It
 * redirects System.out and System.in to input and output files, then invokes
 * the application's main method. This allows the output of a set of commands
 * to be compared against the expected output without user intervention.  
 */
public class HW7TestDriver {
    
    // Files for reading input and writing output in place of the console.
    private File in, out;

    /**
     * @requires in != null && out != null
     *
     * @effects Creates a new HW7TestDriver and runs the Campus Paths main
     * program with System.in and System.out redirected to the provided files.
     **/
    public HW7TestDriver(File in, File out) {
        this.in = in;
        this.out = out;
    }
    
    public void runTests() throws FileNotFoundException {
        // Store original values of I/O streams.
        InputStream stdin = System.in;
        PrintStream stdout = System.out;
        PrintStream stderr = System.err;
        
        // Redirect I/O to files.
        System.setIn(new FileInputStream(in));
        System.setOut(new PrintStream(out));
        
        // TODO: add a line here to call your main method. For example, if your
        // main class is called CampusPaths, write:
        // CampusPaths.main(new String[0]);
        CampusRouteFindingTool.main(new String[0]);

        // Restore standard I/O streams.
        System.setIn(stdin);
        System.setOut(stdout);
        System.setErr(stderr);
    }
}
