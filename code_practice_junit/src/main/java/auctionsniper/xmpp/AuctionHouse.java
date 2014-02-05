package auctionsniper.xmpp;

import auctionsniper.Auction;

public interface AuctionHouse {
	public Auction auctionFor(String itemId);
}