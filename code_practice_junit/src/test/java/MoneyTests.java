/*
H:\student\code_practice_junit\src\test\java>javac -cp ..\..\..\lib\junit-4.11.jar -d ..\..\..\target\classes MoneyTests.java
H:\student\code_practice_junit\src\test\java>java -cp ..\..\..\lib\junit-4.11.jar;..\..\..\lib\hamcrest-core-1.3.jar;..\..\..\target\classes org.junit.runner.JUnitCore MoneyTests
*/
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

class Dollar {
	int amount=10;
	Dollar(int amount){}
	void times(int multiplier){}
}

@RunWith(JUnit4.class)
public class MoneyTests {
	
	@Test
	public void testMultiplication() {
		Dollar five = new Dollar(5);
		five.times(2);
		assertEquals(10, five.amount);
	}
	
}