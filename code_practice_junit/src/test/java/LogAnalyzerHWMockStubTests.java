/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
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
 ***** build the CUT LogAnalyzerHWMock
 J:\src\test\java>cd ..\..\main\java
 J:\src\main\java>l
 J:\src\main\java>echo %CLASSPATH%
 J:\src\main\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\classes LogAnalyzerHWMock.java
 ***** build the Tests LogAnalyzerHWMockTests
 J:\src\main\java>cd ..\..\test\java
 J:\src\test\java>l
 J:\src\test\java>echo %CLASSPATH%   
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes LogAnalyzerHWMockStubTests.java
 ***** run the Tests
 J:\src\test\java>java  -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes org.junit.runner.JUnitCore LogAnalyzerHWMockStubTests
 */
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
public class LogAnalyzerHWMockStubTests {

	@Test
	public void thisAlwaysPasses() {
	}

	@Test
	@Ignore("Need to fix this test.")
	public void thisIsIgnored() {
	}
	
	@Test
	public void analyze_catchesWebServiceException_sendsEmail() {
		// set up the fake object (stub) that eliminates the dependency on the web service
		FakeWebServiceS fakeWebServiceStub = new FakeWebServiceS();
		fakeWebServiceStub.setExceptionToThrow(new Exception("fake exception"));
		
		// set up the fake object (mock) that eliminates the dependency on the email service
		FakeEmailServiceM fakeEmailServiceMock = new FakeEmailServiceM();
		
		// instantiate the CUT and inject the dependencies: web service, email service
		LogAnalyzerHWMockStub logan = new LogAnalyzerHWMockStub(fakeWebServiceStub, fakeEmailServiceMock);
		
		// test the CUT's interaction with it's depencency by using the stubbed out dependencies
		String tooShortFilename = "12.456";
		logan.analyze(tooShortFilename);
		
		// check the test result by mock: that is, 
		// see by asserting against the stubbed dependency itself
		// (rather than asserting against the CUT)
		// if the CUT interacted properly with its depencency  
		assertTrue(fakeEmailServiceMock.getTo().contains("a"));
		assertTrue(fakeEmailServiceMock.getSubject().contains("subject"));
		assertTrue(fakeEmailServiceMock.getBody().contains("fake exc"));
		// <mlr 130914: multiple asserts?! This is a no-no. pdf-p. 98a
		//              shows a solution that uses an EmailInfo object.>
	}
}