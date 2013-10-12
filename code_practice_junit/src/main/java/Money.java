public class Money implements Expression {
	protected int amount;
	protected String currency;

	Money(int amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}
	
	Expression plus(Money addend) {
		return new Sum(this, addend);
		//return new Sum(this.amount + addend.amount, this.currency);
		//return new Money(this.amount + addend.amount, this.currency);
	}
	
	Money times(int multiplier) {
		return new Money(this.amount * multiplier, this.currency);
	}
	
	public Money reduce(String to) {
		int rate = (currency.equals("CHF") && to.equals("USD"))
			? 2
			: 1;
		return new Money(amount / rate, to);
		//return this;
	}
	
	String currency() {
		return currency;
	}
	
	public boolean equals(Object object) {
		Money money = (Money) object;
		return this.amount == money.amount
			&& this.currency().equals(money.currency());
	}
	
	static Money dollar(int amount) {
		return new Money(amount, "USD");
	}
	
	static Money franc(int amount) {
		return new Money(amount, "CHF");
	}
	
	@Override
	public String toString() {
		return amount + " " + currency;
	}
}