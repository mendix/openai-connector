package csv.impl;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;

public class CSV {
	public static final String LOGNODE = "CSV";
	public static final String CONTEXT_READER_OBJ = "CSVReader";
	public static final String CONTEXT_WRITER_OBJ = "CSVWriter"; 
	private static ILogNode logger;
	
	
	public static ILogNode getLogger() {
		if (logger == null) {
			logger = Core.getLogger(LOGNODE);
		}
		return logger;
	}
	
	public static DecimalFormat getDecimalFormat(String decimalSeparator, String groupingSeparator) {
		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols();
		if (decimalSeparator != null) {
			otherSymbols.setDecimalSeparator(decimalSeparator.charAt(0));
		}
		if (groupingSeparator != null) {
			otherSymbols.setGroupingSeparator(groupingSeparator.charAt(0));
		}
		 
		DecimalFormat df = new DecimalFormat();
		df.setDecimalFormatSymbols(otherSymbols);
		df.setParseBigDecimal(true);
		
		return df;
	}
}
