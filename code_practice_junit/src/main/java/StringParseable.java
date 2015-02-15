/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp ..\..\..\target\classes -d ..\..\..\target\classes StringParseable.java
 
 * How to build a test API for my application - Example
 * <u>The Art of Unit Testing</u>
 * Section 7.6.1 Using test class inheritance patterns
 * TEMPLATE TEST CLASS PATTERN
 * This contrives a Java version of the IStringParser interface given by the book
 * pdf-p. 158b (UML diagram)
 *
 */
interface StringParseable
{
	String getStringToParse();
	//void setStringToParse(String stringToParse);
	
	String  getStringVersionFromHeader();
	boolean hasCorrectHeader(String text);
}