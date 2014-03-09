package auctionsniper.tests.acceptance;

import auctionsniper.Main;
import auctionsniper.ui.MainWindow;

import com.objogate.wl.gesture.Gestures;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.driver.JTableHeaderDriver;
import com.objogate.wl.swing.driver.JTextFieldDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.matcher.IterableComponentsMatcher;

import javax.swing.JButton;
import javax.swing.table.JTableHeader;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.Matchers.equalTo;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.withLabelText;

public class AuctionSniperDriver extends JFrameDriver {
	static Logger logger = LogManager.getLogger(AuctionSniperDriver.class.getName());	
	
	public AuctionSniperDriver() {
		this(1500);
	}
	
	@SuppressWarnings("unchecked")
	public AuctionSniperDriver(int timeoutMillis) {
		super(new GesturePerformer(), 
		      JFrameDriver.topLevelFrame(
		      	named(MainWindow.MAIN_WINDOW_NAME),
		      	showingOnScreen()),
		      new AWTEventQueueProber(timeoutMillis, 100));
	}
	
	// <mlr 140112: begin - add items thru UI>
	///public void startBiddingFor(String itemId) {
	public void startBiddingFor(String itemId, int stopPrice) {
		itemIdField().replaceAllText(itemId);
		stopPriceField().replaceAllText(String.valueOf(stopPrice));
		bidButton().click();
	}	
	
	@SuppressWarnings("unchecked")
	private JTextFieldDriver itemIdField() {
		JTextFieldDriver newItemId = 
		  new JTextFieldDriver(this, JTextField.class, named(MainWindow.NEW_ITEM_ID_NAME));
		newItemId.focusWithMouse();
		return newItemId;
	}
	
	@SuppressWarnings("unchecked")
	private JTextFieldDriver stopPriceField() {
		JTextFieldDriver stopPrice = 
		  new JTextFieldDriver(this, JTextField.class, named(MainWindow.NEW_ITEM_STOP_PRICE_NAME));
		stopPrice.focusWithMouse();
		return stopPrice;
	}
	
	@SuppressWarnings("unchecked")
	private JButtonDriver bidButton() {
		return new JButtonDriver(this, JButton.class, named(MainWindow.JOIN_BUTTON_NAME));
	}
	// <mlr 140112: end - add items thru UI>
	
	@SuppressWarnings("unchecked")
	public void showSniperStatus(String statusText) {
		new JTableDriver(this).hasCell(withLabelText(equalTo(statusText)));
	}
	
	@SuppressWarnings("unchecked")
	public void showSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
		logger.info("in call as: showSniperStatus({}, {}, {}, {})", itemId, lastPrice, lastBid, statusText);
		
		JTableDriver tableDriver = new JTableDriver(this);
		tableDriver.hasRow(IterableComponentsMatcher.matching(
		  withLabelText(itemId), withLabelText(Integer.valueOf(lastPrice).toString()), 
		  withLabelText(Integer.valueOf(lastBid).toString()), withLabelText(statusText)
		));
	}
	
	@SuppressWarnings("unchecked")
	public void hasColumnTitles() {
		JTableHeaderDriver header = new JTableHeaderDriver(this, JTableHeader.class);
		header.hasHeaders(IterableComponentsMatcher.matching(
		  withLabelText("Item"), withLabelText("Last Price"), 
		  withLabelText("Last Bid"), withLabelText("State")
		));
	}
}