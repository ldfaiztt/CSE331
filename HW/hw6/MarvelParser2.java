package hw6;
import java.io.*;
import java.util.*;

/**
 * Parser utility to load the Marvel Comics dataset.
 * 
 * @author Chun-Wei Chen
 * @version 05/22/13
 */
public class MarvelParser2 {
	
	/**
	 * 
	 * Reads the Marvel Universe dataset.
     * Each line of the input file contains a character name and a comic
     * book the character appeared in, separated by a tab character
     * 
     * @requires file is well-formed, with each line containing exactly two
     *           quote-delimited tokens separated by a tab, or else starting with
     *           a # symbol to indicate a comment line.
     * @param filename the file that will be read
     * @param common map characters with other characters who appear in other books 
     *        together with the number of book in they appear together
     * @modifies common
     * @effects fills common with characters and map with other characters who appear 
     *          in other books together with the number of book in they appear together
	 * 
	 */
	public static void parseData(String filename, 
			Map<String, HashMap<String, Integer>> common) throws Exception {
		
		BufferedReader reader = null;
	    try {
			reader = new BufferedReader(new FileReader(filename));
			
			// a map to hold the book and its characters
			HashMap<String, ArrayList<String>> books = 
					new HashMap<String, ArrayList<String>>();
			
			String inputLine;
			while ((inputLine = reader.readLine()) != null) {
				// Ignore comment lines.
		        if (inputLine.startsWith("#")) {
		        	continue;
		        }
		        
		        // Parse the data, stripping out quotation marks and throwing
		        // an exception for malformed lines.
		        inputLine = inputLine.replace("\"", "");
		        String[] tokens = inputLine.split("\t");
		        if (tokens.length != 2) {
		            throw new Exception("Line should contain exactly one tab: " + inputLine);
		        }

		        String character = tokens[0];
		        String book = tokens[1];
		        
		        // add characters to common is the character is not in map
		        if (!common.containsKey(character))
		        	common.put(character, new HashMap<String, Integer>());
		        
		        // add book with the character on the same line to books 
		        // if the book is not in books
		        if (!books.containsKey(book)) {
		        	ArrayList<String> ch = new ArrayList<String>();
		        	ch.add(character);
		        	books.put(book, ch);
		        } else {
		        	// if the book is already in books, update the 
		        	// edge count of this character and the character
		        	// in this book
		        	ArrayList<String> chars = books.get(book);
		        	HashMap<String, Integer> count1 = common.get(character);
		        	for (String c : chars) {
		        		if (!count1.containsKey(c))
		        			count1.put(c, 1);
		        		else
		        			count1.put(c, count1.get(c) + 1);
		        		
		        		// if c is in the book, then it must be in common 
		        		// since the if (!common.containsKey(character))
		        		// before if (!books.containsKey(book))
		        		// ensures that the character parsed through is 
		        		// in common
		        		@SuppressWarnings("keyfor")
		        		/*@KeyFor("common")*/ String c2 = c;
		        		HashMap<String, Integer> count2 = common.get(c2);
		        		if (!count2.containsKey(character))
		        			count2.put(character, 1);
		        		else
		        			count2.put(character, count2.get(character) + 1);
		        	}
		        	// add the character to the book
		        	chars.add(character);
		        	books.put(book, chars);
		        }
			}
	    } catch (IOException e) {
	        System.err.println(e.toString());
	        e.printStackTrace(System.err);
	    } finally {
	        if (reader != null) {
	        	reader.close();
	        }
	    }
	}
}