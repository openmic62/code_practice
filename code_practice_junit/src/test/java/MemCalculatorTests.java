/**
C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
C:\Users\michaelr>j:
J:\>cd src\test\java
J:\src\test\java>l
J:\src\test\java>javac -cp ..\junit-4.11.jar;..\hamcrest-core-1.3.jar;..\..\..\target\classes -d ..\..\..\target\test-classes MemCalculatorTests.java
J:\src\test\java>java -cp ..\junit-4.11.jar;..\hamcrest-core-1.3.jar;..\..\..\target\test-classes;..\..\..\target\classes org.junit.runner.JUnitCore MemCalculatorTests
*/
import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.Matcher;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
* Tests for {@link Foo}.
*
* @author user@example.com (John Doe)
*/
@RunWith(JUnit4.class)
public class MemCalculatorTests {
	
	@Test
	public void sum_ByDefault_ReturnsZero() {
		
		MemCalculator mc = makeMemCalculator();

		int result = mc.sum();

		assertEquals(0, result);
	}
	
	@Test
	public void add_WhenCalled_ChangesSum() {
		
		MemCalculator mc = makeMemCalculator();
		
		mc.add(10);
		int result = mc.sum();
		
		assertEquals(10, result);
	}
		
	
	private MemCalculator makeMemCalculator() {
		return new MemCalculator();
		}
}