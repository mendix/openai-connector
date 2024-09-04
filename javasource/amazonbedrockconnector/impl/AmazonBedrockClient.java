package amazonbedrockconnector.impl;
import com.mendix.systemwideinterfaces.MendixRuntimeException;

import awsauthentication.impl.AWSBuilderConfigurator;
import awsauthentication.proxies.AbstractRequest;
import awsauthentication.proxies.Credentials;
import awsauthentication.proxies.ENUM_Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.BedrockClientBuilder;
import software.amazon.awssdk.services.bedrockagent.BedrockAgentClient;
import software.amazon.awssdk.services.bedrockagent.BedrockAgentClientBuilder;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeAsyncClient;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeAsyncClientBuilder;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeClient;
import software.amazon.awssdk.services.bedrockagentruntime.BedrockAgentRuntimeClientBuilder;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClientBuilder;


public class AmazonBedrockClient {
		private static final MxLogger LOGGER = new MxLogger(AmazonBedrockClient.class);

		//TODO Replace X.Y.Z below with correct version nr and delete this line in rc-branch
		private static final String AWS_HEADER_VALUE = "Mendix-Bedrock-5.2.1";
		
		public static BedrockClient getBedrockClient(Credentials credentials, ENUM_Region region, AbstractRequest request) {
			
			try {
				var configurator = new AWSBuilderConfigurator<BedrockClientBuilder, BedrockClient>(BedrockClient.builder());
				configurator.setAbstractRequest(request)
					.setCredentials(credentials)
					.setRegion(region)
					.setAwsHeaderValue(AWS_HEADER_VALUE);			
				BedrockClientBuilder clientBuilder = configurator.configure();
				return clientBuilder.build();
			} catch (Exception e) {
				LOGGER.error("Exception in Java Code, Failed to Create Bedrock Client " + e.getMessage());
				throw new MendixRuntimeException(e);
			}
		}
		
	public static BedrockRuntimeClient getBedrockRuntimeClient(Credentials credentials, ENUM_Region region, AbstractRequest request) {
			
			try {
				var configurator = new AWSBuilderConfigurator<BedrockRuntimeClientBuilder, BedrockRuntimeClient>(BedrockRuntimeClient.builder());
				configurator.setAbstractRequest(request)
					.setCredentials(credentials)
					.setRegion(region)
					.setAwsHeaderValue(AWS_HEADER_VALUE);			
				BedrockRuntimeClientBuilder clientBuilder = configurator.configure();
				return clientBuilder.build();
			} catch (Exception e) {
				LOGGER.error("Exception in Java Code, Failed to Create Bedrock Client " + e.getMessage());
				throw new MendixRuntimeException(e);
			}
		}
		
		public static BedrockAgentClient getBedrockAgentClient(Credentials credentials, ENUM_Region region, AbstractRequest request) {
			
			try {
				var configurator = new AWSBuilderConfigurator<BedrockAgentClientBuilder, BedrockAgentClient>(BedrockAgentClient.builder());
				configurator.setAbstractRequest(request)
					.setCredentials(credentials)
					.setRegion(region)
					.setAwsHeaderValue(AWS_HEADER_VALUE);			
				BedrockAgentClientBuilder clientBuilder = configurator.configure();
				return clientBuilder.build();
			} catch (Exception e) {
				LOGGER.error("Exception in Java Code, Failed to Create Bedrock Client " + e.getMessage());
				throw new MendixRuntimeException(e);
			}
		}
		

	public static BedrockAgentRuntimeClient getBedrockAgentRuntimeClient(Credentials credentials, ENUM_Region region, AbstractRequest request) {
	
		try {
			var configurator = new AWSBuilderConfigurator<BedrockAgentRuntimeClientBuilder, BedrockAgentRuntimeClient>(BedrockAgentRuntimeClient.builder());
			configurator.setAbstractRequest(request)
				.setCredentials(credentials)
				.setRegion(region)
				.setAwsHeaderValue(AWS_HEADER_VALUE);			
			BedrockAgentRuntimeClientBuilder clientBuilder = configurator.configure();
			return clientBuilder.build();
		} catch (Exception e) {
			LOGGER.error("Exception in Java Code, Failed to Create Bedrock Client " + e.getMessage());
			throw new MendixRuntimeException(e);
		}
	}
	
	public static BedrockAgentRuntimeAsyncClient getBedrockAgentRuntimeAsyncClient(Credentials credentials, ENUM_Region region,
			AbstractRequest request) {

		try {
			var configurator = new AWSAsyncBuilderConfigurator<BedrockAgentRuntimeAsyncClientBuilder, BedrockAgentRuntimeAsyncClient>(
					BedrockAgentRuntimeAsyncClient.builder());
			configurator.setAbstractRequest(request).setCredentials(credentials).setRegion(region)
					.setAwsHeaderValue(AWS_HEADER_VALUE);
			BedrockAgentRuntimeAsyncClientBuilder clientBuilder = configurator.configure();
			return clientBuilder.build();
		} 
		catch (Exception e) {
			LOGGER.error("Exception in Java Code, Failed to Create Bedrock Client " + e.getMessage());
			throw new MendixRuntimeException(e);
		}
	}
}