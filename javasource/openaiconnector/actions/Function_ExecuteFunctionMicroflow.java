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
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IDataType;
import com.mendix.webui.CustomJavaAction;
import openaiconnector.impl.FunctionImpl;
import openaiconnector.impl.MxLogger;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Function_ExecuteFunctionMicroflow extends CustomJavaAction<java.lang.String>
{
	private IMendixObject __Function;
	private openaiconnector.proxies.Function Function;
	private java.lang.String Arguments;

	public Function_ExecuteFunctionMicroflow(IContext context, IMendixObject Function, java.lang.String Arguments)
	{
		super(context);
		this.__Function = Function;
		this.Arguments = Arguments;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		this.Function = this.__Function == null ? null : openaiconnector.proxies.Function.initialize(getContext(), __Function);

		// BEGIN USER CODE
		try {
			requireNonNull(Function, "Function is required.");
			requireNonNull(Function.getFunctionMicroflow(), "Function has no FunctionMicroflow.");
			FunctionImpl.validateFunctionMicroflow(Function.getFunctionMicroflow());
			
			String firstInputParamName = getFirstInputParamName();
			
			if(firstInputParamName == null || firstInputParamName.isBlank()){
				LOGGER.info("Calling microflow ", Function.getFunctionMicroflow(), " without input parameters with ", this.context(), ".");
				return Core.microflowCall(Function.getFunctionMicroflow()).withParam(firstInputParamName, Arguments).execute(this.getContext());
			} else {
				rootNodeArguments = mapper.readTree(Arguments);
				String firstInputParamNameLowerCase = firstInputParamName.substring(0, 1).toLowerCase() + firstInputParamName.substring(1); //make first letter LowerCase as in JSON
				JsonNode firstInputParamNode = rootNodeArguments.path(firstInputParamNameLowerCase);
				LOGGER.info("firstInputParamName: ", firstInputParamName, " Node: ", firstInputParamNode.asText());
				if (!firstInputParamNode.isTextual()) {
					throw new IllegalArgumentException("Arguments " + Arguments + " does not match the expected input of the function microflow " + Function.getFunctionMicroflow()+ ".");		
				}
				LOGGER.info("Calling microflow ", Function.getFunctionMicroflow(), " with input parameter ", firstInputParamName, ": ", firstInputParamNode.asText(), " with ", this.context(), ".");
				return Core.microflowCall(Function.getFunctionMicroflow()).withParam(firstInputParamName, firstInputParamNode.asText()).execute(this.getContext());
			}
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
	
	private String getFirstInputParamName() {
		Map<String, IDataType> inputParameters = Core.getInputParameters(Function.getFunctionMicroflow());
		return inputParameters.entrySet().iterator().next().getKey();
	}
	
	// END EXTRA CODE
}
