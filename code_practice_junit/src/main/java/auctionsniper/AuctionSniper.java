package auctionsniper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuctionSniper implements AuctionEventListener {
	static Logger logger = LogManager.getLogger(AuctionSniper.class.getName());	
		
	private Auction auction;
	private SniperListener sniperListener;
	private Item item;
	private SniperSnapshot snapShot;
	
	public AuctionSniper(Item item, Auction a) {
		this.item     = item;
    this.auction  = a;
		this.snapShot = SniperSnapshot.joining(item.getItemId());
	}
	
	public SniperSnapshot getSnapShot() {return this.snapShot;}

	public void addSniperListener(SniperListener sl) {
		this.sniperListener = sl;
	}

	// Implement the AuctionEventListener interface
	@Override
	public void auctionClosed(){
		snapShot = snapShot.closed();
		notifyChange();
	}
	
	// <mlr 140310: begin - add failure detection code>
	@Override
	public void auctionFailed(){
		snapShot = snapShot.failed();
		notifyChange();
	}
	// <mlr 140310: end - add failure detection code>
	
	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource){		
		switch (priceSource) {
			case FromSniper:
				snapShot = snapShot.winning(price);
				break;
			case FromOtherBidder:
			  int bid = price + increment;
			  if ( item.allowsBid(bid) ) {
			    auction.bid(bid);
			  	snapShot = snapShot.bidding(price, bid); 
			  } else {
			  	snapShot = snapShot.losing(price);
			  }
				break;
		}	
		notifyChange();
	}
	
	private void notifyChange() {
		sniperListener.sniperStateChanged(snapShot);
	}
}