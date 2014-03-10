package auctionsniper;

import java.util.EventListener;

public interface AuctionEventListener extends EventListener{
	
	public enum PriceSource {
		FromSniper, FromOtherBidder;
	}
	
	public void auctionClosed();
	// <mlr 140310: add failure detection code>
	public void auctionFailed();
	public void currentPrice(int price, int increment, PriceSource source);
}