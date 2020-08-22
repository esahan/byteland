package com.egesahan.byteland.input;

/**
 * Interface for input handling
 * Different input handlers should implement this interface
 * 
 * @author Ege Sahan
 * @version 0.0.1
 * @since 20-09-19
 *
 */
public interface InputHandler {

	<T> T receiveInput(String[] args);
	
}
