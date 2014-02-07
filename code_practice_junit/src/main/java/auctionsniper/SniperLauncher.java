package auctionsniper;

import auctionsniper.ui.SnipersTableModel;
import auctionsniper.ui.SwingThreadSniperListener;
import auctionsniper.xmpp.XMPPAuctionHouse;

import java.util.ArrayList;

public class SniperLauncher implements UserRequestListener {
	
	@SuppressWarnings("unused") private ArrayList<Auction> notToBeGCd = new ArrayList<Auction>();
	private SnipersTableModel snipers;
	private XMPPAuctionHouse  auctionHouse;
	
	SniperLauncher(SnipersTableModel snipers, XMPPAuctionHouse auctionHouse) {
		this.snipers      = snipers;
		this.auctionHouse = auctionHouse;
	}
	
  public void joinAuction(String itemId) {
  	snipers.addSniper(SniperSnapshot.joining(itemId));
    Auction auction = auctionHouse.auctionFor(itemId);
    notToBeGCd.add(auction);
    AuctionEventListener sniper = new AuctionSniper(itemId, auction, new SwingThreadSniperListener(snipers));
    auction.addAuctionEventListener(sniper);
      
    auction.join();
  }
}