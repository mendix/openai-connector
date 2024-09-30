package openaiconnector.genaicommonsimpl;

import java.util.Map;

import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.core.IDataType;

public class FunctionMappingImpl {
	// Used in RequestMapping_ManipulateJson
	public static String getFirstInputParamName(String functionMicroflow) {
		Map<String, IDataType> inputParameters = Core.getInputParameters(functionMicroflow);
		if(inputParameters != null && !inputParameters.entrySet().isEmpty()) {
			return inputParameters.entrySet().iterator().next().getKey();
		} else {
			return null;
		}
	}
}