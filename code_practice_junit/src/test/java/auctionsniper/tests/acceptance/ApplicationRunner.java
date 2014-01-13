package auctionsniper.tests.acceptance;

import auctionsniper.Main;
import auctionsniper.MainWindow;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.SnipersTableModel;
import auctionsniper.tests.AuctionSniperTestUtilities;

public class ApplicationRunner {
	
	static final String SNIPER_ID = "sniper";
	static final String SNIPER_PASSWORD = "sniper";
	private AuctionSniperDriver driver;

	private String itemId;

	public static final String SNIPER_XMPP_ID = String.format("sniper@%s/Auction", AuctionSniperTestUtilities.myGetHostName());

	public void startBiddingIn(final FakeAuctionServer... auctions) {
				
		//startSniper();
		startSniper(auctions);
		
		for ( FakeAuctionServer auction : auctions ) {
			final String itemId = auction.getItemID();
			driver.startBiddingFor(itemId);
			driver.showSniperStatus(auction.getItemID(), 0, 0, SnipersTableModel.textFor(SniperState.JOINING));
		}
	}
	
	//private void startSniper() {
	private void startSniper(final FakeAuctionServer... auctions) {
		Thread thread = new Thread("Test Application") {
			@Override public void run() {
				try {
					Main.main(arguments(                    auctions));
					//Main.main(  arguments( (FakeAuctionServer)    null));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		driver.hasTitle(MainWindow.MAIN_WINDOW_NAME);
		driver.hasColumnTitles();
	}
	
	private String[] arguments(FakeAuctionServer... auctions) {
		String[] args = new String[3 + auctions.length];
		args[0] = "localhost";
		args[1] = SNIPER_ID;
		args[2] = SNIPER_PASSWORD;
		for (int i=0; i<auctions.length; i++) {
		 	args[i+3] = auctions[i].getItemID();
		}
		return args;
	}
	
	public void hasShownSniperIsBidding(FakeAuctionServer auction, int lastPrice, int lastBid){
		driver.showSniperStatus(auction.getItemID(), lastPrice, lastBid, SnipersTableModel.textFor(SniperState.BIDDING));
	}
	
	public void showsSniperHasLostAuction(FakeAuctionServer auction, int lastPrice, int lastBid) {
		driver.showSniperStatus(auction.getItemID(), lastPrice, lastBid, SnipersTableModel.textFor(SniperState.LOST));
	}

	public void showsSniperIsWinning(FakeAuctionServer auction, int winningBid) {
		driver.showSniperStatus(auction.getItemID(), winningBid, winningBid, SnipersTableModel.textFor(SniperState.WINNING));
	}

	public void showsSniperHasWonAuction(FakeAuctionServer auction, int lastPrice) {
		driver.showSniperStatus(auction.getItemID(), lastPrice, lastPrice, SnipersTableModel.textFor(SniperState.WON));
	}
		
	public void stop() {
		if (driver != null) {
		 	driver.dispose();
		}
	}
}