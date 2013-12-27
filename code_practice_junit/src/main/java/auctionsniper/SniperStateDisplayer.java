package auctionsniper;

import javax.swing.SwingUtilities;

public class SniperStateDisplayer implements SniperListener {

	private MainWindow ui;
	
	SniperStateDisplayer(MainWindow mainWindow) {
		this.ui = mainWindow;
	}
	
	// Implement the SniperListener interface
	@Override
	public void sniperBidding(final SniperState sniperState) {
  	//showStatus(Main.STATUS_BIDDING);
  	SwingUtilities.invokeLater(new Runnable() {
  		@Override
  		public void run() {
  			ui.sniperStatusChanged(sniperState, Main.STATUS_BIDDING);
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
  		}
  	});
 	} 		
}	