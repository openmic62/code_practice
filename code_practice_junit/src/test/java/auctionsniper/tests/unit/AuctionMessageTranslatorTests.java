/**
 *
 * <u>Growing Object-Oriented Software, Guided by Tests</u><br>
 *    Part III: A Worked Example<br>
 * 		Ch. 12 Getting Ready to Bid<br>
 *    The First Unit Test<br>
 *    p. 114a
 *
 * AuctionMessageTranslator holds the responsibility for parsing messages from the
 * Southabee's auction into a Sniper event on which this application can act.

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
 g:
 cd student\code_practice_junit
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\AuctionMessageTranslator.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\AuctionMessageTranslatorTests.java
 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.AuctionMessageTranslatorTests
 ant compile runtest -DtestClass=AuctionMessageTranslatorTests
 
 ***** build the AuctionMessageTranslator source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set AMT_FILES=AuctionMessageTranslator.java
 H:\student\code_practice_junit>echo %AMT_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\%AMT_FILES%
                                javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\AuctionMessageTranslator.java
                                
 ***** build the Tests AuctionMessageTranslatorTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\AuctionMessageTranslatorTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.AuctionMessageTranslatorTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant runtest -DtestClass=AuctionMessageTranslatorTests

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn antrun:run test -Dtest=AuctionMessageTranslatorTests
 */ 
package auctionsniper.tests.unit;

import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionMessageTranslator;
import auctionsniper.tests.AuctionSniperTestUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;

import org.junit.Rule;
import org.junit.Test;

public class AuctionMessageTranslatorTests {
	public static final Chat UNUSED_CHAT = null;
	
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
	private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
	private final String SNIPER_XMPP_ID = String.format("billyBob@%s/Auction", AuctionSniperTestUtilities.myGetHostName());
	
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator(SNIPER_XMPP_ID, listener);
	
	@Test public void 
	notifiesAuctionClosedWhenCloseMessageReceived() {
		context.checking(new Expectations() {{
			oneOf(listener).auctionClosed();
		}});
		
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: CLOSE");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	@Test public void
	notifiesBidDetailsWhenPriceMessageReceivedFromOtherBidder() {
		context.checking(new Expectations() {{
			oneOf(listener).currentPrice(192, 7, AuctionEventListener.PriceSource.FromOtherBidder);
		}});
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	@Test public void
	notifiesBidDetailsWhenPriceMessageReceivedFromSniper() {
		context.checking(new Expectations() {{
			oneOf(listener).currentPrice(234, 5, AuctionEventListener.PriceSource.FromSniper);
		}});
		Message message = new Message();
		message.setBody("SQLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + SNIPER_XMPP_ID + ";");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
}