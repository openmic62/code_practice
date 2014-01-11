package auctionsniper;

import javax.swing.SwingUtilities;

public class SwingThreadSniperListener implements SniperListener {
	
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
  			snipers.sniperStateChanged(sniperSnapshot);
  			sleep(forThisLong); /*>>>REMOVE<<<*/
  		}
  	});
  }

 	/*>>>REMOVE-BEGIN<<<*/
 	//private double forThisLong = 3.5;
 	private double forThisLong = 0.0;
	private void sleep(double sleepDuration) {
		try {
			Thread.sleep((int)(sleepDuration * 1000));
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}
	/*>>>REMOVE-END<<<*/
}	