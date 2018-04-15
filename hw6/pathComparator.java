package hw6;

import java.util.ArrayList;
import java.util.Comparator;

import hw4.Edge;

public class pathComparator implements Comparator<ArrayList<Edge<String, Double>>>{
	
	public int compare(ArrayList<Edge<String, Double>> x, ArrayList<Edge<String, Double>> y) {
		double sumX = 0, sumY = 0;
		
		for (int i = 0; i < x.size(); i++) {
			sumX += x.get(i).label;
		}
		for (int i = 0; i < y.size(); i++) {
			sumY += y.get(i).label;
		}
		
		if (sumX < sumY) {
			return -1;
		}
		if (sumX > sumY) {
			return 1;
		}
		return 0;
	}
}
