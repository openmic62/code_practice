/**
 *
 * <u>Growing Object-Oriented Software, Guided by Tests</u><br>
 *    Part III: A Worked Example<br>
 * 		Ch. 17 Teasing Apart Main<br>
 *    Writing a New Test (for XMPPAuction - expanded)<br>
 *    p. 195a
 *
 * XMPPAcution now holds the responsibility for all communications with
 * Southabee's auction using an XMPP Chat.

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
      NOTE: 131117 - SIH is short for SYSINTERNALS_HOME 
                     (usually =C:\Users\Mike\Downloads\SysinternalsSuite in system ENV)
 e:
 set CLASSPATH=lib;lib\Smack.jar;lib\Smackx.jar;lib\Smackx-debug.jar;lib\junit-4.11.jar;lib\hamcrest-all-1.3.jar
 set WL=lib\windowlicker-core-DEV.jar;lib\windowlicker-swing-DEV.jar
 set CLASSPATH=%WL%;%CLASSPATH%
 set JM=lib\jmock-2.6.0.jar;lib\jmock-junit4-2.6.0.jar
 set CLASSPATH=%JM%;%CLASSPATH%
 set JML=lib\jmock-legacy-2.6.0.jar;lib\cglib-nodep-2.2.3.jar;lib\objenesis-1.0.jar
 set CLASSPATH=%JML%;%CLASSPATH%
 set L4J2=lib\log4j-api-2.0-rc1.jar;lib\log4j-core-2.0-rc1.jar
 set CLASSPATH=%L4J2%;%CLASSPATH%
 set ACL3=lib\commons-lang3-3.1.jar
 set CLASSPATH=%ACL3%;%CLASSPATH%
 set ACIO=lib\commons-io-2.4.jar
 set CLASSPATH=%ACIO%;%CLASSPATH%
 set SIH=src\test\scripts\SysinternalsSuite_131101
 set SC=target\classes
 set TC=target\test-classes
 set SD=src\main\java
 set TD=src\test\java

 cd student\code_practice_junit
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\xmpp\XMPPAuctionHouse.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\integration\XMPPAuctionHouseTests.java
 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.integration.XMPPAuctionHouseTests
 ant runtest -DtestClass=XMPPAuctionHouseTests
 
 ***** build the XMPPAuctionHouse source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set XA_FILES=XMPPAuctionHouse.java
 H:\student\code_practice_junit>echo %XA_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\%XA_FILES%
                                javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\XMPPAuctionHouse.java
                                
 ***** build the Tests XMPPAuctionHouseTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\integration\XMPPAuctionHouseTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.integration.XMPPAuctionHouseTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant runtest -DtestClass=XMPPAuctionHouseTests

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn antrun:run test -Dtest=XMPPAuctionHouseTests
 */ 
package auctionsniper.tests.integration;

import auctionsniper.Auction;
import auctionsniper.AuctionEventListener;
import auctionsniper.xmpp.XMPPAuction;
import auctionsniper.xmpp.XMPPAuctionHouse;

import auctionsniper.tests.AuctionSniperTestUtilities;
import auctionsniper.tests.acceptance.ApplicationRunner;
import auctionsniper.tests.acceptance.FakeAuctionServer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.hamcrest.Matchers.equalTo;

public class XMPPAuctionHouseTests {
	
	private final String HOSTNAME         = AuctionSniperTestUtilities.LOCALHOST;
	private final String USERNAME         = AuctionSniperTestUtilities.SNIPER_ID;
	private final String PASSWORD         = AuctionSniperTestUtilities.SNIPER_PASSWORD;
	
	private final String AUCTION_RESOURCE = XMPPAuction.AUCTION_RESOURCE;

	private final FakeAuctionServer auctionServer  = new FakeAuctionServer(AuctionSniperTestUtilities.ITEM_ID1);
  
	@Test public void 
	receivesEventsFromAuctionServerAfterJoining() throws Exception{
		CountDownLatch auctionWasClosed = new CountDownLatch(1);
		auctionServer.startSellingItem();
		
	  XMPPAuctionHouse auctionHouse = makeAuctionHouse(HOSTNAME, USERNAME, PASSWORD);
	  XMPPConnection   connection   = auctionHouse.connection;
		//Auction auction = new XMPPAuction(connection, auctionServer.getItemID());
		Auction auction = new XMPPAuction(connection, auctionServer.getItemID(),null);
		auction.addAuctionEventListener(auctionClosedListener(auctionWasClosed));
		
		auction.join();
		auctionServer.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		auctionServer.announceClosed();
		
		assertTrue("should have been closed", auctionWasClosed.await(2, TimeUnit.SECONDS));
	}
	
	private XMPPAuctionHouse makeAuctionHouse(String hostname, String username, String password) {
		XMPPAuctionHouse auctionHouse = null;
		try {
			auctionHouse = XMPPAuctionHouse.connect(hostname, username, password);
		} catch (XMPPException xe) {
			xe.printStackTrace();
		}
		return auctionHouse;
	}
	
	private AuctionEventListener
	auctionClosedListener(final CountDownLatch auctionWasClosed) {
		return new AuctionEventListener() {
			public void auctionClosed() { auctionWasClosed.countDown(); }
			public void currentPrice(int price, int increment, PriceSource priceSource) {
				// not implemented because not needed for testing
			}
	    // <mlr 140310: begin - add failure detection code>
	    public void auctionFailed() {}
	    // <mlr 140310: end - add failure detection code>
		};
	}
}