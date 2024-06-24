package amazonbedrockconnector.impl;

import com.mendix.systemwideinterfaces.core.IContext;

import amazonbedrockconnector.proxies.ReferenceLocation;
import software.amazon.awssdk.services.bedrockagentruntime.model.RetrievedReference;

public class MxRetrievedReference {
	
public static void setMxRetrievedReference(amazonbedrockconnector.proxies.RetrievedReference mxRetrievedReference, RetrievedReference awsRetrievedReference, IContext context) {
		
		mxRetrievedReference.setText(awsRetrievedReference.content().text());
		ReferenceLocation mxLocation = new ReferenceLocation(context);
		MxLocation.setMxLocation(mxLocation, awsRetrievedReference.location(), context);
		mxRetrievedReference.setReferenceLocation_RetrievedReference(mxLocation);
		
	}

}
