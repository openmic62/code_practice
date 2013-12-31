package auctionsniper;

public enum Column {
	
	ITEM_IDENTIFIER {
		@Override public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getItemId();
		}
	},
	
	LAST_PRICE {
		@Override public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getLastPrice();
		}
	},
	
	LAST_BID {
		@Override public Object valueIn(SniperSnapshot snapshot) {
			return snapshot.getLastBid();
		}
	},
	
	SNIPER_STATE {
		@Override public Object valueIn(SniperSnapshot snapshot) {
			//return snapshot.getState();
			return SnipersTableModel.textFor(snapshot.getState());
		}
	};
	
	public static Column at(int offset) { return values()[offset]; }
	
	public abstract Object valueIn(SniperSnapshot snapshot);
}