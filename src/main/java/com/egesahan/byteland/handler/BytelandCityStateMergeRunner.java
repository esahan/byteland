package com.egesahan.byteland.handler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Runner class for Byteland CityState Merge Algorithm
 * 
 * @author Ege Sahan
 * @version 0.0.1
 * @since 20-09-19
 *
 */
public class BytelandCityStateMergeRunner implements RunnerObserver{
	
	private static final Logger logger = LoggerFactory.getLogger(BytelandCityStateMergeRunner.class);
	
	private static final int DEFAULT_THREAD_SIZE = 10;
	private static final ConcurrentMap<Integer, Integer> runnerOutputMap = new ConcurrentSkipListMap<>();
	
	private List<Integer[]> inputList;
	
	/**
	 * Default Constructor, Receives input list
	 * @param inputList
	 */
	public BytelandCityStateMergeRunner(List<Integer[]> inputList) {
		this.setInputList(inputList);
	}
	
	/**
	 * Run method splits tests cases and pass them to executor service
	 * </br>
	 * Default pool size is a constant, however if there is less test cases than default pool size then pool size will be # of test case
	 */
	public void run() {		
		int threadPoolSize = getInputList().size() > DEFAULT_THREAD_SIZE ? DEFAULT_THREAD_SIZE : getInputList().size();		
		ExecutorService executor = Executors.newFixedThreadPool(threadPoolSize);
		
		int testCaseNumber = 1;
		for (Integer[] input : getInputList()) {			
			BytelandCityStateMergeHandler mergeHandler = new BytelandCityStateMergeHandler(input, testCaseNumber, Collections.singletonList(this));
			executor.execute(mergeHandler);
			testCaseNumber++;		
		}
		executor.shutdown();
        while (!executor.isTerminated());        
        writeOutput();
	}
	
	public void clearRunnerOutputMap() {
		runnerOutputMap.clear();
	}
	

	public List<Integer[]> getInputList() {
		return inputList;
	}

	private void setInputList(List<Integer[]> inputList) {
		this.inputList = inputList;
	}

	/**
	 * Observer method for merge handlers
	 */
	@Override
	public void update(Object o) {
		BytelandCityStateMergeHandler handler = (BytelandCityStateMergeHandler)o;
		runnerOutputMap.put(handler.getTestCaseNumber(), handler.getCounter());
	}
	
	
	private void writeOutput() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("Output.txt"))) {
			for (Integer value : runnerOutputMap.values()) {
				writer.write(value.toString());
				writer.newLine();
			}
			writer.flush();
		} catch (IOException e) {
			logger.warn("There had been a problem while writing output to Output.txt");
		}
		
		runnerOutputMap.values().forEach(System.out::println);//NOSONAR
	}

}
