/**

* How to build a test API for my application - Example
* <u>The Art of Unit Testing</u>
* Section 7.6.1 Using test class inheritance patterns
* TEMPLATE TEST CLASS PATTERN
* This implements template based tests for XMLStringParser
* see Listing 7.6 A template test classes for testing string parsers
* pdf-p. 160a

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

 ***** build the Tests XMLStringParserTests
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes XMLStringParserTests.java
 ***** run the Tests
 J:\src\test\java>java  -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes org.junit.runner.JUnitCore XMLStringParserTests
 */ 
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
* Tests for {@link Foo}.
*
* @author user@example.com (John Doe)
*/
@RunWith(JUnit4.class)
///@RunWith(JMockit.class)
public class XMLStringParserTests extends TemplateStringParserTests {
	
	// The book uses a helper method: a parser factory method
	public StringParseable getParser(String input) {
		return new XMLStringParser(input);
	}
	 
	@Override
	@Test
	public void testGetStringVersionFromHeader_withMajorVersionOnly_findsIt() {
		StringParseable xmlStringParser = getParser("<header>1</header>");
		
		String actual = xmlStringParser.getStringVersionFromHeader();
		
		assertEquals("1", actual);
	}
	 
	@Override
	@Test
	public void testGetStringVersionFromHeader_withMinorVersionToo_findsIt() {
		StringParseable xmlStringParser = getParser("<header>1.1</header>");
		
		String actual = xmlStringParser.getStringVersionFromHeader();
		
		assertEquals("1.1", actual);
	}
	 
	@Override
	@Test
	public void testGetStringVersionFromHeader_withRevisionToo_findsIt() {
		StringParseable xmlStringParser = getParser("<header>1.1.1</header>");
		
		String actual = xmlStringParser.getStringVersionFromHeader();
		
		assertEquals("1.1.1", actual);
	}
}