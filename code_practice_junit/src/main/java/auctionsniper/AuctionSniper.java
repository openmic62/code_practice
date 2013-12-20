package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	
	private Auction auction;
	private SniperListener sniperListener;
	// private String sniperState = "JOINING"; <mlr 131220: my code>
	private boolean isWinning = false;
	
	public AuctionSniper(Auction a, SniperListener sl) {
		this.auction = a;
		this.sniperListener = sl;
	}
	
	/*
	public AuctionSniper(SniperListener sl) {
		this.sniperListener = sl;
	}*/
	
	public void auctionClosed(){
		if (isWinning) {
		 	sniperListener.sniperWon();
		} else {
			sniperListener.sniperLost();
		}
		/* <mlr 131220: begin - my code>
		if ("WINNING".equals(sniperState)) {
		 	sniperListener.sniperWon();
		} else if ("BIDDING".equals(sniperState)) {
		 	sniperListener.sniperLost();
		} else if ("JOINING".equals(sniperState)) {
		 	sniperListener.sniperLost();
		}
		<mlr 131220: end - my code> */
		///sniperListener.sniperLost();
	}
	
	public void currentPrice(int price, int increment, PriceSource source){
		isWinning = source == PriceSource.FromSniper;
		if (isWinning) {
		 	sniperListener.sniperWinning();
		} else {
			auction.bid(price + increment);
		 	sniperListener.sniperBidding();
		}			
		/* <mlr 131220: begin - my code>
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
		<mlr 131220: end - my code> */
	}
}