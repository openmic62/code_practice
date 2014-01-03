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
		this.itemId = auctions[0].getItemID();
		
		Thread thread = new Thread("Test Application") {
			@Override public void run() {
				try {
					Main.main(arguments(auctions));
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
		for ( FakeAuctionServer auction : auctions ) {
			driver.showSniperStatus(auction.getItemID(), 0, 0, SnipersTableModel.textFor(SniperState.JOINING));
		}
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
	
	public void showsSniperHasLostAuction(int lastPrice, int lastBid) {
		driver.showSniperStatus(itemId, lastPrice, lastBid, SnipersTableModel.textFor(SniperState.LOST));
	}

	public void showsSniperIsWinning(FakeAuctionServer auction, int winningBid) {
		driver.showSniperStatus(itemId, winningBid, winningBid, SnipersTableModel.textFor(SniperState.WINNING));
	}

	public void showsSniperHasWonAuction(FakeAuctionServer auction, int lastPrice) {
		driver.showSniperStatus(itemId, lastPrice, lastPrice, SnipersTableModel.textFor(SniperState.WON));
	}
		
	public void stop() {
		if (driver != null) {
		 	driver.dispose();
		}
	}
}