package amazonbedrockconnector.genaicommons_impl;

import java.util.List;
import java.util.stream.Collectors;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import genaicommons.proxies.ENUM_MessageRole;
import genaicommons.proxies.Message;
import genaicommons.proxies.Request;

public class MessageImpl {

	public static List<Message> retrieveMessageListByRole(
			Request request, ENUM_MessageRole filterByRole, IContext context) {

		return Core.retrieveByPath(context, request.getMendixObject(),
				Request.MemberNames.Request_Message.toString())
				.stream().filter(mxObject -> {
					return filterMessageByRole(filterByRole, context, mxObject);
				}).map(mxObject -> Message.initialize(context, mxObject))
				.collect(Collectors.toList());
	}

	private static boolean filterMessageByRole(ENUM_MessageRole filterByRole, IContext context, IMendixObject mxObject) {
		String messageRole = mxObject.getValue(context, Message.MemberNames.Role.name());
		return messageRole.equals(filterByRole.name());
	}
}