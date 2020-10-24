package frame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lab.Navigation;

public class TestFile6TestCases {
	
	private Duration timeout = AllTests.timeout;

	@Test
	public void TestFile6_Aachen_Goeteborg_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
		assertEquals(-4,
				AllTests.testDistance("TestFile6", "Aachen",
						"Goeteborg"), "From Aachen to Goeteborg: ");
	});
	}

	@Test
	public void TestFile6_Goeteborg_Bamberg_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
		assertEquals(144,
				AllTests.testDistance("TestFile6",
						"Goeteborg", "Bamberg"), "From Goeteborg to Bamberg: ");
	});
	}

	@Test
	public void TestFile6_Aachen_Goeteborg_Time() {
		assertTimeoutPreemptively(timeout, () -> {
		assertEquals(-4,
				AllTests.testTime("TestFile6", "Aachen",
						"Goeteborg"), "From Aachen to Goeteborg: ");
	});
	}

	@Test
	public void TestFile6_Goeteborg_Bamberg_Time() {
		assertTimeoutPreemptively(timeout, () -> {
		assertEquals(151,
				AllTests.testTime("TestFile6", "Goeteborg",
						"Bamberg"), "From Goeteborg to Bamberg: ");
	});
	}

	@Test
	public void TestFile6_Route_Goeteborg_Bamberg_Distance() {
		assertTimeoutPreemptively(timeout, () -> {
		ArrayList<String> route = new ArrayList<String>();
		Navigation lab = new Navigation("TestFile6");
		// Build the route we expect to find
		route.add("Goeteborg\\s*->\\s*Aachen");
		route.add("Aachen\\s*->\\s*Augsburg");
		route.add("Augsburg\\s*->\\s*Berlin");
		route.add("Berlin\\s*->\\s*Bamberg");
		assertTrue(AllTests.testRoute(true,
				lab.findShortestRoute("Goeteborg", "Bamberg"), route,
				"TestFile6", "Goeteborg", "Bamberg",
				AllTests.OutputFormat.RouteDistance), "Route not correct");
	});
	}

}
