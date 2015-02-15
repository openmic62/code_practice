/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\test\java
 J:\src\test\java>l
 J:\src\test\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\test-classes FakeEmailServiceM.java

 * Hand written mock and stub - Example
 * <u>The Art of Unit Testing</u>
 * Section 4.4 Using a mock and a stub together
 * This file implements in Java the .NET email interface given by the book.
 * The test will use an instance of this class as a mock object.
 * pdf-p. 97b
 *
 */
public class FakeEmailServiceM implements Emailable
{
	private String addressee;
	private String subject;
	private String body;
	
	public String getTo() {
		return addressee;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public void sendEmail(String to, String subject, String body) {
		this.addressee = to;
		this.subject   = subject;
		this.body      = body;
	}
}