/**
 *
 * <u>Growing Object-Oriented Software, Guided by Tests</u><br>
 *    Part III: A Worked Example<br>
 * 		Ch. 13 The Sniper Makes a Bid<br>
 *    Introduction AuctionSniper<br>
 *    p. 123a
 *
 * AuctionSniper is a component at the heart of this application. For now, it
 * will take over stuff we did in Main. Specifically, we'll start out with 2
 * requirements. When we call the method currentPrice() (by whoever implements our
 * interface MessageListener - which is AuctionMessageTranslator), AuctionSniper will
 * 1) send a higher bid to the auction
 * 2) update the status in the user interface (the book does this part first)
 * 
 * 
 * Originally we have,
 *   chat ->| AuctionMessageTranslatr ->| Main  (see p. 118a)
 *          |                           |
 *    MessageListener           AuctionEventListener
 * 
 * After doing the 2nd reqwuirement we have,
 *   chat ->| AuctionMessageTranslatr ->| AuctionSniper ->| Main   (see p. 125b)
 *          |                           |                 |
 *    MessageListener           AuctionEventListener   SniperListener

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
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\AuctionSniper.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\AuctionSniperTests.java
 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.AuctionSniperTests
 ant runtest -DtestClass=AuctionSniperTests
 
 ***** build the AuctionSniper source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set AS_FILES=AuctionSniper.java
 H:\student\code_practice_junit>echo %AS_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\%AS_FILES%
                                javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\AuctionSniper.java
                                
 ***** build the Tests AuctionSniperTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\AuctionSniperTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.AuctionSniperTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant runtest -DtestClass=AuctionSniperTests
 
 >>>>>>>>>>>>>>>>>>>>> JUNITPARAMS DRIVEN TESTS FAIL IN ANT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn antrun:run test -Dtest=AuctionSniperTests
 */ 
package auctionsniper.tests.unit;

import static auctionsniper.AuctionEventListener.PriceSource.FromOtherBidder;
import static auctionsniper.AuctionEventListener.PriceSource.FromSniper;
import auctionsniper.AuctionEventListener.PriceSource;

import auctionsniper.Auction;
import auctionsniper.AuctionSniper;
import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
// <mlr 131225: ITEM_ID - added per GOOS, p. 155a>
import auctionsniper.tests.AuctionSniperTestUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.equalTo;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.States;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class AuctionSniperTests {
	public static final Chat UNUSED_CHAT = null;
	
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
	private final SniperListener sniperListener = context.mock(SniperListener.class);
	private final Auction auction = context.mock(Auction.class);
	private final States sniperState = context.states("sniper");
	
	private final AuctionSniper sniper = new AuctionSniper(AuctionSniperTestUtilities.ITEM_ID1, auction);
	
	// setup test fixture
	@Before
	public void setUpFixture() {
		sniper.addSniperListener(sniperListener);
	}

	@Test public void 
	reportLostIfAuctionClosesImmediately() {
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(SniperState.LOST)));
		}});
		
		sniper.auctionClosed();
	}
	
	@Test public void 
	reportLostIfAuctionClosesWhenBidding() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(SniperState.BIDDING)));
			  then(sniperState.is("bidding"));
			  
			atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(SniperState.LOST)));
			  when(sniperState.is("bidding"));
		}});
		
		sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
		sniper.auctionClosed();
	}
	
	// DSL helper method - p. 162a "Lightweight Extensions to jMock
	private Matcher<SniperSnapshot> aSniperThatIs(SniperState state) {
		return new FeatureMatcher<SniperSnapshot, SniperState> (
               equalTo(state), "sniper that is ", "was")
    {
    	@Override
    	protected SniperState featureValueOf(SniperSnapshot actual) {
    		return actual.getState();
    	}
    };
	}
	
	@Test public void 
	reportWonIfAuctionClosesWhenWinning() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(SniperState.WINNING)));
			  then(sniperState.is("winning"));
			  
			atLeast(1).of(sniperListener).sniperStateChanged(with(aSniperThatIs(SniperState.WON)));
			  when(sniperState.is("winning"));
		}});
		
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		sniper.auctionClosed();
	}
	
	@Test public void
	bidsHigherAndReportsBiddingWhenNewPriceArrives() {
		final int price = 1001;
		final int increment = 25;
		final int bid = price + increment;
		context.checking(new Expectations() {{
			oneOf(auction).bid(bid);
			atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(AuctionSniperTestUtilities.ITEM_ID1, price, bid, SniperState.BIDDING));
		}});
		
		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
	}
	
	@Test public void
	reportsWinningWhenNewCurrentPriceComesFromSniper() {
		final int lastBidFromOther = 123;
		int increment = 45;
		final int myBid = lastBidFromOther + increment;
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(SniperState.BIDDING)));
				then(sniperState.is("bidding"));
			atLeast(1).of(sniperListener).sniperStateChanged(new SniperSnapshot(AuctionSniperTestUtilities.ITEM_ID1, 135, 135, SniperState.WINNING));
			  when(sniperState.is("bidding"));
		}});
		
		sniper.currentPrice(123, 12, PriceSource.FromOtherBidder);
		sniper.currentPrice(135, 45, PriceSource.FromSniper);
	}
	
	// <mlr 140223: begin - add tests for Item class changes>
	@Test
	public void
	doesNotBidAndReortsLosingIfSubsequentPriceIsAboveStopPrice() {
		final int bid = 123 + 45;
		allowingSniperBidding();
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperStateChanged(
			  new SniperSnapshot(AuctionSniperTestUtilities.ITEM_ID1, 2345, bid, SniperState.LOSING));
			                                  }}
		);
		sniper.currentPrice(123, 45, PriceSource.FromOtherBidder);
		sniper.currentPrice(2345, 25, PriceSource.FromOtherBidder);
	}
	private void allowingSniperBidding() {
		context.checking(new Expectations() {{
			allowing(sniperListener).sniperStateChanged(with(aSniperThatIs(SniperState.BIDDING)));
			                                            then(sniperState.is("bidding"));
			                                  }}
	  );
	}
	// <mlr 140223: end - add tests for Item class changes>
	
}