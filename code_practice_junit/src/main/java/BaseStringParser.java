/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes BaseStringParser.java

 * How to build a test API for my application - Example
 * <u>The Art of Unit Testing</u>
 * Section 7.6.1 Using test class inheritance patterns
 * TEMPLATE TEST CLASS PATTERN
 * This contrives a Java version of the IStringParser interface given by the book
 * pdf-p. 158b (UML diagram)
 * 
 */
 
/**
 * This class implements the BaseStringParser template (abstract) class 
 */
public abstract class BaseStringParser implements StringParseable
{
	private final String stringToParse;
	
	public BaseStringParser() {
		this("default string to parse");
	}
	
	public BaseStringParser(String input) {
		super();
		this.stringToParse = input;
	}
	
	public String getStringToParse() {
		return this.stringToParse;
	}
	
	public abstract String  getStringVersionFromHeader();
	public abstract boolean hasCorrectHeader(String text);
}