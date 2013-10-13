import java.util.Hashtable;

class Bank {
	
	private Hashtable<Pair, Integer> rates = new Hashtable<Pair, Integer>();
	
	Money reduce(Expression source, String to) {
		return source.reduce(this, to);
		//return source.reduce(to);
	}

	int rate(String from, String to) {
		if ( from.equals(to) ) {
			return 1;
		}
		Integer rate = rates.get(new Pair(from, to));
		return rate.intValue();
		/*
		return (from.equals("CHF") && to.equals("USD"))
		? 2
		: 1;
		*/
	}

	// <mlr 131012: TDD, p. 67b; had to add this to get the green bar mentioned in the book.>
	// <mlr 131012: TDD, p. 70a; the book finally shows this method.
	void addRate(String from, String to, int rate) {
		rates.put(new Pair(from, to), new Integer(rate));
	}
	
	private class Pair {
		private String from;
		private String to;
		
		Pair( String from, String to ) {
			this.from = from;
			this.to = to;
		}
		
		public boolean equals( Object obj ) {
			Pair pair = (Pair) obj;
			return from.equals(pair.from) && to.equals(pair.to);
		}
		
		public int hashCode() {
			return 0;
		}
	}
}
