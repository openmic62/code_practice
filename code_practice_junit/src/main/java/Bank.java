class Bank {
	Money reduce(Expression sourceExp4Money, String targetCurrency) {
		Sum sum = (Sum) sourceExp4Money;
		int amount = sum.augend.amount + sum.addend.amount;
		return new Money(amount, targetCurrency);
		//return Money.dollar(10);
	}
}
