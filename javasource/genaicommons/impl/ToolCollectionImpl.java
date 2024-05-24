package genaicommons.impl;

import genaicommons.proxies.ENUM_ToolChoice;
import genaicommons.proxies.Request;
import genaicommons.proxies.Tool;
import genaicommons.proxies.ToolCollection;

import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;

public class ToolCollectionImpl{

	public static void setToolChoice(ToolCollection toolCollection, ENUM_ToolChoice toolChoice, Tool tool) {
		toolCollection.setToolChoice(toolChoice); //Optional parameter
		
		if(toolChoice.equals(ENUM_ToolChoice.tool)) {
			toolCollection.setToolCollection_ToolChoice(tool);
		}
	}
	
	public static ToolCollection getOrCreateToolCollection(IContext context, Request request) throws CoreException {
		if(request.getRequest_ToolCollection() != null) {
			return request.getRequest_ToolCollection();
		} else {
			ToolCollection toolCollection = new ToolCollection(context);
			request.setRequest_ToolCollection(toolCollection);
			return toolCollection;
		}
	}
}