package auctionsniper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuctionSniper implements AuctionEventListener {
	static Logger logger = LogManager.getLogger(AuctionSniper.class.getName());	
		
	private Auction auction;
	private SniperListener sniperListener;
	private String itemId;
	private SniperSnapshot snapShot;
	
	//public AuctionSniper(String itemId, Auction a, SniperListener sl) {
	public AuctionSniper(String itemId, Auction a) {
		this.itemId = itemId;
		this.auction = a;
		//this.sniperListener = sl;
		
		this.snapShot = SniperSnapshot.joining(itemId); // <mlr 140208: SniperLauncher.java duplicates SniperSnapshot.joining(itemId)>
	}
	
	// <mlr 140213: I added this when teasing out SnipersTableModel from Main>
	public SniperSnapshot getSnapShot() {return this.snapShot;}

	// <mlr 140217: I added this when teasing out SnipersTableModel from Main>
	public void addSniperListener(SniperListener sl) {
		this.sniperListener = sl;
	}

	@Override
	public void auctionClosed(){
		snapShot = snapShot.closed();
		notifyChange();
	}
	
	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource){		
		switch (priceSource) {
			case FromSniper:
				snapShot = snapShot.winning(price);
				break;
			case FromOtherBidder:
			  int bid = price + increment;
			  auction.bid(bid);
			  snapShot = snapShot.bidding(price, bid);
				break;
		}	
		notifyChange();
	}
	
	private void notifyChange() {
		sniperListener.sniperStateChanged(snapShot);
	}
}