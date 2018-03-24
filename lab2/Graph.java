import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
/*
 * Name: Dayoung Lee
 * EID: dl29923
 */

public class Graph implements Program2{
	// Edge is the class to represent an edge between two nodes
	// node is the destination node this edge connected to
	// time is the travel time of this edge
	// capacity is the capacity of this edge
	private class Edge{
		public int node;
		public int time;
		public int capacity;
		public Edge(int n, int t, int c){
			node = n;
			time = t;
			capacity = c;
		}
	}

	// V is the number of ports
	private int V;
    
	// graph is an adjacency matrix
	private Edge[][] graph;

	// adjacencyLists is a collection of adjacency lists
	// only used to for getNeighbors function because example_output file used adjacency lists
	// and I was too lazy to actually read the numbers one by one with my adjacency Matrix
	// no longer needed, I think
	// private ArrayList<LinkedList<Edge>> adjacencyLists;

	// This function is the constructor of the Graph. Do not change the parameters
	// of this function.
	// initialize the adjacency matrix and adjacency lists
	public Graph(int x) {
		V = x;
		graph = new Edge[V][V];
		// adjacencyLists = new ArrayList<LinkedList<Edge>>();
		// for(int i=0; i<V; i++) {
		// 	adjacencyLists.add(new LinkedList<Edge>());
		// }
	}
    
	// This function is called by Driver. The input is an edge in the graph.
	// Your job is to fix this function to generate your graph.
	// Do not change its parameters or return type.
	// Hint: Here is the place to build the graph with the data structure you defined.
	public void inputEdge(int port1, int port2, int time, int capacity) {
		graph[port1][port2] = new Edge(port2, time, capacity);
		graph[port2][port1] = new Edge(port1, time, capacity);
		// adjacencyLists.get(port1).add(new Edge(port2, time, capacity));
		// adjacencyLists.get(port2).add(new Edge(port1, time, capacity));
		return;
	}

	// This function is the solution for the Shortest Path problem.
	// The output of this function is an int which is the shortest travel time
	// from source port to destination port
	// Do not change its parameters or return type.
	public int findTimeOptimalPath(int sourcePort, int destPort) {
		int[] dist = new int[V];

		for(int i=0; i<V; i++) { dist[i] = Integer.MAX_VALUE; }
		dist[sourcePort] = 0;

		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		queue.add(sourcePort);

		while(!queue.isEmpty()) {
			int u = queue.poll();
			ArrayList<Integer> neighbors = getNeighbors(u);

			for(int v : neighbors) {
				if(dist[v] > dist[u] + graph[u][v].time) {
					dist[v] = dist[u] + graph[u][v].time;
					queue.add(v);
				}
			}
		}

		return dist[destPort];
	}

	// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from
	// source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
		return -1;
	}

	// This function returns the neighboring ports of node.
	// This function is used to test if you have contructed the graph correct.
	public ArrayList<Integer> getNeighbors(int node) {
		ArrayList<Integer> edges = new ArrayList<Integer>();
	
		for(int dest=0; dest<V; dest++) {
			if(graph[node][dest] != null) {
				edges.add(dest);
			}
		}

		// for(Edge e : adjacencyLists.get(node)) {
		// 	edges.add(e.node);
		// }

		return edges;
	}

	public int getNumPorts() {
		return V;
	}
}
