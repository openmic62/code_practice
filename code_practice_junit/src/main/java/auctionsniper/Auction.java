package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;

public interface Auction {
	public void addAuctionEventListener(AuctionEventListener ael);
	public void bid(int bidPrice);
	public void join();
}