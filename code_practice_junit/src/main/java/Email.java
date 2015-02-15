/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -d ..\..\..\target\classes Email.java
 
 * Using dynamic mock and stub that return stuff - Example
 * <u>The Art of Unit Testing</u>
 * Section 5.3.1 A Mock, a Stub and a Priest Walk into a Test
 *         COMPARING OBJECTS AND PROPERTIES AGAINST EACH OTHER
 * This file contrives an object to enable illustration of
 * the .NET stuff that illustrates dynamically created fake objects
 * who objects with certain properties set to certain values
 * pdf-p. 116a - Listing 5.8 Comparing full objects
 * ErrorInfo object (this Email object replaces ErrorInfo)
 *
 * See also EmailableJM.java
 */
public class Email {
	private String to;
	private String subject;
	private String body;
	
	public Email() {
		this.to = "mike@gmail.com";
		this.subject = "Loganalyzer error";
		this.body = "An error occured.";
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	public String getTo() {
		return this.to;
	}
	
	public void setSubject(String to) {
		this.subject = subject;
	}
	public String getSubject() {
		return this.subject;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	public String getBody() {
		return this.body;
	}
}
