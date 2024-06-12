package genaicommons.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class MappingUtilsImpl {
	
	public static ObjectNode createFunctionParametersNode(String functionMicroflow, ObjectMapper MAPPER) {
		String inputParamName = FunctionImpl.getFirstInputParamName(functionMicroflow);
		// FunctionImpl.getFirstInputParamName(functionMicroflow);
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

}
