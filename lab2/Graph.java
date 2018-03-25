import java.util.ArrayList;
import java.util.LinkedList;
import java.lang.Math;
/*
 * Name: Dayoung Lee
 * EID: dl29923
 */

public class Graph implements Program2{
	// Edge is the class to represent an edge between two nodes
	// time is the travel time of this edge
	// capacity is the capacity of this edge
	private class Edge{
		public int time;
		public int capacity;
		public Edge(int t, int c){
			time = t;
			capacity = c;
		}
	}

	// V is the number of ports
	private int V;
    
	// graph is an adjacency matrix
	private Edge[][] graph;

	// This function is the constructor of the Graph. Do not change the parameters
	// of this function.
	// initialize the adjacency matrix and adjacency lists
	public Graph(int x) {
		V = x;
		graph = new Edge[V][V];
	}
    
	// This function is called by Driver. The input is an edge in the graph.
	// Your job is to fix this function to generate your graph.
	// Do not change its parameters or return type.
	// Hint: Here is the place to build the graph with the data structure you defined.
	public void inputEdge(int port1, int port2, int time, int capacity) {
		graph[port1][port2] = graph[port2][port1] = new Edge(time, capacity);
		return;
	}

	// This function is the solution for the Shortest Path problem.
	// The output of this function is an int which is the shortest travel time
	// from source port to destination port
	// Do not change its parameters or return type.
	public int findTimeOptimalPath(int sourcePort, int destPort) {
		int[] time = new int[V];
		for(int node=0; node<V; node++) { time[node] = Integer.MAX_VALUE; }
		time[sourcePort] = 0;
		
		ArrayList<Integer> queue = new ArrayList<Integer>();
		for(int node=0; node<V; node++) { queue.add(node); }

		while(!queue.isEmpty()) {
			int u = queue.remove(min(queue, time));
			ArrayList<Integer> neighbors = getNeighbors(u);

			for(int v : neighbors) {
				if(time[v] > time[u] + graph[u][v].time) {
					time[v] = time[u] + graph[u][v].time;
				}
			}
		}

		return time[destPort];
	}

	private int min(ArrayList<Integer> queue, int[] time) {
		int min_time = Integer.MAX_VALUE;
		int min_index = -1;
		for(int node=0; node<queue.size(); node++) {
			if(min_time >= time[queue.get(node)]) {
				min_time = time[queue.get(node)];
				min_index = node;
			}
		}

		return min_index;
	}

	// This function is the solution for the Widest Path problem.
	// The output of this function is an int which is the maximum capacity from
	// source port to destination port 
	// Do not change its parameters or return type.
	public int findCapOptimalPath(int sourcePort, int destPort) {
		int[] cap = new int[V];
		for(int node=0; node<V; node++) { cap[node] = 0; }
		cap[sourcePort] = Integer.MAX_VALUE;
	
		ArrayList<Integer> queue = new ArrayList<Integer>();
		for(int node=0; node<V; node++) { queue.add(node); }

		while(!queue.isEmpty()) {
			int u = queue.remove(max(queue, cap));
			ArrayList<Integer> neighbors = getNeighbors(u);

			for(int v : neighbors) {
				if(Math.min(cap[u], graph[u][v].capacity) > cap[v]) {
					cap[v] = Math.min(cap[u], graph[u][v].capacity);
				}
			}
		}

		return cap[destPort];
	}

	private int max(ArrayList<Integer> queue, int[] cap) {
		int max_cap = -1;
		int max_index = -1;
		for(int node=0; node<queue.size(); node++) {
			if(max_cap <= cap[queue.get(node)]) {
				max_cap = cap[queue.get(node)];
				max_index = node;
			}
		}

		return max_index;
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
		
		return edges;
	}

	public int getNumPorts() {
		return V;
	}
}
