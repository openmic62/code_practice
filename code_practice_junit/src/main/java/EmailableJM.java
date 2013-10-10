/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp ..\..\..\target\classes -d ..\..\..\target\classes EmailableJM.java
 
 * Using dynamic mock and stub that return stuff - Example
 * <u>The Art of Unit Testing</u>
 * Section 5.3 Simulating Fake Values
 * This file contrives an interface that will enable illustration of
 * the .NET stuff that illustrates dynamically created fake objects
 * who return values and exceptions
 * pdf-p. 110a - 111b
 *
 */
interface EmailableJM
{
	Email makeEmail(); // <mlr 130921: I added this to reduce RIGIDITY in the client LogAnalyzerHWJMockitStub>
	
	void sendEmail(String to, String subject, String body);

	// <mlr 130920: added for stuff on pdf-p. 116a, see Email.java)
	void sendEmail(Email email);
	
	String getTo();
	void setTo(String to);
	
	String getSuject();
	void setSubject(String subject) throws IllegalArgumentException;	
}