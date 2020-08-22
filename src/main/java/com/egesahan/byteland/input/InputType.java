package com.egesahan.byteland.input;

/**
 * Input Types for Java Program Parameters
 * When new Input type will be declared it should be added here (Console etc.)
 * 
 * @author Ege Sahan
 * @version 0.0.1
 * @since 20-09-19
 *
 */
public enum InputType {
	FILE_LOCAL("FileLocal","Input.txt"),
	FILE_PATH("FilePath",null);
	
	private String argValue;
	private String defaultValue;
	
	private InputType(String argValue, String defaultValue) {
		this.argValue=argValue;
		this.defaultValue = defaultValue;
	}

	public String getArgValue() {
		return argValue;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public static InputType getByArgValue(String argValue) {
		for(InputType inputType : InputType.values()) {
			if(inputType.getArgValue().equals(argValue)) return inputType;
		}
		return null;
	}
}
