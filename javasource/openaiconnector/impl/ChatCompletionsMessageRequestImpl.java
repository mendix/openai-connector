package openaiconnector.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import openaiconnector.proxies.ChatCompletionsMessages;
import openaiconnector.proxies.ChatCompletionsMessageRequest;
import openaiconnector.proxies.ENUM_Role;

public class ChatCompletionsMessageRequestImpl {

	public static List<ChatCompletionsMessageRequest> retrieveMessageListByRole(
			ChatCompletionsMessages chatCompletionsMessages, ENUM_Role filterByRole, IContext context) {

		return Core.retrieveByPath(context, chatCompletionsMessages.getMendixObject(),
				ChatCompletionsMessageRequest.MemberNames.ChatCompletionsMessageRequest_ChatCompletionsMessages.toString())
				.stream().filter(mxObject -> {
					return filterMessageByRole(filterByRole, context, mxObject);
				}).map(mxObject -> ChatCompletionsMessageRequest.initialize(context, mxObject))
				.collect(Collectors.toList());
	}

	private static boolean filterMessageByRole(ENUM_Role filterByRole, IContext context, IMendixObject mxObject) {
		String messageRole = mxObject.getValue(context, ChatCompletionsMessageRequest.MemberNames.Role.name());
		return messageRole.equals(filterByRole.name());
	}
}
