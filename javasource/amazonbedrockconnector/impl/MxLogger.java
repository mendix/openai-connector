package amazonbedrockconnector.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;

/**
 * This class can be used to log messages to the Mendix logging system.
 * All messages will be logged with the same Mendix Log Node, so it's in line with the Mendix way of logging.
 * This Mendix Log Node name is defined by the constant variable MX_LOG_NODE_NAME. 
 * 
 * Usage of this class is to create a new instance and use this instance to log messages.
 * Creation of a new instance requires the class where the logging comes from as parameter (of the constructor).
 * <pre>
 * public class MyClass {
 *   private final static MxLogger LOGGER = new MxLogger(MyClass.class);
 *   
 *   public MyClass(){
 *     LOGGER.debug("default constructor executed");
 *   }
 *   
 * }
 * </pre>
 * The result of above log statement will be that in Mendix there will be a debug log under the Log Node with message: "MyClass: default constructor executed".
 */
public class MxLogger {

	private static final String MX_LOG_NODE_NAME;
	private static final ILogNode LOGGER;
	static {
		MX_LOG_NODE_NAME = "Amazon Bedrock Connector"; //log node name, e.g. name of your module
		LOGGER = Core.getLogger(MX_LOG_NODE_NAME);
		//init log message, so the Mendix Log Node is added to the Log Level Settings table, as soon a class that uses this ASWSLogger, is used.
		LOGGER.info("MxLogger initialized");
		
		//Statement below is to inject this MxLogger into the logging, the LibLogger of a java library (jar file), when there is one.
		//LibLogger.overrideLogging(MxLogger::critical, MxLogger::error, MxLogger::warn, MxLogger::info, MxLogger::debug, 
		//		                    MxLogger::trace, MxLogger::isDebugEnabledStatic, MxLogger::isTraceEnabledStatic);
	}

	private final Class<?> clazz;

	/**
	 * Constructor, to be used for each class with logging to the Mendix Log system.
	 * @param clazz  The class which is logging, so where the log statement are located.
	 */
	public MxLogger(final Class<?> clazz) {
		this.clazz = clazz;
	}

	//this method is called by the instance method, but is also used for injection in the LibLogger (see comments in static block).
	private static void critical(final Class<?> clazz, final String msg) {
		LOGGER.critical(formatMsg(clazz, msg));
	}

	//this method is called by the instance method, but is also used for injection in the LibLogger (see comments in static block).
	private static void error(final Class<?> clazz, final String msg) {
		LOGGER.error(formatMsg(clazz, msg));
	}

	//this method is called by the instance method, but is also used for injection in the LibLogger (see comments in static block).
	private static void warn(final Class<?> clazz, final String msg) {
		LOGGER.warn(formatMsg(clazz, msg));
	}

	//this method is called by the instance method, but is also used for injection in the LibLogger (see comments in static block).
	private static void info(final Class<?> clazz, final String msg) {
		LOGGER.info(formatMsg(clazz, msg));
	}

	//this method is called by the instance method, but is also used for injection in the LibLogger (see comments in static block).
	private static void debug(final Class<?> clazz, final String msg) {
		LOGGER.debug(formatMsg(clazz, msg));
	}

	//this method is called by the instance method, but is also used for injection in the LibLogger (see comments in static block).
	private static void trace(final Class<?> clazz, final String msg) {
		LOGGER.trace(formatMsg(clazz, msg));
	}

	//this method is called by the instance method, but is also used for injection in the LibLogger (see comments in static block).
	private static boolean isDebugEnabledStatic() {
		return LOGGER.isDebugEnabled();
	}

	//this method is called by the instance method, but is also used for injection in the LibLogger (see comments in static block).
	private static boolean isTraceEnabledStatic() {
		return LOGGER.isTraceEnabled();
	}

	//format the message to log in Mendix, so all log messages from this class have the same format.
	private static String formatMsg(final Class<?> clazz, final String msg) {
		return clazz.getSimpleName() + ": " + msg;
	}

	/**
	 * Log a critical message to the Mendix log system.
	 * @param msg message to log
	 */
	public void critical(final String msg) {
		critical(clazz, msg);
	}
	/**
	 * Log a critical message to the Mendix log system.
	 * All arguments will be joined together to one message.
	 * @param msgs messages to log
	 */
	public void critical(final String... msgs) {
		StringBuilder msg = new StringBuilder();
		for (String s : msgs) {
			msg.append(s);
		}
		critical(clazz, msg.toString());
	}
	/**
	 * Log a critical message to the Mendix log system.
	 * All objects will be converted to String and joined together to one message.
	 * @param objs objects to log
	 */
	public void critical(final Object... objs) {
		StringBuilder msg = new StringBuilder();
		for (Object o : objs) {
			msg.append(o);
		}
		critical(clazz, msg.toString());
	}

	/**
	 * Log an error message to the Mendix log system.
	 * @param msg message to log
	 */
	public void error(final String msg) {
		error(clazz, msg);
	}
	/**
	 * Log an error message to the Mendix log system.
	 * All arguments will be joined together to one message.
	 * @param msgs messages to log
	 */
	public void error(final String... msgs) {
		StringBuilder msg = new StringBuilder();
		for (String s : msgs) {
			msg.append(s);
		}
		error(clazz, msg.toString());
	}
	/**
	 * Log an error message to the Mendix log system.
	 * All objects will be converted to String and joined together to one message.
	 * @param objs objects to log
	 */
	public void error(final Object... objs) {
		StringBuilder msg = new StringBuilder();
		for (Object o : objs) {
			msg.append(o);
		}
		error(clazz, msg.toString());
	}

