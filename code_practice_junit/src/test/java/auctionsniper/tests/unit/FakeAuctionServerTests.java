/**
 *
 * How to build a test API for my application - Example
 * <u>Growing Object-Oriented Software, Guided by Tests</u><br>
 *    Part III: A Worked Example<br>
 * 		Ch. 11 Passing the First Test<br>
 *    The Fake Auction<br>
 *    p. 92a
 *
 * FakeAuctionServer is a substitute server that allows this test [AuctionSniperEndToEndTests]
 * to check how the Auction Sniper [the app we are developing for our customer]
 * interacts with an auction using XMPP messages. FakeAuctionServer has three responsibilities:
 * 1) it must connect to the XMPP broker [Openfire] and accept a request to
 *    join the chat from the Sniper;
 * 2) it must receive chat messages from the Sniper or fail if no message
 *    arrives within some timeout;
 * 3) it must allow the test to send messages back to the Sniper as specified by
 *    Southabee's On-Line.
 *
 * In this class I exercise the skills I acquired from studying The Art of Unit Testing
 * (AOUT) and Test Driven Development (TDD).

 -------------------------------------------------------------------------------

 --------------- which java ------------------
 for %i in (java.exe) do @echo.   %~$PATH:i
 --------------- which java ------------------
 
 For JMockit java.exe MUST be of JDK (with attach.dll) like
   C:\Program Files\Java\jdk1.7.0_02\bin\java.exe
 NOT the JRE one (missing attach.dll) Java automatically installs at
   C:\Windows\system32\java.exe
   
 Work the System PATH variable to ensure correct java.exe gets used.
 PATH = (system) PATH + (user) PATH - in that specific order
 
 -------------------------------------------------------------------------------
   
 C:\Users\michaelr>h:
 ***** Abstract the ENV
 H:\> copy and paste the following lines straight onto the command line
 
 set CLASSPATH=lib\Smack.jar;lib\Smackx.jar;lib\Smackx-debug.jar;lib\junit-4.11.jar;lib\hamcrest-all-1.3.jar;lib\JUnitParams-1.0.3-SNAPSHOT.jar
 set SC=target\classes
 set TC=target\test-classes
 set SD=src\main\java
 set TD=src\test\java
 
 ***** build the FakeAuctionServer source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set FAS_FILES=FakeAuctionServer.java
 H:\student\code_practice_junit>echo %FAS_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\%FAS_FILES%

 ***** build the Tests FakeAuctionServerTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\FakeAuctionServerTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.FakeAuctionServerTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant compile runtest -DtestClass=FakeAuctionServerTests

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn test -Dtest=FakeAuctionServerTests
 */ 
package auctionsniper.tests.unit;

import auctionsniper.tests.acceptance.FakeAuctionServer;

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
	public void getSniperCommandFromMessage_withCmdJOIN_returnsJOINString() {
		
		FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		final String EXPECTED = "JOIN";
		final String ACTUAL = auction.getSniperCommandFromMessage("SQLVersion: 1.1; Command: JOIN;");
		assertEquals("Command with value ->JOIN<- should return a String \"JOIN\"", EXPECTED, ACTUAL);
	}
	
	@Test
	public void getSniperCommandFromMessage_withCmdBID_returnsBIDString() {
		
		FakeAuctionServer auction = new FakeAuctionServer("item-54321");
		final String EXPECTED = "BID";
		final String ACTUAL = auction.getSniperCommandFromMessage("SQLVersion: 1.1; Command: BID;");
		assertEquals("Command with value ->BID<- should return a String \"BID\"", EXPECTED, ACTUAL);
	}

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
}