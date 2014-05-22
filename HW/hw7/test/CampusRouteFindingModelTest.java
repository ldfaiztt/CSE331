package hw7.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import hw4.LabeledEdge;
import hw7.CampusRouteFindingModel;
import hw7.Coordinates;

import org.junit.Before;
import org.junit.Test;

/**
 * This class contains a set of test cases to test 
 * the implementation of the CampusRouteFindingModel class.
 * 
 * @author Chun-Wei Chen
 * @version 05/28/13
 */
public class CampusRouteFindingModelTest {
	private static final int TIMEOUT = 2000;
	
	@Test(timeout = TIMEOUT)
	public void testConstructModelSuccessWithNoException() throws Exception {
		new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", 
									"src/hw7/data/sports_paths.dat");
	}
	
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testConstructModelThrowsExceptionBadBuildingsData() throws Exception {
		new CampusRouteFindingModel("src/hw7/data/bad_buildings.dat", 
									"src/hw7/data/sports_paths.dat");
	}
	
	@Test(timeout = TIMEOUT, expected = Exception.class)
	public void testConstructModelThrowsExceptionBadPathsData() throws Exception {
		new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", 
									"src/hw7/data/bad_paths.dat");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructModelWithBothArgumentsNull() throws Exception {
		new CampusRouteFindingModel(null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructModelWithPathsNull() throws Exception {
		new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testConstructModelWithBuldingsNull() throws Exception {
		new CampusRouteFindingModel(null, "src/hw7/data/sports_paths.dat");
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetBuildingsEmptyBuildings() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		assertEquals(new HashMap<String, String>(), model.getBuildings());
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetFullNameOfBuildingWithNullArgument() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		model.getFullNameOfBuilding(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetAbbreviatedNameOfBuildingWithNullArgument() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		model.getAbbreviatedNameOfBuilding(null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetFullNameOfBuildingWithNonExistingBuilding() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		model.getFullNameOfBuilding("NBA");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetAbbreviatedNameOfBuildingWithNonExistingBuilding() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		model.getAbbreviatedNameOfBuilding("Basketball");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetLocationOfBuildingWithNullArgument() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		model.getLocationOfBuilding(null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingWithNonExistingBuilding() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		assertTrue(model.getLocationOfBuilding("NBA") == null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithBothArgumentNull() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		model.findShortestWalkingRoute(null, null);
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithStartingLocationNull() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		model.findShortestWalkingRoute(null, new Coordinates(0, 0));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithEndingLocationNull() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/empty_buildings.dat", "src/hw7/data/empty_paths.dat");
		
		model.findShortestWalkingRoute(new Coordinates(0, 0), null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetBuildingsTwoBuildings() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		Map<String, String> bn = new HashMap<String, String>();
		bn.put("AA", "Apartment A");
		bn.put("AB", "Apartment B");
		assertEquals(bn, model.getBuildings());
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetFullNameOfBuildingTwoBuildingsWithNonExistingBuilding() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		model.getFullNameOfBuilding("NBA");
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testGetAbbreviatedNameOfBuildingTwoBuildingsWithNonExistingBuilding() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		model.getAbbreviatedNameOfBuilding("Basketball");
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetFullNameOfBuildingTwoBuildingsWithAA() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		assertEquals("Apartment A", model.getFullNameOfBuilding("AA"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetAbbreviatedNameOfBuildingTwoBuildingsWithApartmentA() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		assertEquals("AA", model.getAbbreviatedNameOfBuilding("Apartment A"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetFullNameOfBuildingTwoBuildingsWithAB() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		assertEquals("Apartment B", model.getFullNameOfBuilding("AB"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetAbbreviatedNameOfBuildingTwoBuildingsWithApartmentB() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		assertEquals("AB", model.getAbbreviatedNameOfBuilding("Apartment B"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingTwoBuildingsWithNonExistingBuilding() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		assertTrue(model.getLocationOfBuilding("NBA") == null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingTwoBuildingsWithAA() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		assertEquals(new Coordinates(0, 0), model.getLocationOfBuilding("AA"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingTwoBuildingsWithAB() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		assertEquals(new Coordinates(3, 4), model.getLocationOfBuilding("AB"));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithBothNonExistingLocations() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		model.findShortestWalkingRoute(new Coordinates(3, 0), new Coordinates(0, 4));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithNonExistingStartingLocation() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		model.findShortestWalkingRoute(new Coordinates(3, 0), new Coordinates(3, 4));
	}
	
	@Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
	public void testFindShortestWalkingRouteWithNonExistingEndingLocation() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		model.findShortestWalkingRoute(new Coordinates(0, 0), new Coordinates(0, 4));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteWithBothExistingLocations() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		Coordinates aa = new Coordinates(0, 0);
		Coordinates ab = new Coordinates(3, 4);
		Map<Coordinates, Double> route = new LinkedHashMap<Coordinates, Double>();
		route.put(aa, 0.0);
		route.put(ab, 5.0);
		assertEquals(route, model.findShortestWalkingRoute(aa, ab));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteWithBothExistingLocationsReverseOrder() throws Exception {
		CampusRouteFindingModel model = 
				new CampusRouteFindingModel("src/hw7/data/two_buildings.dat", "src/hw7/data/two_paths.dat");
		
		Coordinates aa = new Coordinates(0, 0);
		Coordinates ab = new Coordinates(3, 4);
		Map<Coordinates, Double> route = new LinkedHashMap<Coordinates, Double>();
		route.put(ab, 0.0);
		route.put(aa, 5.0);
		assertEquals(route, model.findShortestWalkingRoute(ab, aa));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetSportsBuildings() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		Map<String, String> bn = new HashMap<String, String>();
		bn.put("NBA", "Basketball");
		bn.put("NFL", "Football");
		bn.put("NHL", "Hockey");
		bn.put("MLB", "Baseball");
		bn.put("PGA", "Golf");
		bn.put("ATP", "Tennis");
		assertEquals(bn, model.getBuildings());
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetFullNameOfBuildingSportsBuildingsWithNBA() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		assertEquals("Basketball", model.getFullNameOfBuilding("NBA"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetAbbreviatedNameOfBuildingSportsBuildingsWithNBA() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		assertEquals("NBA", model.getAbbreviatedNameOfBuilding("Basketball"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingSportsBuildingsWithNBA() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		assertEquals(new Coordinates(3, 0), model.getLocationOfBuilding("NBA"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingSportsBuildingsWithNFL() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		assertEquals(new Coordinates(0, 4), model.getLocationOfBuilding("NFL"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingSportsBuildingsWithNHL() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		assertEquals(new Coordinates(4, 7), model.getLocationOfBuilding("NHL"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingSportsBuildingsWithMLB() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		assertEquals(new Coordinates(7, 3), model.getLocationOfBuilding("MLB"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingSportsBuildingsWithPGA() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		assertEquals(new Coordinates(10, 10), model.getLocationOfBuilding("PGA"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testGetLocationOfBuildingSportsBuildingsWithATP() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		assertEquals(new Coordinates(15, 15), model.getLocationOfBuilding("ATP"));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromNBAToPGA() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		Coordinates nba = model.getLocationOfBuilding("NBA");
		Coordinates pga = model.getLocationOfBuilding("PGA");
		assertTrue(model.findShortestWalkingRoute(nba, pga) == null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromPGAToNBA() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		Coordinates pga = model.getLocationOfBuilding("PGA");
		Coordinates nba = model.getLocationOfBuilding("NBA");
		assertTrue(model.findShortestWalkingRoute(pga, nba) == null);
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromNBAToNHL() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		Coordinates nba = model.getLocationOfBuilding("NBA");
		Coordinates nhl = model.getLocationOfBuilding("NHL");
		Coordinates nfl = model.getLocationOfBuilding("NFL");
		Map<Coordinates, Double> route = new LinkedHashMap<Coordinates, Double>();
		route.put(nba, 0.0);
		route.put(nfl, 1.0);
		route.put(nhl, 3.0);
		assertEquals(route, model.findShortestWalkingRoute(nba, nhl));
	}
	
	@Test(timeout = TIMEOUT)
	public void testFindShortestWalkingRouteFromMLBToNFL() throws Exception {
		CampusRouteFindingModel model =
				new CampusRouteFindingModel("src/hw7/data/sports_buildings.dat", "src/hw7/data/sports_paths.dat");
		
		Coordinates mlb = model.getLocationOfBuilding("MLB");
		Coordinates nfl = model.getLocationOfBuilding("NFL");
		Map<Coordinates, Double> route = new LinkedHashMap<Coordinates, Double>();
		route.put(mlb, 0.0);
		route.put(nfl, 3.0);
		assertEquals(route, model.findShortestWalkingRoute(mlb, nfl));
	}
}