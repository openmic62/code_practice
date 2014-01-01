package auctionsniper;

import javax.swing.SwingUtilities;

public class SwingThreadSniperListener implements SniperListener {

	/*
	private MainWindow ui;
	
	SniperStateDisplayer(MainWindow mainWindow) {
		this.ui = mainWindow;
	}
	*/
	
	private SniperListener snipers;
	
	SwingThreadSniperListener(SniperListener snipers) {
		this.snipers = snipers;
	}
	
	// Implement the SniperListener interface
	@Override
	public void sniperStateChanged(final SniperSnapshot sniperSnapshot) {
  	SwingUtilities.invokeLater(new Runnable() {
  		@Override
  		public void run() {
  			//ui.sniperStateChanged(sniperSnapshot);
  			snipers.sniperStateChanged(sniperSnapshot);
  			sleep(forThisLong); /*>>>REMOVE<<<*/
  		}
  	});
  }

 	/*>>>REMOVE-BEGIN<<<*/
 	private int forThisLong = 0;
	private void sleep(int sleepDuration) {
		try {
			Thread.sleep(sleepDuration * 1000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	/*>>>REMOVE-END<<<*/
}	