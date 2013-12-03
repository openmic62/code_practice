package auctionsniper;

public class AuctionSniper implements AuctionEventListener {
	
	private SniperListener sniperListener;
	
	public AuctionSniper(SniperListener sl) {
		this.sniperListener = sl;
	}
	
	public void auctionClosed(){
		sniperListener.sniperLost();
	}
	
	public void currentPrice(int price, int increment){
	}
}