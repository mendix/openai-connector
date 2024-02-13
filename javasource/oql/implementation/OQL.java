package oql.implementation;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.connectionbus.data.IDataColumnSchema;
import com.mendix.systemwideinterfaces.connectionbus.data.IDataRow;
import com.mendix.systemwideinterfaces.connectionbus.data.IDataTable;
import com.mendix.systemwideinterfaces.connectionbus.data.IDataTableSchema;
import com.mendix.systemwideinterfaces.connectionbus.requests.IParameterMap;
import com.mendix.systemwideinterfaces.connectionbus.requests.IRetrievalSchema;
import com.mendix.systemwideinterfaces.connectionbus.requests.types.IOQLTextGetRequest;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixIdentifier;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaAssociation;
import com.mendix.systemwideinterfaces.core.meta.IMetaObject;
import com.mendix.systemwideinterfaces.core.meta.IMetaPrimitive;
import com.mendix.systemwideinterfaces.core.meta.IMetaPrimitive.PrimitiveType;

public class OQL {
	static ThreadLocal<Map<String, Object>> nextParameters = new ThreadLocal<Map<String, Object>>();
	
	private static ILogNode logger = Core.getLogger(OQL.class.getSimpleName());
	
	public static Map<String, Object> getNextParameters() {
		if (nextParameters.get() == null) {
			nextParameters.set(new HashMap<String, Object>());
		}
		return nextParameters.get();
	}
	
	public static void resetParameters() {
		nextParameters.set(new HashMap<String, Object>());;
	}
	
	public static void addParameter(String name, Object value) {
		getNextParameters().put(name, value);
	}
	
	public static Long countRowsOQL(IContext context, String statement, Long amount, Map<String, Object> parameters)
		throws CoreException {
		IOQLTextGetRequest request = Core.createOQLTextGetRequest();
		request.setQuery(statement);
		IParameterMap parameterMap = request.createParameterMap();
		for (Entry<String, Object> entry : OQL.getNextParameters().entrySet()) {
			parameterMap.put(entry.getKey(), entry.getValue());
		}
		request.setParameters(parameterMap);
		
		IRetrievalSchema schema = Core.createRetrievalSchema();
		schema.setAmount(amount);
		request.setRetrievalSchema(schema);

		logger.debug("Executing query");
		IDataTable results = Core.retrieveOQLDataTable(context, request);
		return (long) results.getRowCount();
	}
	
	public static List<IMendixObject> executeOQL(IContext context, String statement, String returnEntity, 
			Long amount, Long offset, Map<String, Object> parameters) throws CoreException {
		IOQLTextGetRequest request;
		try {
			request = Core.createOQLTextGetRequestFromDataSet(statement); 
		} catch (IllegalArgumentException e) {
			request = Core.createOQLTextGetRequest();
			request.setQuery(statement);
		}
		
		IParameterMap parameterMap = request.createParameterMap();
		for (Entry<String, Object> entry : OQL.getNextParameters().entrySet()) {
			parameterMap.put(entry.getKey(), entry.getValue());
		}
		request.setParameters(parameterMap);
		
		IRetrievalSchema schema = Core.createRetrievalSchema();
		schema.setOffset(offset != null ? offset : 0);
		schema.setAmount(amount != null ? amount : 0);
		request.setRetrievalSchema(schema);
		
		List<IMendixObject> result = new LinkedList<IMendixObject>();
		logger.debug("Executing query\n:" + statement);
		IDataTable results = Core.retrieveOQLDataTable(context, request);
		logger.debug("Mapping " + results.getRowCount() + " results.");
		IDataTableSchema tableSchema = results.getSchema();
		for (IDataRow row : results.getRows()) {
			IMendixObject targetObj = Core.instantiate(context, returnEntity);
			for (int i = 0; i < tableSchema.getColumnCount(); i++) {
				IDataColumnSchema columnSchema = tableSchema.getColumnSchema(i);
				logger.trace("Mapping column "+ columnSchema.getName());
				Object value = row.getValue(context, i);
				IMetaObject targetMeta = targetObj.getMetaObject();
				
				if (value == null) {
					IMetaPrimitive primitive = targetMeta.getMetaPrimitive(columnSchema.getName());
					if (primitive != null) {
						targetObj.setValue(context, columnSchema.getName(), null);
					} else {
						if (getAssociation(targetObj, columnSchema) == null) {
							throw new NullPointerException("Null value found " + columnSchema.getName() + 
									" was not found as association or attribute.");
						}
					} 
					
				} else {
					
					if (value instanceof IMendixIdentifier) {
						logger.trace("Treating as association");				
						IMetaAssociation association = getAssociation(targetObj, columnSchema);
						if (association != null) {
							targetObj.setValue(context, association.getName(), value);
						} else {
							throw new NullPointerException("Could not find result association " + columnSchema.getName() + " in target object.");
						}
					} else {
						logger.trace("Treating as value");
						IMetaPrimitive primitive = targetMeta.getMetaPrimitive(columnSchema.getName());
						
						if (primitive == null) {
							throw new NullPointerException("Could not find result attribute " + columnSchema.getName() + " in target object.");
						}
						
						if (value instanceof Integer && primitive.getType() == PrimitiveType.Long) {
							value = (Long) ((Integer) value).longValue();
						} else if (value instanceof Long && primitive.getType() == PrimitiveType.Integer) {
							value = Integer.parseInt(((Long) value).toString()); // not so happy way of conversion
						} else if (value instanceof Double && primitive.getType() == PrimitiveType.Decimal) {
							value = new BigDecimal((Double) value);
						}
						targetObj.setValue(context, columnSchema.getName(), value);
					}
				}
				
			}
			result.add(targetObj);
		}
		
		return result;
	}

	private static IMetaAssociation getAssociation(IMendixObject targetObj, IDataColumnSchema columnSchema) {
		/* Escaping an alias as described at https://docs.mendix.com/refguide7/oql-select-clause
		 * leads to an error when using dots e.g. (OQL.ExamplePerson_ExamplePersonResult).
		 * Therefore this action accepts the ExamplePerson_ExamplePersonResult part and searches for the
		 * association that has this in it.
		 */
		for (IMetaAssociation association : targetObj.getMetaObject().getDeclaredMetaAssociationsParent()) {
			String name = association.getName();
			name = name.substring(name.indexOf('.') + 1);
			if (name.equals(columnSchema.getName())) {
				return association;
			}
		}
		return null;
	}
	
}
