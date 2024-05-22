package genaicommons.impl;

import genaicommons.proxies.ENUM_ToolChoice;
import genaicommons.proxies.Tool;
import genaicommons.proxies.ToolCollection;

public class ToolCollectionImpl{

	public static void setToolChoice(ToolCollection toolCollection, ENUM_ToolChoice toolChoice, Tool tool) {
		toolCollection.setToolChoice(toolChoice); //Optional parameter
		
		if(toolChoice.equals(ENUM_ToolChoice.tool)) {
			toolCollection.setToolCollection_ToolChoice(tool);
		}
	}
}