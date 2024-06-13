package genaicommons.impl;

import genaicommons.proxies.Request;
import genaicommons.proxies.ToolCollection;

import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;

public class ToolCollectionImpl {

	public static ToolCollection getOrCreateToolCollection(IContext context, Request request) throws CoreException {
		if (request.getRequest_ToolCollection() != null) {
			return request.getRequest_ToolCollection();
		} else {
			ToolCollection toolCollection = new ToolCollection(context);
			request.setRequest_ToolCollection(toolCollection);
			return toolCollection;
		}
	}
}