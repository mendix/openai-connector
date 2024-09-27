package conversationalui.impl;

import static java.util.Objects.requireNonNull;

import java.util.Map;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IDataType;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import conversationalui.proxies.ChatContext;
import conversationalui.proxies.ProviderConfig;

public class ProviderConfigImpl{
	
	public static void validateActionMicroflow(String ActionMicroflow) {
		if (ActionMicroflow == null || ActionMicroflow.isBlank()) {
			throw new IllegalArgumentException("ActionMicroflow is required.");
		}
		
		Map<String, IDataType> inputParameters = Core.getInputParameters(ActionMicroflow);
		if (inputParameters == null || inputParameters.size() != 1) {
			throw new IllegalArgumentException("ActionMicroflow " + ActionMicroflow + " should only have one input parameter of type " + ChatContext.getType() + ".");
		}
		
		if(Core.getMetaObject(inputParameters.entrySet().iterator().next().getValue().getObjectType()).isSubClassOf(ChatContext.getType()) == false) {
			throw new IllegalArgumentException("ActionMicroflow " + ActionMicroflow + " should have an input parameter of type " + ChatContext.getType()+ " or a specialization thereof.");			
		}

		if(Core.getReturnType(ActionMicroflow) == null || IDataType.DataTypeEnum.Boolean.equals(Core.getReturnType(ActionMicroflow).getType()) == false) {
			throw new IllegalArgumentException("ActionMicroflow " + ActionMicroflow + " should have a Boolean return value.");		
		}
	}
	
	public static void validateSpecialization(IMendixObject ProviderConfigSpecialization) throws Exception {
		requireNonNull(ProviderConfigSpecialization, "ProviderConfig Specialization is required.");
		if (!ProviderConfigSpecialization.isInstanceOf(ProviderConfig.entityName)){
			throw new IllegalArgumentException(ProviderConfig.entityName + " or a specialization of such is required.");
		}		
	}
	
	public static ProviderConfig createAndSetProviderConfigSpecialization(IContext context, String ProviderConfigSpecialization, String ActionMicroflow, String ProviderName) throws Exception {
		// Create an instance of the specialized ProviderConfig object
		IMendixObject providerConfigSpecialization = Core.instantiate(context, ProviderConfigSpecialization);
		ProviderConfigImpl.validateSpecialization(providerConfigSpecialization);

		// Use the specialized proxy class to wrap the generic IMendixObject to set attributes
		ProviderConfig providerConfig = ProviderConfig.initialize(context, providerConfigSpecialization);
		providerConfig.setActionMicroflow(ActionMicroflow);
		if (ProviderName != null && !ProviderName.isBlank()) {
			providerConfig.setDisplayName(ProviderName.trim());
		}
		return providerConfig;
	}
}