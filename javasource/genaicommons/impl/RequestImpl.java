package genaicommons.impl;

import genaicommons.proxies.ENUM_ToolChoice;
import genaicommons.proxies.Function;
import genaicommons.proxies.ToolCollection;
import genaicommons.proxies.Request;

public class RequestImpl{
	public static void setToolChoice(Request request, ToolCollection toolCollection, ENUM_ToolChoice toolChoice, Function function) {
		if(toolChoice != null) {
			request.setToolChoice(toolChoice); //Optional parameter
		}
		
		if(toolChoice.equals(ENUM_ToolChoice.tool)) {
			toolCollection.setToolCollection_ToolChoice(function);
		}
	}
}