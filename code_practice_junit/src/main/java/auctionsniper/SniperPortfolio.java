package auctionsniper;

import auctionsniper.AuctionSniper;
import auctionsniper.ui.SniperCollector;

import java.util.ArrayList;

public class SniperPortfolio implements SniperCollector {
	private ArrayList<AuctionSniper> snipers = new ArrayList<AuctionSniper>();
	private PortfolioListener sniperPortfolioListener;

  public void addPortfolioListener(PortfolioListener pl) {
  	this.sniperPortfolioListener = pl;
  }
  	
	// Implement the SniperCollector interface
	@Override
	public void addSniper(AuctionSniper auctionSniper) {
	  snipers.add(auctionSniper);
	  sniperPortfolioListener.sniperAdded(auctionSniper);
	}
}