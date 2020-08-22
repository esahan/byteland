package com.egesahan.byteland.utility;

/**
 * String Utility Class
 * 
 * @author Ege Sahan
 * @version 0.0.1
 * @since 20-09-19
 *
 */
public final class StringUtility {
	
	private StringUtility() {
		
	}

	public static boolean isConsistOfDigits(String str) {
		if(str==null || str.isEmpty()) return false;
		for (char c : str.toCharArray()) {
			if (!Character.isDigit(c))
				return false;
		}
		return true;
	}

	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}
	
	
}
