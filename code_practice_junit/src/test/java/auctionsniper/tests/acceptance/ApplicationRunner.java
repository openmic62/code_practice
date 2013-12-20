package auctionsniper.tests.acceptance;

import auctionsniper.Main;
import auctionsniper.tests.AuctionSniperTestUtilities;

public class ApplicationRunner {
	
	static final String SNIPER_ID = "sniper";
	static final String SNIPER_PASSWORD = "sniper";
	private AuctionSniperDriver driver;
	
	// <mlr 131126: begin - p. 105, single item: join, bid, and lose>
	///public static final String SNIPER_XMPP_ID = "xxx";
	///public static final String SNIPER_XMPP_ID = "sniper@roco-3/Auction";
	public static final String SNIPER_XMPP_ID = String.format("sniper@%s/Auction", AuctionSniperTestUtilities.myGetHostName());
	// <mlr 131126: end - p. 105, single item: join, bid, and lose>
	
	public void startBiddingIn(FakeAuctionServer auction) {
		Thread thread = new Thread("Test Application") {
			@Override public void run() {
				final String[] mainArgs = {"localhost", SNIPER_ID, SNIPER_PASSWORD, "item-54321"};
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
		driver.showSniperStatus(Main.STATUS_JOINING);
	}
	
	public void hasShownSniperIsBidding(){
		driver.showSniperStatus(Main.STATUS_BIDDING);
	}
	
	public void showsSniperHasLostAuction() {
		driver.showSniperStatus(Main.STATUS_LOST);
	}

	public void showsSniperIsWinning() {
		driver.showSniperStatus(Main.STATUS_WINNING);
	}

	public void showsSniperHasWonAuction() {
		driver.showSniperStatus(Main.STATUS_WON);
	}
		
	public void stop() {
		if (driver != null) {
		 	driver.dispose();
		}
	}
}