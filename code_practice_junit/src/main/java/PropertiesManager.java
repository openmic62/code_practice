/**
 * 1Z0-803 OCA Java SE 7 Study Guide
 * Ch. 1, p. 28
 * Section 1, Item 3
 * Create executable Java applications with a main method
 * Illustrates: compiling with -cp, -classpath
 
 * H:\student\code_practice_junit>ant run -Dmain-class=PropertiesManager
 * or
 * H:\student\code_practice_junit>java -cp target\classes PropertiesManager -list_all
 */

import java.util.Properties;

public class PropertiesManager {
	public static void main (String[] args) {
		if ( args.length == 0 ) { System.exit(0); }
		Properties props = System.getProperties();
		/* New property example */
		props.setProperty("new_property2", "new_value2");
		switch ( args[0] ) {
			case "-list_all":
				props.list(System.out); // list all properties
				break;
			case "-list_prop":
				// <mlr 130219: begin>I added the next line to avoid a array out of bounds exception<mlr 130219: end>
				if ( args.length < 2 ) { System.out.println("Need 2 args"); break; }
				// lists value [of property key specified in arg[1]]
				System.out.println(props.getProperty(args[1]));
				break;
			default:
				System.out.println("Usage: java PropertiesManager [-list_all]");
				System.out.println("Usage: java PropertiesManager [-list_prop <property>]");
				break;
		}
	}
}