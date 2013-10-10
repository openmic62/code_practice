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
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes LogAnalyzerHWMockTests.java
 ***** run the Tests
 J:\src\test\java>java  -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes org.junit.runner.JUnitCore LogAnalyzerHWMockTests
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
public class LogAnalyzerHWMockTests {

	@Test
	public void thisAlwaysPasses() {
	}

	@Test
	@Ignore("Need to fix this test.")
	public void thisIsIgnored() {
	}
	
	@Test
	public void analyze_tooShortFilename_callsWebService() {
		// set up the fake object that eliminates the dependency on the web service
		FakeWebService fake = new FakeWebService();
		
		// instantiate the CUT and inject the web service dependencey 
		LogAnalyzerHWMock logan = new LogAnalyzerHWMock(fake);
		
		// test the CUT's interaction with it's depencency by using the stubbed out dependency
		logan.analyze("12.456");
		
		// check the test result by mock: that is, 
		// see by asserting against the stubbed dependency itself
		// (rather than asserting against the CUT)
		// if the CUT interacted properly with its depencency  
		assertTrue(fake.getLastError().contains("12.456"));
	}
}