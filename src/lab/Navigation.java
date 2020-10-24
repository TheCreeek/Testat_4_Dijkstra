package lab;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The class Navigation finds the shortest (and/or) path between points on a map
 * using the Dijkstra algorithm
 */
public class Navigation {
	ArrayList<String> output = new ArrayList<String>();
	/**
	 * Return codes: -1 if the source is not on the map -2 if the destination is not
	 * on the map -3 if both source and destination points are not on the map -4 if
	 * no path can be found between source and destination
	 */

	public static final int SOURCE_NOT_FOUND = -1;
	public static final int DESTINATION_NOT_FOUND = -2;
	public static final int SOURCE_DESTINATION_NOT_FOUND = -3;
	public static final int NO_PATH = -4;


	private Dijkstra<String> shortestRoute = new Dijkstra<String>();
	private Dijkstra<String> fastestRoute = new Dijkstra<String>();
	
/*
 * Stores the waiting time which was unnecessary added (in our use cases this will be the first and the last node, as their waitingTime was added
 * but should not be added because the first and last node are never traversed
 */
	int unnecessaryWaitingTime = 0;

	/*
	 * The list is later used as a "Dictionary", to check which city has how much of a waiting time 
	 * This List is also called from our Dijkstra calculation, so it is static and public
	 */
	public static List<String> waitingTime = new ArrayList<String>();
	//Saves the connections (edges) taken to get from source to destination
	private List<String> traversedConnections = new ArrayList<String>();

