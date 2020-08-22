package com.egesahan.byteland.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BytelandInputHandlerTest {
	
	private static final Logger logger = LoggerFactory.getLogger(BytelandInputHandlerTest.class);
	
	
	@Test
	void receiveInputWithoutParameterTest() {
		String[] programParameters =null; 
	    BytelandInputHandler inputHandler = new BytelandInputHandler();
	    List<Integer[]> receiveInput = inputHandler.receiveInput(programParameters);
	    assertNotNull(receiveInput);
	}
	
	
	@Test
	void receiveInputWithInputTypeFileLocalTest() throws IOException {
		String fileName = "Input2.txt";
		InputType type = InputType.FILE_LOCAL;
		String[] programParameters = {type.getArgValue(),fileName};		
		Reader fileFromResources = getFileFromResources(fileName);		
		assumeTrue(fileFromResources !=null);		
		printFile(fileFromResources);				
	    BytelandInputHandler inputHandler = new BytelandInputHandler();
	    List<Integer[]> receiveInput = inputHandler.receiveInput(programParameters);
	    assertNotNull(receiveInput);
	}
	
	@Test
	void receiveInputParameterCountTest(){
		String fileName = "Input2.txt";
		InputType type = InputType.FILE_LOCAL;
		String[] programParameters = {type.getArgValue(),fileName,"thirdParam"};		
		BytelandInputHandler inputHandler = new BytelandInputHandler();		
		assertThrows(IllegalArgumentException.class, () -> inputHandler.receiveInput(programParameters));		
	}
	
	@Test
	void receiveInputTestCaseValidationTest(){
		String fileName = "Input-test-exception.txt";
		InputType type = InputType.FILE_LOCAL;
		String[] programParameters = {type.getArgValue(),fileName};		
		BytelandInputHandler inputHandler = new BytelandInputHandler();		
		assertThrows(IllegalArgumentException.class, () -> inputHandler.receiveInput(programParameters));		
	}
	
	@Test
	void receiveInputCityStateCountTest(){
		String fileName = "Input-test-exception2.txt";
		InputType type = InputType.FILE_LOCAL;
		String[] programParameters = {type.getArgValue(),fileName};		
		BytelandInputHandler inputHandler = new BytelandInputHandler();
		assertThrows(IllegalArgumentException.class, () -> inputHandler.receiveInput(programParameters));
	}
	
	@Test
	void receiveInputNonDigitInputTest() {
		String fileName = "Input-test-exception3.txt";
		InputType type = InputType.FILE_LOCAL;
		String[] programParameters = {type.getArgValue(),fileName};		
		BytelandInputHandler inputHandler = new BytelandInputHandler();		
		assertThrows(IllegalArgumentException.class, () -> inputHandler.receiveInput(programParameters));		
	}
	
	
	
	private Reader getFileFromResources(String fileName) {
		ClassLoader classLoader = getClass().getClassLoader();
		InputStream resource = classLoader.getResourceAsStream(fileName);
		if (resource == null) {
			return null;
		} else {
			return new InputStreamReader(resource);
		}
	}
	

    private static void printFile(Reader reader) throws IOException{
        try (BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
            	logger.info(line);
            }
        }
    }

}
