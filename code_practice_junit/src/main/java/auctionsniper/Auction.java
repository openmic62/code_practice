package auctionsniper;

public interface Auction {
	public void addAuctionEventListener(AuctionEventListener ael);
	public void bid(int bidPrice);
	public void join();
}