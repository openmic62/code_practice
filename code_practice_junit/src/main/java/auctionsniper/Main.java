package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
/*

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
*/

public class Main {
	static Logger logger = LogManager.getLogger(Main.class.getName());	
	
	//@SuppressWarnings("unused") private ArrayList<Chat> notToBeGCd = new ArrayList<Chat>();
	@SuppressWarnings("unused") private ArrayList<Auction> notToBeGCd = new ArrayList<Auction>();
	
	private final SnipersTableModel snipers = new SnipersTableModel();
	private MainWindow ui;
	
	private XMPPConnection connection;
	
	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID  = 3;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%S/" + AUCTION_RESOURCE;

	public static final String JOIN_COMMAND_FORMAT = "SQLVersion: 1.1; Command: JOIN;";
	public static final String REPORT_PRICE_COMMAND_FORMAT = "SQLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;";
	public static final String BID_COMMAND_FORMAT = "SQLVersion: 1.1; Command: BID; Price: %d;";
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
		
		Connection.DEBUG_ENABLED = false;
		XMPPConnection connection = 
		  main.connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]);
		main.disconnectWhenUICloses(connection);
		main.addUserRequestListenerFor(connection);

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
	
	//private void addUserRequestListener(final XMPPConnection connection) {
	private void addUserRequestListenerFor(final XMPPConnection connection) {
		ui.addUserRequestListener(new UserRequestListener() 
		{
			public void joinAuction(String itemId) {
				snipers.addSniper(SniperSnapshot.joining(itemId));
        /*
        final Chat chat = 
        	connection.getChatManager().createChat(
          auctionId(itemId, connection),
          null
          );
        */
        /////Auction auction = new XMPPAuction(connection, itemId, chat);
        Auction auction = new XMPPAuction(connection, itemId);
        //////final Chat chat = auction.getChat();
        //notToBeGCd.add(chat);
        notToBeGCd.add(auction);
        //Auction auction = new XMPPAuction(chat);
	      /*
        Announcer<AuctionEventListener> auctionEvents = 
	              Announcer.to(AuctionEventListener.class);
        chat.addMessageListener(
        	new AuctionMessageTranslator(
        	      connection.getUser(),
        	      auctionEvents.announce()
        	      //new AuctionSniper(itemId, auction, new SwingThreadSniperListener(snipers))
        	                            )   // end constructor - new AuctionMessageTranslator
                               );         // end statement   - chat.addMessageListener
                               
        //Auction auction = new XMPPAuction(chat);
        ///Auction auction = new XMPPAuction(connection, chat);
        ////Auction auction = new XMPPAuction(connection, itemId, chat);
        /*
        auctionEvents.addListener(
          new AuctionSniper(itemId, auction, new SwingThreadSniperListener(snipers)));
        */
        AuctionEventListener ael = new AuctionSniper(itemId, auction, new SwingThreadSniperListener(snipers));
        //auctionEvents.addListener(ael);
        auction.addAuctionEventListener(ael);
          
        auction.join();
			} // end method           - joinAuction
		}   // end annonymous class - new UserRequestListener
	  );  // end statement        - ui.addUserRequestListener
	}     // end method           - addUserRequestListener
	
	private void disconnectWhenUICloses(final XMPPConnection connection) {
		ui.addWindowListener(new WindowAdapter()
			{
				@Override
				public void windowClosed(WindowEvent we) {
					connection.disconnect();
				}
			}
		);
	}
	
	private XMPPConnection 
	connection(String hostname, String username, String password)
		throws XMPPException 
	{
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
		
		return connection;
	}
	
	/*
	private String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
		                     connection.getServiceName());
	}
	*/
 }