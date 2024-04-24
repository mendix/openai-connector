package openaiconnector.impl;

import java.util.List;
import java.util.stream.Collectors;
import com.mendix.core.Core;
import openaiconnector.proxies.ChatCompletionsMessages;
import openaiconnector.proxies.ChatCompletionsMessageRequest;
import openaiconnector.proxies.ENUM_Role;
import com.mendix.systemwideinterfaces.core.IContext;

public class ChatCompletionsMessageRequestImpl {

	public static List<ChatCompletionsMessageRequest> retrieveMessageListByRole(
			ChatCompletionsMessages chatCompletionsMessages, ENUM_Role filterByRole, IContext context) {

		return Core.retrieveByPath(context, chatCompletionsMessages.getMendixObject(),
				ChatCompletionsMessageRequest.MemberNames.ChatCompletionsMessageRequest_ChatCompletionsMessages.toString())
				.stream().filter(mxObject -> {
					String messageRole = mxObject.getValue(context, ChatCompletionsMessageRequest.MemberNames.Role.name());
					return messageRole.equals(filterByRole.name());
				}).map(mxObject -> ChatCompletionsMessageRequest.initialize(context, mxObject))
				.collect(Collectors.toList());
	}
}
