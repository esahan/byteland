package com.egesahan.byteland.handler;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class BytelandCityStateMergeHandlerTest {
	
	static List<TestCase> testcases;
	
	@BeforeAll
	static void setup() {
		BytelandCityStateMergeHandlerTest test = new BytelandCityStateMergeHandlerTest();
		testcases = new ArrayList<>();
		testcases.add(test.new TestCase(new Integer[] {0}, 1));
		testcases.add(test.new TestCase(new Integer[] {0,1,2}, 2));
		testcases.add(test.new TestCase(new Integer[] {0,1,2,0,0,3,3}, 4));
		testcases.add(test.new TestCase(new Integer[] {0,1,1,1,1,0,2,2}, 5));
		testcases.add(test.new TestCase(new Integer[] {0,1,2,0,0,3,3,4,4,11,13,5,12}, 5));
		testcases.add(test.new TestCase(new Integer[] {0,1,2,3,4,5,6}, 3));
		testcases.add(test.new TestCase(new Integer[] {0,0,2,2,3,4,4,3,8,0,10,11}, 5));
		testcases.add(test.new TestCase(new Integer[] {0,1,8,1,1,0,2,2}, 4));
		testcases.add(test.new TestCase(new Integer[] {0,0,1,3,3,5,6,6,2,2,2,0,12,12,14,14,16}, 5));	
	}
	
	@Test
	void cityStateMergeProcessTest() {		
		for (int i = 0; i < testcases.size(); i++) {
			BytelandCityStateMergeHandler handler = new BytelandCityStateMergeHandler(testcases.get(i).testData);
			handler.calculateMergingSteps();
			assertTrue(handler.getCounter()==testcases.get(i).expectedResult);
		}
	}
	
	
	@Test
	@Disabled 
	void cityStateMergeProcessTestRepetitive() { //NOSONAR
		for (int j = 0; j < 10000; j++) {
			for (int i = 0; i < testcases.size(); i++) {
				BytelandCityStateMergeHandler handler = new BytelandCityStateMergeHandler(testcases.get(i).testData);
				handler.calculateMergingSteps();
				assertTrue(handler.getCounter()==testcases.get(i).expectedResult);
			}
		}

	}
	
	
	 private class TestCase {		
		Integer[] testData;
		Integer expectedResult;
		
		TestCase(Integer[] testData , Integer expectedResult){
			this.testData = testData;
			this.expectedResult = expectedResult;
		}
	}

}
