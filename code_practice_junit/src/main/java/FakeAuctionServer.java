import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Message;

public class FakeAuctionServer implements ChatManagerListener, MessageListener {
	
	private final String item;
	
	private ChatManager chatManager;
	private ConnectionConfiguration config;
	private XMPPConnection connection;
	
	FakeAuctionServer(String item) {
		this.item = item;
		this.config = new ConnectionConfiguration("roco-3", 9090, "Southabee's");
		this.connection = new XMPPConnection(config);
	}
	
	public void chatCreated(Chat chat, boolean createdLocally) {}
	
	public void processMessage(Chat chat, Message message) {}
	
	public void startSellingItem() {
		connection.connect();
		connection.login(item, "sniper");
		chatManager = connection.getChatManager();
		chatManager.addChatListener(this);
	}
	
	public void hasReceivedJoinRequestFromSniper() {}
	
	public void announceClosed() {}
	
	public void stop() {}
}