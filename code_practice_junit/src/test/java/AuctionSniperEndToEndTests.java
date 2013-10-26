import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AuctionSniperEndToEndTests {
	
	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private final ApplicationRunner application = new ApplicationRunner();
	
	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {        
		auction.startSellingItem();                    // step 1
		//application.startBiddingIn(auction);           // step 2
		boolean result = 
		auction.hasReceivedJoinRequestFromSniper();    // step 3
		//assertTrue(result);
		assertTrue("auction.hasReceivedJoinRequestFromSniper() -> test failed", result);
		
		auction.announceClosed();                      // step 4
		//application.showsSniperHasLostAuction();       // step 5
	}
	
	// clean up
	@After
	public void stopAuction() {
		auction.stop();
	}
	
	@After
	public void stopApplication() {
		application.stop();
	}
}