public abstract class Money {
	protected int amount;
	protected String currency;

	abstract Money times(int multiplier);
	
	String currency() {
		return currency;
	}
	
	public boolean equals(Object object) {
		Money money = (Money) object;
		return this.amount == money.amount
			&& this.getClass().equals(money.getClass());
	}
	
	static Money dollar(int amount) {
		return new Dollar(amount, "USD");
	}
	
	static Money franc(int amount) {
		return new Franc(amount, "CHF");
	}
}