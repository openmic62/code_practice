package com.canon.vi.newsarticlereader;
/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes LogAnalyzerHWJMockitStub.java

 * Using dynamic mock and stub that return stuff - Example
 * <u>The Art of Unit Testing</u>
 * Section 5.3 Simulating Fake Values
 * This file contrives an interface that will enable illustration of
 * the .NET stuff that illustrates dynamically created fake objects
 * who return values and exceptions
 * pdf-p. 110a - 111b
 * 
 * HWJMockitStub -> Hand Written to use both JMockit mock and Stub
 *
 */
public class LogAnalyzerHWJMockitStub
{
	// This attribute stores our web service provider
	private WebServiceableE wsp;
	
	// This attribute stores our email service provider
	private EmailableJM esp;
	
	// Constructor inject the web service dependency
	public LogAnalyzerHWJMockitStub(WebServiceableE webServiceProvider, EmailableJM emailServiceProvider) {
		this.wsp = webServiceProvider;
		this.esp = emailServiceProvider;
	}
	
	public void analyze(String fileName) {
		// here is the logic we want to test
		if (fileName == null || fileName.length() < 8) {
			try {
				wsp.logError("Filename is too short: " + fileName);
			}
			catch (Exception e) {
				// <mlr 130914: this is VERY non-OCP, rigid; small change here breaks the test>
				///esp.sendEmail("a", "subject", e.getMessage());
				
				/* <mlr 130921: I confirmed this rigidity. The Art of Unit Testing, pdf-p. 115a,
				   suggested a change from the explicit sendEmail(Sting,String,String) to an
				   encapsulated sendEmail(Email) where Email is an object with the 3 fields. (Actually,
				   the book's encapsulated oject is ErrorInfo.) Making the change described here breaks
				   existing tests. In other words, making the change here in the CUT will potentially
				   break ANY client that uses LogAnalyzerHWJMockitStub.>
				 */
				///Email emailObj = new Email(); // <mlr 130921: rigid - introduces inflexible dependency on Email>
				Email emailObj = esp.makeEmail(); // <mlr 130922: fragile - these two calls introduce a 2nd responsibility, violates SRP>
				esp.sendEmail(emailObj);
			}
		}
	}
}