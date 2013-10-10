/**
C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
C:\Users\michaelr>j:
J:\>cd src\main\java
J:\src\main\java>l
J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes StandardStringParser.java

* How to build a test API for my application - Example
* <u>The Art of Unit Testing</u>
* Section 7.6.1 Using test class inheritance patterns
* TEMPLATE TEST CLASS PATTERN
* This implements a concrete class based on BaseStringParser class
* pdf-p. 158b (UML diagram)
*
*/
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* This class implements the StandardStringParser concrete class
*/
public class StandardStringParser extends BaseStringParser
{
	public StandardStringParser() {
		super();
	}

	public StandardStringParser(String input) {
		super(input);
	}

	// make concrete implemenations of the template (abstract) methods
	public String getStringVersionFromHeader() {
		// example getStringToParse()="header;version=1.1;\n"
		
		final String ON_SEMICOLON_DELIMITER = ";";
		String[] fields = getStringToParse().split(ON_SEMICOLON_DELIMITER);

		/* REGEX means: a digit one or more,  followed by "." 1 or none, followed by
		                a digit zero or more, followed by "." 1 or none, followed by
		                a digit zero or more
		 */
		final String REGEX = "\\d+\\.?\\d*\\.?\\d*";
		Pattern pattern =
		Pattern.compile(REGEX);

		String returnValue = null;
		for(String field : fields) {
			Matcher matcher =
			pattern.matcher(field);
			
			if (field.contains("version=")) {
			 	matcher.find();
			 	returnValue = matcher.group();
			}
			
		}
		return returnValue;
		///return "Just a plain Jane string, dear.";
	}

	public boolean hasCorrectHeader(String text) {
		boolean returnValue = false;
		if ( text.contains("") ) {
			returnValue = true;
		}
		return returnValue;
	}
}
