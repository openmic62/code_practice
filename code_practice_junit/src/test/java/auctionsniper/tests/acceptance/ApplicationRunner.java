package auctionsniper.tests.acceptance;

import auctionsniper.Main;
import java.net.UnknownHostException;

public class ApplicationRunner {
	
	static final String SNIPER_ID = "sniper";
	static final String SNIPER_PASSWORD = "sniper";
	private AuctionSniperDriver driver;
	
	// <mlr 131126: begin - p. 105, single item: join, bid, and lose>
	///public static final String SNIPER_XMPP_ID = "xxx";
	///public static final String SNIPER_XMPP_ID = "sniper@roco-3/Auction";
	public static final String SNIPER_XMPP_ID = String.format("sniper@%s/Auction", myGetHostName());
	
	// <mlr 131205: begin - I added this to get around testing on Openfire running on home ("roco-3") and work ("vi-1057") laptops>
	private static String myGetHostName() {
		String hostName = null;
		try {
			hostName = java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException uhe) {
			 uhe.printStackTrace();
		}
		return hostName;
	}
	// <mlr 131205: begin - I added this to get around testing on Openfire running on home ("roco-3") and work ("vi-1057") laptops>

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
	
	// <mlr 131126: begin - p. 105, single item: join, bid, and lose>
	public void hasShownSniperIsBidding(){
		driver.showSniperStatus(Main.STATUS_BIDDING);
	}
	// <mlr 131126: end - p. 105, single item: join, bid, and lose>
	
	public void showsSniperHasLostAuction() {
		driver.showSniperStatus(Main.STATUS_LOST);
	}

	public void showsSniperHasWonAuction() {
		driver.showSniperStatus(Main.STATUS_WINNING);
	}
		
	public void stop() {
		if (driver != null) {
		 	driver.dispose();
		}
	}
}