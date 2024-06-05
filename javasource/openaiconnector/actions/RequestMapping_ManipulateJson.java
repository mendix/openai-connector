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
import java.util.Optional;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import openaiconnector.impl.MxLogger;
import openaiconnector.proxies.OpenAIRequest_Extension;
import openaiconnector.proxies.RequestMapping;
import genaicommons.impl.MessageImpl;
import genaicommons.impl.FunctionImpl;
import genaicommons.proxies.ENUM_ToolChoice;
import genaicommons.proxies.Message;
import genaicommons.proxies.Request;
import genaicommons.proxies.Tool;
import genaicommons.proxies.Function;
import genaicommons.proxies.ToolCall;
import genaicommons.proxies.ToolCollection;
import genaicommons.proxies.ENUM_MessageRole;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class RequestMapping_ManipulateJson extends CustomJavaAction<java.lang.String>
{
	private IMendixObject __RequestMapping;
	private openaiconnector.proxies.RequestMapping RequestMapping;
	private java.lang.String Request_Json;

	public RequestMapping_ManipulateJson(IContext context, IMendixObject RequestMapping, java.lang.String Request_Json)
	{
		super(context);
		this.__RequestMapping = RequestMapping;
		this.Request_Json = Request_Json;
	}

	@java.lang.Override
	public java.lang.String executeAction() throws Exception
	{
		this.RequestMapping = this.__RequestMapping == null ? null : openaiconnector.proxies.RequestMapping.initialize(getContext(), __RequestMapping);

		// BEGIN USER CODE
		try {
			requireNonNull(RequestMapping, "RequestMapping is required.");
			requireNonNull(Request_Json, "Request_JSON is required.");
			
			validateRequestMapping();

			rootNode = MAPPER.readTree(Request_Json);
            
            //find stop node and remove if array is empty
            removeEmptyArrayNode(rootNode, "stop");
            
			updateMessages(rootNode);
			setFunctionToolChoice(rootNode);
			mapFunctionParameters();

			return MAPPER.writeValueAsString(rootNode);
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
		return "RequestMapping_ManipulateJson";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(RequestMapping_ManipulateJson.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private JsonNode rootNode;
	
	private boolean validateRequestMapping() throws CoreException {
		Request request = getRequest(RequestMapping);
		requireNonNull(request, "RequestMapping is not associated to a Request");
		return request != null; 
		
	}
	
	private void updateMessages(JsonNode rootNode) {
		//Get messages node
		JsonNode messagesNode = rootNode.path("messages");
		//Loop over all messages

		for (JsonNode messageNode : messagesNode) {
			//find tool_calls node and remove if array is empty
            removeEmptyArrayNode(messageNode, "tool_calls");
            
            //If a fileCollection has been added replace content node with array of text content and file content
            updateImageMessages(messageNode);
        }
		//Update messages within rootNode
		((ObjectNode) rootNode).set("messages", messagesNode);
	}

	private void removeEmptyArrayNode(JsonNode jsonNode, String path) {
		JsonNode arrayNode = jsonNode.path(path);
		if (arrayNode != null && arrayNode.isArray() && arrayNode.size() == 0) {
		    ((ObjectNode) jsonNode).remove(path);
		}
	}
	
	
	private void updateImageMessages(JsonNode messageNode) {
		JsonNode fileCollection = messageNode.path("filecollection");
		
		//Return if there no images will be sent
		if(fileCollection == null || fileCollection.size() == 0) {
			return;
		}
		ArrayNode content = MAPPER.createArrayNode();
			
		//set text content string as first element in array
		addTextContentNode(content, messageNode.path("content").asText());
		
		//add image content to array
		for (JsonNode file : fileCollection) {
			ObjectNode imageURL = MAPPER.createObjectNode();
			if(file.path("textcontent") != null && !file.path("textcontent").asText().isBlank()) {
				addTextContentNode(content, file.path("textcontent").asText());
			}
			
			imageURL.put("url", file.path("filecontent").asText());
			
			if(file.path("detail") != null && !file.path("detail").asText().isBlank()) {
				imageURL.put("detail", file.path("detail").asText());
			}
			
			ObjectNode imageContent = MAPPER.createObjectNode();
			imageContent.put("type", "image_url");
			imageContent.set("image_url", imageURL);
			content.add(imageContent);
		}
			
		//Remove imageCollection helper structure
		((ObjectNode) messageNode).remove("filecollection");
		//Overwrite content node including images
		((ObjectNode) messageNode).set("content", content);
	}

	private void addTextContentNode(ArrayNode content, String text) {
		ObjectNode textContent = MAPPER.createObjectNode();
		textContent.put("type", "text");
		textContent.put("text", text);
		content.add(textContent);
	}
	

	private void setFunctionToolChoice(JsonNode rootNode) throws CoreException {
		// ToolChoice is not function and thus empty, auto or none
		if (RequestMapping.getToolChoice() == null || !RequestMapping.getToolChoice().equals(ENUM_ToolChoice.tool)) {
			return;

		// Add ToolChoice Tool if it has not yet been called in a previous iteration
		} else if (getToolCollection(RequestMapping) != null) {
			Tool toolChoiceTool = getToolCollection(RequestMapping).getToolCollection_ToolChoice();
			
			// Remove tool choice function, because it has already been called
			// This prevents and infinite loop
			if (toolChoiceTool == null || isToolRecall(toolChoiceTool)) {
				LOGGER.debug("ToolChoice " + toolChoiceTool.getName() + " has already been called. Removing ToolChoice from Request.");
				((ObjectNode)rootNode).remove("tool_choice");
			
			} else {
				// Create a new tool_choice node
		        ObjectNode toolChoiceNode = createToolChoiceNode(toolChoiceTool);
		        LOGGER.debug("ToolChoice has not been called yet. Updating ToolChoice function placeholder with: " + toolChoiceNode);
		        
		        // Update the original JsonNode with the tool_choice object
		        ((ObjectNode) rootNode).set("tool_choice", toolChoiceNode);
			}
		}
	}
	
	private ToolCollection getToolCollection(RequestMapping requestMapping) throws CoreException {
		Request request = getRequest(requestMapping);
		if (request == null) {
			return null;
		}
		return request.getRequest_ToolCollection();
	}
	
	private Request getRequest(RequestMapping requestMapping) throws CoreException {
		if (requestMapping.getRequestMapping_OpenAIRequest_Extension() == null) {
			LOGGER.warn("RequestMapper is not associated to a OpenAI_Extension instance");
			return null;
		}
		OpenAIRequest_Extension openAIRequestExtension = requestMapping.getRequestMapping_OpenAIRequest_Extension();
		if (openAIRequestExtension.getOpenAIRequest_Extension_Request() == null) {
			LOGGER.warn("OpenAIRequest_Extension is not associated to a Request instance");
			return null;
		}
		return openAIRequestExtension.getOpenAIRequest_Extension_Request();
	}
	
	private ObjectNode createToolChoiceNode(Tool toolChoiceTool) {
		ObjectNode toolChoiceNode = MAPPER.createObjectNode();
        toolChoiceNode.put("type", toolChoiceTool.getToolType() != null ? toolChoiceTool.getToolType() : "function"); //currently only "function" is supported
        ObjectNode functionNode = MAPPER.createObjectNode();
        functionNode.put("name", toolChoiceTool.getName());
        toolChoiceNode.set("function", functionNode);
        return toolChoiceNode;
	}

	private boolean isToolRecall(Tool toolChoiceTool) throws CoreException {
		// Get all messages with role 'tool'
		List<Message> messageListTool = MessageImpl
				.retrieveMessageListByRole(getRequest(RequestMapping), ENUM_MessageRole.tool, getContext());

		// No tool calls yet; thus no tool recall
		if (messageListTool.size() == 0) {
			return false;
		}

		// Get all messages with role assistant
		// Assistant messages optionally have an array of tool_calls that contain an id and the functionName
		List<Message> messageListAssistant = MessageImpl
				.retrieveMessageListByRole(getRequest(RequestMapping), ENUM_MessageRole.assistant, getContext());

		// HashMap with ToolCall._id and ToolCallFunction.Name created from the messageListAssistant
		// The map contains only those tool calls, where functionName equals the toolChoiceFunctionName
		Map<String, String> toolChoiceToolCallMap = new HashMap<>();

		for (Message message : messageListAssistant) {

			// Get ToolCall list for each assistant message where the function name equals
			// the function name from the tool choice (toolChoiceFunctionName)
			List<ToolCall> toolCallList = Core.retrieveByPath(getContext(), message.getMendixObject(),
							Message.MemberNames.Message_ToolCall.toString())
					.stream()
					.filter(mxObject -> {
						return filterToolCallByFunctionName(toolChoiceTool.getName(), mxObject);
					})
					.map(mxObject -> ToolCall.initialize(getContext(), mxObject))
					.collect(Collectors.toList());

			// Loop over toolCallList and add _id and functionName to a HashMap
			for (ToolCall toolCall : toolCallList) {
				String toolCallId = toolCall.getToolCallId();
				String toolName = toolCall.getName();
				toolChoiceToolCallMap.put(toolCallId, toolName);
			}
		}

		// Loop over Tool messages and compare ToolCallId with Ids from Assistant
		// messages in HashMap to see whether the function from the Tool Choice has
		// already been called
		for (Message messageTool : messageListTool) {
			String toolId = messageTool.getToolCallId();
			if (toolChoiceToolCallMap.containsKey(toolId)) {
				return true;
			}
		}
		return false;
	}

	private boolean filterToolCallByFunctionName(String toolChoiceFunctionName, IMendixObject mxObject) {
		String functionName = "";
		functionName = ToolCall.initialize(getContext(), mxObject).getName();
		// Return true if the functionName equals toolChoiceFunctionName
		return functionName.equals(toolChoiceFunctionName);
	}
	
	private void mapFunctionParameters() throws CoreException {
		ToolCollection toolCollection = getToolCollection(RequestMapping);
		if(toolCollection == null) {
			return;
		}
		List<Tool> toolList = Core.retrieveByPath(getContext(),
				toolCollection.getMendixObject(), ToolCollection.MemberNames.ToolCollection_Tool.toString())
				.stream()
				.map(mxObject -> Tool.initialize(getContext(), mxObject))
				.collect(Collectors.toList());
		
		// Loop through all tools, find FunctionRequest object by functionName that contains the FunctionMicroflow,
		// get InputParameterName of the FunctionMicroflow, create parametersNode and add to toolNode
		JsonNode toolsNode = rootNode.path("tools");
		for (JsonNode toolNode : toolsNode) {
			String toolName = toolNode.path("function").path("name").asText();
			Optional<Tool> toolMatch = toolList.stream()
					.filter(tool -> {
						return tool.getName().equals(toolName);
					})
					.findFirst();
			if(toolMatch.isPresent()) {
				Function functionMatch = Function.load(getContext(), toolMatch.get().getMendixObject().getId());
				if(functionMatch != null) {
				ObjectNode parametersNode = createFunctionParametersNode(functionMatch.getMicroflow());
					if(parametersNode != null) {
						JsonNode functionNode = toolNode.path("function");
						((ObjectNode) functionNode).set("parameters", parametersNode);
						((ObjectNode) toolNode).set("function", functionNode);
					}
				}
			}
		}
		
		// Update tools within rootNode
		((ObjectNode) rootNode).set("tools", toolsNode);
	}
	
	private ObjectNode createFunctionParametersNode(String functionMicroflow) {
		String inputParamName = FunctionImpl.getFirstInputParamName(functionMicroflow);
		if (inputParamName == null || inputParamName.isBlank()) {
			return null;
		}

		ObjectNode parametersNode = MAPPER.createObjectNode();
		ObjectNode propertiesNode = MAPPER.createObjectNode();
		ObjectNode propertyNode = MAPPER.createObjectNode(); 
		ArrayNode requiredNode = MAPPER.createArrayNode();
		
		propertyNode.put("type", "string");
		
		propertiesNode.set(inputParamName, propertyNode);
		
		requiredNode.add(inputParamName);
		
		parametersNode.put("type", "object");
		parametersNode.set("properties", propertiesNode);
		parametersNode.set("required", requiredNode);
		
		return parametersNode;
	}
	
	// END EXTRA CODE
}
