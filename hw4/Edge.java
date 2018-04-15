package hw4;

/*
*	Represents an Edge, which is comprised of two end Nodes and a label
*	
*	Edge is an mutable class where both end Nodes and label must be declared in the constructor.
*
*	NOTE: rep exposure with start and end nodes as we need to assign references to Nodes in Graph to
*		  avoid double counting or not changing the original graph node as opposed to a copy.
*
*	@specfield parent: starting Node
*   @specfield child: endpoint Node
*   @specfield label: label for the edge
*/
public class Edge<T, E> {


	public T child;
	public T parent;
	public E label;
	
	/*
	*	@param from the startpoint node
	*	@param to the endpoint node
	*   @param name edge label
	*	
	*	@requires from != null && to != null && name != null
	*   @effects constructs a new Edge from -> to of label name
	*	@returns a new Edge from -> to of label name
	*/
	public Edge(T from, T to, E name) {
		parent = from;
		child = to;
		label = name;		
	}
	
//	public boolean equals(Edge S) {
//		if ((this.parent).equals(S.parent) && (this.child).equals(S.child) && (this.label).equals(S.label))
//			return true;
//		return false;
//	}


} 
