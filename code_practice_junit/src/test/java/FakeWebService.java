/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\test\java
 J:\src\test\java>l
 J:\src\test\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\test-classes FakeWebService.java

 * Hand written mock - Example
 * <u>The Art of Unit Testing</u>
 * Section 4.3 A simple hand written mock example
 * This file implements in Java the .NET interface given by the book.
 * The test will use an instance of this class as a mock object.
 * pdf-p. 92a
 *
 */
public class FakeWebService implements WebServiceable
{
	private String lastError;
	
	// <mlr 130914: needed to add this getter against which to assert>
	public String getLastError() {
		return this.lastError;
	}
	
	public void logError(String message) {
		this.lastError = message;
	}
}