	/**
	 * The constructor takes a filename as input, it reads that file and fill the
	 * nodes and edges Lists with corresponding node and edge objects
	 * 
	 * @param filename
	 *            name of the file containing the input map
	 */
	public Navigation(String filename) {

		try {
			// Open the file
			FileInputStream fstream = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

			// Read File Line By Line
			for (String curLine = br.readLine(); curLine != null; curLine = br.readLine()) {

				//adds the just read input to the output, this may be a bit lazy but helps us later
				output.add(curLine);

				// first and last line are simply ignored
				if (curLine.contains("{") || curLine.contains("}")) {
					// ignore this lines
				}

				// every other line is interpreted

				else {

					// index of different "signs" in the line, these are helpful for reading the
					// content of each line
					int arrow = curLine.indexOf('>');
					int sign = curLine.indexOf('"');
					int sign2 = curLine.lastIndexOf('"');
					int dividingComma = curLine.indexOf(',');
					int dash = curLine.indexOf('-');
					int bracket = curLine.indexOf('[');

					// meaning: If the position of the arrow is not negativ (--> there is a arrow in
					// this line!) proceed
					// Line of this format: A -> B [label="10,90"];
					if (!(arrow < 0)) {

						String from = curLine.substring(0, dash - 1);
						String to = curLine.substring(arrow + 2, bracket - 1);

						int length = Integer.parseInt(curLine.subSequence(sign + 1, dividingComma).toString());
						int speed = Integer.parseInt(curLine.subSequence(dividingComma + 1, sign2).toString());

						shortestRoute.addEdge(from, to, length);

						fastestRoute.addEdge(from, to, ((double) length / (double) speed * 60));

					}
					// meaning: there is no arrow in this line!
					// Line of this format: A [label="A,5"];

					// Nodes with waiting time
					if ((arrow < 0)) {

						String name = curLine.substring(0, bracket - 1);
						int waitingTime = Integer.parseInt(
								curLine.subSequence(curLine.indexOf(',') + 1, (curLine.indexOf(',') + 2)).toString());
						//This adds the name of the Node and the belonging waiting time to the List. 
						Navigation.waitingTime.add(name + " " + waitingTime);

					}

				}

			}

			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * This methods finds the shortest route (distance) between points A and B on
	 * the map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type ArrayList<String>,
	 * where every element is a String representing one line in the map. The output
	 * map is identical to the input map, apart from that all edges on the shortest
	 * route are marked "bold". It is also possible to output a map where all
	 * shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or if
	 *         there is no path between them the original map is to be returned.
	 */
	public ArrayList<String> findShortestRoute(String A, String B) {

		//Initalizes and saves the shortest Path taken from one source to (another) destination
		List<String> shortestPath = new ArrayList<String>(shortestRoute.getPathLength(A, B));
		

		/*
		 * (Ab)uses our output from the path to get each traversed node (Output looks like A,B,C,D, which
		 * means we started from Node A, traveled over node B and C to reach our destination Node D
		 * The output from our method is then fit to our needs by replacing each unnecessary node
		 */
		String matcher;
		matcher = (shortestPath.toString().replaceAll(":", "").replace("length", "").replaceAll("[0-9]", "").replace(".", "")
				.replace(" ", "").toString());
		
		/*
		 * If we find our "signal word" we know there is no path between the two nodes, so there is no need
		 * to check further and we can simply print our output how we got it (output = input) to save computing
		 * power (--> no node was traversed = no node is made bold!)
		 */
		
		if (matcher.contains("Nopathfound")) {
			return output;
		}
		
		
		
		//Turns our just created matcher into an array by splitting at each ','		
		String[] matcherArray = ((String) matcher.subSequence(1, matcher.length() - 1)).split(",");

		
		/*
		 * Uses our just created Array to produce an output of the form (for the example A,B,C,D) : 
		 * [A -> B, B -> C, C -> D] this is our path taken and fits the input we got as Dot language
		 */
		for (int i = 0; i < matcherArray.length - 1; i++) {

			String element = matcherArray[i] + " -> " + matcherArray[i + 1];

			traversedConnections.add(element);

		}
		
		/*
		 * works on the prepared output (which was basically our input just saved...) and applies the bold style to each traversed edge 
		 * which we find by comparing it with our List (which contains "A -> B" like string).
		 */

		for (int i = 0; i < output.indexOf("}"); i++) {
			if (output.get(i).contains("->")) {

				for (int index = 0; index + 1 < matcherArray.length; index++) {

					if (output.get(i).contains(traversedConnections.get(index))) {

						output.set(i, output.get(i).replace(";", "") + "[style = bold]" + ";");
					}

				}
			}
		}

		return output;

	}

	/**
	 * 
	 * This methods finds the fastest route (in time) between points A and B on the
	 * map given in the constructor.
	 * 
	 * If a route is found the return value is an object of type ArrayList<String>,
	 * where every element is a String representing one line in the map. The output
	 * map is identical to the input map, apart from that all edges on the shortest
	 * route are marked "bold". It is also possible to output a map where all
	 * shortest paths starting in A are marked bold.
	 * 
	 * The order of the edges as they appear in the output may differ from the
	 * input.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destinaton
	 * @return returns a map as described above if A or B is not on the map or if
	 *         there is no path between them the original map is to be returned.
	 */
	public ArrayList<String> findFastestRoute(String A, String B) {
		
		//Initalizes and saves the shortest Path taken from one source to (another) destination
		List<String> a = new ArrayList<String>(shortestRoute.getPathTime(A, B));

		/*
		 * (Ab)uses our output from the path to get each traversed node (Output looks like A,B,C,D, which
		 * means we started from Node A, traveled over node B and C to reach our destination Node D
		 * The output from our method is then fit to our needs by replacing each unnecessary node
		 */
		String matcher;

		matcher = (a.toString().replaceAll(":", "").replace("length", "").replaceAll("[0-9]", "").replace(".", "")
				.replace(" ", "").toString());

		
		/*
		 * If we find our "signal word" we know there is no path between the two nodes, so there is no need
		 * to check further and we can simply print our output how we got it (output = input) to save computing
		 * power (--> no node was traversed = no node is made bold!)
		 */

		if (matcher.contains("Nopathfound")) {
			return output;
		}
		
		//Turns our just created matcher into an array by splitting at each ','		
		String[] matcherArray = ((String) matcher.subSequence(1, matcher.length() - 1)).split(",");
		
		/*
		 * Uses our just created Array to produce an output of the form (for the example A,B,C,D) : 
		 * [A -> B, B -> C, C -> D] this is our path taken and fits the input we got as Dot language
		 */

		for (int i = 0; i < matcherArray.length - 1; i++) {

			String element = matcherArray[i] + " -> " + matcherArray[i + 1];

			traversedConnections.add(element);

		}

		/*
		 * works on the prepared output (which was basically our input just saved...) and applies the bold style to each traversed edge 
		 * which we find by comparing it with our List (which contains "A -> B" like string).
		 */

		for (int i = 0; i < output.indexOf("}"); i++) {
			if (output.get(i).contains("->")) {

				for (int index = 0; index + 1 < matcherArray.length; index++) {
					
					if (output.get(i).contains(traversedConnections.get(index))) {
						
						output.set(i, output.get(i).replace(";", "") + "[style = bold]" + ";");
						
					}
				}

			}
		}

		return output;

	}

	/**
	 * Finds the shortest distance in kilometers between A and B using the Dijkstra
	 * algorithm.
	 * 
	 * @param A
	 *            the start point A
	 * @param B
	 *            the destination point B
	 * @return the shortest distance in kilometers rounded upwards. SOURCE_NOT_FOUND
	 *         if point A is not on the map DESTINATION_NOT_FOUND if point B is not
	 *         on the map SOURCE_DESTINATION_NOT_FOUND if point A and point B are
	 *         not on the map NO_PATH if no path can be found between point A and
	 *         point B
	 */
	public int findShortestDistance(String A, String B) {
		/*
		 * Check if both points are available in the graph and if there is a path
		 * possible between them
		 */
		
		if (A.equals(B)) {
			return 0;
		}

		if (shortestRoute.findNode(A) == null && shortestRoute.findNode(B) == null) {
			return SOURCE_DESTINATION_NOT_FOUND;
		}

		if (shortestRoute.findNode(A) == null) {
			return SOURCE_NOT_FOUND;
		}

		if (shortestRoute.findNode(B) == null) {
			return DESTINATION_NOT_FOUND;
		}

		
		
		
		@SuppressWarnings("unused")
		List<String> path = shortestRoute.getPathLength(A, B);

		int shortest = 0;
		shortest = (int) shortestRoute.getLengthAsInt(A, B);

		return shortest;

	}

	/**
	 * Find the fastest route between A and B using the dijkstra algorithm.
	 * 
	 * @param A
	 *            Source
	 * @param B
	 *            Destination
	 * @return the fastest time in minutes rounded upwards. SOURCE_NOT_FOUND if
	 *         point A is not on the map DESTINATION_NOT_FOUND if point B is not on
	 *         the map SOURCE_DESTINATION_NOT_FOUND if point A and point B are not
	 *         on the map NO_PATH if no path can be found between point A and point
	 *         B
	 */
	public int findFastestTime(String pointA, String pointB) {
		
		/*
		 * Check if both points are available in the graph and if there is a path
		 * possible between them
		 */

		if (pointA.equals(pointB)) {
			return 0;
		}

		if (fastestRoute.findNode(pointA) == null && fastestRoute.findNode(pointB) == null) {
			return SOURCE_DESTINATION_NOT_FOUND;
		}

		if (fastestRoute.findNode(pointA) == null) {
			return SOURCE_NOT_FOUND;
		}

		if (fastestRoute.findNode(pointB) == null) {
			return DESTINATION_NOT_FOUND;
		}

		List<String> path = fastestRoute.getPathTime(pointA, pointB);

		int fastest = 0;
		fastest = fastestRoute.getTime(pointA, pointB);
		
		//If there is no path found, there is no need to Continue searching.
		if (fastest == -4) {
			return fastest;
		}

		List<String> clone = new ArrayList<String>();

		for (int i = 0; i < path.size(); i++) {
			clone.add(path.get(i));
		}
		
		if (clone.size() > 2){
			String lastValue = 
			(clone.get(clone.size()-1).substring(0, clone.get(clone.size() -1 ).indexOf(' ')));
			
			
			
			unnecessaryWaitingTime = unnecessaryWaitingTime - returnWaitingTime(lastValue);
		}
			String firstValue = 
					(clone.get(0)).substring(0, clone.get(0).indexOf(' '));	
			unnecessaryWaitingTime = unnecessaryWaitingTime - returnWaitingTime(firstValue);
		
		
		
		return fastest + unnecessaryWaitingTime;

	}

	/**
	 * "Dictionary Function" which checks to which City which waiting Time belongs
	 * 
	 * @param node
	 * @return returns the value as int of how long the waiting time at given node
	 *         is (in minutes)
	 */
	public int returnWaitingTime(String node) {

		int waitingTimeInt = 0;

		for (int i = 0; i < Navigation.waitingTime.size(); i++) {

			String value = Navigation.waitingTime.get(i).substring(0, Navigation.waitingTime.get(i).indexOf(' '));

			if (value.equals(node)) {
				int blank = Navigation.waitingTime.get(i).indexOf(' ');

				waitingTimeInt = Integer.valueOf(waitingTime.get(i).substring(blank + 1));

			}

		}
		
		
		return waitingTimeInt + unnecessaryWaitingTime;
	}
}
