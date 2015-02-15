/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes IISLogStringParser.java

 * How to build a test API for my application - Example
 * <u>The Art of Unit Testing</u>
 * Section 7.6.1 Using test class inheritance patterns
 * TEMPLATE TEST CLASS PATTERN
 * This implements a concrete class based on BaseStringParser class
 * pdf-p. 158b (UML diagram)
 * 
 */
 
/**
 * This class implements the IISLogStringParser concrete class 
 */
public class IISLogStringParser extends BaseStringParser
{
	public IISLogStringParser() {
		super();
	}
	
	// make concrete implemenations of the template (abstract) methods
	public String  getStringVersionFromHeader() {
		return "Get rid of P.O.S. IIS and use Apache!";
	}
	public boolean hasCorrectHeader(String text) {
		boolean returnValue = false;
		if ( text.contains("IIS") ) {
			returnValue = true;
		}
		return returnValue;
	}
}