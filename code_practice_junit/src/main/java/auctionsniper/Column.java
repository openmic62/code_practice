package auctionsniper;

public enum Column {
	ITEM_IDENTIFIER("Item") {
		@Override public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getItemId();
		}
	},
	
	LAST_PRICE("Last Price") {
		@Override public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getLastPrice();
		}
	},
	
	LAST_BID("Last Bid") {
		@Override public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getLastBid();
		}
	},
	
	SNIPER_STATE("State") {
		@Override public Object valueIn(SniperSnapshot snapshot) {
			//return snapshot.getState();
			return SnipersTableModel.textFor(snapshot.getState());
		}
	};
	
	public final String name;
	
	private Column(String name) {
		this.name = name;
	}
	
	public static Column at(int offset) { return values()[offset]; }
	
	public abstract Object valueIn(SniperSnapshot snapshot);
}