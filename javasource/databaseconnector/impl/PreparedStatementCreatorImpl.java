package databaseconnector.impl;

import com.mendix.systemwideinterfaces.javaactions.parameters.IStringTemplate;
import com.mendix.systemwideinterfaces.javaactions.parameters.ITemplateParameter;
import com.mendix.systemwideinterfaces.javaactions.parameters.TemplateParameterType;
import databaseconnector.interfaces.PreparedStatementCreator;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

import static com.mendix.systemwideinterfaces.javaactions.parameters.TemplateParameterType.*;

public class PreparedStatementCreatorImpl implements PreparedStatementCreator {

	@Override
	public PreparedStatement create(String query, Connection connection) throws SQLException {
		return connection.prepareStatement(query);
	}

	@Override
	public PreparedStatement create(IStringTemplate sql, Connection connection) throws SQLException {
		List<ITemplateParameter> originalParameters = sql.getParameters();
		List<ITemplateParameter> queryParameters = new ArrayList<>();

		String queryTemplate = sql.replacePlaceholders((placeholderString, index) -> {
			queryParameters.add(originalParameters.get(index - 1));
			return "?";
		});

		PreparedStatement preparedStatement = connection.prepareStatement(queryTemplate);
		addPreparedStatementParameters(queryParameters, preparedStatement);
		return preparedStatement;
	}

	private void addPreparedStatementParameters(List<ITemplateParameter> queryParameters,
			PreparedStatement preparedStatement) throws SQLException, IllegalArgumentException {
		EnumMap<TemplateParameterType, Integer> sqlTypeMap = new EnumMap<>(TemplateParameterType.class);
		sqlTypeMap.put(INTEGER, Types.BIGINT);
		sqlTypeMap.put(STRING, Types.VARCHAR);
		sqlTypeMap.put(BOOLEAN, Types.BOOLEAN);
		sqlTypeMap.put(DECIMAL, Types.DECIMAL);
		sqlTypeMap.put(DATETIME, Types.TIMESTAMP);

		for (int i = 0; i < queryParameters.size(); i++) {
			ITemplateParameter parameter = queryParameters.get(i);
			Object parameterValue = parameter.getValue();

			if(parameterValue == null){
				preparedStatement.setNull(i + 1, sqlTypeMap.get(parameter.getParameterType()).intValue());
				continue;
			}
			addParameter(preparedStatement, i, parameter);
		}
	}

	private void addParameter(PreparedStatement preparedStatement,
												 int i,
												 ITemplateParameter parameter) throws SQLException {
		switch (parameter.getParameterType()) {
		case INTEGER:
				preparedStatement.setLong(i + 1, (long) parameter.getValue());
			break;
		case STRING:
				preparedStatement.setString(i + 1, (String) parameter.getValue());
			break;
		case BOOLEAN:
				preparedStatement.setBoolean(i + 1, (Boolean) parameter.getValue());
			break;
		case DECIMAL:
				preparedStatement.setBigDecimal(i + 1, (BigDecimal) parameter.getValue());
			break;
		case DATETIME:
				java.util.Date date = ((java.util.Date) parameter.getValue());
				preparedStatement.setTimestamp(i + 1, new Timestamp(date.getTime()));
			break;
		default:
			throw new IllegalArgumentException("Invalid parameter type: " + parameter.getParameterType());
		}
	}
}
