package auctionsniper.tests.acceptance;

import auctionsniper.Main;
import auctionsniper.MainWindow;

import com.objogate.wl.gesture.Gestures;
import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JTableDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.matcher.IterableComponentsMatcher;

import static org.hamcrest.Matchers.*;
import static com.objogate.wl.swing.matcher.JLabelTextMatcher.*;

public class AuctionSniperDriver extends JFrameDriver {
	public AuctionSniperDriver() {
		this(1000);
	}
	
	public AuctionSniperDriver(int timeoutMillis) {
		super(new GesturePerformer(), 
		      JFrameDriver.topLevelFrame(
		      	named(MainWindow.MAIN_WINDOW_NAME),
		      	showingOnScreen()),
		      new AWTEventQueueProber(timeoutMillis, 100));
	}
	
	@SuppressWarnings("unchecked")
	public void showSniperStatus(String statusText) {
		///new JLabelDriver(this, named(MainWindow.SNIPER_STATUS_NAME)).hasText(equalTo(statusText));
		new JTableDriver(this).hasCell(withLabelText(equalTo(statusText)));
	}
	
	@SuppressWarnings("unchecked")
	public void showSniperStatus(String itemId, int lastPrice, int lastBid, String statusText) {
		JTableDriver tableDriver = new JTableDriver(this);
		tableDriver.hasRow(IterableComponentsMatcher.matching(
		  withLabelText(itemId), withLabelText(Integer.valueOf(lastPrice).toString()), 
		  withLabelText(Integer.valueOf(lastBid).toString()), withLabelText(statusText)
		));
	}
}