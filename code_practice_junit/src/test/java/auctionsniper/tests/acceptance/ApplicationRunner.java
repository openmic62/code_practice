package auctionsniper.tests.acceptance;

import auctionsniper.Main;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.tests.AuctionSniperTestUtilities;
import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SnipersTableModel;

public class ApplicationRunner {
	
	private final String LOCALHOST       = AuctionSniperTestUtilities.LOCALHOST;
	private final String SNIPER_ID       = AuctionSniperTestUtilities.SNIPER_ID;
	private final String SNIPER_PASSWORD = AuctionSniperTestUtilities.SNIPER_PASSWORD;
	private AuctionSniperDriver driver;

	private String itemId;

	public static final String SNIPER_XMPP_ID = String.format("sniper@%s/Auction", AuctionSniperTestUtilities.myGetHostName());

	public void startBiddingIn(final FakeAuctionServer... auctions) {
				
		startSniper();
		
		for ( FakeAuctionServer auction : auctions ) {
			final String itemId = auction.getItemID();
			driver.startBiddingFor(itemId);
			driver.showSniperStatus(auction.getItemID(), 0, 0, SnipersTableModel.textFor(SniperState.JOINING));
		}
	}
	
	private void startSniper() {
		Thread thread = new Thread("Test Application") {
			@Override public void run() {
				try {
					Main.main( arguments() );
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
	
	private String[] arguments() {
		String[] args = new String[3];
		args[0] = LOCALHOST;
		args[1] = SNIPER_ID;
		args[2] = SNIPER_PASSWORD;

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