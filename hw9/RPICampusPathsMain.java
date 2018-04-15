package hw9;

import hw7.*;
import hw7.Point;
import javax.swing.*;

import hw4.Edge;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.*;

public class RPICampusPathsMain {
	//private instance variables
	private String nodefile = "src/hw7/data/RPI_map_data_Nodes.csv";
	private String edgefile = "src/hw7/data/RPI_map_data_Edges.csv";
	private String imageFile = "src/hw9/data/map3.png";
	
	private JFrame frame;
	private String[] buildList = {"1: Pittsburgh Building", "2: West Hall", "3: Carnegie Building", "4: Walker Laboratory", "5: Boiler House at Sage Avenue", 
			"6: Russell Sage Laboratory", "7: Troy Building", "8: North Hall", "9: E Complex", "10: Ricketts Building", "11: 87 Gymnasium", "12: Quadrangle Complex", 
			"13: Russell Sage Dining Hall", "14: CII", "15: Playhouse", "17: DCC", "18: Engineering Center", "19: Science Center", "20: Cogswell Laboratory", "21: MRC",
			"22: VCC", "23: Folsom Library", "24: Greene Building", "25: Lally Hall", "26: Amos Eaton Hall", "27: 41 Ninth Street", "28: Service Building", 
			"29: Blaw-Knox 1 & 2", "30: J Building", "31: H Building", "32: Alumni House", "33: 2021 Peoples Avenue", "34: Admissions", "35: Rensselaer Union", 
			"36: Public Safety", "37: Alumni Sports & Recreation Center", "38: Robison Swimming Pool", "39: Commons Dining Hall", "40: Crockett Hall", "41: Nason Hall",
			"42: Davison Hall", "43: Sharp Hall", "44: Nugent Hall", "45: Warren Hall", "46: Hall Hall", "47: Cary Hall", "48: Bray Hall", "49: Chapel and Cultural Center", 
			"50: Burdett Avenue Residence Hall", "51: 2144 Burdett Avenue", "52: Field House Houston", "53: Rensselaer Apartment Housing Project RAHP A Site", 
			"54: 200 Sunset Terrace", "55: Seismograph Laboratory", "57: Greenhouses and Grounds Barn", "58: LINAC Facility", "59: Stacwyck Apartments", "60: Radio Club", 
			"61: Bryckwyck", "62: Rensselaer Apartment Housing Project RAHP B Site", "65: Patroon Manor", "66: Colonie Apartments", "67: Academy Hall", 
			"68: Empire State Hall", "69: Beman Park Firehouse", "71: 133 Sunset Terrace", "72: Mueller Center", "73: Barton Hall", "74: CBIS", "75: Parking Garage", 
			"76: EMPAC", "77: Boiler House at 11th Street", "78: Winslow Building", "79: Louis Rubin Memorial Approach", "80: Java++ Cafe", "81: RPI Ambulance", 
			"85: Blitman Residence Commons", "86: Polytechnic Residence Commons", "89: East Campus Athletic Village Arena", "90: East Campus Athletic Village Stadium",
			"91: OGE", };
	private JComboBox<String> buildings;
	private JComboBox<String> buildings2;
	private JLabel label;

	private JPanel panel1;
	private JPanel panel2;
	private JTextArea rules;
	private JTextField to;
	
	private Paths ps;
	
	private Map<String, ArrayList<String>> ids;
	private Map<String, ArrayList<String>> names;
	private ArrayList<String> edges;
	private ArrayList<String> angles;
	private Map<String, Point> coordinates;
    private ArrayList<String> nodes;
    private BufferedImage img;
    private Image originalImg;
    private boolean clicked = false;
    String instructions = "Select a start and end point for your \ndesired path from the dropdown menus \nand press \"Find Path\" to calculate the path.\nFollowing each"
    		+ " calculation, press \n\"Reset\" to begin a new path search.";
    
