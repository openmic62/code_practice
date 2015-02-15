/**
C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
C:\Users\michaelr>j:
J:\>cd src\test\java
J:\src\test\java>l
J:\src\test\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes;..\..\..\target\test-classes -d ..\..\..\target\test-classes LogAnalyzerFMTests.java
J:\src\test\java>java -cp %CLASSPATH%;..\..\..\target\test-classes;..\..\..\target\classes org.junit.runner.JUnitCore LogAnalyzerFMTests
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
public class LogAnalyzerFMsimOutTests {

	@Test
	public void thisAlwaysPasses() {
	}

	@Test
	@Ignore("Need to fix this test.")
	public void thisIsIgnored() {
	}

	@Test
	public void IsValidLogFileName_NameExtensionSupported_ReturnsTrue() {
		// instantiate and configure the CUT 
		LogAnalyzerFMsimOutTestable analyzer = new LogAnalyzerFMsimOutTestable();
		analyzer.setSupported(true);

		// test the CUT using EXTRACT and OVERRIDE (fake a result)
		boolean result = analyzer.IsValidLogFileName("short.ext");

		// check the result
		assertTrue(result);
	}

	@Test(expected = RuntimeException.class)
	public void IsValidLogFileName_ThrowsException_ReturnsFalse() {
		// instantiate and configure the CUT 
		LogAnalyzerFMsimOutTestable analyzer = new LogAnalyzerFMsimOutTestable();
		analyzer.setWillThrow(new RuntimeException("Simulated runtime exception."));

		// test the CUT using EXTRACT and OVERRIDE (fake a result)
		boolean result = analyzer.IsValidLogFileName("anything.anyextension");

		// check the result
		assertFalse(result);
	}
	
	// EXTEND and OVERRIDE (simulating output to CUT)
	class LogAnalyzerFMsimOutTestable extends LogAnalyzerFMsimOut
	{
		// make this test class a bit more flexible by providing a mutable property
		private boolean supported = false;
		public void setSupported(boolean b) {
			this.supported = b;
		}
		
		// make this test class a bit more flexible by providing a mutable property
		private RuntimeException willThrow = null;
		public void setWillThrow(RuntimeException re) {
			this.willThrow = re;
		}
		
		// This override creates the seam where we can output our own result.
		@Override
		protected boolean isValid(String fileName) {
			if (this.willThrow != null) {
				throw willThrow;
			}
			return this.supported;
		}
	}
}