package frame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lab.Navigation;

public class TestFile4TestCases {

	private Duration timeout = AllTests.timeout;

	@Test
	public void testFile4_A_I_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(9, AllTests.testDistance("TestFile4", "A", "I"), "From A to I: ");
		});
	}

	@Test
	public void testFile4_B_H_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(12, AllTests.testDistance("TestFile4", "B", "H"), "From B to H: ");
		});
	}

	@Test
	public void testFile4_C_G_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(12, AllTests.testDistance("TestFile4", "C", "G"), "From C to G: ");
		});
	}

	@Test
	public void testFile4_A_I_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(9, AllTests.testTime("TestFile4", "A", "I"), "From A to I: ");
		});
	}

	@Test
	public void testFile4_B_F_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(13, AllTests.testTime("TestFile4", "B", "F"), "From B to H: ");
		});
	}

	@Test
	public void testFile4_B_H_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(20, AllTests.testTime("TestFile4", "B", "H"), "From B to H: ");
		});
	}

	@Test
	public void testFile4_C_G_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(15, AllTests.testTime("TestFile4", "C", "G"), "From C to G: ");
		});
	}

	@Test
	public void testfile4_Route_A_E_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile4");
			// Build the route we expect to find
			route.add("A\\s*->\\s*B");
			route.add("B\\s*->\\s*C");
			route.add("C\\s*->\\s*D");
			route.add("D\\s*->\\s*E");
			assertTrue(AllTests.testRoute(true, lab.findShortestRoute("A", "E"), route, "testfile1.txt", "A", "E",
					AllTests.OutputFormat.RouteDistance), "Route not correct");
		});
	}

	@Test
	public void testfile4_Route_B_F_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile4");
			// Build the route we expect to find
			route.add("B\\s*->\\s*C");
			route.add("C\\s*->\\s*D");
			route.add("D\\s*->\\s*F");
			assertTrue(AllTests.testRoute(true, lab.findFastestRoute("B", "F"), route, "TestFile4", "B", "F",
					AllTests.OutputFormat.RouteTime), "Route not correct");
		});
	}

}
