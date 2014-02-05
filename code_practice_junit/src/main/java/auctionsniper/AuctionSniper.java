package auctionsniper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuctionSniper implements AuctionEventListener {
	static Logger logger = LogManager.getLogger(AuctionSniper.class.getName());	
		
	private Auction auction;
	private SniperListener sniperListener;
	private String itemId;
	private SniperSnapshot snapShot;
	
	public AuctionSniper(String itemId, Auction a, SniperListener sl) {
		this.itemId = itemId;
		this.auction = a;
		this.sniperListener = sl;
		
		this.snapShot = SniperSnapshot.joining(itemId);
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