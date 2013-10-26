/**

* How to build a test API for my application - Example
* <u>The Art of Unit Testing</u>
* Section 7.6.1 Using test class inheritance patterns
* ABSTRACT 'FILL IN THE BLANKS' TEST DRIVER CLASS PATTERN
* This implements "hooks" type template for testing
* see Listing 7.7 A 'Fill in The Blanks' base test class
* pdf-p. 162a

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

 ***** build the Tests FillInTheBlanksStringParserTests
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes FillInTheBlanksStringParserTests.java
 */ 
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public abstract class FillInTheBlanksStringParserTests {
	// declare the expected results
	public final String EXPECTED_SINGLE_DIGIT  = "1";
	public final String EXPECTED_WITH_MINOR    = "1.1";
	public final String EXPECTED_WITH_REVISION = "1.1.1";
	
	// declare the parser "factory" method as abstract
	protected abstract StringParseable getParser(String input);

	// declare the "hooks" (where  you fill in the blanks) as abstract
	protected abstract String getHeaderVersionSingleDigit();
	protected abstract String getHeaderVersionWithMinor();
	protected abstract String getHeaderVersionWithRevision();
	
	@Test
	public void testGetStringVersionFromHeader_withMajorVersionOnly_findsIt() {
		
		// the subclass for the parser type "fills in the blank" here
		String stringContainsHeaderVersionSingleDigit = getHeaderVersionSingleDigit();
		
		// get the parser as usual, except pass the "filled in the blank" String
		StringParseable parser = getParser(stringContainsHeaderVersionSingleDigit);
		
		// exercise the CUT
		String actual = parser.getStringVersionFromHeader();
		
		// assert the result
		assertEquals(EXPECTED_SINGLE_DIGIT, actual);
	};
	 
	@Test
	public void testGetStringVersionFromHeader_withMinorVersionToo_findsIt() {
		
		// the subclass for the parser type "fills in the blank" here
		String stringContainsHeaderVersionWithMinor = getHeaderVersionWithMinor();
		
		// get the parser as usual, except pass the "filled in the blank" String
		StringParseable parser = getParser(stringContainsHeaderVersionWithMinor);
		
		// exercise the CUT
		String actual = parser.getStringVersionFromHeader();
		
		// assert the result
		assertEquals(EXPECTED_WITH_MINOR, actual);
	};
	 
	@Test
	public void testGetStringVersionFromHeader_withRevisionToo_findsIt() {
		
		// the subclass for the parser type "fills in the blank" here
		String stringContainsHeaderVersionWithRevision = getHeaderVersionWithRevision();
		
		// get the parser as usual, except pass the "filled in the blank" String
		StringParseable parser = getParser(stringContainsHeaderVersionWithRevision);
		
		// exercise the CUT
		String actual = parser.getStringVersionFromHeader();
		
		// assert the result
		assertEquals(EXPECTED_WITH_REVISION, actual);
	};
	
}