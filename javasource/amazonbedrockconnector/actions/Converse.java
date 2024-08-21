// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package amazonbedrockconnector.actions;

import static java.util.Objects.requireNonNull;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.io.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import amazonbedrockconnector.genaicommons_impl.FunctionMappingImpl;
import amazonbedrockconnector.genaicommons_impl.MessageImpl;
import amazonbedrockconnector.impl.AmazonBedrockClient;
import amazonbedrockconnector.impl.MxLogger;
import amazonbedrockconnector.proxies.AbstractRequestParameter;
import amazonbedrockconnector.proxies.ChatCompletionsResponse;
import amazonbedrockconnector.proxies.DecimalRequestParameter;
import amazonbedrockconnector.proxies.IntegerRequestParameter;
import amazonbedrockconnector.proxies.RequestedResponseField;
import amazonbedrockconnector.proxies.ResponseFieldRequest;
import amazonbedrockconnector.proxies.StringRequestParameter;
import genaicommons.proxies.ENUM_FileType;
import genaicommons.proxies.ENUM_MessageRole;
import genaicommons.proxies.ENUM_ToolChoice;
import genaicommons.proxies.FileCollection;
import genaicommons.proxies.FileContent;
import genaicommons.proxies.Function;
import genaicommons.proxies.Request;
import genaicommons.proxies.Response;
import genaicommons.proxies.StopSequence;
import genaicommons.proxies.Tool;
import genaicommons.proxies.ToolCall;
import genaicommons.proxies.ToolCollection;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.core.document.Document;
import software.amazon.awssdk.services.bedrockruntime.model.ContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseOutput;
import software.amazon.awssdk.services.bedrockruntime.model.ConverseResponse;
import software.amazon.awssdk.services.bedrockruntime.model.DocumentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.DocumentSource;
import software.amazon.awssdk.services.bedrockruntime.model.ImageBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ImageSource;
import software.amazon.awssdk.services.bedrockruntime.model.InferenceConfiguration;
import software.amazon.awssdk.services.bedrockruntime.model.Message;
import software.amazon.awssdk.services.bedrockruntime.model.SpecificToolChoice;
import software.amazon.awssdk.services.bedrockruntime.model.SystemContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ToolChoice;
import software.amazon.awssdk.services.bedrockruntime.model.ToolConfiguration;
import software.amazon.awssdk.services.bedrockruntime.model.ToolInputSchema;
import software.amazon.awssdk.services.bedrockruntime.model.ToolResultBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ToolResultContentBlock;
import software.amazon.awssdk.services.bedrockruntime.model.ToolSpecification;
import software.amazon.awssdk.services.bedrockruntime.model.ToolUseBlock;

public class Converse extends CustomJavaAction<IMendixObject>
{
	private IMendixObject __Credentials;
	private awsauthentication.proxies.Credentials Credentials;
	private IMendixObject __ConverseRequest;
	private amazonbedrockconnector.proxies.ChatCompletionsRequest_Extension ConverseRequest;
	private IMendixObject __AmazonBedrockConnection;
	private amazonbedrockconnector.proxies.AmazonBedrockConnection AmazonBedrockConnection;

