package hw5;

import java.io.IOException;
import java.util.*;
import hw4.*;

public class MarvelPaths {
	private GraphWrapper<String, String> graph; 
	
	public void createNewGraph(String fileName) {
		graph = new GraphWrapper<String, String>();
		
		Map<String, Set<String>> charsInBooks = new HashMap<String,Set<String>>();
		Set<String> chars = new HashSet<String>();
		
		try {
			MarvelParser.readData(fileName,charsInBooks,chars);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.println("Read "+chars.size()+" characters who appear in "+charsInBooks.keySet().size() +" books.");
	
		Iterator<String> itr = chars.iterator();
		
		while(itr.hasNext()){
			graph.addNode(itr.next());
		}
		
		for(Map.Entry<String, Set<String>> book : charsInBooks.entrySet()) {
			String label = book.getKey();
			Set<String> characters = book.getValue();
			
			itr = characters.iterator();
			
			while(itr.hasNext()) {
				String start = itr.next();
				
				for(String end : characters) 
					if (!start.equals(end)) {
						graph.addEdge(start, end, label);
						graph.addEdge(end, start, label);
					}					
			}		
		}
	}
	
	public String findPath(String node1, String node2) {
		boolean fault1 = false, fault2 = false;
		String unknown = "";
		Iterator<String> nodes = graph.listNodes();
		while(nodes.hasNext()) {
			String current = nodes.next();
			if (current.equals(node1))
				fault1 = true;
			if (current.equals(node2))
				fault2 = true;
		}
		
		if (!fault1) {
			unknown += "unknown character "+node1+"\n";
		}
		if(!node1.equals(node2)) {
			if (!fault2)
				unknown += "unknown character "+node2+"\n";
		}

		if (!fault1||!fault2)
			return unknown;
		
		String output = "path from "+node1+" to "+node2+":" + "\n"; 
		
		Queue<String> queue = new LinkedList<String>();
		Map<String, ArrayList<Edge<String, String>>> nodePaths = new HashMap<String,ArrayList<Edge<String, String>>>();
		
		queue.add(node1);
		nodePaths.put(node1, new ArrayList<Edge<String, String>>());
		
		while(!queue.isEmpty()) {
			String n = queue.remove();
			
			if (n.equals(node2)) {
				ArrayList<Edge<String, String>> edges = nodePaths.get(node2);
				Iterator<Edge<String, String>> pathItr = edges.iterator();
				
				while(pathItr.hasNext()) {
					Edge<String, String> currentEdge = pathItr.next();
					
					output += currentEdge.parent + " to " + currentEdge.child + " via " + currentEdge.label +"\n";
				}	
				return output;
			}
				
			Iterator<String> itr = graph.listChildren(n);
			
			while(itr.hasNext()) {
				String currentNode = itr.next();
				int mark = 0;
				for (int i = 0; i < currentNode.length(); i++) {
					if (currentNode.charAt(i) == '(')
						mark = i;
				}
				String charName = currentNode.substring(0, mark);
				String bookName = currentNode.substring(mark+1, currentNode.length()-1);
				
				if (!nodePaths.containsKey(charName)) {
					ArrayList<Edge<String, String>> edges = new ArrayList<Edge<String, String>>(nodePaths.get(n));
					Edge<String, String> tempEdge = new Edge<String, String>(n, charName, bookName);
					edges.add(tempEdge);
					nodePaths.put(charName, edges);
					queue.add(charName);
				}
			}
		}
		
		output += "no path found\n";
		return output;
	}

	public static void main(String[] arg) {
 
    		String file = arg[0];  
    		 
    		MarvelPaths mp = new MarvelPaths();
    		
    		mp.createNewGraph(file);     		
    		
    		System.out.println(mp.findPath("SENTRY IV/VAL", "BANK, IRVING"));
    }
}
