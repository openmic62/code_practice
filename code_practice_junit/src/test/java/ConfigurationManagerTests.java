/**

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
 ***** build the interface Loggable
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>javac -d ..\..\..\target\classes Loggable.java
 ***** build the LoggingFacility
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes LoggingFacility.java
 ***** build the CUT ConfigurationManager
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes ConfigurationManager.java
 ***** build the Tests ConfigurationManagerTests
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes ConfigurationManagerTests.java
 ***** run the Tests
 J:\src\test\java>java  -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes org.junit.runner.JUnitCore ConfigurationManagerTests

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
 */
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import mockit.Mock;
import mockit.MockUp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
* Tests for {@link Foo}.
*
* @author user@example.com (John Doe)
*/
///@RunWith(JUnit4.class)
@RunWith(JMockit.class)
public class ConfigurationManagerTests {

	@Test
	public void isConfigured_whenCalled_returnsTrue(){
		
		// a test local variable for the CUT's return value
		boolean result;
		
		// using JMockit stub out the call to log in isConfigured 
		new MockUp<LoggingFacility>() {
			@Mock
			void log(String s){}
		};
	 	 	
		// instantiate the CUT
		ConfigurationManager configManager = new ConfigurationManager();
		
		// exercise the CUT and assert on it's return value
		result = configManager.isConfigured("12.4567");
		assertTrue(result);
		
		result = configManager.isConfigured(null);
		assertTrue(result);
	}

	@Test
	public void isConfigured_whenCalled_makesLogEntry(){

		// set up the mock expectations for the dependency on the logging facility
		new Expectations() {
			LoggingFacility mockLoggingFacility;
			{
				mockLoggingFacility.log(anyString);
				times=2;
			}
		};
		
		// instantiate the CUT
		ConfigurationManager configManager = new ConfigurationManager();
		
		// exercise the CUT and assert by implicit verification of recorded expectation
		configManager.isConfigured("12.4567");
		configManager.isConfigured(null);
	}
}