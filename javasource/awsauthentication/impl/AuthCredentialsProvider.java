package awsauthentication.impl;


import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;

import awsauthentication.proxies.Credentials;
import awsauthentication.proxies.TemporaryCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;

public class AuthCredentialsProvider {
	
	public static void setCredentialProvider(final AwsClientBuilder<?, ?> awsClientBuilder, final IContext context,
			final Credentials credentials, final String awsHeaderValue) throws CoreException {
		
		CredentialsProvider credentialsProvider = AuthCredentialsProvider.getCredentialsProvider(credentials);
		
		AwsCredentialsProvider awsCredentialsProvider = credentialsProvider.getAwsCredentialsProvider();
		
		awsClientBuilder.credentialsProvider(awsCredentialsProvider)
						.overrideConfiguration(ClientOverrideConfiguration.builder()
								.putHeader("User-Agent", awsHeaderValue)
								.build());
	}
	

	public static CredentialsProvider getCredentialsProvider(final Credentials credentials) {
		switch (credentials.getMendixObject().getType()) {
			case TemporaryCredentials.entityName: return new TemporaryCredentialsProvider((TemporaryCredentials)credentials);
			case Credentials.entityName: return new StaticCredentialsProvider(credentials);
		}

		throw new IllegalStateException("Unimplemented credentials provider:" + credentials.getMendixObject().getType());
	}
}
