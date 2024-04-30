// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package openaiconnector.actions;

import static java.util.Objects.requireNonNull;
import java.io.IOException;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import openaiconnector.impl.FunctionImpl;
import openaiconnector.impl.MxLogger;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Function_ExecuteFunctionMicroflow extends CustomJavaAction<java.lang.String>
{
	private IMendixObject __FunctionRequest;
	private openaiconnector.proxies.FunctionRequest FunctionRequest;
	private java.lang.String Arguments;

	public Function_ExecuteFunctionMicroflow(IContext context, IMendixObject FunctionRequest, java.lang.String Arguments)
	{
		super(context);
		this.__FunctionRequest = FunctionRequest;
		this.Arguments = Arguments;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		this.FunctionRequest = this.__FunctionRequest == null ? null : openaiconnector.proxies.FunctionRequest.initialize(getContext(), __FunctionRequest);

		// BEGIN USER CODE
		try {
			requireNonNull(FunctionRequest, "Function is required.");
			requireNonNull(FunctionRequest.getFunctionMicroflow(), "Function has no FunctionMicroflow.");
			FunctionImpl.validateFunctionMicroflow(FunctionRequest.getFunctionMicroflow());
			
			return executeFunctionMicroflow();
		
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "Function_ExecuteFunctionMicroflow";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(Function_ExecuteFunctionMicroflow.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private JsonNode rootNodeArguments;
	
	private String executeFunctionMicroflow() throws Exception {
		String firstInputParamName = FunctionImpl.getFirstInputParamName(FunctionRequest.getFunctionMicroflow());
		
		if(firstInputParamName == null || firstInputParamName.isBlank()){
			return executeAndLogFunctionMicroflow(null, null);
		
		} else {
			rootNodeArguments = mapper.readTree(Arguments);
			JsonNode firstInputParamNode = rootNodeArguments.path(firstInputParamName);
			
			if (!firstInputParamNode.isTextual()) {
				throw new IOException("Arguments " + Arguments + " does not match the expected input of the function microflow " + FunctionRequest.getFunctionMicroflow()+ ".");		
			}
			return executeAndLogFunctionMicroflow(firstInputParamName, firstInputParamNode.asText());
		}
	}
	
	private String executeAndLogFunctionMicroflow(String firstInputParamName, String firstInputParamValue) {
		String response;
		String logMessageInfo = "Finished calling microflow " + FunctionRequest.getFunctionMicroflow() + " with " + getContext();
		String logMessageTrace = logMessageInfo;
		long startTime = System.currentTimeMillis();
		if(firstInputParamName == null || firstInputParamName.isBlank()) {
			logMessageTrace = logMessageTrace + "\nwithout input parameters ";
			response = Core.microflowCall(FunctionRequest.getFunctionMicroflow()).execute(getContext());
		} else {
			logMessageTrace = logMessageTrace +  "\n\nInput parameter [" + firstInputParamName + "]:\n" + firstInputParamValue;
			response = Core.microflowCall(FunctionRequest.getFunctionMicroflow()).withParam(firstInputParamName, firstInputParamValue).execute(getContext());
		}
		long endTime = System.currentTimeMillis();
		long executionTime = endTime - startTime;
		logMessageInfo = logMessageInfo + "\n\nDuration:\n" + executionTime + "ms";
		logMessageTrace = logMessageTrace+ "\n\nReturn value:\n" + response + "\n\nDuration:\n" + executionTime + "ms";
		LOGGER.info(logMessageInfo);
		LOGGER.trace(logMessageTrace);
		return response;
	}
	
	// END EXTRA CODE
}
