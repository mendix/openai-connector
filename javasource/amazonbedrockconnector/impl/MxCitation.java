package amazonbedrockconnector.impl;

import com.mendix.systemwideinterfaces.core.IContext;

import amazonbedrockconnector.proxies.CitationRetrievedReference;
import amazonbedrockconnector.proxies.GeneratedResponsePart;
import software.amazon.awssdk.services.bedrockagentruntime.model.Citation;

public class MxCitation {
	
	public static void setMxCitations(software.amazon.awssdk.services.bedrockagentruntime.model.Citation awsCitation, amazonbedrockconnector.proxies.Citation mxCitation, IContext context) {
		setMxGeneratedResponsePart(awsCitation, mxCitation, context);
		
		if (awsCitation.hasRetrievedReferences()) {
			awsCitation.retrievedReferences().forEach(awsRetrievedReference -> {
				CitationRetrievedReference mxRetrievedReference = new CitationRetrievedReference(context);
				MxRetrievedReference.setMxRetrievedReference(mxRetrievedReference, awsRetrievedReference, context);
				mxRetrievedReference.setCitationRetrievedReference_Citation(mxCitation);
			});
		}
		
	}

	private static void setMxGeneratedResponsePart(Citation awsCitation, amazonbedrockconnector.proxies.Citation mxCitation, IContext Context) {
		
		GeneratedResponsePart mxGenerateResponsePart = new GeneratedResponsePart(Context);
		mxGenerateResponsePart.setText(awsCitation.generatedResponsePart().textResponsePart().text());
		mxGenerateResponsePart.setStart(awsCitation.generatedResponsePart().textResponsePart().span().start());
		mxGenerateResponsePart.setEnd(awsCitation.generatedResponsePart().textResponsePart().span().end());
		mxGenerateResponsePart.setGeneratedResponsePart_Citation(mxCitation);
	}

}

