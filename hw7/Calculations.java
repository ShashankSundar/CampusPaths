package hw7;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

import hw4.Edge;
import hw6.pathComparator;

public class Calculations extends Paths {
	
	//Checks if name/id's exists
	public int checkExists(String nameId1, String nameId2, Map<String, ArrayList<String>> ids, 
			Map<String, ArrayList<String>> names, ArrayList<String> edges, ArrayList<String> nodes) {
		String name1 = null;
		String name2 = null;
		if (ids.containsKey(nameId1)) 
			name1 = ids.get(nameId1).get(0);
		else
			name1 = nameId1;
		if (ids.containsKey(nameId2)) 
			name2 = ids.get(nameId2).get(0);
		else
			name2 = nameId2;
		
		boolean found1 = false;
		boolean found2 = false;
		
		if (nodes.contains(name1)) {
			found1 = true;
		}

		if (nodes.contains(name2)) {
			found2 = true;
		}
		
		if (!found1 && !found2 && name1.equals(name2)) {
			return -1;
		}
		else if (!found1 && !found2) {
			return -3;
		}
		else if (!found1) {
			return -1;
		}
		else if (!found2) {
			return -2;
		}
		else
			return 1;
	}	

	public String computeAngle(String a1, String b1, String a2, String b2){
		Double x1 = Double.parseDouble(a1);
		Double y1 = Double.parseDouble(b1);
		Double x2 = Double.parseDouble(a2);
		Double y2 = Double.parseDouble(b2);
		
		final double TWOPI = 6.2831853071795865;
	    final double RADICAL2 = 57.2957795130823209;
	    double theta = Math.atan2(x2 - x1, y1 - y2);
	    if (theta < 0.0)
	        theta += TWOPI;
	    double angle = RADICAL2 * theta;
	    
	    if (angle >= 22.5 && angle <= 67.5) 
	    		return "NorthEast";
	    else if (angle >= 67.5 && angle <= 112.5) 
    			return "East";
	    else if (angle >= 112.5 && angle <= 157.5) 
			return "SouthEast";
	    else if (angle >= 157.5 && angle <= 202.5) 
			return "South";
	    else if (angle >= 202.5 && angle <= 247.5) 
			return "SouthWest";
	    else if (angle >= 247.5 && angle <= 292.5) 
			return "West";
	    else if (angle >= 292.5 && angle <= 337.5) 
			return "NorthWest";
	    else
	    		return "North";
	}
	
	

}
