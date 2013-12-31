package auctionsniper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuctionSniper implements AuctionEventListener {
	static Logger logger = LogManager.getLogger(AuctionSniper.class.getName());	
		
	private Auction auction;
	private SniperListener sniperListener;
	/////private boolean isWinning = false;
	private String itemId;
	private SniperSnapshot snapShot;
	
	//public AuctionSniper(Auction a, SniperListener sl) {
	public AuctionSniper(String itemId, Auction a, SniperListener sl) {
		this.itemId = itemId;
		this.auction = a;
		this.sniperListener = sl;
		
		this.snapShot = SniperSnapshot.joining(itemId);
	}

	public void auctionClosed(){
		/*
		if (isWinning) {
		 	//sniperListener.sniperWon();
		 	//sniperListener.sniperStateChanged(snapShot.won());
		 	///snapShot = snapShot.won();
		 	snapShot = snapShot.closed();
			//sniperListener.sniperStateChanged(snapShot);
		} else {
			//sniperListener.sniperLost();
			//sniperListener.sniperStateChanged(snapShot.lost());
			///snapShot = snapShot.lost();
			snapShot = snapShot.closed();
			//sniperListener.sniperStateChanged(snapShot);
		}
		*/
		snapShot = snapShot.closed();
		notifyChange();
		////sniperListener.sniperStateChanged(snapShot);
	}
	
	public void currentPrice(int price, int increment, PriceSource priceSource){
	/*public void currentPrice(int price, int increment, PriceSource source){
		isWinning = source == PriceSource.FromSniper;
		//-SniperSnapshot newSnapshot;
		if (isWinning) {
		 	//sniperListener.sniperWinning();
		 	//-newSnapshot = snapShot.winning(price);
		 	snapShot = snapShot.winning(price);
		} else {
			int bid = price + increment;
			//auction.bid(price + increment);
			auction.bid(bid);
			// <mlr 131225: ITEM_ID - changed per GOOS, p. 155a>
		 	//sniperListener.sniperBidding();
		 	//sniperListener.sniperBidding(null);
		 	//sniperListener.sniperBidding(new SniperSnapshot(itemId, price, bid));
		 	//sniperListener.sniperBidding(new SniperSnapshot(itemId, price, bid, SniperState.BIDDING));
		 	//sniperListener.sniperStateChanged(new SniperSnapshot(itemId, price, bid, SniperState.BIDDING));
      //-newSnapshot = snapShot.bidding(price, bid);
      snapShot = snapShot.bidding(price, bid);
		}
		*/
		
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
		////sniperListener.sniperStateChanged(snapShot);
		notifyChange();
	}
	
	private void notifyChange() {
		sniperListener.sniperStateChanged(snapShot);
	}
}