    // Constructor and sets up frame so it doesnt need to be done again
	public RPICampusPathsMain() {			
		frame = new JFrame("Campus Paths");
		ps = new Paths();
		buildings = new JComboBox<>(buildList);
		buildings2 = new JComboBox<>(buildList);
		label = new JLabel();
		panel1 = new JPanel(new BorderLayout());
		panel2 = new JPanel(new FlowLayout());
		ids = new HashMap<>();
		names = new HashMap<>();
		edges = new ArrayList<>();
		angles = new ArrayList<>();
		coordinates = new HashMap<>();
		nodes = new ArrayList<>();
		buildings.setMaximumSize( buildings.getPreferredSize() );
		buildings2.setMaximumSize( buildings2.getPreferredSize() );
		
		Color defaultColor = UIManager.getColor ( "Panel.background" );
		rules = new JTextArea(instructions); rules.setBackground(defaultColor); rules.setEditable(false);	
		to = new JTextField("to"); to.setBackground(defaultColor); to.setEditable(false);
		
		panel2.add(buildings);
		panel2.add(to);
		panel2.add(buildings2);
		panel1.add(panel2, BorderLayout.NORTH);
		panel1.add(label);
		panel1.add(rules, BorderLayout.EAST);
		
		ps.createNewGraph(nodefile, edgefile, ids, names, edges, nodes, coordinates); 
		
		try {
            img = ImageIO.read(new File(imageFile));
        } catch (IOException e) {
        }
        originalImg = img.getScaledInstance(800, 754, Image.SCALE_SMOOTH); 
		
        //Create and set up the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel1);
        
        JButton b1 = new JButton( new AbstractAction("Find Path") {  
            @Override
            public void actionPerformed( ActionEvent e ) {
            	 if (!clicked) {
            		 clicked = true;
            		 showPath();
            	 }
            }
        });

        JButton b2 = new JButton( new AbstractAction("Reset") { 
            @Override
            public void actionPerformed( ActionEvent e ) {
            	  reset();
            	  clicked = false;
            }
        });

        panel2.add(b1);
	    panel2.add(b2); 
	}
	
	private void createAndShowGUI() {    
        label.setIcon( new ImageIcon(originalImg) );
        
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	private void reset() {
		buildings.setSelectedIndex(0);
		buildings2.setSelectedIndex(0);
		createAndShowGUI();
	}
	
	//find path and display new map
	private void showPath() {
		 String nameID1 = (String)buildings.getSelectedItem();
		 String nameID2 = (String)buildings2.getSelectedItem();
		 String name1 = nameID1.substring(nameID1.indexOf(":")+2);
		 String name2 = nameID2.substring(nameID2.indexOf(":")+2);
    	 
		 ArrayList<Edge<String, Double>> path = ps.findPath(name1, name2, ids, names, angles);
		 if (nameID1.equals(nameID2)) {
			 JOptionPane.showMessageDialog(frame, "Same building ("+nameID1+") selected", "ERROR", JOptionPane.ERROR_MESSAGE);
			 clicked = false;
			 return;
		 } 
		 else if (path.size() == 0) {
			 JOptionPane.showMessageDialog(frame, "There is no path from "+name1+" to "+name2+".", "No path found", JOptionPane.PLAIN_MESSAGE);
			 clicked = false;
			 return;
		 }
	     createImage(path, img);
	     Image img2 = img.getScaledInstance(800, 754, Image.SCALE_SMOOTH);
	     label.setIcon( new ImageIcon(img2) );
	}
	
	//Draw Path onto map
	private void createImage(ArrayList<Edge<String, Double>> path, BufferedImage bufferedImage) {
	    Graphics g = bufferedImage.getGraphics();  
	    Graphics2D g2 = (Graphics2D)g;
	
	    g2.setColor(Color.BLACK);
	    g2.setStroke(new BasicStroke(6));
	    g2.drawLine(coordinates.get(path.get(0).parent).x, coordinates.get(path.get(0).parent).y, 
				coordinates.get(path.get(1).child).x, coordinates.get(path.get(1).child).y);
	    g.setColor(Color.GREEN);
		g.fillOval(coordinates.get(path.get(0).parent).x-15,coordinates.get(path.get(0).parent).y-15,30,30);
		g2.setColor(Color.BLACK);
		
	    for (int i = 2; i < path.size(); i++) {
	    		g2.drawLine(coordinates.get(path.get(i).parent).x, coordinates.get(path.get(i).parent).y, 
	    				coordinates.get(path.get(i).child).x, coordinates.get(path.get(i).child).y);
	    }
	    
	    g.setColor(Color.RED);
	    g.fillOval(coordinates.get(path.get(path.size()-1).child).x-15,coordinates.get(path.get(path.size()-1).child).y-15,30,30);
	    g.dispose();  
	}
	
    public static void main(String[] args) {
    		RPICampusPathsMain run = new RPICampusPathsMain();
    		run.createAndShowGUI();
    }

}
