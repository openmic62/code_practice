package auctionsniper;

public enum SniperState {
	JOINING {
		@Override public SniperState whenAuctionClosed() { return LOST; }
	},
	BIDDING {
		@Override public SniperState whenAuctionClosed() { return LOST; }
	},
	WINNING {
		@Override public SniperState whenAuctionClosed() { return WON; }
	},
	LOSING {
		@Override public SniperState whenAuctionClosed() { return LOST; }
	},
	LOST,
	WON,
	FAILED; // <mlr 140310: add failure detection code>
	
	public SniperState whenAuctionClosed() {
		throw new Defect("Auction is already closed");
	}
}
