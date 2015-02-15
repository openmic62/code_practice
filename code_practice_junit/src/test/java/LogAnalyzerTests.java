/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\test\java
 J:\src\test\java>l 
 J:\src\test\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
 J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\test-classes LogAnalyzerTests.java
 J:\src\test\java>java -cp %CLASSPATH%;..\..\..\target\test-classes;..\..\..\target\classes org.junit.runner.JUnitCore LogAnalyzerTests
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
public class LogAnalyzerTests {

	@Test
	public void thisAlwaysPasses() {
	}

	@Test
	@Ignore
	public void thisIsIgnored() {
	}
	
	@Test
	public void IsValidLogFileName_BadExtension_ReturnsFalse() {
		LogAnalyzer analyzer = new LogAnalyzer();
		
		boolean result = analyzer.IsValidLogFileName("filewithbadextension.foo");
		
		assertFalse("You fucked up, bitches!", result);
	}
	
	@Test
	public void IsValidLogFileName_GoodExtensionUppercase_ReturnsTrue() {
		LogAnalyzer analyzer = new LogAnalyzer();
		
		boolean result = analyzer.IsValidLogFileName("filewithgoodextension.SLF");
		
		assertTrue(result);
	}
	
	@Test
	public void IsValidLogFileName_GoodExtensionLowercase_ReturnsTrue() {
		LogAnalyzer analyzer = new LogAnalyzer();
		
		boolean result = analyzer.IsValidLogFileName("filewithgoodextension.slf");
		
		assertTrue(result);
	}
}