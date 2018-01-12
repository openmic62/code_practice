package com.canon.vi.newsarticlereader.test;
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
import mockit.Mocked;
import mockit.NonStrict;
import mockit.VerificationsInOrder;
import mockit.integration.junit4.JMockit;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.canon.vi.newsarticlereader.Email;
import com.canon.vi.newsarticlereader.EmailableJM;
import com.canon.vi.newsarticlereader.LogAnalyzerHWJMockitStub;
import com.canon.vi.newsarticlereader.WebServiceableE;

//import com.canon.vi.newsarticlereader.LogAnalyzerHWJMockitStub;

/**
* Tests for {@link Foo}.
*
* @author user@example.com (John Doe)
*/
///@RunWith(JUnit4.class)
@RunWith(JMockit.class)
public class LogAnalyzerHWJMockitStubTests {

	@Test
	public void analyze_whenWebServiceCallThrowsException_sendsEmailWithArgEmail
		(@NonStrict final WebServiceableE stubWebService,
	 	 @Mocked    final EmailableJM     mockEmailService)
	 	 throws Exception {
	 	 	
		// set up the stub expectations for the dependency on the web service ...
		// ... and    mock expectations for the dependency on the email service
		new Expectations() {
			{
				stubWebService.logError(anyString);
				result = new Exception();
			}
		};
		
		// instantiate the CUT and inject the web service dependencey
		LogAnalyzerHWJMockitStub logan = new LogAnalyzerHWJMockitStub(stubWebService, mockEmailService);
		
		/* <mlr 130922: this testcase passes BUT fails to proves what I want. That is, I want to prove 
		   that EACH call to analyze(String), with various String parameters failing to meet the 
		   minimum length requirement of 8 characters, AND those calls also experience an exception in the 
		   web service logError call, will THEN EACH call makeEmail() once followed immediately by 
		   one call to sendEmail(withAny(stubEmailObj) of the email service provider. 
		   
		   I am tempted to think (incorrectly) that
		   "...as long as I supply "bad" parameters to the analyze(String) calls,
		   my test is reliable. In this context, reliable means, "I can rely that each test
		   actually tests (proves) what I want it to test (prove).
		   
		   However, what actually happens is my Verifications block gets satisfied by ONLY the 1st 
		   analyze(String) call with a "bad" parameter. (Rather, it should get satisfied only if
		   EVERY analyze(String) call with a "bad" parameter happens.) Thus the testcase passes, 
		   but it fails to prove what I actually want it to prove. This is an insidious problem 
		   because I think my test is proving one thing. When, in actuality, it is proving an entirely 
		   different thing.
		   
		   NOTE: originally, I tried implicit verification by recording the calls to makeEmail() and
		         sendEmail in the Expectations() block. That always failed, because the recording happens
		         sequentially: it IS makeEmail() once followed by sendEmail() once DURING THE ENTIRE
		         REPLAY.  It is NOT, makeEmail() once followed by sendEmail() FOR EACH analyze(String)
		         called during replay.
		         
		         Whatever I tried, the testcase always failed. When I realize how the recording and replay
		         actually happen, as decribed above, then I tried moving the makeEmail() and sendEmail()
		         calls into the verification block. That is how I discovered the insidious problem.
		         
		   NOTE 2: What is the root cause of the insidious problem? I think it is that I put 2
		         responsibilities in the CUT: 1) to "analyze" a logfile name for validity and 2)
		         to ACTUALLY PERFORM some error logging for invalid file names. I believe this 
		         violates the single responsibility principle (SRP). What is the solution? A better
		         management of dependencies starting with separating the responsibilities.>
		*/
		// exercise the CUT's ability to handle filenames that are too short 
		logan.analyze("12.45678"); // I would expect this testcase to fail here ... 
		logan.analyze("12.4567");  // ... but, instead, it meets verification here and this testcase passes
		logan.analyze("12.456");
		logan.analyze("12.45");
		logan.analyze("12.4");
		logan.analyze("12.");
		logan.analyze("1");
		logan.analyze("");
		logan.analyze(null);
		
		new VerificationsInOrder() {
			@NonStrict Email stubEmailObj;
			{
				mockEmailService.makeEmail();
				//returns(stubEmailObj);
				times=1;
				
				mockEmailService.sendEmail(withAny(stubEmailObj));
				times=1;
			}
		};
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
		
		/* <mlr 130922: this test passes AND proves what I want. That is, I want to prove that
		   various calls to analyze(String), with various parameters meeting the minimum length
		   requirement of 8 characters, will NEVER call logError(String) of the web service to 
		   log an error. So, as long as I supply "good" parameters to the analyze(String) calls,
		   my test is reliable. In this context, reliable means, "I can rely that thos test
		   actually tests (proves) what I want it to test (prove).>
		*/
		// exercise the CUT's ability to handle filenames greater than the min length boundary
		logan.analyze("12.45678");
		logan.analyze("12345678.");
		logan.analyze("1234.678");
		logan.analyze("ABC_&-12345678");
		logan.analyze("ABC_&-1234.slf");
	}
}