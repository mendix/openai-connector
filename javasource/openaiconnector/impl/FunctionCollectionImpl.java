package openaiconnector.impl;

import openaiconnector.proxies.ENUM_ToolChoice;
import openaiconnector.proxies.Function;
import openaiconnector.proxies.FunctionCollection;

public class FunctionCollectionImpl{

	public static void setToolChoice(FunctionCollection functionCollection, ENUM_ToolChoice toolChoice, Function function) {
		functionCollection.setToolChoice(toolChoice); //Optional parameter
		
		if(toolChoice.equals(ENUM_ToolChoice.function)) {
			functionCollection.setFunctionCollection_Function_ToolChoice(function);
		}
	}
}