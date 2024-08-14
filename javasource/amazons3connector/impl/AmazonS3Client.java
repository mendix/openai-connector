package amazons3connector.impl;

import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.MendixRuntimeException;

import amazons3connector.proxies.AbstractS3Request;
import awsauthentication.impl.AWSBuilderConfigurator;
import awsauthentication.proxies.Credentials;
import awsauthentication.proxies.ENUM_Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;


public class AmazonS3Client {
		private static final MxLogger LOGGER = new MxLogger(AmazonS3Client.class);

		private static final String AWS_HEADER_VALUE = "Mendix-S3-4.0.0";
		
		public static S3Client getS3Client(Credentials credentials, ENUM_Region region, AbstractS3Request request) throws CoreException {
			
			try {
				var configurator = new AWSBuilderConfigurator<S3ClientBuilder, S3Client>(S3Client.builder());
				configurator.setAbstractRequest(request)
					.setCredentials(credentials)
					.setRegion(region)
					.setAwsHeaderValue(AWS_HEADER_VALUE);			
				S3ClientBuilder clientBuilder = configurator.configure();
				
				if (request.getAbstractS3Request_S3Configuration() != null) {
					clientBuilder.serviceConfiguration(createS3Configuration(request.getAbstractS3Request_S3Configuration()));
				}
				
				return clientBuilder.build();
			} catch (Exception e) {
				LOGGER.error("Exception in Java Code, Failed to Create S3 Client " + e.getMessage());
				throw new MendixRuntimeException(e);
			}
		}
		
		private static S3Configuration createS3Configuration(amazons3connector.proxies.S3Configuration mxS3Configuration) {
			S3Configuration awsS3Configuration = S3Configuration.builder()
				.pathStyleAccessEnabled(mxS3Configuration.getPathStyleAccessEnabled())
				.build();
			
			return awsS3Configuration;
		}
}