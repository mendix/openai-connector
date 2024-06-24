package amazonbedrockconnector.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import com.mendix.core.CoreException;

import awsauthentication.impl.AbstractRequestHelper;
import awsauthentication.impl.AuthCredentialsProvider;
import awsauthentication.impl.Utils;
import awsauthentication.proxies.AbstractRequest;
import awsauthentication.proxies.Credentials;
import awsauthentication.proxies.ENUM_Region;
import awsauthentication.proxies.TemporaryCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.awscore.client.builder.AwsAsyncClientBuilder;
import software.amazon.awssdk.awscore.client.builder.AwsClientBuilder;
import software.amazon.awssdk.core.client.config.ClientOverrideConfiguration;
import software.amazon.awssdk.regions.Region;

/**
 * This AWSBuilderConfigurator class for configuring an AWS Client Builder. 
 * Use this to simplify the creation of the AWS Service clients with all the functionality that is already included 
 * in the authentication module. 
 * 
 * An example for how to use the Generator to generate a LambdaClientBuilder using the AbstractRequest, Credentials, and Region provided in the AWSAuthentication module:
 * <br><pre>
 			var configurator = new AWSBuilderConfigurator<LambdaClientBuilder, LambdaClient>(LambdaClient.builder());
			configurator.setAbstractRequest(request)
				.setCredentials(credentials)
				.setRegion(region);
			LambdaClientBuilder clientBuilder = configurator.configure();
 </pre>		
 *
 * The clientBuilder can then be even more configured, should that be needed. 
 * 
 * The client itself can be retrieved with the following: 
 * LambdaClient client = clientBuilder.build();
 * 
 * @param <BuilderT> Type/Generics param: Specific class of builder
 * @param <ClientT>  Type/Generics param: Specific class of client to be build
 */
public class AWSAsyncBuilderConfigurator<BuilderT extends AwsAsyncClientBuilder<BuilderT,ClientT> & AwsClientBuilder<BuilderT,ClientT>, ClientT>{
	@SuppressWarnings("unused")
	private static final MxLogger LOGGER = new MxLogger(AWSAsyncBuilderConfigurator.class);
	
	private static final String AWS_HEADER_VALUE = "Mendix-Authentication-3.0.0";
	
	private AbstractRequest abstractRequest;
	private ENUM_Region region;
	private Credentials credentials;
	private String awsHeaderValue;
	private BuilderT builder;
				
	public AWSAsyncBuilderConfigurator(final BuilderT builderT) {
		this.builder = builderT;
	}

	/**
	 * This allows for the creation of clients with custom settings. 
	 * All non null values that on the abstract request will be set onto the selected client. If the AbstractRequest itself is null, the default client will be used.
	 * The default client is the ApacheHttpClient
	 * 
	 * WARNING:
	 * This sets the overrideConfiguration, httpClient, and endpointOverride according to the values found in the AbstractRequest entity. If you later override these 
	 * within the clientBuilder, the settings set on by adding the AbstractRequest here will be lost. Likewise, if you have set custom settings to you client builder for these
	 * attributes, this will override them.
	 * 
	 * @param abstractRequest Request object that the client is based on. The custom settings will be added to the client when the builder is built
	 * @return ClientGeneratorBuilder<BuilderT>
	 */
	public AWSAsyncBuilderConfigurator<BuilderT,ClientT> setAbstractRequest(final AbstractRequest abstractRequest) {
		this.abstractRequest = abstractRequest;
		return this;
	}
	
	/**
	 * Will set the aws region on the builder. Also coverts from the Mendix region enum to the AWS region enum
	 * 
	 * WARNING:
	 * This will set the aws region on the client builder. If the clientBuilder.region function is later called, this will override the input passed here. 
	 * Likewise, if a region has already been set on the clientBuilder, adding a region here will override the value.
	 * 
	 * @param region AWS region you want to connect to
	 * @return ClientGeneratorBuilder<BuilderT>
	 */
	public AWSAsyncBuilderConfigurator<BuilderT,ClientT> setRegion(final ENUM_Region region) {
		this.region = region;
		return this;
	}
	
	/**
	 * Adds the credentials. When the client builder is built, the credentials will be processed and transformed into the AWS credentials object
	 * @param credentials
	 * @return ClientGeneratorBuilder<BuilderT>
	 */
	public AWSAsyncBuilderConfigurator<BuilderT,ClientT> setCredentials(final Credentials credentials) {
		this.credentials = credentials;
		return this;
	}
			
	public AWSAsyncBuilderConfigurator<BuilderT,ClientT> setAwsHeaderValue(final String awsHeaderValue) {
		
		if (this.credentials instanceof TemporaryCredentials) {
			this.awsHeaderValue = awsHeaderValue + "; " + AWS_HEADER_VALUE + "; Temporary Credentials";
		}
		else
		{
			this.awsHeaderValue = awsHeaderValue + "; " + AWS_HEADER_VALUE + "; Static Credentials";
		}
		
		return this;
	}
	
	/**
	 * Returns the builder of the type that was passed along as an input
	 * @return BuilderT
	 * @throws CoreException
	 * @throws UnknownHostException
	 * @throws URISyntaxException
	 */
	public BuilderT configure() throws CoreException, UnknownHostException, URISyntaxException {
		return getBuilder();
	}

	/**
	 * This function will generate the client based on the settings passed. It will take care of converting the Mendix Credentials object into the format that AWS expects,
	 * setting the region, and setting the values that have been passed along in the AbstractRequest and set those on the builder.
	 * The builder is then returned, should anything that is not a part of all connectors need to be done.
	 * @throws CoreException 
	 * @throws URISyntaxException 
	 * @throws UnknownHostException 
	*/
	private BuilderT getBuilder() throws CoreException, UnknownHostException, URISyntaxException {
		if (region != null) {
			Region awsRegion = Utils.convertAWSRegion(region);
			builder.region(awsRegion);
		}
		if (credentials != null) {
			AwsCredentialsProvider awsCredentialsProvider = AuthCredentialsProvider.getCredentialsProvider(credentials)
					.getAwsCredentialsProvider();
			builder.credentialsProvider(awsCredentialsProvider);
		}
		
		setAbstractRequestToClientBuilder();
		
		return builder;
	}
	
	private void setAbstractRequestToClientBuilder() throws CoreException, URISyntaxException, UnknownHostException {			
		ClientOverrideConfiguration clientOverrideConfiguration = AbstractRequestHelper.getClientOverrideConfiguration(abstractRequest, awsHeaderValue);
		if (clientOverrideConfiguration!= null) {
			builder.overrideConfiguration(clientOverrideConfiguration);
		}
		URI endpointOverride = AbstractRequestHelper.getEndpointOverride(abstractRequest);
		if (endpointOverride != null) {
			builder.endpointOverride(endpointOverride);
		}
		//TODO Use specific async sdkhttpclient with its own settings: but to implement this, the AbstractRequest entity in Auth Connector has to be extended
		//with Async http client configs, so more child entities below the AbstractHttpConfig, 
		// e.g. AwsCrtAsyncHttpConfig (for settings of AwsCrtAsyncHttpClient) and NettyNioAsyncHttpConfig (for settings of NettyNioAsyncHttpClient).
//		SdkHttpClient sdkHttpClient = AbstractRequestHelper.getSdkHttpClient(abstractRequest);
//		if (sdkHttpClient != null) {
//			//builder.httpClient(sdkHttpClient);
//		}
	}
}