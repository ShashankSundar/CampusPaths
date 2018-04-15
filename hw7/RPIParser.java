package hw7;

import hw4.*;

import java.io.*;
import java.util.*;

public class RPIParser {
	
	public static void readNodeData(String filename, Map<String,ArrayList<String>> ids, Map<String,ArrayList<String>> names, 
			ArrayList<String> nodes, Map<String, Point> coordinates) 
    		throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        
        while ((line = reader.readLine()) != null) {
            int i = line.indexOf(",");
            if (i == -1) {
           	 throw new IOException("File "+filename+" not a .csv file.");
            }
            int j = line.indexOf(",", i+1);
            int k = line.indexOf(",", j+1);
            String building = line.substring(0,i);
            String id = line.substring(i+1,j);
            String x = line.substring(j+1,k);
            String y = line.substring(k+1);

            int check = 0;
            if (building.equals("")) {
            		building = new String("Intersection "+id);
            		check = 1;
            }
            
            if (check == 0) {
            		nodes.add(building);
            }
            
            Point temp = new Point(Integer.parseInt(x), Integer.parseInt(y));
            coordinates.put(building, temp);
            
            ArrayList<String> data1 = new ArrayList<>();
            data1.add(building);
            data1.add(x);
            data1.add(y);
            ids.put(id,data1);
            
            ArrayList<String> data2 = new ArrayList<>();
            data2.add(id);
            data2.add(x);
            data2.add(y);
            names.put(building,data2);
       }
	}
	
	public static void readEdgeData(String filename, ArrayList<String> edges) 
    		throws IOException {
		
		BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line = null;
        
        while ((line = reader.readLine()) != null) {
            int i = line.indexOf(",");
            if (i == -1) {
           	 throw new IOException("File "+filename+" not a .csv file.");
            }
            String id1 = line.substring(0,i);
            String id2 = line.substring(i+1);
            
            edges.add(id1);
            edges.add(id2);
       }
	}

}
