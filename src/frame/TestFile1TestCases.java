package frame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lab.Navigation;

public class TestFile1TestCases {

	private Duration timeout = AllTests.timeout;

	@Test
	public void TestFile1_A_C_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(8, AllTests.testDistance("TestFile1", "A", "C"), "From A to C: ");
		});
	}

	@Test
	public void TestFile1_A_E_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(18, AllTests.testDistance("TestFile1", "A", "E"), "From A to G: ");
		});
	}

	@Test
	public void TestFile1_A_G_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(24, AllTests.testDistance("TestFile1", "A", "G"), "From A to G: ");
		});
	}

	@Test
	public void TestFile1_A_H_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(29, AllTests.testDistance("TestFile1", "A", "H"), "From A to G: ");
		});
	}

	@Test
	public void TestFile1_A_B_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(7, AllTests.testTime("TestFile1", "A", "B"), "From A to D: ");
		});
	}

	@Test
	public void TestFile1_A_D_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(14, AllTests.testTime("TestFile1", "A", "D"), "From A to D: ");
		});
	}

	@Test
	public void TestFile1_A_E_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(19, AllTests.testTime("TestFile1", "A", "E"), "From A to D: ");
		});
	}

	@Test
	public void TestFile1_A_F_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(18, AllTests.testTime("TestFile1", "A", "F"), "From A to F: ");
		});
	}

	@Test
	public void TestFile1_A_H_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(37, AllTests.testTime("TestFile1", "A", "H"), "From A to D: ");
		});
	}

	@Test
	public void TestFile1_Size() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile1");
			assertEquals(20, lab.findShortestRoute("A", "B").size(), "Number of entries in output map: ");
		});
	}

	@Test
	public void TestFile1_Reflexive() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile1");
			assertEquals(0, lab.findShortestDistance("A", "A"), "Test reflexitivity (A->A): ");
		});
	}

	@Test
	public void TestFile1_NoPointA() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile1");
			assertEquals(-1, lab.findShortestDistance("R", "A"), "Test non-existing node: ");
		});
	}

	@Test
	public void TestFile1_NoPointB() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile1");
			assertEquals(-2, lab.findShortestDistance("A", "R"), "Test non-existing nodes: ");
		});
	}

	@Test
	public void TestFile1_NoPointsAtAll() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile1");
			assertEquals(-3, lab.findShortestDistance("L", "R"), "Test non-existing path: ");
		});
	}

	@Test
	public void TestFile1_B_A_NoPath() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile1");
			assertEquals(-4, lab.findShortestDistance("B", "A"), "Test non-existing path B->A: ");
		});
	}

	@Test
	public void TestFile1_Route_B_A_NoPath() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile1");
			ArrayList<String> map = lab.findShortestRoute("B", "A");
			assertEquals(20, map.size(), "Expected more lines in the file B->A");
			assertTrue(AllTests.testNoRoute(map, "TestFile1", "B", "A", AllTests.OutputFormat.RouteDistance),
					"Test non-existing path B->A: ");
		});
	}

	@Test
	public void TestFile1_Route_A_B_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile1");
			// Build the route we expect to find
			route.add("A\\s*->\\s*C");
			route.add("C\\s*->\\s*B");
			assertTrue(AllTests.testRoute(true, lab.findShortestRoute("A", "B"), route, "TestFile1", "A", "G",
					AllTests.OutputFormat.RouteDistance), "Route not correct");
		});
	}

	@Test
	public void TestFile1_Route_A_E_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile1");
			// Build the route we expect to find
			route.add("A\\s*->\\s*C");
			route.add("C\\s*->\\s*D");
			route.add("D\\s*->\\s*E");
			assertTrue(AllTests.testRoute(true, lab.findShortestRoute("A", "E"), route, "TestFile1", "A", "E",
					AllTests.OutputFormat.RouteDistance), "Route not correct");
		});
	}

	@Test
	public void TestFile1_Route_A_G_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile1");
			// Build the route we expect to find
			route.add("A\\s*->\\s*C");
			route.add("C\\s*->\\s*D");
			route.add("D\\s*->\\s*F");
			route.add("F\\s*->\\s*G");
			assertTrue(AllTests.testRoute(true, lab.findShortestRoute("A", "G"), route, "TestFile1", "A", "G",
					AllTests.OutputFormat.RouteDistance), "Route not correct");
		});
	}

	@Test
	public void TestFile1_Route_A_F_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile1");
			// Build the route we expect to find
			route.add("A\\s*->\\s*C");
			route.add("C\\s*->\\s*D");
			route.add("D\\s*->\\s*F");
			assertTrue(AllTests.testRoute(true, lab.findFastestRoute("A", "F"), route, "TestFile1", "A", "F",
					AllTests.OutputFormat.RouteTime), "Route not correct");
		});
	}

	@Test
	public void TestFile1_Route_A_H_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile1");
			// Build the route we expect to find
			route.add("A\\s*->\\s*C");
			route.add("C\\s*->\\s*D");
			route.add("D\\s*->\\s*F");
			route.add("F\\s*->\\s*G");
			route.add("G\\s*->\\s*H");
			assertTrue(AllTests.testRoute(true, lab.findFastestRoute("A", "H"), route, "TestFile1", "A", "H",
					AllTests.OutputFormat.RouteTime), "Route not correct");
		});
	}

	@Test
	public void TestFile1_Route_A_F_NOPATH_E_F() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile1");
			// Build the route we expect to find
			route.add("E\\s*->\\s*F");
			assertFalse(AllTests.testRoute(false, lab.findFastestRoute("A", "F"), route, "TestFile1", "A", "F",
					AllTests.OutputFormat.RouteTime), "Route not correct");
		});
	}

	@Test
	public void TestFile1_Route_A_F_NOPATH_B_D() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile1");
			// Build the route we expect to find
			route.add("B\\s*->\\s*D");
			assertFalse(AllTests.testRoute(false, lab.findFastestRoute("A", "F"), route, "TestFile1", "A", "F",
					AllTests.OutputFormat.RouteTime), "Route not correct");
		});
	}

}
