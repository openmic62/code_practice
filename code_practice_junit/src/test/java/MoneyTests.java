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

@RunWith(JUnit4.class)
public class MoneyTests {

	@Test
	public void testDollarMultiplication() {
		Money five = Money.dollar(5);
		assertEquals(Money.dollar(10), five.times(2));
		assertEquals(Money.dollar(15), five.times(3));
	}
	
	@Test
	public void testFrancMultiplication() {
		Franc five = new Franc(5);
		assertEquals(five.times(2), new Franc(10));
		assertEquals(five.times(3), new Franc(15));
		///assertEquals(new Franc(10), five.times(2));
		///assertEquals(new Franc(15), five.times(3));
	}
	
	@Test
	public void testDollarEquality(){
		assertTrue(Money.dollar(5).equals(new Dollar(5)));
		assertFalse(Money.dollar(5).equals(new Dollar(6)));
	}
	
	@Test
	public void testFrancEquality(){
		assertTrue(new Franc(5).equals(new Franc(5)));
		assertFalse(new Franc(5).equals(new Franc(6)));
	}
	
	@Test
	public void testDollarFrancInequality(){
		assertFalse(Money.dollar(5).equals(new Franc(5)));
		assertFalse(new Franc(5).equals(Money.dollar(5)));
	}
}