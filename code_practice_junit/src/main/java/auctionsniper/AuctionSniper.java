package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	
	private Auction auction;
	private SniperListener sniperListener;
	
	public AuctionSniper(Auction a, SniperListener sl) {
		this.auction = a;
		this.sniperListener = sl;
	}
	
	/*
	public AuctionSniper(SniperListener sl) {
		this.sniperListener = sl;
	}*/
	
	public void auctionClosed(){
		sniperListener.sniperLost();
	}
	
	public void currentPrice(int price, int increment){
		auction.bid(price + increment);
		sniperListener.sniperBidding();
	}
}