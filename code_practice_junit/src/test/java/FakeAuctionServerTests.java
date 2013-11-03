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
/*
  // <mlr 131031: this test passes in ant and in mvn>
	@Test
	@Parameters({"AAA,1", "BBB,2"})
	public void params_in_annotation(String p1, Integer p2) { }
	
  // <mlr 131031: this test passes in ant and in mvn>
  @Test
  @Parameters({ "1", "2" })
  public void singleParam(int number) {
      assertTrue(number > 0);
  }
*/	
/*  
	// <mlr 131031: this test NPEs in ant, FAILS 2 tests in mvn> <<<<<<<<<<<<<<<<<<<<<<<!!!!!!!!!<<<<<<<<<<<<<<<<<<<<<<<<<<<
  @Test
  @Parameters({ "a \n b", "a(asdf)", "a \r a" })
  public void specialCharsInParam(String a) throws Exception {
  	assertEquals("a \n b", a);
  }
*/	

/*
  // <mlr 131031: this test passes in ant and in mvn>
  @Test
  @Parameters({ "1, false", "2, true" })
  public void multipleParams(int number, boolean what) throws Exception {
      assertEquals(what, number > 1);
  }

*/
/*
	// <mlr 131031: this test NPEs in ant BUT PASSES in mvn> <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
  @Test
  @Parameters({ "1," })
  public void emptyParam(int number, String empty) {
      assertEquals(1, number);
      assertEquals("", empty);
  }
*/

  /*********************************** BEGIN - use junitparams to parameterize these 2 tests */
  /*
  // <mlr 131031: this test passes in ant and in mvn>
	@Test
	public void getSniperCommandFromMessage_withCmdJOIN_returnsJOINString() {
		
		FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		final String EXPECTED = "JOIN";
		final String ACTUAL = auction.getSniperCommandFromMessage("SQLVersion: 1.1; Command: JOIN;");
		assertEquals("Command with value ->JOIN<- should return a String \"JOIN\"", EXPECTED, ACTUAL);
	}
	
  // <mlr 131031: this test passes in ant and in mvn>
	@Test
	public void getSniperCommandFromMessage_withCmdBID_returnsBIDString() {
		
		FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		final String EXPECTED = "BID";
		final String ACTUAL = auction.getSniperCommandFromMessage("SQLVersion: 1.1; Command: BID;");
		assertEquals("Command with value ->BID<- should return a String \"BID\"", EXPECTED, ACTUAL);
	}
  */
  /************************************* END - use junitparams to parameterize these 2 tests */
  
	/*
	@Parameters({"SQLVersion: 1.1; Command: JOIN;,JOIN",
		           "SQLVersion: 1.1; Command: BID;,BID"})
	@Parameters({"AAA,1", "BBB,2"})
	@Parameters({"AAA,1"})
	@Parameters({ "AAA" })
	public void getSniperCommandFromMessage_calledWithACmd_returnsThatCmd(String message, String expectedResult) {
	public void getSniperCommandFromMessage_calledWithACmd_returnsThatCmd(String message) {
	*/
	
	/*
	// <mlr 131031: this test NPEs in ant BUT PASSES in mvn> <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Test
	@Parameters({"SQLVersion: 1.1; Command: JOIN;,JOIN",
		           "SQLVersion: 1.1; Command: BID;,BID"})
	public void getSniperCommandFromMessage_calledWithACmd_returnsCmdString(String message, String expectedResult) {
		FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		String EXPECTED = expectedResult;
		String ACTUAL = auction.getSniperCommandFromMessage(message);
		assertEquals("Command with value ->"
		             + EXPECTED
		             + "<- should return a String \"" 
		             + EXPECTED 
		             + "\"", 
		             EXPECTED, ACTUAL);
	}
	*/
}