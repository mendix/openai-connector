package awsauthentication.impl;

import java.time.Instant;

import com.mendix.core.CoreException;

import awsauthentication.proxies.Credentials;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

public class StaticCredentialsProvider extends CredentialsProvider {

	public StaticCredentialsProvider(Credentials credentials) {
		super(credentials);
	}

	@Override
	public AwsCredentialsProvider getAwsCredentialsProvider() throws CoreException {
		AwsBasicCredentials awsBasicCreds = AwsBasicCredentials.create(
				credentials.getAccessKey(), 
				credentials.getSecretAccessKey());
		
		return software.amazon.awssdk.auth.credentials.StaticCredentialsProvider.create(awsBasicCreds);
	}

	@Override
	public Instant getExpiration() throws CoreException {
		return null;
	}

	
	
}
