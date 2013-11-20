import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

public class Main {
	private MainWindow ui;
	
	private static final int ARG_HOSTNAME = 0;
	private static final int ARG_USERNAME = 1;
	private static final int ARG_PASSWORD = 2;
	private static final int ARG_ITEM_ID  = 3;

	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%S/" + AUCTION_RESOURCE;

	public static final String SNIPER_STATUS_NAME = "status";
	public static final String STATUS_JOINING = "Joining auction";
	public static final String STATUS_LOST = "Lost auction";
	
	public Main() {
		startUserInterface();
	}

	public static void main(String[] args) throws Exception {
		Main main = new Main();
		XMPPConnection connection = connectTo(args[ARG_HOSTNAME],
		                                      args[ARG_USERNAME],
		                                      args[ARG_PASSWORD]);
		Chat chat = connection.getChatManager().createChat(
		  auctionId(args[ARG_ITEM_ID], connection),
		  new MessageListener() {
		  	@Override
		  	public void processMessage(Chat aChat, Message message) {
		  		// nothing yet
		  	}
		  });
		  chat.sendMessage(new Message());
	}
	
	private static XMPPConnection 
	connectTo(String hostname, String username, String password)
		throws Exception 
	{
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
		
		return connection;
	}
	
	private static String auctionId(String itemId, XMPPConnection connection) {
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