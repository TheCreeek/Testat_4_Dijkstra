package frame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;

import lab.Navigation;

/**
 * TestCases for the second lab. This class contains the static methods used by
 * the test cases.
 * 
 * @author Stefan Kropp, Andreas Johansson
 */
// public class NavigationLabTestCase extends TestCase {
public class AllTests {
	
	public static final Duration timeout = Duration.ofMillis(750);

	public static enum OutputFormat {
		Distance, Time, RouteDistance, RouteTime
	};

	/**
	 * This method returns the shortest distance from start to stop on the map
	 * stored in filename.
	 * 
	 * It also writes the output map to a file. The file name follows the following
	 * format:
	 * 
	 * output_[filename]_from[start]to[stop]Distance.txt
	 * 
	 * @param filename
	 *            The name of the file storing the map
	 * @param start
	 *            Source node
	 * @param stop
	 *            Destination node
	 * @return The shortest distance between start and stop in km
	 */
	public static final int testDistance(String filename, String start, String stop) {
		Navigation lab = new Navigation(filename);
		// ArrayList < String > returnMap = new ArrayList < String >();

		// returnMap = lab.findShortestRoute(start,stop);
		// writeGraphToFile(returnMap, filename, start, stop, OutputFormat.Distance);
		return lab.findShortestDistance(start, stop);
	}

	/**
	 * This method returns the fastest route from start to stop on the map stored in
	 * filename.
	 * 
	 * It also writes the output map to a file. The file name follows the following
	 * format:
	 * 
	 * output_[filename]_from[start]to[stop]Time.txt
	 * 
	 * @param filename
	 *            The name of the file storing the map
	 * @param start
	 *            Source node
	 * @param stop
	 *            Destination node
	 * @return Fastest route in minutes
	 */
	public static final int testTime(String filename, String start, String stop) {
		Navigation lab = new Navigation(filename);
		// ArrayList < String > returnMap = new ArrayList < String >();

		// returnMap = lab.findFastestRoute(start,stop);
		// writeGraphToFile(returnMap, filename, start, stop, OutputFormat.Time);
		return lab.findFastestTime(start, stop);
	}

	/**
	 * This method tests wether the edges contained in boldEdges are present and
	 * marked as bold in map. It prints any edges not found bold to standard error.
	 * It also prints the graph to a file with a fitting name.
	 * 
	 * @param map
	 *            The map to check, in dot format
	 * @param boldEdges
	 *            The edges to find
	 * @return True if all edges in boldEdges are marked bold in map
	 */
	public static final boolean testRoute(boolean positiveTest, ArrayList<String> map, ArrayList<String> boldEdges,
			String filename, String start, String stop, OutputFormat format) {
		boolean foundEdge = false;
		;
		boolean correct = true;
		boolean tmpCorrect = true;
		int matches = 0;

		for (String edge : boldEdges) { // for all edges we're looking for
			foundEdge = false;
			for (String line : map) { // for all lines in the map
				if (line.matches(".*" + edge + ".*")) { // if the edge is there
					tmpCorrect = line.matches(".*bold.*"); // check if it is bold
					if (!tmpCorrect && positiveTest) {
						System.err.println("[" + filename + "_route_" + start + "_" + stop
								+ "] looked for bold for edge: '" + edge + "' but found '" + line + "'");
					}
					correct = correct && tmpCorrect;
					matches++; // Count the number of bold lines correctly found
					foundEdge = true;
					break; // Let's not continue when we found one edge
				}
			}
			if (!foundEdge && positiveTest) {
				System.err.println("[" + filename + "_route_" + start + "_" + stop + "] Didn't find edge: " + edge);
			}
		}
		// Check if we found all of them
		correct = correct && (matches == boldEdges.size());
		//writeGraphToFile(map, filename, start, stop, format);
		return correct;
	}

	/**
	 * Tests if any edges are marked as bold. Returns true if no edges are marked as
	 * bold.
	 * 
	 * @param map
	 * @param filename
	 * @param start
	 * @param stop
	 * @param format
	 * @return
	 */
	public static final boolean testNoRoute(ArrayList<String> map, String filename, String start, String stop,
			OutputFormat format) {
		boolean correct = true;

		for (String line : map) { // for all lines in the map
			if (line.matches(".*bold.*")) { // if the edge is there
				correct = false;
				System.err.println(
						"[" + filename + "_route_" + start + "_" + stop + "] found unexpected bold edge: " + line);
			}
		}

		// writeGraphToFile(map,filename,start,stop,format);
		return correct;
	}

	/**
	 * This method writes a map to file
	 * 
	 * The format of the filename of the file created depends on the last four
	 * parameters:
	 * 
	 * if format = OutputForma.Time: output_[filename]_from[start]to[stop]Time.txt
	 * if format = OutputForma.Distance if format = OutputForma.Distance
	 * 
	 * @param map
	 * @param filename
	 * @param start
	 * @param stop
	 * @param format
	 */
	public static final void writeGraphToFile(ArrayList<String> map, String filename, String start, String stop,
			OutputFormat format) {
		try {
			String typeString = null;
			switch (format) {
			case Distance:
				typeString = "_Distance";
				break;
			case Time:
				typeString = "_Time";
				break;
			case RouteDistance:
				typeString = "_RouteDistance";
				break;
			case RouteTime:
				typeString = "_RouteTime";
				break;
			}

			FileWriter fw = new FileWriter("output_" + filename + "_from" + start + "to" + stop + typeString + ".txt");
			BufferedWriter bw = new BufferedWriter(fw);

			if (map.size() > 2)
				Collections.sort(map.subList(1, map.size() - 2));

			for (String element : map) {
				bw.write(element);
				bw.newLine();
			}
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
