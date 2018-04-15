package hw4.test;

import hw4.*;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the GraphWrapper class.
 * <p>
 */

public final class GraphWrapperTest {
	GraphWrapper<String, String> G1 = new GraphWrapper<>();
	
	@Test
	public void testConstructor() {
		new GraphWrapper<String, String>();
	}
	
	// Test addNode()
	@Test
	public void testaddNode() {
		G1.addNode("A");
		G1.addNode("B");
		G1.addNode("C");
	}
	
	// Test addEdge()
	@Test
	public void testaddEdge() {
		G1.addNode("A");
		G1.addNode("B");
		G1.addNode("C");
		
		G1.addEdge("A", "B", "1");
		G1.addEdge("B", "B", "1");
		G1.addEdge("A", "B", "2");
	}
		
	// Test listNodes()
	@Test
	public void testlistNodes() {
		G1.addNode("B");
		G1.addNode("A");
		G1.addNode("C");
		
		Iterator<String> itr = G1.listNodes();
				
		assertEquals(itr.next(), "A");
		assertEquals(itr.next(), "B");
		assertEquals(itr.next(), "C");
	}
	
	// Test listChildren()
	@Test
	public void testlistChildren() {
		G1.addNode("B");
		G1.addNode("A");
		G1.addNode("C");
		
		G1.addEdge("A", "B", "4");
		G1.addEdge("A", "B", "1");
		G1.addEdge("B", "C", "3");
		G1.addEdge("C", "A", "2");
			
		Iterator<String> itrA = G1.listChildren("A");
		Iterator<String> itrB = G1.listChildren("B");
		Iterator<String> itrC = G1.listChildren("C");
					
		assertEquals(itrA.next(), "B(1)");
		assertEquals(itrA.next(), "B(4)");
		assertEquals(itrB.next(), "C(3)");
		assertEquals(itrC.next(), "A(2)");
	}
	
	// Test reflexive edges
	@Test
	public void testReflexiveEdges() {
		G1.addNode("A");
		G1.addNode("B");
		
		G1.addEdge("A", "A", "1");
		G1.addEdge("A", "A", "3");
		G1.addEdge("B", "B", "2");
		
		Iterator<String> itrA = G1.listChildren("A");
		Iterator<String> itrB = G1.listChildren("B");
		
		assertEquals(itrA.next(), "A(1)");
		assertEquals(itrA.next(), "A(3)");
		assertEquals(itrB.next(), "B(2)");
	}
	
	// Test identical edges
	@Test
	public void testIdenticalEdges() {
		G1.addNode("A");
		G1.addNode("B");
		
		G1.addEdge("A", "A", "1");
		G1.addEdge("A", "A", "1");
		G1.addEdge("A", "B", "2");
		G1.addEdge("A", "B", "2");
		G1.addEdge("B", "A", "3");
		G1.addEdge("B", "A", "3");
		G1.addEdge("B", "A", "1");
		
		Iterator<String> itrA = G1.listChildren("A");
		Iterator<String> itrB = G1.listChildren("B");
		
		assertEquals(itrA.next(), "A(1)");
		assertEquals(itrA.next(), "A(1)");
		assertEquals(itrA.next(), "B(2)");
		assertEquals(itrA.next(), "B(2)");
		assertEquals(itrB.next(), "A(1)");
		assertEquals(itrB.next(), "A(3)");
		assertEquals(itrB.next(), "A(3)");
	}
}
