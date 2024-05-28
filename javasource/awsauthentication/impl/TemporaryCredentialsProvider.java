package awsauthentication.impl;

import java.time.Instant;

import com.mendix.core.CoreException;

import awsauthentication.proxies.TemporaryCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;

public class TemporaryCredentialsProvider extends CredentialsProvider {

	public TemporaryCredentialsProvider(final TemporaryCredentials credentials) {
		super(credentials);
	}

	@Override
	public AwsCredentialsProvider getAwsCredentialsProvider() throws CoreException {
		TemporaryCredentials temporaryCredentials = (TemporaryCredentials)credentials;
		AwsSessionCredentials awsSessionCreds = AwsSessionCredentials.create(
				temporaryCredentials.getAccessKey(), 
				temporaryCredentials.getSecretAccessKey(),
				temporaryCredentials.getToken());
		
		return software.amazon.awssdk.auth.credentials.StaticCredentialsProvider.create(awsSessionCreds);
	}

	@Override
	public Instant getExpiration() throws CoreException {
		return null;
	}

	
	
}
