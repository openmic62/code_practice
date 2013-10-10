/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes XMLStringParser.java

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
 * This class implements the XMLStringParser concrete class 
 */
public class XMLStringParser extends BaseStringParser
{
	public XMLStringParser() {
		super();
	}

	public XMLStringParser(String input) {
		super(input);
	}

	// make concrete implemenations of the template (abstract) methods
	@Override
	public String  getStringVersionFromHeader() {
		// example getStringToParse()="<header>1.1</header>"
		final String REGEX = "<header>(\\d+\\.?\\d*\\.?\\d*)</header>";
		Pattern pattern =
		Pattern.compile(REGEX);

		Matcher matcher =
		pattern.matcher(getStringToParse());
		matcher.find();
		
		return matcher.group(1);
		///return "fucking XML bitches!";
	}
	@Override
	public boolean hasCorrectHeader(String text) {
		boolean returnValue = false;
		if ( text.contains("XML") ) {
			returnValue = true;
		}
		return returnValue;
	}
}