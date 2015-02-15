/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes LogAnalyzerHWMockStub.java

 * Hand written mock and stub - Example
 * <u>The Art of Unit Testing</u>
 * Section 4.4 Using a mock and a stub together
 * This file implements in Java the .NET CUT given by the book
 * pdf-p. 94b
 * 
 * HWMockStub -> Hand Written to use both Mock and Stub
 *
 */
public class LogAnalyzerHWMockStub
{
	// This attribute stores our web service provider
	private WebServiceableE wsp;
	
	// This attribute stores our email service provider
	private Emailable esp;
	
	// Constructor inject the web service dependency
	LogAnalyzerHWMockStub(WebServiceableE webServiceProvider, Emailable emailServiceProvider) {
		this.wsp = webServiceProvider;
		this.esp = emailServiceProvider;
	}
	
	public void analyze(String fileName) {
		// here is the logic we want to test
		if (fileName.length() < 8) {
			try {
				wsp.logError("Filename is too short: " + fileName);
			}
			catch (Exception e) {
				// <mlr 130914: this is VERY non-OCP, ridgid; small change here breaks the test>
				esp.sendEmail("a", "subject", e.getMessage());
			}
		}
	}
}