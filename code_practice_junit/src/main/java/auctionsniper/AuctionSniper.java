package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	
	private Auction auction;
	private SniperListener sniperListener;
	private boolean isWinning = false;
	
	public AuctionSniper(Auction a, SniperListener sl) {
		this.auction = a;
		this.sniperListener = sl;
	}

	public void auctionClosed(){
		if (isWinning) {
		 	sniperListener.sniperWon();
		} else {
			sniperListener.sniperLost();
		}	}
	
	public void currentPrice(int price, int increment, PriceSource source){
		isWinning = source == PriceSource.FromSniper;
		if (isWinning) {
		 	sniperListener.sniperWinning();
		} else {
			auction.bid(price + increment);
		 	sniperListener.sniperBidding();
		}			
	}
}