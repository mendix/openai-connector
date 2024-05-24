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
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import genaicommons.impl.FunctionImpl;
import genaicommons.impl.MxLogger;
import genaicommons.impl.RequestImpl;
import genaicommons.impl.ToolCollectionImpl;
import genaicommons.proxies.ENUM_ToolChoice;
import genaicommons.proxies.Function;
import genaicommons.proxies.Request;
import genaicommons.proxies.ToolCollection;
import com.mendix.systemwideinterfaces.core.IMendixObject;

/**
 * Adds a new Function to an existing FunctionCollection.
 * 
 * Parameters: 
 * - FunctionCollection: The FunctionCollection to which the new function should be added.
 * - FunctionName: The name of the function to call.
 * - FunctionMicroflow: The microflow that is called within this function.
 * - FunctionDescription (optional): A description of what the function does, used by the model to choose when and how to call the function.
 * - IsToolChoiceFunction: If set to true, the new function will become the tool choice of the FunctionCollection. This will force the model to call that particular function.
 */
public class Request_AddFunction extends CustomJavaAction<java.lang.Void>
{
	private IMendixObject __Request;
	private genaicommons.proxies.Request Request;
	private java.lang.String ToolName;
	private java.lang.String ToolDescription;
	private genaicommons.proxies.ENUM_ToolChoice ToolChoice;
	private java.lang.String FunctionMicroflow;

	public Request_AddFunction(IContext context, IMendixObject Request, java.lang.String ToolName, java.lang.String ToolDescription, java.lang.String ToolChoice, java.lang.String FunctionMicroflow)
	{
		super(context);
		this.__Request = Request;
		this.ToolName = ToolName;
		this.ToolDescription = ToolDescription;
		this.ToolChoice = ToolChoice == null ? null : genaicommons.proxies.ENUM_ToolChoice.valueOf(ToolChoice);
		this.FunctionMicroflow = FunctionMicroflow;
	}

	@java.lang.Override
	public java.lang.Void executeAction() throws Exception
	{
		this.Request = this.__Request == null ? null : genaicommons.proxies.Request.initialize(getContext(), __Request);

		// BEGIN USER CODE
		try{
			requireNonNull(Request, "Request is required.");
			FunctionImpl.validateFunctionInput(FunctionMicroflow, ToolName);
			
			ToolCollection toolCollection = ToolCollectionImpl.getOrCreateToolCollection(getContext(), Request);
			
			Function function = FunctionImpl.createFunction(getContext(), FunctionMicroflow, ToolName, ToolDescription, toolCollection);
			
			if(ToolChoice != null) {
				RequestImpl.setToolChoice(Request, toolCollection, ToolChoice, function);
			}
			
			return null;
		
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
		return "Request_AddFunction";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(Request_AddFunction.class);
	// END EXTRA CODE
}
