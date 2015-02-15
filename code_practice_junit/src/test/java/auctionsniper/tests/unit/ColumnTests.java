/**
 *
 * <u>Growing Object-Oriented Software, Guided by Tests</u><br>
 *    Part III: A Worked Example<br>
 * 		Ch. 15 Towards a Real User Interface<br>
 *    Object-Oriented Column<br>
 *    p. 166b
 *
 * SnipersTableModel extends the Swing class AbstractTableModel. SnipersTableModel
 * manages the data model that backs our JTable view of the various Sniper statuses.
 * The book created an Enum in Column.java to support the column management of our
 * TableModel. At first, it was a basic as basic can be enum that required "switch"
 * implemented logic in SnipersTableModel. The book see the "swictch" construct as
 * non-object-oriented and noisy. So, they clean up the implemenation by moving the
 * logic into overriding methods inside constant bodies added to the enum. Then,
 * they remove the switch from SnipersTableModel.
 *
 * The book OMITS the unit tests for the more capable Colum enum. Instead, they just
 * mention they wrote the tests in a terse paragraph at the bottom of p. 167b. This
 * irritated me a bit as it was the 2nd time in 2 pages (the 1st was on p. 165b) that
 * they just blew off showing unit test code. I thought, "For a book who's title
 * contains '... Guidd by Tests', they sure take a casual approach. Whatever. I'm
 * writing the tests here for my own benefit.
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
 
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\MainWindow.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\ColumnTests.java

 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.ColumnTests
 ant runtest -DtestClass=ColumnTests
 
 ***** build the MainWindow source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set MW_FILES=MainWindow.java
 H:\student\code_practice_junit>echo %MW_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\%MW_FILES%
                                javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\MainWindow.java
                                
 ***** build the Tests ColumnTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\ColumnTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.ColumnTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant runtest -DtestClass=ColumnTests
 
 >>>>>>>>>>>>>>>>>>>>> JUNITPARAMS DRIVEN TESTS FAIL IN ANT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn antrun:run test -Dtest=ColumnTests
 */ 
package auctionsniper.tests.unit;

import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.ui.Column;
import auctionsniper.ui.SnipersTableModel;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class ColumnTests {
	
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
	//private final SniperSnapshot snapshot = context.mock(SniperSnapshot.class);
	private SniperSnapshot snapshot;
	private final SniperState snapshotState = SniperState.JOINING;

	//----------------------------------------------------------------------------		
	@Before public void
	createAFreshSnapshot() {
		snapshot = new SniperSnapshot("test item id", 123, 456, snapshotState);
	}
	
	@After public void
	removeExistingSnapshot() {
		snapshot = null;
	}
	
	//----------------------------------------------------------------------------		
	@Test public void 
	valueInMethod_forItemIdConstant_returnsIdString() {
		assertEquals("test item id", Column.ITEM_IDENTIFIER.valueIn(snapshot));
	}
	
	@Test public void 
	valueInMethod_forItemPriceConstant_returnsPriceInt() {
		assertEquals(123, Column.LAST_PRICE.valueIn(snapshot));
	}
	
	@Test public void 
	valueInMethod_forItemBidConstant_returnsBidInt() {
		assertEquals(456, Column.LAST_BID.valueIn(snapshot));
	}
	
	@Test public void 
	valueInMethod_forItemStateConstant_returnsStateObject() {
		//assertEquals(SniperState.JOINING, Column.SNIPER_STATE.valueIn(snapshot));
		assertEquals(SnipersTableModel.textFor(snapshotState), Column.SNIPER_STATE.valueIn(snapshot));
	}
	
	//----------------------------------------------------------------------------		
	@Test public void 
	columnOneNamesItemId() {
		assertEquals("ITEM_IDENTIFIER", Column.values()[0].name());
	}
	
	@Test public void 
	columnTwoNamedLastPrice() {
		assertEquals("LAST_PRICE", Column.values()[1].name());
	}
	
	@Test public void 
	columnThreeNamedLastBid() {
		assertEquals("LAST_BID", Column.values()[2].name());
	}
	
	@Test public void 
	columnFourNamesState() {
		assertEquals("SNIPER_STATE", Column.values()[3].name());
	}
	
	//----------------------------------------------------------------------------		
	@Test public void 
	columnAt_whenOffset0_returnsID() {
		assertEquals(Column.ITEM_IDENTIFIER, Column.at(0));
	}
	
	@Test public void 
	columnAt_whenOffset1_returnsPrice() {
		assertEquals(Column.LAST_PRICE, Column.at(1));
	}
	
	@Test public void 
	columnAt_whenOffset2_returnsBid() {
		assertEquals(Column.LAST_BID, Column.at(2));
	}
	
	@Test public void 
	columnAt_whenOffset3_returnsBid() {
		assertEquals(Column.SNIPER_STATE, Column.at(3));
	}
}