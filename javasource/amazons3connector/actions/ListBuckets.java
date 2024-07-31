// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package amazons3connector.actions;

import static java.util.Objects.requireNonNull;
import java.util.Date;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import amazons3connector.impl.AmazonS3Client;
import amazons3connector.impl.MxLogger;
import amazons3connector.proxies.Bucket;
import amazons3connector.proxies.ListBucketsResponse;
import awsauthentication.proxies.AbstractRequest;
import software.amazon.awssdk.services.s3.S3Client;

public class ListBuckets extends CustomJavaAction<IMendixObject>
{
	private awsauthentication.proxies.ENUM_Region Region;
	private IMendixObject __Credentials;
	private awsauthentication.proxies.Credentials Credentials;
	private IMendixObject __ListBucketsRequest;
	private amazons3connector.proxies.ListBucketsRequest ListBucketsRequest;

	public ListBuckets(IContext context, java.lang.String Region, IMendixObject Credentials, IMendixObject ListBucketsRequest)
	{
		super(context);
		this.Region = Region == null ? null : awsauthentication.proxies.ENUM_Region.valueOf(Region);
		this.__Credentials = Credentials;
		this.__ListBucketsRequest = ListBucketsRequest;
	}

	@java.lang.Override
	public IMendixObject executeAction() throws Exception
	{
		this.Credentials = this.__Credentials == null ? null : awsauthentication.proxies.Credentials.initialize(getContext(), __Credentials);

		this.ListBucketsRequest = this.__ListBucketsRequest == null ? null : amazons3connector.proxies.ListBucketsRequest.initialize(getContext(), __ListBucketsRequest);

		// BEGIN USER CODE
		software.amazon.awssdk.services.s3.model.ListBucketsResponse awsResponse = null;
		try {
			// Validation of input parameters
			requireNonNull(this.Credentials, "AWS Credentials are required.");
			requireNonNull(this.Region, "AWS Region is required.");
			requireNonNull(this.ListBucketsRequest, "ListBucketsRequest is required.");
			// Client creation
			S3Client client = AmazonS3Client.getS3Client(Credentials, Region, this.ListBucketsRequest);
			// Retrieving the ListBucketsResponse and logging it
			awsResponse = client.listBuckets();
			LOGGER.debug("AWS response:", awsResponse);
		}
		catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
		return createMxResponse(awsResponse).getMendixObject();
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "ListBuckets";
	}

	// BEGIN EXTRA CODE
	private static final MxLogger LOGGER = new MxLogger(ListBuckets.class);
	
	private ListBucketsResponse createMxResponse(software.amazon.awssdk.services.s3.model.ListBucketsResponse awsResponse) {
		ListBucketsResponse mxResponse = new ListBucketsResponse(getContext());
		if (awsResponse.hasBuckets()) {
		awsResponse.buckets().forEach(awsBucket -> createMxBucket(mxResponse, awsBucket));
		} else {
			LOGGER.debug("No buckets were returned");
		}
		return mxResponse;
	}
	
	private void createMxBucket(ListBucketsResponse mxResponse, software.amazon.awssdk.services.s3.model.Bucket awsBucket) {
		Bucket mxBucket = new Bucket(getContext());
		mxBucket.setBucketName(awsBucket.name());
		mxBucket.setCreationDate(Date.from(awsBucket.creationDate()));
		mxBucket.setBucket_ListBucketsResponse(mxResponse);
	}
	// END EXTRA CODE
}
