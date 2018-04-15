package hw4;

import java.util.*;

/*
*	Represents a graph, which is comprised of Nodes and Edges
*	
*	Graph is an mutable class. There can exist identical edges between nodes.
*
*	@specfield nodesEdges: Map from Strings(node names) to ArrayList of edges, which represent children.
*/
public class Graph<T, E> {	
	private Map< T, ArrayList<Edge<T, E>> > nodesEdges;
	
	// Abstraction Function:
    //   A Graph G is composed of Nodes, taken from nodesEdges.keySet, and Edges, 
	//	 taken from the value list of each key where each element is that list 
	//	 represents an edge from this key to this element.

    // Representation invariant for every Graph G:
    //   G != null && 
	//   For all String S in nodesEdges.keySet: 
	//		S's ArrayList contains this node
	// 
	//   Checking to make sure that each node is in the other node's list
	//   and vice versa if there exists an edge between them
	
	/*
	*   @effects constructs a new empty Graph
	*	@returns a new empty Graph
	*/
	public Graph() {
		nodesEdges = new HashMap<>();
	}
	
	/**
     * Checks that the representation invariant holds (if any).
     **/
    // Throws a RuntimeException if the rep invariant is violated.
//    private void checkRep() throws RuntimeException {
//        for (int i = 0; i < edgeList.size(); i++) {
//        		Node S = edgeList.get(i).start;
//        		Node E = edgeList.get(i).end;
//        		
//        		if (!(S.connections.contains(E)))
//        			throw new RuntimeException("Node edgelist does not match edge representation");
//        }
//    }

//	
	/*
	*	@param S the start node of new edge
	*	@param E the end node of new edge
	*	@param label the label of this new edge
	*	
	*	@requires S != null && E != null && this != null && label != null
	*   @effects adds 1 node to the other node's connections with label "label"
	*   and vice versa
	*/
	public void addEdgeTo(T start, T end,  E label){
		ArrayList<Edge<T,E>> temp = nodesEdges.get(start);
		temp.add(new Edge<T,E>(start, end, label));

		//checkRep();
	}
	
	/*
	*	@param N the node to add
	*	
	*	@requires N != null && this != null && N is not in G
	*   @effects adds N to Graph by adding it to nodesEdges
	*/
	public void addNode(T node){
		nodesEdges.put(node, new ArrayList<Edge<T, E>>());
		
		//checkRep();
	}
	
	/*
	*	@requires this != null
	*   @returns a sorted list of Node names
	*/
	public Iterator<String> listNodes(){
		ArrayList<String> output = new ArrayList<String>();
		
		for(Map.Entry<T, ArrayList<Edge<T, E>>> node : nodesEdges.entrySet()) {
			output.add(node.getKey().toString());
		}
		
		Collections.sort(output);
		return output.listIterator();
	}
	
	/*
	*   @param parent the parent node 
	*	@requires this != null && parent != null
	*   @returns a sorted list of parents children
	*/
	public Iterator<String> listChildren(T parent){
		ArrayList<Edge<T, E>> P = nodesEdges.get(parent);
		ArrayList<String> output = new ArrayList<String>();
		
		for (int i = 0; i < P.size(); i++) {
			Edge<T, E> temp = P.get(i);
			output.add(temp.child+"(" +temp.label+ ")");
		}
	
		
		Collections.sort(output);
		return output.listIterator();
	}
}


