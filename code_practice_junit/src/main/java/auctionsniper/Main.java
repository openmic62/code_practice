package auctionsniper;

import auctionsniper.ui.MainWindow;
import auctionsniper.ui.SnipersTableModel;
import auctionsniper.ui.SwingThreadSniperListener;
import auctionsniper.xmpp.AuctionHouse;
import auctionsniper.xmpp.XMPPAuctionHouse;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	static Logger logger = LogManager.getLogger(Main.class.getName());	
	
	@SuppressWarnings("unused") private ArrayList<Auction> notToBeGCd = new ArrayList<Auction>();
	
	private final SnipersTableModel snipers = new SnipersTableModel();
	private MainWindow ui;
	
	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID  = 3;

	public static final String REPORT_PRICE_COMMAND_FORMAT = "SQLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;";
	public static final String CLOSE_COMMAND_FORMAT = "SQLVersion: 1.1; Event: CLOSE;";
	public static final String WINNER_COMMAND_FORMAT = "SQLVersion: 1.1; Event: WINNER; WinningPrice: %d; Bidder: %s";
	
	public Main() {
		startUserInterface();
	}
	
	private void startUserInterface() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					ui = new MainWindow(snipers);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		//     Main - args[]-->[localhost, sniper, sniper, item-54321, item-65432]<--
		logger.debug("args[]-->" + Arrays.toString(args) + "<--");
		
		Main main = new Main();
		
	  XMPPAuctionHouse auctionHouse = 
		  XMPPAuctionHouse.connect(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]);
		main.disconnectWhenUICloses(auctionHouse);
		main.addUserRequestListenerFor(auctionHouse);

    sleep(0); /*>>>REMOVE<<<*/
	}
	
 	/*>>>REMOVE-BEGIN<<<*/
	private static void sleep(final double sleepDuration) {
		try {
  		SwingUtilities.invokeAndWait(new Runnable() {
  			public void run() {
         	try {
         		Thread.sleep((int)(sleepDuration * 1000));
         	} catch(InterruptedException ex) {
         		Thread.currentThread().interrupt();
         	}
         }
  		});
  	}  catch(Exception ex) {
      Thread.currentThread().interrupt();
    }
	}
 	/*>>>REMOVE-END<<<*/
	
	private void addUserRequestListenerFor(final AuctionHouse auctionHouse) {
		//ui.addUserRequestListener(new UserRequestListener() 
		///UserRequestListener userRequestListener = new SniperLauncher(snipers, auctionHouse);
		UserRequestListener sniperLauncher = new SniperLauncher(snipers, auctionHouse);
		/*
		UserRequestListener userRequestListener = new UserRequestListener() 
		{
			public void joinAuction(String itemId) {
				snipers.addSniper(SniperSnapshot.joining(itemId));
        Auction auction = auctionHouse.auctionFor(itemId);
        notToBeGCd.add(auction);
        AuctionEventListener ael = new AuctionSniper(itemId, auction, new SwingThreadSniperListener(snipers));
        auction.addAuctionEventListener(ael);
          
        auction.join();
			} // end method           - joinAuction
		};   // end annonymous class - new UserRequestListener
		*/
	  ///ui.addUserRequestListener(userRequestListener);
	  ui.addUserRequestListener(sniperLauncher);
	}

	private void disconnectWhenUICloses(final XMPPAuctionHouse auctionHouse) {
		ui.addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowClosed(WindowEvent we) {
					auctionHouse.connection.disconnect();
				}
			}
		);
	}
}