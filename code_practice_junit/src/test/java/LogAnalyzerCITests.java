/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\test\java
 J:\src\test\java>l
 J:\src\test\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes LogAnalyzerCITests.java
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes FakeFileExtensionManager.java
 J:\src\test\java>java -cp %CLASSPATH%;..\..\..\target\test-classes;..\..\..\target\classes org.junit.runner.JUnitCore LogAnalyzerCITests
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
public class LogAnalyzerCITests {

	@Test
	public void thisAlwaysPasses() {
	}

	@Test
	@Ignore
	public void thisIsIgnored() {
	}
	
	@Test
	public void IsValidLogFileName_NameExtensionSupported_ReturnsTrue() {
		// set up the fake object that cuts the dependency on the file system
		FileExtensionManagable fake = new FakeFileExtensionManager();
		((FakeFileExtensionManager)fake).setWillBeValid(true);
		
		// dependency inject the fake object via constructor 
		LogAnalyzerCI analyzer = new LogAnalyzerCI(fake);
		
		// test the CUT using dependency injection
		boolean result = analyzer.IsValidLogFileName("short.ext");
		
		// check the result
		assertTrue(result);
	}
	
	@Test(expected = RuntimeException.class)
	public void IsValidLogFileName_ThrowsException_ReturnsFalse() {
		// set up the fake object that cuts the dependency on the file system
		FileExtensionManagable fake = new FakeFileExtensionManager();
		((FakeFileExtensionManager)fake).setWillThrow("This is a fake exception");
		
		// dependency inject via constructor the fake object
		LogAnalyzerCI analyzer = new LogAnalyzerCI(fake);
		
		// test the CUT using dependency injection
		boolean result = analyzer.IsValidLogFileName("anything.anyextension");
		
		// check the result
		assertFalse(result);
	}
		
	// <mlr 130816: begin>
	// Moved class FakeFileExtensionManager implements FileExtensionManagable
	// to its own file so that I could reuse it in DI tests.
	// <mlr 130816: end>
}