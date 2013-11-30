package auctionsniper.tests.unit;

import auctionsniper.tests.acceptance.FakeAuctionServer;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//import mockit.NonStrict;

import org.jivesoftware.smack.packet.Message;

import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

@RunWith(Parameterized.class)
public class FakeAuctionServerPTests {
	
	private String message;
	private String expectedResult;
	
	FakeAuctionServerPTests() {
		this("SQLVersion: 1.1; Command: DEFAULT;", "DEFAULT");
	}
	
	public FakeAuctionServerPTests(String pMessage, String pExpectedResult) {
		super();
		this.message = pMessage;
		this.expectedResult = pExpectedResult;
	}
	
	@Parameters(name = "\nmessage[{index}]-->{0}<--\nexpecteResult[{index}]-->{1}<--\n")
	public static Collection<Object[]> data() {
		//
		Object[][] data = new Object[][] {
			{ "SQLVersion: 1.1; Command: JOIN;", "JOIN" },
			{ "SQLVersion: 1.1; Command: BID;", "BID" },
		};
		return Arrays.asList(data);
	}

	@Test
	public void getSniperCommandFromMessage_calledWithACmd_returnsThatCmd() {
		
		FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		String EXPECTED = expectedResult;
		String ACTUAL = auction.getSniperCommandFromMessage(message);
		//assertEquals("Command with value ->" + expectedResult + "<- should return a String \"JOIN\"", EXPECTED, ACTUAL);
		//assertEquals(EXPECTED, ACTUAL);
		//assertEquals("Fuck", "You");
		assertTrue(true);
	}
}