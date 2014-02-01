package auctionsniper;

import org.jivesoftware.smack.Chat;

public interface Auction {
	public Chat getChat();
	
	public void bid(int bidPrice);
	public void join();
}