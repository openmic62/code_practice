/**
 *
 * <u>Growing Object-Oriented Software, Guided by Tests</u><br>
 *    Part III: A Worked Example<br>
 * 		Ch. 15 Towards a Real User Interface<br>
 *    Showing a Bidding Sniper<br>
 *    p. 156b
 *
 * SnipersTableModel extends the Swing class AbstractTableModel. SnipersTableModel
 * manages the data model that backs our JTable view of the various Sniper statuses.
 * As a starting point, we created a rudimentary inners class within MainWindow.java. 
 * From that point, we need to expand the app code capability. So, it's come time to
 * write some unit tests. This source file contains those unit tests.

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
 
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\\ui\SnipersTableModel.java
 javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\SnipersTableModelTests.java
 java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.SnipersTableModelTests

 ant runtest -DtestClass=SnipersTableModelTests
 
 ***** build the MainWindow source file
 H:\>cd student\code_practice_junit
 H:\student\code_practice_junit>l
 H:\student\code_practice_junit>echo %CLASSPATH%
 H:\student\code_practice_junit>set MW_FILES=MainWindow.java
 H:\student\code_practice_junit>echo %MW_FILES%
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\%MW_FILES%
                                javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\auctionsniper\MainWindow.java
                                
 ***** build the Tests SnipersTableModelTests
 H:\student\code_practice_junit>javac -cp %CLASSPATH%;%SC%;%TC% -d %TC% %TD%\auctionsniper\tests\\unit\SnipersTableModelTests.java
 
 ***** run the Tests (command line Java)
 H:\student\code_practice_junit>java  -cp %CLASSPATH%;%SC%;%TC% org.junit.runner.JUnitCore auctionsniper.tests.unit.SnipersTableModelTests
 
 ***** run the Tests (command line Ant)
 H:\student\code_practice_junit>ant clean_all
 H:\student\code_practice_junit>ant runtest -DtestClass=SnipersTableModelTests
 
 >>>>>>>>>>>>>>>>>>>>> JUNITPARAMS DRIVEN TESTS FAIL IN ANT <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

 ***** run the Tests (command line Maven)
 H:\student\code_practice_junit>mvn antrun:run test -Dtest=SnipersTableModelTests
 */ 
package auctionsniper.tests.unit;

import auctionsniper.AuctionSniper;
import auctionsniper.Defect;
import auctionsniper.Item;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;
import auctionsniper.ui.Column;
import auctionsniper.ui.SnipersTableModel;
import auctionsniper.tests.AuctionSniperTestUtilities;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.hamcrest.Matcher;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.SamePropertyValuesAs.samePropertyValuesAs;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;

public class SnipersTableModelTests {
	static Logger logger = LogManager.getLogger(SnipersTableModelTests.class.getName());	
		
	@Rule public final JUnitRuleMockery context = new JUnitRuleMockery();
	private final TableModelListener listener = context.mock(TableModelListener.class);
	
	private final SnipersTableModel model = new SnipersTableModel();
	
	@Before public void
	attachModelListener() {
		model.addTableModelListener(listener);
	}
		
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Test public void 
	throwsDefectIfNoExistingSniperForAnUpdate () {
		// JUnit4 set expectation for the desired exception
		thrown.expect(Defect.class);
		
		// JMock2 expectations - to appease the god of mocking
		context.checking(new Expectations() {{
			allowing(listener).tableChanged(with(aRowInsertedEvent(0)));
		}});
		
		// simulate a programming defect
		SniperSnapshot defective = SniperSnapshot.joining("item-DEFECT");
		defective = defective.bidding(69, 69);
		
		// act as normal
		/// <mlr 140217: triple slashed code - changed due to GOOS, p. 198b>
		///SniperSnapshot joining = SniperSnapshot.joining("item-98765");
		///model.addSniper(joining);
		////model.addSniper(new AuctionSniper("item-98765", null));
		/////model.sniperAdded(new AuctionSniper("item-98765", null));
		model.sniperAdded(new AuctionSniper(new Item("item-98765", 6969), null));
		
		// this should produce a Defect exception
		model.sniperStateChanged(defective);
	}

	@Test public void 
	rowCountIncreasesByOneWhenAddingASniper() {
		int expectedRowCountDifference = 1;
		int rowCountBeforeAdd = model.getRowCount();
		context.checking(new Expectations() {{
			allowing(listener).tableChanged(with(aRowInsertedEvent(0)));
		}});
		
		///SniperSnapshot joining = SniperSnapshot.joining("item id");
		///model.addSniper(joining);
		////model.addSniper(new AuctionSniper("item id", null));
		/////model.sniperAdded(new AuctionSniper("item id", null));
		model.sniperAdded(new AuctionSniper(new Item("item id", 6969), null));
		
		int rowCountAfterAdd = model.getRowCount();
		int actualRowCountDifference = rowCountAfterAdd - rowCountBeforeAdd;
		
		assertEquals(expectedRowCountDifference, actualRowCountDifference);
	}
	
	@Test public void
	setsCorrectCellValuesWhenAddingASniper() {
		context.checking(new Expectations() {{
			oneOf(listener).tableChanged(with(aRowInsertedEvent(0)));
		}});
		
		///SniperSnapshot joining = SniperSnapshot.joining("item id INSERT");
		///model.addSniper(joining);
		////model.addSniper(new AuctionSniper("item id INSERT", null));
		/////model.sniperAdded(new AuctionSniper("item id INSERT", null));
		model.sniperAdded(new AuctionSniper(new Item("item id INSERT", 6969), null));
		
		assertColumnEquals(Column.ITEM_IDENTIFIER, "item id INSERT");
		assertColumnEquals(Column.LAST_PRICE, 0);
		assertColumnEquals(Column.LAST_BID, 0);
		assertColumnEquals(Column.SNIPER_STATE, SnipersTableModel.textFor(SniperState.JOINING));
	}
		
