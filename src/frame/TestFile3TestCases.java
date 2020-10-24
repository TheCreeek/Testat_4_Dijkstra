package frame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lab.Navigation;

public class TestFile3TestCases {

	private Duration timeout = AllTests.timeout;

	@Test
	public void TestFile3_Duisburg_Augsburg_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(176, AllTests.testDistance("TestFile3", "Duisburg", "Augsburg"),
					"From Duisburg to Augsburg: ");
		});
	}

	@Test
	public void TestFile3_Hanover_Giessen_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(153, AllTests.testDistance("TestFile3", "Hanover", "Giessen"), "From Hanover to Giessen: ");
		});
	}

	@Test
	public void TestFile3_Regensburg_Braunschweig_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(245, AllTests.testDistance("TestFile3", "Regensburg", "Braunschweig"),
					"From Regensburg to Braunschweig: ");
		});
	}

	@Test
	public void TestFile3_Duisburg_Augsburg_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(125, AllTests.testTime("TestFile3", "Duisburg", "Augsburg"), "From Duisburg to Augsburg: ");
		});
	}

	@Test
	public void TestFile3_Hanover_Giessen_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(143, AllTests.testTime("TestFile3", "Hanover", "Giessen"), "From Hanover to Giessen: ");
		});
	}

	@Test
	public void TestFile3_Regensburg_Braunschweig_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(226, AllTests.testTime("TestFile3", "Regensburg", "Braunschweig"),
					"From Regensburg to Braunschweig: ");
		});
	}

	@Test
	public void TestFile3_Route_Duisburg_Augsburg_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile3");
			// Build the route we expect to find
			route.add("Duisburg\\s*->\\s*Heidelberg");
			route.add("Heidelberg\\s*->\\s*Bremen");
			route.add("Bremen\\s*->\\s*Chemnitz");
			route.add("Chemnitz\\s*->\\s*Augsburg");
			ArrayList<String> map = lab.findShortestRoute("Duisburg", "Augsburg");
			assertTrue(AllTests.testRoute(true, map, route, "TestFile3", "Duisburg", "Augsburg",
					AllTests.OutputFormat.RouteDistance), "Route not correct");
		});
	}

	@Test
	public void TestFile3_Route_Duisburg_Augsburg_Time_Size() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> map;
			Navigation lab = new Navigation("TestFile3");
			map = lab.findFastestRoute("Duisburg", "Augsburg");
			assertEquals(124, map.size(), "Expected more lines in output file:");
		});
	}

	@Test
	public void TestFile3_Route_Duisburg_Augsburg_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			ArrayList<String> map;
			Navigation lab = new Navigation("TestFile3");
			map = lab.findFastestRoute("Duisburg", "Augsburg");
			// Build the route we expect to find
			route.add("Duisburg\\s*->\\s*Heidelberg");
			route.add("Heidelberg\\s*->\\s*Bremen");
			route.add("Bremen\\s*->\\s*Chemnitz");
			route.add("Chemnitz\\s*->\\s*Augsburg");
			assertTrue(AllTests.testRoute(true, map, route, "TestFile3", "Duisburg", "Augsburg",
					AllTests.OutputFormat.RouteTime), "Route not correct");
		});
	}

	@Test
	public void TestFile3_Route_Regensburg_Braunschweig_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile3");
			// Build the route we expect to find
			route.add("Regensburg\\s*->\\s*Osnabrueck");
			route.add("Osnabrueck\\s*->\\s*Brunswick");
			route.add("Brunswick\\s*->\\s*Bochum");
			route.add("Bochum\\s*->\\s*Aachen");
			route.add("Aachen\\s*->\\s*Giessen");
			route.add("Giessen\\s*->\\s*Braunschweig");
			assertTrue(AllTests.testRoute(true, lab.findShortestRoute("Regensburg", "Braunschweig"), route, "TestFile3",
					"Regensburg", "Braunschweig", AllTests.OutputFormat.RouteDistance), "Route not correct");
		});
	}

	@Test
	public void TestFile3_Route_Regensburg_Braunschweig_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route1 = new ArrayList<String>();
			ArrayList<String> route2 = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile3");
			// Build the route we expect to find
			route1.add("Regensburg\\s*->\\s*Osnabrueck");
			route1.add("Osnabrueck\\s*->\\s*Brunswick");
			route1.add("Brunswick\\s*->\\s*Bochum");
			route1.add("Bochum\\s*->\\s*Aachen");
			route1.add("Aachen\\s*->\\s*Giessen");
			route1.add("Giessen\\s*->\\s*Braunschweig");

			// alternatively instead

			route2.add("Regensburg\\s*->\\s*Luebeck");
			route2.add("Luebeck\\s*->\\s*Brunswick");
			route2.add("Brunswick\\s*->\\s*Bochum");
			route2.add("Bochum\\s*->\\s*Aachen");
			route2.add("Aachen\\s*->\\s*Giessen");
			route2.add("Giessen\\s*->\\s*Braunschweig");

			ArrayList<String> res = lab.findFastestRoute("Regensburg", "Braunschweig");
			boolean path1 = AllTests.testRoute(true, res, route1, "TestFile3-path1", "Regensburg", "Braunschweig",
					AllTests.OutputFormat.RouteTime);

			ArrayList<String> res2 = lab.findFastestRoute("Regensburg", "Braunschweig");
			boolean path2 = AllTests.testRoute(true, res2, route2, "TestFile3-path2", "Regensburg", "Braunschweig",
					AllTests.OutputFormat.RouteTime);

			assertTrue(path1 || path2, "Route not correct");
		});
	}

	@Test
	public void TestFile3_Route_FrankfurtAmMain_Brunswick_NOPATH_Goeteborg_Aachen() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile3");
			// Build the route we expect to find
			route.add("Goeteborg\\s*->\\s*Aachen");
			assertFalse(
					AllTests.testRoute(false, lab.findFastestRoute("FrankfurtamMain", "Brunswick"), route, "TestFile3",
							"Goeteborg", "Aachen", AllTests.OutputFormat.RouteTime),
					"Route not correct (wrong bold edge present)");
		});
	}

}
