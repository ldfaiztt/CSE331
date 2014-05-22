package hw5;
import java.io.*;
import java.util.*;

/**
 * Parser utility to load the Marvel Comics dataset.
 */
public class MarvelParser {

  /**
   * Reads the Marvel Universe dataset.
   * Each line of the input file contains a character name and a comic
   * book the character appeared in, separated by a tab character
   * 
   * @requires file is well-formed, with each line containing exactly two
   *          quote-delimited tokens separated by a tab, or else starting with
   *          a # symbol to indicate a comment line.
   * @param filename the file that will be read
   * @param characters list in which all character names will be stored;
   *          typically empty when the routine is called
   * @param books map from titles of comic books to characters that
   *          appear in them; typically empty when the routine is called
   * @modifies characters, books
   * @effects fills characters with a list of all unique character names
   * @effects fills books with a map from each comic book to all characters
   *          appearing in it
   */
  public static void parseData(String filename, Set <String> characters,
          Map<String, List<String>> books) throws Exception {
    // Why does this method accept the Collections to be filled as
    // parameters rather than making them a return value? To allows us to
    // "return" two different Collections. If only one or neither Collection
    // needs to be returned to the caller, feel free to rewrite this method
    // without the parameters. Generally this is better style.
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(filename));

      // Construct the collections of characters and books, one
      // <character, book> pair at a time.
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

        // Add the parsed data to the character and book collections.
        characters.add(character);
        if (!books.containsKey(book)) {
          books.put(book, new ArrayList<String>());
        }
        books.get(book).add(character);
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
