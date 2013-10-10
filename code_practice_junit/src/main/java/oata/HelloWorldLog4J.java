package oata;

import org.apache.log4j.Logger;
///import org.apache.log4j.BasicConfigurator;

public class HelloWorldLog4J {
	static Logger logger = Logger.getLogger(HelloWorldLog4J.class);

	public static void main(String[] args) {
		///BasicConfigurator.configure();
		logger.info("Hello, log4j bitches!");          // the old SysO-statement
	}
}