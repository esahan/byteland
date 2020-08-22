package com.egesahan.byteland;

import java.util.List;

import com.egesahan.byteland.handler.BytelandCityStateMergeRunner;
import com.egesahan.byteland.input.BytelandInputHandler;

/**
 * Class that holds main method of byteland application
 * 
 * @author Ege Sahan
 * @version 0.0.1
 * @since 20-09-19
 *
 */
public class Application {

	public static void main(String[] args){
		BytelandInputHandler inputHandler = new BytelandInputHandler();
		List<Integer[]> inputList = inputHandler.receiveInput(args);
		BytelandCityStateMergeRunner runner = new BytelandCityStateMergeRunner(inputList);
		runner.run();
	}

}
