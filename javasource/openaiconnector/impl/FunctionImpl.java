package openaiconnector.impl;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IDataType;

import openaiconnector.proxies.Function;
import openaiconnector.proxies.FunctionCollection;

public class FunctionImpl {
	
	public static void validateInput(String functionMicroflow, String functionName) throws Exception {
		requireNonNull(functionMicroflow, "FunctionMicroflow is required.");
		requireNonNull(functionName, "FunctionName is required.");
		validateFunctionName(functionName);
		validateFunctionMicroflow(functionMicroflow);
	}
		
	private static void validateFunctionName(String functionName) throws Exception {
	    // Name must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64
	    String pattern = "^[a-zA-Z0-9_-]{1,64}$";
	    Pattern regex = Pattern.compile(pattern);

	    // Check if the input string matches the pattern
	    if(!regex.matcher(functionName).matches()) {
	    	throw new IllegalArgumentException("Function Name is not valid. Name must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.");
	    }
	}
	
	public static void validateFunctionMicroflow(String functionMicroflow) throws Exception {
		Set<String> microflowNames = Core.getMicroflowNames();
		if(!microflowNames.contains(functionMicroflow)) {
			throw new IllegalArgumentException("FunctionMicroflow " + functionMicroflow + " does not exists.");
		}
		
		Map<String, IDataType> inputParameters = Core.getInputParameters(functionMicroflow);
		if (inputParameters != null && inputParameters.size() > 1) {
			throw new IllegalArgumentException("FunctionMicroflow " + functionMicroflow + " should only have one input parameter of type String.");
		}
		
		if(inputParameters != null && !inputParameters.entrySet().isEmpty()
				&& IDataType.DataTypeEnum.String.equals(inputParameters.entrySet().iterator().next().getValue().getType()) == false) {
			throw new IllegalArgumentException("FunctionMicroflow " + functionMicroflow + " should have an input parameter of type String.");			
		}

		if(Core.getReturnType(functionMicroflow) == null || IDataType.DataTypeEnum.String.equals(Core.getReturnType(functionMicroflow).getType()) == false) {
			throw new IllegalArgumentException("FunctionMicroflow " + functionMicroflow + " should have a String return value.");		
		}
	}
	
	public static Function createFunction(IContext context, String functionMicroflow, String functionName, String functionDescription, FunctionCollection functionCollection) {
		Function function = new Function(context);
		function.setFunctionMicroflow(functionMicroflow);
		function.setName(functionName);	
		function.setDescription(functionDescription); //Optional parameter
		function.setFunction_FunctionCollection(functionCollection);
		return function;
	}
	
	public static String getFirstInputParamName(String functionMicroflow) {
		Map<String, IDataType> inputParameters = Core.getInputParameters(functionMicroflow);
		if(inputParameters != null && !inputParameters.entrySet().isEmpty()) {
			return inputParameters.entrySet().iterator().next().getKey();
		} else {
			return null;
		}
	}
}