/**

* How to build a test API for my application - Example
* <u>The Art of Unit Testing</u>
* Section 7.6.1 Using test class inheritance patterns
* TEMPLATE TEST CLASS PATTERN
* This implements template tests for StandardStringParser
* see Listing 7.6 A template test classes for testing string parsers
* pdf-p. 160b

 -------------------------------------------------------------------------------

 --------------- which java ------------------
 for %i in (java.exe) do @echo.   %~$PATH:i
 --------------- which java ------------------
 
 For JMockit java.exe MUST be of JDK (with attach.dll) like
   C:\Program Files\Java\jdk1.7.0_02\bin\java.exe
 NOT the JRE one (missing attach.dll) Java automatically installs at
   C:\Windows\system32\java.exe
   
 Work the System PATH variable to ensure correct java.exe gets used.
 PATH = (system) PATH + (user) PATH - in that specific order
 
 -------------------------------------------------------------------------------
   
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>set CLASSPATH=..\jmockit.jar;..\junit-4.11.jar;..\hamcrest-core-1.3.jar

 ***** build the template example production source files
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>set PARSER_FILES=StringParseable.java BaseStringParser.java IISLogStringParser.java XMLStringParser.java StandardStringParser.java RegexTestHarness.java
 J:\src\main\java>echo %PARSER_FILES%
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes %PARSER_FILES%

 ***** build the Tests TemplateStringParserTests
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes TemplateStringParserTests.java
 */ 
public abstract class TemplateStringParserTests {

	public abstract void testGetStringVersionFromHeader_withMajorVersionOnly_findsIt();
	 
	public abstract void testGetStringVersionFromHeader_withMinorVersionToo_findsIt();
	 
	public abstract void testGetStringVersionFromHeader_withRevisionToo_findsIt();
	
}