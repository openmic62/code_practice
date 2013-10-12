class Bank {
	Money reduce(Expression source, String to) {
		return source.reduce(to);
	}
	
	// <mlr 131012: TDD, p. 67b; had to add this to get the green bar mentioned in the book.>
	void addRate(String currency1, String currency2, int exchangeRate) {}
}
