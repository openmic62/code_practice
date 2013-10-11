//public abstract class Money { 
public class Money {
	protected int amount;
	protected String currency;

	Money(int amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	//abstract Money times(int multiplier);
	Money times(int multiplier) {
		return null;
	}
	
	String currency() {
		return currency;
	}
	
	public boolean equals(Object object) {
		Money money = (Money) object;
		return this.amount == money.amount
			&& this.currency().equals(money.currency());
			//&& this.getClass().equals(money.getClass());
	}
	
	static Money dollar(int amount) {
		return new Dollar(amount, "USD");
	}
	
	static Money franc(int amount) {
		return new Franc(amount, "CHF");
	}
	
	@Override
	public String toString() {
		return amount + " " + currency;
	}
}