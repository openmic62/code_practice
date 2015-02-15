/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\test\java
 J:\src\test\java>l
 J:\src\test\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\test-classes FakeWebServiceS.java

 * Hand written mock and stub - Example
 * <u>The Art of Unit Testing</u>
 * Section 4.4 Using a mock and a stub together
 * This file implements in Java the .NET web service interface given by the book.
 * The test will use an instance of this class as a stub object.
 * pdf-p. 97a
 *
 * FakeWebServiceS -> "S" indicates "Stub"
 * WebServiceableE -> "E" indicates throws exception
 *
 */
public class FakeWebServiceS implements WebServiceableE
{
	// provide more flexibility for the type of exception thrown
	private Exception exceptionToThrow;
	void setExceptionToThrow(Exception exception) {
		this.exceptionToThrow = exception;
	}
		
	// implement the interface WebServiceableE
	public void logError(String message) throws Exception {
		throw exceptionToThrow;
	}
}