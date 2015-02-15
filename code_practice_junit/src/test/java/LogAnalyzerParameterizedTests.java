/**
C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
C:\Users\michaelr>j:
J:\>cd src\test\java
J:\src\test\java>l
J:\src\test\java>set CLASSPATH=..\junit-4.11.jar;..\hamcrest-core-1.3.jar
J:\src\test\java>javac -cp %CLASSPATH%;..\..\..\target\classes -d ..\..\..\target\test-classes LogAnalyzerParameterizedTests.java
J:\src\test\java>java -cp %CLASSPATH%;..\..\..\target\test-classes;..\..\..\target\classes org.junit.runner.JUnitCore LogAnalyzerParameterizedTests
*/
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
* Tests for {@link Foo}.
*
* @author user@example.com (John Doe)
*/
@RunWith(Parameterized.class)
public class LogAnalyzerParameterizedTests {

	private String  fileNameUT;
	private boolean resultExpected;

	LogAnalyzerParameterizedTests() {
		super();
	}

	public LogAnalyzerParameterizedTests(String pFileNameUT, boolean pResultExpected) {
		super();
		this.fileNameUT     = pFileNameUT;
		this.resultExpected = pResultExpected;
	}

	@Parameters(name = "\nfileName[{index}]-->{0}<--\nexpecteResult[{index}]-->{1}<--\n")
	public static Collection<Object[]> data() {
		//
		Object[][] data = new Object[][] {
			{ "filewithbadextension.foo", false },
			{ "filewithgoodextension.SLF", true },
			{ "filewithgoodextension.slf", true }
		};
		return Arrays.asList(data);
		//
		/*
		return Arrays.asList(new Object[][] {
		{ "filewithbadextension.foo", "" },
		{ "filewithgoodextension.SLF", "" },
		{ "filewithgoodextension.slf", "" }
		});
		*/
	}

	@Test
	public void IsValidLogFileName_VariousNames_CheckEm() {
				
		LogAnalyzer analyzer = new LogAnalyzer();

		boolean result = analyzer.IsValidLogFileName(fileNameUT);

		assertEquals(result, resultExpected);
	}
}