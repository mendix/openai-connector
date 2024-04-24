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
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import openaiconnector.impl.MxLogger;
import openaiconnector.proxies.ENUM_ToolChoice;
import openaiconnector.proxies.ToolRequest;
import openaiconnector.proxies.FunctionRequest;
import openaiconnector.proxies.ToolCall;
import openaiconnector.proxies.ChatCompletionsMessageRequest;
import openaiconnector.proxies.ChatCompletionsMessages;
import openaiconnector.proxies.ENUM_Role;
import openaiconnector.impl.ChatCompletionsMessageRequestImpl;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ChatCompletionsRequest_ManipulateExportMapping extends CustomJavaAction<java.lang.String>
{
	private IMendixObject __ChatCompletionsRequest;
	private openaiconnector.proxies.ChatCompletionsRequest ChatCompletionsRequest;
	private java.lang.String ChatCompletionsRequest_Json;

	public ChatCompletionsRequest_ManipulateExportMapping(IContext context, IMendixObject ChatCompletionsRequest, java.lang.String ChatCompletionsRequest_Json)
	{
		super(context);
		this.__ChatCompletionsRequest = ChatCompletionsRequest;
		this.ChatCompletionsRequest_Json = ChatCompletionsRequest_Json;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		this.ChatCompletionsRequest = this.__ChatCompletionsRequest == null ? null : openaiconnector.proxies.ChatCompletionsRequest.initialize(getContext(), __ChatCompletionsRequest);

		// BEGIN USER CODE
		try {
			requireNonNull(ChatCompletionsRequest, "ChatCompletionsRequest is required.");
			requireNonNull(ChatCompletionsRequest_Json, "ChatCompletionsRequest_Json is required.");

			rootNode = mapper.readTree(ChatCompletionsRequest_Json);
			
			removeEmptyMessageToolCalls(rootNode);

			setFunctionToolCall(rootNode);

			return mapper.writeValueAsString(rootNode);
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
		return "ChatCompletionsRequest_ManipulateExportMapping";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(ChatCompletionsRequest_ManipulateExportMapping.class);
	private static ObjectMapper mapper = new ObjectMapper();
	private JsonNode rootNode;
	
	private void removeEmptyMessageToolCalls(JsonNode rootNode) {
		//Get messages node
		JsonNode messagesNode = rootNode.path("messages");
		//Loop over all messages, find tool_calls node and remove if array is empty
		for (JsonNode messageNode : messagesNode) {
            JsonNode toolCallsNode = messageNode.path("tool_calls");
            if (toolCallsNode.isArray() && toolCallsNode.size() == 0) {
                ((ObjectNode) messageNode).remove("tool_calls");
            }
        }
		//Update messages within rootNode
		((ObjectNode) rootNode).set("messages", messagesNode);
	}

	private void setFunctionToolCall(JsonNode rootNode) throws CoreException {

		// ToolChoice is not function and thus auto or none
		if (ChatCompletionsRequest.getToolChoice() == null || ChatCompletionsRequest.getToolChoice().equals(ENUM_ToolChoice.function) == false) {
			return;

		// Remove ToolChoice, because information is missing
		} else if (ChatCompletionsRequest.getChatCompletionsRequest_ToolRequest_ToolChoice() == null
				|| ChatCompletionsRequest.getChatCompletionsRequest_ToolRequest_ToolChoice().getToolType() == null
				|| ChatCompletionsRequest.getChatCompletionsRequest_ToolRequest_ToolChoice()
						.getToolRequest_FunctionRequest() == null
				|| ChatCompletionsRequest.getChatCompletionsRequest_ToolRequest_ToolChoice()
						.getToolRequest_FunctionRequest().getName().isBlank()) {
			LOGGER.debug("ToolChoice is set to function, but function information is missing. Removing ToolChoice from Request.");
			((ObjectNode)rootNode).remove("tool_choice");

		// Add ToolChoice Function if it has not yet been called in a previous iteration
		} else {
			ToolRequest toolRequest = ChatCompletionsRequest.getChatCompletionsRequest_ToolRequest_ToolChoice();
			FunctionRequest functionRequest = toolRequest.getToolRequest_FunctionRequest();
			
			//Remove tool choice function, because it has already been called; this prevents and infinite loop
			if (isToolRecall(functionRequest.getName())) {
				LOGGER.debug("ToolChoice function " + functionRequest.getName() + " has already been called. Removing ToolChoice from Request.");
				((ObjectNode)rootNode).remove("tool_choice");
			
			} else {
				// Create a new ObjectNode for the tool_choice object
		        ObjectNode toolChoiceNode = mapper.createObjectNode();
		        toolChoiceNode.put("type", toolRequest.getToolType().name());
		        ObjectNode functionNode = mapper.createObjectNode();
		        functionNode.put("name", functionRequest.getName());
		        toolChoiceNode.set("function", functionNode);
		        
		        LOGGER.debug("ToolChoice has not been called yet. Updating ToolChoice function placeholder with: " + toolChoiceNode);
		        
		        // Update the original JsonNode with the tool_choice object
		        ((ObjectNode) rootNode).set("tool_choice", toolChoiceNode);
			}
		}
	}

	private boolean isToolRecall(String toolChoiceFunctionName) throws CoreException {
		ChatCompletionsMessages chatCompletionsMessages = ChatCompletionsRequest
				.getChatCompletionsMessages_ChatCompletionsRequest();

		List<ChatCompletionsMessageRequest> messageListTool = ChatCompletionsMessageRequestImpl
				.retrieveMessageListByRole(chatCompletionsMessages, ENUM_Role.tool, this.getContext());

		// No tool calls yet
		if (messageListTool.size() == 0) {
			return false;
		}

		List<ChatCompletionsMessageRequest> messageListAssistant = ChatCompletionsMessageRequestImpl
				.retrieveMessageListByRole(chatCompletionsMessages, ENUM_Role.assistant, this.getContext());

		// HashMap with ToolCall._id and ToolCallFunction.Name
		Map<String, String> toolIdNameMap = new HashMap<>();

		for (int i = 0; i < messageListAssistant.size(); i++) {

			// Get ToolCall list for each assistant message where the function name equals
			// the function name from the tool choice (toolChoiceFunctionName)
			List<ToolCall> toolCallList = Core.retrieveByPath(this.getContext(), messageListAssistant.get(i).getMendixObject(),
							ToolCall.MemberNames.ToolCall_AbstractChatCompletionsMessage.toString())

					.stream().filter(mxObject -> {
						String functionName = "";
						try {
							functionName = ToolCall.initialize(getContext(), mxObject).getToolCallFunction_ToolCall().getName();
						} catch (CoreException e) {
							LOGGER.error(e);
						}
						return functionName.equals(toolChoiceFunctionName);
					})
					.map(mxObject -> ToolCall.initialize(this.getContext(), mxObject))
					.collect(Collectors.toList());

			// Loop over toolCallList and add _id and functionName to a HashMap
			for (int j = 0; j < toolCallList.size(); j++) {
				String toolCallId = toolCallList.get(j).get_id();
				String functionName = toolCallList.get(j).getToolCallFunction_ToolCall().getName();
				toolIdNameMap.put(toolCallId, functionName);
			}
		}

		// Loop over Tool messages and compare ToolCallId with Ids from Assistant
		// messages in HashMap to see whether the function from the Tool Choice has
		// already been called
		for (int i = 0; i < messageListTool.size(); i++) {
			String toolId = messageListTool.get(i).getToolCallId();
			if (toolIdNameMap.containsKey(toolId)) {
				return true;
			}
		}
		return false;
	}
	// END EXTRA CODE
}
