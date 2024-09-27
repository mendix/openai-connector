// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package genaicommons.actions;

import static java.util.Objects.requireNonNull;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IDataType;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import genaicommons.proxies.Response;
import genaicommons.proxies.microflows.Microflows;
import genaicommons.impl.MxLogger;

/**
 * This Java Action executes the Request by calling the LLM via the passed ModelCallMicroflow.
 * If the model returns a tool call response, the function microflows are automatically executed and added as messages with MessageRole "tool" to the request. Afterwards the ModelCallMicroflow is executed again. This process is repeated until the model returns a final assistant response.
 */
public class Request_Execute extends CustomJavaAction<IMendixObject>
{
	private IMendixObject __Request;
	private genaicommons.proxies.Request Request;
	private IMendixObject __Connection;
	private genaicommons.proxies.Connection Connection;
	private java.lang.String CallModelMicroflow;

	public Request_Execute(IContext context, IMendixObject Request, IMendixObject Connection, java.lang.String CallModelMicroflow)
	{
		super(context);
		this.__Request = Request;
		this.__Connection = Connection;
		this.CallModelMicroflow = CallModelMicroflow;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		this.Request = this.__Request == null ? null : genaicommons.proxies.Request.initialize(getContext(), __Request);

		this.Connection = this.__Connection == null ? null : genaicommons.proxies.Connection.initialize(getContext(), __Connection);

		// BEGIN USER CODE
		
		//Validations
		requireNonNull(Request, "Request is required.");
		requireNonNull(Connection, "Connection is required.");
		validateMicroflow(CallModelMicroflow);
		try {
			LOGGER.info(startTime);
			startTime = System.currentTimeMillis();
			LOGGER.info(startTime);
			return processRequest().getMendixObject();
		} catch (Exception e) {
			LOGGER.error(e);
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
		return "Request_Execute";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(Request_Execute.class);
	
	private int totalTokens = 0;
	private int requestTokens = 0;
	private int responseTokens = 0;
	private long startTime;
	
	//Recursive response processing until there is no ToolCall available
	private Response processRequest() throws CoreException {
		IMendixObject responseMendixObject = Core.microflowCall(CallModelMicroflow).withParam("Connection", Connection.getMendixObject()).withParam("Request", Request.getMendixObject()).execute(this.getContext());
		Response response = genaicommons.proxies.Response.load(getContext(), responseMendixObject.getId());
		
		responseUpdateTokenCount(response);
		
		boolean toolCallsProcessed = Microflows.response_ProcessToolCalls(getContext(), response, Request);
		
		//Recursion if tool calls are available
		if (toolCallsProcessed) {
			return processRequest();
		}
		
		response.setDurationMilliseconds((int) Math.ceil(System.currentTimeMillis() - startTime));
		return response;
	}
	
	//Update tokens of Response
	private void responseUpdateTokenCount(Response response) {
		requestTokens += response.getRequestTokens();
		responseTokens += response.getResponseTokens();
		totalTokens += response.getTotalTokens();
		response.setRequestTokens(requestTokens);
		response.setResponseTokens(responseTokens);
		response.setTotalTokens(totalTokens);
	}
	
	//Validate input (Request, Connection) and output (Response) entities
	private static void validateMicroflow(String CallModelMicroflow) {
		if (CallModelMicroflow == null || CallModelMicroflow.isBlank()) {
			throw new IllegalArgumentException("CallModelMicroflow is required.");
		}
		
		validateMicroflowInput(CallModelMicroflow);
		
		//Check if the return Type is a Response object
		if(!Core.getReturnType(CallModelMicroflow).getObjectType().equals(genaicommons.proxies.Response.entityName)) {
			throw new IllegalArgumentException("The CallModelMicroflow " + CallModelMicroflow + " should have " + genaicommons.proxies.Response.entityName + " as return object.");
		}
	}
	
	
	//Check if the input parameters match exactly Connection and Request
	private static void validateMicroflowInput(String CallModelMicroflow){
		Map<String, IDataType> inputParameters = Core.getInputParameters(CallModelMicroflow);
		if (inputParameters == null || inputParameters.size() != 2) {
			throw new IllegalArgumentException("The CallModelMicroflow " + CallModelMicroflow + " should have input parameters of type " + genaicommons.proxies.Request.getType() + " and " + genaicommons.proxies.Connection.getType()+".");
		}
		for(Map.Entry<String, IDataType> entry : inputParameters.entrySet()) {
			String value = entry.getValue().getObjectType();
            if (!value.equals(genaicommons.proxies.Request.entityName) && !value.equals(genaicommons.proxies.Connection.entityName)) {
            	throw new IllegalArgumentException("The CallModelMicroflow " + CallModelMicroflow + " should have input parameters of type " + genaicommons.proxies.Request.getType() + " and " + genaicommons.proxies.Connection.getType()+".");
            }
		}
	}
	// END EXTRA CODE
}
