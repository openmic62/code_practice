import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class FakeAuctionServerTests {
	
	@Test
	public void getSniperCommandFromMessage_withCmdJOIN_returnsJOIN() {
		
		FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		final String EXPECTED = "JOIN";
		final String ACTUAL = auction.getSniperCommandFromMessage("SQLVersion: 1.1; Command: JOIN;");
		assertEquals("Command with value ->JOIN<- should return a String \"JOIN\"", EXPECTED, ACTUAL);
	}
	
	@Test
	public void getSniperCommandFromMessage_withCmdBID_returnsBID() {
		
		FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		final String EXPECTED = "BID";
		final String ACTUAL = auction.getSniperCommandFromMessage("SQLVersion: 1.1; Command: BID;");
		assertEquals("Command with value ->BID<- should return a String \"BID\"", EXPECTED, ACTUAL);
	}

	@Test
	@Parameters({"AAA,1", "BBB,2"})
	public void params_in_annotation(String p1, Integer p2) { }

	/*
	@Parameters({"SQLVersion: 1.1; Command: JOIN;,JOIN",
		           "SQLVersion: 1.1; Command: BID;,BID"})
	@Parameters({"AAA,1", "BBB,2"})
	@Parameters({"AAA,1"})
	public void getSniperCommandFromMessage_calledWithACmd_returnsThatCmd(String message, String expectedResult) {
	*/
	@Test
	@Parameters({ "AAA" })
	public void getSniperCommandFromMessage_calledWithACmd_returnsThatCmd(String message) {

		//FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		//assertNotNull(expectedResult);
		assertNotNull(message);
		
		String messageHW = (message == null) ? "Hello, world!" : message; // ??? :-(
		//String messageHW = "Hello, world!";
		
		assertEquals("-->" + messageHW.toString() + "<--", "AAA", messageHW);
		
		/*
		String EXPECTED = expectedResult;
		String ACTUAL = auction.getSniperCommandFromMessage(message);
		assertEquals("Command with value ->"
		             + EXPECTED
		             + "<- should return a String \"" 
		             + EXPECTED 
		             + "\"", 
		             EXPECTED, ACTUAL);
		             */
	}
}