package auctionsniper;

import javax.swing.SwingUtilities;

public class SniperStateDisplayer implements SniperListener {

	private MainWindow ui;
	
	SniperStateDisplayer(MainWindow mainWindow) {
		this.ui = mainWindow;
	}
	
	// Implement the SniperListener interface
	@Override
	public void sniperBidding(final SniperSnapshot sniperSnapshot) {
  	//showStatus(Main.STATUS_BIDDING);
  	SwingUtilities.invokeLater(new Runnable() {
  		@Override
  		public void run() {
  			ui.sniperStatusChanged(sniperSnapshot, Main.STATUS_BIDDING);
  			sleep(forThisLong); /*>>>REMOVE<<<*/
  		}
  	});
  }
 	
	@Override
	public void sniperLost() {
  	showStatus(Main.STATUS_LOST);
 	}
 	
	@Override
	public void sniperWinning() {
  	showStatus(Main.STATUS_WINNING);
 	}
 	
	@Override
	public void sniperWon() {
  	showStatus(Main.STATUS_WON);
 	}
 	
 	private void showStatus(final String status) {
  	SwingUtilities.invokeLater(new Runnable() {
  		@Override
  		public void run() {
  			ui.showStatus(status);
  			sleep(forThisLong); /*>>>REMOVE<<<*/
  		}
  	});
 	}
 	
 	/*>>>REMOVE-BEGIN<<<*/
 	private int forThisLong = 2;
	private void sleep(int sleepDuration) {
		try {
			Thread.sleep(sleepDuration * 1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	/*>>>REMOVE-END<<<*/
}	