	public Converse(IContext context, IMendixObject Credentials, IMendixObject ConverseRequest, IMendixObject AmazonBedrockConnection)
	{
		super(context);
		this.__Credentials = Credentials;
		this.__ConverseRequest = ConverseRequest;
		this.__AmazonBedrockConnection = AmazonBedrockConnection;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		this.Credentials = this.__Credentials == null ? null : awsauthentication.proxies.Credentials.initialize(getContext(), __Credentials);

		this.ConverseRequest = this.__ConverseRequest == null ? null : amazonbedrockconnector.proxies.ChatCompletionsRequest_Extension.initialize(getContext(), __ConverseRequest);

		this.AmazonBedrockConnection = this.__AmazonBedrockConnection == null ? null : amazonbedrockconnector.proxies.AmazonBedrockConnection.initialize(getContext(), __AmazonBedrockConnection);

		// BEGIN USER CODE
		try {
			requireNonNull(this.Credentials, "A Credentials object is required");
			requireNonNull(this.ConverseRequest, "A ConverseRequest_Extension object is required");
			requireNonNull(this.AmazonBedrockConnection, "An AmazonBedrockConnection object is required");
			
			var client = AmazonBedrockClient.getBedrockRuntimeClient(Credentials, AmazonBedrockConnection.getRegion(), ConverseRequest);
			
			var awsRequest = getAwsRequest();
			LOGGER.debug("AWS Request: " + awsRequest);
			
			var awsResponse = client.converse(awsRequest);
			LOGGER.debug("AWS Response: " + awsResponse);
			
			Response mxResponse = getMxResponse(awsResponse);
			
			return mxResponse.getMendixObject();
			
		} catch (Exception e) {
			LOGGER.error("An error ocurred during Converse operation " + e.getMessage());
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
		return "Converse";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(Converse.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	/* 
	 * The name attribute of a document that is sent to the model.
	 * This field is vulnerable to prompt injections, because the model might inadvertently interpret it as instructions
	 * Therefore, it is recommended to use a hardcoded name. 
	 */
	private static final String DOC_NAME = "user document";
	
	//Request Mapping 
	
	// Main method to build AWS Request
	private software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest getAwsRequest() throws CoreException, MalformedURLException, URISyntaxException, IOException {
		LOGGER.debug("Building AWS Request");
		// GenAICommons.Request
		Request commonRequest = ConverseRequest.getChatCompletionsRequest_Extension_Request();
		
		var builder = software.amazon.awssdk.services.bedrockruntime.model.ConverseRequest.builder()
				.modelId(AmazonBedrockConnection.getModel())
				.inferenceConfig(getAwsInferenceConfig(commonRequest));
		
		if (commonRequest.getSystemPrompt() != null && !commonRequest.getSystemPrompt().isBlank()) {
			builder.system(getAwsSystemPrompt(commonRequest));
		}
		
		builder.messages(getAwsMessages(commonRequest));
		
		if (hasTools(commonRequest)) {
			builder.toolConfig(getAwsToolConfig(commonRequest));
		}
		
		if (hasAdditionalRequestParams()) {
			builder.additionalModelRequestFields(getAdditionalRequestParams());
		}
		
		if (hasAdditionalResponseFieldRequests()) {
			builder.additionalModelResponseFieldPaths(getAdditionalResponseFields());
		}
		
		return builder.build();
	}
	
	// Getting aws InferenceConfiguration object
	private InferenceConfiguration getAwsInferenceConfig(Request commonRequest) throws CoreException {
		LOGGER.debug("Getting aws inference config.");
		var builder = InferenceConfiguration.builder();
		
		if (commonRequest.getMaxTokens() != null) {
			builder.maxTokens(commonRequest.getMaxTokens());
		}
		
		if (commonRequest.getTemperature() != null) {
			builder.temperature(commonRequest.getTemperature().floatValue());
		}
		
		if (commonRequest.getTopP() != null) {
			builder.topP(commonRequest.getTopP().floatValue());
		}
		
		List<StopSequence> mxStopSequences = commonRequest.getRequest_StopSequence();
		if (mxStopSequences.size() > 0) {
			List<String> stopSequences = mxStopSequences.stream().map(mxSeq -> mxSeq.getSequence())
					.collect(Collectors.toList());
			builder.stopSequences(stopSequences);
		}
		
		return builder.build();
	}
	
	// Getting System Prompt
	private SystemContentBlock getAwsSystemPrompt(Request commonRequest) {
		LOGGER.debug("Getting aws system prompt.");
		var builder = SystemContentBlock.builder();
		builder.text(commonRequest.getSystemPrompt());
		
		return builder.build();
	}
	
	private boolean hasAdditionalRequestParams() throws CoreException {
		List<AbstractRequestParameter> abstractParams = ConverseRequest.getChatCompletionsRequest_Extension_AbstractRequestParameter();
		return abstractParams.size() > 0;
	}
	
	// Getting additional request params dependent on type
	private Document getAdditionalRequestParams() throws CoreException {
		LOGGER.debug("Getting additional request parameters");
		List<AbstractRequestParameter> abstractParams = ConverseRequest.getChatCompletionsRequest_Extension_AbstractRequestParameter();
		
		var builder = Document.mapBuilder();
		
		for (AbstractRequestParameter param : abstractParams) {
			
			if (param instanceof StringRequestParameter) {
				StringRequestParameter strParam = (StringRequestParameter) param;
				builder.putString(strParam.getKey(), strParam.getValue());
				continue;
			}
			
			if (param instanceof IntegerRequestParameter) {
				IntegerRequestParameter intParam = (IntegerRequestParameter) param;
				builder.putNumber(intParam.getKey(), intParam.getValue());
				continue;
			}
			
			if (param instanceof DecimalRequestParameter) {
				DecimalRequestParameter decParam = (DecimalRequestParameter) param;
				builder.putNumber(decParam.getKey(), decParam.getValue());
				continue;
			}
			
			// If object is not of a supported type
			LOGGER.error("Skipping invalid additional request parameter. To add additional request parameters use 'StringRequestParameter', 'DecimalRequestParameter' or 'IntegerRequestParameter' entities.");
			
		}
		return builder.build();
	}
	
	private boolean hasAdditionalResponseFieldRequests() throws CoreException {
		List<ResponseFieldRequest> responseFields = ConverseRequest.getChatCompletionsRequest_Extension_ResponseFieldRequest();
		return responseFields.size() > 0;
	}
	
	// Getting additional response field requests as list of strings
	// These must be a list of valid JsonPointer Strings, which indicate where in the Json hierachy the requested value resides
	private List<String> getAdditionalResponseFields() throws CoreException {
		LOGGER.debug("Getting additional response field requests");
		return ConverseRequest.getChatCompletionsRequest_Extension_ResponseFieldRequest().stream()
				.map(obj -> getJsonPointer(obj.getFieldName()))
				.collect(Collectors.toList());
	}
	
	// Helper to create valid Json Pointer
	private String getJsonPointer(String fieldName) {
		if (fieldName.startsWith("/")) {
			return fieldName;
		} else {
			return String.format("/%s", fieldName);
		}
	}
	
	// Mapping every Mendix Message to a AWS Message
	private List<Message> getAwsMessages(Request commonRequest) throws CoreException, MalformedURLException, URISyntaxException, IOException {
		List<Message> awsMessages = new ArrayList<>();
		
		List<genaicommons.proxies.Message> mxMessages = getMxMessagesSorted(commonRequest);
		
		LOGGER.debug("MX Messages: ", mxMessages);
		
		for (int i = 0; i < mxMessages.size(); i++) {
			genaicommons.proxies.Message mxMsg = mxMessages.get(i);
			
			// Checking if this message can be skipped
			// This is the case if it's a subsequent tool_result message
			// In this case it has been mapped to the contents of the previous message already
			if (skipMessage(mxMsg, mxMessages, i)) {
				LOGGER.debug("Skipping Message", mxMsg);
				continue;
			}
			
			Message awsMsg = getAwsMessage(mxMsg, mxMessages, i);
			awsMessages.add(awsMsg);
		}
		
		return awsMessages;
	}
	
	// Sorting the Mendix Messages by Created-Date to ensure they are mapped in the correct order
	private List<genaicommons.proxies.Message> getMxMessagesSorted(Request commonRequest) throws CoreException {
		return Core.retrieveByPath(getContext(), commonRequest.getMendixObject(), Request.MemberNames.Request_Message.toString())
				.stream().map(mxObj -> genaicommons.proxies.Message.initialize(getContext(), mxObj))
				.collect(Collectors.toList());
	}
	
	// Check if a message needs to be skipped because it's contents have been mapped to a previous message
	private boolean skipMessage(genaicommons.proxies.Message mxMsg,List<genaicommons.proxies.Message> messages, int i) {
		
		boolean skip = false;
		
		if (i != 0) {
			if (isToolResultMessage(mxMsg)) {
				genaicommons.proxies.Message previous = messages.get(i-1);
				if (isToolResultMessage(previous)) {
					skip = true;
				}
			}
		}
		
		return skip;
	}
	
	// Method to map a Mx Message to the correct type of aws message
	private Message getAwsMessage(genaicommons.proxies.Message mxMsg, List<genaicommons.proxies.Message> mxMessages, int i) throws CoreException, MalformedURLException, URISyntaxException, IOException {
		var msgBuilder = Message.builder()
				.role(mxMsg.getRole().name());
		
		List<ContentBlock> contentBlockList = new ArrayList<>();
		
		// Case 1: Message has a FileCollection with FileContent(s). 
		if (hasFiles(mxMsg)) {
			LOGGER.debug("Message with Files found");
			
			// Check if a message content is present it can be added as a separate text content.
			if (mxMsg.getContent() != null && !mxMsg.getContent().isBlank()) {
				ContentBlock textContent = getTextContent(mxMsg.getContent());
				contentBlockList.add(textContent);
			}
			
			// Count for documents in message
			// Used for unique document name
			int j = 0; 
			
			// Adding file content for each file
			List<FileContent> files = getFiles(mxMsg);
			for (FileContent file : files) {
				
				// Adding additional text content if TextContent attribute contains content
				if (file.getTextContent() != null && !file.getTextContent().isBlank()) {
					ContentBlock imgTextContent = getTextContent(file.getTextContent());
					contentBlockList.add(imgTextContent);
				}
				
				// Checking if file is image or document
				// Then creating the corresponding content block types for it
				ENUM_FileType fileType = file.getFileType();
				if (fileType != null) {
					
					switch (fileType) {
					case image: {
						ContentBlock imageContentBlock = getImageContent(file);
						if (imageContentBlock != null) {
							contentBlockList.add(imageContentBlock);
						}
						break;
					}
					case document: {
						ContentBlock documentContentBlock = getDocumentContent(file, i, j);
						contentBlockList.add(documentContentBlock);
						j++;
						break;
					}
					default:
						LOGGER.warn("Unsupported FileContent FileType found in request.");
						break;
					}
				} else {
					LOGGER.error("FileContent with empty FileType found in request.");
				}
				
			}
		
		// Case 2: After a Function Call, a Tool Result message is being sent	
		} else if (isToolResultMessage(mxMsg)) {
			LOGGER.debug("Tool Result Message found");
			ContentBlock toolResultContent = getToolResultContent(mxMsg);
			contentBlockList.add(toolResultContent);
			
			// Bedrock expects all subsequent tool results as part of a single message
			// Looking for subsequent tool results and adding them to this message until a different message type is found
			while ((i+1) < mxMessages.size()) {
				genaicommons.proxies.Message next = mxMessages.get(i+1);
				if (!isToolResultMessage(next)) {
					break;
				}
				ContentBlock nextToolResultContent = getToolResultContent(next);
				contentBlockList.add(nextToolResultContent);
				i++;
			}
			
		// Case 3: A Message requesting the use of tool (function call)
		} else if (hasToolUse(mxMsg)) {
			LOGGER.debug("Tool Use message found");
			// If content is present, this contains the reasoning process
			// Is mapped as additional text content
			if (mxMsg.getContent() != null && !mxMsg.getContent().isBlank()) {
				ContentBlock textContent = getTextContent(mxMsg.getContent());
				contentBlockList.add(textContent);
			}
			
			List<ToolCall> toolCalls = mxMsg.getMessage_ToolCall();
			for (ToolCall toolCall : toolCalls) {
				ContentBlock toolUseContent = getToolUseContent(toolCall);
				contentBlockList.add(toolUseContent);
			}
			
		// Case 4: Normal text message
		} else {
			LOGGER.debug("Standard Text message found");
			ContentBlock textContent = getTextContent(mxMsg.getContent());
			contentBlockList.add(textContent);
		}
		
		msgBuilder.content(contentBlockList);
		return msgBuilder.build();
	}
	
	// Check if the message has images
	private boolean hasFiles(genaicommons.proxies.Message mxMsg) throws CoreException {
		FileCollection fileCol = mxMsg.getMessage_FileCollection();
		if (fileCol == null) {
			return false;
		}
		
		List<FileContent> fileContents = fileCol.getFileCollection_FileContent();
		if (fileContents.size() == 0) {
			return false;
		}
		
		return true;
	}
	
	private ContentBlock getDocumentContent(FileContent doc, int i, int j) {
		// Creating document content block
		// Using fixed name because this field is vulnerable to prompt injection
		// source is fileContent attribute as byte[] from base64 string
		String format = getDocFormat(doc);
		String name = String.format("%s-%s-%s", DOC_NAME, i, j);
		DocumentSource source = getDocSource(doc);
		
		DocumentBlock docBlock = DocumentBlock.builder().format(format)
				.name(name)
				.source(source)
				.build();
		
		return ContentBlock.builder()
				.document(docBlock)
				.build();
	}
	
	private String getDocFormat(FileContent doc) {
		String mediaType = doc.getMediaType();
		if (mediaType == null || mediaType.isBlank()) {
			LOGGER.error("FileContent with empty MediaType found in request.");
			return null;
		}
		
		int index = mediaType.indexOf("/");
		String format = index == -1 ? mediaType : mediaType.substring(index+1);
		return format;
	}
	
	private DocumentSource getDocSource(FileContent doc) {
		byte[] bytes = Base64.getDecoder().decode(doc.getFileContent());
		var builder = DocumentSource.builder()
				.bytes(SdkBytes.fromByteArray(bytes));
		return builder.build();
	}
	
	// Helper to get all Image FileContent objects from a message
	private List<FileContent> getFiles(genaicommons.proxies.Message mxMsg) throws CoreException {
		return mxMsg.getMessage_FileCollection().getFileCollection_FileContent();
	}
	
	// Creating a Content Block with text 
	private ContentBlock getTextContent(String text) {
		return ContentBlock.builder().text(text).build();
	}
	
	// Method to call one of the two Image Content Methods
	// Different methods for FileContent as Base64 vs URL
	private ContentBlock getImageContent(FileContent mxImage) throws CoreException, MalformedURLException, URISyntaxException, IOException {
		switch (mxImage.getContentType()) {
		case Base64: {
			return getImageContentBase64(mxImage);
		}
		case Url: {
			return getImageContentURI(mxImage);
		}
		default:
			LOGGER.error("Unknown Image ContentType: " + mxImage.getContentType());
			return null;
		}
	}
	
	// ContentBlock with image
	private ContentBlock getImageContentBlock(String format, byte[] bytes) {
		var imageBuilder = ImageBlock.builder()
				.format(format);
		
		var imageSourceBuilder = ImageSource.builder()
				.bytes(SdkBytes.fromByteArray(bytes));
		
		imageBuilder.source(imageSourceBuilder.build());
		
		return ContentBlock.builder().image(imageBuilder.build()).build();
	}
	
	private ContentBlock getImageContentBase64(FileContent mxImage) {
		String format = getImageFormat(mxImage.getMediaType());
		byte[] bytes = Base64.getDecoder().decode(mxImage.getFileContent());
		
		return getImageContentBlock(format, bytes);
	}
	
	// Handling an image that is only present as a URL
	// Getting the bytes and the format
	private ContentBlock getImageContentURI(FileContent mxImage) throws URISyntaxException, MalformedURLException, IOException {
		URL url = new URL(mxImage.getFileContent());
		
		try (InputStream is = url.openStream ()) {
		  
		  byte[] imageBytes = IOUtils.toByteArray(is);
			
			String format;
			if (mxImage.getMediaType() != null && !mxImage.getMediaType().isBlank()) {
				format = getImageFormat(mxImage.getMediaType());
			} else {
				format = getFormatFromURL(url);
			}
			
			return getImageContentBlock(format, imageBytes);
		}
	}
	
	// Image Format from mediaType attribute
	// Bedrock accetps "jpeg", not "jpp"
	private String getImageFormat(String mediaType) {
		String format = mediaType.substring(mediaType.indexOf("/") + 1);
		if (format.equals("jpg")) {
			format = "jpeg";
		}
		return format;
	}
	
	// Image Format from URL
	private String getFormatFromURL(URL url) throws MalformedURLException {
        String file = url.getFile();
        String format = file.substring(file.lastIndexOf('.') + 1);
        if (format.equals("jpg")) {
        	format= "jpeg";
		}
        return format;
	}
	
	// Content Block with tool result
	private ContentBlock getToolResultContent(genaicommons.proxies.Message mxMsg) {
		var toolResultContentBuilder = ToolResultContentBlock.builder()
				.text(mxMsg.getContent());
		
		var toolResultBuilder = ToolResultBlock.builder()
				.toolUseId(mxMsg.getToolCallId())
				.content(toolResultContentBuilder.build());
		
		return ContentBlock.builder().toolResult(toolResultBuilder.build()).build();
	}
	
	// ContentBlock with tool use
	// Needed when the LLM is requesting a function call
	private ContentBlock getToolUseContent(ToolCall mxToolCall) throws JsonMappingException, JsonProcessingException {
		var builder = ToolUseBlock.builder()
				.name(mxToolCall.getName())
				.toolUseId(mxToolCall.getToolCallId());
		
		if (mxToolCall.getArguments() == null || mxToolCall.getArguments().isBlank()) {
			builder.input(Document.mapBuilder().putString("", "").build());
			
		} else {
			// Arguments JSON must be build by Document.mapBuilder()
			// Getting the arguments as JSON from the arguments attribute as specified by the LLM
			JsonNode args = MAPPER.readTree(mxToolCall.getArguments());
			String key = args.fieldNames().next();
			Document input = Document.mapBuilder()
					.putString(key, args.get(key).asText())
					.build();
			
			builder.input(input);
		}
		
		return ContentBlock.builder().toolUse(builder.build()).build();
	}
	
	// Adding the available tools to the request
	private ToolConfiguration getAwsToolConfig(Request commonRequest) throws CoreException, JsonProcessingException {
		var builder = ToolConfiguration.builder();
		// Checking if Tool Choice attribute is set to Tool
		// Other values will be mapped to the default: auto
		if (commonRequest.getToolChoice() == ENUM_ToolChoice.tool) {
			if (hasToolChoice(commonRequest)) {
				Tool toolChoiceTool = commonRequest.getRequest_ToolCollection().getToolCollection_ToolChoice();
				if (!isToolRecall(toolChoiceTool, commonRequest)) {
					ToolChoice awsToolChoice = getAwsToolChoice(toolChoiceTool);
					builder.toolChoice(awsToolChoice);
				}
			}
		}
		
		List<software.amazon.awssdk.services.bedrockruntime.model.Tool> awsTools = getAwsTools(commonRequest);
		builder.tools(awsTools);
		
		return builder.build();
	}
	
	// Check if a message contains a tool call (= is a tool result message)
	private boolean isToolResultMessage(genaicommons.proxies.Message msg) {
		return msg.getToolCallId() != null && !msg.getToolCallId().isBlank();
	}
	
	// Check if the request contains any tools
	private boolean hasTools(Request commonRequest) throws CoreException {
		ToolCollection toolCol = commonRequest.getRequest_ToolCollection();
		if (toolCol == null) {
			return false;
		}
		
		List<Tool> tools = toolCol.getToolCollection_Tool();
		return tools.size() > 0;
	}
	
	// Check if the request has specified a tool choice
	private boolean hasToolChoice(Request commonRequest) throws CoreException {
		ToolCollection toolCol = commonRequest.getRequest_ToolCollection();
		if (toolCol == null) {
			return false;
		}
		
		Tool toolCall = toolCol.getToolCollection_ToolChoice();
		return toolCall != null;
	}
	
	// Check if message has Tool Call objects associated
	private boolean hasToolUse(genaicommons.proxies.Message mxMsg) throws CoreException {
		List<ToolCall> toolCalls = mxMsg.getMessage_ToolCall();
		return toolCalls.size() > 0;
	}
	
	// Getting list of aws Tool objects
	private List<software.amazon.awssdk.services.bedrockruntime.model.Tool> getAwsTools(Request commonRequest) throws CoreException, JsonProcessingException {
		List<Tool> mxTools = commonRequest.getRequest_ToolCollection().getToolCollection_Tool();
		List<software.amazon.awssdk.services.bedrockruntime.model.Tool> awsTools = new ArrayList<>();
		
		for (Tool mxTool : mxTools) {
			var awsTool = getAwsTool(mxTool);
			awsTools.add(awsTool);
		}
		
		return awsTools;
	}
	
	// Mapping Mendix Tool to aws tool
	private software.amazon.awssdk.services.bedrockruntime.model.Tool getAwsTool(Tool mxTool) throws JsonProcessingException{
		var toolSpecBuilder = ToolSpecification.builder()
				.name(mxTool.getName())
				.description(mxTool.getDescription())
				.inputSchema(getToolInputSchema(mxTool));
		
		return software.amazon.awssdk.services.bedrockruntime.model.Tool.builder().toolSpec(toolSpecBuilder.build()).build();
	}
	
	// Getting the Input Schema of a Tool
	private ToolInputSchema getToolInputSchema(Tool mxTool) throws JsonProcessingException {
		// All Tools to be called are function objects
		Function function = (Function) mxTool;
		String inputParamName = FunctionMappingImpl.getFirstInputParamName(function.getMicroflow());
		if (inputParamName == null) {
			return null;
		}
		// Must be created using Document.mapBuilder()
		// Constructing the JSON in a different way causes errors
		// Only One Parameter of type String is supported
		
		Document input = Document.mapBuilder()
				.putString("type", "string")
				.build();
		
		Document required = Document.listBuilder()
				.addString(inputParamName)
				.build();
		
		Document properties = Document.mapBuilder()
				.putDocument(inputParamName, input)
				.build();
		
		Document json = Document.mapBuilder()
				.putString("type", "object")
				.putDocument("properties", properties)
				.putDocument("required", required)
				.build();
		
		return ToolInputSchema.builder().json(json).build();
	}
	
	// Check if a tool has already been called to decide whether Tool Choice should be set or not
	private boolean isToolRecall(Tool toolChoiceTool, Request commonRequest) throws CoreException {
		// Get all messages where ToolCallId is set. These messages indicate that a tool has been called
		List<genaicommons.proxies.Message> messageListTool = Core.retrieveByPath(getContext(), commonRequest.getMendixObject(), 
				Request.MemberNames.Request_Message.toString()).stream()
				.map(msg -> genaicommons.proxies.Message.initialize(getContext(), msg))
				.filter(msg -> msg.getToolCallId() != null && !msg.getToolCallId().isEmpty())
				.collect(Collectors.toList());

		// No tool calls yet; thus no tool recall
		if (messageListTool.size() == 0) {
			return false;
		}
		
		// Get all messages with role assistant
		// Assistant messages optionally have an array of tool_calls that contain an id and the functionName
		List<genaicommons.proxies.Message> messageListAssistant = MessageImpl
				.retrieveMessageListByRole(commonRequest, ENUM_MessageRole.assistant, getContext());

		// HashMap with ToolCall._id and ToolCallFunction.Name created from the messageListAssistant
		// The map contains only those tool calls, where functionName equals the toolChoiceFunctionName
		Map<String, String> toolChoiceToolCallMap = getToolChoiceToolCallMap(messageListAssistant, toolChoiceTool);
		
		// Loop over Tool messages and compare ToolCallId with Ids from Assistant
				// messages in HashMap to see whether the function from the Tool Choice has
				// already been called
		return toolCallFound(toolChoiceToolCallMap, messageListTool);
	}
	
	private Map<String, String> getToolChoiceToolCallMap(List<genaicommons.proxies.Message> messageListAssistant, Tool toolChoiceTool) {
		Map<String, String> toolChoiceToolCallMap = new HashMap<>();

		for (genaicommons.proxies.Message message : messageListAssistant) {

			// Get ToolCall list for each assistant message where the function name equals
			// the function name from the tool choice (toolChoiceFunctionName)
			List<ToolCall> toolCallList = Core.retrieveByPath(getContext(), message.getMendixObject(),
							genaicommons.proxies.Message.MemberNames.Message_ToolCall.toString())
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
		
		return toolChoiceToolCallMap;
	}
	
	private boolean toolCallFound(Map<String, String> toolChoiceToolCallMap, List<genaicommons.proxies.Message> messageListTool) {
		for (genaicommons.proxies.Message messageTool : messageListTool) {
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
	
	// Getting aws Tool Choice set to the name of the specified tool choice tool
	private ToolChoice getAwsToolChoice(Tool mxTool) {
		var specificBuilder = SpecificToolChoice.builder()
				.name(mxTool.getName());
		
		return ToolChoice.builder().tool(specificBuilder.build()).build();
	}
	
	// Response Mapping
	
	// Method to map aws response to mendix response
	private Response getMxResponse(ConverseResponse awsResponse) throws JsonProcessingException {
		ChatCompletionsResponse mxResponse = new ChatCompletionsResponse(getContext());
		
		mxResponse.setRequestTokens(awsResponse.usage().inputTokens());
		mxResponse.setResponseTokens(awsResponse.usage().outputTokens());
		mxResponse.setTotalTokens(awsResponse.usage().totalTokens());
		mxResponse.setStopReason(awsResponse.stopReasonAsString());
		mxResponse.setLatencyMs(awsResponse.metrics().latencyMs().intValue());
		
		mxResponse.setResponse_Message(getMxResponseMessage(awsResponse.output()));
		
		if (awsResponse.additionalModelResponseFields() != null) {
			setMxResponseExtension(awsResponse.additionalModelResponseFields(), mxResponse);
		}
		
		return mxResponse;
	}
	
	// Creating the Mendix response message
	private genaicommons.proxies.Message getMxResponseMessage(ConverseOutput converseOutput) throws JsonProcessingException {
		if (converseOutput.type() != ConverseOutput.Type.MESSAGE) {
			LOGGER.error("Unknown response type returned. No response message received.");
			return null;
		}
		
		genaicommons.proxies.Message mxMessage = new genaicommons.proxies.Message(getContext());
		Message awsMessage = converseOutput.message();
		mxMessage.setRole(ENUM_MessageRole.valueOf(awsMessage.roleAsString()));
		
		// Setting message contents and checking for returned tool uses
		
		List<ContentBlock> awsMsgContents = awsMessage.content();
		if (awsMsgContents.size() == 0) {
			LOGGER.error("No message content was returned.");
			return null;
		}
		
		List<ToolCall> mxToolCallList = new ArrayList<>();
			
		for (ContentBlock awsContent : awsMsgContents) {
			addMessageContent(mxMessage, awsContent, mxToolCallList);
		}
		
		if (mxToolCallList.size() > 0) {
			mxMessage.setMessage_ToolCall(mxToolCallList);
		}
		
		return mxMessage;
	}
	
	// Helper to add Content based on aws content type
	// Only text and tool use are supported
	private void addMessageContent(genaicommons.proxies.Message mxMessage, ContentBlock awsContent, List<ToolCall> toolCallList) throws JsonProcessingException {
		switch (awsContent.type()){
		case TEXT: {
			setMessageTextContent(mxMessage, awsContent.text());
			break;
		}
		case TOOL_USE: {
			setMessageToolUseContent(toolCallList, awsContent.toolUse());
			break;
		}
		default:
			LOGGER.error("Unsupported message content returned: " + awsContent.type());
			break;
		}
	}
	
	// Setting text content
	private void setMessageTextContent(genaicommons.proxies.Message mxMessage, String textContent) {
		if (mxMessage.getContent() == null || mxMessage.getContent().isBlank()) {
			mxMessage.setContent(textContent);
		} else {
			mxMessage.setContent(mxMessage.getContent() + " \n " + textContent);
		}
	}
	
	// Setting tool use content
	private void setMessageToolUseContent(List<ToolCall> toolCallList, ToolUseBlock awsToolUse) throws JsonProcessingException {
		ToolCall mxToolCall = new ToolCall(getContext());
		
		mxToolCall.setArguments(getInputParamsJson(awsToolUse.input()));
		mxToolCall.setName(awsToolUse.name());
		mxToolCall.setToolCallId(awsToolUse.toolUseId());
		
		toolCallList.add(mxToolCall);
	}
	
	// Getting requested input parameters and storing them as Json string
	private String getInputParamsJson(Document awsDoc) throws JsonProcessingException {
		if (!awsDoc.isMap() || awsDoc.asMap().isEmpty()) {
			LOGGER.error("No Input Schema for Tool Use received.");
			return "{}";
		}
		// Returned map always has only one value because Function microflows have single parameter
		Map.Entry<String, Document> entry = awsDoc.asMap().entrySet().iterator().next();
		
		String key = entry.getKey();
		String value = entry.getValue().asString();
		
		Map<String, String> stringMap = Map.of(key, value);
		
		return MAPPER.writeValueAsString(stringMap);
	}
	
	private void setMxResponseExtension(Document awsDoc, ChatCompletionsResponse mxResponse) {
		
		Map<String, Document> map = awsDoc.asMap();
		
		for (Map.Entry<String, Document> entry : map.entrySet()) {
			RequestedResponseField responseField = new RequestedResponseField(getContext());
			responseField.setKey(entry.getKey());
			responseField.setValue(entry.getValue().toString());
			responseField.setRequestedResponseField_ChatCompletionsResponse(mxResponse);
		}
	}
	
	// END EXTRA CODE
}
