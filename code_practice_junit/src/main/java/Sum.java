class Sum implements Expression {
	
	Expression augend;
	Expression addend;

	Sum (Expression augend, Expression addend) {
		this.augend = augend;
		this.addend = addend;
	}
	
	public Money reduce (Bank bank, String targetCurrency) {
		int amount = this.augend.reduce(bank, targetCurrency).amount 
		           + this.addend.reduce(bank, targetCurrency).amount;
		return new Money(amount, targetCurrency);
	}
	
	public Expression plus(Expression addend) {
		return new Sum(this, addend);
	}
	
	public Expression times(int multiplier) {
		return new Sum(augend.times(multiplier), addend.times(multiplier));
	}
}