package lab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * 
 * @author thecr
 *
 * @param <T>
 *            ensures that the parameter supports the comparison of nodes
 */
public class Dijkstra<T extends Comparable<T>> {

	private ArrayList<Node> nodes;
	private ArrayList<Edge> edges;
	private static List<String> waitingTime = new ArrayList<String>();

	/*
	 * State of the node. Can either be white (= not visited), grey (= visited) or
	 * black (= visited and completed)
	 */
	public enum State {
		WHITE, GREY, BLACK
	};

	/**
	 * Describes the Edge. Connects 2 Nodes (from and to) and has a weight which in
	 * our case either can be the distance to the to node or the time it takes in
	 * minutes to traverse to the to node
	 * 
	 * Edge Class
	 * 
	 * @author thecr
	 *
	 */
	class Edge {

		Node from;
		Node to;
		double weight;

		/**
		 * Constructor
		 * 
		 * @param fromNode
		 *            the Node from which the Edge starts
		 * @param toNode
		 *            the Node to which the Edge goes
		 * @param weight
		 *            the weight of the Edge (can either be in km or in minutes)
		 */
		public Edge(T fromNode, T toNode, double weight) {

			from = findNode(fromNode);
			if (from == null) {
				from = new Node(fromNode);
				nodes.add(from);
			}

			to = findNode(toNode);
			if (to == null) {
				to = new Node(toNode);
				nodes.add(to);
			}
			this.weight = weight;

			from.addOutgoing(to);
			to.addIncoming(from);
		}
	}

	/**
	 * Describes the Node. My Node approach is a bit "sloppy" as it is basically
	 * only two lists of nodes holding all the outgoing and incoming nodes. It also
	 * uses the State to describe the status of the node white (= not visited), grey
	 * (= visited) or black (= visited and completed)
	 * 
	 * @author thecr
	 *
	 */
	class Node implements Comparable<Node> {

		Node father = null;
		T name;
		double minDistance = Double.MAX_VALUE;

		List<Node> nodesIncoming;
		List<Node> nodesOutgoing;
		State state;

		/**
		 * @param value
		 *            name of the node
		 */

		public Node(T value) {
			this.name = value;
			nodesIncoming = new ArrayList<>();
			nodesOutgoing = new ArrayList<>();
			state = State.WHITE;
		}

		/**
		 * this is very necessary to make the nodes comparable to each other. This is
		 * the whole essence of our priorityQueue
		 */
		public int compareTo(Node other) {
			return Double.compare(minDistance, other.minDistance);
		}

		/**
		 * 
		 * @param node
		 *            receives the node which should be put into the incoming list
		 */
		public void addIncoming(Node node) {
			nodesIncoming.add(node);
		}

		/**
		 * 
		 * @param node
		 *            receives the node which should pe put into the outgoing list
		 */
		public void addOutgoing(Node node) {
			nodesOutgoing.add(node);
		}

	}

	/**
	 * Constructor
	 */
	public Dijkstra() {
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
	}

	/**
	 * Adds an Edge and checks if the Edge is already existing if it is existing we
	 * just update the length, if it is not existing jet we create it with the given
	 * values
	 * 
	 * @param from
	 *            starting Node of the edge
	 * @param to
	 *            end Node of the edge
	 * @param d
	 *            weight of the edge, in our case either the length in km or the
	 *            time it takes to traverse the edge, represented as double
	 */
	public void addEdge(T from, T to, double d) {
		Edge temp = findEdge(from, to);
		if (temp != null) {
			temp.weight = (int) d;
		} else {
			Edge e = new Edge(from, to, d);
			edges.add(e);
		}
	}

	/**
	 * Resets the Distance by setting all the distances to the max_value. This
	 * ensures the characteristics of the Dijkstra algorithm (just like the
	 * pseudocode shown in the lecture)
	 */
	private void initializeSingleSource() {
		for (Node each : nodes) {
			each.minDistance = Double.MAX_VALUE; // set it to endless
			each.father = null; // set the father to null
		}
	}

	/**
	 * Iterates trough all the nodes in the graph to search for the node
	 * 
	 * @param n
	 *            receives the Node from type T (to ensure comparability)
	 * @return returns the node searched for if it is found, if it is not found it
	 *         will return null
	 */
	public Node findNode(T n) {
		for (Node allNodes : nodes) {
			if (allNodes.name.compareTo(n) == 0)
				return allNodes;
		}
		return null;
	}

	/**
	 * Iterates trough all the edges and checks if the edge we are searching for is
	 * existing by comparing it to the given nodes
	 * 
	 * @param node1
	 *            (not the same as node2!)
	 * @param node2
	 *            (not the same as node1!)
	 * @return Returns the edge between two nodes, if there is any.
	 */
	private Edge findEdge(Node node1, Node node2) {
		for (Edge each : edges) {
			if (each.from.equals(node1) && each.to.equals(node2)) {
				return each;
			}
		}
		return null;
	}

