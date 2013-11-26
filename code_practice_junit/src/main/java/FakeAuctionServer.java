import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

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
	private final XMPPConnection connection;
	private Chat currentChat;
	
	private final SingleMessageListener messageListener = new SingleMessageListener();
  	
	private boolean isJoinCommand; // this is mine, not the book's
	
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
				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
					currentChat = chat;
					chat.addMessageListener(messageListener);
				}
			});
	}
	
	public String getItemID() {
		return this.itemID;
	}
	
	public void hasReceivedJoinRequestFromSniper() throws InterruptedException {
		messageListener.receivesAMessage();
	}
	
	// <mlr 131126: begin - p. 105, single item: join, bid, and lose>
	public void reportPrice(int price, int bidIncrement, String currentWinner) throws XMPPException {
		currentChat.sendMessage("SQLVersion: 1.1; Command: PRICE-" + price + "," + bidIncrement + ";");
	}
	public void hasReceivedBid(int bidAmound, String bidder) throws InterruptedException {
		messageListener.receivesAMessage();	
	}
	// <mlr 131126: end - p. 105, single item: join, bid, and lose>
	
	public void announceClosed() throws XMPPException {
		//currentChat.sendMessage(new Message());
		currentChat.sendMessage("SQLVersion: 1.1; Command: CLOSED;");
	}
	
	public void stop() {
		connection.disconnect();
	}
	
	public class SingleMessageListener implements MessageListener {
		private final ArrayBlockingQueue<Message> messages =
			        new ArrayBlockingQueue<Message>(1);
		
		@Override
    public void processMessage(Chat currentChat, Message message) {
    	messages.add(message);
    	/* <mlr 131113: begin - this is my stuff, not the book's> *
      String messageBody = message.getBody().toString();
      String command = getSniperCommandFromMessage(messageBody);
      isJoinCommand = command.equals("JOIN") ? true : false;
      *<mlr 131113: end - this is my stuff, not the book's> */
    }
    
    public void receivesAMessage() throws InterruptedException {
    	//System.out.println("FAS: message received -->" + message.getBody() + "<--");
    	assertThat("Message", messages.poll(5, TimeUnit.SECONDS), is(notNullValue()));
    }
  }
  
  /* <mlr 131113: begin - this is my stuff, not the book's> */
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
  /* <mlr 131113: end - this is my stuff, not the book's> */

}