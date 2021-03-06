/**
 *
 * <u>Growing Object-Oriented Software, Guided by Tests</u><br>
 *    Part III: A Worked Example<br>
 * 		Ch. 17 Teasing Apart Main<br>
 *    Extracting the SnipersTableModel - SniperLauncher<br>
 *    p. 198b
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
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\SniperLauncher.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\SniperLauncherTests.java
 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.SniperLauncherTests
 ant runtest -DtestClass=SniperLauncherTests
 
 ***** build the SniperLauncher source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set SL_FILES=SniperLauncher.java
 H:\student\code_practice_junit>echo %SL_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\%SL_FILES%
                                javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\SniperLauncher.java
                                
 ***** build the Tests SniperLauncherTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\SniperLauncherTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.SniperLauncherTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant runtest -DtestClass=SniperLauncherTests
 
 >>>>>>>>>>>>>>>>>>>>> JUNITPARAMS DRIVEN TESTS FAIL IN ANT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn antrun:run test -Dtest=SniperLauncherTests
 */ 
package auctionsniper.tests.unit;

import auctionsniper.Auction;
import auctionsniper.AuctionSniper;
import auctionsniper.Item;
import auctionsniper.xmpp.AuctionHouse;
import auctionsniper.ui.SniperCollector;
import auctionsniper.SniperLauncher;
import auctionsniper.SniperSnapshot;

// <mlr 131225: ITEM_ID - added per GOOS, p. 155a>
import auctionsniper.tests.AuctionSniperTestUtilities;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.jmock.States;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class SniperLauncherTests {
	
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
	private final Auction auction = context.mock(Auction.class);
	private final AuctionHouse auctionHouse = context.mock(AuctionHouse.class);
	private final SniperCollector collector = context.mock(SniperCollector.class);
	
	private final States auctionState = context.states("auction states")
	                                           .startsAs("not joined");
	
	private final SniperLauncher launcher = new SniperLauncher(collector, auctionHouse);
	
	@Test public void 
	addNewSniperToCollectorThenJoinsAuction() {
		final String         itemId    = "item 123";
		final int            stopPrice = 6969;
		final Item           testItem  = new Item(itemId, stopPrice);
		context.checking(new Expectations() {{
			allowing(auctionHouse).auctionFor(itemId); will(returnValue(auction));
			
			oneOf(auction).addAuctionEventListener(with(sniperForItem(testItem)));
			                                       when(auctionState.is("not joined"));
			oneOf(collector).addSniper(with(sniperForItem(testItem)));
			                           when(auctionState.is("not joined"));
			                           
			oneOf(auction).join(); then(auctionState.is("joined"));
		}});
		
		launcher.joinAuction(testItem);
	}	
  private Matcher<AuctionSniper> sniperForItem(Item testItem) {
  	return samePropertyValuesAs(new AuctionSniper(testItem, null));
  }

}