	/**
	 * Iterates trough all the edges and checks if the edge we are searching for is
	 * existing by comparing it to the given nodes
	 * 
	 * @param from
	 *            not the same as node2!)
	 * @param to
	 *            (not the same as node1!)
	 * @return Returns the edge between two nodes, if there is any.
	 * 
	 *         The difference between this and the method above is that the lower
	 *         one uses our T-Save (the parameter and receiving value are of the
	 *         same type)
	 */
	private Edge findEdge(T from, T to) {
		for (Edge each : edges) {
			if (each.from.name.equals(from) && each.to.name.equals(to)) {
				return each;
			}
		}
		return null;
	}

	/*
	 * Methods to find the shortest path!
	 */

	/**
	 * Searches for all shortest paths and put the information into the nodes
	 * 
	 * @param startingNode
	 *            receives the starting node from which to start the algorithm
	 * @return true if the action was performed successfull and false if it was not
	 *         or if the starting node is empty
	 */
	private boolean Dijkstra_findPathLength(T startingNode) {
		// obvious case
		if (nodes.isEmpty())
			return false;

		// as shown in the lecture: Initalize the value of each node
		initializeSingleSource();

		Node source = findNode(startingNode);
		if (source == null)
			return false;

		// obviously the distance to the node itself is zero
		source.minDistance = 0;

		// priorityQueue is created and our source Element is put into it
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(source);

		// as long as the priorityQueue is not empty...
		while (priorityQueue.isEmpty() == false) {
			// ...we poll the first Element
			Node pqNode = priorityQueue.poll();

			// compares each outgoing nodes value
			for (Node node : pqNode.nodesOutgoing) {

				// from here on it is basically the relax function trying
				// to find a minimal node
				Edge edge = findEdge(pqNode, node);

				if (edge == null)
					return false;

				// the total distance traveled so far is our previous distance plus the weight
				// of the edge we are looking at at the moment
				double totalDistance = pqNode.minDistance + edge.weight;

				if (totalDistance < node.minDistance) {
					priorityQueue.remove(node);
					node.minDistance = totalDistance;
					node.father = pqNode;
					priorityQueue.add(node);
				}
			}

		}
		return true;
	}

	/*
	 * Methods to find the fastest path!
	 */
	
	/**
	 * 
	 * @param target
	 *            the target node to which to travel
	 * @return returns the path we took (as list) to get to our destination and the
	 *         length to each path (accumulated)
	 */
	private List<String> getShortestPath(Node target) {
		List<String> path = new ArrayList<String>();
	
		if (target.minDistance == Double.MAX_VALUE) {
			path.add("No path found");
		}
	
		for (Node n = target; n != null; n = n.father) {
	
			path.add(n.name + " : length : " + Math.ceil(n.minDistance));
		}
	
		// reverses the path because we start at the destination and go up until we are
		// at our source again
		Collections.reverse(path);
	
		return path;
	
	}

	/**
	 * 
	 * 
	 * @param from
	 *            node from which to start
	 * @param to
	 *            node to which to go
	 * @return returns the route we took to get there
	 */
	public List<String> getPathLength(T from, T to) {
		/*
		 * Is not really unused, this is the essential call to initalize the path and
		 * get the all information needed
		 */
		@SuppressWarnings("unused")
		boolean djikstra = Dijkstra_findPathLength(from);

		List<String> path = getShortestPath(findNode(to));
		return path;
	}

	/**
	 * Gets the string information from the getPathLength method and only extracts
	 * the integer of the route travelled
	 * 
	 * @param source
	 *            node from which to start
	 * @param destination
	 *            node to which to go
	 * @return returns the length (as km) of the shortest route from source to
	 *         destination
	 */
	public int getLengthAsInt(T source, T destination) {

		/**
		 * Catches all the mistakes
		 */
		if (findNode(source) == null) {
			return Navigation.SOURCE_NOT_FOUND;

		}

		if (findNode(destination) == null) {
			return Navigation.DESTINATION_NOT_FOUND;
		}

		if (findNode(destination) == null && findNode(source) == null) {
			return Navigation.SOURCE_DESTINATION_NOT_FOUND;
		}

		List<String> path = getShortestPath(findNode(destination));

		// stores the information of the length at the destination node
		String maxValueOfPath = Collections.max(path);

		// replaces all the unnecessary strings so we can use the integer
		String maxPath = path.toString().replaceAll(" ", "").replaceAll("length", "");

		maxPath = maxPath.replaceAll("[A-Z]", "");
		maxPath = maxPath.replaceAll("[a-z]", "");
		maxPath = maxPath.replaceAll("::", "");

		// turns all the Elements into an string array
		String[] maxPathArray = maxPath.replace("[", "").replace("]", "").split(",");

		if (maxValueOfPath.compareTo("No path found") == 0) {
			return Navigation.NO_PATH;
		}

		/*
		 * compares all the values and finds the biggest of them, so we output the
		 * correct length
		 */
		double highest = 0;

		for (int i = 0; i < maxPathArray.length; i++) {

			if (highest < Double.parseDouble(maxPathArray[i])) {

				highest = Double.parseDouble(maxPathArray[i]);

			}
		}

		return (int) highest;

	}

	/*
	 * Methods to find the fastest path!
	 */

