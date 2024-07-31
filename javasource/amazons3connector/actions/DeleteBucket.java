// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package amazons3connector.actions;

import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import static java.util.Objects.requireNonNull;
import amazons3connector.impl.AmazonS3Client;
import amazons3connector.impl.MxLogger;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

public class DeleteBucket extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __Credentials;
	private awsauthentication.proxies.Credentials Credentials;
	private IMendixObject __DeleteBucketRequest;
	private amazons3connector.proxies.DeleteBucketRequest DeleteBucketRequest;
	private awsauthentication.proxies.ENUM_Region Region;

	public DeleteBucket(IContext context, IMendixObject Credentials, IMendixObject DeleteBucketRequest, java.lang.String Region)
	{
		super(context);
		this.__Credentials = Credentials;
		this.__DeleteBucketRequest = DeleteBucketRequest;
		this.Region = Region == null ? null : awsauthentication.proxies.ENUM_Region.valueOf(Region);
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.Credentials = this.__Credentials == null ? null : awsauthentication.proxies.Credentials.initialize(getContext(), __Credentials);

		this.DeleteBucketRequest = this.__DeleteBucketRequest == null ? null : amazons3connector.proxies.DeleteBucketRequest.initialize(getContext(), __DeleteBucketRequest);

		// BEGIN USER CODE
		software.amazon.awssdk.services.s3.model.DeleteBucketResponse awsResponse = null;
		try {
			// Validation of input parameters
			requireNonNull(this.Credentials, "AWS Credentials are required.");
			requireNonNull(this.DeleteBucketRequest, "DeleteBucketRequest is required.");
			requireNonNull(this.Region, "AWS Region is required.");
			
			// Validation of the DeleteBucketRequest object
			validateRequest();
			
			// Building the AWS DeleteBucketRequest
			software.amazon.awssdk.services.s3.model.DeleteBucketRequest awsRequest = createAWSRequest();
			
			// Log the request
			LOGGER.debug("AWS request:", awsRequest);
			
			// Client creation
			S3Client client = AmazonS3Client.getS3Client(Credentials, Region, DeleteBucketRequest);
			
			// Invoke action on AWS client
			awsResponse = client.deleteBucket(awsRequest);
			
			// Log the response
			LOGGER.debug("AWS response:", awsResponse);
		}
		catch (NoSuchBucketException e) {
			LOGGER.error("The specified bucket (" + DeleteBucketRequest.getBucketName() + ") cannot be located in the specified region (" + Region + ")");
			throw e;
			}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
			}
		
		return awsResponse.sdkHttpResponse().isSuccessful();
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "DeleteBucket";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(DeleteBucket.class);
	
	private void validateRequest() throws CoreException {	
	// Validate bucket name 
	requireNonNull(DeleteBucketRequest.getBucketName(), "Bucket name is required");
	if (DeleteBucketRequest.getBucketName().isBlank()) {
		throw new IllegalArgumentException("Bucket name cannot be blank");
		}
	}
	
	private software.amazon.awssdk.services.s3.model.DeleteBucketRequest createAWSRequest() {
		software.amazon.awssdk.services.s3.model.DeleteBucketRequest awsRequest = software.amazon.awssdk.services.s3.model.DeleteBucketRequest.builder()
			.bucket(DeleteBucketRequest.getBucketName())
			.build();
		return awsRequest;
	}
	// END EXTRA CODE
}
