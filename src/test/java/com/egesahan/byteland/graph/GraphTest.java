package com.egesahan.byteland.graph;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.egesahan.byteland.input.BytelandInputHandlerTest;



public class GraphTest {
	
	private static final Logger logger = LoggerFactory.getLogger(BytelandInputHandlerTest.class);
	
	static Graph<Integer> graph;
	
	@BeforeAll
	static void setup() {
		logger.info("GraphTest Class Started");
		/**
		 *
		 *           1
		 *          /  \ \ \ \
		 *         2    3 4 5 0
		 *        / \          \
		 *       7   8          6
		 *			  \
		 *			   9
		 *			  / \
		 *			 10  11
		 */
		
		Integer[] input = {0,1,1,1,1,0,2,2,8,9,9};		
		graph = new Graph<>();
		for (int i = 0; i < input.length; i++) {
			// index + 1 and value of i are adjacent vertices
			graph.addVertex(i + 1);
			graph.addVertex(input[i]);
			graph.addEdgeConnection(i + 1, input[i]);
		}
		
	}
	
	@Test
	void adjacentVerticesCreationTest() {
		List<Integer> adjacentVerticesOfVertex = graph.getAdjacentVerticesOfVertex(1);
		Optional<Integer> findAny = adjacentVerticesOfVertex.stream().filter(a -> !in(a, 2,3,4,5,0)).findAny();
		assertFalse(findAny.isPresent());
		List<Integer> adjacentVertices = adjacentVerticesOfVertex.stream().filter(a -> in(a, 2,3,4,5,0)).collect(Collectors.toList());
		assertTrue(adjacentVertices.size() == 5);
	}
	
	@Test
	void graphDFSTest() {
		Set<Integer> depthFirstSearchTraversalVertexSet = Graph.depthFirstSearchTraversal(graph, 1);		
		assertTrue(depthFirstSearchTraversalVertexSet.size()==graph.getAdjacentVertices().size());
	}
	
	@Test
	void addAndRemoveVertexTest() {
		graph.addVertex(12);
		graph.addEdgeConnection(11, 12);
		List<Integer> adjacentVerticesOfVertex = graph.getAdjacentVerticesOfVertex(11);
		assertTrue(adjacentVerticesOfVertex.contains(12));
		graph.removeVertex(12);
		graph.removeEdgeConnection(11, 12);
		adjacentVerticesOfVertex = graph.getAdjacentVerticesOfVertex(11);
		assertFalse(adjacentVerticesOfVertex.contains(12));
	}
	
	

	
	@SafeVarargs
	public static <T> boolean in(T value, T... list) {
	    for (T item : list) {
	        if (value.equals(item))
	            return true;
	    }
	    return false;
	}


}
