package hw6.test;

import org.junit.*;

import hw6.*;

public class MarvelPaths2Tests {
	MarvelPaths2 mp;
	
	String test1 = "hw5/data/test.csv";
	String test2 = "hw5/data/test2.csv";
	String test3 = "hw5/data/test3.csv";
	String test4 = "hw5/data/test4.csv";
	String node1 = "FROST, CARMILLA";
	String node2 = "KILLRAVEN/JONATHAN R";
	String node3 = "M'SHULLA";
	String node4 = "OLD SKULL";
	String node5 = "3-D MAN/CHARLES CHAN";
	String node6 = "JONES, RICHARD MILHO";
	String node7 = "Shashank";
	String node8 = "Trump";
	
	
	@Test 
	public void constructGraph() {
		mp = new MarvelPaths2();
		mp.createNewGraph(test1);
	}
	
	@Test 
	public void correctPath1() {
		mp = new MarvelPaths2();
		mp.createNewGraph(test4);
		mp.findPath(node1, node6);
	}
	
	@Test 
	public void noPath() {
		mp = new MarvelPaths2();
		mp.createNewGraph(test3);
		mp.findPath(node1, node2);
	}
	
	@Test 
	public void unknownChar1() {
		mp = new MarvelPaths2();
		mp.createNewGraph(test1);
		mp.findPath(node7, node2);
	}
	
	@Test 
	public void unknownChar2() {
		mp = new MarvelPaths2();
		mp.createNewGraph(test1);
		mp.findPath(node1, node8);
	}
	
	@Test 
	public void unknownCharBoth() {
		mp = new MarvelPaths2();
		mp.createNewGraph(test1);
		mp.findPath(node7, node8);
	}
	
}
