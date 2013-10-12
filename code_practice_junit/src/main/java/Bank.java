class Bank {
	Money reduce(Expression sourceExp4Money, String to) {
		Sum sum = (Sum) sourceExp4Money;
		return sum.reduce(to);
		//return Money.dollar(10);
	}
}
