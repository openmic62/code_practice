class Sum implements Expression {
	
	Money augend;
	Money addend;

	Sum (Money augend, Money addend) {
		this.augend = augend;
		this.addend = addend;
	}
	
	public Money reduce (String targetCurrency) {
		int amount = this.augend.amount + this.addend.amount;
		return new Money(amount, targetCurrency);
	}
}