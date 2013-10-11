/*
H:\student\code_practice_junit\src\test\java>javac -cp ..\..\..\lib\junit-4.11.jar -d ..\..\..\target\classes MoneyTests.java
H:\student\code_practice_junit\src\test\java>java -cp ..\..\..\lib\junit-4.11.jar;..\..\..\lib\hamcrest-core-1.3.jar;..\..\..\target\classes org.junit.runner.JUnitCore MoneyTests
*/
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

class Dollar {
	private int amount;
	Dollar(int amount){
		this.amount = amount;
	}
	Dollar times(int multiplier){
		return new Dollar(amount * multiplier);
	}
	public boolean equals(Object object) {
		Dollar dollar = (Dollar) object;
		return this.amount == dollar.amount;
	}
}

@RunWith(JUnit4.class)
public class MoneyTests {

	@Test
	public void testMultiplication() {
		Dollar five = new Dollar(5);
		assertEquals(new Dollar(10), five.times(2));
		assertEquals(new Dollar(15), five.times(3));
	}
	
	@Test
	public void testEquality(){
		assertTrue(new Dollar(5).equals(new Dollar(5)));
		assertFalse(new Dollar(5).equals(new Dollar(6)));
	}
}