	/**
	 * Searches for all fastest paths and put the information into the nodes
	 * 
	 * @param startingNode
	 *            receives the starting node from which to start the algorithm
	 * @return true if the action was performed successfull and false if it was not
	 *         or if the starting node is empty
	 */
	private boolean Dijkstra_findPathTime(T startingNode) {
		// obvious case
		if (nodes.isEmpty())
			return false;

		// as shown in the lecture: Initalize the value of each node
		initializeSingleSource();

		Node source = findNode(startingNode);
		if (source == null)
			return false;

		// obviously the distance to the node itself is zero
		source.minDistance = 0;

		// priorityQueue is created and our source Element is put into it
		PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(source);

		// as long as the priorityQueue is not empty...
		while (priorityQueue.isEmpty() == false) {

			// ...we poll the first Element
			Node nodePQ = priorityQueue.poll();

			// compares each outgoing nodes value
			for (Node nodes : nodePQ.nodesOutgoing) {

				// from here on it is basically the relax function trying
				// to find a minimal node
				Edge edge = findEdge(nodePQ, nodes);

				if (edge == null)
					return false;

				// calls the dictionary function and looks up the waiting time
				int waitTime = returnWaitingTime(nodePQ.name.toString());

				// the total distance traveled so far is our previous distance plus the weight
				// of the edge we are looking at at the moment. Also adds the waitTime of the
				// current Node
				double totalDistance = nodePQ.minDistance + edge.weight + waitTime;

				if (totalDistance < nodes.minDistance) {
					priorityQueue.remove(nodes);
					nodes.minDistance = totalDistance;
					nodes.father = nodePQ;
					priorityQueue.add(nodes);
				}
			}

		}
		return true;
	}

	/**
	 * private method
	 * @param destination the target node to which to travel
	 * @return returns the path which 
	 */
	private List<String> getFastestPath(Node destination) {
		List<String> path = new ArrayList<String>();

		if (destination.minDistance == Double.MAX_VALUE) {

			path.add("No path found");
		}

		for (Node n = destination; n != null; n = n.father) {

			path.add(n.name + " : length : " + n.minDistance);

		}

		Collections.reverse(path);

		return path;
	}

	/**
	 * 
	 * @param source start node from which the path should start
	 * @param destination destination node on which the path should ends
	 * @return returns the list of all traversed nodes and the accumulated length
	 */
	public List<String> getPathTime(T source, T destination) {
		@SuppressWarnings("unused")
		boolean test = Dijkstra_findPathTime(source);

		List<String> path = getFastestPath(findNode(destination));
		return path;
	}

	/**
	 * Extracts only the useful information from our path to return only the integer of the time 
	 * it takes to travel from the source to the destination
	 * @param source source node from which to start
	 * @param destination destination node to which to travel
	 * @return returns the time it takes to travel from source to destination ( in minutes )
	 * 
	 */

	public int getTime(T source, T destination) {
		//catches all exceptions or cases which should not further be calculated
		if (findNode(source) == null) {
			return Navigation.SOURCE_NOT_FOUND;

		}

		if (findNode(destination) == null) {
			return Navigation.DESTINATION_NOT_FOUND;
		}

		if (findNode(destination) == null && findNode(source) == null) {
			return Navigation.SOURCE_DESTINATION_NOT_FOUND;
		}
		
		
		//receives all path informatin
		List<String> path = getShortestPath(findNode(destination));
		String maxValuesofPath = Collections.max(path);

		//removes all unnecessary information we dont need for our calculation
		String stringExtractor = path.toString().replaceAll(" ", "").replaceAll("length", "");

		stringExtractor = stringExtractor.replaceAll("[A-Z]", "");
		stringExtractor = stringExtractor.replaceAll("[a-z]", "");
		stringExtractor = stringExtractor.replaceAll("::", "");

		//turns the string into an array to make it easier to compare
		String[] valueArray = stringExtractor.replace("[", "").replace("]", "").split(",");

		
		//catches if there is no path found 
		if (maxValuesofPath.compareTo("No path found") == 0) {
			return Navigation.NO_PATH;
		}

		
		//finds the highes value 
		double highest = 0;

		for (int i = 0; i < valueArray.length; i++) {

			if (highest < Double.parseDouble(valueArray[i])) {

				highest = Double.parseDouble(valueArray[i]);

			}
		}

		return (int) highest;
	}

	/**
	 * 
	 * @param node
	 *            receives the node which should be looked up in our "dictionary"
	 * @return returns the integer value of the waiting time of the node
	 */
	private int returnWaitingTime(String node) {

		Dijkstra.waitingTime = Navigation.waitingTime;
		int waitingTimeInt = 0;

		for (int i = 0; i < waitingTime.size(); i++) {

			String value = Dijkstra.waitingTime.get(i).substring(0, Dijkstra.waitingTime.get(i).indexOf(' '));

			if (value.equals(node)) {
				int blank = Dijkstra.waitingTime.get(i).indexOf(' ');

				waitingTimeInt = Integer.valueOf(waitingTime.get(i).substring(blank + 1));

			}

		}

		return waitingTimeInt;
	}

}