	@Test public void 
	notifiesListenerWhenAddingASniper() {
		context.checking(new Expectations() {{
			oneOf(listener).tableChanged(with(aRowInsertedEvent(0)));
		}});
		
		///SniperSnapshot joining = SniperSnapshot.joining("anudda id");
		///model.addSniper(joining);
		////model.addSniper(new AuctionSniper("anudda id", null));
		model.sniperAdded(new AuctionSniper(new Item("anudda id", 6969), null));
	}
  private Matcher<TableModelEvent> aRowInsertedEvent(int rowIndex) {
  	int lastRow = rowIndex;
  	return samePropertyValuesAs(new TableModelEvent(model, lastRow, lastRow, 
  	                            TableModelEvent.ALL_COLUMNS, 
  	                            TableModelEvent.INSERT));
  }
  
  @Test public void
  holdsSnipersInAdditionOrder() {
		context.checking(new Expectations() {{
			ignoring(listener);
		}});
  	
		///SniperSnapshot joining1 = SniperSnapshot.joining("item-1");
		///SniperSnapshot joining2 = SniperSnapshot.joining("item-2");
		///model.addSniper(joining1);
		///model.addSniper(joining2);
		////model.addSniper(new AuctionSniper("item-1", null));
		////model.addSniper(new AuctionSniper("item-2", null));
		/////model.sniperAdded(new AuctionSniper("item-1", null));
		/////model.sniperAdded(new AuctionSniper("item-2", null));
		model.sniperAdded(new AuctionSniper(new Item("item-1", 6969), null));
		model.sniperAdded(new AuctionSniper(new Item("item-2", 6969), null));
		
		assertOrderedRowHasAColumnOneValueOf(0, "item-1");		
		assertOrderedRowHasAColumnOneValueOf(1, "item-2");		
  }
  private void assertOrderedRowHasAColumnOneValueOf(int row, String expected) {
  	int col = Column.ITEM_IDENTIFIER.ordinal();
  	String actual = (String) model.getValueAt(row, col);
  	assertEquals(expected, actual);
  }
	
	@Test public void
	updatesCorrectRowForSniper() {
		context.checking(new Expectations() {{
			allowing(listener).tableChanged(with(aRowInsertedEvent(0)));
			allowing(listener).tableChanged(with(aRowInsertedEvent(1)));
			allowing(listener).tableChanged(with(aRowInsertedEvent(2)));
			oneOf(listener).tableChanged(with(aChangeInRow(1)));
		}});
		
		///SniperSnapshot joining1 = SniperSnapshot.joining("item-1a");
		SniperSnapshot joining2 = SniperSnapshot.joining("item-2a");
		///SniperSnapshot joining3 = SniperSnapshot.joining("item-3a");
		///model.addSniper(joining1);
		///model.addSniper(joining2);		
		///model.addSniper(joining3);
		////model.addSniper(new AuctionSniper("item-1a", null));
		////model.addSniper(new AuctionSniper("item-2a", null));
		////model.addSniper(new AuctionSniper("item-3a", null));
		/////model.sniperAdded(new AuctionSniper("item-1a", null));
		/////model.sniperAdded(new AuctionSniper("item-2a", null));
		/////model.sniperAdded(new AuctionSniper("item-3a", null));
		model.sniperAdded(new AuctionSniper(new Item("item-1a", 6969), null));
		model.sniperAdded(new AuctionSniper(new Item("item-2a", 6969), null));
		model.sniperAdded(new AuctionSniper(new Item("item-3a", 6969), null));
		
		SniperSnapshot bidding2 = joining2.bidding(345, 678);
		model.sniperStateChanged(bidding2);
	}
	
	@Test public void 
	setsUpColumnHeadings() {
		for ( Column column : Column.values() ) {
		  assertEquals(column.name, model.getColumnName(column.ordinal()));
		}
	}
	
	@Test public void 
	hasEnoughColumns() {
		assertThat(model.getColumnCount(), equalTo(Column.values().length));
	}
	
	@Test public void 
	setsSniperValuesInColumns() {
		context.checking(new Expectations() {{
			allowing(listener).tableChanged(with(aRowInsertedEvent(0)));
			oneOf(listener).tableChanged(with(aChangeInRow(0)));
		}});
		
		SniperSnapshot joining = SniperSnapshot.joining("item id xxx");
		SniperSnapshot bidding = joining.bidding(555, 666);
		///model.addSniper(joining);
		////model.addSniper(new AuctionSniper("item id xxx", null));
		/////model.sniperAdded(new AuctionSniper("item id xxx", null));
		model.sniperAdded(new AuctionSniper(new Item("item id xxx", 6969), null));
		model.sniperStateChanged(bidding);
		
		assertColumnEquals(Column.ITEM_IDENTIFIER, "item id xxx");
		assertColumnEquals(Column.LAST_PRICE, 555);
		assertColumnEquals(Column.LAST_BID, 666);
		assertColumnEquals(Column.SNIPER_STATE, SnipersTableModel.textFor(SniperState.BIDDING));
	}
	
  private void assertColumnEquals(Column colEnum, Object expected) {
  	final int row = 0;
  	final int col = colEnum.ordinal();
  	assertEquals(expected, model.getValueAt(row, col));
  }
  private Matcher<TableModelEvent> aChangeInRow(int rowIndex) {
  	return samePropertyValuesAs(new TableModelEvent(model, rowIndex));
  }
  private Matcher<TableModelEvent> aRowChangedEvent() {
  	return samePropertyValuesAs(new TableModelEvent(model, 0));
  }
}