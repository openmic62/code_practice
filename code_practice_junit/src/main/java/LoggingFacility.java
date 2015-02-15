/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes LoggingFacility.java

 * How to build a test API for my application - Example
 * <u>The Art of Unit Testing</u>
 * Section 7.6.1 Using test class inheritance patterns
 * ABSTRACT TEST INFRASTRUCTURE CLASS PATTERN
 * This file implements in Java the .NET CUT given by the book
 * pdf-p. 154b-156a
 * 
 * Dry -> don't repeat yourself
 *
 * Listing 7.4 A refactored solution [using BaseTestClass]
 * pdf-p. 156b-157a
 * <mlr 130929: after JMockit implementing the non-refactored non-DRY implementation
 *              of "Listing 7.3 An example of not following the DRY principle in test classes"
 *              I think this idea of using a BaseTestClass is too much work (and
 *              reduction in readability) than it's worth. Even the author says at the bottom
 *              of pdf-p.157b, "That is why I recommend to use this technique as little as you can,
 *              but no less." So, I'm gonna skip java implementing the refactor.>
 *              
 *
 */
 
/**
 * This class implements the LoggingFacility
 */
public class LoggingFacility
{
	// use Dependency Injection with property getter and setter
	private static Loggable logger;
	
	public static Loggable getLogger() {return LoggingFacility.logger;}
	public static void setLogger(Loggable logger) {LoggingFacility.logger = logger;}
	
	public static void log(String text) {
		logger.log(text);
	}
}