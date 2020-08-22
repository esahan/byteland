package com.egesahan.byteland.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.egesahan.byteland.utility.StringUtility;

/**
 * Class that receive Java Program Arguments and prepares input for BytelandCityStateMergeHandler
 * 
 * @author Ege Sahan
 * @version 0.0.1
 * @since 20-09-19
 *
 */
public class BytelandInputHandler implements InputHandler {

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer[]> receiveInput(String[] args)  {
		InputType inputType;
		String filePath;
		if (args != null && args.length > 0) {
			inputType = InputType.getByArgValue(args[0]);
			validateArguments(args, inputType);
			if(inputType == InputType.FILE_LOCAL) {
				filePath = args.length == 2 ? args[1] : inputType.getDefaultValue();
			}else {
				filePath = args[1];
			}
		} else {
			inputType = InputType.FILE_LOCAL;
			filePath = InputType.FILE_LOCAL.getDefaultValue();
		}

		try {
		return prepareCityStateInput(getReader(inputType, filePath));
		} catch (IOException e) {
			throw new IllegalArgumentException("There is a problem while reading file, please check if path and file is valid");
		}

	}
	
	private void validateArguments(String[] args, InputType inputType) {
		if (args.length > 2)
			throw new IllegalArgumentException("Invalid parameter count");		
		if (inputType == null)
			throw new IllegalArgumentException("Invalid input type parameter! Try \"FileLocal\" or \"FilePath\" ");
		if (inputType == InputType.FILE_PATH && (args.length != 2))
			throw new IllegalArgumentException("Incorrect parameter count for \"FilePath\" input type");
	}

	private Reader getReader(InputType inputType, String filePath) throws FileNotFoundException {
		Reader reader;
		if (inputType == InputType.FILE_LOCAL) {
			reader = getFileFromResources(filePath);
		} else {
			reader = new FileReader(new File(filePath));
		}
		return reader;
	}

	
	private Reader getFileFromResources(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream resource = classLoader.getResourceAsStream(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			return new InputStreamReader(resource);
		}
	}

	private List<Integer[]> prepareCityStateInput(Reader reader) throws IOException {
		try (BufferedReader br = new BufferedReader(reader)) {
			List<Integer[]> inputList = new ArrayList<>();
			int numberOfCities;
			String line = br.readLine();
			validateTestCaseCount(line);
			int testCases = Integer.parseInt(line);
			
			for (int i = 1; i <= testCases; i++) {
				line = br.readLine();
				validateNumberOfCities(line, i);
				numberOfCities = Integer.parseInt(line);
				line = br.readLine();
				validateRoadConnectionInput(line, numberOfCities, i);
				inputList.add(Arrays.asList(line.split(" ")).stream().map(Integer::valueOf).toArray(Integer[]::new));
			}
			return inputList;
		}
	}

	private void validateTestCaseCount(String line) {
		if (!StringUtility.isConsistOfDigits(line) || Integer.parseInt(line) >= 1000) {
			throw new IllegalArgumentException("Test Case Count is not valid, value : " + line);
		}
	}

	private void validateNumberOfCities(String line, int testCase) {
		if (!StringUtility.isConsistOfDigits(line) || Integer.parseInt(line) < 2 || Integer.parseInt(line) > 600)
			throw new IllegalArgumentException(
					"Number of cities value is not valid, test case:" + testCase + " value:" + line);
	}

	private void validateRoadConnectionInput(String line, int numberOfCities, int testCase) {
		if (line == null || line.split(" ").length != numberOfCities - 1)
			throw new IllegalArgumentException(
					"Number of cities do not match with road connection input, test case: " + testCase + " value:" + line);
		String[] split = line.split(" ");
		for (int i = 0; i < split.length; i++) {
			if (!StringUtility.isConsistOfDigits(split[i]) || Integer.parseInt(split[i]) > numberOfCities - 1) {
				throw new IllegalArgumentException(
						"Road connection input is not valid, test case: " + testCase + " value: " + line);
			}
		}
	}		

}
