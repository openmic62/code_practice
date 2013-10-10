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
 ***** build the interface EmailableJM
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>javac -d ..\..\..\target\classes EmailableJM.java
 ***** build the CUT LogAnalyzerHWJMockitStub
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes LogAnalyzerHWJMockitStub.java
 ***** build the Tests LogAnalyzerHWJMockitStubTests
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes LogAnalyzerHWJMockitStubTests.java
 ***** run the Tests
 J:\src\test\java>java  -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes org.junit.runner.JUnitCore LogAnalyzerHWJMockitStubTests
 */ 
import mockit.Expectations;
import mockit.integration.junit4.JMockit;
import mockit.Mocked;
import mockit.NonStrict;
import mockit.NonStrictExpectations;

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
public class LogAnalyzerHWJMockitStubTests {

	@Test
	public void thisAlwaysPasses() {
	}

	@Test
	@Ignore("Need to fix this test.")
	public void thisIsIgnored() {
	}
	
	@Test
	public void analyze_whenWebServiceCallThrowsException_sendsEmail
		(@NonStrict final WebServiceableE stubWebService,
	 	 @Mocked    final EmailableJM     mockEmailService)
	 	 throws Exception {
	 	 	
		// set up the stub expectations for the dependency on the web service ...
		// ... and    mock expectations for the dependency on the email service
		new Expectations() {
			{
				/* <mlr 130920: begin>
				   localMockField has nothing to do with this test. Rather, I
				   used it as an experiment related to "local" mock variables. I think
				   a history of how I got to this experiment will help clarify.
				   
				   Level 1 mocks
				   Originally, I got this "mock and stub" test working with "class wide"
				   mocks. However, when done that way, it prohibited the interchanged use
				   of WebServiceableE and EmailableJM types. That is, once declared at the
				   class level, they had to remain with their same name leading to
				   unreadability when using a mock as a stub and vice versa.
				   
				   Level 2 mocks
				   To fix this unreadability issue, I figured out how to use test method mock
				   parameters. Done this way, I could interchange the use of WebServiceableE
				   and EmailableJM types as mock/stub or stub/mock. NOTE: I had to remain implemented
				   at this level (test method parameter) of mock/stub declaration because of the 
				   constructor injection used by the CUT. In other words, the stubWebService and 
				   mockEmailService need to be available OUTSIDE the Expectations block for
				   use in the replay phase when we instantiate the CUT before exercising it.
				   
				   Level 3 mocks
				   JMockit also provides the ability to declare "Zero or more local mock fields".
				   See
				   - http://jmockit.googlecode.com/svn/trunk/www/tutorial/BehaviorBasedTesting.html#model
				   - http://code.google.com/p/jmockit/issues/detail?id=4
				   At this level, local mock/stub field declarations occur inside the Expecations and, 
				   according to info at the 2nd URL, are also available in Verifications when identically
				   declared with same type and same name. When I tried declaring at this level is
				   when I discovered the concern mentioned in my NOTE above.
				   
				   So, directly below shows (I think) an example of a "local mock field".
				   <mlr 130920: end>
				*/
				WebServiceable localMockField; // unused in this test
				
				stubWebService.logError(anyString);
				result = new Exception();
				
				mockEmailService.sendEmail(anyString, anyString, anyString);
				times=8;
			} // end new Expectations()
		};
		
		// instantiate the CUT and inject the web service dependencey
		LogAnalyzerHWJMockitStub logan = new LogAnalyzerHWJMockitStub(stubWebService, mockEmailService);
		
		// exercise the CUT's ability to handle filenames that are too short 
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
	public void analyze_whenWebServiceCallThrowsException_sendsEmailWithArgEmail
		(@NonStrict final WebServiceableE stubWebService,
	 	 @Mocked    final EmailableJM     mockEmailService)
	 	 throws Exception {
	 	 	
		// set up the stub expectations for the dependency on the web service ...
		// ... and    mock expectations for the dependency on the email service
		new Expectations() {
			Email stubEmailObj;
			{
				stubWebService.logError(anyString);
				result = new Exception();
				
				mockEmailService.makeEmail();
				returns(stubEmailObj);
				
				mockEmailService.sendEmail(withAny(stubEmailObj));
				times=8;
			}
		};
		
		// instantiate the CUT and inject the web service dependencey
		LogAnalyzerHWJMockitStub logan = new LogAnalyzerHWJMockitStub(stubWebService, mockEmailService);
		
		// exercise the CUT's ability to handle filenames that are too short 
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
	public void analyze_minLengthFilename_omitsWebServiceCall
		(@Mocked    final WebServiceableE mockWebService,
	 	 @NonStrict final EmailableJM     stubEmailService) throws Exception {
		// set up the mock expectations for the dependency on the web service ...
		// ... and      no expectations for the dependency on the email service
		new Expectations() {
			{
				mockWebService.logError(anyString);
				maxTimes = 0;
			}
		};
		
		// instantiate the CUT and inject the web service dependencey
		LogAnalyzerHWJMockitStub logan = new LogAnalyzerHWJMockitStub(mockWebService, stubEmailService);
		
		// exercise the CUT's ability to handle filenames at min length boundary
		logan.analyze("12.45678");
	}
	
	@Test
	public void analyze_greaterThanMinLengthFilename_omitsWebServiceCall
		(@Mocked    final WebServiceableE mockWebService,
	 	 @NonStrict final EmailableJM     stubEmailService) throws Exception {
		// set up the mock expectations for the dependency on the web service ...
		// ... and      no expectations for the dependency on the email service
		new Expectations() {
			{
				mockWebService.logError(anyString);
				maxTimes = 0;
			}
		};
		
		// instantiate the CUT and inject the web service dependencey
		LogAnalyzerHWJMockitStub logan = new LogAnalyzerHWJMockitStub(mockWebService, stubEmailService);
		
		// exercise the CUT's ability to handle filenames greater than the min length boundary
		logan.analyze("12.45678");
		logan.analyze("12345678.");
		logan.analyze("1234.678");
		logan.analyze("ABC_&-12345678");
		logan.analyze("ABC_&-1234.slf");
	}
}