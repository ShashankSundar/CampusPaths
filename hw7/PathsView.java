package hw7;

import java.util.*;
import hw7.CampusPaths;

import hw4.Edge;

public class PathsView {
	
	public void output (ArrayList<Edge<String, Double>> minPath, ArrayList<String> angles, String name1, String name2) {
		double sum = 0.0;
		if(minPath.size() == 0) {
			System.out.println("There is no path from "+name1+" to "+name2+".");
			return;
		}
		System.out.println("Path from "+minPath.get(0).parent+" to "+minPath.get(minPath.size()-1).child+":");
		for (int i = 1; i < minPath.size(); i++) {
			String building2 = minPath.get(i).child;
			System.out.println("\tWalk "+angles.get(i-1)+" to ("+building2+")");	
			sum += minPath.get(i).label;
		}
		System.out.println("Total distance: "+String.format("%.3f", sum)+" pixel units.");
	}
	
	public void setup (int i) {
		if (i == 1) {
			System.out.print("First building id/name, followed by Enter: ");
		}
		else if (i == 2) {
			System.out.print("Second building id/name, followed by Enter: ");
		}
	}
	
	public void output (ArrayList<String> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			System.out.println(nodes.get(i));
		}
	}
	
	public void output () {
		System.out.println("Unknown option");
	}
	
	public void options () {
		System.out.println("b lists all buildings (only buildings) in the form name,id in lexicographic"
				+ "(alphabetical) order of name.");
		System.out.println("r prompts the user for the ids or names of two buildings (only buildings!) "
				+ "and prints directions for the shortest route between them.");
		System.out.println("q quits the program.");
		System.out.println("m prints a menu of all commands.");
	}
	
	public void unknownBuilding(String str) {
		System.out.println(str);
	}
}
