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
	// <mlr 131225: itemId - added per GOOS, p. 153a>
	private String itemId;
		
	// <mlr 131126: begin - p. 105, single item: join, bid, and lose>
	///public static final String SNIPER_XMPP_ID = "xxx";
	///public static final String SNIPER_XMPP_ID = "sniper@roco-3/Auction";
	public static final String SNIPER_XMPP_ID = String.format("sniper@%s/Auction", AuctionSniperTestUtilities.myGetHostName());
	// <mlr 131126: end - p. 105, single item: join, bid, and lose>
	
	public void startBiddingIn(FakeAuctionServer auction) {
		this.itemId = auction.getItemID();
		
		Thread thread = new Thread("Test Application") {
			@Override public void run() {
				// <mlr 131225: ITEM_ID - added per GOOS, p. 155a>
				//final String[] mainArgs = {"localhost", SNIPER_ID, SNIPER_PASSWORD, "item-54321"};
				final String[] mainArgs = {"localhost", SNIPER_ID, SNIPER_PASSWORD, AuctionSniperTestUtilities.ITEM_ID};
				try {
					Main.main(mainArgs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		thread.setDaemon(true);
		thread.start();
		driver = new AuctionSniperDriver(1000);
		//driver.showSniperStatus(MainWindow.STATUS_JOINING);
		//driver.showSniperStatus("", 0, 0, MainWindow.STATUS_JOINING);
		driver.showSniperStatus("", 0, 0, SnipersTableModel.textFor(SniperState.JOINING));
	}
	
	public void hasShownSniperIsBidding(int lastPrice, int lastBid){
		//driver.showSniperStatus(MainWindow.STATUS_BIDDING);
		//driver.showSniperStatus(itemId, lastPrice, lastBid, MainWindow.STATUS_BIDDING);
		driver.showSniperStatus(itemId, lastPrice, lastBid, SnipersTableModel.textFor(SniperState.BIDDING));
	}
	
	//public void showsSniperHasLostAuction() {
	public void showsSniperHasLostAuction(int lastPrice, int lastBid) {
		//driver.showSniperStatus(MainWindow.STATUS_LOST);
		//driver.showSniperStatus(SnipersTableModel.textFor(SniperState.LOST));
		driver.showSniperStatus(itemId, lastPrice, lastBid, SnipersTableModel.textFor(SniperState.LOST));
	}

	public void showsSniperIsWinning(int winningBid) {
		//driver.showSniperStatus(MainWindow.STATUS_WINNING);
		//driver.showSniperStatus(itemId, winningBid, winningBid, MainWindow.STATUS_WINNING);
		driver.showSniperStatus(itemId, winningBid, winningBid, SnipersTableModel.textFor(SniperState.WINNING));
	}

	public void showsSniperHasWonAuction(int lastPrice) {
		//driver.showSniperStatus(MainWindow.STATUS_WINNING);
		//driver.showSniperStatus(itemId, lastPrice, lastPrice, MainWindow.STATUS_WON);
		driver.showSniperStatus(itemId, lastPrice, lastPrice, SnipersTableModel.textFor(SniperState.WON));
	}
		
	public void stop() {
		if (driver != null) {
		 	driver.dispose();
		}
	}
}