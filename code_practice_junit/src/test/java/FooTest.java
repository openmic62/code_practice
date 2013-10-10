/**
 C:\Users\michaelr>subst j: "C:\Asus WebStorage\openmic62@gmail.com\MySyncFolder\student\junit\code_practice_junit"
 C:\Users\michaelr>j:
 J:\>cd src\test\java
 J:\src\test\java>l
 J:\src\test\java>javac -cp ..\junit-4.11.jar -d ..\..\..\target\test-classes FooTest.java
 J:\src\test\java>java -cp ..\junit-4.11.jar;..\hamcrest-core-1.3.jar;..\..\..\target\test-classes org.junit.runner.JUnitCore FooTest
 */
import static org.junit.Assert.assertEquals;

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
public class FooTest {

	@Test
	public void thisAlwaysPasses() {
	}

	@Test
	@Ignore
	public void thisIsIgnored() {
	}
}