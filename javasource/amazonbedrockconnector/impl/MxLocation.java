package amazonbedrockconnector.impl;

import com.mendix.systemwideinterfaces.core.IContext;

import amazonbedrockconnector.proxies.ENUM_DataSourceType;
import amazonbedrockconnector.proxies.Location;
import amazonbedrockconnector.proxies.S3Location;

public class MxLocation {
	
	public static void setMxLocation(Location mxLocation ,software.amazon.awssdk.services.bedrockagentruntime.model.RetrievalResultLocation awsLocation, IContext context) {
		ENUM_DataSourceType mxDataSourceType = getMxDataSourceType(awsLocation.type());	
		mxLocation.setDataSourceType(mxDataSourceType);
		
		switch (mxDataSourceType) {
		case S3: {
			S3Location mxS3Location = createMxS3Location(awsLocation.s3Location(), context);
			mxLocation.setLocation_S3Location(mxS3Location);
			break;
		}
		// TODO: When updating the SDK, this can be expanded to other location types
		// TODO: When everything talks GenAICommons: Check which BedrockImplementation classes (+ and corresponding entities) are still in use. 
		default:
			break;
		}
	}
	
	public static ENUM_DataSourceType getMxDataSourceType(software.amazon.awssdk.services.bedrockagentruntime.model.RetrievalResultLocationType awsDataSourceType) {
		switch (awsDataSourceType) {
		case S3:
			return ENUM_DataSourceType.S3;
		// TODO: Add other location types after updating the SDK and ENUM
		default:
			LOGGER.warn("A knowledge base with a currently unsupported source type was queried. Not all information such as the source URL of the returned references can be mapped to Mendix. ");
			return ENUM_DataSourceType.UNKNOWN_TO_SDK_VERSION;
		}
	}
	
	private static S3Location createMxS3Location(software.amazon.awssdk.services.bedrockagentruntime.model.RetrievalResultS3Location awsS3Location, IContext context) {
		S3Location mxS3Location = new S3Location(context);
		mxS3Location.setURI(awsS3Location.uri());
		return mxS3Location;
	}
	
	private static final MxLogger LOGGER = new MxLogger(MxLocation.class);

}
