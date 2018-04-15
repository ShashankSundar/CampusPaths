package hw4;

import java.util.*;

/*
*	Represents a Node, which has a name and incoming and outgoing edges.
*	
*	Node is an mutable class where the connected edges and name must be declared in the constructor.
*
*	NOTE: We have some rep exposure with connections. This was used to alleviate the need of a getConnections()
*   		  method to increase efficiency.
*
*	@specfield name: name of Node in the graph
*	@specfield connections: List of Nodes in graph that this Node has an outgoing edge to
*/
public class Node {
	/* name of node */
	public String name;

	/* List of Nodes this node has an outgoing edge to */
	//public ArrayList<Node> connections;
	
	//public Set<String> connections;
	// Abstraction Function:
    //   A Node is named "name" and has outgoing edges to all 
	// 	 Nodes in connections

    // Representation invariant for every Node N:
    //   N != null && N.connections != null
	
	/*
	*	@param n the name of the node
	*	
	*	@requires n != null
	*   @effects constructs a new Node called "n"
	*	@returns a new Node called "n"
	*/
	public Node(String n) {
		name = n;
		//connections = new ArrayList<Node>();
		//connections = new HashSet<String>();
		
		//checkRep();
	}


	
	/*
	*	@param E the name of the node to make an edge to 
	*	@param label the label of this new edge
	*	
	*	@requires E != null && this != null
	*   @effects adds an node to this nodes connections with label "label"
	*	@returns a new edge called "label" from this node to E
	*/
//	public Edge addEdgeTo(Node E, String label){
//
//		
//		//checkRep();
//		
//		return new Edge(this, E, label);	
//	}
	
	/*
	*	@param S the node to compare to
	*	
	*	@requires this != null && S != null 
	*	@returns a true if this node's name is the same as S's name and false otherwise
	*/
	public boolean equals(Node S) {
		if ((this.name).equals(S.name))
			return true;
		return false;
	}

}