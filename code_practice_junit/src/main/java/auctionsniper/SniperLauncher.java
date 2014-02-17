package auctionsniper;

import auctionsniper.ui.SniperCollector;
//import auctionsniper.ui.SnipersTableModel;
//import auctionsniper.ui.SwingThreadSniperListener;
import auctionsniper.xmpp.AuctionHouse;
import auctionsniper.xmpp.XMPPAuctionHouse;

import java.util.ArrayList;

public class SniperLauncher implements UserRequestListener {
	
	/* <mlr 140217: begin - moved over to SnipersTableModel>
	@SuppressWarnings("unused") private ArrayList<Auction> notToBeGCd = new ArrayList<Auction>();
	<mlr 140217: end - moved over to SnipersTableModel> */

	private SniperCollector collector;
	//private SnipersTableModel snipers;
	//private XMPPAuctionHouse  auctionHouse;
	private AuctionHouse  auctionHouse;
	
	//public SniperLauncher(SnipersTableModel snipers, XMPPAuctionHouse auctionHouse) {
	public SniperLauncher(SniperCollector collector, AuctionHouse auctionHouse) {
		this.collector    = collector;
		//this.snipers      = snipers;
		this.auctionHouse = auctionHouse;
	}
	
  public void joinAuction(String itemId) {
  	//snipers.addSniper(SniperSnapshot.joining(itemId)); // <mlr 140208: AuctionSniper.java duplicates SniperSnapshot.joining(itemId)>
    Auction auction = auctionHouse.auctionFor(itemId);
    //notToBeGCd.add(auction); // <mlr 140217: moved over to SnipersTableModel>
    //AuctionEventListener sniper = new AuctionSniper(itemId, auction, new SwingThreadSniperListener(snipers));
    //AuctionEventListener sniper = new AuctionSniper(itemId, auction);
    AuctionSniper sniper = new AuctionSniper(itemId, auction);
    auction.addAuctionEventListener(sniper);
  	collector.addSniper(sniper); // <mlr 140208: AuctionSniper.java duplicates SniperSnapshot.joining(itemId)>
    //auction.addAuctionEventListener(sniper);
      
    auction.join();
  }
}