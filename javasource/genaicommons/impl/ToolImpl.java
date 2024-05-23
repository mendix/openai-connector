package genaicommons.impl;

import java.util.regex.Pattern;

public class ToolImpl {
		
	protected static void validateToolName(String toolName) throws Exception {
	    // Name must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64
	    String pattern = "^[a-zA-Z0-9_-]{1,64}$";
	    Pattern regex = Pattern.compile(pattern);

	    // Check if the input string matches the pattern
	    if(!regex.matcher(toolName).matches()) {
	    	throw new IllegalArgumentException("Tool Name is not valid. Name must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.");
	    }
	}
	
	
}