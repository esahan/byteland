package com.egesahan.byteland.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.egesahan.byteland.graph.Graph;
import com.egesahan.byteland.graph.GraphValidationException;

/**
 * Class that holds business logic for Byteland CityState Merge Algorithm
 * 
 * @author Ege Sahan
 * @version 0.0.1
 * @since 20-09-19
 *
 */
public class BytelandCityStateMergeHandler implements Runnable {

	private Integer[] input;
	private int counter;
	private Integer testCaseNumber;
	private List<RunnerObserver> observerList = new ArrayList<>();

	/**
	 * Constructor for single execution
	 * 
	 * @param input
	 */
	public BytelandCityStateMergeHandler(Integer[] input) {
		if (input == null || input.length == 0)
			throw new IllegalArgumentException();
		this.input = input;
		this.counter = 0;
	}

	/**
	 * Constructor for using with MergeRunner
	 * 
	 * @param input
	 * @param testCaseNumber
	 */
	public BytelandCityStateMergeHandler(Integer[] input, Integer testCaseNumber) {
		if (input == null || input.length == 0 || testCaseNumber == null)
			throw new IllegalArgumentException();
		this.input = input;
		this.counter = 0;
		this.testCaseNumber = testCaseNumber;
	}

	/**
	 * Constructor for using with RunnerObserver If you want to use observer pattern
	 * with runner class you should use this
	 * 
	 * @param input
	 * @param testCaseNumber
	 * @param observerList
	 */
	public BytelandCityStateMergeHandler(Integer[] input, Integer testCaseNumber, List<RunnerObserver> observerList) {
		if (input == null || input.length == 0 || testCaseNumber == null || observerList == null
				|| observerList.isEmpty())
			throw new IllegalArgumentException();
		this.input = input;
		this.counter = 0;
		this.testCaseNumber = testCaseNumber;
		this.observerList = observerList;
	}

	public int calculateMergingSteps() {
		this.counter = 0;
		mergeCityStates(createGraph(input));
		return counter;
	}

	/**
	 * Creates graph structure of input data This method validates for if given
	 * input represents a connected graph There is no need to check for cycle here
	 * because input consists 1 less edges than nodes
	 * 
	 * @param input
	 * @return
	 */
	private Graph<Integer> createGraph(Integer[] input) {
		Graph<Integer> graph = new Graph<>();
		for (int i = 0; i < input.length; i++) {
			// index + 1 and value of i are adjacent vertices
			graph.addVertex(i + 1);
			graph.addVertex(input[i]);
			graph.addEdgeConnection(i + 1, input[i]);
		}
		if ((input.length + 1) != Graph.depthFirstSearchTraversal(graph, 1).size()) {
			throw new GraphValidationException("Graph is not connected for input: " + Arrays.asList(input).stream()
					.map(String::valueOf).reduce("", (partialStr, element) -> partialStr + " " + element));
		}
		return graph;
	}

	/**
	 * Main method for merging city states process Country snapshot after every
	 * iteration can be obtainable here
	 * 
	 * @param graph
	 */
	private void mergeCityStates(Graph<Integer> graph) {
		while (graph.getAdjacentVertices().size() != 1) {
			processMergeStep(graph, 1);
			counter++;
		}
	}

	public void processMergeStep(Graph<Integer> graph, Integer root) {
		Set<Integer> used = new LinkedHashSet<>();
		Deque<Integer> stack = new LinkedList<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Integer headElementId = stack.peek();
			List<Integer> mergeAbleNodes = new ArrayList<>();
			List<Integer> adjacentVerticesOfHead = graph.getAdjacentVerticesOfVertex(headElementId);			
			adjacentVerticesOfHead.forEach(id -> {if(!used.contains(id)) mergeAbleNodes.add(id);});
			if (mergeAbleNodes.isEmpty()) {
				stack.remove(headElementId);
				continue;
			}
			if (mergeAbleNodes.size() == 1 && stack.contains(mergeAbleNodes.get(0))) {
				// Merge process
				Integer mergeVertexId = mergeAbleNodes.get(0);
				used.add(headElementId);
				used.add(mergeVertexId);
				stack.remove(headElementId);
				stack.remove(mergeVertexId);
				graph.removeVertex(headElementId);
				adjacentVerticesOfHead.stream().filter(a -> !a.equals(mergeVertexId))
						.forEach(id -> graph.addEdgeConnection(id, mergeVertexId));

			} else {
				mergeAbleNodes.forEach(node -> {if (!stack.contains(node)) stack.push(node);});
			}
		}
	}

	public int getCounter() {
		return counter;
	}

	public Integer getTestCaseNumber() {
		return testCaseNumber;
	}

	/**
	 * Implementation of Runnable interface, Purpose of this implementation is for to
	 * be used with runner class only. Calls observers after merging is done
	 */
	@Override
	public void run() {
		calculateMergingSteps();
		for (RunnerObserver observer : observerList) {
			observer.update(this);
		}
	}

}
