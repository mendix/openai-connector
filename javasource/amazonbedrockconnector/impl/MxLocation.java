package amazonbedrockconnector.impl;

import com.mendix.systemwideinterfaces.core.IContext;

import amazonbedrockconnector.proxies.ENUM_DataSourceType;
import amazonbedrockconnector.proxies.Location;
import amazonbedrockconnector.proxies.S3Location;

public class MxLocation {
	
	public static void setMxLocation(Location mxLocation ,software.amazon.awssdk.services.bedrockagentruntime.model.RetrievalResultLocation awsLocation, IContext context) {
		mxLocation.setDataSourceType(getMxDataSourceType(awsLocation.type()));
		
		S3Location mxS3Location = createMxS3Location(awsLocation.s3Location(), context);
		mxLocation.setLocation_S3Location(mxS3Location);
	}
	
	private static ENUM_DataSourceType getMxDataSourceType(software.amazon.awssdk.services.bedrockagentruntime.model.RetrievalResultLocationType awsDataSourceType) {
		switch (awsDataSourceType) {
		case S3:
			return ENUM_DataSourceType.S3;
		case UNKNOWN_TO_SDK_VERSION:
			LOGGER.debug("An unknown data source type was returned by the Amazon Bedrock service.");
			return ENUM_DataSourceType.UNKNOWN_TO_SDK_VERSION;
		default:
			return null;
		}
	}
	
	private static S3Location createMxS3Location(software.amazon.awssdk.services.bedrockagentruntime.model.RetrievalResultS3Location awsS3Location, IContext context) {
		S3Location mxS3Location = new S3Location(context);
		mxS3Location.setURI(awsS3Location.uri());
		return mxS3Location;
	}
	
	private static final MxLogger LOGGER = new MxLogger(MxLocation.class);

}
