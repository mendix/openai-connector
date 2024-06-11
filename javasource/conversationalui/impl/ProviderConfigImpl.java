package conversationalui.impl;

import java.util.Map;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IDataType;
import conversationalui.proxies.ChatContext;

public class ProviderConfigImpl{
	
	public static void validate(String ActionMicroflow) {
		if (ActionMicroflow == null || ActionMicroflow.isBlank()) {
			throw new IllegalArgumentException("ActionMicroflow is required.");
		}
		
		Map<String, IDataType> inputParameters = Core.getInputParameters(ActionMicroflow);
		if (inputParameters == null || inputParameters.size() != 1) {
			throw new IllegalArgumentException("ActionMicroflow " + ActionMicroflow + " should only have one input parameter of type " + ChatContext.getType() + ".");
		}
		
		if(ChatContext.getType().equals(inputParameters.entrySet().iterator().next().getValue().getObjectType()) == false) {
			throw new IllegalArgumentException("ActionMicroflow " + ActionMicroflow + " should have an input parameter of type " + ChatContext.getType()+ ".");			
		}

		if(Core.getReturnType(ActionMicroflow) == null || IDataType.DataTypeEnum.Boolean.equals(Core.getReturnType(ActionMicroflow).getType()) == false) {
			throw new IllegalArgumentException("ActionMicroflow " + ActionMicroflow + " should have a Boolean return value.");		
		}
	}	
}