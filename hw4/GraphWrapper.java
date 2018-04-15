package hw4;

import java.util.*;

public class GraphWrapper<T, E> {
	
	private Graph<T, E> G;
	
	public GraphWrapper() {
		G = new Graph<T, E>();
	}
	
	public void addNode(T nodeData) {
		G.addNode(nodeData);
	}
	
	public void addEdge(T parentNode, T childNode, E edgeLabel) {
		G.addEdgeTo(parentNode, childNode, edgeLabel);
	}
	
	public Iterator<String> listNodes(){
		return G.listNodes();
	}
	
	public Iterator<String> listChildren(T P){
		return G.listChildren(P);
	}
}
