/**
 *
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
 
 set CLASSPATH=lib;lib\Smack.jar;lib\Smackx.jar;lib\Smackx-debug.jar;lib\junit-4.11.jar;lib\hamcrest-all-1.3.jar
 set WL=lib\windowlicker-core-DEV.jar;lib\windowlicker-swing-DEV.jar
 set CLASSPATH=%WL%;%CLASSPATH%
 set JM=lib\jmock-2.6.0.jar;lib\jmock-junit4-2.6.0.jar
 set CLASSPATH=%JM%;%CLASSPATH%
 set L4J2=lib\log4j-api-2.0-beta9.jar;lib\log4j-core-2.0-beta9.jar
 set CLASSPATH=%L4J2%;%CLASSPATH%
 set ACL3=lib\commons-lang3-3.1.jar
 set CLASSPATH=%ACL3%;%CLASSPATH%
 set SIH=src\test\scripts\SysinternalsSuite_131101
 set SC=target\classes
 set TC=target\test-classes
 set SD=src\main\java
 set TD=src\test\java
 cd student\code_practice_junit
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\Main.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\acceptance\AuctionSniperEndToEndTests.java
 ant run-perl-openfirectl
 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.acceptance.AuctionSniperEndToEndTests
 ant runtest -DtestClass=AuctionSniperEndToEndTests
 
 ***** build the FakeAuctionServer source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set FAS_FILES=FakeAuctionServer.java
 H:\student\code_practice_junit>echo %FAS_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\%FAS_FILES%
                                javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\Main.java
                                
 ***** build the Tests AuctionSniperEndToEndTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\acceptance\AuctionSniperEndToEndTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.acceptance.AuctionSniperEndToEndTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant runtest -DtestClass=AuctionSniperEndToEndTests

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn antrun:run test -Dtest=AuctionSniperEndToEndTests
 
   http://stackoverflow.com/questions/6819888/how-to-run-all-tests-in-a-particular-package-with-maven
   mvn test                                                 - all tests
   mvn test -Dtest=auctionsniper.tests.unit.*Tests          - AuctionSniper all unit tests only
   mvn antrun:run test -Dtest=AuctionMessageTranslatorTests - Auction Sniper only the specified unit test
   mvn antrun:run test -Dtest=auctionsniper.tests.*.*Tests  - start Openfire server, AuctionSniper acceptance and unit tests
   mvn antrun:run test -Dtest=AuctionSniperEndToEndTests    - start Openfire server, AuctionSniper acceptance tests only

 */ 
package auctionsniper.tests.acceptance;

// <mlr 131225: ITEM_ID - added per GOOS, p. 155a>
import auctionsniper.tests.AuctionSniperTestUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AuctionSniperEndToEndTests {
	static Logger logger = LogManager.getLogger(AuctionSniperEndToEndTests.class.getName());	
		
	//private  FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private  FakeAuctionServer auction = new FakeAuctionServer(AuctionSniperTestUtilities.ITEM_ID);
	private  ApplicationRunner application = new ApplicationRunner();
	
	@Test
	public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {        
		auction.startSellingItem();                    // step 1
		application.startBiddingIn(auction);           // step 2
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);    // step 3
		auction.announceClosed();                      // step 4
		application.showsSniperHasLostAuction();       // step 5
		sleep(forThisLong);
	}

	@Test
	public void sniperMakesHigherBidButLoses() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);       
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		
		// 1st features after walking skeleton
		/* 1) Tell the auction to send a price to the Sniper
		   2) Check the Sniper has received and responded to the price
		   3) Check the auction has received an incremented bid from the Sniper
		*/
		/* here's my code
		auction.sendPriceToSniper(1000);
		application.receivesPriceAndResponds();
		auction.receivesBidFromSniper(); */ 
		
		// here's the book's code
		auction.reportPrice(900, 48, "other bidder");
		application.hasShownSniperIsBidding(900, 948);
		auction.hasReceivedBid(948, ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.announceClosed();
		application.showsSniperHasLostAuction();
		sleep(forThisLong);
	}

	@Test
	public void sniperWinsAnAuctionByBiddingHigher() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);       
		auction.hasReceivedJoinRequestFromSniper(ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSniperIsBidding(1000, 1098);
		
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
		application.showsSniperIsWinning(1098);		
		
		auction.announceClosed();
		application.showsSniperHasWonAuction(1098);		
		sleep(forThisLong);
	}

	// setup test fixture
	@Before
	public void setUpFixture() {
		logger.info("Setup fixture...");
	}

	// clean up
	@After
	public void stopAuction() {
		logger.info("Stop auction ...");
		auction.stop();
	}
	
	@After
	public void stopApplication() {
		logger.info("Stop Sniper ...");
		application.stop();
	}
	
 	private int forThisLong = 0;
	private void sleep(int sleepDuration) {
		try {
			Thread.sleep(sleepDuration * 1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	

}