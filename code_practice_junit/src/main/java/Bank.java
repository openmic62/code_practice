class Bank {
	Money reduce(Expression sourceExp4Money, String to) {
		if (sourceExp4Money instanceof Money) return (Money) sourceExp4Money;
		Sum sum = (Sum) sourceExp4Money;
		return sum.reduce(to);
		//return Money.dollar(10);
	}
}
