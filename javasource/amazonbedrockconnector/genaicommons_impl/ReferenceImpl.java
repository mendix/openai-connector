package amazonbedrockconnector.genaicommons_impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mendix.systemwideinterfaces.core.IContext;

import amazonbedrockconnector.impl.MxLocation;
import amazonbedrockconnector.proxies.ENUM_DataSourceType;
import genaicommons.proxies.ENUM_SourceType;
import genaicommons.proxies.Reference;
import software.amazon.awssdk.services.bedrockagentruntime.model.Citation;
import software.amazon.awssdk.services.bedrockagentruntime.model.RetrievalResultLocation;

public class ReferenceImpl {
	
	// Create References and Citations from AWS Citations (Retrieve And Generate, Agents)
	public static List<Reference> getMxReferences(List<Citation> awsCitations, IContext ctx) {
		
		List<Reference> mxReferences = new ArrayList<>();
		
		awsCitations.forEach(awsCitation -> {
			genaicommons.proxies.Citation mxCitation = new genaicommons.proxies.Citation(ctx);
			setMxCitation(mxCitation, awsCitation);
			
			List<genaicommons.proxies.Citation> singleCitationList = Arrays.asList(mxCitation);
			if (awsCitation.hasRetrievedReferences()) {
				
				awsCitation.retrievedReferences().forEach(awsReference -> {
					Reference mxReference = new Reference(ctx);
					
					ENUM_DataSourceType mxDataSourceType = MxLocation.getMxDataSourceType(awsReference.location().type());
					String sourceUrl = getSourceUrl(mxDataSourceType, awsReference.location());
					ENUM_SourceType sourceType = getSourceType(mxDataSourceType);
					
					setMxReference(mxReference, awsReference.content().text(), sourceUrl, sourceType, awsReference.location());
					mxReference.setReference_Citation(singleCitationList);
					mxReferences.add(mxReference);
				});
			}
		});
		
		return mxReferences;
	}
	
	public static String getSourceUrl(ENUM_DataSourceType type, RetrievalResultLocation awsLocation) {
		switch (type) {
		case S3: {
			return awsLocation.s3Location().uri();
		}
		// TODO: This needs to be extended with other location types when updating the SDK
		default:
			return null;
		}
	}
	
	public static ENUM_SourceType getSourceType(ENUM_DataSourceType type) {
		switch (type) {
		case S3: {
			return ENUM_SourceType.Url;
		}
		// TODO: This needs to be extended with other location types when updating the SDK
		default:
			return null; // SourceType attribute will be empty for unknown types. In the future, a "Unknown / Unsupported" Enum value might be introduced in GenAI Commons
		}
	}
	
	public static void setMxReference(Reference mxReference, String content, String sourceUrl, ENUM_SourceType sourceType, RetrievalResultLocation awsLocation) {
		mxReference.setContent(content);
		mxReference.setSource(sourceUrl);
		mxReference.setSourceType(sourceType);
		mxReference.setTitle(getReferenceTitle(sourceUrl, awsLocation));
	}
	
	private static void setMxCitation(genaicommons.proxies.Citation mxCitation, Citation awsCitation) {
		mxCitation.setStartIndex(awsCitation.generatedResponsePart().textResponsePart().span().start());
		mxCitation.setEndIndex(awsCitation.generatedResponsePart().textResponsePart().span().end());
		mxCitation.setText(awsCitation.generatedResponsePart().textResponsePart().text());
	}
	
	private static String getReferenceTitle(String url, RetrievalResultLocation awsLocation) {
		if (url == null || url.isBlank()) {
			return awsLocation.typeAsString();
		}
		
		if (url.contains("/")) {
			int last = url.lastIndexOf("/");
			return url.substring(last + 1);
			
		} else return url;
		
	}

}
