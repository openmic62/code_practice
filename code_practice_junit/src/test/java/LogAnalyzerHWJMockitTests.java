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
 ***** build the interface WebServiceable
 J:\>cd src\main\java
 J:\src\main\java>l
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>javac -d ..\..\..\target\classes WebServiceable.java
 ***** build the fake (stub/mock) FakeWebService
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\test-classes FakeWebService.java
 ***** build the CUT LogAnalyzerHWJMockit
 J:\src\test\java>cd ..\..\main\java
 J:\src\main\java>l
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes LogAnalyzerHWJMockit.java
 ***** build the Tests LogAnalyzerHWJMockitTests
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%   
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes LogAnalyzerHWJMockitTests.java
 ***** run the Tests
 J:\src\test\java>java  -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes org.junit.runner.JUnitCore LogAnalyzerHWJMockitTests
 */
import mockit.Expectations;
import mockit.Mocked;

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
@RunWith(JUnit4.class)
public class LogAnalyzerHWJMockitTests {

	@Mocked WebServiceable mockWebService;
	
	@Test
	public void thisAlwaysPasses() {
	}

	@Test
	@Ignore("Need to fix this test.")
	public void thisIsIgnored() {
	}
	
	@Test
	public void analyze_tooShortFilename_callsWebService() {
		// set up the mock expectations for the dependency on the web service
		new Expectations() {{
			mockWebService.logError(anyString); times=8;
		}};
		
		// instantiate the CUT and inject the web service dependencey 
		LogAnalyzerHWJMockit logan = new LogAnalyzerHWJMockit(mockWebService);
		
		// exercise the CUT's ability to handle too short filenames
		logan.analyze("12.4567");
		logan.analyze("12.456");
		logan.analyze("12.45");
		logan.analyze("12.4");
		logan.analyze("12.");
		logan.analyze("1");
		logan.analyze("");
		logan.analyze(null);
	}
	
	@Test
	public void analyze_minLengthFilename_omitsWebServiceCall() {
		// set up the mock expectations for the dependency on the web service
		new Expectations() {{
			mockWebService.logError(anyString); maxTimes = 0;
		}};
		
		// instantiate the CUT and inject the web service dependencey 
		LogAnalyzerHWJMockit logan = new LogAnalyzerHWJMockit(mockWebService);
		
		// exercise the CUT's ability to handle filenames at min length boundary
		logan.analyze("12.45678");
	}
	
	@Test
	public void analyze_greaterThanMinLengthFilename_omitsWebServiceCall() {
		// set up the mock expectations for the dependency on the web service
		new Expectations() {{
			mockWebService.logError(anyString); maxTimes = 0;
		}};
		
		// instantiate the CUT and inject the web service dependencey 
		LogAnalyzerHWJMockit logan = new LogAnalyzerHWJMockit(mockWebService);
		
		// exercise the CUT's ability to handle filenames greater than the min length boundary
		logan.analyze("12.45678");
		logan.analyze("12345678.");
		logan.analyze("1234.678");
		logan.analyze("ABC_&-12345678");
		logan.analyze("ABC_&-1234.slf");
	}
}