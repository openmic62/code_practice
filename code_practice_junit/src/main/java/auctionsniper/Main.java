package auctionsniper;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Main {
	@SuppressWarnings("unused") private Chat notToBeGCd;
	
	private MainWindow ui;
	
	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID  = 3;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%S/" + AUCTION_RESOURCE;

	public static final String SNIPER_STATUS_NAME = "status";
	public static final String STATUS_BIDDING = "Bidding in auction";
	public static final String STATUS_JOINING = "Joining auction";
	public static final String STATUS_WINNING = "Winning auction";
	public static final String STATUS_LOST = "Lost auction";
	
	public static final String JOIN_COMMAND_FORMAT = "SQLVersion: 1.1; Command: JOIN;";
	public static final String REPORT_PRICE_COMMAND_FORMAT = "SQLVersion: 1.1; Event: PRICE; CurrentPrice: %d; Increment: %d; Bidder: %s;";
	public static final String BID_COMMAND_FORMAT = "SQLVersion: 1.1; Command: BID; Price: %d;";
	public static final String CLOSE_COMMAND_FORMAT = "SQLVersion: 1.1; Event: CLOSE;";
	
	public Main() {
		startUserInterface();
	}

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		main.joinAuction(
		  main.connection(args[ARG_HOSTNAME], args[ARG_USERNAME], args[ARG_PASSWORD]),
		  args[ARG_ITEM_ID]);
	}
	
	private void joinAuction(XMPPConnection connection, String itemId) 
	  throws XMPPException 
	{
		disconnectWhenUICloses(connection);
		
		final Chat chat = 
			connection.getChatManager().createChat(
		  auctionId(itemId, connection),
		  ///new AuctionMessageTranslator(new AuctionSniper(nullAuction, this))
		  null
		  );
		this.notToBeGCd = chat;  
		
		Auction auction = new XMPPAuction(chat);
		chat.addMessageListener(
			new AuctionMessageTranslator(new AuctionSniper(auction, new SniperStateDisplayer(ui))));
		auction.join();
	}
	
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
		throws Exception 
	{
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
		
		return connection;
	}
	
	private String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
		                     connection.getServiceName());
	}
	
	private void startUserInterface() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					ui = new MainWindow();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 }