	/**
	 * Log a warn message to the Mendix log system.
	 * @param msg message to log
	 */
	public void warn(final String msg) {
		warn(clazz, msg);
	}
	/**
	 * Log a warn message to the Mendix log system.
	 * All arguments will be joined together to one message.
	 * @param msgs messages to log
	 */
	public void warn(final String... msgs) {
		StringBuilder msg = new StringBuilder();
		for (String s : msgs) {
			msg.append(s);
		}
		warn(clazz, msg.toString());
	}
	/**
	 * Log a warn message to the Mendix log system.
	 * All objects will be converted to String and joined together to one message.
	 * @param objs objects to log
	 */
	public void warn(final Object... objs) {
		StringBuilder msg = new StringBuilder();
		for (Object o : objs) {
			msg.append(o);
		}
		warn(clazz, msg.toString());
	}

	/**
	 * Log an info message to the Mendix log system.
	 * @param msg message to log
	 */
	public void info(final String msg) {
		info(clazz, msg);
	}
	/**
	 * Log an info message to the Mendix log system.
	 * All arguments will be joined together to one message.
	 * @param msgs messages to log
	 */
	public void info(final String... msgs) {
		StringBuilder msg = new StringBuilder();
		for (String s : msgs) {
			msg.append(s);
		}
		info(clazz, msg.toString());
	}
	/**
	 * Log an info message to the Mendix log system.
	 * All objects will be converted to String and joined together to one message.
	 * @param objs objects to log
	 */
	public void info(final Object... objs) {
		StringBuilder msg = new StringBuilder();
		for (Object o : objs) {
			msg.append(o);
		}
		info(clazz, msg.toString());
	}

	/**
	 * Log a debug message to the Mendix log system.
	 * @param msg message to log
	 */
	public void debug(final String msg) {
		debug(clazz, msg);
	}
	/**
	 * Log a debug message to the Mendix log system.
	 * All arguments will be joined together to one message.
	 * @param msgs messages to log
	 */
	public void debug(final String... msgs) {
		if (isDebugEnabled()) {
			StringBuilder msg = new StringBuilder();
			for (String s : msgs) {
				msg.append(s);
			}
			debug(clazz, msg.toString());
		}
	}
	/**
	 * Log a debug message to the Mendix log system.
	 * All objects will be converted to String and joined together to one message.
	 * @param objs objects to log
	 */
	public void debug(final Object... objs) {
		if (isDebugEnabled()) {
			StringBuilder msg = new StringBuilder();
			for (Object o : objs) {
				msg.append(o);
			}
			debug(clazz, msg.toString());
		}
	}

	/**
	 * Log a trace message to the Mendix log system.
	 * @param msg message to log
	 */
	public void trace(final String msg) {
		trace(clazz, msg);
	}
	/**
	 * Log a trace message to the Mendix log system.
	 * All arguments will be joined together to one message.
	 * @param msgs messages to log
	 */
	public void trace(final String... msgs) {
		if (isTraceEnabled()) {
			StringBuilder msg = new StringBuilder();
			for (String s : msgs) {
				msg.append(s);
			}
			trace(clazz, msg.toString());
		}
	}
	/**
	 * Log a trace message to the Mendix log system.
	 * All objects will be converted to String and joined together to one message.
	 * @param objs objects to log
	 */
	public void trace(final Object... objs) {
		if (isTraceEnabled()) {
			StringBuilder msg = new StringBuilder();
			for (Object o : objs) {
				msg.append(o);
			}
			trace(clazz, msg.toString());
		}
	}

	/**
	 * Returns true when debug output is enabled for this Mendix Log node.<br>
	 * This will be when logging of the node is set to debug or below (trace).<br>
	 * Useful when constructing a debug log message is 'expensive' (performance wise), 
	 * so the construction of the message only is executed when the debug output is set to true.
	 * For example:
	 * <pre>
	 * if (LOGGER.isDebugEnabled()){
	 *    String logMsg = constructComplexLogMessage();
	 *    LOGGER.debug(logMsg)
	 * }
	 * </pre>
	 * 
	 * @return true if debug output is enabled, otherwise false
	 */
	public boolean isDebugEnabled() {
		return isDebugEnabledStatic();
	}

	/**
	 * Returns true when trace output is enabled for this Mendix Log node.<br>
	 * This will be when logging of the node is set to trace.
	 * Useful when constructing a trace log message is 'expensive' (performance wise), 
	 * so the construction of the message only is executed when the trace output is set to true.
	 * For example:
	 * <pre>
	 * if (LOGGER.isTraceEnabled()){
	 *    String logMsg = constructComplexTraceMessage();
	 *    LOGGER.trace(logMsg)
	 * }
	 * </pre>
	 * 
	 * @return true if trace output is enabled, otherwise false
	 */
	public boolean isTraceEnabled() {
		return isTraceEnabledStatic();
	}
	
	/**
	 * Util method to get the Stacktrace of a Throwable (Exception)
	 * 
	 * @param throwable object to get stackstrace from
	 * @return stacktrace as string or "null" when null
	 */
	public static String getStackTrace(final Throwable throwable) {
        if (throwable == null) {
            return "null";
        }
        final StringWriter sw = new StringWriter();
        throwable.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

}