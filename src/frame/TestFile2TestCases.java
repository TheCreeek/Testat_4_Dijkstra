package frame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lab.Navigation;

public class TestFile2TestCases {

	private Duration timeout = AllTests.timeout;

	@Test
	public void TestFile2_A_C_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(8, AllTests.testDistance("TestFile2", "A", "C"), "From A to C: ");
		});
	}

	@Test
	public void TestFile2_A_D_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(12, AllTests.testDistance("TestFile2", "A", "D"), "From A to D: ");
		});
	}

	@Test
	public void TestFile2_A_F_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(16, AllTests.testDistance("TestFile2", "A", "F"), "From A to F: ");
		});
	}

	@Test
	public void TestFile2_B_F_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(15, AllTests.testDistance("TestFile2", "B", "F"), "From A to F: ");
		});
	}

	@Test
	public void TestFile2_A_C_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(8, AllTests.testTime("TestFile2", "A", "C"), "From A to C: ");
		});
	}

	@Test
	public void TestFile2_A_E_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(15, AllTests.testTime("TestFile2", "A", "E"), "From A to E: ");
		});
	}

	@Test
	public void TestFile2_A_F_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(12, AllTests.testTime("TestFile2", "A", "F"), "From A to F: ");
		});
	}

	@Test
	public void TestFile2_B_F_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(16, AllTests.testTime("TestFile2", "B", "F"), "From A to F: ");
		});
	}

	@Test
	public void TestFile2_Size() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile2");
			assertEquals(16, lab.findShortestRoute("A", "B").size(), "Number of entries in output map: ");
		});
	}

	@Test
	public void TestFile2_NoPointA() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile2");
			assertEquals(-1, lab.findShortestDistance("G", "A"), "Test non-existing node 1: ");
		});
	}

	@Test
	public void TestFile2_NoPointB() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile2");
			assertEquals(-2, lab.findShortestDistance("A", "G"), "Test non-existing node 2: ");
		});
	}

	@Test
	public void TestFile2_NoPointsAtAll() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile2");
			assertEquals(-3, lab.findShortestDistance("L", "G"), "Test non-existing nodes: ");
		});
	}

	@Test
	public void TestFile2_NoPath() {
		assertTimeoutPreemptively(timeout, () -> {
			Navigation lab = new Navigation("TestFile2");
			assertEquals(-4, lab.findShortestDistance("B", "A"), "Test non-existing path B->A: ");
		});
	}

	@Test
	public void TestFile2_Route_A_E_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile2");
			// Build the route we expect to find
			route.add("A\\s*->\\s*B");
			route.add("B\\s*->\\s*C");
			route.add("C\\s*->\\s*D");
			route.add("D\\s*->\\s*E");
			assertTrue(AllTests.testRoute(true, lab.findShortestRoute("A", "E"), route, "TestFile2", "A", "E",
					AllTests.OutputFormat.RouteDistance), "Route not correct");
		});
	}

	@Test
	public void TestFile2_Route_A_E_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			ArrayList<String> foundRoute = new ArrayList<String>();
			String source = "A";
			String destination = "E";
			String fileName = "TestFile2";

			Navigation lab = new Navigation(fileName);
			// Build the route we expect to find
			route.add("A\\s*->\\s*B");
			route.add("B\\s*->\\s*C");
			route.add("C\\s*->\\s*E");

			foundRoute = lab.findFastestRoute(source, destination);

			assertTrue(AllTests.testRoute(true, foundRoute, route, fileName, source, destination,
					AllTests.OutputFormat.RouteTime), "Route not correct");
		});
	}

}
