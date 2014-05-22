package hw7.test;

import static org.junit.Assert.*;

import hw4.DGraph;
import hw7.CampusDataParser;
import hw7.Coordinates;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the CampusDataParser class.
 * 
 * @author Chun-Wei Chen
 * @version 05/28/13
 */
public class CampusDataParserTest {
	private static final int TIMEOUT = 2000;
	private Map<String, String> bNames;
	private Map<String, String> bNames2;
	private Map<String, Coordinates> bLocations;
	private DGraph<Coordinates, Double> campusPaths;
	
	@Before
	public void setUp() throws Exception {
		bNames = new HashMap<String, String>();
		bNames2 = new HashMap<String, String>();
		bLocations = new HashMap<String, Coordinates>();
		campusPaths = new DGraph<Coordinates, Double>();
	}

	@Test(timeout = TIMEOUT)
	public void testParseEmptyBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw7/data/empty_buildings.dat", bNames, bNames2, bLocations);
		assertEquals(new HashMap<String, String>(), bNames);
		assertEquals(new HashMap<String, Coordinates>(), bLocations);
	}
	
	@Test(timeout = TIMEOUT)
	public void testBuildEmptyCampusPaths() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw7/data/empty_paths.dat", campusPaths);
		assertEquals((new DGraph<Coordinates, Double>()).entrySet(), campusPaths.entrySet());
	}
	
	@Test(timeout = TIMEOUT)
	public void testParseBuildingDataTwoBuildings() throws Exception {
		CampusDataParser.parseBuildingData("src/hw7/data/two_buildings.dat", bNames, bNames2, bLocations);
		Map<String, String> bn = new HashMap<String, String>();
		Map<String, Coordinates> bl = new HashMap<String, Coordinates>();
		bn.put("AA", "Apartment A");
		bn.put("AB", "Apartment B");
		bl.put("AA", new Coordinates(0, 0));
		bl.put("AB", new Coordinates(3, 4));
		assertEquals(bn, bNames);
		assertEquals(bl, bLocations);
	}
	
	@Test(timeout = TIMEOUT)
	public void testBuildCampusPathsTwoPaths() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw7/data/two_paths.dat", campusPaths);
		DGraph<Coordinates, Double> cp = new DGraph<Coordinates, Double>();
		Coordinates aa = new Coordinates(0, 0);
		Coordinates ab = new Coordinates(3, 4);
		cp.addNode(aa);
		cp.addNode(ab);
		cp.addEdge(aa, ab, 5.0);
		cp.addEdge(ab, aa, 5.0);
		assertEquals(cp.entrySet(), campusPaths.entrySet());
	}
	
	@Test(timeout = TIMEOUT)
	public void testParseBuildingDataTwoBuildingsWithComments() throws Exception {
		CampusDataParser.parseBuildingData("src/hw7/data/two_with_comments_buildings.dat", bNames, bNames2, bLocations);
		Map<String, String> bn = new HashMap<String, String>();
		Map<String, Coordinates> bl = new HashMap<String, Coordinates>();
		bn.put("AA", "Apartment A");
		bn.put("AB", "Apartment B");
		bl.put("AA", new Coordinates(0, 0));
		bl.put("AB", new Coordinates(3, 4));
		assertEquals(bn, bNames);
		assertEquals(bl, bLocations);
	}
	
	@Test(timeout = TIMEOUT)
	public void testBuildCampusPathsTwoPathsWithComments() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw7/data/two_with_comments_paths.dat", campusPaths);
		DGraph<Coordinates, Double> cp = new DGraph<Coordinates, Double>();
		Coordinates aa = new Coordinates(0, 0);
		Coordinates ab = new Coordinates(3, 4);
		cp.addNode(aa);
		cp.addNode(ab);
		cp.addEdge(aa, ab, 5.0);
		cp.addEdge(ab, aa, 5.0);
		assertEquals(cp.entrySet(), campusPaths.entrySet());
	}
	
	// tokens not separated by tab character
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testParseBadFormatedBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw7/data/bad_buildings.dat", bNames, bNames2, bLocations);
	}
	
	// bad formated on indented line
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testBuildBadFormatedPaths() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw7/data/bad_paths.dat", campusPaths);
	}
	
	// indented line comes before non-indented line
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testBuildBadFormatedPaths2() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw7/data/bad_paths2.dat", campusPaths);
	}
	
	@Test(timeout = TIMEOUT)
	public void testParseSportsBuildingData() throws Exception {
		CampusDataParser.parseBuildingData("src/hw7/data/sports_buildings.dat", bNames, bNames2, bLocations);
		Map<String, String> bn = new HashMap<String, String>();
		Map<String, Coordinates> bl = new HashMap<String, Coordinates>();
		bn.put("NBA", "Basketball");
		bn.put("NFL", "Football");
		bn.put("NHL", "Hockey");
		bn.put("MLB", "Baseball");
		bn.put("PGA", "Golf");
		bn.put("ATP", "Tennis");
		bl.put("NBA", new Coordinates(3, 0));
		bl.put("NFL", new Coordinates(0, 4));
		bl.put("NHL", new Coordinates(4, 7));
		bl.put("MLB", new Coordinates(7, 3));
		bl.put("PGA", new Coordinates(10, 10));
		bl.put("ATP", new Coordinates(15, 15));
		assertEquals(bn, bNames);
		assertEquals(bl, bLocations);
	}
	
	@Test(timeout = TIMEOUT)
	public void testBuildSportsPaths() throws Exception {
		CampusDataParser.buildCampusPaths("src/hw7/data/sports_paths.dat", campusPaths);
		DGraph<Coordinates, Double> cp = new DGraph<Coordinates, Double>();
		Coordinates nba = new Coordinates(3, 0);
		Coordinates nfl = new Coordinates(0, 4);
		Coordinates nhl = new Coordinates(4, 7);
		Coordinates mlb = new Coordinates(7, 3);
		Coordinates pga = new Coordinates(10, 10);
		Coordinates atp = new Coordinates(15, 15);
		cp.addNode(nba);
		cp.addNode(nfl);
		cp.addNode(nhl);
		cp.addNode(mlb);
		cp.addNode(pga);
		cp.addNode(atp);
		cp.addEdge(nba, nfl, 1.0);
		cp.addEdge(nba, nhl, 4.0);
		cp.addEdge(nba, mlb, 3.0);
		cp.addEdge(nfl, nba, 1.0);
		cp.addEdge(nfl, nhl, 2.0);
		cp.addEdge(nfl, mlb, 3.0);
		cp.addEdge(nhl, nba, 4.0);
		cp.addEdge(nhl, nfl, 2.0);
		cp.addEdge(nhl, mlb, 3.0);
		cp.addEdge(mlb, nba, 3.0);
		cp.addEdge(mlb, nfl, 3.0);
		cp.addEdge(mlb, nhl, 3.0);
		cp.addEdge(pga, atp, 5.0);
		cp.addEdge(atp, pga, 5.0);
		assertEquals(cp.entrySet(), campusPaths.entrySet());
	}
}