package hw7;

import java.util.*;

public class CampusPaths {
	
	public static void main(String[] arg) {

		String nodefile = "hw7/data/RPI_map_data_Nodes.csv";
		String edgefile = "hw7/data/RPI_map_data_Edges.csv";
		
		Paths ps = new Paths();
		Calculations cl = new Calculations();
		PathsView pv = new PathsView();
		
		Map<String, ArrayList<String>> ids = new HashMap<>();
		Map<String, ArrayList<String>> names = new HashMap<>();
		ArrayList<String> edges = new ArrayList<>();
		ArrayList<String> angles = new ArrayList<>();
		ArrayList<String> nodes = new ArrayList<>();
		Map<String, Point> coordinates = new HashMap<>();
		ps.createNewGraph(nodefile, edgefile, ids, names, edges, nodes, coordinates); 
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			String input = scan.nextLine();
			
			if (input.equals("b")){
				pv.output(ps.listNodes(nodes, names));
			}
			else if (input.equals("r")) {
				pv.setup(1);
				String nameId1 = scan.nextLine();
				pv.setup(2);
				String nameId2 = scan.nextLine();
				
				int check = cl.checkExists(nameId1, nameId2, ids, names, edges, nodes);
				if (check == 1) {
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
					pv.output(ps.findPath(name1, name2, ids, names, angles), angles, name1, name2);
				}
				else if (check == -3) {
					pv.unknownBuilding("Unknown building: ["+nameId1+"]");
					pv.unknownBuilding("Unknown building: ["+nameId2+"]");
				}
				else if (check == -1) {
					pv.unknownBuilding("Unknown building: ["+nameId1+"]");
				}
				else if (check == -2) {
					pv.unknownBuilding("Unknown building: ["+nameId2+"]");
				}
			}
			else if (input.equals("q")) {
				scan.close();
				return;
			}
			else if (input.equals("m")) {
				pv.options();
			}
			else
				pv.output();
		}
		
	}
}
