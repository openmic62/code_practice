import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

class Dollar {
	int amount;
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