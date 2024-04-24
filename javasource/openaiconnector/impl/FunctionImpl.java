package openaiconnector.impl;

import java.util.Map;
import java.util.Set;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IDataType;

public class FunctionImpl {
	
	public static boolean validateFunctionMicroflow(String FunctionMicroflow) {
		Set<String> microflowNames = Core.getMicroflowNames();
		if(!microflowNames.contains(FunctionMicroflow)) {
			throw new IllegalArgumentException("FunctionMicroflow " + FunctionMicroflow + " does not exists.");
		}
		
		Map<String, IDataType> inputParameters = Core.getInputParameters(FunctionMicroflow);
		if (inputParameters == null || inputParameters.size() != 1) {
			throw new IllegalArgumentException("FunctionMicroflow " + FunctionMicroflow + " should only have one input parameter of type String.");
		}
		
		if(IDataType.DataTypeEnum.String.equals(inputParameters.entrySet().iterator().next().getValue().getType()) == false) {
			throw new IllegalArgumentException("FunctionMicroflow " + FunctionMicroflow + " should have an input parameter of type String.");			
		}

		if(Core.getReturnType(FunctionMicroflow) == null || IDataType.DataTypeEnum.String.equals(Core.getReturnType(FunctionMicroflow).getType()) == false) {
			throw new IllegalArgumentException("FunctionMicroflow " + FunctionMicroflow + " should have a String return value.");		
		}
		return true;
	}

}