package genaicommons.impl;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IDataType;

import genaicommons.proxies.Function;
import genaicommons.proxies.Tool;
import genaicommons.proxies.ToolCollection;

public class FunctionImpl {
	
	public static void validateFunctionInput(String functionMicroflow, String toolName) throws Exception {
		requireNonNull(functionMicroflow, "Function Microflow is required.");
		requireNonNull(toolName, "Tool Name is required.");
		validateFunctionMicroflow(functionMicroflow);
	}
	
	public static Function createFunction(IContext context, String functionMicroflow, String functionName, String functionDescription, ToolCollection toolCollection) throws CoreException {
		Function function = new Function(context);
		function.setMicroflow(functionMicroflow);
		function.setName(functionName);	
		function.setDescription(functionDescription); //Optional parameter
		List<Tool> ToolList = toolCollection.getToolCollection_Tool();
		ToolList.add(function);
		toolCollection.setToolCollection_Tool(ToolList); 
		return function;
	}
	
	// to be used later when manipulating JSON and actually calling the function
	public static String getFirstInputParamName(String functionMicroflow) {
		Map<String, IDataType> inputParameters = Core.getInputParameters(functionMicroflow);
		if(inputParameters != null && !inputParameters.entrySet().isEmpty()) {
			return inputParameters.entrySet().iterator().next().getKey();
		} else {
			return null;
		}
	}
	
	private static void validateFunctionMicroflow(String functionMicroflow) throws Exception {
		Set<String> microflowNames = Core.getMicroflowNames();
		if(!microflowNames.contains(functionMicroflow)) {
			throw new IllegalArgumentException("Function Microflow " + functionMicroflow + " does not exists.");
		}
		
		Map<String, IDataType> inputParameters = Core.getInputParameters(functionMicroflow);
		if (inputParameters != null && inputParameters.size() > 1) {
			throw new IllegalArgumentException("Function Microflow " + functionMicroflow + " should only have one input parameter of type String.");
		}
		
		if(inputParameters != null && !inputParameters.entrySet().isEmpty()
				&& IDataType.DataTypeEnum.String.equals(inputParameters.entrySet().iterator().next().getValue().getType()) == false) {
			throw new IllegalArgumentException("Function Microflow " + functionMicroflow + " should have an input parameter of type String.");			
		}

		if(Core.getReturnType(functionMicroflow) == null || IDataType.DataTypeEnum.String.equals(Core.getReturnType(functionMicroflow).getType()) == false) {
			throw new IllegalArgumentException("Function Microflow " + functionMicroflow + " should have a String return value.");		
		}
		
	}

}
