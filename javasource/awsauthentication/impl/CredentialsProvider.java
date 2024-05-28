package awsauthentication.impl;

import java.time.Instant;

import com.mendix.core.CoreException;

import awsauthentication.proxies.Credentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

public abstract class CredentialsProvider {
	protected Credentials credentials;
	
	public CredentialsProvider(Credentials credentials) {
		this.credentials = credentials;
	}
	
	public abstract AwsCredentialsProvider getAwsCredentialsProvider() throws CoreException;
	public abstract Instant getExpiration() throws CoreException;
}
