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
		//int amount = this.augend.amount + this.addend.amount;
		return new Money(amount, targetCurrency);
	}
}