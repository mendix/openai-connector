package openaiconnector.impl;

import java.util.Map;
import java.util.Set;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IDataType;

public class FunctionImpl {
	
	public static boolean validateActionMicroflow(String actionMicroflow) {
		Set<String> microflowNames = Core.getMicroflowNames();
		if(!microflowNames.contains(actionMicroflow)) {
			throw new IllegalArgumentException("ActionMicroflow " + actionMicroflow + " does not exists.");
		}
		
		Map<String, IDataType> inputParameters = Core.getInputParameters(actionMicroflow);
		if (inputParameters == null || inputParameters.size() != 1) {
			throw new IllegalArgumentException("ActionMicroflow " + actionMicroflow + " should only have one input parameter of type String.");
		}
		
		if(IDataType.DataTypeEnum.String.equals(inputParameters.entrySet().iterator().next().getValue().getType()) == false) {
			throw new IllegalArgumentException("ActionMicroflow " + actionMicroflow + " should have an input parameter of type String.");			
		}

		if(Core.getReturnType(actionMicroflow) == null || IDataType.DataTypeEnum.String.equals(Core.getReturnType(actionMicroflow).getType()) == false) {
			throw new IllegalArgumentException("ActionMicroflow " + actionMicroflow + " should have a String return value.");		
		}
		return true;
	}

}