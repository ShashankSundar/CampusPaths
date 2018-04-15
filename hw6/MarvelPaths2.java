package hw6;

import java.io.IOException;
import java.util.*;

import hw4.*;
import hw5.*;

public class MarvelPaths2 {
	private GraphWrapper<String, Double> graph; 
	
	public void createNewGraph(String fileName) {
		graph = new GraphWrapper<String, Double>();
		
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
		
		Map<String, Integer> frequency = new HashMap<String, Integer>();
		
		
		for(Map.Entry<String, Set<String>> book : charsInBooks.entrySet()) {
			Set<String> characters = book.getValue();
			
			itr = characters.iterator();
			
			while(itr.hasNext()) {
				String start = itr.next();
				
				for(String end : characters) 
					if (!start.equals(end)) {
						String key = start+"%"+end;
						if (frequency.containsKey(key)) {
							frequency.put(key, frequency.get(key) + 1);
						}
						else
							frequency.put(key, 1);	
					}
				//		graph.addEdge(start, end, 0.0);			
			}		
		}
		
		for(Map.Entry<String, Integer> freq : frequency.entrySet()) {
			String current = freq.getKey();
			int colon = current.indexOf('%');
			String start = current.substring(0, colon);
			String end = current.substring(colon+1); 
			int num = freq.getValue(); 
			graph.addEdge(start, end, 1/(double)num);
			graph.addEdge(end, start, 1/(double)num);
		}
	}
	
	public String findPath(String node1, String node2) {
		boolean fault1 = false, fault2 = false;
		String unknown = "";
		Iterator<String> nodes1 = graph.listNodes();
		while(nodes1.hasNext()) {
			String current = nodes1.next();
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
		int count = 0;
		Iterator<String> nodes2 = graph.listNodes();
		while(nodes2.hasNext()) {
			count++;
			nodes2.next();
		}
		
		Comparator<ArrayList<Edge<String, Double>>> comparator = new pathComparator();
		
		PriorityQueue<ArrayList<Edge<String, Double>>> active = new PriorityQueue<>(count, comparator);
		ArrayList<String> finished = new ArrayList<>();
		ArrayList<Edge<String, Double>> first = new ArrayList<>();
		first.add(new Edge<String, Double>(node1, node1, 0.0));
		active.add(first);
		
		while(!active.isEmpty()) {
			ArrayList<Edge<String, Double>> minPath = active.remove();
			String minDest = minPath.get(minPath.size()-1).child;
			
			if (minDest.equals(node2)) {
				double sum = 0.0;
				for (int i = 1; i < minPath.size(); i++) {
					output += minPath.get(i).parent+" to "+minPath.get(i).child+" with weight "+String.format("%.3f", minPath.get(i).label)+"\n";	
					sum += minPath.get(i).label;
				}
				output += "total cost: "+String.format("%.3f", sum)+"\n";
				return output;
			}
			
			if (finished.contains(minDest)) {
				continue;
			}
			
			Iterator<String> itr = graph.listChildren(minDest);
			
			while(itr.hasNext()) {
				String currentNode = itr.next();
				int mark = 0;
				for (int i = 0; i < currentNode.length(); i++) {
					if (currentNode.charAt(i) == '(')
						mark = i;
				}
				String charName = currentNode.substring(0, mark);
				String weightStr = currentNode.substring(mark+1, currentNode.length()-1);
				double weight = Double.parseDouble(weightStr);
				
				if (!finished.contains(charName)) {
					ArrayList<Edge<String, Double>> newPath = new ArrayList<>(minPath);
					newPath.add(new Edge<String, Double>(minDest, charName, weight));
					active.add(newPath);	
				}
			}
			finished.add(minDest);
			
		}
		output += "no path found\n";
		return output;
	}

//	public static void main(String[] arg) {
//
//    		String file = arg[0];
//    		
//    		MarvelPaths2 mp = new MarvelPaths2();
//    		
//    		mp.createNewGraph(file);     		
//    		
//    		System.out.println(mp.findPath("SENTRY IV/VAL", "BANK, IRVING"));
//    }
}



