/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes LogAnalyzerHWMock.java

 * Hand written mock - Example
 * <u>The Art of Unit Testing</u>
 * Section 4.3 A simple hand written mock example
 * This file implements in Java the .NET CUT given by the book
 * pdf-p. 92b-93a
 * 
 * HWMock -> Hand Written Mock
 *
 */
public class LogAnalyzerHWMock
{
	// This attribute stores our web service provider
	private WebServiceable wsp;
	
	// Constructor inject the web service dependency
	LogAnalyzerHWMock(WebServiceable webServiceProvider) {
		this.wsp = webServiceProvider;
	}
	
	public void analyze(String fileName) {
		// here is the logic we want to test
		if (fileName.length() < 8) {
			wsp.logError("Filename is too short: " + fileName);
		}
	}
}