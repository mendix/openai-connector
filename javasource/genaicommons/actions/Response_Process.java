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
 * This Java Action processes function calling (if requested by the model) until there is no ToolCall requested anymore. It will call the LLM via the passed MicroflowToExecuteRequest.
 * 
 * The tool result is added as message with MessageRole "tool" to the request.
 */
public class Response_Process extends CustomJavaAction<IMendixObject>
{
	private java.lang.String MicroflowToExecuteRequest;
	private IMendixObject __Request;
	private genaicommons.proxies.Request Request;
	private IMendixObject __Response;
	private genaicommons.proxies.Response Response;
	private IMendixObject __Connection;
	private genaicommons.proxies.Connection Connection;

	public Response_Process(IContext context, java.lang.String MicroflowToExecuteRequest, IMendixObject Request, IMendixObject Response, IMendixObject Connection)
	{
		super(context);
		this.MicroflowToExecuteRequest = MicroflowToExecuteRequest;
		this.__Request = Request;
		this.__Response = Response;
		this.__Connection = Connection;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		this.Request = this.__Request == null ? null : genaicommons.proxies.Request.initialize(getContext(), __Request);

		this.Response = this.__Response == null ? null : genaicommons.proxies.Response.initialize(getContext(), __Response);

		this.Connection = this.__Connection == null ? null : genaicommons.proxies.Connection.initialize(getContext(), __Connection);

		// BEGIN USER CODE
		//Validations
		requireNonNull(Request, "Request is required.");
		requireNonNull(Response, "Response is required.");
		requireNonNull(Connection, "Connection is required.");
		validateMicroflowToExecuteRequest(MicroflowToExecuteRequest);
		try {
			return processResponse(Response).getMendixObject();
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
		return "Response_Process";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(Response_Process.class);
	
	private int totalTokens = 0;
	private int requestTokens = 0;
	private int responseTokens = 0;
	
	//Recursive response processing until there is no ToolCall available
	private Response processResponse(Response response) throws CoreException {
		//Returns true if there is a ToolCall
		boolean toolCallAvailable = Microflows.response_PrepareRequestForFunctionCalling(getContext(), response, Request);
		responseUpdateTokenCount(response);
		//Return the response if there is no ToolCall available
		if (toolCallAvailable == false) {
			return response;
		}
	
		//Execute LLM call
		IMendixObject responseMendixObject = Core.microflowCall(MicroflowToExecuteRequest).withParam("Connection", Connection.getMendixObject()).withParam("Request", Request.getMendixObject()).execute(this.getContext());
		Response responseToolCall = genaicommons.proxies.Response.load(getContext(), responseMendixObject.getId());
		return processResponse(responseToolCall);
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
	private static void validateMicroflowToExecuteRequest(String microflowToExecuteRequest) {
		if (microflowToExecuteRequest == null || microflowToExecuteRequest.isBlank()) {
			throw new IllegalArgumentException("MicroflowToExecuteRequest is required.");
		}
		
		validateMicroflowInput(microflowToExecuteRequest);
		
		//Check if the return Type is a Response object
		if(!Core.getReturnType(microflowToExecuteRequest).getObjectType().equals(genaicommons.proxies.Response.entityName)) {
			throw new IllegalArgumentException("The MicroflowToExecuteRequest " + microflowToExecuteRequest + " should have "+ genaicommons.proxies.Response.entityName + " as return object.");
		}
	}
	
	
	//Check if the input parameters match exactly Connection and Request
	private static void validateMicroflowInput(String microflowToExecuteRequest){
		Map<String, IDataType> inputParameters = Core.getInputParameters(microflowToExecuteRequest);
		if (inputParameters == null || inputParameters.size() != 2) {
			throw new IllegalArgumentException("The MicroflowToExecuteRequest " + microflowToExecuteRequest + " should have input parameters of type " + genaicommons.proxies.Request.getType() + " and " + genaicommons.proxies.Connection.getType()+".");
		}
		for( Map.Entry<String, IDataType> entry : inputParameters.entrySet()) {
			String value = entry.getValue().getObjectType();
            if (!value.equals(genaicommons.proxies.Request.entityName) && !value.equals(genaicommons.proxies.Connection.entityName)) {
            	throw new IllegalArgumentException("The MicroflowToExecuteRequest " + microflowToExecuteRequest + " should have input parameters of type " + genaicommons.proxies.Request.getType() + " and " + genaicommons.proxies.Connection.getType()+".");
            }
		}
	}
	// END EXTRA CODE
}
