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
 set SIH=src\test\scripts\SysinternalsSuite_131101
 set SC=target\classes
 set TC=target\test-classes
 set SD=src\main\java
 set TD=src\test\java
 cd student\code_practice_junit
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\AuctionSniper.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\AuctionSniperTests.java
 ant run-perl-openfirectl
 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.AuctionSniperTests
 ant compile runtest -DtestClass=AuctionSniperTests
 
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
import auctionsniper.tests.AuctionSniperTestUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class AuctionSniperTests {
	public static final Chat UNUSED_CHAT = null;
	
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
	private final SniperListener sniperListener = context.mock(SniperListener.class);
	private final Auction auction = context.mock(Auction.class);
	
	private final AuctionSniper sniper = new AuctionSniper(auction, sniperListener);
	
	@Test public void 
	reportLostWhenAuctionCloses() {
		context.checking(new Expectations() {{
			oneOf(sniperListener).sniperLost();
		}});
		
		sniper.auctionClosed();
	}
	
	@Test public void
	bidsHigherAndReportsBiddingWhenNewPriceArrives() {
		final int price = 1001;
		final int increment = 25;
		context.checking(new Expectations() {{
			oneOf(auction).bid(price + increment);
			atLeast(1).of(sniperListener).sniperBidding();
		}});
		
		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
	}
	
	@Test public void
	reportsWinningWhenNewCurrentPriceComesFromSniper() {
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperWinning();
		}});
		
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
	}
}