package auctionsniper;

import auctionsniper.ui.SniperCollector;
import auctionsniper.xmpp.AuctionHouse;
import auctionsniper.xmpp.XMPPAuctionHouse;

import java.util.ArrayList;

public class SniperLauncher implements UserRequestListener {
	
	private SniperCollector collector;
	private AuctionHouse  auctionHouse;
	
	public SniperLauncher(SniperCollector collector, AuctionHouse auctionHouse) {
		this.collector    = collector;
		this.auctionHouse = auctionHouse;
	}
	
  //public void joinAuction(String itemId) {
  public void joinAuction(Item item) {
  	String itemId = item.getItemId();
    Auction auction = auctionHouse.auctionFor(itemId);
    //AuctionSniper sniper = new AuctionSniper(itemId, auction);
    AuctionSniper sniper = new AuctionSniper(item, auction);
    auction.addAuctionEventListener(sniper);
  	collector.addSniper(sniper);
      
    auction.join();
  }
}