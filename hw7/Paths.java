package hw7;

import java.io.IOException;
import java.util.*;
import java.math.*;

import hw4.Edge;
import hw4.GraphWrapper;
import hw6.MarvelPaths2;
import hw6.pathComparator;

public class Paths {
	private GraphWrapper<String, Double> graph;
	private Calculations cl;
	
	public ArrayList<String> listNodes(ArrayList<String> nodes, Map<String, ArrayList<String>> names){
		ArrayList<String> out = new ArrayList<>();
		for (int i = 0; i < nodes.size(); i++) {
			out.add(nodes.get(i)+","+names.get(nodes.get(i)).get(0));
		}
		Collections.sort(out);
		return out;
	}
	
	public void createNewGraph(String nodeFileName, String edgeFileName, Map<String, ArrayList<String>> ids, 
			Map<String, ArrayList<String>> names, ArrayList<String> edges, ArrayList<String> nodes, Map<String, Point> coordinates) {
		RPIParser rp = new RPIParser();
		graph = new GraphWrapper<String, Double>();
		cl = new Calculations();
		
		
		try {
			RPIParser.readNodeData(nodeFileName, ids, names, nodes, coordinates);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			RPIParser.readEdgeData(edgeFileName, edges);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(Map.Entry<String, ArrayList<String>> building : names.entrySet()) {
			String name = building.getKey();
			
			graph.addNode(name);
		}
	
		for (int i = 0; i < edges.size()-1; i+=2) {
			String id1 = edges.get(i);
			String id2 = edges.get(i+1);
			ArrayList<String> data1 = ids.get(id1);
			ArrayList<String> data2 = ids.get(id2);
			Double x1 = Double.parseDouble(data1.get(1));
			Double y1 = Double.parseDouble(data1.get(2));
			Double x2 = Double.parseDouble(data2.get(1));
			Double y2 = Double.parseDouble(data2.get(2));
			Double weight = Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));
			
			graph.addEdge(data1.get(0), data2.get(0), weight);
			graph.addEdge(data2.get(0), data1.get(0), weight);
		}
	}
	
	public ArrayList<Edge<String, Double>> findPath(String name1, String name2, Map<String,ArrayList<String>> ids, 
			Map<String,ArrayList<String>> names, ArrayList<String> angles) {
		
		Comparator<ArrayList<Edge<String, Double>>> comparator = new pathComparator();
		
		PriorityQueue<ArrayList<Edge<String, Double>>> active = new PriorityQueue<>(ids.size(), comparator);
		ArrayList<String> finished = new ArrayList<>();
		ArrayList<Edge<String, Double>> first = new ArrayList<>();
		first.add(new Edge<String, Double>(name1, name1, 0.0));
		active.add(first);
		
		while(!active.isEmpty()) {
			ArrayList<Edge<String, Double>> minPath = active.remove();
			String minDest = minPath.get(minPath.size()-1).child;
			
			if (minDest.equals(name2)) {
				for (int i = 1; i < minPath.size(); i++) {
					String b1 = minPath.get(i).parent;
					String b2 = minPath.get(i).child;
					String x1 = names.get(b1).get(1);
					String y1 = names.get(b1).get(2);
					String x2 = names.get(b2).get(1);
					String y2 = names.get(b2).get(2);
					angles.add(cl.computeAngle(x1,y1,x2,y2));
				}
				return minPath;
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
				String name = currentNode.substring(0, mark);
				String weightStr = currentNode.substring(mark+1, currentNode.length()-1);
				double weight = Double.parseDouble(weightStr);
				
				if (!finished.contains(name)) {
					ArrayList<Edge<String, Double>> newPath = new ArrayList<>(minPath);
					newPath.add(new Edge<String, Double>(minDest, name, weight));
					active.add(newPath);	
				}
			}
			finished.add(minDest);
		}
		return new ArrayList<Edge<String, Double>>();
	}
}
