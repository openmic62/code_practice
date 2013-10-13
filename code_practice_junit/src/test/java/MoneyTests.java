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
	public void testMixedAddition() {
		Expression fiveBucks = Money.dollar(5);
		Expression tenFrancs  = Money.franc(10);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");
		assertEquals(Money.dollar(10), result);
	}

	@Test
	public void testIdentityRate() {
		assertEquals(1, new Bank().rate("USD", "USD"));
	}

	// <mlr 131012: I dunno. I think the book says that this should fail. But, mine passes.>
	@Test
	public void testArrayEquals() {
		assertEquals(new Object[] {"abc"}, new Object[] {"abc"});
	}

	@Test
	public void testReduceMoneyWithDifferentCurrencies() {
		Expression franc  = Money.franc(2);
		Bank bank = new Bank();
		bank.addRate("CHF", "USD", 2);
		Money reduced = bank.reduce(franc, "USD");
		assertEquals(Money.dollar(1), reduced);
	}
	
	@Test
	public void testReduceWithMoneyDollarArg() {
		Expression money = Money.dollar(1);
		Bank bank = new Bank();
		Money reduced = bank.reduce(money, "USD");
		assertEquals(Money.dollar(1), reduced);
	}
	
	@Test
	public void testReduceWithSumArg() {
		Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
		Bank bank = new Bank();
		Money actual = bank.reduce(sum, "USD");
		assertEquals(Money.dollar(7), actual);
	}		

	@Test
	public void testPlusReturnsSum() {
		Expression five = Money.dollar(5);
		Expression result = five.plus(five);
		Sum sum = (Sum) result;
		assertEquals(five, sum.augend);
		assertEquals(five, sum.addend);
	}

	@Test
	public void testSimpleAddition() {
		Expression five = Money.dollar(5);
		Expression sum = five.plus(five);
		Bank bank = new Bank();
		Money reduced = bank.reduce(sum, "USD");
		assertEquals(Money.dollar(10), reduced);
	}
	
	@Test
	public void testDollarMultiplication() {
		Money five = Money.dollar(5);
		assertEquals(Money.dollar(10), five.times(2));
		assertEquals(Money.dollar(15), five.times(3));
	}
	
	@Test
	public void testFrancMultiplication() {
		Money five = Money.franc(5);
		assertEquals(Money.franc(10), five.times(2));
		assertEquals(Money.franc(15), five.times(3));
	}
	
	@Test
	public void testEquality(){
		assertTrue(Money.dollar(5).equals(Money.dollar(5)));
		assertFalse(Money.dollar(5).equals(Money.dollar(6)));
		assertFalse(Money.dollar(5).equals(Money.franc(5)));
		assertFalse(Money.franc(5).equals(Money.dollar(5)));
	}
	
	@Test
	public void testCurrency() {
		assertEquals("USD", Money.dollar(5).currency());
		assertEquals("CHF", Money.franc(5).currency());
	}
}