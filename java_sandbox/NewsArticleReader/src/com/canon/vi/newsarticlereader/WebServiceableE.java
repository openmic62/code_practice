package com.canon.vi.newsarticlereader;
/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\main\java>javac -d ..\..\..\target\classes WebServiceableE.java
 
 * Hand written mock and stub - Example
 * <u>The Art of Unit Testing</u>
 * Section 4.4 Using a mock and a stub together
 * This file defines in Java the .NET web service interface given by the book 
 * pdf-p. 92a
 *
 * <mlr 130914: needed to modify the interface so that it throws an exception>
 *
 */
public interface WebServiceableE
{
	public void logError(String message) throws Exception;
}