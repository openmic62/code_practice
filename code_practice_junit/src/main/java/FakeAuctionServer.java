import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

public class FakeAuctionServer {
	
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String XMPP_HOSTNAME = "localhost";
	private static final String AUCTION_PASSWORD = "auction";
	
	private final String itemID;
	private XMPPConnection connection;
	private Chat currentChat;
	
	FakeAuctionServer(String item) {
		this.itemID = itemID;
		this.connection = new XMPPConnection(XMPP_HOSTNAME);
	}
	
	public void startSellingItem() {
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemID),
		                 AUCTION_PASSWORD, AUCTION_RESOURCE);
		connection.getChatManager().addChatListener(
			new ChatManagerListener() {
				public void chatCreated(Chat chat, boolean createdLocally) {
					currentChat = chat;
				}
			});
	}
	
	public String getItemID() {
		return this.itemID;
	}
	
	public void hasReceivedJoinRequestFromSniper() {}
	
	public void announceClosed() {}
	
	public void stop() {}
}