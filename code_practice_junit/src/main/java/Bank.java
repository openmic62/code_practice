class Bank {
	Money reduce(Expression source, String to) {
		return source.reduce(this, to);
		//return source.reduce(to);
	}

	int rate(String from, String to) {
		return (from.equals("CHF") && to.equals("USD"))
		? 2
		: 1;
	}

	// <mlr 131012: TDD, p. 67b; had to add this to get the green bar mentioned in the book.>
	void addRate(String currency1, String currency2, int exchangeRate) {}
}
