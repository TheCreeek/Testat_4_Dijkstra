package frame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lab.Navigation;

public class TestFile5TestCases {

	private Duration timeout = AllTests.timeout;

	@Test
	public void testFile5_A_F_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(-4, AllTests.testDistance("TestFile5", "A", "F"), "From A to F: ");
		});
	}

	@Test
	public void testfile5_F_C_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(144, AllTests.testDistance("TestFile5", "F", "C"), "From F to C: ");
		});
	}

	@Test
	public void testFile5_A_F_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(-4, AllTests.testTime("TestFile5", "A", "F"), "From A to F: ");
		});
	}

	@Test
	public void testfile5_F_C_Time() {
		assertTimeoutPreemptively(timeout, () -> {
			assertEquals(151, AllTests.testTime("TestFile5", "F", "C"), "From F to C: ");
		});
	}

	@Test
	public void testfile5_Route_F_C_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
			ArrayList<String> route = new ArrayList<String>();
			Navigation lab = new Navigation("TestFile5");
			// Build the route we expect to find
			route.add("F\\s*->\\s*A");
			route.add("A\\s*->\\s*B");
			route.add("B\\s*->\\s*D");
			route.add("D\\s*->\\s*C");
			assertTrue(AllTests.testRoute(true, lab.findShortestRoute("F", "C"), route, "TestFile5", "F", "C",
					AllTests.OutputFormat.RouteDistance), "Route not correct");
		});
	}

}
