import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class FakeAuctionServer {
	
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String XMPP_HOSTNAME = "localhost";
	private static final String AUCTION_PASSWORD = "auction";
	
	public static final String BUYER_ID = "sniper@roco-3";

	private final String itemID;
	private XMPPConnection connection;
	private Chat currentChat;
	
	private final SingleMessageListener messageListener = new SingleMessageListener();
	
	public class SingleMessageListener implements MessageListener {
		@Override
    public void processMessage(Chat currentChat, Message message) {
    	/*
      String messageBody = message.getBody().toString();
      String command = getSniperCommandFromMessage(messageBody);
      isJoinCommand = command.equals("JOIN") ? true : false;
      */
    }
    
    public void receivesAMessage() {}
  }
  	
	private boolean isJoinCommand;
	
	FakeAuctionServer(String item) {
		this.itemID = item;
		this.connection = new XMPPConnection(
			new ConnectionConfiguration(XMPP_HOSTNAME,5222,AUCTION_RESOURCE));
		SmackConfiguration.setLocalSocks5ProxyEnabled(false);
	}
	
	public void startSellingItem() throws XMPPException{
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemID),
		                 AUCTION_PASSWORD, AUCTION_RESOURCE);
		connection.getChatManager().addChatListener(
			new ChatManagerListener() {
				public void chatCreated(Chat chat, boolean createdLocally) {
					currentChat = chat;
					chat.addMessageListener(messageListener);
				}
			});
	}
	
	public String getItemID() {
		return this.itemID;
	}
	
	public void hasReceivedJoinRequestFromSniper() {
		messageListener.receivesAMessage();
	}
	
	String getSniperCommandFromMessage(String message) {
		// example message-->SQLVersion: 1.1; Command: JOIN;<--
		
		final String ON_SEMICOLON_DELIMITER = ";";
		String[] fields = message.split(ON_SEMICOLON_DELIMITER);

		/* REGEX means: the text "Command: ", start remembering, followed by
		                one of "JOIN" or "BID", stop remembering
		 */
		final String REGEX = "Command: (JOIN|BID)";
		Pattern pattern =
		Pattern.compile(REGEX);

		String returnValue = null;
		for(String field : fields) {
			Matcher matcher =
			pattern.matcher(field);
			
			if (field.contains("Command:")) {
			 	matcher.find();
			 	returnValue = matcher.group(1);
			}
			
		}
		return returnValue;
		///return "Just a plain Jane string, dear.";
	}

	public void announceClosed() throws XMPPException {
		currentChat.sendMessage("Auction closed");
		//currentChat.sendMessage(new Message());
	}
	
	public void stop() {
		connection.disconnect();
	}
}