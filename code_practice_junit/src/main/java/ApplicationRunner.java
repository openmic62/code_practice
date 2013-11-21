public class ApplicationRunner {
	
	static final String SNIPER_ID = "sniper";
	static final String SNIPER_PASSWORD = "sniper";
	private AuctionSniperDriver driver;
	
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
	
	public void receivesPrice() {}
	public void sendsHigherBid() {}
	
	public void showsSniperHasLostAuction() {
		driver.showSniperStatus(Main.STATUS_LOST);
	}
	
	public void stop() {
		if (driver != null) {
		 	driver.dispose();
		}
	}
}