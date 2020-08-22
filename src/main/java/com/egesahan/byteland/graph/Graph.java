package com.egesahan.byteland.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adjacency List Graph Representation
 * 
 * @author Ege Sahan
 * @version 0.0.1
 * @since 20-09-19
 *
 */
public class Graph<T extends Serializable> {
	private Map<Vertex, List<Vertex>> adjacentVertices;

	public Graph() {
		this.adjacentVertices = new HashMap<>();
	}
 
	/**
	 * Returns Graph Representation as a map
	 * @return 
	 */
	public Map<Vertex, List<Vertex>> getAdjacentVertices() {
		return adjacentVertices;
	}

	public void setAdjacentVertices(Map<Vertex, List<Vertex>> adjacentVertices) {
		this.adjacentVertices = adjacentVertices;
	}

	/**
	 * Returns list of adjacent vertices for given vertex (vertex's id)
	 * @param id
	 * @return List<T> returns list of vertices (vertices' id's)
	 */
	public List<T> getAdjacentVerticesOfVertex(T id) {
		return adjacentVertices.get(new Vertex(id)).stream().map(Vertex::getId).collect(Collectors.toList());
	}	

	/**
	 * Add vertex to graph if it is absent
	 * @param id (vertex's id)
	 */
	public void addVertex(T id) {
		adjacentVertices.putIfAbsent(new Vertex(id), new ArrayList<Vertex>());
	}

	/**
	 * Removes vertex from graph
	 * </br>
	 * This also removes connections that vertex holds
	 * @param id (vertex's id)
	 */
	public void removeVertex(T id) {
		Vertex vertex = new Vertex(id);
		adjacentVertices.values().stream().forEach(adjList -> adjList.remove(vertex));
		adjacentVertices.remove(vertex);
	}

	/**
	 * Connects given vertices
	 * @param id1 first vertex (vertex's id)
	 * @param id2 second vertex (vertex's id)
	 */
	public void addEdgeConnection(T id1, T id2) {
		Vertex vertex1 = new Vertex(id1);
		Vertex vertex2 = new Vertex(id2);
		if (adjacentVertices.containsKey(vertex1) && adjacentVertices.containsKey(vertex2)) {
			adjacentVertices.get(vertex1).add(vertex2);
			adjacentVertices.get(vertex2).add(vertex1);
		} else {
			throw new GraphValidationException("Vertices are absent.");
		}
	}

	/**
	 * Removes connection between given vertices
	 * @param id1 first vertex (vertex's id)
	 * @param id2 second vertex (vertex's id)
	 */
	public void removeEdgeConnection(T id1, T id2) {
		Vertex vertex1 = new Vertex(id1);
		Vertex vertex2 = new Vertex(id2);
		List<Vertex> vertex1AdjacentVertices = adjacentVertices.get(vertex1);
		List<Vertex> vertex2AdjacentVertices = adjacentVertices.get(vertex2);
		if (vertex1AdjacentVertices != null && !vertex1AdjacentVertices.isEmpty()) {
			vertex1AdjacentVertices.remove(vertex2);
		}
		if (vertex2AdjacentVertices != null && !vertex2AdjacentVertices.isEmpty()) {
			vertex2AdjacentVertices.remove(vertex1);
		}
	}	


    /**
     * Implementation of DFS 
     * @param <G> Graph Representation generic value (Vertex's id type)
     * @param graph
     * @param root
     * @return Set of visited vertices
     */
	public static <G extends Serializable> Set<G> depthFirstSearchTraversal(Graph<G> graph, G root) {
		Set<G> visited = new LinkedHashSet<>();
		Deque<G> stack = new LinkedList<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			G vertexId = stack.pop();
			if (!visited.contains(vertexId)) {
				visited.add(vertexId);
				for (G id : graph.getAdjacentVerticesOfVertex(vertexId)) {
					stack.push(id);
				}
			}
		}
		return visited;
	}

	public class Vertex {

		private T id;

		Vertex(T id) {
			this.id = id;
		}

		public T getId() {
			return id;
		}

		public void setId(T id) {
			this.id = id;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vertex other = (Vertex) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id)) {
				return false;
			}
			return true;
		}

		private Graph<T> getEnclosingInstance() {
			return Graph.this;
		}


	}
}
