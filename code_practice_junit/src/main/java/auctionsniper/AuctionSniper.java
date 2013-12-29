package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	
	private Auction auction;
	private SniperListener sniperListener;
	private boolean isWinning = false;
	private String itemId;
	
	//public AuctionSniper(Auction a, SniperListener sl) {
	public AuctionSniper(String itemId, Auction a, SniperListener sl) {
		this.itemId = itemId;
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
			int bid = price + increment;
			//auction.bid(price + increment);
			auction.bid(bid);
			// <mlr 131225: ITEM_ID - changed per GOOS, p. 155a>
		 	//sniperListener.sniperBidding();
		 	//sniperListener.sniperBidding(null);
		 	//sniperListener.sniperBidding(new SniperSnapshot(itemId, price, bid));
		 	sniperListener.sniperBidding(new SniperSnapshot(itemId, price, bid, SniperState.BIDDING));
		}			
	}
}