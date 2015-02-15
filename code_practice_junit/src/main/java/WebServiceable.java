/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -d ..\..\..\target\classes WebServiceable.java
 
 * Hand written mock - Example
 * <u>The Art of Unit Testing</u>
 * Section 4.3 A simple hand written mock example
 * This file defines in Java the .NET interface given by the book 
 * pdf-p. 92a
 *
 */
interface WebServiceable
{
	void logError(String message);
}