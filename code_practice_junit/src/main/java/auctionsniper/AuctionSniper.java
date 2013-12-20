package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	
	private Auction auction;
	private SniperListener sniperListener;
	private String sniperState = "JOINING";
	
	public AuctionSniper(Auction a, SniperListener sl) {
		this.auction = a;
		this.sniperListener = sl;
	}
	
	/*
	public AuctionSniper(SniperListener sl) {
		this.sniperListener = sl;
	}*/
	
	public void auctionClosed(){
		if ("WINNING".equals(sniperState)) {
		 	sniperListener.sniperWon();
		} else if ("BIDDING".equals(sniperState)) {
		 	sniperListener.sniperLost();
		} else if ("JOINING".equals(sniperState)) {
		 	sniperListener.sniperLost();
		}
		///sniperListener.sniperLost();
	}
	
	public void currentPrice(int price, int increment, PriceSource source){
		switch (source) {
			case FromSniper: 
				sniperState = "WINNING";
		 	  sniperListener.sniperWinning();
				break;
			case FromOtherBidder:
				sniperState = "BIDDING";
				auction.bid(price + increment);
		 	  sniperListener.sniperBidding();
				break;
		}
	}
}