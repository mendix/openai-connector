// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package conversationalui.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.mendix.core.Core;
import com.mendix.systemwideinterfaces.connectionbus.data.IDataTable;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;
import conversationalui.proxies.DeploymentSelection;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class JA_DeploymentSelection_GetList extends CustomJavaAction<java.util.List<IMendixObject>>
{
	public JA_DeploymentSelection_GetList(IContext context)
	{
		super(context);
	}

	@java.lang.Override
	public java.util.List<IMendixObject> executeAction() throws Exception
	{
		// BEGIN USER CODE
		String deploymentIdentifierAttribute = genaicommons.proxies.Usage.MemberNames.DeploymentIdentifier.toString();
		String usageEntity = genaicommons.proxies.Usage.entityName.toString();
		String oqlQuery = "SELECT DISTINCT " + deploymentIdentifierAttribute + " FROM " + usageEntity + " ORDER BY " + deploymentIdentifierAttribute;
		IDataTable oqlResult = Core.retrieveOQLDataTable(getContext(), oqlQuery);
		
		return oqlResult.getRows().stream().map(e -> {
			DeploymentSelection newDeploymentSelection = new DeploymentSelection(getContext());
			newDeploymentSelection.setDeploymentIdentifier(getContext(), e.getValue(getContext(), 0));
			return newDeploymentSelection;
		}).map(e -> e.getMendixObject()).collect(Collectors.toList());
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "JA_DeploymentSelection_GetList";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
